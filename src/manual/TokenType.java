package manual;

/**
 * Created by Yixin on 2017/5/20.
 */
public enum TokenType {
    OP_ADD(0),// +
    OP_SUB(1),// -
    OP_MUL(2),// *
    OP_DIV(3),// /
    L_BRACE(4),// (
    R_BRACE(5),// )
    END(6),// #
    NUMBER(7);

    private TokenType(int val) {
        this.val = val;
    }

    private int val;

    private int getVal() {
        return val;
    }

    // 算符优先级关系
    // 0代表相等,1代表大于,-1代表小于
    private static final int[][] opPriorityTable = {
            // + | - | * | / | ( | ) | #
            {0, 0, -1, -1, -1, -1, -1}, // +
            {0, 0, -1, -1, -1, -1, -1}, // -
            {1, 1, 0, 0, -1, -1, -1},   // *
            {1, 1, 0, 0, -1, -1, -1},   // /
            {1, 1, 1, 1, 0, 0, -1},     // (
            {1, 1, 1, 1, 0, 0, -1},     // )
            {1, 1, 1, 1, 1, 1, 0}       // #
    };

    /**
     * 比较两个运算符token的优先级
     */
    public static int compareOp(MyToken o1, MyToken o2) {
        return opPriorityTable[o1.getType().getVal()][o2.getType().getVal()];
    }
}
