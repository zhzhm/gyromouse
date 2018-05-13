package cn.zzm.remote.keyboard;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.rmi.Remote;

public class App {
    public static void main(String args[]) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void go2() {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < 1024; i++) {
                if (i == 10) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
                robot.mouseMove(i, i / 2);
                Thread.sleep(4l);
            }
            robot.keyPress(KeyEvent.VK_ESCAPE);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void go() throws InterruptedException {
        while (true) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            System.out.printf("x:%d, y:%d \n", p.x, p.y);
            Thread.sleep(200l);
        }

    }

    public static void start() {
        Server server = new Server(7778);

        /* webSocket的handler */

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        ContextHandler context = new ContextHandler();
        /* 路径 */
        handler.setContextPath("/ws");
        handler.addServlet(new ServletHolder(RemoteController.class), "/ws");
        ResourceHandler webHandler = new ResourceHandler();  //静态资源处理的handler
        「」
        webHandler.setDirectoriesListed(true);  //会显示一个列表
        webHandler.setWelcomeFiles(new String[]{"index.html"});
        webHandler.setResourceBase("www");

        HandlerList handlers = new HandlerList();
//        handlers.addHandler(handler);
        handlers.addHandler(webHandler);

        server.setHandler(handlers);

        try {
            /* 启动服务端 */
            server.start();
            server.join();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
