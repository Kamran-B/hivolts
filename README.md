Read Me: HiVolts Project

Aidan Lee and Kamran Bastani

a) Introduction
	
  The HiVolts class contains 
	
b) How this fulfills and doesn't fulfill requirements

Requirements:
  
1. Recreate the HiVolts Game that was created on the PLATO system (Programmed Logic for Automated Teaching Operations)
  	
Both our HiVolts class and testHiVolts class fullfill the requirement to create the game because we pieced together every aspect of the game, including logical, graphical, and mechanical components, to produce the game. Our game properly works and follows the all the rules and circumstances that were listed in the debrief of the project. All aspects of the game work including the moving mho icons, the moving player icon, the logic behind the placing of everything on the board, the visuals of the game, and other miscellaneous things that relate to the construction of the project.
  
c) Explanation of Current Errors
	
As of now, we don't have any major errors that impact the game greatly, though we have one minor error that should be taken into consideration. Our error that we cannot fix is relating to the movement of mhos each time the player moves. When Mr. Kuszmaul played our game, 
	
	
	
d) Overview of Code Explaining the Structure of It

The code starts out with all the necessary imports for this project to be completed. One of the major imports that effected the game a lot was the javax.swing.JFrame import because ultimately, this import was the foundation for the applet to appear which contained all the other parts of the game. After that, all the variables we declared and arrays we created which are listed under the imports which will be used later on in the code. The first method that appears in this program is the drawBoard method, which basically draws the dimensions of the game. Next, a few conditionals are in place to randomly place electrical fences on both the exterior and interior of the game. Following that are the two methods isDeadPlayer and isDeadMho. These two methods are important because they figure out whether both a mho and a player are alive or dead at a certain position of the board. After that are the main graphical methods which create the visuals of the game. The fence, mho, and counter methods hold the functions which draw these features. After these graphical methods are the methods for the mechanics of the game. They include the conditions for either winning and losing, the movement of each mho (which took us a long time to code), and a few more graphical methods after that which visualize the losing and winning of each game. Following those methods are the Key Listener, Mouse Listener, and the movement methods for the player icon. These were quite simple because they all had pretty much the same functions but with minor tweaks to each method. Lastly are the instructions and key legend of the game.
	
e) Major Challenges
  
  Aidan's Challenge: One of the major challenges I encountered when writing up the code was the paint method since I did the graphics section of the game. When coming upon this requirement, I did not know a way to erase the board properly and I thought that the repaint() function would help clear the screen when the user lost the game and wanted to replay it. After a day or two, I finally figured out that I could use the clearRect function or fillRect function to block the whole screen with a black rectangle to cover the previous game up. I ended up deciding to use the fillRect function because I had never used that function before and wanted to try something new. Fortunately, I was able to learn something new from using this function and was also able to complete the task.
  
  Kamran's Challenge: One of the major challenges I encountered when writing the code was definitely the moveMho method since I worked on mostly the mechanics and logic of the game. When I first wrote the code, it took me a long time to think of the logic for how to move each of the mhos. After figuring out the logic, I was able to program each situation that each mho could possibly experience. From this, I realized that this was an extreme amount of code, almost 200 lines of code. I decided to generalize and use abstraction to shorten and simplify the code in a way to make the code shorter. After the use of both generalization and abstraction, I was able to code the moveMho code to around 100 lines. This was great for our code because it shortened our code a lot and made it more simple for a beginner coder to read.
  
  
f) Acknowledgments

As we finished the project, we would both like to thank both Mr. Kuszmaul and each other for being able to work together to complete such an intricate game. Mr. Kuszmaul gave us feedback that we could learn from and use in future programming and we would like to definitely thank him for that. Hopefully in the future, we'll be able to do more projects like this because both of us extremely enjoyed working together.


	
