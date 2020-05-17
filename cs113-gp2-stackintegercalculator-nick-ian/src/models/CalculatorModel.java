/**
 * CalculatorModel.java : Concrete class using stack data structure to evaluate infix math expression
 *
 * TODO: This file given just to get code to compile (method stubbed).  Make sure to implement appropriately. [and remove this]
 *
 * @author Nery Chapeton-Lamas
 * @version 1.0
 *
 */

package models;

public class CalculatorModel implements CalculatorInterface {
    @Override
    public String evaluate(String expression) {
        try {
            PostFix fix = new PostFix();
            String convertedExpression = fix.convert(expression);
            PostfixEvaluator post1 = new PostfixEvaluator(convertedExpression);
            return post1.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
