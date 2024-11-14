package evaluator.arith;

import language.Operand;
import language.Operator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import evaluator.PostfixEvaluator;

/** An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions. */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

  private final StackInterface<Operand<Integer>> stack;

  /** Constructs an {@link ArithPostfixEvaluator} */
  public ArithPostfixEvaluator() {
    // TODO Initialize to your LinkedStack
    stack = new LinkedStack<Operand<Integer>>();
  }

  /** {@inheritDoc} */
  @Override
  public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
    ArithPostfixParser parser = new ArithPostfixParser(expr);
    for (Token<Integer> token : parser) {
      Type type = token.getType();
      switch (type) {
        case OPERAND:
          // TODO What do we do when we see an operand?
          stack.push(token.getOperand());
          break;
        case OPERATOR:
          // TODO What do we do when we see an operator?
          Operator<Integer> op0 = token.getOperator();
          if (op0.getNumberOfArguments() == 1) {
            op0.setOperand(0, stack.pop());
          }
          else {
            op0.setOperand(1, stack.pop());
            op0.setOperand(0, stack.pop());
          }
          Operand<Integer> temp = op0.performOperation();
          stack.push(temp);
          break;
        default:
          throw new IllegalStateException("Parser returned an invalid Type: " + type);
      }
    }
    // TODO What do we return?
    Integer result = stack.pop().getValue();
    if (stack.isEmpty() == false) {
      throw new IllegalPostfixExpressionException();
    }
    return result;
  }
}
