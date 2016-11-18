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
import java.util.Hashtable;
import java.util.Scanner;


/**
 *
 * @author sozaki19
 */
public class Assembler {
    String instruction;
    Hashtable<String,String> destTable;
    int countLine = 0;
    int placeholder = 16;
    boolean doDebug = false; // this is to help debug, thanks to Nigel Ticknor
    
    public Assembler(String args) throws Exception{
        destTable = loading();
        String outPrefix = args.substring(0, args.indexOf("."));
        PrintStream out = new PrintStream(new File(outPrefix + ".hack"));
        parse(args,out);
    }
    
    private void parse(String args, PrintStream out) throws Exception{
        String filename = args;
        labeling(filename);
//        try{
            Scanner in = new Scanner(new File(filename));
            while(in.hasNextLine()){
                String line = in.nextLine();
                String spaceless = line.replaceAll("\\s", "");
                if(spaceless.startsWith("//") || spaceless.length()==0 || spaceless.startsWith("(")){
                  
                } else {
                    if(doDebug)System.out.println(spaceless);
                    assembly(spaceless);
                    out.println(instruction);
                }
            
            }
            
            
//        } catch(Exception e) {
//            System.out.println("I am sorry, but we cannot find the file you"
//                    + " are trying to access.");
//        }
    }
    
    private void labeling(String filename) throws Exception{
        try{
        Scanner file = new Scanner(new File(filename));
        while(file.hasNextLine()){
            String line = file.nextLine();
            String spaceless = line.replaceAll("\\s", "");
            if(spaceless.startsWith("//") || spaceless.length()==0){
                
            } else if(spaceless.startsWith("(")) {
                String name = spaceless.substring(spaceless.indexOf("(") + 1, spaceless.indexOf(")"));
                //System.out.println(name);
                int num = countLine;  // +1 to look at the next command, but not make the () count as one of the commands, but -1 because it starts from 0.
                String lineNum = "" + num; // convertion from http://stackoverflow.com/questions/4105331/how-do-i-convert-from-int-to-string
                destTable.put(name, lineNum);
                //System.out.println(lineNum);
            } else {
                countLine++;
            }
        }
        } catch(Exception e){
            System.out.println("The labeling search failed!");
        }
    }
    
    private void assembly(String line){
        String nline = line;
        if(line.contains("/")){
            nline = line.substring(0, line.indexOf("/"));
        }
        
        if(nline.startsWith("@")){
            aInstruction(nline);
        } else {
            cInstruction(nline);
        }
    }
    
    private void aInstruction(String aline){
        instruction = "0";
        String line = aline.substring(1, aline.length()); // gets rid of the @.
        if(doDebug)System.out.println(line + " this is the command for A- instruction");
//        System.out.println(destTable.get(line) + " is the location for "
//                + line + ".");
        /*System.out.println("Does destTable contain " + line +"?  "
        + destTable.containsKey(line) + ".");   This is used to test
        destTable.*/
        Boolean number = false;
        try{ // checks to see if this is a number or a string
            int numbering = Integer.parseInt(line);
            number = true;
        } catch(Exception e) {
            if(doDebug)System.out.println("That is not a number!!");
        }
        String addr = null;  // incase the line does not meet the if statement
        if(doDebug)System.out.println("Is the string in the destTable? " + destTable.containsKey(line));
        if(doDebug)System.out.println("Is the string a number? " + number);
        if(destTable.containsKey(line)){ // checking the table
            String location = destTable.get(line);
            int num = Integer.parseInt(location);
            addr = Integer.toBinaryString(num);
        } else if(number) {
            //int num = Integer.parseInt(line); // converting from string to int
            
            // converting from string to binary
            addr = Integer.toBinaryString(Integer.parseInt(line));
        } else {
            String place = placeholder + ""; // coverts the memory address to a string
            destTable.put(line,place); // stores the name and new memory.
            addr = Integer.toBinaryString(placeholder);
            placeholder++;
        }
        while(addr.length() != 15){
            addr = "0" + addr;
        }
        instruction = instruction + addr;
        if(doDebug)System.out.println(instruction + " is the binary location of " + line + ".");
    }
    
    private void cInstruction(String cline){
        instruction = "111";
        if(doDebug)System.out.println(cline + " this should not have //");
            
            // Doing computation
            comp(cline);
            // Doing the destination now
            if(cline.contains("=")){
                // destination will only looks at the left of the equals.
                destination(cline);
            } else {
                instruction = instruction + "000";
            }
            // When you give a value to a memory, you do not jump
            if(doDebug)System.out.println(cline.contains(";") + " does it contain ;?");
            if(cline.contains(";")){
                jump(cline);
            } else {
                instruction = instruction + "000";
            }
            if(doDebug)System.out.println(instruction + " this is the final instruction!");
        
    }
    
    private void destination(String line){
        String dest = line.substring(0, line.indexOf("="));
        if(doDebug)System.out.println(dest+" This should be the destination.");
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
        if(doDebug)System.out.println(instruction + " this has destination");
    }
    
    private void comp(String line){
        String compare = line;
            if(line.contains("=")){
                compare = line.substring(line.indexOf("=") + 1);
               if(doDebug) System.out.println(compare + " should not contain =");
            }
            if(line.contains(";")){ /* gets rid of the jump because this is
                comp not jump. */
                compare = compare.substring(0, compare.indexOf(";"));
            }
            if(doDebug)System.out.println(compare + " This is the thing that will be computed");
            if(compare.contains("M")){
                instruction = instruction + "1";
                comp1(compare);
                if(doDebug)System.out.println(instruction + "for if it contain M");
            } else {
                instruction = instruction + "0";
                comp2(compare);
                if(doDebug)System.out.println(instruction + "for if it does not contain M");
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
        String jum = line.substring(line.indexOf(";") + 1, line.length());
        if(doDebug)System.out.println(jum + " this should be just the jump");
        if(jum.equals("JGT")){
            instruction = instruction + "001";
        } else if(jum.equals("JEQ")){
            instruction = instruction + "010";
            
        } else if(jum.equals("JGE")){
            instruction = instruction + "011";
            
        } else if(jum.equals("JLT")){
            instruction = instruction + "100";
            
        } else if(jum.equals("JNE")){
            instruction = instruction + "101";
            
        } else if(jum.equals("JLE")){
            instruction = instruction + "110";
            
        } else if(jum.equals("JMP")){
            instruction = instruction + "111";
            
        }
    }
    
    private Hashtable loading(){
        destTable = new Hashtable<String, String>();
        destTable.put("SP", "0");
        destTable.put("LCL", "1");
        destTable.put("ARG", "2");
        destTable.put("THIS", "3");
        destTable.put("THAT", "4");
        destTable.put("R0", "0");
        destTable.put("R1", "1");
        destTable.put("R2", "2");
        destTable.put("R3", "3");
        destTable.put("R4", "4");
        destTable.put("R5", "5");
        destTable.put("R6", "6");
        destTable.put("R7", "7");
        destTable.put("R8", "8");
        destTable.put("R9", "9");
        destTable.put("R10", "10");
        destTable.put("R11", "11");
        destTable.put("R12", "12");
        destTable.put("R13", "13");
        destTable.put("R14", "14");
        destTable.put("R15", "15");
        destTable.put("SCREEN", "16384");
        destTable.put("KBD", "24576");
        return(destTable);
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
