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
package de.monticore.numberunit._ast;

import java.util.Optional;

import static de.monticore.numberunit.PrintHelper.print;

/**
 * Created by MichaelvonWenckstern on 08.01.2018.
 */
public class ASTComplexNumber extends ASTComplexNumberTOP {
    public  ASTComplexNumber (
        de.monticore.literals.literals._ast.ASTNumericLiteral real, de.monticore.literals.literals._ast.ASTNumericLiteral im,
        de.monticore.numberunit._ast.ASTI i, Optional<String> negRe, Optional<String> negIm) {
        super(real, im, i, negRe, negIm);
    }

    public ASTComplexNumber() {
        super();
    }

    public double getRealNumber() {
        if (this.isPresentNegRe()) {
            return -1*Double.parseDouble(print(this.getReal()));
        }
        else {
            return Double.parseDouble(print(this.getReal()));
        }
    }

    public double getImagineNumber() {
        if (this.isPresentNegIm()) {
            return -1*Double.parseDouble(print(this.getIm()));
        }
        else {
            return Double.parseDouble(print(this.getIm()));
        }
    }
}
