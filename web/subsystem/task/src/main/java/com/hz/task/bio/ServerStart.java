package com.hz.task.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @explain
 * @Classname BIOTest
 * @Date 2022/1/6 16:01
 * @Created by hanzhao
 */
public class ServerStart implements Runnable {
    @Override
    public void run() {
        int port = 9999;
        ServerSocket serverSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("启动监听");
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new BIOServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
                out = null;
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
    }
}
