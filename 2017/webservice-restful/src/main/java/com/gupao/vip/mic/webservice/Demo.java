package com.gupao.vip.mic.webservice;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Demo {

    public static void main(String[] args) {
        SayHelloImplService service=new SayHelloImplService();
        SayHelloImpl sayHello=service.getSayHelloImplPort();
        System.out.println(sayHello.sayHello("Mic"));
    }
}
