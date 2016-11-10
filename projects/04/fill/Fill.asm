// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.


(LOOP)
	@SCREEN //starting from the top left corner of the screen
	D=A
	@spot
	M=D-1 // decreases the spot by one to compensate for NEXT's immidiate increaement.
	@NEXT
	0;JMP
(NEXT)
	@spot
	MD=M+1 // moves the spot by one and stores it in D as well
	@24576
	D=A-D // takes the lower right corner - spot
	@LOOP
	D;JLE // if we have reached the end, then start from the top left corner again
	@KBD
	D=M // grabs the value of the keyboard
	@BLACK
	D;JNE // if the keyboard is pressed, then turn the color black
	@WHITE
	D;JEQ // else turn the color black
(BLACK)
	@spot // goes to the spot that needs to be colored
	A=M
	M=-1 // colors the spot black
	@NEXT
	0;JMP // moves back to the next spot
(WHITE)
	@spot // goes to the spot that needs to be colored
	A=M
	M=0 // colors the spot white
	@NEXT
	0;JMP // moves back to the next spot
