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
    public Assembler(String args) throws Exception{
        String filename = args;
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
    }
    
    private void assembly(String line){
        if(line.startsWith("@")){
            aInstruction();
        } else {
            cInstruction();
        }
    }
    
    private void aInstruction(){
        
    }
    
    private void cInstruction(){
        
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
