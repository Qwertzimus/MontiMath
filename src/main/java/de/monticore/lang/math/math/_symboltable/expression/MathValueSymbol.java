package de.monticore.lang.math.math._symboltable.expression;

/**
 * Symbol represents a MathValueSymbol which consists of a type and a mathexpression that determines its value.
 *
 * @author Sascha Schneiders
 */
public class MathValueSymbol extends MathValueExpressionSymbol {

    protected MathValueType type;
    protected MathExpressionSymbol value;

    public MathValueSymbol(String name) {
        super(name);
    }

    public MathValueType getType() {
        return type;
    }

    public void setType(MathValueType type) {
        this.type = type;
    }

    public MathExpressionSymbol getValue() {
        return value;
    }

    public void setValue(MathExpressionSymbol value) {
        this.value = value;
    }

    @Override
    public String getTextualRepresentation() {
        String result = "";
        if (type != null)
            result += type.getTextualRepresentation() + " ";

        result += getFullName();

        if (value != null)
            result += " = " + getValue().getTextualRepresentation();
        return result;
    }

    public boolean isMatrixValueSymbol() {
        return false;
    }

    @Override
    public boolean isAssignmentDeclarationExpression() {
        return true;
    }


    public MathExpressionSymbol getAssignedMathExpressionSymbol() {
        if (value != null)
            return value;
        return this;
    }
}
