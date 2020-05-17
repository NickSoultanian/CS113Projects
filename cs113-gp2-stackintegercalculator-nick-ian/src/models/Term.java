package models;

/**
 * Term.java : Will create a manipulate albagraic terms in the form +ax^b
 * so that they can be combined together to form a polynomial
 *
 * Class invariant: constructor inputs are not validated
 *                  terms with exponents but no variables such as 2^3 can not be evaluated by this class as I originally built this class
 *                  terribly and its easier to evaluate these expressions in a polynomial class
 *
 * @author Ian Ward
 * @version 1.0
 *
 */

public class Term implements Comparable<Term>{

    private String unfilteredTerm = null;
    private int exponent, coefficient;
    private boolean xPresent = false;

    /**
     * Constructor for building terms out of strings
     * @param input
     *            The string containing a term that needs refining
     */
    public Term(String input) throws PostfixEvaluator.SyntaxErrorException{
        //'B' indicates the term need to have its current sign flipped
        if(input.charAt(1) == 'B'){
            input = flipSign(input);
        }
        unfilteredTerm = input.toLowerCase();
        parse(unfilteredTerm);
    }
    /**
     * Default Constructor
     *            Creates a term with value 1^1
     */
    public Term(){
        exponent = 1;
        coefficient = 1;
    }
    /**
     * Flips the sign of a term from negative to positive or positive to negative and removes the 'B' that indicates a sign
     * change is necessary
     * @param toFlip
     *            The string containing a term that needs to have its sign changed
     * @return String: the altered Term
     */
    private String flipSign(String toFlip){
        char firstChar = toFlip.charAt(0);
        if(firstChar == '+'){
            toFlip = '-' + toFlip.substring(2,toFlip.length());
        }else{
            toFlip = '+' + toFlip.substring(2,toFlip.length());
        }
        return toFlip;
    }
    /**
     * Constructor for building terms out of strings
     * @param coefficient
     *            The number in front of the variable x
     * @param exponent
     *              The number after the character ^
     */
    public Term(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }
    /**
     * Copy Constructor: Shallow copies Term Object
     * @ param copy
     *       Term: the term to be copied
     */
    public Term(Term copy){
        this(copy.getCoefficient(), copy.getExponent());
    }
    /**
     * Mutator for coefficients and exponents
     * @param coefficient
     *            Integer value representing term's coefficient
     * @param exponent
     * Integer value representing term's exponent
     */
    public void setAll(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }
    /**
     * Mutator for term's coefficient
     * @param desiredCoefficient
     *            Integer value representing term's coefficient
     */
    public void setCoefficient(int desiredCoefficient){
        coefficient = desiredCoefficient;
    }
    /**
     * Accessor for term's coefficient
     * @return  coefficient
     *            Integer value representing term's coefficient
     */
    public int getCoefficient(){
        return coefficient;
    }
    /**
     * Mutator for term's exponent
     * @param exponent
     *            Integer value representing term's exponent
     * @param exponent
     * Integer value representing term's exponent
     */
    public void setExponent(int exponent) {
        this.exponent = exponent;
    }
    /**
     * Accessor for term's exponent
     * @return  exponent
     *            Integer value representing term's exponent
     */
    public int getExponent() {
        return exponent;
    }
    /**
     * Mutator for separating the math symbols of a term from its integer coefficients and exponents
     * when the two are combined in a single string
     * @param unfiltered
     *            A string containing the coefficients and exponents along with the mathematical operations
     *            of the term.
     */
    private void parse(String unfiltered) throws PostfixEvaluator.SyntaxErrorException {
        int lastx = -2;
        String filteredExponent;
        int exponentMarker = -1;
        xPresent = false;
        //catch 0 coefficient
        if(unfiltered.charAt(1) == '0'){
            coefficient = 0;
            exponent = 0;
            return;
        }
        //separates the exponent from the string
        for (int i = 0; i < unfiltered.length(); i++) {
            if (unfiltered.charAt(i) == '^') {
                exponentMarker = i + 1;
                filteredExponent = unfiltered.substring(exponentMarker, unfiltered.length());
                exponent = Integer.valueOf(filteredExponent);
            }
            //handles the case when there is no given exponent
            if (unfiltered.charAt(i) == 'x') {
                if(i - 1 == lastx){
                    throw new PostfixEvaluator.SyntaxErrorException("back to back x's are not allowed");
                }
                xPresent = true;
                unfilteredTerm = unfilteredTerm.substring(0, i);
                lastx = i;
            }
        }
        //sets the exponent for the power of 1
        if (xPresent == true & exponentMarker == -1) {
            exponent = 1;
        }
        //sets the exponent for the power of 0
        if (xPresent == false & exponentMarker == -1) {
            exponent = 0;
        }
        //handles cases with no given coefficient
        if (unfilteredTerm.length() == 1) {
            if (unfilteredTerm.charAt(0) == '+') {
                coefficient = 1;
            }
            if (unfilteredTerm.charAt(0) == '-') {
                coefficient = -1;
            }
        } else if (unfilteredTerm.charAt(1) == 'x' & unfilteredTerm.charAt(0) == '-') {
            coefficient = -1;
        } else if (unfilteredTerm.charAt(1) == 'x' & unfilteredTerm.charAt(0) == '+') {
            coefficient = 1;
            //converts the remaining coefficient from a string to an integer
        } else {
            coefficient = Integer.valueOf(unfilteredTerm);
        }
    }
    /**
     * Creates a new term that is a exact replica of this one
     * @return  deepClone
     *            a deep clone of the term
     */
    public Term clone(){
        Term deepClone = new Term(this.coefficient, this.exponent);
        deepClone.unfilteredTerm = this.unfilteredTerm;
        return deepClone;
    }
    /**
     * Compares two terms and returns true if they are the same
     *
     * @param  otherTerm
     *            term to be compared to this term
     * @return truth
     *          returns 0 if the terms are the same
     *          1 if this term has a larger exponent
     *          -1 if this term has a smaller exponent
     */
    public int compareTo(Term otherTerm) {
        int truth = -2;
        if (otherTerm.exponent == this.exponent) {
            truth = 0;
        } else if (otherTerm.exponent < this.exponent) {
            truth = 1;
        } else if (otherTerm.exponent > this.exponent) {
            truth = -1;
        }
        return truth;
    }
    /**
     * Converts this term to a String of form +ax^b
     * by first evaluating the coefficient and appending
     * an appropriate +x or -x as applicable. It then
     * appends the ^ for the newly expanded string
     *
     * @return  the string value of the term
     */
    public String toString(){
        String tempCoefficient = null;
        if(coefficient == 0 & exponent == 0){
            return "+0";
        }
        if(coefficient == 0 & exponent != 0){
            return "+0";
        }
        else if (coefficient > 1){
            tempCoefficient = "+" + coefficient + "x";
        }
        else if (coefficient == 1){
            tempCoefficient = "+x";
        }
        else if (coefficient == -1){
            tempCoefficient = "-x";
        }
        else if (coefficient < -1){
            tempCoefficient = coefficient + "x";
        }
        if (exponent == 0 & coefficient > 0){
            return "+" + coefficient;
        }
        else if(exponent == 0 & coefficient < 0){
            return "" + coefficient;
        }
        else if (-1 <= exponent && exponent <= 1){
            return tempCoefficient;
        }else{
            return tempCoefficient + "^" + exponent;
        }

    }
    /**
     * A placeholder for a string that was fed into the string constructor
     * to be utilized by the parse method
     * @return  the string value toLowerCase of the term fed to the String
     * constructor
     */
    public String getUnfilteredTerm() {
        return unfilteredTerm;
    }
    /**
     * A placeholder for a string that was fed into the string constructor
     * to be utilized by the parse method
     * @param unfilteredTerm
     *                 a new string to parse exponents and coefficients from
     */
    public void setUnfilteredTerm(String unfilteredTerm) {
        this.unfilteredTerm = unfilteredTerm;
    }
    /**
     * returns true if 'x' is present in this term, false otherwise
     * @return
     *         boolean: returns true if 'x' is present in this term
     */
    public boolean isxPresent() {
        return xPresent;
    }
    /**
     * sets the boolean xPresent
     * @param xPresent
     *         boolean: the value desired for xPresent
     */
    public void setxPresent(boolean xPresent) {
        this.xPresent = xPresent;
    }

    /**
     * Compares two objects to see if their exponents
     * and coefficients are the same
     * @param anObject
     *          an object to compare this term with
     * @return  true means the two objects have matching coefficients
     * and exponents
     */
    public boolean equals(Object anObject){
        if(anObject == null) {
            return false;
        }
        if(getClass() != anObject.getClass()) {
            return false;
        }
        Term otherTerm = (Term) anObject;
        if(otherTerm.coefficient == this.coefficient && otherTerm.exponent == this.exponent) {
            return true;
        } else{
            return false;
        }
    }
}