package models;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InfixToPostfixTest {
    //Test 1 with simple arithmetic.
    @Test
    public void testSimple(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("4 * 7") , "4 7 * ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }
    @Test
    public void testSimple2(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("4 * (7 + 2)"), "4 7 2 + * ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }
    @Test
    public void testSimple3(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("(4 * 7) - 20"), "4 7 * 20 - ");
        }catch(PostfixEvaluator.SyntaxErrorException e){

        }
    }
    @Test
    public void testSimple4(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("3 + ((4 * 7) / 2)"), "3 4 7 * 2 / + ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }



    //test2 with harder calculations, including variables.
    @Test
    public void testBigBoi(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("(3x + 3"), "3x 3 + ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }
    @Test
    public void testBigBoi2(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("3x^2 + (4x * 7)"), "3x^2 4x 7 * + ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }
    @Test
    public void testBigBoi3(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("3x^5 * 5x^3 + 6x"), "3x^5 5x^3 * 6x + ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }


    //test2 attempting both styles but with negative numbers.
    @Test
    public void testNegativeBoi(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("- 4 - 3"), "B4 3 - ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }
    @Test
    public void testNegativeBoi1(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("-3x^5 * 5x^3 + 6x"), "3x^5 5x^3 * - 6x + ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }
    @Test
    public void testNegativeBoi2(){
        try {
            PostFix trial = new PostFix();
            assertEquals("The output String is not valid", trial.convert("-6x^5 * -2x "), "6x^5 * - 2x - ");
        }catch(PostfixEvaluator.SyntaxErrorException e){
        }
    }

}
