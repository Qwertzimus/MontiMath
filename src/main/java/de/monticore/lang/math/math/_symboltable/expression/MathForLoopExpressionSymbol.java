package de.monticore.lang.math.math._symboltable.expression;

import de.monticore.lang.math.math._symboltable.MathForLoopHeadSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sascha Schneiders
 */
public class MathForLoopExpressionSymbol extends MathExpressionSymbol {

    protected MathForLoopHeadSymbol forLoopHead;

    protected List<MathExpressionSymbol> forLoopBody = new ArrayList<>();

    public MathForLoopExpressionSymbol() {
        super();
    }

    public MathForLoopHeadSymbol getForLoopHead() {
        return forLoopHead;
    }

    public void setForLoopHead(MathForLoopHeadSymbol forLoopHead) {
        this.forLoopHead = forLoopHead;
    }

    public List<MathExpressionSymbol> getForLoopBody() {
        return forLoopBody;
    }

    public void addForLoopBody(MathExpressionSymbol forLoopBody) {
        this.forLoopBody.add(forLoopBody);
    }

    @Override
    public String getTextualRepresentation() {
        return "for(" + forLoopHead.getNameLoopVariable() + "=" + forLoopHead.getMathExpression().getTextualRepresentation() + ")";
    }

    @Override
    public boolean isForLoopExpression() {
        return true;
    }
}
