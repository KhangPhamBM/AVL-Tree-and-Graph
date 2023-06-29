/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import avltree.AVLTree;
import graph.Graph;
import util.Menu;


/**
 *
 * @author PHAMKHANG
 */
public class Main {
    public static void main(String[] args) {
        int choice;
        do{
            choice = Menu.int_getChoice(Menu.menu);
            switch(choice){
                case 1:AVLTree.getINSTANCE().function();break;
                case 2:Graph.getINSTANCE().function();break;
                default:System.out.println("Program end!");
            }
        }while(choice >0 && choice <3);
    }
}
