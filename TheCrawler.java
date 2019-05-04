//***********************************************************************
//  Filename: TheCrawler.java  -- Inital Start File
//  Application Name: The Crawler
//  Version: Alpha_1.00
//  Author: Snubiss
//  Modified Date: 2/9/2019
//
// The Purpose:
//   The goal is this application is to count the words on a wepage and display them 
//   in ranking order according to occurrence. Once the application begins it will
//   ask for a URL address from the user. Next, the program will grab all of the 
//   website links from the page and create a list of website links. It then repeats
//   this process with the URLs in the url list is just created and essentials "Crawls"
//   across as many webpages as it can for 20 seconds, sucking down links.
//   
//   Once the crawling phase is complete, the program will sift through all of the text
//   data from those websites. Code, such as html, javascript, xml, will be ignored and only
//   real words (hopefully) will be obtained. Special characters will be removed and any words
//   on the whitelist will be removed as well.
//   
//   Finally, the program will count the occurrences of each word and create a descending list of
//   the top 100 words (or less) according to their occurrence. It will then display the list onto
//   the screen for the user to view and save the file to the user's hard disk.
//
//   ----   NOTES  ----
//
//   You will find the number of words scraped from websites will vary depending on how the website 
//   is coded.
//
//   All scanning processes run for 20 seconds for per url per page by default.
//
// Required Input Variables: 
//
//   Valid URL
//
//*************************************************************************


package thecrawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TheCrawler {
    
    private static String getLogo(){
    
        String logo =
               (String.format("%-10s","              (")) + "\n" +
               (String.format("%-10s","               )")) + "\n" +
               (String.format("%-10s","              (")) + "\n" +
               (String.format("%-10s","        /\\  .-\"\"\"-.  /\\")) + "\n" +
               (String.format("%-10s","       //\\\\/  ,,,  \\//\\\\")) + "\n" +
               (String.format("%-10s","       |/\\| ,;;;;;, |/\\|")) + "\n" +
               (String.format("%-10s","       //\\\\\\;-\"\"\"-;///\\\\")) + "\n" +
               (String.format("%-10s","      //  \\/   .   \\/  \\\\")) + "\n" +
               (String.format("%-10s","     (| ,-_| \\ | / |_-, |)")) + "\n" +
               (String.format("%-10s","       //`__\\.-.-./__`\\\\")) + "\n" +
               (String.format("%-10s","      // /.-(() ())-.\\ \\\\")) + "\n" +
               (String.format("%-10s","     (\\ |)   '---'   (| /)")) + "\n" +
               (String.format("%-10s","      ` (|           |) `")) + "\n" +
               (String.format("%-10s","        \\           (/")) + "\n";
    
        return (logo);
    }
    
    public static void main(String[] args) {
        
        UniqueURLList links = null;
        WordList wordList = null;
        WordCounter finalList = null;
        URL url = null;
        Scanner input = null;
        ArrayList<String> urlList= new ArrayList();
        long stoptime;
        // How long we should scan for urls. - Set higher for deeper scan.
        long timeLength = 20;
        boolean tempBool = false;
        String temp;
        String initialURL = null;
        boolean mainLoop = true;
        boolean appLoop = true;
        
        
        while(appLoop){
            
            System.out.println("\n" + getLogo());
            System.out.println("\nWelcome to the WebCrawler Experience!");
            mainLoop = true;
            
            while (mainLoop){
                // REQUEST URL //
               
                    try {
                        System.out.println("\nPlease enter a URL to crawl and count some words!");
                        input = new Scanner(System.in);
                        temp = input.next();
                        initialURL = temp;
                        url = new URL(temp);
                    } catch (MalformedURLException ex) {
                        System.out.println("That is not a valid url. \nURLs start with \"http\" or \"https\"");
                        mainLoop = false;
                        tempBool = true;
                        break;
                    }

                // Create a list of URLS from one URL.
                try{
                    links = new UniqueURLList(url, timeLength);
                }
                catch(IOException e){
                    System.out.println("Error 73");
                    mainLoop = false;
                    break;
                }
                // Check if our list has links in it.
                if (links.getLinkList().size() > 0){
                    // Create a word list from one URLs.
                    try{
                        wordList = new WordList(links.linkList, timeLength);
                    }
                    catch(IOException e){
                        System.out.println("No URLS were found in the created list.");
                        mainLoop = false;
                        break;
                    }



                    ArrayList<String>rawText = new ArrayList(wordList.getWordList());


                    finalList = new WordCounter(rawText);

                    // List all of the words found to the user.
                    for (int i = 0; i < finalList.getCountedList().size(); i++){
                        System.out.println(""+ (i+1) + " - " + finalList.getCountedList().get(i));
                    }
                }
                else{
                    System.out.println("No links were found!");
                    mainLoop = false;
                    break;
                }
                
                // Save to file
                try {
                    FileIO.saveFile(finalList.getCountedList(), initialURL);
                    System.out.println("\nFile Saved!");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TheCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }    
                


            }// End main loop
        }// End of app loop
    } // End of main class
} // End of crawler class


