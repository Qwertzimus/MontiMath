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

import static de.monticore.numberunit.PrintHelper.print;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import java.util.Optional;

import de.monticore.literals.literals._ast.ASTNumericLiteral;

/**
 * Created by MichaelvonWenckstern on 08.01.2018.
 */
public class ASTNumberWithUnit extends ASTNumberWithUnitTOP {

  public  ASTNumberWithUnit (
          Optional<de.monticore.numberunit._ast.ASTComplexNumber> complexNumber,
          Optional<de.monticore.numberunit._ast.ASTNumberWithInf> number,
          Optional<de.monticore.numberunit._ast.ASTUnit> unit) {
    super(complexNumber,number, unit);
  }

  public ASTNumberWithUnit() {
    super();
  }

  public boolean isPlusInfinite() {
    return this.isPresentNum() && this.getNum().isPresentNegInf();
  }

  public boolean isMinusInfinite() {
    return this.isPresentNum() && this.getNum().isPresentNegInf();
  }

  public boolean isComplexNumber() {
    return this.isPresentCn();
  }

  /**
   * returns Optional.empty() if the number is:
   *   a) a complex number
   *   b) plus or minus infinity
   * @return
   */
  public Optional<Double> getNumber() {
    if (this.isPresentNum() && this.getNum().isPresentNumber()) {
      ASTNumericLiteral number = this.getNum().getNumber();
      double d;
      if (this.getNum().isPresentNegNumber()) {
        d = -1*Double.parseDouble(print(number));
      }
      else {
        d = Double.parseDouble(print(number));
      }

      if (this.isPresentUn() && this.getUn().isPresentImperialUnit() &&
              this.getUn().getImperialUnit().getName().equals("th")) {
        d *= 0.0254;
      }
      return Optional.of(d);
    }
    return Optional.empty();
  }

  public Optional<ASTComplexNumber> getComplexNumber() {
    return this.getCnOpt();
  }

  public Unit getUnit() {
    if (this.isPresentUn()) {
      if (this.getUn().isPresentDegCelsius()) {
        return SI.CELSIUS;
      }
      if (this.getUn().isPresentDegFahrenheit()) {
        return NonSI.FAHRENHEIT;
      }
      if (this.getUn().isPresentImperialUnit()) {
        if (this.getUn().getImperialUnit().getName().equals("th")) {
          return Unit.valueOf("mm");
        }
        return Unit.valueOf(this.getUn().getImperialUnit().getName());
      }
      if (this.getUn().isPresentSIUnit()) {
        return siUnit(this.getUn().getSIUnit());
      }
    }
    return Unit.ONE;
  }

  protected Unit siUnit(ASTSIUnit siUnit) {
    if (siUnit.isEmptyTimeDivs()) {
      return Unit.valueOf(siUnit.getSiUnitDimensionless().getName().replace("deg", "°"));
    }
    String s = toString(siUnit.getSIUnitBasicList().get(0));
    for (int i = 1; i < siUnit.getSIUnitBasicList().size(); i++) {
      s += toString(siUnit.getTimeDiv(i-1)) +  toString(siUnit.getSIUnitBasicList().get(i));
    }
    return Unit.valueOf(s);
  }

  protected String toString(ASTSIUnitBasic sib) {
    String unit = "";
    if (sib.isPresentUnitBaseDimWithPrefix()) {
      unit =  sib.getUnitBaseDimWithPrefix().getName();
    }
    else if (sib.isPresentOfficallyAcceptedUnit()) {
      unit = sib.getOfficallyAcceptedUnit().getName();
    }
    else if (sib.isPresentDeg()) {
      unit = "°";
    }
    if (sib.isPresentSignedIntLiteral()) {
      unit = unit + "^" + print(sib.getSignedIntLiteral());
    }
    return unit;
  }

  protected String toString(ASTTimeDiv timeDivs) {
    return timeDivs.isPresentIsDiv() ? "/" : "*";
  }
}