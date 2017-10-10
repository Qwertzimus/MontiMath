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

import de.monticore.lang.math.math._cocos.MathCoCoChecker;
import de.monticore.lang.math.math._cocos.MathCocos;
import de.monticore.lang.math.math._ast.*;
import de.monticore.lang.math.math._symboltable.*;
import de.se_rwth.commons.logging.Log;
import org.jscience.mathematics.number.Rational;

import javax.measure.unit.UnitFormat;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author math-group
 *         generator class that generade the appropriate code
 */
public class Generator extends AbstCocoCheck {

    private String FILEENDING = ".m";
    public static String GENERATEDTARGETPATH = "./target/generated-sources/monticore/sourcecode/de/monticore/lang/math/math/generator/";
    public static String GENERATEDCLASSTARGETPATH = "./target/classes/";
    public static String BASEPACKAGE = "de.monticore.lang.math.math.generator";
    private int tmpCount = 0;
    private BufferedWriter bw;
    private Stack<String> names = new Stack<>(); // stack to save code fragments for Operations (Numbers)
    private Stack<String> units = new Stack<>(); // stack to save code fragments for Operations (Units)
    private Stack<Object> upnStack = new Stack<>(); //stack for Calculations (reverse Polish Notation)
    private List<String> imports = Arrays.asList("package de.monticore.lang.math.math.generator;",
            "import org.jscience.mathematics.number.Rational;",
            "import org.jscience.mathematics.vector.DenseMatrix;",
            "import org.jscience.mathematics.vector.Matrix;",
            "import javax.measure.unit.Unit;", "import de.monticore.lang.math.math.Executable;",
            "import java.lang.reflect.Field;",
            "import java.io.FileDescriptor;",
            "import java.io.FileOutputStream;",
            "import java.io.PrintStream;");


