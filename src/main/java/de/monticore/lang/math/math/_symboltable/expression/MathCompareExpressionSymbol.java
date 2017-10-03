package de.monticore.lang.math.math._symboltable.expression;

/**
 * @author Sascha Schneiders
 */
public class MathCompareExpressionSymbol extends MathExpressionSymbol {
    protected String compareOperator;
    protected MathExpressionSymbol leftExpression;
    protected MathExpressionSymbol rightExpression;

    public MathCompareExpressionSymbol() {

    }

    public String getCompareOperator() {
        return compareOperator;
    }

    public void setCompareOperator(String compareOperator) {
        this.compareOperator = compareOperator;
    }

    public MathExpressionSymbol getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression(MathExpressionSymbol leftExpression) {
        this.leftExpression = leftExpression;
    }

    public MathExpressionSymbol getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression(MathExpressionSymbol rightExpression) {
        this.rightExpression = rightExpression;
    }

    @Override
    public String getTextualRepresentation() {
        return leftExpression + compareOperator + rightExpression;
    }

    @Override
    public boolean isCompareExpression() {
        return true;
    }
}
