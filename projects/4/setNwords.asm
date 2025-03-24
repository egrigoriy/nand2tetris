// Program: setNwords
// sets the first n words to -1 starting at base address
// usage: set R0 to base address and set R1 to n
@i
M=1
(LOOP)
@i
D=M
// if i>=R1 goto END
@R1
D=D-M
@END
D;JGT
// R0+i
@i
D=M
@R0
A=D+M
// RAM[A]=-1
M=-1
@i
M=M+1
@LOOP
0;JMP
(END)
@END
0;JMP