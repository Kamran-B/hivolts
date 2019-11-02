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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;

public class hiVolts extends JFrame implements KeyListener, MouseListener {

	Random rand = new Random();
	int c;
	int r;
	int inputKey;
	boolean dead;
	boolean notPlay = true;
	int var;
	int first;
	boolean pTurn = true;
	boolean newGame = true;
	boolean win = false;

	int[][] boardPos = new int[12][12];
	int[] counterPos = new int[2];
	int[][] mhoPos = new int[12][2];
	int[][] fencePos = new int[20][2];


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
				c = rand.nextInt(10);
				c += 2;
				r = rand.nextInt(10);
				r += 2;
				if(boardPos[c-1][r-1]==0) {
					boardPos[c-1][r-1] = 1;
					break;
				}
			}
			fencePos[i][0] = c;
			fencePos[i][1] = r;
			fence(g, calcCoord(c), calcCoord(r));
		}
		for(int i = 0; i<12; i++) {
			while(true) {
				c = rand.nextInt(10);
				c += 2;
				r = rand.nextInt(10);
				r += 2;
				if(boardPos[c-1][r-1]==0) {
					boardPos[c-1][r-1] = 2;
					break;
				}
			}
			mhoPos[i][0] = c;
			mhoPos[i][1] = r;
			mho(g, calcCoord(c), calcCoord(r));
		}
		while(true) {
			c = rand.nextInt(10);
			c += 2;
			r = rand.nextInt(10);
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

	// This figures out whether or not the player (or mho) is dead at a given position
	public boolean isDeadPlayer(int newPosX, int newPosY) {
		dead = false;
		if((boardPos[newPosX-1][newPosY-1]==1)||(boardPos[newPosX-1][newPosY-1]==2)) {
			dead = true;
		}
		return dead;
	}

	public boolean isDeadMho(int newPosX, int newPosY) {
		dead = false;
		for(int col = 0; col<12; col++) {
			for(int row = 0; row<12; row++) {
				if((boardPos[newPosX-1][newPosY-1]==1)) {
					dead = true;
				}
			}
		}
		return dead;
	}

	// This method draws the electric fence
	public void fence(Graphics g, int startX, int startY) {
		g.drawLine(startX, startY, startX, startY+20);

		g.drawLine(startX, startY+5, startX+20, startY+5);
		g.drawLine(startX, startY+10, startX+20, startY+10);
		g.drawLine(startX, startY+15, startX+20, startY+15);

		g.drawLine(startX+20, startY, startX+20, startY+20);
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
	
	public boolean win(Graphics g) {
		for(int i = 0; i<12; i++) {
			if(mhoPos[i][0]!=13) {
				return false;
			}
		}
		win = true;
		g.drawString("YOU WIN", 500, 100);
		g.drawString("Click here to play again:", 460, 120);
		g.fillOval(518, 140, 30, 30);
		return true;
	}

	// This calculates the graphics coordinate based on the grid place
	public int calcCoord(int pos) {
		return 50+(pos-1)*30;
	}

	// NOT FINISHED - intended to move a mho
	public void moveMho(Graphics g) {
		for(int mho = 0; mho<12; mho++) {
			if(mhoPos[mho][0]!=13) {
				int col = mhoPos[mho][0];
				int row = mhoPos[mho][1];
				if(col==counterPos[0]) {
					if(row<counterPos[1]&&boardPos[col][row+1]!=2) {
						if(isDeadMho(col, row+1)) {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							boardPos[col-1][row-1] = 0;
							mhoPos[mho][0] = 13;
						}
						else {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							mho(g, calcCoord(col), calcCoord(row+1));
							boardPos[col-1][row-1] = 0;
							boardPos[col-1][row] = 2;
							mhoPos[mho][1] += 1;
						}
					}
					else if(row>counterPos[1]&&boardPos[col][row-1]!=2) {
						if(isDeadMho(col, row-1)) {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							boardPos[col-1][row-1] = 0;
							mhoPos[mho][0] = 13;
						}
						else {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							mho(g, calcCoord(col), calcCoord(row-1));
							boardPos[col-1][row-1] = 0;
							boardPos[col-1][row-2] = 2;
							mhoPos[mho][1] -= 1;
						}
					}
				}
				else if(row==counterPos[1]) {
					if(col<counterPos[0]&&boardPos[col+1][row]!=2) {
						if(isDeadMho(col+1, row)) {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							boardPos[col-1][row-1] = 0;
							mhoPos[mho][0] = 13;
						}
						else {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							mho(g, calcCoord(col+1), calcCoord(row));
							boardPos[col-1][row-1] = 0;
							boardPos[col][row-1] = 2;
							mhoPos[mho][0] += 1;
						}
					}
					else if(col>counterPos[0]&&boardPos[col-1][row]!=2) {
						if(isDeadMho(col-1, row)) {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							boardPos[col-1][row-1] = 0;
							mhoPos[mho][0] = 13;
						}
						else {
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							mho(g, calcCoord(col-1), calcCoord(row));
							boardPos[col-1][row-1] = 0;
							boardPos[col-2][row-1] = 2;
							mhoPos[mho][0] -= 1;
						}
					}
					else if(col>counterPos[0]&&row>counterPos[1]&&boardPos[col-1][row-1]!=1) {
						g.setColor(Color.BLACK);
						g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
						g.setColor(Color.YELLOW);
						mho(g, calcCoord(col-1), calcCoord(row-1));
						boardPos[col-1][row-1] = 0;
						boardPos[col-2][row-2] = 2;
						mhoPos[mho][0] -= 1;
						mhoPos[mho][1] -= 1;
					}
					else if(col<counterPos[0]&&row>counterPos[1]&&boardPos[col+1][row-1]!=1) {
						g.setColor(Color.BLACK);
						g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
						g.setColor(Color.YELLOW);
						mho(g, calcCoord(col-1), calcCoord(row-1));
						boardPos[col-1][row-1] = 0;
						boardPos[col][row-2] = 2;
						mhoPos[mho][0] += 1;
						mhoPos[mho][1] -= 1;
					}
				}
			}
			if(isDeadPlayer(counterPos[0], counterPos[1])) {
				dead(g);
			}
		}
	}

	public void dead(Graphics g) {
		testHiVolts.end = true;
		g.drawString("GAME OVER", 500, 100);
		g.drawString("Click here to play again:", 460, 120);
		g.fillOval(518, 140, 30, 30);
	}

	public void up(Graphics g) {
		if(isDeadPlayer(counterPos[0], counterPos[1]-1)) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]), calcCoord(counterPos[1]-1));
			counterPos[1] -= 1;
		}
		moveMho(g);
		win(g);
	}

	public void down(Graphics g) {
		if(isDeadPlayer(counterPos[0], counterPos[1]+1)) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]), calcCoord(counterPos[1]+1));
			counterPos[1] += 1;
		}
		moveMho(g);
		win(g);
	}

	public void right(Graphics g) {
		if(isDeadPlayer(counterPos[0]+1, counterPos[1])) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]+1), calcCoord(counterPos[1]));
			counterPos[0] += 1;
		}
		moveMho(g);
		win(g);
	}

	public void left(Graphics g) {
		if(isDeadPlayer(counterPos[0]-1, counterPos[1])) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]-1), calcCoord(counterPos[1]));
			counterPos[0] -= 1;
		}
		moveMho(g);
		win(g);
	}

	public void rightUp(Graphics g) {
		if(isDeadPlayer(counterPos[0]+1, counterPos[1]-1)) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]+1), calcCoord(counterPos[1]-1));
			counterPos[0] += 1;
			counterPos[1] -= 1;
		}
		moveMho(g);
		win(g);
	}

	public void leftUp(Graphics g) {
		if(isDeadPlayer(counterPos[0]-1, counterPos[1]-1)) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]-1), calcCoord(counterPos[1]-1));
			counterPos[0] -= 1;
			counterPos[1] -= 1;
		}
		moveMho(g);
		win(g);
	}

	public void leftDown(Graphics g) {
		if(isDeadPlayer(counterPos[0]-1, counterPos[1]+1)) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]-1), calcCoord(counterPos[1]+1));
			counterPos[0] -= 1;
			counterPos[1] += 1;
		}
		moveMho(g);
		win(g);
	}

	public void rightDown(Graphics g) {
		if(isDeadPlayer(counterPos[0]+1, counterPos[1]+1)) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(counterPos[0]+1), calcCoord(counterPos[1]+1));
			counterPos[0] += 1;
			counterPos[1] += 1;
		}
		moveMho(g);
		win(g);
	}

	public void jump(Graphics g) {
		c = rand.nextInt(11);
		c += 2;
		r = rand.nextInt(11);
		r += 2;
		if(isDeadPlayer(c, r)) {
			dead(g);
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(calcCoord(counterPos[0])-1, calcCoord(counterPos[1])-1, 22, 22);
			g.setColor(Color.RED);
			counter(g, calcCoord(c), calcCoord(r));
			counterPos[0] = c;
			counterPos[1] = r;
		}
		win(g);
	}

	public void sit(Graphics g) {
		moveMho(g);
		win(g);
	}

	// Key listener
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_W) {
			var = 1;
			repaint();
		}
		else if(key==KeyEvent.VK_X) {
			var = 2;
			repaint();
		}
		else if(key==KeyEvent.VK_Q) {
			var = 3;
			repaint();
		}
		else if(key==KeyEvent.VK_E) {
			var = 4;
			repaint();
		}
		else if(key==KeyEvent.VK_A) {
			var = 5;
			repaint();
		}
		else if(key==KeyEvent.VK_S) {
			var = 6;
			repaint();
		}
		else if(key==KeyEvent.VK_D) {
			var = 7;
			repaint();
		}
		else if(key==KeyEvent.VK_Z) {
			var = 8;
			repaint();
		}
		else if(key==KeyEvent.VK_C) {
			var = 9;
			repaint();
		}
		else if(key==KeyEvent.VK_J) {
			var = 10;
			repaint();
		}
	}
	public void keyTyped(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

	public hiVolts() {
		addKeyListener(this);
		addMouseListener(this);
		init();
	}

	public void init() {
		setSize(700,700);
		setBackground(Color.BLACK);
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		if(testHiVolts.end||win) {
			int x = e.getX();
		    int y = e.getY();
		    if((x>=518&&x<=548)||(y>=140&&y<=170)) {
		    	notPlay = false;
		    }
		    
		    for(int i = 1; i<11; i++) {
		    	for(int a = 1; a<11; a++) {
		    		boardPos[i][a] = 0;
		    	}
		    }
		    
		    newGame = true;
		    testHiVolts.end = false;
		    first = 0;
		    repaint();
		}
	}

	public void mousePressed(MouseEvent e) {


	}

	public void mouseReleased(MouseEvent e) {


	}

	public void mouseEntered(MouseEvent e) {


	}

	public void mouseExited(MouseEvent e) {

	}


	// Paint
	public void paint(Graphics g) {
		g.setColor(Color.YELLOW);
		
		if(newGame) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.YELLOW);
		}
		
		if(first==0) {
			drawboard(g);
		}

		if(!testHiVolts.end) {

			if(var==1) {
				up(g);
			}
			else if(var==2) {
				down(g);
			}
			else if(var==3) {
				leftUp(g);
			}
			else if(var==4) {
				rightUp(g);
			}
			else if(var==5) {
				left(g);
			}
			else if(var==6) {
				sit(g);
			}
			else if(var==7) {
				right(g);
			}
			else if(var==8) {
				leftDown(g);
			}
			else if(var==9) {
				rightDown(g);
			}
			else if(var==10) {
				jump(g);
			}
		}

		first = 1;
		newGame = false;
	}
}
