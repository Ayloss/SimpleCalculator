# SimpleCalculator 
利用编译原理思想制作的一个简单的计算器。正好最近在学kotlin就直接用kotlin写了。
做了两套，一套纯手写。另一套用了javacc。

# 文法

- 该文法中,如果负号或正号不在子表达式的首位，不能通过验证。即 2+-3 之类的式子会报错。
- 如果需要使用正负号，需要用括号括起来。
```
Expression:=[+|-]Term[(+|-)Term]
Term:=Factor[(*|/)Factor]
Factor:=Number|(Expression)
```