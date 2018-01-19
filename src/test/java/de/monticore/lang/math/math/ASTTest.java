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
package de.monticore.lang.math.math;

import de.monticore.lang.math._ast.*;
import de.monticore.lang.math._parser.MathParser;
import de.monticore.lang.matrix._ast.ASTMathMatrixValueExplicitExpression;
import de.monticore.lang.matrix._ast.ASTMathVectorExpression;
import de.monticore.numberunit._ast.ASTComplexNumber;
import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ASTTest {

    @BeforeClass
    public static void init(){
        Log.enableFailQuick(false);
    }

    @Test
    public void ASTComplexTest1() throws Exception{
        MathParser parser = new MathParser();
        Optional<ASTComplexNumber> ast = parser.parse_StringComplexNumber("1+2i");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTComplexTest2() throws Exception{
        MathParser parser = new MathParser();
        Optional<ASTMathScript> ast = parser.parse_StringMathScript("script a Q^2+1i a; end");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
        assertFalse(ast.get().getExpressionList().isEmpty());
    }

    @Test
    public void ASTForTest1() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathForLoopExpression> ast = parser.parse_StringMathForLoopExpression("for a = b a++; b= a+c; end");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
        }

    @Test
    public void ASTForTest2() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathForLoopExpression> ast = parser.parse_StringMathForLoopExpression("for a = b a++; for c= 1 b= a+c; end d= 4+a end");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTIfTest1() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathConditionalExpression> ast = parser.parse_StringMathConditionalExpression("if (a == b) b=a+1; end ");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTMatrix1Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathMatrixValueExplicitExpression> ast = parser.parse_StringMathMatrixValueExplicitExpression("[1,2,3;4,5,6] ");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTMatrix2Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathMatrixValueExplicitExpression> ast = parser.parse_StringMathMatrixValueExplicitExpression("[1 2 3;4 5 6] ");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTMatrixTest3() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathMatrixValueExplicitExpression> ast = parser.parse_StringMathMatrixValueExplicitExpression("[1+3i, 4+5i ;8 9] ");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTDeclaration1Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathDeclarationExpression> ast = parser.parse_StringMathDeclarationExpression("Q^{1,2} a");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTDeclaration2Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathDeclarationExpression> ast = parser.parse_StringMathDeclarationExpression("N(1:2:6)^1 a");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTDeclarationInvalidTest() throws Exception {
        //A is no ElementType
        MathParser parser = new MathParser();
        Optional<ASTMathDeclarationExpression> ast = parser.parse_StringMathDeclarationExpression("A(1:2:6)^1 a");
        assertFalse(ast.isPresent());
        assertTrue(parser.hasErrors());
    }

    @Test
    public void ASTAssignmentDeclaration1Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentDeclarationExpression> ast = parser.parse_StringMathAssignmentDeclarationExpression("Q(1:2:6)^1 a = 1+2*4");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTAssignmentDeclaration2Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentDeclarationExpression> ast = parser.parse_StringMathAssignmentDeclarationExpression("Z^{3,7} nsu1je = [1,2;3,4]");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTAssignmentDeclarationInvalid1Test() throws Exception {
        //E is no ElementType
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentDeclarationExpression> ast = parser.parse_StringMathAssignmentDeclarationExpression("E^{3,7} nsu1je = [1,2;3,4]");
        assertFalse(ast.isPresent());
        assertTrue(parser.hasErrors());
    }

    @Test
    public void ASTAssignmentDeclarationInvalid2Test() throws Exception {
        //== is not allowed
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentDeclarationExpression> ast = parser.parse_StringMathAssignmentDeclarationExpression("Q^{3,7} nsu1je == [1,2;3,4]");
        assertFalse(ast.isPresent());
        assertTrue(parser.hasErrors());
    }

    @Test
    public void ASTAssignmentDeclarationInvalid3Test() throws Exception {
        //name is missing
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentDeclarationExpression> ast = parser.parse_StringMathAssignmentDeclarationExpression("Q^3 = [1 2 3]");
        assertFalse(ast.isPresent());
        assertTrue(parser.hasErrors());
    }

    @Test
    public void ASTAssignment1Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentExpression> ast = parser.parse_StringMathAssignmentExpression("A = [1,2,2]");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTAssignment2Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentExpression> ast = parser.parse_StringMathAssignmentExpression("A.r = 3:4:5");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

    @Test
    public void ASTAssignment3Test() throws Exception {
        MathParser parser = new MathParser();
        Optional<ASTMathAssignmentExpression> ast = parser.parse_StringMathAssignmentExpression("B(1,3) = [4;6]");
        assertTrue(ast.isPresent());
        assertFalse(parser.hasErrors());
    }

}