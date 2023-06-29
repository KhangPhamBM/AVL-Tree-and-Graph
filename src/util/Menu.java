/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import util.Tool;

/**
 *
 * @author PHAMKHANG
 */
public class Menu {
    private int choice;
    private Menu(){};
    private static Menu INSTANCE = new Menu();
    public static final String[] treeMenu = new String[]{"Insert","search",
                                                "Delete", "Print all",
                                                "Save to file", "Return"};
    public static final String[] printMenu = new String[]{"DepdthFirst - Preorder","DepdthFirst - Inorder",
                                                "DepdthFirst - Postorder", "BreadthFirst", "Return"};
    public static final String[] graphMenu = new String[]{"Print all","Compute degree",
                                                "Dijkstra", "Floyd",
                                                "Prim", "Kruskal","Euler", "Save to file", "Return"};
    public static final String[] graphPrintNmnu = new String[]{"dfs", "bfs","Return"};
    public static final String[] menu = new String[]{"AVL Tree", "Graph","End program"};
    
    /**
     * Print out a list of choice and get user choice
     * @return int
     */
    public static Menu getINSTANCE(){
        return INSTANCE;
    }   

    public static String[] getTreeMenu() {
        return treeMenu;
    }

    public static String[] getPrintMenu() {
        return printMenu;
    }

    public static String[] getGraphMenu() {
        return graphMenu;
    }

    public static String[] getMenu() {
        return menu;
    }
    
    
    
    public static int int_getChoice(String[] options){
    for (int i = 0;i < options.length; i++){
        System.out.println((i + 1) + " - " + options[i]);
    }
      return Tool.inputInteger("Enter your choice", 1, options.length);
    }  
    
}
