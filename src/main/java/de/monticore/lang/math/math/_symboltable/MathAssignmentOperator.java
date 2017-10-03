package de.monticore.lang.math.math._symboltable;

import de.monticore.lang.math.math._ast.ASTMathAssignmentOperator;

/**
 * @author Sascha Schneiders
 */
public class MathAssignmentOperator {
    protected String operator;

    public MathAssignmentOperator() {

    }

    public MathAssignmentOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public static MathAssignmentOperator convert(ASTMathAssignmentOperator astMathAssignmentOperator) {
        return new MathAssignmentOperator(astMathAssignmentOperator.getOperator().get());
    }
}
