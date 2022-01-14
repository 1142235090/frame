package com.hz.task.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @explain
 * @Classname MultiplexerServer
 * @Date 2022/1/7 13:51
 * @Created by hanzhao
 */
public class MultiplexerServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerServer(int port) {
        try {
            //创建多路复用器，打开通道监听客户端的连接
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //设置为异步非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //绑定监听端口，默认监听本机，backlog当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度，默认是50
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            //初始化完成之后将channel通道注册到selector，监听ACCEPT（线程等待连接）操作
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("启动监听" + port + "端口");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    /**
     * 多路复用器在run方法中无限循环准备就绪的key,
     * 如果key对应的channel是准备连接，就设置参数开启监听
     * 如果对应的channel是读取则开始逻辑处理（这里方便写代码所以没有做半包读写）
     */
    @Override
    public void run() {
        while (!stop) {
            try {
                // 间隔一秒获取，准备就绪的channel，也有无参构造，只要channel准备好了就立刻返回该channel的SelectionKey
                selector.select(1000);
                // 当一个channel准备好可以被读取，或者已经读取完成，或者某个channel出现了错误那么就会添加进selectedKeys中
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key;
                // 遍历多路复用器中的选择键集合
                while (it.hasNext()) {
                    // 一旦开始处理该key，则要在集合中删除掉，避免重复操作
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 多路复用器关闭之后，所有注册在上边的channel和pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理key消息
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException {
        // 通过cancel方法取消键,取消的键不会立即从selector中移除,而是添加到cancelledKeys中,在下一次select操作时移除它.所以在调用某个key时,需要使用isValid进行校验.
        if (key.isValid()) {
            // 处理新接入的消息
            if (key.isAcceptable()) {
                // 获取选择键对应的通道
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                // 打开通道
                SocketChannel sc = ssc.accept();
                // 设置为异步
                sc.configureBlocking(false);
                // 重新注册进入多路复用器，监听读取
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                // 如果通道准备好被读取那么久开始处理增加逻辑
                SocketChannel sc = (SocketChannel) key.channel();
                // 创建一个定长的buffer
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                // 将数据读取到buffer中
                int readBytes = sc.read(readBuffer);
                // 如果大于0说明说明读取到了数据开始处理
                if (readBytes > 0) {
                    // 有数据的情况翻转缓冲区，将缓冲区当前的limit设置为position，position设置为0
                    readBuffer.flip();
                    // 创建字节数组
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = body.equals("查询时间") ? new Date().toString() : "查询错误！";
                    doWrite(sc, currentTime);
                }else if (readBytes < 0) { // 如果为-1则说明链路已经断开了，则需要释放资源
                    //对客户端链路关闭
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    /**
     * 向通道里写入数据
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
