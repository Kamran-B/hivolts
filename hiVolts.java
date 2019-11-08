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

	//random number generator
	Random rand = new Random();

	//variables used in this program
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
	int greaterX = 1;
	int greaterY = 1;

	//Arrays used to set the size of the board to 12x12
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
					//fence(g, calcCoord(col), calcCoord(row));
				}
			}
		}
		/* For loop is used to position the 20 fences on the interior of the game
		at random spots each time the program is run */
		for(int i = 0; i<20; i++) {
			while(true) {
				generateRandom();
				fencePos[i][0] = c;
				fencePos[i][1] = r;
				if(boardPos[c-1][r-1]==0) {
					boardPos[c-1][r-1] = 1;
					break;
				}
				//function draws all fences on the game

			}fence(g, calcCoord(c), calcCoord(r));
		}
		/* For loop is used to position each of the mhos on the interior
		of the game randomly each time the program is run */
		for(int i = 0; i<12; i++) {
			while(true) {
				generateRandom();
				mhoPos[i][0] = c;
				mhoPos[i][1] = r;
				if(boardPos[c-1][r-1]==0) {
					boardPos[c-1][r-1] = 2;
					break;
				}
			}
			//function draws all mhos of the game
			mho(g, calcCoord(c), calcCoord(r));
		}
		//while loop is used to position the fences on the exterior of the game
		while(true) {
			generateRandom();
			counterPos[0] = c;
			counterPos[1] = r;
			if(boardPos[c-1][r-1]==0) {
				boardPos[c-1][r-1] = 3;
				break;
			}
		}
		counter(g, calcCoord(c), calcCoord(r));

		//places external fences on the board
		for(int i = 30; i<=12*30; i+=30) {
			fence(g, 50, i+20);
			fence(g, 380, i+20);
			fence(g, i+20, 50);
			fence(g, i+20, 380);
		}
	}

	// This figures out whether or not the player is dead at a given position
	public boolean isDeadPlayer(int newPosX, int newPosY) {
		if((boardPos[newPosX-1][newPosY-1]==1)||(boardPos[newPosX-1][newPosY-1]==2)) {
			return true;
		}
		return false;
	}
	//This figures out whether the mho is alive or dead at a given position
	public boolean isDeadMho(int newPosX, int newPosY) {
		if((boardPos[newPosX-1][newPosY-1]==1)) {
			return true;
		}
		return false;
	}

	public void generateRandom() {
		//while(true) {
		c = rand.nextInt(10);
		c += 2;
		r = rand.nextInt(10);
		r += 2;
		//if(boardPos[c-1][r-1]==0) {
		//	boardPos[c-1][r-1] = 2;
		//	break;
		//}
		//}
	}

	/**
	 * This method contains the math to create the graphical items of the boardgame
	 * @param g helps draw the lines, arcs, and ovals necessary to create the fences
	 * @param startX the x coordinate of the start position of the fence
	 * @param startY the y coordinate of the start position of the fence
	 */
	public void fence(Graphics g, int startX, int startY) {
		//sets color of fences to yellow
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
		g.setColor(Color.YELLOW);
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
	/**
	 * This method returns false if the player hasn't won the game
	 * @param g Graphics are to give the option to the player to play again
	 * @return false if the player doesn't win
	 */
	public boolean win(Graphics g) {
		for(int i = 0; i<12; i++) {
			if(mhoPos[i][0]!=13) {
				return false;
			}
		}
		//returns true if the player wins
		win = true;
		g.drawString("YOU WIN", 500, 100);
		//option to play again
		g.drawString("Click here to play again:", 460, 120);
		g.fillOval(518, 140, 30, 30);
		return true;
	}

	// This calculates the graphics coordinate based on the grid place
	public int calcCoord(int pos) {
		return 50+(pos-1)*30;
	}
	/**
	 * This method is used to move each mho every single time a move is made by the player (with the exception of after a jump)
	 * @param g Graphics are used to repaint the colors of the mhos
	 */
	public void moveMho(Graphics g) {
		for(int mho = 0; mho<12; mho++) {
			if(mhoPos[mho][0]!=13) {
				int col = mhoPos[mho][0];
				int row = mhoPos[mho][1];

				if(counterPos[0]-col>0) {
					greaterX = -1;
				}
				if(counterPos[1]-row>0) {
					greaterY = -1;
				}

				if(col==counterPos[0]) {
					if(row<counterPos[1]&&boardPos[col-1][row]!=2) {
						moveMhoUpDown(g, col, row, mho, 0, 1);
					}
					else if(row>counterPos[1]&&boardPos[col-1][row-2]!=2) {
						moveMhoUpDown(g, col, row, mho, 0, -1);
					}
				}
				else if(row==counterPos[1]) {
					if(col<counterPos[0]&&boardPos[col][row-1]!=2) {
						moveMhoUpDown(g, col, row, mho, 1, 0);
					}

					else if(col>counterPos[0]&&boardPos[col-2][row-1]!=2) {
						moveMhoUpDown(g, col, row, mho, -1, 0);
					}
				}

				else if(col>counterPos[0]&&row>counterPos[1]&&!isDeadMho(col-1, row-1)&&boardPos[col-2][row-2]!=2) {
					coverSquare(g, col, row);
					rePaintMho(g, col, row, mho, -1, -1);
				}
				else if(col<counterPos[0]&&row>counterPos[1]&&!isDeadMho(col+1, row-1)&&boardPos[col][row-2]!=2) {
					coverSquare(g, col, row);
					rePaintMho(g, col, row, mho, 1, -1);
				}
				else if(col>counterPos[0]&&row<counterPos[1]&&!isDeadMho(col-1, row+1)&&boardPos[col-2][row]!=2) {
					coverSquare(g, col, row);
					rePaintMho(g, col, row, mho, -1, 1);
				}
				else if(col<counterPos[0]&&row<counterPos[1]&&!isDeadMho(col+1, row+1)&&boardPos[col][row]!=2) {
					coverSquare(g, col, row);
					rePaintMho(g, col, row, mho, 1, 1);
				}

				else if(Math.abs(counterPos[0]-col)>=Math.abs(counterPos[1]-row)&&!isDeadMho(col+greaterX, row)&&boardPos[col-1+greaterX][row-1]!=2) {
					coverSquare(g, col, row);
					rePaintMho(g, col, row, mho, greaterX, 0);
				}
				else if(Math.abs(counterPos[0]-col)<Math.abs(counterPos[1]-row)&&!isDeadMho(col, row+greaterY)&&boardPos[col-1][row-1+greaterY]!=2) {
					coverSquare(g, col, row);
					rePaintMho(g, col, row, mho, 0, greaterY);
				}
				
				else if(!isSurroundedByMhos(col, row)) {
					coverSquare(g, col-1, row-1);
				}
			}
			if(isDeadPlayer(counterPos[0], counterPos[1])) {
				dead(g);
			}
		}
	}
	/**
	 * This is the method that creates the replay option for the user
	 * @param g
	 */
	public void dead(Graphics g) {
		testHiVolts.end = true;
		g.drawString("GAME OVER", 500, 100);
		g.drawString("Click here to play again:", 460, 120);
		g.fillOval(518, 140, 30, 30);
		// Drawing  a black rectangle to cover the your turn
		g.setColor(Color.BLACK);
		g.fillRect(500, 40, 80, 10);
	}

	public void coverSquare(Graphics g, int col, int row) {
		g.setColor(Color.BLACK);
		g.fillRect(calcCoord(col)-1, calcCoord(row)-1, 22, 22);
		g.setColor(Color.YELLOW);
	}

	public void rePaintMho(Graphics g, int col, int row, int mho, int upX, int upY) {
		mho(g, calcCoord(col+upX), calcCoord(row+upY));
		boardPos[col-1][row-1] = 0;
		boardPos[col-1+upX][row-1+upY] = 2;
		mhoPos[mho][0] += upX;
		mhoPos[mho][1] += upY;
	}

	public void moveMhoUpDown(Graphics g, int col, int row, int mho, int x, int y) {
		coverSquare(g, col, row);
		if(isDeadMho(col+x, row+y)) {
			boardPos[col-1][row-1] = 0;
			mhoPos[mho][0] = 13;
		}
		else {
			mho(g, calcCoord(col+x), calcCoord(row+y));
			boardPos[col-1][row-1] = 0;
			boardPos[col-1+x][row-1+y] = 2;
			mhoPos[mho][0] += x;
			mhoPos[mho][1] += y;
		}
	}

	public boolean isSurroundedByMhos(int col, int row) {
		int tempCol = col-1;
		int tempRow = row-1;
		if(boardPos[tempCol-1][tempRow-1]==2) {
			return false;
		}
		else if(boardPos[tempCol][tempRow-1]==2){
			return false;
		}
		else if(boardPos[tempCol+1][tempRow-1]==2){
			return false;
		}
		else if(boardPos[tempCol+1][tempRow]==2){
			return false;
		}
		else if(boardPos[tempCol+1][tempRow+1]==2){
			return false;
		}
		else if(boardPos[tempCol][tempRow+1]==2){
			return false;
		}
		else if(boardPos[tempCol-1][tempRow+1]==2){
			return false;
		}
		else if(boardPos[tempCol-1][tempRow]==2){
			return false;
		}
		return true;
	}

	public void moveCounter(Graphics g, int x, int y) {
		if(isDeadPlayer(counterPos[0]+x, counterPos[1]+y)) {
			dead(g);
		}
		else {
			coverSquare(g, counterPos[0], counterPos[1]);
			counter(g, calcCoord(counterPos[0]+x), calcCoord(counterPos[1]+y));
			counterPos[0] += x;
			counterPos[1] += y;
			moveMho(g);
			win(g);
		}

	}

	/**
	 * This method moves player up one position from its original spot
	 * @param g
	 */
	public void up(Graphics g) {
		moveCounter(g, 0, -1);
	}
	/**
	 * This method moves the player one space downwards from its original spot
	 * @param g
	 */
	public void down(Graphics g) {
		moveCounter(g, 0, 1);
	}
	//method for moving to the right
	public void right(Graphics g) {
		moveCounter(g, 1, 0);
	}
	//method for moving to the left
	public void left(Graphics g) {
		moveCounter(g, -1, 0);
	}
	//method for moving right and upwards
	public void rightUp(Graphics g) {
		moveCounter(g, 1, -1);
	}
	//method for moving left and upwards
	public void leftUp(Graphics g) {
		moveCounter(g, -1, -1);
	}
	//method for moving left and downwards
	public void leftDown(Graphics g) {
		moveCounter(g, -1, 1);
	}
	//method for moving right and downwards
	public void rightDown(Graphics g) {
		moveCounter(g, 1, 1);
	}
	//method for jumping
	public void jump(Graphics g) {
		generateRandom();
		if(isDeadPlayer(c, r)) {
			coverSquare(g, counterPos[0], counterPos[1]);
			counter(g, calcCoord(c), calcCoord(r));
			g.setColor(Color.YELLOW);
			dead(g);
		}
		else {
			coverSquare(g, counterPos[0], counterPos[1]);
			counter(g, calcCoord(c), calcCoord(r));
			counterPos[0] = c;
			counterPos[1] = r;
			win(g);
		}
	}
	//method for staying at same location
	public void sit(Graphics g) {
		moveMho(g);
		win(g);
	}

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
	//constructor
	public hiVolts() {
		addKeyListener(this);
		addMouseListener(this);
		init();
	}
	//setting background color and size of the applet and repaint each time it runs 
	public void init() {
		setSize(700,750);
		setBackground(Color.BLACK);
		repaint();
	}

	//This method gets the position of the mouse click when the replay option appears
	public void mouseClicked(MouseEvent e) {
		if(testHiVolts.end||win) {
			int x = e.getX();
			int y = e.getY();
			if((x>=518&&x<=548)||(y>=140&&y<=170)) {
				notPlay = false;


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
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void instructions(Graphics g) {
		//Instruction List
		g.drawString("Instructions:", 47, 450);
		g.drawString("Welcome to the HiVolts Game!", 62, 475);
		g.drawString("The goal of the game is to try to move as many", 62, 505);
		g.drawString("times as possible without getting hit by either", 62, 530);
		g.drawString("an electric fence or a mho (yellow frowny faces).", 62, 555);
		g.drawString("and attempt to get rid of as many mhos as possible.", 62, 580);
		g.drawString("To win, you must get rid of all mhos on the map.", 62, 605);
		g.drawString("To remove a mho, is must run into an electric fence", 62, 630);
		g.drawString("There are a few things to note regarding the keys:", 62, 655);
		g.drawString("1. J Key will move you to a random spot on the map", 67, 680);
		g.drawString("2. S Key will keep you at the same spot", 67, 705);
		g.drawString("NOW THAT YOU UNDERSTAND THE RULES, GO & PLAY!", 47, 730);
		//Key Legend
		g.drawString("Key Legend:", 500, 480);
		g.drawLine(400, 700, 400, 450);
		g.drawLine(700, 450, 400, 450);
		g.drawString("W = UP", 425, 530);
		g.drawString("A = Left", 565, 530);
		g.drawString("D = Right", 565, 560);
		g.drawString("S = Sit", 425, 560);
		g.drawString("Q = Up & Left", 425, 590);
		g.drawString("E = Up & Right", 565, 590);
		g.drawString("Z = Down & Left", 425, 620);
		g.drawString("C = Down & Right", 565, 620);
		g.drawString("X = Down", 425, 650);
		g.drawString("J = Jump", 565, 650);
	}

	//paint method creates a new game depending on the user
	public void paint(Graphics g) {
		g.setColor(Color.YELLOW);

		if(newGame) {
			g.clearRect(0, 0, 700, 750);
			drawboard(g);
			g.drawString("YOUR TURN", 500, 50);
			instructions(g);
		}

		//testing all the controls and keys in the game
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

		var = 0;

		first = 1;
		newGame = false;
	}
}
