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

import org.junit.Test;

import static de.monticore.lang.math.math.SymbolTableTestHelper.createSymbolTableOnInputAndExpectErrorCode;



public class ElementTypeTest {

    @Test
    public void testElementTypeMinBiggerThanMax() throws Exception {
        createSymbolTableOnInputAndExpectErrorCode("script S Q(8:4) a; end", "0xMATH17");
    }

    @Test
    public void testElementTypeTooBigSteps() throws Exception {
        createSymbolTableOnInputAndExpectErrorCode("script S Q(1:20:10) a; end", "0xMATH19");
    }

    @Test
    public void testElementTypeComplex() throws Exception {
        createSymbolTableOnInputAndExpectErrorCode("script S Q(1+3i : 5+4i) a; end", "0xMATH18");
    }

    @Test
    public void testElementTypeInfinity() throws Exception {
        createSymbolTableOnInputAndExpectErrorCode("script S Q(1:oo) a; end", "0xMATH18");
    }

}
