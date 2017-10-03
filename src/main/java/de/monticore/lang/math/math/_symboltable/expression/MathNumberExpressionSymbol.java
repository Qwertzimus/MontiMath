package de.monticore.lang.math.math._symboltable.expression;

import de.monticore.lang.math.math._symboltable.JSValue;
import org.jscience.mathematics.number.Rational;

/**
 * @author Sascha Schneiders
 */
public class MathNumberExpressionSymbol extends MathValueExpressionSymbol {
    JSValue value;

    public MathNumberExpressionSymbol() {
        super();
    }

    public MathNumberExpressionSymbol(Rational number) {
        super();
        value = new JSValue(number);
    }

    public void setValue(JSValue value) {
        this.value = value;
    }

    public JSValue getValue() {
        return value;
    }

    @Override
    public String getTextualRepresentation() {
        String result = "";
        if (value.getImagNumber().isPresent())
            return value.toString();
        result += value.getRealNumber().getDividend().toString();
        if (!value.getRealNumber().getDivisor().equals(1)) {
            result += "/" + value.getRealNumber().getDivisor();
        }
        return result;
    }

    @Override
    public boolean isNumberExpression() {
        return true;
    }
}
