/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutputArranger {
    public static void main(String[] args) throws IOException {
        Node finalNodes[] = new Node[50*7];
        int size = 0;
        String str1[] = new String[7];
        for ( int l = 0; l <= 6; l++){
            str1[l] = readFile("outputs/part-0000" + Integer.toString(l), StandardCharsets.UTF_8);
            System.out.println("outputs/part-0000" + Integer.toString(l));
            String tokens[] = str1[l].split("'");
            Node nodes[] = new Node[tokens.length];
            String words[] = new String[tokens.length];
            Integer counts[] = new Integer[tokens.length];
            for( int i = 1; i < tokens.length; i++){
                nodes[i] = new Node(tokens[i].split(",")[0], Integer.parseInt(tokens[i].split(",")[1]));
                words[i] = tokens[i].split(",")[0];
                counts[i] = Integer.parseInt(tokens[i].split(",")[1]);
            }
            for (int i = 0; i < nodes.length - 2; i++) {
                int index = i;
                for (int j = i + 1; j < nodes.length; ++j) {
                    try {
                        if (nodes[j].count < nodes[index].count)
                            index = j;
                        int smallerNumber = nodes[index].count; 
                        nodes[index].count = nodes[i].count;
                        nodes[i].count = smallerNumber;
                        String tempWord = nodes[index].word; 
                        nodes[index].word = nodes[i].word;
                        nodes[i].word = tempWord;
                    } catch (NullPointerException e ) {}
                }
            }
            PrintWriter writer = new PrintWriter("outputs/part-0000" + Integer.toString(l) + ".txt", "UTF-8");
            for( int i = tokens.length-1; i > 0; i--) {
                if ( i < (tokens.length)-50)
                    break;
                writer.println(nodes[i-1].word + ": " + Integer.toString(nodes[i-1].count) + "\n");
                finalNodes[tokens.length-1-(i-size)] = nodes[i-1];
            }
            writer.close();
            size = size + 50;
        }
        for (int i = 0; i < finalNodes.length - 2; i++) {
            int index = i;
            for (int j = i + 1; j < finalNodes.length; ++j) {
                try {
                    if (finalNodes[j].count < finalNodes[index].count)
                        index = j;
                    int smallerNumber = finalNodes[index].count; 
                    finalNodes[index].count = finalNodes[i].count;
                    finalNodes[i].count = smallerNumber;
                    String tempWord = finalNodes[index].word; 
                    finalNodes[index].word = finalNodes[i].word;
                    finalNodes[i].word = tempWord;
                } catch (NullPointerException e ) {}
            }
        }
        PrintWriter writer = new PrintWriter("outputs/finalOutput.txt", "UTF-8");
        for( int i = finalNodes.length-1; i > 0; i--) {
            if ( i < (finalNodes.length)-349)
                break;
            writer.println(finalNodes[i-1].word + ": " + Integer.toString(finalNodes[i-1].count) + "\n");
        }
        writer.close();
    }
    
    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}

class Node{
    String word;
    int count;
    
    public Node(){}    
    public Node( String word, int count) {
        this.word = word;
        this.count = count;
    }
}