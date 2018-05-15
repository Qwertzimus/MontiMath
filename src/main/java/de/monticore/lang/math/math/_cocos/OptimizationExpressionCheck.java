package de.monticore.lang.math.math._cocos;

import de.monticore.lang.math.math._ast.ASTMathOptimizationExpression;
import de.monticore.lang.math.math._ast.ASTMathOptimizationObjectiveFunctionExpression;
import de.se_rwth.commons.logging.Log;

/**
 * Context Conditions for optimization statement
 *
 * @author Christoph Richter
 */
public class OptimizationExpressionCheck implements MathASTMathOptimizationExpressionCoCo {


    @Override
    public void check(ASTMathOptimizationExpression node) {
        checkObjectiveFunctionReturnVariable(node.getMathOptimizationObjectiveFunctionExpression());
    }

    /**
     * Checks if the return value of the objective function is scalar
     *
     * @param objFunc AST objective function expression
     */
    private void checkObjectiveFunctionReturnVariable(ASTMathOptimizationObjectiveFunctionExpression objFunc) {
        if (objFunc.getMathAssignmentDeclarationExpression().isPresent()) {
            if (objFunc.getMathAssignmentDeclarationExpression().get().getType().dimIsPresent()) {
                Log.error(String.format("Return type of objective function \"%s\" must be scalar.", objFunc.toString()), objFunc.get_SourcePositionStart());
            }
        }
    }

}
