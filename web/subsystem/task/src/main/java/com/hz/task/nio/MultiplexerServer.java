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
            //创建多路复用器
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //设置为异步非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度，默认是50
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //初始化完成之后将channel通道注册到selector，
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("启动监听" + port + "端口");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (key != null) {
                        key.cancel();
                        if (key.channel() != null) {
                            key.channel().close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 多路复用器关闭之后，所有注册在上边的channel和pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key)throws IOException{
        if(key.isValid()){
            // 处理新接入的消息
            if(key.isAcceptable()){
                // accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                // add the new connection to the selector
                sc.register(selector,SelectionKey.OP_ACCEPT);
            }
            if(key.isReadable()){
                // read the data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = body.equals("查询时间")? new Date().toString():"查询错误！";
                    doWrite(sc,currentTime);
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if(response != null && response.trim().length() > 0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
