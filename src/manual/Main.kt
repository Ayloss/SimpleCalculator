package manual

import java.util.ArrayList
import java.util.Scanner

/**
 * Created by 35894 on 2017/5/18.
 */
object Main {

    @JvmStatic fun main(args: Array<String>) {
        var lexicalAnalyser = LexicalAnalyser()

        var scanner = Scanner(System.`in`)

        var tokens = ArrayList<MyToken>()

        var expr = scanner.nextLine()

        //java的String结束符不让访问,因此加一个空白符防止数组越界
        expr += " "

        if (!lexicalAnalyser.analyse(expr, tokens)) {
            System.err.println("Lex Error!")
            return
        }

        var grammarAnalyser = GrammarAnalyser(tokens)

        if(!grammarAnalyser.analyse()) {
            return
        }

        val semanticAnalyser = SemanticAnalyser()

        var result = semanticAnalyser.analyse(tokens)

        println(result)

    }
}
