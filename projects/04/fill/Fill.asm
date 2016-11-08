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

@24576
D=A
@track
M=D
(LOOP)
@24576
D=M
@BLACK
D;JGE
@WHITE
D;JEQ


(BLACK)
@SCREEN
@spot
D=M
A=D
M=-1
D=D+1
@spot
M=D
@track
D=D-M
@LOOP
D;JGE
@BLACK
0;JMP


(WHITE)
@SCREEN
@spot
D=M
A=D
M=0
D=D+1
@spot
M=D
@track
D=D-M
@LOOP
D;JGE
@WHITE
0;JMP
