package com.hz.task.nio;

/**
 * @explain
 * @Classname NioServer
 * @Date 2022/1/7 13:50
 * @Created by hanzhao
 */
public class NioServerStart {
    public static void main(String[] args) {
        int port = 9999;
        MultiplexerServer multiplexerServer = new MultiplexerServer(port);
        new Thread(multiplexerServer,"NIO-MultiplexerServer-01").start();
    }
}
