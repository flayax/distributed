package com.gupaoedu.rmi.rpc;

import java.lang.reflect.Proxy;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RpcClientProxy {


    public <T> T clientProxy(final Class<T>
                                     interfaceCls,
                             final String host,final int port){

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class[]{interfaceCls},new RemoteInvocationHandler(host,port));
    }




}
