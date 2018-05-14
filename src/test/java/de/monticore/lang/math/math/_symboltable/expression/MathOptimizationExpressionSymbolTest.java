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
package de.monticore.lang.math.math._symboltable.expression;

import de.monticore.lang.math.math.AbstractMathChecker;
import de.monticore.lang.math.math._ast.ASTMathCompilationUnit;
import de.monticore.lang.math.math._ast.ASTMathExpression;
import de.monticore.lang.math.math._ast.ASTMathOptimizationExpression;
import de.monticore.lang.math.math._cocos.MathCoCoChecker;
import de.monticore.lang.math.math._cocos.MathCocos;
import de.monticore.lang.math.math._symboltable.MathOptimizationType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MathOptimizationExpressionSymbolTest extends AbstractMathChecker {

    // fields
    protected MathOptimizationExpressionSymbol minimizationTestSymbol;
    protected MathOptimizationExpressionSymbol maximizationTestSymbol;
    protected MathOptimizationExpressionSymbol lpTestSymbol;
    protected MathOptimizationExpressionSymbol upperAndLowerBoundTestSymbol;
    protected MathOptimizationExpressionSymbol forLoopConditionTestSymbol;

    @Override
    protected MathCoCoChecker getChecker() {
        return MathCocos.createChecker();
    }

    // helper methods
    protected MathOptimizationExpressionSymbol getMathOptimizationExpressionSymbolFromTestScript(String pathToModel, int index) {
        // get all math expressions in script
        ASTMathCompilationUnit root = loadModel(pathToModel);
        List<ASTMathExpression> mathExpressions = root.getMathScript().getMathStatements().getMathExpressions();
        // delete non MathOptimizationExpressionSymbols
        mathExpressions.removeIf(astMathExpression -> !(astMathExpression instanceof ASTMathOptimizationExpression));
        return (MathOptimizationExpressionSymbol) mathExpressions.get(index).getSymbol().orElse(null);
    }

    @Before
    public void setUp() throws Exception {
        minimizationTestSymbol = getMathOptimizationExpressionSymbolFromTestScript("src/test/resources/optimization/MinimizationTest.m", 0);
        maximizationTestSymbol = getMathOptimizationExpressionSymbolFromTestScript("src/test/resources/optimization/MaximizationTest.m", 0);
        lpTestSymbol = getMathOptimizationExpressionSymbolFromTestScript("src/test/resources/optimization/LpTest.m", 0);
        upperAndLowerBoundTestSymbol = getMathOptimizationExpressionSymbolFromTestScript("src/test/resources/optimization/UpperAndLowerBoundTest.m", 0);
        forLoopConditionTestSymbol = getMathOptimizationExpressionSymbolFromTestScript("src/test/resources/optimization/ForLoopConditionTest.m", 0);
    }

    @Test
    public void getOptimizationType() {
        assertEquals(minimizationTestSymbol.getOptimizationType(), MathOptimizationType.MINIMIZATION);
        assertEquals(maximizationTestSymbol.getOptimizationType(), MathOptimizationType.MAXIMIZATION);
    }

    @Test
    public void getOptimizationVariable() {
        assertTrue(minimizationTestSymbol.getOptimizationVariable().getName().contentEquals("x"));
    }

    @Test
    public void getObjectiveExpression() {
        assertTrue(minimizationTestSymbol.getObjectiveExpression().getTextualRepresentation().contentEquals("Q optimization.MinimizationTest.y = x^2"));
    }

    @Test
    public void getSubjectToExpressions() {
        assertTrue(minimizationTestSymbol.getSubjectToExpressions().get(0).getTextualRepresentation().replace(" ", "").contentEquals("x<=1"));
        assertTrue(lpTestSymbol.getSubjectToExpressions().size() == 6);
        assertTrue(upperAndLowerBoundTestSymbol.getSubjectToExpressions().size() == 2);
        assertTrue(forLoopConditionTestSymbol.getSubjectToExpressions().size() >= 1);
    }

}