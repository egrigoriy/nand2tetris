// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

(LOOP_KBD)
	// set default color to white
	@color
	M=0
	// if no key touch just fill with default color
	@KBD
	D=M
	@FILL
	D; JEQ
	// else change color to black
	@color
	M=-1
	// fill the screen with the color set abouve
(FILL)
	// set addr = SCREEN
	@SCREEN
	D=A
	@addr
	M=D
	(LOOP_FILL)
		// set RAM[addr] = color
		@color
		D=M
		@addr
		A=M
		M=D
		// set addr++
		@addr
		MD=M+1
		// if addr < KBD then goto LOOP_FILL
		@KBD
		D=D-A
		@LOOP_FILL
		D; JLT
	// else loop back to key listening
	@LOOP_KBD
	0; JMP