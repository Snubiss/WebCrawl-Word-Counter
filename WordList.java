/********************************************************************
//  WordList.java       Author: Snubiss
//
//  Date: February 9, 2019
//  Modified: February 13, 2019
//
//  Defines the instance data, constructors and associated
//  methods of a WordList class object. The WordList class is
//  designed to take input of an ArrayList of URL objects and produce a list of
//  words associated with each URL's page.
//
//********************************************************************/

package thecrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;


public class WordList {
    
    ArrayList <String> wordList = new ArrayList<>();
    ArrayList <String> uniqueWordList = new ArrayList<>();
    ArrayList <String> whiteList = new ArrayList<>();
    // How long we should scan each page for words. - Set higher for deeper scan.
    long scanTime = 20;
    
    public WordList(ArrayList linkList, long timeLength) throws IOException{
        
        
        System.out.println("\nScraping all links found for words...");
        getWords(linkList);
        
        // Check our words against words we declared illegal.
        runWhiteList();
        
        // Create a set to weed out the duplicates.
        Set<String> set = new HashSet<>(this.wordList);
        
        // Clear our unique array list.
        this.uniqueWordList.clear();
        
        // Populate our original list with unique URLs.
        this.uniqueWordList.addAll(set);
        this.uniqueWordList.trimToSize();
        
        // Let the user know what is going on.
        System.out.println("\nWordlist created successfully!");
        System.out.println(wordList.size() + " words were found");
        System.out.println(uniqueWordList.size() + " unique words were found\n\n");
    }
    
    public void setScanTime(long data){
        scanTime = data;
    }
    public ArrayList getWordList(){
        return this.wordList;
    }
    
    public ArrayList getUniqueWordList(){
        return this.uniqueWordList;
    }
    
    private void getWords(ArrayList linkList) throws IOException{
        
        URL url = null;
        // Create a point in time to stop at.
        long stopTime = (System.currentTimeMillis()/1000) + this.scanTime;
        
        // While we are within our time limit repeat and count.
        for (int i = 0; System.currentTimeMillis()/1000 < stopTime; i++){
            
            try {
                // Grab a url from our list and create a new URL.
                url = new URL(linkList.get(i).toString());
            } 
            catch (MalformedURLException ex) {
                System.out.println("URL not found");
            }
            
            // System.out.println(url); // URL testing
            // Search the url for words!
            stripAndGrab(url);
        }
        
    }
    
    private void stripAndGrab(URL url) throws IOException{
        
        // Create a temporary string holder.
        String temp;
        Scanner input;
       
        try{ 
        // Connect to our url and attempt to get data.
            input = new Scanner(url.openStream());
        }
        catch(IOException uh){
            return;
        }
        while (input.hasNext()) {

            // hold the line being read as a string
            temp = input.nextLine().toLowerCase();

            // Convert the string to a doc
            Document doc = Jsoup.parse(temp);

                //grab all of the anchor links from the line
                Elements links = doc.getElementsByTag("p");

                // For each link found get the url address only 
                for (Element link : links){

                    // convert the url to a string for examination.
                    String x = link.attr("abs:p");
                    // If the string contains a link than add it to our list otherwise ignore it.
                    if(!x.isEmpty()){
                        doc.appendText(x);
                    }
                }
                
            // Strip the code and special characters from our doc text
            temp = Jsoup.clean(doc.text(), Whitelist.simpleText());
            temp = temp.replaceAll("[^A-Za-z]"," ").trim();

            // If the string actually contains text than execute.
            if (!temp.isEmpty() && !temp.contains("function") && !temp.contains("var ") && !temp.contains("=")){

                // Strip all the extra spacing out of our text.
                while (temp.contains("  ")){
                    temp = temp.replaceFirst("  "," ");
                }

                // Splitting our text with spaces isn't reliable. Let's use the ? instead.
                temp = temp.replaceFirst(" ","?");


                int cursor = 0;
                // Go through the string and chop up the words based on the location of the ?'s. Then add them to our list.    
                while (cursor < temp.length()){
                    if(temp.charAt(cursor) == '?'){
                        this.wordList.add(temp.substring(0,cursor));

                        // Remove the word we found and chop down our string but only if the string is big enough.
                        if (temp.length() > 0){
                            temp = temp.substring(cursor+1, temp.length());
                            temp = temp.trim();
                        }
                    }
                    else{
                        // Move the cursor ahead until for find a ?.
                        cursor++;
                    }
                } 
            }
            else{
            }
        }

    }
    
    private void runWhiteList(){
        
        whiteList.add("the");
        whiteList.add("to");
        whiteList.add("for");
        whiteList.add("and");
        whiteList.add("a");
        whiteList.add("be");
        whiteList.add("or");
        whiteList.add("if");
        whiteList.add("aria");
        whiteList.add("data");
        whiteList.add("it");
        whiteList.add("href");
        whiteList.add("font");
        whiteList.add("script");
        whiteList.add("htmlid");
        whiteList.add("http");
        whiteList.add("https");
        whiteList.add("settimeout");
        whiteList.add("return");
        whiteList.add("refreshremotessosession");
        whiteList.add("null");
        whiteList.add("moz");
        whiteList.add("html");
        whiteList.add("hiddenfield");
        whiteList.add("src");
        whiteList.add("countrycode");
        whiteList.add("input");
        whiteList.add("onclick");
        whiteList.add("footer");
        whiteList.add("overflow");
        whiteList.add("xlmns");
        whiteList.add("padding");
        whiteList.add("width");
        whiteList.add("class");
        whiteList.add("contacttype");
        
        
        for (int i = 0; i < this.wordList.size(); i++){
            for (int j = 0; j < whiteList.size(); j++){
                if(wordList.get(i).toLowerCase().equalsIgnoreCase(whiteList.get(j).toLowerCase()) || wordList.get(i).length() <= 2){
                    wordList.remove(i);
                    wordList.trimToSize();
                    i--;
                }
                if(wordList.get(i).endsWith(".pdf") || wordList.get(i).endsWith(".jpg")){
                    wordList.remove(i);
                    wordList.trimToSize();
                    i--;
                }
            }
            }
        }
    }


