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
public class Edge {
    public char start;
    public char end;
    public int distance;

    public Edge(char start, char end, int distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public char getStart() {
        return start;
    }

    public void setStart(char start) {
        this.start = start;
    }

    public char getEnd() {
        return end;
    }

    public void setEnd(char end) {
        this.end = end;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "(" + start + "," + end + "," + distance + ')';
    }
    
    
}
