package de.monticore.lang.math.math._symboltable.matrix;

import de.monticore.lang.math.math._ast.ASTMathMatrixNameExpression;

/**
 * @author Sascha Schneiders
 */
public class MathMatrixNameExpressionSymbol extends MathMatrixExpressionSymbol {

    protected ASTMathMatrixNameExpression astMathMatrixNameExpression;
    protected String nameToAccess;

    public MathMatrixNameExpressionSymbol(String nameToAccess) {
        super();
        this.nameToAccess = nameToAccess;
    }

    public ASTMathMatrixNameExpression getAstMathMatrixNameExpression() {
        return astMathMatrixNameExpression;
    }

    public void setAstMathMatrixNameExpression(ASTMathMatrixNameExpression astMathMatrixNameExpression) {
        this.astMathMatrixNameExpression = astMathMatrixNameExpression;
    }

    public String getNameToAccess() {
        return nameToAccess;
    }

    public void setNameToAccess(String nameToAccess) {
        this.nameToAccess = nameToAccess;
    }

    public boolean hasEndOperator() {
        return astMathMatrixNameExpression.getEndOperator().isPresent();
    }

    public boolean hasMatrixAccessExpression() {
        return astMathMatrixNameExpression.getMathMatrixAccessExpression().isPresent();
    }

    public MathMatrixAccessOperatorSymbol getMathMatrixAccessOperatorSymbol() {
        return (MathMatrixAccessOperatorSymbol) astMathMatrixNameExpression.getMathMatrixAccessExpression().get().getSymbol().get();
    }

    public void setMathMatrixAccessOperatorSymbol(MathMatrixAccessOperatorSymbol mathMatrixAccessOperatorSymbol) {
        astMathMatrixNameExpression.getMathMatrixAccessExpression().get().setSymbol(mathMatrixAccessOperatorSymbol);
    }

    @Override
    public String getTextualRepresentation() {
        return astMathMatrixNameExpression.toString();
    }

    @Override
    public boolean isMatrixNameExpression() {
        return true;
    }
}
