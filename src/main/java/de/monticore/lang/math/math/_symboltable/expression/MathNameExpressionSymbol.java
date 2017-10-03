package de.monticore.lang.math.math._symboltable.expression;

/**
 * @author Sascha Schneiders
 */
public class MathNameExpressionSymbol extends MathValueExpressionSymbol {

    protected String nameToResolveValue;

    public MathNameExpressionSymbol() {
        super();
    }

    public MathNameExpressionSymbol(String nameToResolveValue) {
        super();
        this.nameToResolveValue = nameToResolveValue;
    }

    public String getNameToResolveValue() {
        return nameToResolveValue;
    }

    public void setNameToResolveValue(String nameToResolveValue) {
        this.nameToResolveValue = nameToResolveValue;
    }

    @Override
    public String getTextualRepresentation() {
        return getNameToResolveValue();
    }

    public boolean isDottedName() {
        return nameToResolveValue.contains(".");
    }

    @Override
    public boolean isNameExpression() {
        return true;
    }
}
