package Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExelReader {
	// "operacje.xls"
	public static void exelToTxt() {
		try {
			File file = new File("operacje.xls");
			System.out.println(file.getAbsolutePath());
			FileInputStream fileInputStream = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("operacje");
			HSSFRow row1;
			HSSFCell cell;
			int iterator=1;
			row1 = worksheet.getRow(iterator);
			do{
			cell = row1.getCell(0);
			PrintWriter zapis = new PrintWriter("testy\\DaneTestowe"+String.format("%03d", iterator)+"[true].txt", "UTF-8");
			String text = cell.getStringCellValue();
			zapis.println(text);
			zapis.close();
			
			iterator+=1;
			row1 = worksheet.getRow(iterator);
			}while(row1!=null);

			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException error) {
			error.printStackTrace();
		}
	}
}
