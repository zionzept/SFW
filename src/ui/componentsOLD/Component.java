package ui.componentsOLD;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;




import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import ui.Action;
import ui.ZUI;
import ui.componentsOLD.Button;
import wrap.ZTexture;

public abstract class Component {

	final int CENTER = 0;

	private ZUI zui;
	
	Texture texture;
	Texture hlTexture;
	Texture clickedTexture;
	private String text;
	private String finalText;
	private ZTexture overlayTexture;

	private double alpha;

	boolean hl;
	boolean clicked;
	private boolean clickAction;
	private boolean clickedAction;
	private boolean hlSoundAction;
	private boolean clickSoundAction;
	private boolean clickedSoundAction;
	private int clickActionID;
	private int clickedActionID;
	private int hlSoundActionID;
	private int clickSoundActionID;
	private int clickedSoundActionID;

	protected boolean focusable;
	private boolean focused;

	private boolean overwriteText;

	protected Color textColor = Color.white;
	protected Color inputTargetColor = new Color(0, 200, 0);
	protected Color inputTargetColorOverwrite = new Color(144, 144, 200);

	protected double xPos;
	protected double yPos;

	private boolean keyInput;

	protected double width;
	protected double height;
	private int textPos;
	protected int link;

	protected int textRelativeTo;

	private boolean animating;
	private int previousRowsWidth;
	private int animationSpeed;
	private int animationPoint;
	private int animationCD;

	final int KEYCODE = 0;
	final int NUMERICDOUBLE = 1;
	final int NUMERICINT = 2;
	final int ALPHABETIC = 3;

	private boolean dotUsed = true;

	private Integer intLink;
	private Double doubleLink;
	private Boolean booleanLink;

	public ArrayList<Text> texts = new ArrayList<Text>();

	enum LinkType {
		integer, decimal, binary, string, stringAlpha
	}

	protected LinkType linkType;

	protected Component(double x, double y, ZUI zui) {
		this.zui = zui;
		xPos = x;
		yPos = y;
		alpha = 1;
	}

	public void link() {
		switch (linkType) {
		case integer:
			intLink = Integer.parseInt(text);
			break;
		case decimal:
			doubleLink = Double.parseDouble(text);
			break;
		case binary:
			break;
		case string:
			// add
			break;
		case stringAlpha:
			break;
		}
		focused = false;
		if (linkType == LinkType.decimal) {
			text = Double.toString(Double.parseDouble(text));
		}
		ZUI.focusedComponent = null;
		ZUI.keyInputTarget = null;
	}

	public void setLink(Integer zi) {
		intLink = zi;
		linkType = LinkType.integer;
		setText(Integer.toString(intLink));
	}

	public void setLink(Double zd) {
		doubleLink = zd;
		linkType = LinkType.decimal;
		setText(Double.toString(doubleLink));
	}

	public void setLink(Boolean zb) {
		booleanLink = zb;
		linkType = LinkType.binary;
		setBooleanText(booleanLink);
	}

	public void setSize(double x, double y) {
		width = x;
		height = y;
	}

	public boolean interference(int x, int y) {
		if (x >= xPos && x < xPos + width && y >= yPos && y < yPos + height)
			return true;
		return false;
	}

	public void click() {
		clicked = true;
		if (clickAction)
			executeAction(clickActionID);
	}

	public void clicked() {
		clicked = false;
		if (clickedAction)
			executeAction(clickedActionID);
	}

	public void setClickActionID(int a) {
		clickActionID = a;
		clickAction = true;
	}

	public void setClickedActionID(int a) {
		clickedActionID = a;
		clickedAction = true;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
		width = texture.getImageWidth();
		height = texture.getImageHeight();
	}

	public void setHighlightedTexture(Texture texture) {
		hlTexture = texture;
	}

	public void setClickedTexture(Texture texture) {
		clickedTexture = texture;
	}

	public void setOverlayTexture(ZTexture texture, int pos) {
		overlayTexture = texture;
	}

