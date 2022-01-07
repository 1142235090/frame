package com.hz.task.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @explain
 * @Classname BIOTest
 * @Date 2022/1/6 16:01
 * @Created by hanzhao
 */
public class BIOClientTest implements Runnable {
    @Override
    public void run() {
        int port = 9999;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("192.168.1.100",port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("查询时间");
            System.out.println("返回数据成功");
            String resp = in.readLine();
            System.out.println("现在时间："+resp);
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
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
