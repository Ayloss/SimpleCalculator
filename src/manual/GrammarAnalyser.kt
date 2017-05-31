package manual

/**
 * Created by 35894 on 2017/5/19.
 */
class GrammarAnalyser(private var tokens: List<MyToken>) {

    // Expression:=[+|-]Term[(+|-)Term]
    // Term:=Factor[(*|/)Factor]
    // Factor:=Number|(Expression)

    // 该文法中,如果负号或正号不在子表达式的首位，不能通过验证。即 2+-3 之类的式子会报错。
    // 如果需要使用正负号，需要用括号括起来。

    private var index: Int = 0
    private var isOK = true
    
    fun analyse(): Boolean {
        isOK = true
        index = 0
        expression()

        // 不符合文法,导致只能解析一部分token,文法错误
        if(tokens[index].type != TokenType.END) {
            isOK = false
        }

        return isOK
    }


    private fun expression(){

        if (tokens[index].type == TokenType.OP_ADD
                || tokens[index].type == TokenType.OP_SUB) {
            index++
            term()
        } else {
            term()
        }

        while (tokens[index].type == TokenType.OP_ADD
                || tokens[index].type == TokenType.OP_SUB) {
            index++
            term()
        }
    }

    private fun term() {
        factor()

        while (tokens[index].type == TokenType.OP_MUL
                || tokens[index].type == TokenType.OP_DIV) {
            index++
            factor()
        }
        return
    }

    private fun factor() {
        if (tokens[index].type == TokenType.NUMBER) {
            index++
        } else if (tokens[index].type == TokenType.L_BRACE) {
            index++
            expression()
            if(tokens[index].type == TokenType.R_BRACE) {
                index++
                return
            }
            isOK = false
            System.err.println("Syntax Error")
        } else {
            isOK = false
            System.err.println("Syntax Error")
        }
        return
    }
}
