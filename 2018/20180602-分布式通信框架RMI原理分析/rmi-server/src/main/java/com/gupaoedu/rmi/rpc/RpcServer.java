package com.gupaoedu.rmi.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RpcServer {

    private static final ExecutorService executorService=Executors.newCachedThreadPool();

    public void publisher(final Object service,int port){
        ServerSocket serverSocket=null;
        try{
            serverSocket=new ServerSocket(port);  //启动一个服务监听

            while(true){
                Socket socket=serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
