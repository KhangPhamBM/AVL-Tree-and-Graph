/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Scanner;

/**
 *
 * @author PHAMKHANG
 */
public final class Tool {
    public static final Scanner SC = new Scanner(System.in);
    public static String SEPARATOR = ",";
    
    /**
     * Ask user to input a non-blank string
     * @return String
     */
    public static String inputString(String inputMessage, String errorMessage){
        String input = " ";
        do{
        if(input.isEmpty()) System.out.println(errorMessage);
        System.out.println(inputMessage + ": ");
        input = SC.nextLine().trim();
        } while(input.isEmpty());
        return input;
    };
    
    /**
     * Ask user to input a valid integer
     * @return int
     */
    public static int inputInteger(String message, int minValue, int maxValue) {
        int num = -1;
        do {
            System.out.print(message + ": ");
            try {
                String numString = SC.nextLine();
                if(numString.matches("[0-9]+"))
                num = Integer.parseInt(numString);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter the correct format!");
            }
        } while (num < minValue || maxValue < num || num == -1);
        return num;
    }   
    
    /**
     * Ask user to input a valid double
     * @return double
     */
    public static double inputDouble(String message, double minValue, double maxValue) {
        double num = -1;
        do {
            System.out.print(message + ": ");
            try {
                String numString = SC.nextLine();
                if(numString.matches("([0-9]*[.])?[0-9]+"))
                num = Double.parseDouble(numString);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter the correct format!");
            }
        } while (num < minValue || maxValue < num || num == -1);
        return num;
    }
    
    /**
     * Ask user to input a boolean value
     * @return value
     */
    public static boolean inputBoolean(String message, String errorMessage){
        String input = " ";
        do{
            if(input.isEmpty()) System.out.println(errorMessage);
            System.out.println(message + "[1/0-Y/N-T/F]: ");
            input = SC.nextLine().trim().toUpperCase();
        } while(input.isEmpty());
        char c = Character.toUpperCase(input.charAt(0));
        return (c == '1' || c == 'Y' || c == 'T');
    };    
    
    /** inputPassword is a function that asks user to enter a password.
     * 
     * @param message: Notify user to enter what type of content(In this case password)
     *@return String  
    */
    public static String inputPassword(String message){
        String input;
        boolean valid;
        do{
            System.out.println(message + ": ");
            input = SC.nextLine().trim();
            valid = validPassword(input, 8);
        }while(!valid);
        return input;
    }    
    
    /**
     * Ask user to input a non-blank string
     * @return String
     */
    public static String inputNonblankString(String message){
        String input;
        do{
        System.out.println(message + ": ");
        input = SC.nextLine();
        }while(input.isEmpty());
        return input;
    }
    
    /** validPassword is a function that checks whether a password is valid or not
     * @param str: password to valid
     * @param minLen: minimum length of a password
     *@return boolean
    */
    public static boolean validPassword(String str, int minLen){
        if(str.length() < minLen) return false;
        return str.matches(".*[a-zA-Z]+.*") && // AT LEAST 1 CHARATER
               str.matches(".*[\\d]+.*") && // AT LEAST 1 DIGIT
               str.matches(".*[\\W]+.*"); // AT LEAST 1 SPEACIAL CHARATER
    }
    
    /** valisString is a function checking whether a String matches a pattern or not
     *Use the method String.matches(regEx)
     * @param str: String to check
     * @param regex: the pattern used to check to str
     * @return Boolean
     */
    public static boolean validStr(String str, String regex){
        if(str != null && regex != null){
            return str.matches(regex);
        }
        return false;
    }
    
    /**
     * inputStringWithPattern is a function asking user to enter a non-blank String with specific pattern and return that String.
     * Any incorrect input is asked to re-enter until correct . 
     *
     * @param message: Notify user to enter what type of content(Example: ID, password)
     * @param pattern: the pattern that the inputted String must follow
     * @return String
     */ 
    public static String inputStringWithPattern(String message, String pattern){
        String input = "";
        boolean valid;
        do{
            System.out.println(message + ": ");
            input = SC.nextLine().trim();
            valid = validStr(input, pattern);
        } while(!valid);
        return input;
    }
  
    /** inputBoolean is a function that asks user to enter boolean value.
     * 
     * @param message: Notify user to enter what type of content(In this case TRue/False)
     *@return boolean  
    */
    public static boolean inputBoolean(String message){
        String input;
        System.out.println(message + "[1/0-Y/N-T/F]: ");
        input = SC.nextLine().trim();
        if(input.isEmpty()) return false;
        char c = Character.toUpperCase(input.charAt(0));
        return (c == '1' || c == 'Y' || c == 'T');
    }
    
    /** parseBool is a function that convert a String to Boolean value
     * 
     * @param boolStr: string to convert to boolean value
     *@return boolean  
    */
    public static boolean parseBool(String boolStr){
        char c = boolStr.trim().toUpperCase().charAt(0);
        return (c == '1' || c == 'Y' || c == 'T');
    }
    
}
