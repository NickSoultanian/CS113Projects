package models;
/**
 * PostfixEvaluator.java : Will take in a postfix string and evaluate the mathematical
 * expression by calling the appropriate add/subtract/multiply/divide methods in the
 * Polynomial class
 *
 * @author Ian Ward
 * @version 1.0
 *
 */
import java.util.EmptyStackException;

public class PostfixEvaluator {
    private ArrayListStack<Polynomial> operandStack;
    private Polynomial answer = null;

    /**
     * String constructor evaluates a string and throws any unhandled syntax exceptions
     * @param stringToEval String: should be in postfix form else errors will be thrown
     */
    public PostfixEvaluator(String stringToEval) throws SyntaxErrorException{
        eval(stringToEval);
    }
    /**
     * Evaluates operands popped from the stack by popping the next two Polynomials from
     * the stack and then calling the appropriate add/sub/mult/divide method on them
     * @param op Char: the operand
     * @return Polynomial: The Polynomial object resulting from the math operation
     */
    private Polynomial evalOp(char op) throws SyntaxErrorException{
        Polynomial rhs = operandStack.pop();
        Polynomial lhs = operandStack.pop();
        Polynomial result = null;
        //catch division by zero
        if( rhs.toString().equals("+0") & op == '/'){
            throw new SyntaxErrorException("Please stop trying to divide by 0");
        }
        //call the appropriate method
        switch(op){
            case '+' : result = lhs.add(rhs);
                        break;
            case '-' : result = lhs.subtract(rhs);
                        break;
            case '/' : result = lhs.divide(rhs);
                        break;
            case '*' : result = lhs.multiply(rhs);
                        break;
            default: throw new SyntaxErrorException("Invalid operator") ;
        }
        return result;
    }
    /**
     * Loops through each token in the input string and converts each applicable token to a polynomial
     * and adds it to a stack. If an operand is found the evalOp method is called and the result is added to the stack.
     * This loop continues until the stack is empty or throws an error
     * @param expression String: A postfix string to be evaluated
     * @return String: The final result to be returned once all math operations have been conducted
     */
    public String eval(String expression) throws SyntaxErrorException{
        operandStack = new ArrayListStack<Polynomial>();
        String[] tokens = expression.split("\\s+");
        //ensure the minimum number of tokens is met
        if(tokens.length < 3){
            throw new SyntaxErrorException("there should be at least 3 tokens....Reminder: 3(4x) needs to be entered as 3*(4x)");
        }
        // loop through all the tokens
        try{
            for(String nextToken : tokens){
                char firstChar = nextToken.charAt(0);
                //catch operands
                if(!(Character.isDigit(firstChar)) & nextToken.length() == 1){
                    Polynomial result = evalOp(firstChar);
                    System.out.println("result"+result);
                    operandStack.push(result);
                    //evaluate non-operand tokens
                }else {
                    if(firstChar != '-'){
                        nextToken = "+" + nextToken;
                    }
                    Polynomial poly = new Polynomial(nextToken);
                    operandStack.push(poly);
                }
            }
            //return the final polynomial in string form
            answer = operandStack.pop();
            if (operandStack.empty()){
                return answer.toString();
            }else{
                throw new SyntaxErrorException("Invalid syntax! Reminder: 3(4x) needs to be entered as 3*(4x)");
            }
        }
        catch (EmptyStackException ex){
            throw new SyntaxErrorException("the stack is empty");
        }
    }
    @Override
    public String toString(){
        String withPlusSign = answer.toString();
        String withoutPlusSign;
        //ensure the sign (negative or positive is accurate)
        if(withPlusSign.charAt(0) == '+'){
            withoutPlusSign= withPlusSign.substring(1);
        }else{
            withoutPlusSign = answer.toString();
        }
        return withoutPlusSign;
    }

    @Override
    public boolean equals(Object o) {
        if(o.toString().equals(this.toString())){
            return true;
        }else{
            return false;
        }
    }
    public static  class SyntaxErrorException extends Exception{
        public SyntaxErrorException(String message){
            super(message);
        }
    }
}
