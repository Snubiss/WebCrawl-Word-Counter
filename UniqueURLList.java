/********************************************************************
//  UniqueURLList.java       Author: Snubiss
//
//  Date: February 9, 2019
//  Modified: February 13, 2019
//
//  Defines the instance data, constructors and associated
//  methods of a UniqueURLList class object. UniqueURLList class is
//  designed to take a input of one URL object and produce a list of
//  unique URLS.
//
//********************************************************************/

package thecrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class UniqueURLList {
    
    
    ArrayList<String> linkList = new ArrayList<String>();
    Scanner input = null;
    String temp = "";
    
    
    public UniqueURLList(URL url,long timeLength) throws IOException{
        
        // Let the user know what we are doing.
        System.out.println("\nCreating a URL list.....");
        
        // Create our first list of links and add it to our list.
        createList(url);
        
        // Get URLs from our URLs.
        stretchOurTentacles(this.linkList, timeLength);
        
        // Create a set to weed out the duplicates.
        Set<String> set = new LinkedHashSet<>(this.linkList);
        
        // Clear our array list.
        this.linkList.clear();
        
        // Populate our original list with unique URLs.
        this.linkList.addAll(set);
        this.linkList.trimToSize();
        
        // Let the user know what we are doing.
        System.out.println("URL List Created! (" + linkList.size() + " unique links found)");
    }
    
    // Grabs all the links on the page of the URL given and adds thems to the list.
    private void createList(URL url){
        
        try {
        input = new Scanner(url.openStream());
        }
        catch (IOException ex){
            System.out.print(url + " URL is Unreachable. Moving to next URL.....\n");
            return;
        }
        catch (NullPointerException nul){
            System.out.println ("Null Pointer");
        }
        
        try{
            // While we have data keep reading it.
            while (input.hasNext()) {

                // hold the line being read as a string
                temp = input.nextLine();

                // Convert the string to a doc
                Document doc = Jsoup.parse(temp);

                //grab all of the anchor links from the line
                Elements links = doc.select("a[href]");

                // For each link found get the url address only 
                for (Element link : links){

                    // convert the url to a string for examination.
                    String x = link.attr("abs:href");
                    // If the string contains a link than add it to our list otherwise ignore it.
                    if(!x.isEmpty()){
                        linkList.add(link.attr("abs:href").trim());
                    }
                }
            }
        }
        catch(NullPointerException e){
            System.out.print(url + " URL is Unreachable\n");
        }
    }
        
   
        
        
    private void stretchOurTentacles(ArrayList urlList, long timeLength) throws MalformedURLException{    
        
        // Create a point in time to stop at.
        long stoptime = (System.currentTimeMillis()/1000) + timeLength;
        
        // While we are within our time limit repeat and count.
        for (int i = 0; System.currentTimeMillis()/1000 < stoptime; i++){
            
            if (linkList.size() > 0){
            // Grab a url from our list and create a new URL.
            URL tempURL = new URL(linkList.get(i));
            // Search the url for more links and add them to our list.
            createList(tempURL);
            }
        }
    }
    
    public ArrayList getLinkList(){
        
        // Give us our ArrayList upon request.
        return this.linkList;
    }
}
