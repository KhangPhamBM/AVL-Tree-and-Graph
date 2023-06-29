package avltree;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Menu;
import util.Tool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PHAMKHANG
 */
public final class AVLTree {
    private static AVLTree INSTANCE = new AVLTree("avltreedata.txt");
    
    private Node root;

    public static  AVLTree getINSTANCE(){
        return INSTANCE;
    }
    
    private AVLTree(String filepath) {
        try {
            load_data(filepath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AVLTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private AVLTree(Node root) {
        this.root = root;
    }
    
    private boolean load_data(String filepath) throws FileNotFoundException {
        try {
            File f = new File(filepath);
            if (!f.exists()) {
                return false;
            }
            try (FileReader fr = new FileReader(f)) {
                try (BufferedReader br = new BufferedReader(fr)) {
                    String info;
                    while ((info = br.readLine()) != null) {
                        root = insert(root, Student_info.parseStudent(info));
                    }
                    br.close();
                }
                fr.close();
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
        return true;
    }
     
    private boolean write_date(String filepath) {
        if (root == null) {
            System.out.println("Empty list");
            return false;
        }
        try {
            File f = new File(filepath);
            try (FileWriter fw = new FileWriter(f); PrintWriter pw = new PrintWriter(fw)) {
                Stack<Node> q = new Stack<>();
                Node p;
                q.add(root);
                while(!q.isEmpty()){
                    p = q.pop();
                    pw.println(p.getData().toData());
                    if(p.getLeft() != null) q.add(p.getLeft());
                    if(p.getRight() != null) q.add(p.getRight());
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return true;
    } 
    
    private void search(){
        int inID = Tool.inputInteger("Enter ID to search", 1, 99);
        Node node  = search(inID);
        if(node != null) System.out.println(node.getData().toString());
        else System.out.println("Doesn't exist!");
    }
    
    private Node search(int key){
        return search(root, key);
    }
    
    private Node search(Node node, int key){
        if(node == null) return null;
        if(key == node.getData().getStudent_code()) return node;
        else if(key < node.getData().getStudent_code()) return search(node.left, key);
        else return search(node.right, key);
    }
    
    private Node searchPrev(int key){
        Node curr = root;
        if(curr.getData().getStudent_code() == key) return null;
        while(curr != null && curr.left != null && curr.right != null){
            if(curr.left.getData().getStudent_code() == key || 
               curr.right.getData().getStudent_code() == key) return curr;
            else if(curr.left.getData().getStudent_code() > key) curr = curr.right;
            else curr = curr.left;
        }
        return curr;
    }
    
    private int height(Node N) {
        if (N == null)
            return 0;
 
        return N.getHeight();
    }
 
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = calHeight(y);
        x.height = calHeight(x);
        return x;
    }
 
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = calHeight(x);
        y.height = calHeight(y);;
        return y;
    }
    
    private int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }
    
    private void insert(){
        Student_info inStu = Student_info.inputStudent();
        if(search(inStu.getStudent_code()) != null) System.out.println("Inputted student's ID exists!!");
        else insert(inStu);
    }
 
    private Node insert(Student_info stu){
        return insert(root, stu);
    }
    
    private Node insert(Node node, Student_info stu) {
        if (node == null)
            return (new Node(stu));
        if (stu.getStudent_code() < node.getData().getStudent_code())
            node.left = insert(node.left, stu);
        else if (stu.getStudent_code() > node.getData().getStudent_code())
            node.right = insert(node.right, stu);
        else return node;
        node.height = calHeight(node);
 
        int balance = getBalance(node);
        if (balance > 1 && stu.getStudent_code() < node.left.getData().getStudent_code())
            return rightRotate(node);
 
        if (balance < -1 && stu.getStudent_code() > node.right.getData().getStudent_code())
            return leftRotate(node);
 
        if (balance > 1 && stu.getStudent_code() > node.left.getData().getStudent_code()) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
 
        if (balance < -1 && stu.getStudent_code() < node.right.getData().getStudent_code()) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    
 
    private void delete(){
        int inID = Tool.inputInteger("Enter an ID to delete", 1, 99);
        if(search(inID) == null) System.out.println("There is no student having inputted ID!");
        delete(inID);
    }
    
    private  Node delete(int key){
        return deleteNode(root, key);
    }
    
    private Node deleteNode(Node root, int key){
        if (root == null)
            return root;
        
        if (key < root.getData().getStudent_code())
            root.left = deleteNode(root.left, key);
        else if (key > root.getData().getStudent_code())
            root.right = deleteNode(root.right, key);
        else
        {
            if ((root.left == null) || (root.right == null)){
                Node temp = null;
                
                if (temp == root.left) temp = root.right;
                else temp = root.left;
 
                if (temp == null){
                    temp = root;
                    root = null;
                }
                else root = temp; 
            }
            
            else {
                Node temp = minValueNode(root.right);
                root.setData(temp.getData());
                root.right = deleteNode(root.right, temp.getData().getStudent_code());
            }
        }
        if (root == null)
            return root;
        
        root.height = calHeight(root);
        int balance = getBalance(root);
        if (balance > 1 && getBalance(root.left) >= 0)
                   return rightRotate(root);
        if (balance > 1 && getBalance(root.left) < 0)
               {
                   root.left = leftRotate(root.left);
                   return rightRotate(root);
               }
        if (balance < -1 && getBalance(root.right) <= 0)
                   return leftRotate(root);
        if (balance < -1 && getBalance(root.right) > 0)
               {
                   root.right = rightRotate(root.right);
                   return leftRotate(root);
               }
 
        return root;
    }
    
    public Node dlete(Node node, int key){
        if(node == null) return null;
        if(node.getData().getStudent_code() > key) node.left = dlete(node.left, key);
        else if(node.getData().getStudent_code() < key) node.right = dlete(node.right, key);
        else{
            Node prev = searchPrev(node.getData().getStudent_code());
            //node is root
            if(prev == null){
                if(node.left == null && node.right == null) root = null;
                else if(node.left == null) node = node.right;
                else if(node.right == null) node = node.left;
                else{
                    Node right = node.right, leftmostright = getmostRight(node.left);
                    node = node.left;
                    leftmostright.right = right;
                }
            } else {
                //node is a leaf
                if(node.right == null && node.left == null){
                    if(node.equals(prev.left)) prev.left = null;
                    else prev.right = null;
                //node only has a left node
                } else if(node.right == null){
                    if(node.equals(prev.left)) prev.left = node.left;
                    else prev.right = node.left;
                //node only has a right node
                } else if(node.left == null){
                    if(node.equals(prev.left)) prev.left = node.right;
                    else prev.right = node.right;
                //node has both right and left node
                } else{
                    Node right = node.right, leftmostright = getmostRight(node.left);
                    node = node.left;
                    leftmostright.right = right;
                }
                }
            node.height = calHeight(node);
            int balance = getBalance(node);
            if (balance > 1 && getBalance(node.left) >= 0)
                       return rightRotate(node);
            if (balance > 1 && getBalance(node.left) < 0)
                   {
                       node.left = leftRotate(node.left);
                       return rightRotate(node);
                   }
            if (balance < -1 && getBalance(node.right) <= 0)
                       return leftRotate(node);
            if (balance < -1 && getBalance(node.right) > 0)
                   {
                       root.right = rightRotate(node.right);
                       return leftRotate(node);
                   }
        }
        return node;   
        }
    
    
    private Node getmostRight(Node node){
        Node mostright = node;
        if(node == null) return null;
        while(mostright.right != null)
            mostright = mostright.right;
        return mostright;
    }
    
    private Node minValueNode(Node node){
        Node curr = node;
        while (curr.left != null)
        curr = curr.left;
        return curr;
    }
    
    private void preOrder(){
        preOrder(root);
    }
    
    private void inOrder(){
        inOrder(root);
    }
    
    private void postOrder(){
        postOrder(root);
    }
 
    
    private void postOrder(Node t) {
        if (t == null) {
            return;
        }
        postOrder(t.left);
        postOrder(t.right);
        System.out.println(t.getData().toString());
    }

    private void inOrder(Node t) {
        if (t == null) {
            return;
        }
        inOrder(t.left);
        System.out.println(t.getData());
        inOrder(t.right);
    }

    private void preOrder(Node t) {
        if (t == null) {
            return;
        }
        System.out.println(t.getData());
        preOrder(t.left);
        preOrder(t.right);
    }

    private void breadthFirst(){
        if(root == null) return;
        Stack<Node> q = new Stack();
        Node curr;
        q.add(root);
        while(!q.isEmpty()){
            curr = q.pop();
            System.out.println(curr.getData().toString());
            if(curr.left != null) q.add(curr.left);
            if(curr.right != null) q.add(curr.right);
        }
    }
        
    private int count(Node t) {
        if (t == null) return 0;
        return count(t.left) + count(t.right) + 1;
    }

    private int calHeight(Node t) {
        if (t == null) return 0;
        return Math.max(height(t.left), height(t.right)) + 1;
    }
    
    public void function(){
        int choice, subChoice;
        do{
            choice =  Menu.int_getChoice(Menu.treeMenu);
            switch(choice){
                case 1:
                    insert();
                break;
                case 2:
                    search();
                break;
                case 3:
                    delete();
                break;
                case 4:
                   subChoice = Menu.int_getChoice(Menu.printMenu);
                    switch(subChoice){
                        case 1: preOrder(); break;
                        case 2: inOrder(); break;
                        case 3: postOrder(); break;
                        default:breadthFirst(); break;
                    }
                break;
                case 5:
                    write_date("avltreedata.txt");
                break;
                default:
                    System.out.println("Return...");
            }}while(choice > 0 && choice < Menu.treeMenu.length);
    }
    
    public static void main(String[] args) {
        AVLTree tree = new AVLTree("C:\\Users\\PHAMKHANG\\Documents\\NetBeansProjects\\CSD inplementation\\(CSD)Assignment2\\avltreedata.txt");
        tree.inOrder();
        tree.dlete(tree.root, 40);
        System.out.println("");
        tree.inOrder();       
    }
}