    /**
     * Method you have to call to generate the code
     *
     * @param Filepath filepath where the generated file is
     * @param filename filename of the file
     */ /*
    public void generate(String Filepath, String filename) throws IOException {

        //do CocoChecking before generating the code
        MathCoCoChecker checker = this.getChecker();

        ASTMathCompilationUnit root = loadModel(Filepath + filename + FILEENDING);
        checker.checkAll(root);

        ///imports= new ArrayList<String>();
        //imports.add("package de.monticore.lang.math.generator;");
        //imports.add("import org.jscience.mathematics.number.Rational;");
        //imports.add("import org.jscience.mathematics.vector.DenseMatrix;");
        //imports.add("import org.jscience.mathematics.vector.Matrix;");
        //imports.add("import javax.measure.unit.Unit;");

        //create the File and initialize the Writers
        File javaFile = new File("./" + GENERATEDTARGETPATH + filename + ".java");

        if (!javaFile.exists()) {
            javaFile.getParentFile().mkdirs();
            if (!javaFile.createNewFile()) {
                Log.error("File could not be created");
            }
        }


        bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(javaFile.getPath())));;

        //generate the code
        generateImports();
        bw.newLine();
        bw.write("public class " + filename + " implements Executable {");
        bw.newLine();
        generateGlobalVariables(root.getMathScript());
        bw.write("\t" + "public " + filename + "(){}\n");
        bw.newLine();
        generateExecute(root.getMathScript());
        bw.newLine();
        bw.newLine();
        generateTimeWise();
        generatePowerWise();
        generateBuildString();
        generateBuildStringHelp();
        bw.write("}");
        bw.close();
    }

    /**
     * generates the Imports of the java class
     *//*
    private void generateImports() throws IOException {
        for (int i = 0; i < imports.size(); i++) {
            bw.write(imports.get(i));
            bw.newLine();
        }
    }

    /**
     * generates the Globalvariables of the java class
     *
     * @param root the root node of the symbolTable
     */
    /*
    private void generateGlobalVariables(ASTMathScript root) throws IOException {
        String variableNameList = "private String[] variableNames = {";
        List<ASTMathStatement> statements = root.getMathStatements().getMathStatements();
        //looks for the Assignments and generates the Variables
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i).assignmentIsPresent()) {
                if(statements.get(i).getAssignment().get().getType().isPresent()) {
                    bw.write("\t" + generateGlobalVariable(statements.get(i).getAssignment().get()) + "\n");

                    MathVariableDeclarationSymbol sym = (MathVariableDeclarationSymbol) statements.get(i).getAssignment().get().getSymbol().get();
                    if(i == 0){
                        variableNameList += "\"" + sym.getName() + "\"";
                    }
                    else {
                        variableNameList += ", " + "\"" + sym.getName() + "\"";
                    }
                }
            }
        }
        variableNameList += "};";
        bw.write("\t" + variableNameList + "\n");
        bw.write("\tprivate String FILEENDING = \".m\";\n");
        bw.write("\tprivate String GENERATEDTARGETPATH= \"src/test/resources\";\n");
    }

    /**
     * generates the Globalvariables of the java class
     *
     * @param assignment the assignment of the global variable
     * @return the String of the initialization of the variable
     */ /*
    private String generateGlobalVariable(ASTAssignment assignment) {
        String GlobalVariable = "";
        MathVariableDeclarationSymbol sym = (MathVariableDeclarationSymbol) assignment.getSymbol().get();
        //if we have a dimension, it is a matrix
        if (assignment.getType().get().dimIsPresent()) {
            GlobalVariable += "private Matrix<Rational> " + sym.getName() + " ;\n";

        } else {
            //looks if it is an boolean variable
            if (sym.isBoolean()) {
                GlobalVariable += "private boolean " + sym.getName() + " ;\n";
            }
            //it is a Rational variable
            else {
                GlobalVariable += "private Rational " + sym.getName() + " ;\n";
            }
        }
        //if it is not boolean it maybe has a Unit
        if (!sym.isBoolean())
            GlobalVariable += "\tUnit unit" + sym.getName() + ";";
        return GlobalVariable;
    }

    /**
     * generates the ForVariable
     *
     * @param forsymbol the symbol of the forvariable
     */ /*
    private void generateForVariable(MathVariableDeclarationSymbol forsymbol) throws IOException {
        bw.write("Matrix<Rational> " + forsymbol.getName() + ";");
        bw.newLine();
        bw.write("Unit unit" + forsymbol.getName() + ";");
        bw.newLine();
        generateExecute(forsymbol);
    }

    /**
     * generates the execute Method
     *
     * @param root the rootnode of the symbol table
     */ /*
    private void generateExecute(ASTMathScript root) throws IOException {
        bw.write("\tpublic void execute(){\n");
        List<ASTMathStatement> statements = root.getMathStatements().getMathStatements();

        //generates for every Mathstatement the java code
        for (int i = 0; i < statements.size(); i++) {
            generateExecute(statements.get(i));
        }

        //generates for every important variable an output
        //for (int i = 0; i < statements.size(); i++) {
            //if (statements.get(i).assignmentIsPresent()) {
               // if (statements.get(i).getAssignment().get().getType().isPresent()) {
                //    String name = statements.get(i).getAssignment().get().getSymbol().get().getName();
               //     if (!((MathVariableDeclarationSymbol) statements.get(i).getAssignment().get().getSymbol().get()).isBoolean())
              //          bw.write("System.out.println(" + "\"" + name + " : \"+" + name + "+ \" Unit : \" + unit" + name + ");\n");
            //        else bw.write("System.out.println(" + "\"" + name + " : \"+" + name + ");\n");
          //      }
         //   }
        //}
        //generates outputFile
        bw.write("\tbuildString();\n");

        bw.write("\n\t}");
    }

    /**
     * generates for a Mathstatement the code for the execute Method
     *
     * @param mathStatement mathstatement we want to generate the code for
     */ /*
    private void generateExecute(ASTMathStatement mathStatement) throws IOException {
        //generates code for an assignment
        if (mathStatement.assignmentIsPresent()) {
            generateExecute((MathVariableDeclarationSymbol) mathStatement.getAssignment().get().getSymbol().get());
        }
        //generates code for a for loop
        else if (mathStatement.forStatementIsPresent()) {
            MathVariableDeclarationSymbol forSymbol = (MathVariableDeclarationSymbol) mathStatement.getForStatement().get().getSymbol().get();
            generateForVariable(forSymbol);
            bw.write("for(int tmp" + forSymbol.getName() + " = 0; tmp" + forSymbol.getName() + "<" + forSymbol.getName() + ".getNumberOfColumns();tmp" + forSymbol.getName() + "++){ ");
            bw.newLine();
            for (int i = 0; i < mathStatement.getForStatement().get().getMathStatements().size(); i++) {
                generateExecute(mathStatement.getForStatement().get().getMathStatements().get(i));

            }
            bw.write("}");
            bw.newLine();
        }
        //generates code for a conditonal Statement
        else if (mathStatement.conditionalStatementIsPresent()) {
            generateConditionalStatement(mathStatement.getConditionalStatement().get());
        }
    }

    /**
     * generates for a MathvariableDeclarationSymbol the code for the execute Method
     *
     * @param symbol the symbol we want to generate the code from
     */ /*
    private void generateExecute(MathVariableDeclarationSymbol symbol) throws IOException {
        //this case means we have an important symbol (Global variable or forVariable)
        if (symbol.getRange() != null || (symbol.getMatrixProperties() != null && symbol.getMatrixProperties().contains("forVariable"))) {
            if (symbol.getValue() instanceof JSValue) {
                bw.write(symbol.getName() + " = " + "Rational.valueOf(\"" + ((JSValue) symbol.getValue()).getRealNumber() + "\");");
                bw.newLine();
                generateUnit(symbol.getName(), UnitFormat.getUCUMInstance().format(symbol.getValue().getUnit()), false, false);
            } else if (symbol.getValue() instanceof JSMatrix) {
                generateMatrix((JSMatrix) symbol.getValue(), Optional.of(symbol.getName()));
                generateUnit(symbol.getName(), UnitFormat.getUCUMInstance().format(symbol.getValue().getUnit()), false, false);
            } else if (symbol.getValue() instanceof MathExpression) {
                generateExecuteMathexpression(symbol);
            } else if (symbol.getValue() instanceof LogicalExpression) {
                generateExecuteLogicalExpression(symbol);
            } else if (symbol.getValue() instanceof MathValueReference) {
                bw.write(symbol.getName() + " = ");
                bw.write(((MathValueReference) symbol.getValue()).getReferencedSymbol().getName() + ";");
                bw.newLine();
                generateUnit(symbol.getName(), ((MathValueReference) symbol.getValue()).getReferencedSymbol().getName(), true, false);
            }
        }
        //in this case the only important case is if the symbol is a Mathexpression with an assignment
        else {
            MathValue val = symbol.getValue();
            if (val instanceof MathExpression) {
                if (((MathExpression) val).getOp().equals(Operator.Assign)) {
                    generateMathOrLogicalExpression(val);
                    generateOp(false);
                    bw.newLine();
                }
            }
        }

    }

    /**
     * generates for a MathvariableDeclarationSymbol which contains a MathExpression the code for the execute Method
     *
     * @param symbol the symbol we want to generate the code from
     */ /*
    private void generateExecuteMathexpression(MathVariableDeclarationSymbol symbol) throws IOException {
        generateMathOrLogicalExpression(symbol.getValue());
        generateOp(false);
        bw.write(symbol.getName() + " = " + names.peek() + ";");
        bw.newLine();
        if (units.peek().startsWith("unit")) {
            bw.write("unit" + symbol.getName() + " = " + units.pop() + ";");
        } else {
            bw.write("unit" + symbol.getName() + " = " + "unit" + units.pop() + ";");
        }
        bw.newLine();
    }

    /**
     * generates for a MathvariableDeclarationSymbol which contains a LogivalExpression the code for the execute Method
     *
     * @param symbol the symbol we want to generate the code from
     */ /*
    private void generateExecuteLogicalExpression(MathVariableDeclarationSymbol symbol) throws IOException {
        generateMathOrLogicalExpression(symbol.getValue());
        generateOp(true);
        bw.write(symbol.getName() + " = " + names.peek() + ";");
        bw.newLine();
    }

    /**
     * generates the code for a ConditionalStatement
     *
     * @param conditionalStatement the conditional Statement we want to generate the code from
     */ /*
    private void generateConditionalStatement(ASTConditionalStatement conditionalStatement) throws IOException {
        //if an ifStatement is present
        if (conditionalStatement.ifStatement2IsPresent()) {
            List<String> boolexps = new ArrayList<>();
            MathVariableDeclarationSymbol boolexp = ((MathVariableDeclarationSymbol) conditionalStatement.getIfStatement2().get().getBooleanExpression()
                    .getSymbol().get());
            //if the condition is an Logical expression generate the logical expression and add it to the list
            if (boolexp.getValue() instanceof LogicalExpression) {
                generateLogicalExpression((LogicalExpression) boolexp.getValue());
                boolexps.add(names.pop());
            }
            //if it is not a LogicalExpresion it can only be an MathValueReference so we add the name of the refernece
            else boolexps.add(((MathValueReference) boolexp.getValue()).getReferencedSymbol().getName());
            //same for the else if statements
            for (ASTElseIfStatement2 elseIfStatement2 : conditionalStatement.getElseIfStatement2s()) {
                boolexp = (MathVariableDeclarationSymbol) elseIfStatement2.getBooleanExpression().getSymbol().get();
                if (boolexp.getValue() instanceof LogicalExpression) {
                    generateLogicalExpression((LogicalExpression) boolexp.getValue());
                    boolexps.add(names.pop());
                } else boolexps.add(((MathValueReference) boolexp.getValue()).getReferencedSymbol().getName());
            }
            //generate the code for if Statement
            generateIfStatement(conditionalStatement.getIfStatement2().get(), boolexps.get(0));
            //generate code for the else if statements
            int i = 1;
            for (ASTElseIfStatement2 elseIfStatement2 : conditionalStatement.getElseIfStatement2s()) {
                generateElseIfStatement(elseIfStatement2, boolexps.get(i));
                i++;
            }
            //if else statement is present generate the code for the else statement
            if (conditionalStatement.elseStatement2IsPresent()) {
                generateElseStatement(conditionalStatement.getElseStatement2().get());
            }
        }
    }

    /**
     * generates for a IfStatement the code for the execute Method
     *
     * @param ifStatement the ifStatement
     * @param cond        the condition in the if statement
     */ /*
    private void generateIfStatement(ASTIfStatement2 ifStatement, String cond) throws IOException {
        bw.write("if(" + cond + "){");
        bw.newLine();
        //generate the body of the if statement
        for (ASTMathStatement mathStatement : ifStatement.getMathStatements()) {
            generateExecute(mathStatement);
        }
        bw.write("}");
        bw.newLine();
    }

    /**
     * generates for a IfStatement the code for the execute Method
     *
     * @param elseifStatement the elseifStatement
     * @param cond            the condition in the else if statement
     */ /*
    private void generateElseIfStatement(ASTElseIfStatement2 elseifStatement, String cond) throws IOException {
        bw.write("else if(" + cond + "){");
        bw.newLine();
        //generates the body of the else if statement
        for (ASTMathStatement mathStatement : elseifStatement.getMathStatements()) {
            generateExecute(mathStatement);
        }
        bw.write("}");
        bw.newLine();
    }

    /**
     * generates for a elseStatement the code for the execute Method
     *
     * @param elseStatement the elseStatement
     */ /*
    private void generateElseStatement(ASTElseStatement2 elseStatement) throws IOException {
        bw.write("else{");
        bw.newLine();
        //generates the body of the else statement
        for (ASTMathStatement mathStatement : elseStatement.getMathStatements()) {
            generateExecute(mathStatement);
        }
        bw.write("}");
        bw.newLine();
    }

    /**
     * generates the code for a LogicalExpression
     *
     * @param log the LogicalExpression
     */ /*
    private void generateLogicalExpression(LogicalExpression log) throws IOException {
        generateMathOrLogicalExpression(log);
        generateOp(true);
    }

    /**
     * generates java code if we have an MatrixIndex present
     *
     * @param tmp the Reference of the Matrix
     */ /*
    private void generateIndex(MathValueReference tmp) throws IOException {
        //Index ist immer JSMatrix (SymbolTableCreator VectorInner ist immer Matrix)
        MathVariableDeclarationSymbol sym = tmp.getReferencedSymbol();
        MathValue index = tmp.getMatrixIndex().get();
        if (index instanceof JSMatrix) {
            //save the Stacks
            Stack saveUpn = (Stack) upnStack.clone();
            Stack saveNames = (Stack) names.clone();
            Stack saveUnits = (Stack) units.clone();
            //generates the indices
            generateIndexHelp((JSMatrix) index, 0);
            //save the code in the Stacks
            String name = names.pop();
            if (((JSMatrix) index).getColDimension() == 2) {
                generateIndexHelp((JSMatrix) index, 1);
                name = ".get(" + name + ".intValue()," + names.pop() + ".intValue())";
                names = saveNames;
                names.push(sym.getName() + name);
            } else {
                names = saveNames;
                names.push(sym.getName() + ".get(0," + name + ".intValue())");
            }
            upnStack = saveUpn;
            units = saveUnits;
        }
    }

    /**
     * generates the indices of a MatrixIndex
     *
     * @param index the indices
     * @param i     specifies which index we generate
     */ /*
    private void generateIndexHelp(JSMatrix index, int i) throws IOException {
        upnStack.clear();
        names.clear();
        units.clear();
        generateMatrixIndex(((JSMatrix) index).getElement(0, i));
        generateOp(false);
    }

    /**
     * generates for a Mathvalue the MatrixIndex
     *
     * @param val the Mathvalue
     */ /*
    private void generateMatrixIndex(MathValue val) {
        if (val instanceof JSValue) {
            upnStack.push("Rational.valueOf(\"" + ((JSValue) val).getRealNumber() + "\")");
        } else if (val instanceof MathValueReference) {
            MathVariableDeclarationSymbol sym = ((MathValueReference) val).getReferencedSymbol();
            if (sym.getMatrixProperties() != null && sym.getMatrixProperties().contains("forVariable")) {
                upnStack.push(val + ".get(0,tmp" + sym.getName() + ")");
            } else {
                upnStack.push(val + "");
            }
        } else if (val instanceof MathExpression) {
            generateMatrixIndex((MathExpression) val);
        }

    }

    /**
     * generates for a Mathexpression the code for the index
     *
     * @param index the index as Mathexpression
     */ /*
    private void generateMatrixIndex(MathExpression index) {
        List<MathVariableDeclarationSymbol> operands = index.getOperands();
        upnStack.push(index.getOp());
        generateMatrixIndex(operands.get(0).getValue(), operands.get(1).getValue());
    }

    /**
     * generates for two Mathvalues the code for the index
     *
     * @param op1 the first operand
     * @param op2 the second operand
     */ /*
    private void generateMatrixIndex(MathValue op1, MathValue op2) {
        if (op1 instanceof JSValue) {
            upnStack.push("Rational.valueOf(\"" + ((JSValue) op1).getRealNumber() + "\")");
            generateMatrixIndexHelp(op2);
        } else if (op1 instanceof MathExpression) {
            generateMatrixIndex((MathExpression) op1);
            generateMatrixIndexHelp(op2);
        } else if (op1 instanceof MathValueReference) {
            upnStack.push(op1);
            generateMatrixIndexHelp(op2);
        }
    }

    /**
     * generates for a Mathvalue the code for the index
     *
     * @param op2 the Operand
     */ /*
    private void generateMatrixIndexHelp(MathValue op2) {
        if (op2 instanceof JSValue) {
            upnStack.push("Rational.valueOf(\"" + ((JSValue) op2).getRealNumber() + "\")");
        } else if (op2 instanceof MathValueReference) {
            upnStack.push(op2);
        } else if (op2 instanceof MathExpression) {
            generateMatrixIndex((MathExpression) op2);
        }
    }

    /**
     * generates the code for an Operation
     *
     * @param isLogical flag if we have a logical Expression
     */ /*
    private void generateOp(boolean isLogical) throws IOException {
        //clear name and unit stacks
        names.clear();
        units.clear();
        //clone upnStack for generation Units
        Stack s = (Stack) upnStack.clone();
        //generate Operation using the reverse Polish notation
        while (!upnStack.isEmpty()) {
            if (upnStack.peek() instanceof String) {
                names.push((String) upnStack.pop());
            } else if (upnStack.peek() instanceof MathValueReference) {
                //if we have a MathvalueReference we have to distinguish some cases
                generateOpMathValueRefernece((MathValueReference) upnStack.pop());
            } else {
                Operator op = (Operator) upnStack.pop();
                generateOpNumbers(names.pop(), names.pop(), op);
                upnStack.push(names.pop());
            }
            if (!isLogical) {
                generateOp(s);
            }
        }
    }

    /**
     * generates the code for an Operation for the Units
     *
     * @param s the clone of the UpnStack
     */ /*
    private void generateOp(Stack s) throws IOException {
        while (!s.isEmpty()) {
            if (s.peek() instanceof String) {
                units.push((String) s.pop());
            } else if (s.peek() instanceof MathValueReference) {
                MathValueReference tmp = (MathValueReference) s.pop();
                MathVariableDeclarationSymbol sym = tmp.getReferencedSymbol();
                units.push(sym.getName());
            } else {
                Operator op = (Operator) s.pop();
                generateOpUnits(units.pop(), units.pop(), op);
                s.push(units.pop());
            }
        }
    }

    /**
     * generates the code for an MathvalueReference in an Operation
     *
     * @param tmp the Reference
     */ /*
    private void generateOpMathValueRefernece(MathValueReference tmp) throws IOException {
        MathVariableDeclarationSymbol sym = tmp.getReferencedSymbol();
        //a forVariable is present
        if (sym.getMatrixProperties().contains("forVariable")) {
            names.push(sym.getName() + ".get(0,tmp" + sym.getName() + ")");
        }
        //a MatrixIndex is present
        else if (tmp.getMatrixIndex().isPresent()) {
            generateIndex(tmp);
        } else {
            names.push(sym.getName());
        }
    }

    /**
     * generates the code for an Operation Numbers/Logical signs
     *
     * @param name1 the code on the left side of the Operation
     * @param name2 the code on the right side of the Operation
     * @param op    the Operation
     */ /*
    private void generateOpNumbers(String name1, String name2, Operator op) throws IOException {
        String tmp;
        switch (op) {
            case Plus:
                tmp = name1 + ".plus(" + name2 + ")";
                names.push(tmp);
                break;
            case Minus:
                tmp = name1 + ".minus(" + name2 + ")";
                names.push(tmp);
                break;
            case Times:
                tmp = name1 + ".times(" + name2 + ")";
                names.push(tmp);
                break;
            case Div:
                tmp = name1 + ".divide(" + name2 + ")";
                names.push(tmp);
                break;
            case Slash:
                tmp = name1 + ".divide(" + name2 + ")";
                names.push(tmp);
                break;
            case Power:
                tmp = name1 + ".pow(" + name2 + ".intValue())";
                names.push(tmp);
                break;
            case Mod:
                tmp = "Rational.valueOf(" + name1 + ".intValue()%" + name2 + ".intValue(),1)";
                names.push(tmp);
                break;
            case PowerWise:
                tmp = "powerWise(" + name1 + "," + name2 + ")";
                names.push(tmp);
                break;
            case TimesWise:
                tmp = "timeWise(" + name1 + "," + name2 + ")";
                names.push(tmp);
                break;
            case SolEqu:
                tmp = name1 + ".solve(" + name2 + ")";
                names.push(tmp);
                break;
            case Trans:
                tmp = name1 + ".transpose()";
                names.push(tmp);
                break;
            case Assign:
                tmp = name1 + " = " + name2 + ";";
                bw.write(tmp);
                bw.newLine();
                names.push(tmp);
                break;
            case And:
                tmp = name1 + "&&" + name2;
                names.push(tmp);
                break;
            case Or:
                tmp = name1 + "||" + name2;
                names.push(tmp);
                break;
            case Equals:
                tmp = name1 + ".equals(" + name2 + ")";
                names.push(tmp);
                break;
            case Nequals:
                tmp = "!" + name1 + ".equals(" + name2 + ")";
                names.push(tmp);
                break;
            case Ge:
                tmp = name1 + ".isGreaterThan(" + name2 + ")";
                names.push(tmp);
                break;
            case Geq:
                tmp = name1 + ".plus(Rational.ONE).isGreaterThan(" + name2 + ")";
                names.push(tmp);
                break;
            case Le:
                tmp = name1 + ".isLessThan(" + name2 + ")";
                names.push(tmp);
                break;
            case Leq:
                tmp = name1 + ".minus(Rational.ONE).isLessThan(" + name2 + ")";
                names.push(tmp);
                break;

        }
    }

    /**
     * generates the code for an Operation for the Units
     *
     * @param name1 the code on the left side of the Operation
     * @param name2 the code on the right side of the Operation
     * @param op    the Operation
     */ /*
    private void generateOpUnits(String name1, String name2, Operator op) throws IOException {
        String tmp;
        switch (op) {
            case Plus:
                if (name2.startsWith("unit")) {
                    units.push(name2);
                } else {
                    units.push("unit" + name2);
                }
                break;
            case Minus:
                if (name2.startsWith("unit")) {
                    units.push(name2);
                } else {
                    units.push("unit" + name2);
                }
                break;
            case Times:
                if (name2.startsWith("unit")) {
                    tmp = name1 + ".times(" + name2 + ")";
                } else {
                    tmp = name1 + ".times(" + "unit" + name2 + ")";
                }
                units.push(tmp);
                break;
            case Div:
                if (name2.startsWith("unit")) {
                    tmp = name1 + ".divide(" + name2 + ")";
                } else {
                    tmp = name1 + ".divide(" + "unit" + name2 + ")";
                }
                units.push(tmp);
                break;
            case Slash:
                if (name2.startsWith("unit")) {
                    tmp = name1 + ".divide(" + name2 + ")";
                } else {
                    tmp = name1 + ".divide(" + "unit" + name2 + ")";
                }
                units.push(tmp);
                break;
            case Power:
                tmp = name1 + ".pow(" + name2 + ".intValue())";
                units.push(tmp);
                break;
            case Mod:
                if (name2.startsWith("unit")) {
                    units.push(name2);
                } else {
                    units.push("unit" + name2);
                }
                break;
            case PowerWise:
                tmp = name1 + ".pow(" + name2 + ".intValue())";
                units.push(tmp);
                break;
            case TimesWise:
                if (name2.startsWith("unit")) {
                    tmp = name1 + ".times(" + name2 + ")";
                } else {
                    tmp = name1 + ".times(" + "unit" + name2 + ")";
                }
                units.push(tmp);
                break;
            case SolEqu:
                if (name1.startsWith("unit")) {
                    tmp = name2 + ".divide(" + name1 + ")";
                } else {
                    tmp = name2 + ".divide(" + "unit" + name1 + ")";
                }
                units.push(tmp);
                break;
            case Trans:
                if (name1.startsWith("unit")) {
                    units.push(name1);
                } else {
                    units.push("unit" + name1);
                }
                break;
            case Assign:
                if (name2.startsWith("unit")) {
                    tmp = "unit" + name1 + " = " + name2 + ";";
                } else {
                    tmp = "unit" + name1 + " = " + "unit" + name2 + ";";
                }
                bw.write(tmp);
                bw.newLine();
                units.push(tmp);
                break;
        }
    }

    /**
     * generates the code for a Matrix
     *
     * @param name   if the Matrix is not just a Help variable it has a specific name
     * @param matrix the Matrix we want to generate the code for
     */ /*
    private void generateMatrix(JSMatrix matrix, Optional<String> name) throws IOException {
        int save = tmpCount;
        tmpCount++;
        bw.write("Rational[][] tmp" + save + " = new Rational[" + matrix.getRowDimension() + "][" + matrix.getColDimension() + "];\n");
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColDimension(); j++) {
                if (matrix.getElement(i, j) instanceof JSValue) {
                    bw.write("tmp" + save + "[" + i + "][" + j + "] = " + "Rational.valueOf(\"" + ((JSValue) matrix.getElement(i, j)).getRealNumber() + "\");\n");
                } else if (matrix.getElement(i, j) instanceof MathValueReference) {
                    generateMatrixHelpRef((MathValueReference) matrix.getElement(i, j), save, i, j);
                } else if (matrix.getElement(i, j) instanceof MathExpression) {
                    generateMatrixHelpExp((MathExpression) matrix.getElement(i, j), save, i, j);
                }
            }
        }
        generateMatrixHelpName(name, save);
        tmpCount++;
    }

    /**
     * generates the code for a Matrixenty which is an Reference
     *
     * @param save save of the tmpcounter
     * @param ref  the Reference in the MatrixEntry
     * @param i,j  the indecies of the Matrix entry
     */ /*
    private void generateMatrixHelpRef(MathValueReference ref, int save, int i, int j) throws IOException {
        if (ref.getMatrixIndex().isPresent()) {
            generateIndex(ref);
            bw.write("tmp" + save + "[" + i + "][" + j + "] = " + names.pop() + ";");
            bw.newLine();
        } else {
            bw.write("tmp" + save + "[" + i + "][" + j + "] = " + ref.getReferencedSymbol().getName() + ";");
            bw.newLine();
        }
    }

    /**
     * generates the code for a Matrix enty which is an Mathexpression
     *
     * @param save save of the tmpcounter
     * @param exp  the Reference in the MatrixEntry
     * @param i,j  the indecies of the Matrix entry
     */ /*
    private void generateMatrixHelpExp(MathExpression exp, int save, int i, int j) throws IOException {
        Stack s = (Stack) upnStack.clone();
        upnStack.clear();
        generateMathOrLogicalExpression(exp);
        generateOp(false);
        upnStack = s;
        bw.write("tmp" + save + "[" + i + "][" + j + "] = " + names.pop());
        bw.write(";\n");
    }

    /**
     * generates the code for the Matrix
     *
     * @param name Name if it is not a tmp variable
     * @param save the save of tmpcount
     */ /*
    private void generateMatrixHelpName(Optional<String> name, int save) throws IOException {
        if (name.isPresent()) {
            bw.write(name.get() + " = ");
            bw.write("DenseMatrix.valueOf(tmp" + save + ");");
            bw.newLine();
        } else {
            bw.write("Matrix<Rational> tmp" + (tmpCount) + " = DenseMatrix.valueOf(tmp" + save + ");");
            bw.newLine();
            upnStack.push("tmp" + tmpCount);
        }
    }

    /**
     * generates the code for a MathExpression or a LogicalExpression
     *
     * @param mathOrLogExpression the Expression we want to generate the code for
     */ /*
    private void generateMathOrLogicalExpression(MathValue mathOrLogExpression) throws IOException {
        Operator op;
        List<MathVariableDeclarationSymbol> operands;
        if (mathOrLogExpression instanceof MathExpression) {
            op = ((MathExpression) mathOrLogExpression).getOp();
            operands = ((MathExpression) mathOrLogExpression).getOperands();
        } else {
            op = ((LogicalExpression) mathOrLogExpression).getOp();
            operands = ((LogicalExpression) mathOrLogExpression).getOperands();
        }
        upnStack.push(op);
        //if we have a transpose operation we have to treat the case seperate (unary function)
        if (op.equals(Operator.Trans)) {
            generateTranspose(operands.get(0).getValue());
        } else {
            generateOperation(operands.get(0).getValue(), operands.get(1).getValue());
        }

    }

    /**
     * generates the code for the One operand of the transposeOperation
     *
     * @param value the only Operand
     */ /*
    private void generateTranspose(MathValue value) throws IOException {
        if (value instanceof JSMatrix) {
            generateMatrix((JSMatrix) value, Optional.empty());
            generateUnit("tmp" + (tmpCount - 1), UnitFormat.getUCUMInstance().format(value.getUnit()), false, true);
            upnStack.push("");
        } else if (value instanceof MathValueReference) {
            upnStack.push(value);
            upnStack.push("");
        } else if (value instanceof MathExpression) {
            generateMathOrLogicalExpression(value);
            upnStack.push("");
        }
    }

    /**
     * generates the code for the Two Operands of an Operation
     *
     * @param op1 the first Operand
     * @param op2 the second Operand
     */ /*
    private void generateOperation(MathValue op1, MathValue op2) throws IOException {
        if (op1 instanceof JSValue) {
            generateJSValue(((JSValue) op1).getRealNumber());
            generateUnit("tmp" + (tmpCount - 1), UnitFormat.getUCUMInstance().format(op1.getUnit()), false, true);
            generateOperation(op2);
        } else if (op1 instanceof JSMatrix) {
            generateMatrix((JSMatrix) op1, Optional.empty());
            generateUnit("tmp" + (tmpCount - 1), UnitFormat.getUCUMInstance().format(op1.getUnit()), false, true);
            generateOperation(op2);
        } else if (op1 instanceof MathExpression || op1 instanceof LogicalExpression) {
            generateMathOrLogicalExpression(op1);
            generateOperation(op2);
        } else if (op1 instanceof MathValueReference) {
            upnStack.push(op1);
            generateOperation(op2);
        }
    }

    /**
     * generates the code for the Second Operand
     *
     * @param op2 the second Operand
     */ /*
    private void generateOperation(MathValue op2) throws IOException {
        if (op2 instanceof JSValue) {
            generateJSValue(((JSValue) op2).getRealNumber());
            generateUnit("tmp" + (tmpCount - 1), UnitFormat.getUCUMInstance().format(op2.getUnit()), false, true);
        } else if (op2 instanceof JSMatrix) {
            generateMatrix((JSMatrix) op2, Optional.empty());
            generateUnit("tmp" + (tmpCount - 1), UnitFormat.getUCUMInstance().format(op2.getUnit()), false, true);
        } else if (op2 instanceof MathExpression || op2 instanceof LogicalExpression) {
            generateMathOrLogicalExpression(op2);
        } else if (op2 instanceof MathValueReference) {
            upnStack.push(op2);
        }
    }

    /**
     * generates the code for the Unit of a variable
     *
     * @param name1  the name of the variable we want to generate the unit for
     * @param name2  the name of the variable we want to get the unit from if isRef is true
     *               or the name of the Unit if isRef is false
     * @param isRef  flag if we get the Unit from another variable
     * @param isDecl flag if it is a Unitdeclaration
     */ /*
    private void generateUnit(String name1, String name2, boolean isRef, boolean isDecl) throws IOException {
        if (isDecl) {
            bw.write("Unit unit" + name1 + ";");
            bw.newLine();
        }
        if (isRef) {
            bw.write("unit" + name1 + " = " + "unit" + name2 + ";");
        } else {
            bw.write("unit" + name1 + " = " + "Unit.valueOf(\"" + name2 + "\");");
        }
        bw.newLine();
    }

    /**
     * generates the code for a JSValue
     *
     * @param number the number of the JSValue
     */ /*
    private void generateJSValue(Rational number) throws IOException {
        bw.write("Rational tmp" + tmpCount + " = Rational.valueOf(\"" + number + "\");");
        bw.newLine();
        upnStack.push("tmp" + tmpCount);
        tmpCount++;
    }

    /**
     * generates the code for the function TimeWise
     */ /*
    private void generateTimeWise() throws IOException {
        String tmp = "private static Matrix<Rational> timeWise(Matrix<Rational> r1,Matrix<Rational> r2){\n" +
                "Rational[][] res = new Rational[r1.getNumberOfRows()][r1.getNumberOfColumns()];\n" +
                "for(int i =0; i<r1.getNumberOfRows();i++){\n" +
                "for(int j=0; j<r1.getNumberOfColumns();j++){\n" +
                "res[i][j]= r1.get(i,j).times(r2.get(i,j));\n" +
                "}\n" +
                "}\n" +
                "return DenseMatrix.valueOf(res);\n" +
                "}\n";
        bw.write(tmp);
        bw.newLine();
    }

    /**
     * generates the code for the function PowerWise
     */ /*
    private void generatePowerWise() throws IOException {
        String tmp = "private static Matrix<Rational> powerWise(Matrix<Rational> r1,Rational r2){\n" +
                "Rational[][] res = new Rational[r1.getNumberOfRows()][r1.getNumberOfColumns()];\n" +
                "for(int i =0; i<r1.getNumberOfRows();i++){\n" +
                "for(int j=0; j<r1.getNumberOfColumns();j++){\n" +
                "res[i][j]= r1.get(i,j).pow(r2.intValue());\n" +
                "}\n" +
                "}\n" +
                "return DenseMatrix.valueOf(res);\n" +
                "}\n";
        bw.write(tmp);
        bw.newLine();
    }

    /**
     * generates the code for the function buildString
     */ /*
    private void generateBuildString() throws IOException{
        String tmp = "public void buildString(){\n"+
                "try {\n"+
                "PrintStream out = new PrintStream(new FileOutputStream(GENERATEDTARGETPATH + \"/expectedOutput/\" + this.getClass().getSimpleName() + \"Output\" + FILEENDING));\n"+
                "System.setOut(out);\n"+
                "System.out.println(\"package expectedOutput;\");\n"+
                "System.out.println(\"\\n\");\n"+
                "System.out.println(\"script \" + this.getClass().getSimpleName() + \"Output\");\n"+
                "for (String name: variableNames) {\n"+
                "System.out.println(\"\\t\" + this.buildStringHelp(name));\n"+
                "}\n"+
                "System.out.println(\"end\");\n"+
                "System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));\n"+
                "} catch (Exception e) {\n"+
                "System.out.println(\"Error: Variable \" + this.getClass().getSimpleName() + \" does not exist.\");\n"+
                "e.printStackTrace();\n"+
                "}\n"+
                "}\n";
        bw.write(tmp);
        bw.newLine();
    }

    /**
     * generates the code for the function buildStringHelp
     */ /*
    private void generateBuildStringHelp() throws IOException{
        String tmp =  "private String buildStringHelp(String name){\n"+
                "String unit = \"unit\" + name;\n"+
                "String output = \"\";\n"+
                "try {\n"+
                "Class clazz = this.getClass();\n"+
                "Field field = clazz.getDeclaredField(name);\n"+
                "if (field.get(this) instanceof Boolean){\n"+
                "output += \"B \" + field.getName() + \" = \";\n"+
                "if (((Boolean) field.get(this)).booleanValue() == true) {\n"+
                "output += \"1\";\n"+
                "}\n"+
                "else {\n"+
                "output += \"0\";\n"+
                "}\n"+
                "output += \";\";\n"+
                "}\n"+
                "else {\n"+
                "Field fieldUnit = clazz.getDeclaredField(unit);\n"+
                "if (field.get(this) instanceof Rational) {\n"+
                "output += \"Q \" + field.getName() + \" = \" + field.get(this).toString() + fieldUnit.get(this).toString() + \";\";\n"+
                "}\n"+
                "else if (field.get(this) instanceof Matrix) {\n"+
                "output += \"Q^{\" + ((Matrix) field.get(this)).getNumberOfRows() + \",\" + ((Matrix) field.get(this)).getNumberOfColumns() + \"} \" + field.getName() + \" = [\";\n"+
                "for (int i = 0; i < ((Matrix) field.get(this)).getNumberOfRows(); i++){\n"+
                "if (i != 0)\n"+
                "output += \";\";\n"+
                "for (int j = 0; j < ((Matrix) field.get(this)).getNumberOfColumns(); j++){\n"+
                "if (j == 0) {\n"+
                "output += ((Matrix) field.get(this)).get(i, j) + fieldUnit.get(this).toString();\n"+
                "}\n"+
                "else {\n"+
                "output += \",\" + ((Matrix) field.get(this)).get(i, j) + fieldUnit.get(this).toString();\n"+
                "}\n"+
                "}\n"+
                "}\n"+
                "output += \"];\";\n"+
                "}\n"+
                "output = output.replaceAll(\"²\",\"^2\");\n"+
                "output = output.replaceAll(\"·\",\"*\");\n"+
                "output = output.replaceAll(\"³\",\"^3\");\n"+
                "}\n"+
                "} catch (Exception e) {\n"+
                "System.out.println(\"Error: Variable \" + name + \" does not exist.\");\n"+
                "e.printStackTrace();\n"+
                "}\n"+
                "return output;\n"+
                "}\n";
        bw.write(tmp);
        bw.newLine();
    }
*/
    @Override
    protected MathCoCoChecker getChecker() {
        return MathCocos.createChecker();
    }
/*
    public static Class loadGeneratedClass(String name) throws Exception {

        File classFile = new File("./");
        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{

                new URL("file://" + GENERATEDCLASSTARGETPATH)

        }, URLClassLoader.getSystemClassLoader());

        urlClassLoader.clearAssertionStatus();

        Class classInstance = urlClassLoader.loadClass(BASEPACKAGE + "." + name);

        urlClassLoader.close();

        return classInstance;
    }

    public static Executable getInstanceOfGeneratedClass(String name) throws Exception {
        return (Executable) loadGeneratedClass(name).newInstance();
    }

    /**
     * Compiles the generated java file which is specified by name and returns whether compilation was successful or not.
     * @param name The name of the generated class which should be compiled
     * @return Whether compilation was successful or not
     */ /*
    public boolean compileGeneratedClass(String name) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            createCompilerResultPathIfNotExistent();

            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            //set class out path
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(GENERATEDCLASSTARGETPATH)));

            File fileToCompile = new File(GENERATEDTARGETPATH+ name + ".java");
            return compiler.getTask(null, fileManager, null, null, null, fileManager.getJavaFileObjects(fileToCompile)).call();


        } catch (Exception ex) {
            ex.printStackTrace();
            Log.info("Compilation Failed", this.getClass().getSimpleName());
        }
        return false;
    }

    private void createCompilerResultPathIfNotExistent() {
        File compilerResultPath = new File(GENERATEDCLASSTARGETPATH);
        if (!compilerResultPath.exists()) {
            compilerResultPath.mkdirs();
        }
    }*/
}
