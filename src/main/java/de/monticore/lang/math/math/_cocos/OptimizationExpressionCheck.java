/**
 *
 *  ******************************************************************************
 *  MontiCAR Modeling Family, www.se-rwth.de
 *  Copyright (c) 2017, Software Engineering Group at RWTH Aachen,
 *  All rights reserved.
 *
 *  This project is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 3.0 of the License, or (at your option) any later version.
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * *******************************************************************************
 */
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
