package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SignUpProperties {
	 public String category ;
	 public String Country;
	 public String Product_name;
	 public String Selling_price;
	 public String Original_price;
	 public String Email_address,Password,Phone_number;
	 public String First_name,	Last_name,	Store_name,	Business_number,	Address_City,	State,	Pincode;
	 public String Product_description,	product_Category,	Total, stock_quantity,	Domain_name,	Dom_Offline_payment,	Int_Offline_payment;
	 public String[] values = new String[23];
	
	public  SignUpProperties populateData(String inputFileName, int row_num) throws Exception {
	 //String inputFileName="/Users/swetha/Downloads/stocks/Automatic_Sign_up_Process.xlsx";	
	 
     FileInputStream input=null;
     XSSFWorkbook  wb=null;
     XSSFSheet sheet=null;
	try 
	{
		 input = new FileInputStream(inputFileName);
		  wb = new XSSFWorkbook(input);
		 sheet = wb.getSheetAt(0);
		 getSheetHeaders(sheet,row_num);
	}
	catch(FileNotFoundException e) {
		e.printStackTrace();
	}
	SignUpProperties s1=new SignUpProperties();
	s1.category=values[0];
	s1.Email_address = values[1];
	s1.Password = values[2];
	s1.Phone_number = values[3];
	s1.First_name= values[4];
	s1.Last_name= values[5];
	s1.Store_name= values[6];
	s1.Business_number= values[7];
	s1.Address_City= values[8];
	s1.State= values[9];
	s1.Pincode= values[10];
	s1.Country= values[11];
	s1.Product_name= values[12];
	s1.Selling_price= values[13];
	s1.Original_price= values[14];
	s1.Product_description= values[15];
	s1.product_Category= values[16];
	s1.Total= values[17];
	s1.stock_quantity= values[18];
	s1.Domain_name= values[19];
	s1.Dom_Offline_payment= values[20];
	s1.Int_Offline_payment= values[21];
	
	return s1;
}
	
	public  void getSheetHeaders(Sheet sheet,int i)
	{
		int j=0;
		if(sheet == null)
			return;
		
		
		
		Row headerRow = sheet.getRow(1);
		Iterator<Cell> headerIterator = headerRow.cellIterator();
		//ArrayList<String> headerValues = new ArrayList<String>();
		while(headerIterator.hasNext())	//Add headers to list
		{
			String cellValue;
			Cell nextHeaderCell = headerIterator.next();
			if(nextHeaderCell.getCellType()== XSSFCell.CELL_TYPE_NUMERIC)
				cellValue=String.valueOf(nextHeaderCell.getNumericCellValue());
			else
				cellValue = nextHeaderCell.getStringCellValue();
			if(cellValue.trim().equals(""))
				break;
			
		//	headerValues.add(cellValue);
			values[j++]=cellValue;			
		}
		//return headerValues;
	}
}