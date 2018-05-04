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
package de.monticore.lang.math.math._symboltable;

import de.monticore.lang.math.math._symboltable.expression.MathExpressionSymbol;
import de.monticore.lang.math.math._symboltable.expression.MathOptimizationExpressionSymbolTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for MathOptimizationConditionSymbol
 *
 * @author Christoph Richter
 */
public class MathOptimizationConditionSymbolTest extends MathOptimizationExpressionSymbolTest {

    @Test
    public void getLowerBound() {
        assertFalse(minimizationTestSymbol.getSubjectToExpressions().get(0).getLowerBound().isPresent());
        assertTrue(lpTestSymbol.getSubjectToExpressions().get(0).getLowerBound().isPresent());
        assertTrue(upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(0).getLowerBound().isPresent());
        assertTrue(upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(1).getLowerBound().isPresent());
    }

    @Test
    public void getUpperBound() {
        assertTrue(minimizationTestSymbol.getSubjectToExpressions().get(0).getUpperBound().isPresent());
        assertTrue(lpTestSymbol.getSubjectToExpressions().get(0).getUpperBound().isPresent());
        assertTrue(upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(0).getUpperBound().isPresent());
        assertFalse(upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(1).getUpperBound().isPresent());
    }

    @Test
    public void getBoundedExpression() {
        assertTrue(minimizationTestSymbol.getSubjectToExpressions().get(0).getBoundedExpression().getTextualRepresentation().contains("x"));
        assertTrue(lpTestSymbol.getSubjectToExpressions().get(0).getBoundedExpression().getTextualRepresentation().contains("x"));
        assertTrue(upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(0).getBoundedExpression().getTextualRepresentation().contains("x"));
        assertTrue(upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(1).getBoundedExpression().getTextualRepresentation().contains("x"));
    }

    @Test
    public void testBoundsEqual() {
        MathOptimizationConditionSymbol condition1 = upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(0);
        assertNotEquals(condition1.getLowerBound().get(), condition1.getUpperBound().get());
        MathOptimizationConditionSymbol condition2 = lpTestSymbol.getSubjectToExpressions().get(0);
        assertEquals(condition2.getLowerBound().get(), condition2.getUpperBound().get());
    }

    @Test
    public void resolveBoundedExpressionToOptimizationVariable() {
        MathExpressionSymbol bound = minimizationTestSymbol.getSubjectToExpressions().get(0).getUpperBound().get();
        MathExpressionSymbol expr = minimizationTestSymbol.getSubjectToExpressions().get(0).getBoundedExpression();
        resolveBoundedExpressionToOptimizationVariableForOperator(bound, expr, "<=");
        resolveBoundedExpressionToOptimizationVariableForOperator(expr, bound, "<=");
        resolveBoundedExpressionToOptimizationVariableForOperator(bound, expr, "==");
        resolveBoundedExpressionToOptimizationVariableForOperator(expr, bound, "==");
        resolveBoundedExpressionToOptimizationVariableForOperator(bound, expr, ">=");
        resolveBoundedExpressionToOptimizationVariableForOperator(expr, bound, ">=");
    }

    private void resolveBoundedExpressionToOptimizationVariableForOperator(MathExpressionSymbol bound, MathExpressionSymbol expr, String op) {
        MathOptimizationConditionSymbol symbol = new MathOptimizationConditionSymbol(bound, op, expr);
        symbol.resolveBoundedExpressionToOptimizationVariable(minimizationTestSymbol.getOptimizationVariable());
        assertTrue(symbol.getBoundedExpression().getTextualRepresentation().contains(minimizationTestSymbol.getOptimizationVariable().getName()));
    }
}