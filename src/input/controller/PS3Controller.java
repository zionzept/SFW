package input.controller;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

import net.java.games.input.*;


public class PS3Controller implements ClipboardOwner{
	static PS3Controller ct;
	
	
	private boolean running = true;
	
	private final int instances = 10;
	private boolean antighost[] = new boolean[instances];
	private int action[] = new int[instances];
	
	private Controller gamepad;
	private SComponent y; 
	private SComponent o;
	private SComponent x; 
	private SComponent z; 
	private SComponent lx; 
	private SComponent ly; 
	private SComponent rx; 
	private SComponent ry; 
	private SComponent l1; 
	private SComponent r1; 
	private SComponent start; 
	private SComponent select; 
	private SComponent tilt; 
	private SComponent angle; 
	private SComponent lr2;
	
	
	private RumblerManager rumbleManager;
	private Robot robot;
	
	
	private final float SPEED_NORMAL = 14.0f, SPEED_FACTOR = 1.1f;
	private float speed = SPEED_NORMAL;
	private float scrollDelay = 200f;
	
	private boolean selectProfile;
	private int profile;

	public PS3Controller() {
		
		setUpDisplayAndRobot();
		setupController();
		
		while (running) {
			updateController();
			try {
				Thread.sleep(8);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			/*if (Display.isCloseRequested() || (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Keyboard.isKeyDown(Keyboard.KEY_V)))
					running = false;
			Display.update();
			Display.sync(120);*/
		}
		//Display.destroy();
		System.exit(0);
	}
	
	private void updateController() {
		gamepad.poll();
		
		if (select.getPollData() == 1) {
			if (!antighost[6]) {
				antighost[6] = true;
				if (!selectProfile) {
					action[0] = 64;
					action[1] = 65;
					action[2] = 66;
					action[3] = 67;
					rumblePattern(0);
				}
				selectProfile = true;
			}
		}
		else if (antighost[6]) {
			antighost[6] = false;
		}
		
		if (!selectProfile) {
			updateAxises();
			updateOtherButtons();
		}
		updateXYZO();
	}
	
	
	
	
	private void unbindActions() {
		for (int i = 0; i < instances; i++) {
			action[i] = -1;
		}
	}
	
	private void bindActions() {
		System.out.println(profile);
		switch (profile) {
			default:
				break;
				
			case 1:
				action[0] = 0;
				action[1] = 1;
				action[4] = 3;
				action[5] = 4;
				break;
			case 2:
				action[0] = 0;
				action[1] = 1;
				action[4] = 3;
			case 4:
				action[0] = 18;
				action[1] = 1;
				action[2] = 16;
				action[3] = 17;
				action[4] = 1338;
				action[5] = 1337;
				
		}
	}
	
	private void executeAction(int i) {
		switch (i) {
		case 0:
			robot.mousePress(InputEvent.BUTTON1_MASK);
			break;
		case 1:
			robot.mousePress(InputEvent.BUTTON3_MASK);
			break;
		case 2:
			robot.keyPress(17);
			break;
		case 3:
			robot.keyPress(27);
			break;
		case 4:
			robot.keyPress(32);
			break;
		case 16:
			robot.keyPress(86);
			break;
		case 17:
			robot.keyPress(67);
			break;
		case 64:
			setProfile(1);
			break;
		case 65:
			setProfile(2);
			break;
		case 66:
			setProfile(3);
			break;
		case 67:
			setProfile(4);
			break;
		case 1337:
			phatAction1();
			break;
		case 1338:
			spamLMB();
			break;
		}
	}
	
	private void releaseAction(int i) {
		switch (i) {
		case 0:
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
		case 1:
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
			break;
		case 2:
			robot.keyRelease(17);
			break;
		case 3:
			robot.keyRelease(27);
			break;
		case 4:
			robot.keyRelease(32);
			break;
		case 16:
			robot.keyRelease(86);
			break;
		case 17:
			robot.keyRelease(67);
			break;
		case 1338:
			spamLMB = false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean scrollCheck ;
	private void scroll() {
		new Thread(new Runnable() {
			public void run() {
				if (!scrollCheck) {
					scrollCheck = true;
					if (ry.getPollData() > 0.02 || ry.getPollData() < -0.02)
						robot.mouseWheel(ry.getPollData() < 0? -1 : 1);
					try {
						Thread.sleep((int) (scrollDelay * (1 - Math.abs(ry.getPollData()))));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					scrollCheck = false;
				}
			}
		}).start();
	}
	
	private void updateAxises() {
		switch(profile) {
		default:
			break;
			
		case 1:
			speed = SPEED_NORMAL * (SPEED_FACTOR + lr2.getPollData());		
			if (r1.getPollData() == 1) {
				xPos = WIDTH + WIDTH * lx.getPollData() * 1.1;
				yPos = HEIGHT + HEIGHT * ly.getPollData() * 1.1;
			}
			else {
				xPos += (lx.getPollData() < 0 && lx.getPollData() > -0.01 ? 0 : lx.getPollData() * speed);
				yPos += (ly.getPollData() < 0 && ly.getPollData() > -0.01 ? 0 : ly.getPollData() * speed);
			}
			robot.mouseMove((int) xPos, (int) yPos);
			scroll();
			break;
			
		case 2:
			speed = SPEED_NORMAL * (SPEED_FACTOR + lr2.getPollData());		
			if (r1.getPollData() == 1) {
				xPos = WIDTH + WIDTH * -angle.getPollData() * 1.1;
				yPos = HEIGHT + HEIGHT * tilt.getPollData() * 1.1;
			}
			else {
				xPos += (-angle.getPollData() < 0 && -angle.getPollData() > -0.01 ? 0 : -angle.getPollData() * speed);
				yPos += (tilt.getPollData() < 0 && tilt.getPollData() > -0.01 ? 0 : tilt.getPollData() * speed);
			}
			robot.mouseMove((int) xPos, (int) yPos);
			
			break;
		}
	}
	
	private void memrise3xClick() {
		if (x.getPollData() == 1) {
			if (!antighost[0]) {
				new Thread(new Runnable() {
					public void run() {
						try {
							robot.keyPress(KeyEvent.VK_ENTER);
							Thread.sleep(10);
							robot.keyRelease(KeyEvent.VK_ENTER);
							Thread.sleep(10);
							robot.keyPress(KeyEvent.VK_ENTER);
							Thread.sleep(10);
							robot.keyRelease(KeyEvent.VK_ENTER);
							Thread.sleep(40);
							robot.keyPress(KeyEvent.VK_ENTER);
							Thread.sleep(10);
							robot.keyRelease(KeyEvent.VK_ENTER);
						} catch (InterruptedException e) {
						}
					}
				}).start();
				antighost[0] = true;
			}
		}
		else  {
			if (antighost[0]) {
				antighost[0] = false;
			}
		}
	}
	
	private void updateXYZO() {
		//--------------------------------------------- X
		if (x.getPollData() == 1) {
			if (!antighost[0]) {
				executeAction(action[0]);
				antighost[0] = true;
			}
		}
		else  {
			if (antighost[0]) {
				releaseAction(action[0]);
				antighost[0] = false;
			}
		}
				
		//--------------------------------------------- Square
		if (z.getPollData() == 1) {
			if (!antighost[1]) {
				executeAction(action[1]);
				antighost[1] = true;
			}
		}
		else {
			if (antighost[1]) {
				releaseAction(action[1]);
				antighost[1] = false;
			}
		}
		
		//--------------------------------------------- O
		if (o.getPollData() == 1) {
			if (!antighost[2]) {
				executeAction(action[2]);
				antighost[2] = true;
			}
		}
		else {
			if (antighost[2]) {
				releaseAction(action[2]);
				antighost[2] = false;
			}
		}
			
		//--------------------------------------------- Triangle
		if (y.getPollData() == 1) {
			if (!antighost[3]) {
				executeAction(action[3]);
				antighost[3] = true;
			}
		}
		else {
			if (antighost[3]) {
				releaseAction(action[3]);
				antighost[3] = false;
			}
		}
	}
	
	private void updateOtherButtons() {
		//--------------------------------------------- Start
		if (start.getPollData() == 1) {
			if (!antighost[4]) {
				executeAction(action[4]);
				antighost[4] = true;
			}
		}
		else {
			if (antighost[4]) {
				releaseAction(action[4]);
				antighost[4] = false;
			}
		}
				
		//--------------------------------------------- L1
		if (l1.getPollData() == 1) {
			if (!antighost[5]) {
				executeAction(action[5]);
				antighost[5] = true;
			}
		}
		else {
			if (antighost[5]) {
				releaseAction(action[5]);
				antighost[5] = false;
			}
		}
	}
	
	private void setProfile(int i) {
		xPos = WIDTH;
		yPos = HEIGHT;
		profile = i;
		selectProfile = false;
		unbindActions();
		bindActions();
		rumblePattern(i);
		System.out.println("Profile: " + profile);
	}
	
	private void setupController() {
		for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if (c.getType() == Controller.PS3Controller.STICK) {
				gamepad = c;
				System.out.println(gamepad.getName());
			}
		}
		
		if (gamepad == null) {
			System.err.println("No gamepad was found.");
			Display.destroy();
			System.exit(1);
		}
		
		Rumbler[] rumblers = gamepad.getRumblers();
		rumbleManager = new RumblerManager(rumblers);
		
		for (SComponent c : gamepad.getComponents()) {
			//System.out.println(c.getIdentifier().getName() + ": " + c.getName() + ": " + (c.isAnalog()? "analog: " : "digital: ") + c.getPollData());
			switch (c.getIdentifier().getName()) {
			case "0":
				y = c;
				break;
			case "1":
				o = c;
				break;
			case "2":
				x = c;
				break;
			case "3":
				z = c;
				break;
			case "6":
				l1 = c;
				break;
			case "7":
				r1 = c;
				break;
			case "8":
				select = c;
				break;
			case "9":
				start = c;
				break;
			case "x":
				lx = c;
				break;
			case "y":
				ly = c;
				break;
			case "rx":
				rx = c;
				break;
			case "ry":
				ry = c;
				break;
			case "slider":
				tilt = c;
				break;
			case "rz":
				angle = c;
				break;
			case "z":
				lr2 = c;
				break;
			}
		}
	}
	
	public void setUpDisplayAndRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		/*try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Display was not able to be created.");
			Display.destroy();
			System.exit(1);
		}*/
	}
	
	public static void main(String[] args) {
		ct = new PS3Controller();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	final double oldBPM = 280;
	final double bpm = 220.01;
	final double offset = 19;
	
	public void phatAction1() {
		new Thread(new Runnable() {
			public void run() {
				try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					Thread.sleep(4);
					robot.keyPress(KeyEvent.VK_C);
					Thread.sleep(4);
					robot.keyRelease(KeyEvent.VK_C);
					Thread.sleep(4);
					robot.keyRelease(KeyEvent.VK_CONTROL);
				} catch (InterruptedException e1) {
					System.out.println("robot break");
					return;
				}
				
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				String data = "0";
				try {
					data = (String) Toolkit.getDefaultToolkit()
					        .getSystemClipboard().getData(DataFlavor.stringFlavor);
				} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
					e.printStackTrace();
				}
				
				
				int copy;
				try {
					copy = Integer.parseInt(data);
				} catch (NumberFormatException e) {
					System.out.println("non-numeric");
					return;
				}
				StringSelection stringSelection = new StringSelection(Integer.toString((int)(copy * oldBPM / bpm + offset)));
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents( stringSelection, ct );
			    try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    try {
					robot.keyPress(KeyEvent.VK_CONTROL);
					Thread.sleep(4);
					robot.keyPress(KeyEvent.VK_V);
					Thread.sleep(4);
					robot.keyRelease(KeyEvent.VK_V);
					Thread.sleep(4);
					robot.keyRelease(KeyEvent.VK_CONTROL);
				} catch (InterruptedException e1) {
					System.out.println("robot break");
					return;
				}
			}
		}).start();
	}
	
	private boolean spamLMB;
	private void spamLMB() {
		spamLMB = true;
		new Thread (new Runnable() {
			public void run() {
				while (spamLMB) {
					try {
						robot.mousePress(InputEvent.BUTTON1_MASK);
						Thread.sleep(4);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);
						Thread.sleep(16);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		
	}

}