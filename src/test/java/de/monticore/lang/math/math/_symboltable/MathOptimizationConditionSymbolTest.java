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
import de.monticore.lang.math.math._symboltable.expression.MathForLoopExpressionSymbol;
import de.monticore.lang.math.math._symboltable.expression.MathOptimizationExpressionSymbolTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for MathOptimizationConditionSymbol
 *
 * @author Christoph Richter
 */
public class MathOptimizationConditionSymbolTest extends MathOptimizationExpressionSymbolTest {

    // condition symbols
    protected MathOptimizationConditionSymbol minimizationTestConditionSymbol1;
    protected MathOptimizationConditionSymbol lpTestConditionSymbol1;
    protected MathOptimizationConditionSymbol upperAndLowerBoundTestConditionSymbol1;
    protected MathOptimizationConditionSymbol upperAndLowerBoundTestConditionSymbol2;
    protected MathForLoopExpressionSymbol forLoopConditionTestConditionSymbol;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        minimizationTestConditionSymbol1 = (MathOptimizationConditionSymbol) minimizationTestSymbol.getSubjectToExpressions().get(0);
        lpTestConditionSymbol1 = (MathOptimizationConditionSymbol) lpTestSymbol.getSubjectToExpressions().get(0);
        upperAndLowerBoundTestConditionSymbol1 = (MathOptimizationConditionSymbol) upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(0);
        upperAndLowerBoundTestConditionSymbol2 = (MathOptimizationConditionSymbol) upperAndLowerBoundTestSymbol.getSubjectToExpressions().get(1);
        forLoopConditionTestConditionSymbol = (MathForLoopExpressionSymbol) forLoopConditionTestSymbol.getSubjectToExpressions().get(0);
    }

    @Test
    public void getLowerBound() {
        assertFalse(minimizationTestConditionSymbol1.getLowerBound().isPresent());
        assertTrue(lpTestConditionSymbol1.getLowerBound().isPresent());
        assertTrue(upperAndLowerBoundTestConditionSymbol1.getLowerBound().isPresent());
        assertTrue(upperAndLowerBoundTestConditionSymbol2.getLowerBound().isPresent());
    }

    @Test
    public void getUpperBound() {
        assertTrue(minimizationTestConditionSymbol1.getUpperBound().isPresent());
        assertTrue(lpTestConditionSymbol1.getUpperBound().isPresent());
        assertTrue(upperAndLowerBoundTestConditionSymbol1.getUpperBound().isPresent());
        assertFalse(upperAndLowerBoundTestConditionSymbol2.getUpperBound().isPresent());
    }

    @Test
    public void getBoundedExpression() {
        assertTrue(minimizationTestConditionSymbol1.getBoundedExpression().getTextualRepresentation().contains("x"));
        assertTrue(lpTestConditionSymbol1.getBoundedExpression().getTextualRepresentation().contains("x"));
        assertTrue(upperAndLowerBoundTestConditionSymbol1.getBoundedExpression().getTextualRepresentation().contains("x"));
        assertTrue(upperAndLowerBoundTestConditionSymbol2.getBoundedExpression().getTextualRepresentation().contains("x"));
    }

    @Test
    public void testBoundsEqual() {
        MathOptimizationConditionSymbol condition1 = upperAndLowerBoundTestConditionSymbol1;
        assertNotEquals(condition1.getLowerBound().get(), condition1.getUpperBound().get());
        MathOptimizationConditionSymbol condition2 = lpTestConditionSymbol1;
        assertEquals(condition2.getLowerBound().get(), condition2.getUpperBound().get());
    }

    @Test
    public void resolveBoundedExpressionToOptimizationVariable() {
        MathExpressionSymbol bound = minimizationTestConditionSymbol1.getUpperBound().get();
        MathExpressionSymbol expr = minimizationTestConditionSymbol1.getBoundedExpression();
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

    @Test
    public void testForLoopConditions() {
        assertNotNull(forLoopConditionTestConditionSymbol);
        for (MathExpressionSymbol symbol: forLoopConditionTestConditionSymbol.getForLoopBody()) {
            assertTrue(symbol instanceof MathOptimizationConditionSymbol);
        }
        assertTrue(forLoopConditionTestConditionSymbol.getForLoopBody().size() == 2);
    }
}