	public void animate() {
		if (animationCD < 0) {
			if (width - previousRowsWidth > width
					&& finalText.charAt(animationPoint) == ' ') {
				// previousRowsWidth = width;
				setText(getText() + "@");
			} else
				setText(getText() + finalText.charAt(animationPoint));
			animationCD = animationSpeed;
			if (++animationPoint == finalText.length())
				animating = false;
		}
		animationCD--;
	}

	public void deleteChar() {
		if (overwriteText)
			text = "";
		else {
			if (dotUsed && text.charAt(text.length() - 1) == '.')
				dotUsed = false;
			text = text.substring(0, text.length() - 1);
		}
	}

	public void dot() {
		if (overwriteText) {
			text = "" + '.';
			overwriteText = false;
		} else if (!dotUsed)
			text += '.';
		dotUsed = true;
	}

	public void setFinalText(String txt) {
		finalText = txt;
	}

	public void setAnimationSpeed(int animSpeed) {
		animationSpeed = animSpeed;
		animating = true;
	}

	public void setText(String txt) {
		text = txt;
	}

	public void setBooleanText(Boolean b) {
		if (b)
			text = "yes";
		else
			text = "no";
	}

	public void setTextPosition() {

	}

	public void refreshTextPosition() {

	}

	public String getText() {
		if (linkType == LinkType.binary) {
			if (booleanLink)
				return "yes";
			else
				return "no";
		}
		return text;
	}

	/*
	 * public Text lastAddedText() { return texts.get(texts.size() - 1); }
	 */

	public void keyInput(int keyCode) {
		if (keyCode == 10 || keyCode == 27) {
			link();
		} else if (keyCode == 8)
			deleteChar();
		else if (keyCode == 46 && linkType == LinkType.decimal)
			dot();
		else
			keyPress(keyCode);
	}

	public void keyPress(int keyCode) {
		if (keyCode > 64 && keyCode < 91 && linkType == LinkType.string)
			input(keyCode);
		else if (keyCode > 47 && keyCode < 58
				&& linkType != LinkType.stringAlpha)
			input(keyCode);
	}

	public void input(int keyCode) {
		if (overwriteText) {
			text = "" + (char) (keyCode);
			dotUsed = false;
			overwriteText = false;
		} else
			text += (char) (keyCode);
	}

	public void mouse(double x, double y) {
		if (x >= xPos && x < xPos + width && y >= yPos && y < yPos + height) {
			if (hl == false) {
				executeAction(Action.HLSOUND);
			}
			hl = true;
		} else
			hl = false;
		if (dragging) {
			drag(ZUI.mouseX, ZUI.mouseY);
		}
	}

	public boolean press(double x, double y) {
		if (x >= xPos && x < xPos + width && y >= yPos && y < yPos + height) {
			clicked = true;
			if (clickAction) {
				executeAction(Action.CLICKSOUND);
				executeAction(clickActionID);
				if (dragging) {
					dragFromX = ZUI.mouseX;
					dragFromY = ZUI.mouseY;
				}
			}
			return true;
		}
		return false;
	}

	public void release(double x, double y) {
		clicked = false;
		if (x >= xPos && x < xPos + width && y >= yPos && y < yPos + height) {
			if (clickedAction) {
				if (clickedSoundAction) {
					executeAction(clickedSoundActionID);
				}
				executeAction(clickedActionID);
				if (dragging) {
					dragFromX = ZUI.mouseX;
					dragFromY = ZUI.mouseY;
				}
			}
		}
	}

