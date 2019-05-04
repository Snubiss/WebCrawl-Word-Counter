/********************************************************************
//  FileIO.java       Author: Snubiss
//
//  Date: February 9, 2019
//  Modified: February 13, 2019
//
//  Defines the instance data, constructors and associated
//  methods of a FileIO class object. The FileIO class is
//  designed to save the user's searched data to a file on
//  the user's system.
//
//********************************************************************/

package thecrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileIO {
    
    public static boolean saveFile(ArrayList e, String url) throws FileNotFoundException {
        
        int counter = 1;
        boolean fileCheck = true;
        Date currentDate = new Date( );
        SimpleDateFormat df = new SimpleDateFormat ("E MM.dd.yyyy 'at' hh:mm:ss a zzz");

        
        while (fileCheck){
            // Create the save data files based on the users name and class role.
            File file = new File("SortedListData-" + counter + ".txt");

            // If the file exists then delete it to prevent errors.
            if (file.exists()) {
                counter++;
                fileCheck = true;
            }
            else{
                fileCheck = false;
            }

            // Create a new file and dump the players stats to it.
            try ( 
                PrintWriter output = new PrintWriter(file);
            ) 
            {
                output.println("The Crawler - version 1.0alpha\n\n");
                output.println("");
                output.println("Data retrieved on: " +  df.format(currentDate));
                output.println("Data retrieved from url: " +  url);
                output.println("");
                output.println("");
                output.println("Most Found Words:");
                
                for(int i = 0; i < e.size(); i++){
                    output.println(""+ (i+1) + " - " +e.get(i));
                }
            }
        }
    return true;
    }
}
