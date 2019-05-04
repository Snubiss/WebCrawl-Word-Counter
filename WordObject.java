/********************************************************************
//  WordObject.java       Author: Snubiss
//
//  Date: February 9, 2019
//  Modified: February 13, 2019
//
//  Defines the instance data, constructors and associated
//  methods of a WordObject class object. The WordObject class is
//  designed to make the java collections library more flexible when
//  counting words. This should not be altered!
//
//********************************************************************/

package thecrawler;

import java.util.Comparator;


public class WordObject {
    
    private String word;
    private int occurence;
    
    public WordObject(String word, int counted){
    
        this.word = word;
        this.occurence = counted;
    }
    
    public String getText(){
        return this.word + " " + this.occurence;
    }
    
    public static Comparator<WordObject> WebpageCounter = new Comparator<WordObject>() {
        
        @Override
        public int compare(WordObject w1, WordObject w2){
            
            int num1 = w1.occurence;
            int num2 = w2.occurence;
            
            return num2-num1;
        }
    };
}
