package graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PHAMKHANG
 */
public class Relative {
    private char id;
    private int length;

    public Relative() {
    }

    public Relative(char id, int length) {
        this.id = id;
        this.length = length;
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    public static Relative parseRelative(String relativeString){
       String[] relativeArray  = relativeString.split(",");
        return new Relative(relativeArray[0].toCharArray()[0], Integer.parseInt(relativeArray[1]));
    }    
    @Override
    public String toString() {
        return "(" + id + "," + length + ")";
    }
   
}
