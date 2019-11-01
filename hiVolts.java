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
import javax.swing.JButton;

//KeyListener 
public class hiVolts extends JFrame implements KeyListener, MouseListener {

	//instance creates new random number generator 
	Random rand = new Random();
	
	//declared variables used in this program
	int c;
	int r;
	int inputKey;
	boolean dead;
	boolean notPlay = false;
	int var;
	int first;
	boolean pTurn = true;
	
	//Arrays used to set the size of the board to 12 x 12
	int[][] boardPos = new int[12][12];
	int[] counterPos = new int[2];
	//Arrays used to store the position of the fence and the mhos
	int[][] mhoPos = new int[12][2];
	int[][] fencePos = new int[20][2];

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
		
		/* For loop is used to position the 20 fences on the interior of the game
		at random spots each time the program is run */
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
			//function draws all fences on the game
			fence(g, calcCoord(c), calcCoord(r));
		}
		/* For loop is used to position each of the mhos on the interior
		of the game randomly each time the program is run */
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
			//function draws all mhos of the game
			mho(g, calcCoord(c), calcCoord(r));
		}
		//while loop is used to position the fences on the exterior of the game
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
		//places external fences
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
	/**
	 * This method contains the math to create the graphical items of the boardgame
	 * @param g helps draw the lines, arcs, and ovals necessary to create the fences
	 * @param startX the x coordinate of the start position of the fence
	 * @param startY the y coordinate of the start position of the fence
	 */
	public void fence(Graphics g, int startX, int startY) {
		g.setColor(Color.YELLOW);
		int nextX = startX;
		int nextY = startY;
		//left side of fence
		g.drawLine(startX, startY, startX, startY+20);
		//top barbs of fence
		g.drawLine(startX+5, startY+3, startX+5, startY+4);
		g.drawLine(startX+15, startY+3, startX+15, startY+4);
		//middle barbs of fence
		g.drawLine(startX+5, startY+11, startX+5, startY+12);
		g.drawLine(startX+10, startY+8, startX+10, startY+9);
		g.drawLine(startX+15, startY+11, startX+15, startY+12);
		//bottom barbs of fence
		g.drawLine(startX+5, startY+16, startX+5, startY+17);
		g.drawLine(startX+15, startY+16, startX+15, startY+17);
		//3 middle wires of fence
		g.drawLine(startX, startY+5, startX+20, startY+5);
		g.drawLine(startX, startY+10, startX+20, startY+10);
		g.drawLine(startX, startY+15, startX+20, startY+15);
		//right side of fence
		g.drawLine(startX+20, startY, startX+20, startY+20);
	}
	//Graphics to draw the mhos of the game
	public void mho(Graphics g, int startX, int startY) {
		//face
		g.drawOval(startX, startY, 20, 20);
		//eyes
		g.fillOval(startX+5, startY+4, 3, 3);
		g.fillOval(startX+13, startY+4, 3, 3);
		//frown
		g.drawArc(startX+6, startY+13, 8, 6, 0, 180);
	}
	//Graphics draws the player icon of the game
	public void counter(Graphics g, int startX, int startY) {
		g.setColor(Color.RED);
		//face
		g.drawOval(startX, startY, 20, 20);
		//eyes
		g.fillOval(startX+5, startY+4, 3, 3);
		g.fillOval(startX+13, startY+4, 3, 3);
		//smile
		g.drawArc(startX+6, startY+10, 8, 6, 180, 180);
	}
	// This calculates the graphics coordinate based on the grid place
	public int calcCoord(int pos) {
		return 50+(pos-1)*30;
	}
	/**
	 * This method is used to move each mho every single time a move is made by the player
	 * @param g Graphics are used to repaint the colors of the mhos
	 */
	public void moveMho(Graphics g) {
		for(int mho = 0; mho<12; mho++) {
			if(mhoPos[mho][0]!=13) {
				int col = mhoPos[mho][0];
				int row = mhoPos[mho][1];
				System.out.println("1");
				if(col==counterPos[0]) {
					System.out.println("2");
					if(row<counterPos[1]) {
						System.out.println("3");
						boardPos[col][row] = 0;
						if(isDeadMho(col, row+1)) {
							System.out.println("4");
							//paints black color over mho to get rid of each one visually
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							//repaints the mho into a different position on the board
							g.setColor(Color.YELLOW);
							boardPos[col][row] = 0;
							mhoPos[mho][0] = col;
							mhoPos[mho][1] = row;
						}
						else {
							System.out.println("5");
							boardPos[col][row+1] = 2;
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							mho(g, calcCoord(col), calcCoord(row+1));
							boardPos[col][row] = 0;
							boardPos[col][row-1] = 2;
							mhoPos[mho][0] = 13;
						}
						if(isDeadPlayer(counterPos[0], counterPos[1])) {
							dead(g);
						}
					}
					else if(row>counterPos[1]) {
						System.out.println("6");
						boardPos[col][row] = 0;
						if(isDeadMho(col, row-1)) {
							System.out.println("7");
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							boardPos[col][row] = 0;

						}
						else {
							System.out.println("8");
							boardPos[col][row-1] = 2;
							g.setColor(Color.BLACK);
							g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
							g.setColor(Color.YELLOW);
							mho(g, calcCoord(col), calcCoord(row-1));
							boardPos[col][row] = 0;
							boardPos[col][row-1] = 2;
							mhoPos[mho][0] = 13;
						}
						if(isDeadPlayer(counterPos[0], counterPos[1])) {
							dead(g);
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
	public void dead(Graphics g) {
		testHiVolts.end = true;
		g.drawString("GAME OVER", 500, 100);
		g.drawString("Click here to play again:", 460, 120);
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
		moveMho(g);
	}

	public void sit(Graphics g) {
		moveMho(g);
	}

	// NOT FINISHED - key listener
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
		//System.out.println("hi");
		 int x=e.getX();
		    int y=e.getY();
		    System.out.println(x+","+y);

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
		if(first==0) {
			drawboard(g);
		}

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

		first = 1;

		if(testHiVolts.end&&notPlay) {
			first = 0;
		}
	}
}
