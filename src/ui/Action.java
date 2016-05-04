package ui;
public class Action {
	public static final int REMOVEPANEL = -1,
					  SETDRAG = REMOVEPANEL - 1,
					  SETNODRAG = SETDRAG - 1,
					  SETKEYINPUTTARGET = SETNODRAG - 1,
					  LINKBOOLEAN = SETKEYINPUTTARGET - 1,
					  TROLLREDUCEWIDTH = LINKBOOLEAN - 1,
					  
					  CLICKSOUND = TROLLREDUCEWIDTH - 1,
					  HLSOUND = CLICKSOUND - 1
					  ;	
}