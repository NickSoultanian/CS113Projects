package models;

import java.util.EmptyStackException;
import java.util.Scanner;

public class PostFix<E> {
    private static final String OPERATORS = "+-*/()";
    private static final String OPERATORS_MACH_2 = "+-/*";
    private static final int[] PRECEDENCE = {1,1,2,2,-1,-1};
    private StringBuilder postfix;
    private ArrayListStack<Character> operandStack = new ArrayListStack<Character>();

    /**
     * does nothing during instantiation.
     */
    public PostFix(){

    }

    /**
     * Convert a String from infix notation to postfix notation.
     * The very beginning of the method replaces '^' to 'c' to avoid the carrot getting deleted in string builder
     * then we handle possible negative numbers by using a 'B'
     * then we begin converting.
     * after the conversion 'b's and 'c's are converted back into what they need to be.
     * @param infix
     * @return newPostFix
     * @throws PostfixEvaluator.SyntaxErrorException
     */
    public String convert(String infix) throws PostfixEvaluator.SyntaxErrorException{
        infix = infix.replace("^", "c");
        if(infix.charAt(0) == '-' & !(infix.charAt(1) == '(')){
           infix = infix.replaceFirst("- ", "B");

        }else if(infix.charAt(0) == '-' & infix.charAt(1) == '('){
            infix = infix.replaceFirst("-", "B");

        }
        else if(infix.contains("(-")){
            infix = infix.replace("(- ", "(B");
        }

        int j = 0;
        for(int i = 0; i < infix.length() - 1; i++){
            if(OPERATORS_MACH_2 .contains("" + infix.charAt(i))){
                j = i;
                if(OPERATORS_MACH_2 .contains("" + infix.charAt(j+2))){
                    infix = infix.replace("" + infix.charAt(i) + " " +infix.charAt(j+2) + " ", infix.charAt(i)+ " " + "B");
                }
            }
        }

        postfix = new StringBuilder();
        try{
            String nextToken;
            Scanner scan = new Scanner(infix);
            while((nextToken = scan.findInLine("[\\p{L}\\p{N}]+|[-+/\\*()]")) != null) {
                char firstChar = nextToken.charAt(0);
                if (Character.isJavaIdentifierStart(firstChar) || Character.isDigit(firstChar)) {
                    postfix.append(nextToken);
                    postfix.append(' ');
                } else if (isOperator(firstChar)) {
                    processOperator(firstChar);
                } else {
                   // throw new PostfixEvaluator.SyntaxErrorException("Unexpected Character Encountered: " + firstChar);
                }
            }
            while(!operandStack.empty()){
                char op = operandStack.pop();
                if (op == '(') { throw new PostfixEvaluator.SyntaxErrorException("Unmatched opening parenthesis"); }

                postfix.append(op);
                postfix.append(' ');

            }
            String newPostFix = postfix.toString();
            newPostFix = newPostFix.replace('c', '^');

            return newPostFix;
        } catch(EmptyStackException ex){
            throw new PostfixEvaluator.SyntaxErrorException("SyntaxError: The stack is empty");
        }
    }

    /**
     * Method to process operators
     * @param op
     * @throws EmptyStackException
     */
    private void processOperator(char op){
        if(operandStack.empty() || op == '(') {
            operandStack.push(op);
        } else {
            char topOp= operandStack.peek();
            if (precedence(op) > precedence(topOp)){
                operandStack.push(op);
            }
            else {
                while(!operandStack.empty() && precedence(op) <= precedence(topOp)){
                    operandStack.pop();
                    if(topOp == '(') {
                        break;
                    }
                    postfix.append(topOp);
                    postfix.append(' ');
                    if(!operandStack.empty()){
                        topOp = operandStack.peek();
                    }
                }
                if(op != ')'){
                    operandStack.push(op);
                }
            }
        }

    }

    /**
     * Finds out the precedence
     * @param op
     * @return int
     */
    private int precedence(char op){
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }

    /**
     * finds out if char is operator
     * @param ch
     * @return boolean
     */
    private boolean isOperator(char ch){
        return OPERATORS.indexOf(ch) != -1;
    }

}
