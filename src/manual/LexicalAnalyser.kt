package manual

/**
 * Created by 35894 on 2017/5/18.
 */
class LexicalAnalyser {

    private fun isOperator(c: Char): Boolean {
        return c == '+'
                || c == '-'
                || c == '*'
                || c == '/'
                || c == '('
                || c == ')'
    }

    private fun isSpace(c: Char): Boolean {
        return c == ' '
                || c == '\t'
                || c == '\r'
                || c == '\n'
    }

    private fun isNumber(c: Char): Boolean {
        return c in '0'..'9'
    }


    fun analyse(expr: String, tokens: MutableList<MyToken>): Boolean {
        var i = 0
        while (i < expr.length) {
            // 加减乘除符号
            if (isOperator(expr[i])) {
                val tmp = StringBuilder()
                tmp.append(expr[i])

                when (expr[i]) {
                    '+' -> tokens.add(MyToken(tmp.toString(), TokenType.OP_ADD))
                    '-' -> tokens.add(MyToken(tmp.toString(), TokenType.OP_SUB))
                    '*' -> tokens.add(MyToken(tmp.toString(), TokenType.OP_MUL))
                    '/' -> tokens.add(MyToken(tmp.toString(), TokenType.OP_DIV))
                    ')' -> tokens.add(MyToken(tmp.toString(), TokenType.R_BRACE))
                    '(' -> tokens.add(MyToken(tmp.toString(), TokenType.L_BRACE))
                }
            } else if (isNumber(expr[i])) {
                val tmp = StringBuilder()

                //读取接下来的所有数字
                while (isNumber(expr[i])) {
                    tmp.append(expr[i])
                    i++
                }

                //遇到小数点
                if (expr[i] == '.') {
                    i++
                    //小数点后边是数字
                    if (isNumber(expr[i])) {
                        tmp.append('.')
                        //读取所有数字
                        while (isNumber(expr[i])) {
                            tmp.append(expr[i])
                            i++
                        }
                    } else {
                        return false//小数点后边不是数字，词法分析错误
                    }
                }
                tokens.add(MyToken(tmp.toString(), TokenType.NUMBER))
                //由于多读了一位判断结束，因此需要减一
                i--
            } else if (isSpace(expr[i])) {
            } else {
                return false
            }//如果以小数点或者其他字符开头
            //跳过空白符
            // 数字开头
            i++

        }

        tokens.add(MyToken("#", TokenType.END))
        return true
    }
}
