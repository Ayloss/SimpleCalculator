package manual

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by Yixin on 2017/5/20.
 */
class SemanticAnalyser {

    /**
     * 解析算式
     */
    fun analyse(tokens: java.util.ArrayList<MyToken>): java.math.BigDecimal {
        //如果减号为单目运算符，即负号，在其前边添加0
        addZero(tokens)
//        tokens.forEach { print(it.str) }
//        println()
        //求值
        return calculate(tokens)
    }

    /**
     * 求值
     */
    private fun calculate(tokens: List<MyToken>): java.math.BigDecimal {

        return calPostfix(toPostfix(tokens))
    }

    /**
     * 对后缀表达式求值
     */
    private fun calPostfix(tokens: List<MyToken>): java.math.BigDecimal {
        var values = java.util.LinkedList<BigDecimal>()
        tokens.forEach { e ->
            if (e.type == TokenType.NUMBER) {
                values.push(java.math.BigDecimal(e.str))
            } else {
                var opr2 = values.pop()
                var opr1 = values.pop()
                var outCome: java.math.BigDecimal = java.math.BigDecimal.ZERO

                when (e.type) {
                    TokenType.OP_ADD -> outCome = opr1.add(opr2)
                    TokenType.OP_SUB -> outCome = opr1.subtract(opr2)
                    TokenType.OP_MUL -> outCome = opr1.multiply(opr2)
                    TokenType.OP_DIV -> outCome = opr1.divide(opr2,16,RoundingMode.HALF_DOWN)
                }

                values.push(outCome)
            }
        }

        return values.last
    }

    /**
     * 中缀表达式转后缀表达式
     */
    private fun toPostfix(tokens: List<MyToken>): java.util.LinkedList<MyToken> {
        var opStack = java.util.LinkedList<MyToken>()
        var postfix = java.util.LinkedList<MyToken>()

        // 使用一个栈opStack存储运算符
        // 另一个List postfix存储后缀表达式
        /*
            先依次读取算式中的token,如果为数字,直接存入E,如果不是数字,则有以下几种情况
            1.如果是左括号,直接进栈opStack
            2.如果是右括号,开始出栈到postfix中,直到遇到左括号
            3.如果是一般的运算符e,先和栈顶运算符比较优先级.如果e的优先级比较小或者相等,一直出栈到postfix,直到opStack栈空或者遇到优先级比较大的运算符,最后再把该运算符进opStack栈。
            4.如果遇到结束符，把栈中剩余符号出栈到postfix
         */
        tokens.forEach { e ->
            if (e.type == TokenType.NUMBER) {
                postfix.add(e)
            } else if (e.type == TokenType.L_BRACE) {
                opStack.push(e)
            } else if (e.type == TokenType.R_BRACE) {
                while (!opStack.isEmpty() && opStack.first.type != TokenType.L_BRACE) {
                    postfix.add(opStack.pop())
                }
                //左括号出栈
                opStack.pop()
            } else if (e.type == TokenType.END) {
                while (!opStack.isEmpty()) {
                    postfix.add(opStack.pop())
                }
            } else {
                // 左括号只有遇到右括号才会出栈。因此运算符比较优先级应当排除括号
                while (!opStack.isEmpty()
                        && opStack.first.type != TokenType.L_BRACE
                        && opStack.first.type != TokenType.R_BRACE
                        && TokenType.compareOp(e, opStack.first) <= 0) {
                    postfix.add(opStack.pop())
                }
                opStack.push(e)
            }

        }

//        postfix.forEach { print(it.str) }
//        println()
        return postfix
    }

    /**
     * 负号前加0
     */
    fun addZero(tokens: java.util.ArrayList<MyToken>) {

        // 记录插入0的位置
        var zeroAddPos = java.util.ArrayList<Int>()

        var i = 0

        //判断开头是否有正负号
        if (tokens[i].type == TokenType.OP_ADD || tokens[i].type == TokenType.OP_SUB) {
            zeroAddPos.add(0)
            i++
        }

        //遍历获取所有添加正负号的位置
        while (i < tokens.size - 1) {
            if (tokens[i].type == TokenType.L_BRACE
                    && (tokens[i + 1].type == TokenType.OP_ADD || tokens[i + 1].type == TokenType.OP_SUB)) {
                zeroAddPos.add(i + 1)
            }
            i++
        }

        for ((count, j) in zeroAddPos.withIndex()) {
            tokens.add(j + count, MyToken("0", TokenType.NUMBER))
        }

    }

}
