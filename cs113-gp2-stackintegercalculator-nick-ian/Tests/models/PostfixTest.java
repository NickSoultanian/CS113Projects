package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PostfixTest {

    @Test
    public void testValidInput(){
        try {
            PostfixEvaluator trial = new PostfixEvaluator("16x^2+4 -4x^1 + 4 -");
            assertEquals("The output String is not valid", trial.toString(), "16x^2-4x");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }

    @Test
    public void testInvalidNumberOfTokens(){
        try {
            //test less than 3 tokens
            new PostfixEvaluator("16x^2+4-4x -4");
            fail("A Syntax error should have been thrown");

        }catch(PostfixEvaluator.SyntaxErrorException e){
            /* Test Passed! */
        }
    }

    @Test
    public void testInvalidOperand(){
        try {
            //using ^ instead of an operand
            new PostfixEvaluator("16x^2+4-4x -4 ^");
            fail("A Syntax exception should have been thrown");
        }catch (PostfixEvaluator.SyntaxErrorException e){
            /* Test Passed! */
        }
    }

    @Test
    public void testExponentSyntax() {
        try {
            //multiple ^^ signs attempted
            new PostfixEvaluator("16x^^2+4-4x -4 +");
            fail("A NumberFormat exception should have been thrown");
        } catch (NumberFormatException e) {
            /* Test Passed! */
        }catch (PostfixEvaluator.SyntaxErrorException e){
            /* Test Passed! */
        }
    }

    @Test
    public void testInvalidExponent() {
        try {
            //invalid exponent
            new PostfixEvaluator("16x^x+4-4x -4 -");
            fail("A Syntax exception should have been thrown");
        } catch (NumberFormatException e) {
            /* Test Passed! */
        }catch (PostfixEvaluator.SyntaxErrorException e){
            /* Test Passed! */
        }
    }

    @Test
    public void testInvalidParenthesis() {
        try {
            //testing if a ( made it through the infix conversion
            new PostfixEvaluator("16x2+4(-4x) -4 -");
            fail("A Syntax exception should have been thrown");
        } catch (NumberFormatException e) {
            /* Test Passed! */
        }catch (PostfixEvaluator.SyntaxErrorException e){
            /* Test Passed! */
        }
    }
    @Test
    public void testMultipleVariables() {
        try {
            //multiple xx's tested
            new PostfixEvaluator("16xx+4-4x -4 -");
            fail("A Syntax exception should have been thrown");
        } catch (NumberFormatException e) {
            /* Test Passed! */
        }catch (PostfixEvaluator.SyntaxErrorException e){
            /* Test Passed! */
        }
    }
}
