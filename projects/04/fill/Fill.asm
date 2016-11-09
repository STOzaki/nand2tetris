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
@SCREEN
D=A
@spot
M=D-1 // decreases the spot by one to compensate for next's immidiate increaement.
@NEXT
0;JMP


(BLACK)
@spot //finds the spot tha
A=M
M=-1
@NEXT
0;JMP

(WHITE)
@spot
A=M
M=0
@NEXT
0;JMP

(NEXT)
@spot
M=M+1
D=M
@24576
D=A-D
@LOOP
D;JLE
@KBD
D=M
@BLACK //Determine whether the keyboard is pressed
D;JNE
@WHITE //Determine whether it is black
D;JEQ
