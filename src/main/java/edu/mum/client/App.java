package edu.mum.client;

import edu.mum.spreadsheet.SpreadSheet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	SpreadSheet sheet = new SpreadSheet();
    	Director director = new Director();
    	director.fillSampleSheet(sheet);
    }
}
