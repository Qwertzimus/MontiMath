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
package de.monticore.lang.math._symboltable.expression;

import de.monticore.expressionsbasis._ast.ASTExpression;
import de.monticore.lang.math._ast.ASTAssignmentType;
import de.monticore.lang.math._ast.ASTDimension;
import de.monticore.lang.math._ast.ASTElementType;
import de.se_rwth.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sascha Schneiders
 */
public class MathValueType extends MathExpressionSymbol {
    protected ASTElementType type;
    protected List<String> properties = new ArrayList<>();
    protected List<MathExpressionSymbol> dimensions = new ArrayList<>();

    public MathValueType() {

    }

    public void addDimension(MathExpressionSymbol dimension) {
        dimensions.add(dimension);
    }

    public List<MathExpressionSymbol> getDimensions() {
        return dimensions;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public void setDimensions(List<MathExpressionSymbol> dimensions) {
        this.dimensions = dimensions;
    }

    public ASTElementType getType() {
        return type;
    }

    public void setType(ASTElementType type) {
        this.type = type;
    }

    public boolean isRationalType() {
        return type.getName().contentEquals("Q");
    }

    public boolean isComplexType() {
        return type.getName().contentEquals("C");
    }

    public boolean isStatic() {
        return properties.contains("static");
    }

    @Override
    public boolean isMathValueTypeExpression() {
        return true;
    }

    @Override
    public String getTextualRepresentation() {
        String result = "";

        //result += type.toString();

        if (isRationalType())
            result += "Q";
        else if (isComplexType()) {
            result += "C";
        }
        if (type.getRangeOpt().isPresent()) {
            result += type.getRangeOpt().get().toString();
        }
        if (dimensions.size() > 0) {
            int counter = 0;
            result += "^{";
            for (MathExpressionSymbol dimension : dimensions) {
                result += dimension.getTextualRepresentation();
                ++counter;
                if (dimensions.size() > counter)
                    result += ", ";
            }
            result += "}";
        }
        return result;
    }

    public static MathValueType convert(ASTAssignmentType type) {
        MathValueType mathValueType = new MathValueType();

        mathValueType.setProperties(type.getMatrixPropertyList());

        mathValueType.setType(type.getElementType());

        if (type.getElementType().getDimensionOpt().isPresent()) {
            ASTDimension astDimension = type.getElementType().getDimensionOpt().get();
            if (astDimension.getVecDimOpt().isPresent())
                if (astDimension.getVecDimOpt().get().getSymbolOpt().isPresent())
                    mathValueType.addDimension((MathExpressionSymbol) astDimension.getVecDimOpt().get().getSymbolOpt().get());
                else
                    Log.error(String.format("%s: Dimension symbol not present.", astDimension.toString()), astDimension.get_SourcePositionStart());
            for (ASTExpression astMathArithmeticExpression : astDimension.getMatrixDimList()) {
                if (astMathArithmeticExpression.getSymbolOpt().isPresent())
                    mathValueType.addDimension((MathExpressionSymbol) astMathArithmeticExpression.getSymbolOpt().get());
                else
                    Log.error(String.format("%s: Dimension symbol not present.", astDimension.toString()), astDimension.get_SourcePositionStart());
            }
        }

        return mathValueType;
    }
}
