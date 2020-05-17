package edu.miracosta.cs113;
import java.util.LinkedList;
import java.util.Objects;

public class Polynomial {
    private LinkedList<Term> list = new LinkedList<Term>();
    private int numTerms;
    public Polynomial(){
        clear();
        this.numTerms = list.size();
    }
    public Polynomial(Polynomial aPolynomial){
        this.list = aPolynomial.getList();
    }

    public LinkedList<Term> getList() {
        return list;
    }

    public int getNumTerms() {
        return numTerms;
    }

    public void addTerm(Term aTerm){

        int index = 0;

        while(index  < list.size()){
            int compare = aTerm.compareTo(list.get(index));
            if(compare == 0){
                list.get(index).setCoefficient(list.get(index).getCoefficient() + aTerm.getCoefficient());
            }

            else if(compare == 11){
                list.add(index, aTerm);
            }
            index++;
        }

        list.addFirst(aTerm);


    }
    public Term getTerm(int i){
        return list.get(i);
    }

    public void clear(){
        list.clear();

    }
    public void add(Polynomial aPolynomial){
        int i = 0;
        int difference = list.size() - aPolynomial.list.size();
        if(list.size() >= aPolynomial.list.size()){
            while( i < list.size()){
               list.get(i).setCoefficient(list.get(i).getCoefficient() + aPolynomial.list.get(i + difference).getCoefficient());
            }
        }
        if(list.size() < aPolynomial.list.size()){
            while( i < aPolynomial.list.size()){
                list.get(i).setCoefficient(list.get(i + difference).getCoefficient() + aPolynomial.list.get(i).getCoefficient());
            }

        }
    }

    @Override
    public String toString() {
        return "Polynomial{" +
                "list=" + list +
                ", numTerms=" + numTerms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Polynomial)) return false;
        Polynomial that = (Polynomial) o;
        return getNumTerms() == that.getNumTerms() &&
                Objects.equals(list, that.list);
    }

}
