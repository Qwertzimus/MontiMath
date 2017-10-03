package de.monticore.lang.math.math._symboltable;

//import de.monticore.lang.math.math._ast.ASTMathStatement;

import de.monticore.lang.math.math._ast.ASTMathExpression;
import de.monticore.lang.math.math._ast.ASTMathStatements;
import de.monticore.lang.math.math._symboltable.expression.MathExpressionSymbol;
import de.monticore.symboltable.CommonSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sascha Schneiders
 */
public class MathStatementsSymbol extends CommonSymbol {
    public static MathStatementsSymbolKind KIND = new MathStatementsSymbolKind();
    public ASTMathStatements astMathStatements = null;

    protected List<MathExpressionSymbol> mathExpressionSymbols = null;

    public MathStatementsSymbol(String name, ASTMathStatements ast) {
        super(name, KIND);
        this.astMathStatements = ast;
    }


    public ASTMathStatements getAstMathStatements() {
        return astMathStatements;
    }

    public void setAstMathStatements(ASTMathStatements astMathStatements) {
        this.astMathStatements = astMathStatements;
    }

    public List<MathExpressionSymbol> getMathExpressionSymbols() {
        if (mathExpressionSymbols == null) {
            mathExpressionSymbols = new ArrayList<>();
            for (ASTMathExpression astMathExpression : astMathStatements.getMathExpressions()) {
                mathExpressionSymbols.add((MathExpressionSymbol) astMathExpression.getSymbol().get());
            }
        }
        return mathExpressionSymbols;
    }
}
