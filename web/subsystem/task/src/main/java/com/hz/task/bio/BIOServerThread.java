package com.hz.task.bio;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @explain
 * @Classname BIOTest
 * @Date 2022/1/6 16:01
 * @Created by hanzhao
 */
@Component
public class BIOServerThread implements Runnable {

    private Socket socket = null;

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 获取到socket输入输出流
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true){
                body = in.readLine();
                if(body == null){
                    // 读取到空值退出
                    break;
                }
                System.out.println("The time server receive order: " + body);
                currentTime = body.equals("查询时间")? new Date().toString():"查询错误！";
                out.println(currentTime);
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

    public BIOServerThread() {
    }

    public BIOServerThread(Socket socket) {
        this.socket = socket;
    }
}
