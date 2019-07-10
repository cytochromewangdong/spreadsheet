package edu.mum.client;

import edu.mum.spreadsheet.SpreadSheet;

public class Director {
	public void fillSampleSheet(SpreadSheet sheet) {
		sheet.setCellValue(1, 1, "Airfare:");
		sheet.setCellValue(1, 2, 6885.15);
		sheet.setCellValue(1, 4, "What we pay to the airlines");
		sheet.setCellValue(2, 1, "Taxi:");
		sheet.setCellValue(2, 2, 118);
		sheet.setCellValue(3, 1, "Rental Car:");
		sheet.setCellValue(3, 2, 295.85);
		sheet.setCellValue(4, 1, "Hotel:");
		sheet.setCellValue(4, 2, 432);
		sheet.setCellValue(5, 1, "Meals:");
		sheet.setCellValue(5, 2, 150);
		sheet.setCellValue(5, 4, "All meals combined");
		
		sheet.setCellValue(7, 1, "Sub-Total:");
		sheet.setCellValue(7, 2, "[1,2]+[2,2]+[3,2]+[4,2]+[5,2]");
		sheet.setCellValue(8, 1, "Discount:");
		sheet.setCellValue(8, 2, 0.15);
		sheet.setCellValue(9, 1, "Total:");
		sheet.setCellValue(9, 2, "[7,2]*(1-[8,2])");
		sheet.setCellValue(10, 1, "Partners:");
		sheet.setCellValue(10, 2, 4);
		sheet.setCellValue(11, 1, "Months:");
		sheet.setCellValue(11, 2, 12);
		sheet.setCellValue(12, 1, "Installments:");
		sheet.setCellValue(12, 2, "[9,2]/[10,2]/[11,2]");
	}
}
