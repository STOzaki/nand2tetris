// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)


	@R2
	M=0 // sets the sum to 0
(LOOP)
	@R0
	M=M-1
	D=M // uses one to add R1
	@END
	D;JLT // if R1 has been added R0 times, then finish, else continue
	@R1
	D=M
	@R2
	M=M+D // adds the sum with the R1 value
	@LOOP
	0;JMP // keeps going until R1 has been added R0 times
(END)
	@END
	0;JMP // infinite loop
