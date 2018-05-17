package cn.zzm.remote.keyboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class MouseRobot {
    private static final Logger LOGGER = LoggerFactory.getLogger(MouseRobot.class);
    private static final int RATE = 30;
    private int x;
    private int y;

    private Point origin;
    private Robot robot;

    public MouseRobot() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public void setMouse(double x, double y, double z) {
        int intX = (int) Math.round(x * RATE), intY = (int) Math.round(y * RATE);
        int offsetX = 0, offsetY = 0;
        if (origin == null) {
            origin = new Point((int) (x * RATE), (int) (y * RATE));
            return;
        } else {
            offsetX = origin.x - intX;
            offsetY = origin.y - intY;
            origin.setLocation(intX, intY);
        }

        Point currentMouse = MouseInfo.getPointerInfo().getLocation();
        //LOGGER.info("offsetX:"+offsetX+",   offsetY:"+offsetY);

        currentMouse.setLocation(currentMouse.x + offsetX, currentMouse.y + offsetY);
//        currentMouse.x = currentMouse.x<0?0:currentMouse.x;
//        currentMouse.y = currentMouse.y<0?0:currentMouse.y;
        robot.mouseMove(currentMouse.x, currentMouse.y);

    }
}
