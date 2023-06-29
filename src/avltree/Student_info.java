package avltree;


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
public class Student_info {
    int student_code;
    String name;
    String gender;
    int gpa_score;

    public Student_info() {
    }

    public Student_info(int student_code, String name, String gender, int gpa_score) {
        this.student_code = student_code;
        this.name = name;
        this.gender = gender;
        this.gpa_score = gpa_score;
    }

    public int getStudent_code() {
        return student_code;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getGpa_score() {
        return gpa_score;
    }

    public void setStudnet_code(int student_code) {
        this.student_code = student_code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGpa_score(int gpa_score) {
        this.gpa_score = gpa_score;
    }

    @Override
    public String toString() {
        return student_code + "," + name + "," + gender + "," + gpa_score ;
    }
    
    public String toData(){
        return student_code + " " + name + " " + gender + " " + gpa_score ;
    }
    
    public static Student_info parseStudent(String studentLine){
        String[] studentInfoArray = studentLine.split(" ");
        int inID = Integer.parseInt(studentInfoArray[0]);
        String inName = studentInfoArray[1];
        String inGender = studentInfoArray[2];
        int inGpa = Integer.parseInt(studentInfoArray[3]);
        return new Student_info(inID, inName, inGender, inGpa);    
    }
    
    public static Student_info inputStudent(){
        int id = inputID();
        String na = inputName();
        String ge  = inputGender();
        int gpa = inputGpa();
        return new Student_info(id, na, ge, gpa);
    }
    
    private static int inputID(){
        int inId = Tool.inputInteger("Enter ID", 0,99);
        return inId;
        
    }
    
    private static  String inputName(){
        String inName = Tool.inputNonblankString("Enter name");
        return inName;
    }
   
    private static  String inputGender(){
        String inName;
        do{
        inName= Tool.inputNonblankString("Enter gender");
        }while(!inName.equalsIgnoreCase("male") && !inName.equalsIgnoreCase("female"));
        return inName;
    }
    
    private static int inputGpa(){
        int inId = Tool.inputInteger("Enter gpa_score", 0,10);
        return inId;
    }
}
