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
