/********************************************************************
//  WordCounter.java       Author: Snubiss
//
//  Date: February 9, 2019
//  Modified: February 12, 2019
//
//  Defines the instance data, constructors and associated
//  methods of a WordCounter class object. The WordCounter class is
//  designed to take an input of an ArrayList of words and produce an
//  list sorted in descending order based on word occurrence.
//
//********************************************************************/

package thecrawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class WordCounter {
    
    ArrayList <WordObject> newList = new ArrayList();
    ArrayList <String> finishedList = new ArrayList();
    int listLength = 100;
    
    
    public WordCounter (ArrayList wordList){
        
        Set<String> unique = new HashSet<String>(wordList);
        
        for (String key : unique) {
            WordObject temp = new WordObject (key, Collections.frequency(wordList, key));
            newList.add(temp);
        }
        
        Collections.sort(newList, WordObject.WebpageCounter);
        
        if(newList.size() < 100){
            this.listLength = newList.size();
        }
        else{
            this.listLength = 100;
        }
        for (int j = 0; j < listLength; j++){
            finishedList.add(newList.get(j).getText()); 
        }
    }
    
    public ArrayList getCountedList(){
        return finishedList;
    }
}
