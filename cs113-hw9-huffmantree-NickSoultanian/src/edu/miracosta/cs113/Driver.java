package edu.miracosta.cs113;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Driver.java : Takes in a url and strips the text from the website. From there, the text file is scanned in order to
 * make a frequency table on how many times each ASCII character is used. this table is then used to build the Huffman
 * Tree which will encode the file into "Binary." Then the encoded file is stored and decoded back into text. After
 * that the bits are counted up as well as a compression number. And thus the assignment is completed!!
 *
 * @author Nick Soultanian
 * @version 1.0
 *
 */
public class Driver {
    private static final String url = "http://www.i-fuckyou.com";
    private static final String file = "OGFile";

    public static void main(String[] args){
        //Create Original Text File
        TextFileGenerator gen = new TextFileGenerator();
        try {
            gen.makeCleanFile(url, file);
        } catch(Exception e){
            System.out.println("oof");
            System.exit(0);
        }

        BufferedReader buff;

        ArrayList<HuffmanTree.HuffData> frequency = new ArrayList<HuffmanTree.HuffData>();
        int[] weight = new int[130];

        try{
            buff = new BufferedReader(new FileReader(new File(file)));
            int i;
            while((i = buff.read()) != -1){
                if(gen.withinWhiteList((char)i)){
                    weight[i] = weight[i] + 1;
                }
            }
            for(int j = 0; j < weight.length; j++) {
                if(weight[j] != 0){
                    frequency.add(new HuffmanTree.HuffData(weight[j], (char) j));
                }
            }
            buff.close();
        }catch(IOException e){
            System.out.println("big oof");
            System.exit(0);
        }

        HuffmanTree huff = new HuffmanTree();
        HuffmanTree.HuffData[] bro = new HuffmanTree.HuffData[frequency.size()];
        frequency.toArray(bro);
        //Print table
        System.out.println("Encoded table");
        System.out.println("---------------------------------------");
        for(int i = 0; i < bro.length; i++){
            System.out.print(i + "               "); System.out.println(frequency.get(i).getData());
        }
        //working
        //ENCODING----------------------------------------------------------------------------------------
        huff.buildTree(bro);

        System.out.println();
        System.out.println("Going into Encoding phase");

        try {
            huff.printCode(new PrintStream("newFile"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error writing to file.");
            System.exit(0);
        }

        String code = "";

        try {
            Scanner scan = new Scanner(new File(file));
            StringBuilder build = new StringBuilder();
            while ((scan.hasNext())) {
                build.append(scan.nextLine());
            }
            scan.close();
            code = getEncoded(build.toString());
        }catch(IOException e){
            System.exit(0);
        }

        System.out.println("File as Code: Please check the file called code. The string is so long it breaks my other prints ");

        //WRITE CODE TO FILE----------------------------------------------------------------------------
        try {
            PrintWriter print = new PrintWriter(new File("EncodedFile"));
            print.write(code);
            print.close();
        }
        catch(FileNotFoundException e){
            System.exit(0);
        }


        //Decoder-----------------------------------------------------------------------------------------
        try {
            BufferedReader read = new BufferedReader(new FileReader("newFile"));
            System.out.println();
            System.out.println("Going into Decoding phase");
            System.out.println(huff.decode(code));
            read.close();
        }catch(IOException e){
            System.out.println("mega oof");
            System.exit(0);
        }

        try {
            PrintWriter print = new PrintWriter(new File("DecodedFile"));
            print.write(huff.decode(code));
            print.close();
        }
        catch(FileNotFoundException e){
            System.exit(0);
        }

        //counting chars
        int decodedBits = gen.getNumChars("DecodedFile") * 4;
        int encodedBits = gen.getNumChars("EncodedFile");


        System.out.println();

        System.out.println("Counting Bits");
        System.out.print("Original:      ");
        System.out.println(decodedBits);
        System.out.print("Encoded:      ");
        System.out.println(encodedBits);
        System.out.println();
        System.out.println("Percentage of compression");
        System.out.println((double) decodedBits/encodedBits);


    }
    /**
     * takes in a String of the original file and the table built from the huffman tree are used to encode a string
     * of 1s and 0s
     *
     * @param text original text from file
     * @return String which represents fake binary of the original text file
     */
    public static String getEncoded(String text) {
        String[] codes = new String[256];
        try {
            Scanner scan = new Scanner(new FileInputStream("newFile"));
            while (scan.hasNext()) {
                String nextStr = scan.next();
                char nextChar;
                if (nextStr.startsWith("space")) {
                    nextChar = ' ';
                } else {
                    nextChar = nextStr.charAt(0);
                }
                String code = scan.next();
                int i = nextChar;
                codes[i] = code;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error reading from file.");
            System.exit(1);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(codes[text.charAt(i)]);

        }
        return sb.toString();
    }
}
