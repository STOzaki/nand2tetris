/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.Scanner;


/**
 *
 * @author sozaki19
 */
public class Assembler {
    String instruction;
    public Assembler(String args) throws Exception{
        parse(args);
    }
    
    private void parse(String args) throws Exception{
        String filename = args;
        try{
            Scanner in = new Scanner(new File(filename));
            while(in.hasNextLine()){
                String line = in.nextLine();
                String spaceless = line.replaceAll("\\s", "");
                if(spaceless.startsWith("//") || spaceless.length()==0){
                  
                } else {
                    System.out.println(spaceless);
                    assembly(spaceless);
                }
            
            }
        } catch(Exception e) {
            System.out.println("I am sorry, but we cannot find the file you"
                    + " are trying to access.");
        }
    }
    
    private void assembly(String line){
        if(line.startsWith("@")){
            aInstruction(line);
        } else {
            cInstruction(line);
        }
    }
    
    private void aInstruction(String aline){
        instruction = "0";
        System.out.println(instruction);
        System.out.println("I will do this later"); // Continue this later!!!!
        System.out.println("");
    }
    
    private void cInstruction(String cline){
        instruction = "111";
            // Doing computation
            comp(cline);
            // Doing the destination now
            destination(cline);
            // When you give a value to a memory, you do not jump
            jump(cline);
            instruction = instruction + "000";
            System.out.println(instruction + " this is the final instruction!");
        
    }
    
    private void destination(String line){
        String dest = line.substring(0, line.indexOf("="));
        System.out.println(dest+" This should be the destination.");
        String d1;
        String d2;
        String d3;
        if(dest.contains("A")){
            d1 = "1";
        } else {
            d1 = "0";
        }
        if(dest.contains("D")){
            d2 = "1";
        } else {
            d2 = "0";
        }
        if(dest.contains("M")){
            d3 = "1";
        } else {
            d3 = "0";
        }
        instruction = instruction + d1 + d2 + d3;
    }
    
    private void comp(String line){
        String compare;
            /* This may not be necessary
        int stop = line.indexOf("/"); // finds where the comment starts.
        int start = line.indexOf("="); // finds where to start.
            */
            if(line.contains("/")){
                compare = line.substring(line.indexOf("=") + 1, line.indexOf("/")); /* because it needs
            start a string after the = sign. */
            } else {
                compare = line.substring(line.indexOf("=") + 1);
            }
            if(line.contains(";")){
                compare = compare.substring(0, compare.indexOf(";"));
            }
            System.out.println(compare + " This is the thing that will be computed");
            if(compare.contains("M")){
                instruction = instruction + "1";
                comp1(compare);
                System.out.println(instruction + "for if it contain M");
            } else {
                instruction = instruction + "0";
                comp2(compare);
                System.out.println(instruction + "for if it does not contain M");
            }
        
    }
    
    private void comp1(String compare){
        if(compare.equals("M")){
            instruction = instruction + "110000";
            
        } else if(compare.equals("!M")){
            instruction = instruction + "110001";
            
        } else if(compare.equals("-M")){
            instruction = instruction + "110011";
            
        } else if(compare.equals("M+1")){
            instruction = instruction + "110111";
            
        } else if(compare.equals("M-1")){
            instruction = instruction + "110010";
            
        } else if(compare.equals("D+M")){
            instruction = instruction + "000010";
            
        } else if(compare.equals("D-M")){
            instruction = instruction + "010011";
            
        } else if(compare.equals("M-D")){
            instruction = instruction + "000111";
            
        } else if(compare.equals("D&M")){
            instruction = instruction + "000000";
            
        } else if(compare.equals("D|M")){
            instruction = instruction + "010101";
            
        } else {
            System.out.println("ERROR, I do not reconize this computation"
                    + " when a = 1, means that we are dealing with M.");
        }
    }
    
    private void comp2(String compare){
        if(compare.equals("0")){
            instruction = instruction + "101010";
        } else if(compare.equals("1")){
            instruction = instruction + "111111";
            
        } else if(compare.equals("-1")){
            instruction = instruction + "111010";
            
        } else if(compare.equals("D")){
            instruction = instruction + "001100";
            
        } else if(compare.equals("A")){
            instruction = instruction + "110000";
            
        } else if(compare.equals("!D")){
            instruction = instruction + "001101";
            
        } else if(compare.equals("!A")){
            instruction = instruction + "110001";
            
        } else if(compare.equals("-D")){
            instruction = instruction + "001111";
            
        } else if(compare.equals("-A")){
            instruction = instruction + "110011";
            
        } else if(compare.equals("D+1")){
            instruction = instruction + "011111";
            
        } else if(compare.equals("A+1")){
            instruction = instruction + "110111";
            
        } else if(compare.equals("D-1")){
            instruction = instruction + "001110";
            
        } else if(compare.equals("A-1")){
            instruction = instruction + "110010";
            
        } else if(compare.equals("D+A")){
            instruction = instruction + "000010";
            
        } else if(compare.equals("D-A")){
            instruction = instruction + "010011";
            
        } else if(compare.equals("A-D")){
            instruction = instruction + "000111";
            
        } else if(compare.equals("D&A")){
            instruction = instruction + "000000";
            
        } else if(compare.equals("D|A")){
            instruction = instruction + "010101";
            
        } else {
            System.out.println("ERROR, I do not reconize this computation"
                    + " when a = 1, means that we are dealing with M.");
        }
    }
    private void jump(String line){
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Assembler assem = new Assembler(args[0]);
            
            /*else if(!(in.hasNext())) {
                System.out.println("Deleted");
            } else {
                System.out.println(spaceless);
            } */
        } 
    }
