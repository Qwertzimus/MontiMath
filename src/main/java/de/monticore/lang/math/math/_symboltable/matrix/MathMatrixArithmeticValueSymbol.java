package de.monticore.lang.math.math._symboltable.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sascha Schneiders
 */
public class MathMatrixArithmeticValueSymbol extends MathMatrixExpressionSymbol {

    protected List<MathMatrixAccessOperatorSymbol> vectors = new ArrayList<>();

    public MathMatrixArithmeticValueSymbol() {

    }

    public void addMathMatrixAccessSymbol(MathMatrixAccessOperatorSymbol vector) {
        vectors.add(vector);
    }

    @Override
    public String getTextualRepresentation() {
        String result = "[";
        int counter = 0;
        for (MathMatrixAccessOperatorSymbol symbol : vectors) {
            result += symbol.getTextualRepresentation();
            ++counter;
            if (vectors.size() > counter)
                result += ";";
        }
        result += "]";
        return result;
    }

    @Override
    public boolean isValueExpression() {
        return true;
    }


}
