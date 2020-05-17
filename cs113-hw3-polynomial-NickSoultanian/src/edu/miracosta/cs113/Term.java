package edu.miracosta.cs113;

import java.util.Objects;

public class Term implements Comparable, Cloneable {
    private int coefficient;
    private int exponent;

    public Term(){
        this.coefficient = 0;
        this.exponent = 0;
    }
    public Term(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public Term(Term aTerm){
        this.coefficient = aTerm.getCoefficient();
        this.exponent = aTerm.getExponent();
    }

    public Term(String stringTerm){
        if(stringTerm.contains("^")) {
            int indexAt = 0;
            for (int i = 0; !(stringTerm.charAt(i) == 'x'); i++) {
                indexAt++;
            }
            this.coefficient = Integer.parseInt(stringTerm.substring(0, indexAt));
            this.exponent = Integer.parseInt(stringTerm.substring((indexAt + 2), stringTerm.length()));
        }else if(!stringTerm.contains("x")){
            this.exponent = 0;
            this.coefficient = Integer.parseInt(stringTerm);
        }else if(stringTerm.contains("x")){
            this.exponent = 1;

            int indexAt = 0;
            for (int i = 0; !(stringTerm.charAt(i) == 'x'); i++) {
                indexAt++;
            }
            this.coefficient = Integer.parseInt(stringTerm.substring(0, indexAt));
        }

    }
    public int getCoefficient(){
        return exponent;
    }

    public void setCoefficient(int coefficient){
        this.coefficient = coefficient;
    }

    public int getExponent(){
        return exponent;
    }

    public void setExponent(int exponent){
        this.exponent = exponent;
    }

    public void setAll(int coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    @Override
    public Term clone(){
        Term term;
        try{
            term =  (Term) super.clone();
        }
        catch(CloneNotSupportedException e){
            throw new Error();
        }
        return term;
    }



    @Override
    public String toString() {
        return "Term{" +
                "coefficient=" + coefficient +
                ", exponent=" + exponent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term)) return false;
        Term term = (Term) o;
        return getCoefficient() == term.getCoefficient() &&
                getExponent() == term.getExponent();
    }

    @Override
    public int compareTo(Object o) {
        Term aTerm = (Term) o;

        if(this.exponent == aTerm.getExponent()){
            return 0;
        }

        if(this.exponent < aTerm.getExponent()){
            return -1;
        }
        if(this.exponent > aTerm.getExponent()){
            return 1;
        }
        return -2;
    }
}
