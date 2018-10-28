package input;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class InputDocumentReader 
{
	//Used to parse the Excel document using Apache POI API
	
	private String inputFileName;		//Name of the file to read
	
	public String getInputFileName() 
	{
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) 
	{
		this.inputFileName = inputFileName;
	}

	//Reads one data row
	//Returns an array of strings corresponding to it
	public ArrayList<String> readRow(Row row, int columns)	
	{
		ArrayList<String> cellValueList = new ArrayList<String>();	//List to hold the values we read
		DataFormatter formatter = new DataFormatter();	//Used to get String value of cell
		int cellNumber;
		int lastCellNumber = row.getFirstCellNum() + columns;
		for(cellNumber = row.getFirstCellNum(); cellNumber < lastCellNumber; cellNumber++)
		{
			//Iterating over cells in the row
			Cell currentCell = row.getCell(cellNumber, Row.RETURN_BLANK_AS_NULL);	//Get Cell object
			if(currentCell == null)
			{
				cellValueList.add("");	//Treat empty cell entry as a blank string
			}
			else
			{
				//Format the current cell as a String and add it to the list
				cellValueList.add(formatter.formatCellValue(currentCell));																					
			}
		}
		
		return cellValueList;
	}
	
	//Takes a Sheet object
	//Headers are handled separately, since we definitely know there aren't any empty headers
	//Returns a list of String objects representing the headers of the sheet
	public ArrayList<String> getSheetHeaders(Sheet sheet)
	{
		if(sheet == null)
			return null;
		
		Row headerRow = sheet.getRow(0);
		Iterator<Cell> headerIterator = headerRow.cellIterator();
		ArrayList<String> headerValues = new ArrayList<String>();
		while(headerIterator.hasNext())	//Add headers to list
		{
			Cell nextHeaderCell = headerIterator.next();
			//headerValues.add(nextHeaderCell.getStringCellValue());
			String cellValue = nextHeaderCell.getStringCellValue();
			if(cellValue.trim().equals(""))
				break;
			
			headerValues.add(cellValue);
		}
		return headerValues;
	}
	//Processes the entire Sheet object
	//Returns a list of lists, the first list corresponding to the headers, and the rest corresponding to data items in the sheet
	 public ArrayList<ArrayList<String>> readSheet(Sheet sheet)
	 {
		 if(sheet == null)
			 return null;
		 
		 ArrayList<ArrayList<String>> rowValueList = new ArrayList<ArrayList<String>>();	
		 Iterator<Row> rowIterator = sheet.iterator();		//Get the row iterator for the sheet
		 int columnCount;
		 
		 //First row consists of headings, use it to get column count
		 ArrayList<String> headerValues = getSheetHeaders(sheet);
		 columnCount = headerValues.size();
		 rowValueList.add(headerValues);	//Append headers to list of rows
		 rowIterator.next();	//Skip the first header row, it has been handled separately
		 
		 //Now to handle the data rows
		 while(rowIterator.hasNext())	//Iterate over all rows
		 {
			 Row currentRow = rowIterator.next();	//Get the next row
			 ArrayList<String> currentRowValue = readRow(currentRow, columnCount);	//Process each row
			 //Assuming first column is token, stop adding if a blank token is encountered
			 if(currentRowValue.get(0).trim().equals(""))
			 {
				 break;
			 }
			 rowValueList.add(currentRowValue);	//Append the row value to the list of lists
		 }
		 //Now return the list of lists
		 return rowValueList;	 
	 }
	 
	 public ArrayList<String> getRowFromSheet(int rowIndex, Sheet sheet, int columns)
	 {
		 if(sheet == null)
			 return null;
		 		 
		 Row row = sheet.getRow(rowIndex);
		 return readRow(row, columns);
		 
	 }
	
	 //Helper routine for pretty printing of headers and data
	 public void printHeadersAndData(ArrayList<String> headers, ArrayList<String> data)
	 {
		 int index;
		 int tabsNeeded;
		 for(index = 0; index < headers.size(); index++)
		 {
			 tabsNeeded = 4 - (headers.get(index).length()/8);
			 System.out.print(headers.get(index));
			 while(tabsNeeded-- > 0)
				 System.out.print("\t");
			 if(data.get(index).equals(""))
				 System.out.println("<Empty String>");
			 else
				 System.out.println(data.get(index));
		 }
	 }
	 //Helper routine for pretty printing of the entire processed sheet
	 public void printSheet(ArrayList<ArrayList<String>> sheetData)
	 {
		 Iterator<ArrayList<String>> rowIterator= sheetData.iterator();
		 ArrayList<String> headers = rowIterator.next();	//First row is header
		 int rowCounter = 1;
		 while(rowIterator.hasNext())
		 {
			 ArrayList<String> nextRow = rowIterator.next();
			 System.out.println("\n---Record " + rowCounter + " ---");
			 printHeadersAndData(headers, nextRow);
			 rowCounter++;
		 }
	 }
	 
}
