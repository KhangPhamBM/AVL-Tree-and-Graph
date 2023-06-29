package avltree;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PHAMKHANG
 */
public class Node {
    Node left;
    Node right;
    Student_info data;
    int height;
    public Node(Student_info data) {
        setData(data);
        setLeft(null);
        setRight(null);
        setHeight(1);
    }

    public Node(Student_info data, Node left, Node right, int height) {
        setData(data);
        setLeft(left);
        setRight(right);
        setHeight(height);
   }

    

    public Node getLeft() {
        return left;
    }

    public final void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public final void setRight(Node right) {
        this.right = right;
    }

    public Student_info getData() {
        return data;
    }

    public final void setData(Student_info data) {
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public final void setHeight(int height) {
       this.height = height;
    }

   
    
    
    
    
}
