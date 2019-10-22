/**
 * Authors - Kamran Bastani and Aidan Lee
 */
// Problems we have encountered so far have been with respect to drawing the board and moving the mhos
// We have chosen to use a 12x12 2D array to store all of the board positions 
// Note: more detailed comments will come later (for now I do not want to put in too many comments, as the code might
// change)

// Imports necesssary to complete this project
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;

public class hiVolts extends JFrame implements KeyListener {
	
	//instance creates new random number generator 
	Random rand = new Random();
	
	//declared variables used in this program
	int c;
	int r;
	int inputKey;
	boolean dead;
	int var;
	int first;
	
	//Arrays used to set the size of the board to 12 x 12
	int[][] boardPos = new int[12][12];
	int[] counterPos = new int[2];

	/**
	 * This method contains several conditionals and math equations to 
	 * set the frame of the game up
	 * @param g Graphics are used to establish the board
	 * including the fences, mhos, and player of the game
	 * and are also used to position each of these objects.
	 */
	public void drawboard(Graphics g) {
		for(int col = 0; col<12; col++) {
			for(int row = 0; row<12; row++) {
				if(col==0||col==11||row==0||row==11) {
					boardPos[col][row] = 1;
				}
			}
		}
		//for loop used to position each of the fences in random positions each time
		for(int i = 0; i<20; i++) {
			while(true) {
				c = rand.nextInt(9);
				c += 2;
				r = rand.nextInt(9);
				r += 2;
				if(boardPos[c][r]==0) {
					boardPos[c][r] = 1;
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
				if(boardPos[c][r]==0) {
					boardPos[c][r] = 2;
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
			if(boardPos[c][r]==0) {
				boardPos[c][r] = 3;
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

	// This figures out whether or not the player (or mho) is dead at a given position
	public boolean isdead(int newPosX, int newPosY) {
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
		for(int count = 0; count<12; count++) {
			for(int col = 1; col<11; col++) {
				for(int row = 1; row<11; row++) {
					if(boardPos[col][row]==2) {
						if(col==counterPos[0]) {
							if(row<counterPos[1]) {
								boardPos[col][row] = 0;
								if(isdead(col, row+1)==true) {
									g.setColor(Color.BLACK);
									g.fillRect(calcCoord(col), calcCoord(row), 20, 20);
									g.setColor(Color.YELLOW);
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
								if(isdead(col, row-1)==true) {
									g.setColor(Color.BLACK);
									g.fillRect(calcCoord(col), calcCoord(row), 20, 20);
									g.setColor(Color.YELLOW);
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
