// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

//Clear output
@R2
M=0 

//check if register 0 is empty
@R0
D=M
@HALT
D;JEQ

//check if register 1 is empty
@R1
D=M
@HALT
D;JEQ

//multiply by addition
(MULT)
	@R0
	D=M
	@HALT
	D;JEQ
	
	@R1
	D=M
	@R2
	M=D+M
	
	@R0
	M=M-1
	
	@MULT
	0;JMP
(HALT)
@HALT
0;JMP