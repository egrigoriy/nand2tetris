// Program: sum1ToN (R0 contains N)
// Computes R1 = 1 + 2 + 3 +...
// Usage: put a value >= 1 in R0
@i
M=1
@sum
M=0
(LOOP)
@i
D=M
@R0
D=D-M
@STOP
D;JGT
@i
D=M
@sum
M=M+D
@i
M=M+1
@LOOP
0;JMP
(STOP)
@sum
D=M
@R1
M=D
(END)
@END
0;JMP
