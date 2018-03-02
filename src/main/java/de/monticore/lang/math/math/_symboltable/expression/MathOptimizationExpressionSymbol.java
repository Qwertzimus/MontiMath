/*
 *
 * *****************************************************************************
 * MontiCAR Modeling Family, www.se-rwth.de
 * Copyright (c) 2018, Software Engineering Group at RWTH Aachen,
 * All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package de.monticore.lang.math.math._symboltable.expression;

import de.monticore.lang.math.math._symboltable.MathOptimizationType;
import de.monticore.lang.math.math._symboltable.MathVariableDeclarationSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christoph Richter
 */

public class MathOptimizationExpressionSymbol extends MathExpressionSymbol {
    //region fields

    /**
     * Defines if minimization or maximization is performed
     */
    private MathOptimizationType optimizationType;
    /**
     * Variable which will be minimized/ maximized
     */
    private MathVariableDeclarationSymbol optimizationVariable;
    /**
     * The expression which should be minimized / maximized
     */
    private MathExpressionSymbol objectiveExpression;
    /**
     * List of 0..n constraints
     */
    private List<MathExpressionSymbol> subjectToExpressions = new ArrayList<>();


    //endregion
    // region constructor
    // endregion
    // region getter setter methods
    public MathOptimizationType getOptimizationType() {
        return optimizationType;
    }

    public void setOptimizationType(String optimizationTypeString) {
        if (optimizationTypeString.trim().contentEquals("minimize")) {
            this.optimizationType = MathOptimizationType.MINIMIZATION;
        } else {
            this.optimizationType = MathOptimizationType.MAXIMIZATION;
        }
    }

    public MathVariableDeclarationSymbol getOptimizationVariable() {
        return optimizationVariable;
    }

    public void setOptimizationVariable(MathVariableDeclarationSymbol optimizationVariable) {
        this.optimizationVariable = optimizationVariable;
    }

    public MathExpressionSymbol getObjectiveExpression() {
        return objectiveExpression;
    }

    public void setObjectiveExpression(MathExpressionSymbol objectiveExpression) {
        this.objectiveExpression = objectiveExpression;
    }

    public List<MathExpressionSymbol> getSubjectToExpressions() {
        return subjectToExpressions;
    }

    // endregion
    // region methods
    @Override
    public String getTextualRepresentation() {
        StringBuilder result = new StringBuilder(String.format("minimize(%s) /n", optimizationVariable.getFullName()));
        result.append(String.format("%s /n", objectiveExpression.getTextualRepresentation()));
        result.append("subject to \n");
        for (MathExpressionSymbol symbol : subjectToExpressions) {
            result.append(symbol.getTextualRepresentation()).append(";\n");
        }
        result.append("end;");
        return result.toString();
    }
    // endregion
}
