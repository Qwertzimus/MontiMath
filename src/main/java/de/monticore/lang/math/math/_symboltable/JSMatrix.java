/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
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

package de.monticore.lang.math.math._symboltable;

import org.jscience.mathematics.number.Rational;
import org.jscience.mathematics.vector.Matrix;

import javax.measure.unit.Unit;
import java.util.Vector;

/**
 * @author math-group
 *
 * JScience Matrix Type
 */

public class JSMatrix implements MathValue {
    /** matrix stored in a 2D std::vector */
    private Vector<Vector<MathValue>> matrix;

    /** matrix stored in a JScience Matrix from type Rational */
    private Matrix<Rational> m;

    /** every matrix has one unit, this is the initial value */
    private Unit unit = Unit.ONE;

    /** flag for incompatible units */
    private boolean incompUnit =false;

    /**
     * initialize the 2D std::vector matrix
     */
    public JSMatrix() {
       this.matrix = new Vector<Vector<MathValue>>();
    }

    /**
     * copy the 2D std::vector matrix given by the argument
     *
     * @param matrix
     */
    public JSMatrix(Vector<Vector<MathValue>> matrix) {
        this.matrix = matrix;
    }

    /**
     * set a new 2D std::vector matrix given by the argument
     *
     * @param matrix new 2D std::vector matrix
     */
    public void setMatrix(Vector<Vector<MathValue>> matrix) {
        this.matrix = matrix;
    }

    /**
     *  get the 2D std::vector matrix
     *
     * @return 2D std::vector matrix
     */
    public Vector<Vector<MathValue>> getMatrix() {
        return this.matrix;
    }

    /**
     * add this row to the 2D std::vector based matrix representation
     *
     * @param row std::vector representation
     */
    public void addRow(Vector<MathValue> row) {
        this.matrix.add(row);
    }

    /**
     * get element (i,j) from matrix A
     *
     * @param row number of row ranges between 0..n-1
     * @param col number of col ranges between 0..m-1
     * @return math value of matrix A at position A(row, col)
     */
    public MathValue getElement(int row, int col) {
        return matrix.get(row).get(col);
    }

    /**
     * set element (i,j) of matrix A
     *
     * @param row number of row ranges between 0..n-1
     * @param col number of col ranges between 0..m-1
     * @param val new value for entry A(row, col)
     */
    public void setElement(int row, int col,MathValue val){
        matrix.get(row).set(col,val);
    }

    /**
     * @return number of rows
     */
    public int getRowDimension() {
        return matrix.size();
    }

    /**
     * @return number of columns
     */
    public int getColDimension() {
        return matrix.get(0).size();
    }

    /**
     * get a specific row as std::vector based representation
     *
     * @param i number of row
     * @return std::vector based representation of the i-th row
     */
    public Vector<MathValue> getRow(int i) {
        return matrix.get(i);
    }

    /**
     * @return JScience based representation of the matrix
     */
    public Matrix<Rational> getJScienceMatrix(){
        return this.m;
    }

    /**
     * each vector (row) of the 2D std::vector based matrix is printed in a new line to the screen
     *
     * @return String which contains the whole output
     */
    @Override
    public String toString() {
        String output = new String();

        for(int i = 0; i < matrix.size(); i++) {
                output = output + matrix.get(i).toString() + "\n";
        }

        return output;
    }

    /**
     * @return the unit of the matrix
     */
    @Override
    public Unit getUnit() {
        return unit;
    }

    /**
     * @param unit (jscience unit)
     */
    @Override
    public void setUnit(Unit unit) {
        this.unit=unit;
    }

    /**
     * @return TRUE if the unit is incompatible otherwise FALSE
     */
    @Override
    public boolean isIncompUnit(){
        return incompUnit;
    }

    /**
     * @param x set TRUE if the math value/expression has incompatible units, otherwise FALSE
     */
    @Override
    public void setIncompUnit(boolean x){
        incompUnit = x;
    }

}
