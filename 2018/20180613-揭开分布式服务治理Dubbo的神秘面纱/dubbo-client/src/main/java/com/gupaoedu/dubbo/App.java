package com.gupaoedu.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        ClassPathXmlApplicationContext context=new
                ClassPathXmlApplicationContext
                ("dubbo-client.xml");

        for(int i=0;i<10;i++) {
            //得到IGpHello的远程代理对象
            IGpHello iGpHello = (IGpHello) context.getBean("gpHelloService");

            System.out.println(iGpHello.sayHello("Mic"));
            Thread.sleep(1000);
        }

        System.in.read();
    }
}
