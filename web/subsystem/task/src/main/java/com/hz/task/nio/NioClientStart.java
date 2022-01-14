package com.hz.task.nio;

/**
 * @explain
 * @Classname NioClientStart
 * @Date 2022/1/10 9:21
 * @Created by hanzhao
 */
public class NioClientStart {
    public static void main(String[] args) {
        new Thread(new NioClientThread("127.0.0.1", 9999), "NioClient0").start();
    }
}
