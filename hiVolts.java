/**
 * Authors - Kamran Bastani and Aidan Lee
 */
// Problems we have encountered so far have been with respect to drawing the board and moving the mhos
// We have chosen to use a 12x12 2D array to store all of the board positions 
// Note: more detailed comments will come later (for now I do not want to put in too many comments, as the code might
// change)

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;

public class hiVolts extends JFrame implements KeyListener {
	Random rand = new Random();
	int c;
	int r;
	int inputKey;
	boolean dead;
	int var;
	int first;
	
	//Sets the size of the board to 12 x 12
	int[][] boardPos = new int[12][12];
	int[] counterPos = new int[2];

	// This draws the board 
	public void drawboard(Graphics g) {
		for(int col = 0; col<12; col++) {
			for(int row = 0; row<12; row++) {
				if(col==0||col==11||row==0||row==11) {
					boardPos[col][row] = 1;
				}
			}
		}
		for(int i = 0; i<20; i++) {
			while(true) {
				c = rand.nextInt(9);
				c += 2;
				r = rand.nextInt(9);
				r += 2;
				if(boardPos[c-1][r-1]==0) {
					boardPos[c-1][r-1] = 1;
					break;
				}
			}
			counterPos[0] = r;
			counterPos[1] = c;
			fence(g, calcCoord(c), calcCoord(r));
		}
		for(int i = 0; i<12; i++) {
			while(true) {
				c = rand.nextInt(9);
				c += 3;
				r = rand.nextInt(9);
				r += 2;
				if(boardPos[c-1][r-1]==0) {
					boardPos[c-1][r-1] = 2;
					break;
				}
			}
			mho(g, calcCoord(c), calcCoord(r));
		}
		while(true) {
			c = rand.nextInt(9);
			c += 2;
			r = rand.nextInt(9);
			r += 2;
			if(boardPos[c-1][r-1]==0) {
				boardPos[c-1][r-1] = 3;
				counterPos[0] = c;
				counterPos[1] = r;
				break;
			}
		}
		counter(g, calcCoord(c), calcCoord(r));
		for(int i = 30; i<=12*30; i+=30) {
			fence(g, 50, i+20);
			fence(g, 380, i+20);
			fence(g, i+20, 50);
			fence(g, i+20, 380);
		}
	}

	// This figures out whether or not the player is dead at a given position
	public boolean isdeadPlayer(int newPosX, int newPosY) {
		dead = false;
		for(int col = 0; col<12; col++) {
			for(int row = 0; row<12; row++) {
				if((newPosX==1||newPosX==2)&&(newPosY==1||newPosY==2)) {
					dead = true;
				}
			}
		}
		return dead;
	}
	
	public boolean isdeadMho(int newPosX, int newPosY) {
		dead = false;
		for(int col = 0; col<12; col++) {
			for(int row = 0; row<12; row++) {
				if((newPosX==1&&newPosY==1)) {
					dead = true;
				}
			}
		}
		return dead;
	}

	// This method draws the electric fence
	public void fence(Graphics g, int startX, int startY) {
		int nextX = startX;
		int nextY = startY;
		g.drawLine(startX, startY, startX, startY+20);

		g.drawLine(startX, startY+5, startX+20, startY+5);
		g.drawLine(startX, startY+10, startX+20, startY+10);
		g.drawLine(startX, startY+15, startX+20, startY+15);

		g.drawLine(startX+20, startY, startX+20, startY+20);
	}

	// This calculates the graphics coordinate based on the grid place
	public int calcCoord(int pos) {
		return 50+(pos-1)*30;
	}

	// Draws a mho
	public void mho(Graphics g, int startX, int startY) {
		g.drawOval(startX, startY, 20, 20);
		g.fillOval(startX+5, startY+4, 3, 3);
		g.fillOval(startX+13, startY+4, 3, 3);
		g.drawLine(startX+5, startY+14, startX+10, startY+10);
		g.drawLine(startX+10, startY+10, startX+15, startY+14);
	}

	// Draws the player
	public void counter(Graphics g, int startX, int startY) {
		g.setColor(Color.RED);
		g.drawOval(startX, startY, 20, 20);
		g.fillOval(startX+5, startY+4, 3, 3);
		g.fillOval(startX+13, startY+4, 3, 3);
		g.drawLine(startX+5, startY+10, startX+10, startY+14);
		g.drawLine(startX+10, startY+14, startX+15, startY+10);
		g.setColor(Color.YELLOW);
	}

	// NOT FINISHED - intended to move a mho
	public void moveMho(Graphics g) {
		for(int mho = 0; mho<12; mho++) {
			int col = mhoPos[mho][0];
			int row = mhoPos[mho][1];
			if(col==counterPos[0]) {
				if(row<counterPos[1]) {
					boardPos[col][row] = 0;
					if(isdeadMho(col, row+1)==true) {
						g.setColor(Color.BLACK);
						g.fillRect(calcCoord(col), calcCoord(row), 20, 20);
						g.setColor(Color.YELLOW);
						boardPos[col][row] = 0;
					}
					else {
						boardPos[col][row+1] = 2;
						if((counterPos[0]==col)&&(counterPos[0]==row)) {
							// Dead
						}
					}
				}
				else if(row>counterPos[1]) {
					boardPos[col][row] = 0;
					if(isdeadMho(col, row-1)==true) {
						g.setColor(Color.BLACK);
						g.fillRect(calcCoord(col), calcCoord(row), 20, 20);
						g.setColor(Color.YELLOW);
						boardPos[col][row] = 0;
					}
					else {
						boardPos[col][row-1] = 2;
						if((counterPos[0]==col)&&(counterPos[0]==row)) {
							// Dead
						}
					}
				}
			}
			if(row==counterPos[1]) {
				if(col<counterPos[0]) {

				}
			}
		}
	}

	// NOT FINISHED - key listener
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_A) {
			System.out.println("Hello there");
			var = 1;
			repaint();
		}
	}
	public void keyTyped(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

	public hiVolts() {
		addKeyListener(this);
		init();
	}

	public void init() {
		setSize(700,700);
		setBackground(Color.BLACK);
		repaint();
	}

	// Paint
	public void paint(Graphics g) {
		
		g.setColor(Color.YELLOW);
		if(first==0) {
			drawboard(g);
		}
		
		if(var==1) {
			moveMho(g);
		}
		first = 1;
	}
}
