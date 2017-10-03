package de.monticore.lang.math.math._symboltable.expression;

/**
 * @author Sascha Schneiders
 */
public abstract class MathValueExpressionSymbol extends MathExpressionSymbol {

    public MathValueExpressionSymbol() {
        super();
    }

    public MathValueExpressionSymbol(String name) {
        super(name);
    }

    @Override
    public boolean isValueExpression() {
        return true;
    }

    public boolean isNameExpression() {
        return false;
    }

    public boolean isNumberExpression() {
        return false;
    }
}
