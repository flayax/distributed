package com.gupaoedu.rmi.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ProcessorHandler implements Runnable{

    private Socket socket;
    private Object service; //服务端发布的服务

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        //TODO 处理请求
        ObjectInputStream inputStream=null;
        try {
            inputStream=new ObjectInputStream(socket.getInputStream());

            RpcRequest request=(RpcRequest) inputStream.readObject();
            Object result=invoke(request);

            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private Object invoke(RpcRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object[] args=request.getParameters();
        Class<?>[] types=new Class[args.length];
        for(int i=0;i<args.length;i++){
            types[i]=args[i].getClass();
        }
        Method method=service.getClass().getMethod(request.getMethodName(),types);
        return method.invoke(service,args);
    }
}
