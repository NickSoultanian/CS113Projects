package models;
/**
 * Polynomial.java : Will take terms and add them sequentially
 * to a linkedList to form a list whose terms, when combined form
 * a polynomial. Polynomial also can conduct addition, subtraction, multiplication and integer division(poly can not
 * contain variables) for two polynomials
 *
 * Class invariant: constructor inputs are not validated.
 *
 * @author Ian Ward
 * @version 1.0
 *
 */
import java.util.LinkedList;

public class Polynomial {
    private int numTerms = 0;
    private LinkedList<Term> listOfPolynomials = new LinkedList<Term>();
    /**
     *      default constructor
     */
    public Polynomial() {
    }
    /**
     * Constructor for building polynomials out of strings
     * @param input
     *            The string containing a polynomial that needs refining
     *            must consist of terms in for +ax^b linked by either plus or
     *            minus signs
     *            a 'B' char in an input string indicates the term needs to have its sign flipped
     */
    public Polynomial(String input) throws PostfixEvaluator.SyntaxErrorException{
        int startOfTerm = 0, last = input.length();
        String termToAddString;

        //separates the terms by checking for the +/- inbetween terms
        //and then adds each term to the linkedList
        for(int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == '+' | input.charAt(i) == '-') {
                termToAddString = input.substring(startOfTerm, i);
                if(termToAddString.contains("^") & !(termToAddString.contains("x^"))){
                    termToAddString = evalExponent(termToAddString);
                }
                Term termToAdd = new Term(termToAddString);
                this.addTerm(termToAdd);
                startOfTerm = i;
            }
        }
        //adds the last term to the polynomial
        String lastTerm = input.substring(startOfTerm, last);
        if(lastTerm.contains("^") & !(lastTerm.contains("x^"))){
            lastTerm = evalExponent(lastTerm);
        }
        Term termToAdd = new Term(lastTerm);
        this.addTerm(termToAdd);
    }
    /**
     * error checks exponents of a polynomial for syntax and converts polynomials such as 2^3 to their simplified form 2^3 = 8
     * @param input String: A polynomial in +ax^b form
     * @return String: returns a simplified polynomial(for example: "2^3" is returned as "8")
     */
    private String evalExponent(String input) throws PostfixEvaluator.SyntaxErrorException {
        int indexOfExponent = -1, exponent, coefficient, originalCoeff;
        //ensure exponent doesn't contain x
        if (input.contains("x")) {
            throw new PostfixEvaluator.SyntaxErrorException("no exponents with x^s allowed");
        }
        //find the index of'^'
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '^') {
                indexOfExponent = i;
            }
        }
        coefficient = Integer.valueOf(input.substring(1, indexOfExponent));
        originalCoeff = coefficient;
        //set the exponent
        try {
            exponent = Integer.valueOf(input.substring(indexOfExponent + 1, input.length()));
        }catch (Exception e){
            throw new PostfixEvaluator.SyntaxErrorException("negative exponents are not allowed for this calculator");
        }
        //conduct the reduction of an exponent until its something^1
        while (exponent > 1) {
            coefficient = coefficient * originalCoeff;
            exponent --;
        }
        return "" + input.charAt(0) + coefficient;
    }
    /**
     * Copy Constructor for creating a duplicate of this polynomial
     *
     * @param copy
     *            The polynomial whose list of terms should be
     *            made equivalent to the one contained in this class
     */
    public Polynomial(Polynomial copy){
        this();
        copy.numTerms = 0;
        for(int i = 0; i < numTerms; i++){
            copy.listOfPolynomials.add(this.listOfPolynomials.get(i));
        }
    }
    /**
     * accessor that gets a term contained in the polynomial
     * @param index
     *          an int the references the index the desired term
     *          is stored in the linked list
     * @return
     *      returns the stored term
     */
    public Term getTerm(int index){
        return listOfPolynomials.get(index);
    }
    /**
     * accessor that returns the number of terms in the polynomial
     * @return
     *            The number of polynomials
     */
    public int getNumTerms(){
        return numTerms;
    }
    /**
     * returns the list of polynomials
     * @return
     *            The linked list contaning polynomials
     */
    public LinkedList<Term> getListOfPolynomials() {
        return listOfPolynomials;
    }
    /**
     * can be used to replace the linked list of polynomials
     * @param listOfPolynomials
     *            linked list containing polynomials
     */
    public void setListOfPolynomials(LinkedList<Term> listOfPolynomials) {
        this.listOfPolynomials = listOfPolynomials;
    }

    /**
     * adds a new term to the linked list.
     * first checks to see if the list is empty, if so it simply adds the term
     * then ensures it is not adding a term whose exponent is already stored in the list
     * then ensures it is adding the term so that those terms with smaller exponents come later
     * in the list
     *
     * @param newTerm
     *            The term to be added to the linked list
     */
    public void addTerm(Term newTerm) {
        Term addedTerm = null;
        //checks if list is empty
        if (numTerms == 0) {
            listOfPolynomials.add(newTerm);
            numTerms++;
        }else {
            for(int i = 0; i < numTerms; i++){
                //ensures is is not adding a duplicate exponent
                if(newTerm.getExponent() == listOfPolynomials.get(i).getExponent()){
                    addedTerm = addTerms(newTerm, listOfPolynomials.get(i));
                    listOfPolynomials.set(i,addedTerm);
                    return;
                    //ensures the terms are ordered from largest exponent to smallest
                }else if(newTerm.getExponent() > listOfPolynomials.get(i).getExponent()) {
                    listOfPolynomials.add(i,newTerm);
                    numTerms++;
                    return;
                    //if smaller than all list members adds term to end of list
                }else if(i == numTerms -1){
                    listOfPolynomials.addLast(newTerm);
                    numTerms++;
                    return;
                }
            }
        }
    }
    /**
     * Takes two terms and adds them together
     * @param term1
     *            Term: the first term to be added
     * @param term2
     *            Term: the second term to be added
     * @return Term: the sum of the two added terms
     */
    public Term addTerms(Term term1, Term term2) {
        int newCoefficient = 0;
        newCoefficient = term1.getCoefficient() + term2.getCoefficient();
        Term addedTerm = new Term(newCoefficient, term2.getExponent());
        return addedTerm;
    }
    /**
     * adds this polynomial to another polynomial
     * by comparing the exponents of every term contained in
     * each polynomial and adding together those that match. Any
     * terms not already contained in this polynomial will be added to it.
     *Any any terms that have a coefficient of 0 are then removed from the polynomial
     *
     * @param poly2
     *            The polynomial to be added to this polynomial
     * @return
     *             this polynomial is returned in its new state.
     */
    public Polynomial add(Polynomial poly2){
        for(int i = 0 ; i < poly2.numTerms; i++){
            this.addTerm(poly2.getTerm(i));
            //removes any terms that summed to 0
            if(this.getTerm(i).getCoefficient() == 0){
                listOfPolynomials.remove(i);
                numTerms--;
            }
        }
        return this;
    }
    /**
     *Looks at all of the terms in a polynomial to see if the entire poly sums to zero
     * @param polyToSum
     *            Polynomial: The polynomial whose terms need to be evaluated for zero
     * @return boolean: true if the polynomial terms sum to zero
     */
    private boolean sumPoly(Polynomial polyToSum){
        Term initialTerm = polyToSum.getListOfPolynomials().get(0);
        //add each term in the polynomial
        for(int i = 1; i < polyToSum.getListOfPolynomials().size(); i++){
            initialTerm = addTerms(initialTerm, polyToSum.getListOfPolynomials().get(i));
        }
        if(initialTerm.getCoefficient() == 0){
            return true;
        }else{
            return false;
        }
    }
    /**
     *Conducts INTEGER DIVISION for two polynomials that do not contain variables
     * @param poly2
     *            Polynomial: The polynomial that is the divisor
     * @return Polynomial: The result of the division
     */
    public Polynomial divide(Polynomial poly2) throws ArithmeticException{
        if(poly2.toString().equals("0")){
            throw new ArithmeticException("No dividing by zero!");
        }
        int firstCoef = this.getTerm(0).getCoefficient();
        int secondCoef = poly2.getTerm(0).getCoefficient();
        //check that we are only doing division of a polynomial with only one term
        if(poly2.numTerms > 1 | this.numTerms > 1){
            throw new ArithmeticException("Sorry, we were lazy and didn't do polynomial division");
        }
        //make sure the Polynomial does not contain a variable
        else if(poly2.getTerm(0).isxPresent() == true | this.getTerm(0).isxPresent() == true){
            throw new ArithmeticException("Sorry, we were lazy and didn't do polynomial division");
        }
        //ensure we are not dividing by zero
        else if(sumPoly(poly2)){
            throw new ArithmeticException("Division by zero is not allowed on this calculator!");
        }else{
            this.getTerm(0).setCoefficient(firstCoef/secondCoef);
            return this;
        }
    }
    /**
     *Conducts subtraction for two polynomials by switching the signs of the poly to be subtracted and calling addition
     * @param poly2
     *            Polynomial: The polynomial that is to be subtracted
     * @return Polynomial: The result of the subtraction
     */
    public Polynomial subtract(Polynomial poly2) throws PostfixEvaluator.SyntaxErrorException{
        String poly2String = poly2.toString();
        //create a placeholder for all '+'
        String replace1 = poly2String.replace('+','z');
        //replace all '-'
        String replace2 = replace1.replace('-','+');
        //replace the placeholder with a '-'
        String replace3 = replace2.replace('z','-');
        Polynomial subtractionPoly = new Polynomial(replace3);
        return this.add(subtractionPoly);
    }
    /**
     *Conducts multiplication for two polynomials
     * @param poly2
     *            Polynomial: The polynomial that is to be multiplied by this
     * @return Polynomial: The result of the multiplication
     */
    public Polynomial multiply(Polynomial poly2) throws PostfixEvaluator.SyntaxErrorException{
        Term[] storeTerms = new Term[100];
        int count = 0;
        //handle multiplication by zero
        if(poly2.toString().equals("+0") | this.toString().equals("+0")){
            return new Polynomial("+0");
        }
        //expand out the polynomial and retrieve the coefficients and exponents
        for(int i = 0 ; i < this.numTerms; i++){
            int thisCoef = listOfPolynomials.get(i).getCoefficient();
            int thisExp = listOfPolynomials.get(i).getExponent();
            //for each term multiply the coefficients and save the resulting term in an array
            for(int j = 0; j < poly2.numTerms; j++){
                Term currentTerm = new Term(poly2.getListOfPolynomials().get(j).toString());
                currentTerm.setCoefficient(thisCoef * poly2.getListOfPolynomials().get(j).getCoefficient()) ;
                currentTerm.setExponent(thisExp + poly2.getListOfPolynomials().get(j).getExponent());
                storeTerms[count] = currentTerm;
                count++;
            }
        }
        //create a string from all of the new terms saved in the array
        String newPolyString = "";
        for(int i = 0; i < storeTerms.length; i++){
            if(storeTerms[i] != null){
                newPolyString = newPolyString + storeTerms[i];
            }
        }
        //create a new Polynomial to return from the String previously created
        Polynomial toReturn = new Polynomial(newPolyString);
        return toReturn;
    }
    /**
     * can be used to set the number of terms in the polynomial,
     * however the actual terms will not reflect the change
     * to this variable
     * @param numTerms   int: number of terms the polynomial will have
     */
    public void setNumTerms(int numTerms) {
        this.numTerms = numTerms;
    }
    /**
     * Converts this polynomial to a String of form +ax^b,+cx^d+...
     * it iterates through the linked list and adds each entry to
     * a string by calling the term's to String method
     * @return  the string value of the polynomial
     */
    public String toString(){
        String currentString = "";
        if(numTerms == 0){
            return "+0";
        } else if(numTerms == 1 & listOfPolynomials.get(0).toString().equals("+0")){
            return "+0";
        }else if(sumPoly(this)){
            return "+0";
        }
        for(int i = 0; i < numTerms; i++ ){
            if(listOfPolynomials.get(i).toString().equals("+0") ){
                continue;
            }else {
                currentString = currentString + (listOfPolynomials.get(i)).toString();
            }
        }
        return currentString;
    }
    /**
     * erases all terms in the linked list and resets
     * the number of terms in the polynomial to 0
     */
    public void clear(){
        numTerms = 0;
        listOfPolynomials = new LinkedList<Term>();
    }
    /**
     * Compares two objects to see if they are of the same
     * class and if they contain linked lists whose values all carry the same
     * value
     *
     * @param anObject
     *          an object to compare this polynomial with
     *
     * @return
     *          true means the two objects contain identical linked lists
     */
    public boolean equals(Object anObject){
        Term term1 = null;
        Term term2 = null;
        boolean truthState = false;
        if(anObject == null) {
            return false;
        }
        if(getClass() != anObject.getClass()) {
            return false;
        }
        Polynomial otherPoly = (Polynomial) anObject;
        if(otherPoly.listOfPolynomials.size() == listOfPolynomials.size()) {
            //compares all elements of the two linked lists
            for (int i = 0; i < listOfPolynomials.size(); i++) {
                term1 = listOfPolynomials.get(i);
                term2 = otherPoly.listOfPolynomials.get(i);
                if (term1 == term2) {
                    truthState = true;
                }
            }
        }
        return truthState;
    }
}