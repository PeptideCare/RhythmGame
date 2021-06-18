package bcteamC.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bcteamC.Main;
import bcteamC.frame.GameStartFrame;

public class KeyListener extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent e) {
		if(GameStartFrame.game == null)
			return;
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_S:
				GameStartFrame.game.pressS();
				break;
			case KeyEvent.VK_D:
				GameStartFrame.game.pressD();
				break;
			case KeyEvent.VK_F:
				GameStartFrame.game.pressF();
				break;
			case KeyEvent.VK_SPACE:
				GameStartFrame.game.pressSpace();
				break;
			case KeyEvent.VK_J:
				GameStartFrame.game.pressJ();
				break;
			case KeyEvent.VK_K:
				GameStartFrame.game.pressK();
				break;
			case KeyEvent.VK_L:
				GameStartFrame.game.pressL();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(GameStartFrame.game == null)
			return;
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_S:
				GameStartFrame.game.releaseS();
				break;
			case KeyEvent.VK_D:
				GameStartFrame.game.releaseD();
				break;
			case KeyEvent.VK_F:
				GameStartFrame.game.releaseF();
				break;
			case KeyEvent.VK_SPACE:
				GameStartFrame.game.releaseSpace();
				break;
			case KeyEvent.VK_J:
				GameStartFrame.game.releaseJ();
				break;
			case KeyEvent.VK_K:
				GameStartFrame.game.releaseK();
				break;
			case KeyEvent.VK_L:
				GameStartFrame.game.releaseL();
				break;
		}
	}
	
}
