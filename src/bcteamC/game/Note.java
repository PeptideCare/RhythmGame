package bcteamC.game;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import bcteamC.Main;

public class Note extends Thread {
	private Image noteBasicImage = new ImageIcon(Note.class.getResource("/images/image-game-noteBasic.png")).getImage();
	private int x, y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	private boolean proceeded = true;
	
	public Note(String noteType) {
		switch(noteType) {
			case "S":
				x = 228;
				break;
			case "D":
				x = 332;
				break;
			case "F":
				x = 436;
				break;
			case "Space":
				x = 540;
				break;
			case "J":
				x = 744;
				break;
			case "K":
				x = 848;
				break;
			case "L":
				x = 952;
				break;
		}
		
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		if(noteType.equals("Space")) {
			g.drawImage(noteBasicImage, x, y, null);
			g.drawImage(noteBasicImage, x+100, y, null);
		} else {
			g.drawImage(noteBasicImage, x, y, null);
		}
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);					
				} else {
					interrupt();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		proceeded = false;
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		
		if(y > 620) { // 노트가 판정라인 넘어서면 Miss
			System.out.println("Miss");
			close();
		}
	}
	
	public String judge() {
		 if(y > 600) {
			 System.out.println("Good");
	         close();
	         return "Good";
	      } else if(y > 570) {
	    	  System.out.println("Perfect");
	    	  close();
	    	  return "Perfect";
	      } else if(y > 550) {
			 System.out.println("Good");
	         close();
	         return "Good";
	      }
		 return "None";
	}
	
	public boolean isProceeded() { return proceeded; }
	
	public String getNoteType() { return noteType; }

	public int getY() { return y; }
	
}