	public void render(double x, double y) {
		x += xPos;
		y += yPos;
		boolean check = false;
		if (clickedTexture != null && clicked) {
			clickedTexture.bind();
			check = true;
		} else if (hlTexture != null && hl) {
			hlTexture.bind();
			check = true;
		} else if (texture != null) {
			texture.bind();
			check = true;
		}
		if (check) {
			glBegin(GL_QUADS);
			GL11.glColor4d(1, 1, 1, alpha);
			glTexCoord2d(0, 0);
			glVertex2d(x * ZUI.WIDTH, y * ZUI.HEIGHT);
			glTexCoord2d(1, 0);
			glVertex2d((x + width) * ZUI.WIDTH, y * ZUI.HEIGHT);
			glTexCoord2d(1, 1);
			glVertex2d((x + width) * ZUI.WIDTH, (y + height) * ZUI.HEIGHT);
			glTexCoord2d(0, 1);
			glVertex2d(x * ZUI.WIDTH, (y + height) * ZUI.HEIGHT);
			glEnd();

			if (overlayTexture != null) {
				overlayTexture.texture().bind();
				double w = (width * ZUI.WIDTH) < overlayTexture.width() ? width
						* ZUI.WIDTH
						: overlayTexture.width(), h = (height * ZUI.HEIGHT) < overlayTexture
						.height() ? height * ZUI.HEIGHT : overlayTexture
						.height();

				glBegin(GL_QUADS);
				glTexCoord2d(0, 0);
				glVertex2d((x + width / 2) * ZUI.WIDTH - w / 2,
						(y + height / 2) * ZUI.HEIGHT - h / 2);
				glTexCoord2d(1, 0);
				glVertex2d((x + width / 2) * ZUI.WIDTH + w / 2,
						(y + height / 2) * ZUI.HEIGHT - h / 2);
				glTexCoord2d(1, 1);
				glVertex2d((x + width / 2) * ZUI.WIDTH + w / 2,
						(y + height / 2) * ZUI.HEIGHT + h / 2);
				glTexCoord2d(0, 1);
				glVertex2d((x + width / 2) * ZUI.WIDTH - w / 2,
						(y + height / 2) * ZUI.HEIGHT + h / 2);
				glEnd();
			}
		}
		for (Text t : texts) {
			t.render(xPos, yPos);
		}

		/*
		 * g.drawImage(hlImage, x + xPos, y + yPos, null); else
		 * g.drawImage(image, x + xPos, y + yPos, null); if (hasText) { if
		 * (Base.dt.keyInputTarget == this) { g.setColor(overwriteText?
		 * inputTargetColorOverwrite : inputTargetColor); } else
		 * g.setColor(textColor); g.setFont(font); if (textRelativeTo == 2 ||
		 * textRelativeTo == 1) { g.drawString(text, x + xPos + 10, y + yPos +
		 * height / 2 + 10); } else { g.drawString(text, x + xPos, y + yPos); }
		 * if (animating) animate(); }
		 */
	}

	public void executeAction(int a) {
		if (a < 0) {
			switch (a) {
			case Action.REMOVEPANEL:
				removeContainingPanel();
				break;

			case Action.SETDRAG:
				dragging = true;
				break;
			case Action.SETNODRAG:
				dragging = false;
				break;
			case Action.SETKEYINPUTTARGET:
				if (ZUI.keyInputTarget != this) {
					if (ZUI.keyInputTarget != null)
						ZUI.keyInputTarget.link();
					overwriteText = true;
				} else
					overwriteText = !overwriteText;
				ZUI.keyInputTarget = this;
				break;
			case Action.LINKBOOLEAN:
				booleanLink = !booleanLink;
				setBooleanText(booleanLink);
				break;
			case Action.TROLLREDUCEWIDTH:
				width *= .9;
				break;
			case Action.CLICKSOUND:
				zui.clicksound();
				break;
			case Action.HLSOUND:
				zui.hlsound();
			}
		}
		zui.executeAction(a);
	}

	private void removeContainingPanel() {
		for (Panel p : ZUI.panels) {
			if (p.components.contains(this)) {
				ZUI.panels.remove(p);
				break;
			}
		}
	}

	private boolean dragging = false;
	private double dragFromX, dragFromY;

	private void drag(double x, double y) {
		for (Panel p : ZUI.panels) {
			if (p.components.contains(this)) {
				p.slide(x - dragFromX, y - dragFromY);
				dragFromX = x;
				dragFromY = y;
				break;
			}
		}
		System.out.println(x);
	}

}
