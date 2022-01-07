package com.hz.task.syn.fake;

import com.hz.task.bio.BIOServerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @explain
 * @Classname FackSynServer
 * @Date 2022/1/6 17:21
 * @Created by hanzhao
 */
public class FackSynServer {

    public static void main(String[] args) {
        int port = 9999;
        ServerSocket serverSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        //线程池
        ServerHandlerExecutePool serverHandlerExecutePool = new ServerHandlerExecutePool(5,10000);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("启动监听");
            while (true){
                Socket socket = serverSocket.accept();
                serverHandlerExecutePool.execute(new BIOServerThread(socket));
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
