package codegeneration;

import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {

    private static CodeGenerator instance = null;
    private FileWriter outputFile;
    private int labelCounter = 0;
    private String nameIn;

    private CodeGenerator(FileWriter outputFile, String nameIn) {
        this.outputFile = outputFile;
        this.nameIn = nameIn;
    }

    public static CodeGenerator getInstance(FileWriter outputFile, String nameOut) {
        if (instance == null) {
            instance = new CodeGenerator(outputFile, nameOut);
        }
        return instance;
    }

    public void writeCode(String code) {
        if (code.isBlank()) {return;}
        try {
            outputFile.write(code+"\n");
            outputFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String nextLabel() {
        return "label" + labelCounter++;
    }

    public void printSource(){
        try{
            outputFile.write("#source \"" + nameIn + "\"\n");
            outputFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ret(int bytesReturn, int bytesLocals, int bytesParams){
        try{
            outputFile.write("ret " + bytesReturn + " , " + bytesLocals + " , " + bytesParams + "\n");
            outputFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void line(int line){
        try{
            outputFile.write("#line " + line + "\n");
            outputFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pop(char suffix) {
        writeCode("pop" + suffix);
    }

    public void pushbp() {
        writeCode("push bp");
    }

    public void pushf(Double value) {
        writeCode("pushf " + value);
    }

    public void pushb(int value) {
        writeCode("pushb " + value);
    }

    public void pushi(int value) {
        writeCode("pushi " + value);
    }

    public void pusha(int value) {
        writeCode("pusha " + value);
    }

    public void add(char suffix) {
        writeCode("add" + suffix);
    }

    public void mul(char suffix) {
        writeCode("mul" + suffix);
    }

    public void sub(char suffix) {
        writeCode("sub" + suffix);
    }

    public void div(char suffix) {
        writeCode("div" + suffix);
    }

    public void modi() {
        writeCode("modi");
    }

    public void and() {
        writeCode("and");
    }

    public void or() {
        writeCode("or");
    }

    public void not() {
        writeCode("not");
    }

    public void lt(char suffix) {
        writeCode("lt" + suffix);
    }

    public void gt(char suffix) {
        writeCode("gt" + suffix);
    }
    public void le(char suffix) {
        writeCode("le" + suffix);
    }

    public void ge(char suffix) {
        writeCode("ge" + suffix);
    }

    public void eq(char suffix) {
        writeCode("eq" + suffix);
    }

    public void ne(char suffix) {
        writeCode("ne" + suffix);
    }

    public void call(String name) {
        writeCode("call " + name);
    }

    public void label(String label) {
        writeCode(label+":");
    }

    public void jz(String label) {
        writeCode("jz "+label);
    }

    public void jmp(String label) {
        writeCode("jmp "+label);
    }

    public void enter(int bytes) {
        writeCode("enter " + bytes);
    }

    public void variable(String type, String name, int offset) {
        writeCode("\t\t' * " + type + " " + name + " (offset " + offset + ")");
    }

    public void store(char suffix) {
        writeCode("store" + suffix);
    }

    public void load(char suffix) {
        writeCode("load" + suffix);
    }

    public void in(char suffix) {
        writeCode("in" + suffix);
    }

    public void out(char suffix) {
        writeCode("out" + suffix);
    }

    public void halt() {
        writeCode("halt");
    }
}
