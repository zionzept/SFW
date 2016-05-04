package aaaa_testing;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Paul
 */

public class DrawPoints {
    public static void main(String[] args) {
        try {
            // create the display
            Display.create();

            // clear the color buffer to black;
            GL11.glClearColor(0.0f,0.0f,0.0f,1.0f);

            // loop until the Display window is closed
            while(!Display.isCloseRequested())
            {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            // OpenGL 1.1 drawing routine
            GL11.glBegin(GL11.GL_POINTS);
            GL11.glVertex2f(0.5f,  0.0f);
             GL11.glVertex2f( 0.0f,  0.5f);
             GL11.glVertex2f(0.0f, -0.5f);
             GL11.glVertex2f(-0.5f, -0.0f);
            GL11.glEnd();

            // update the Display and read events
            Display.update();
            }

            // destroy the Display
            Display.destroy();

        } catch (LWJGLException ex) {
            Logger.getLogger(DrawPoints.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}