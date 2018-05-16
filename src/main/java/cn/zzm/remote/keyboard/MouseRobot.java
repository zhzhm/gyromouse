package cn.zzm.remote.keyboard;

import java.awt.*;

public class MouseRobot {
    private int x;
    private int y;

    public void setMouse(double x, double y, double z){
        try {
            Robot robot = new Robot();
            robot.mouseMove((int)(1280 - (x * 10)), (int)(800 - y* 10));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
