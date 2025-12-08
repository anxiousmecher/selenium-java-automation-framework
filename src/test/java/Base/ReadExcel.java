package Base;

import com.beust.ah.A;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReadExcel {
    @DataProvider(name="Readexcel")
    public Object[][] readexceldata(Method testmethod) throws IOException {
        String sheetname;
        String[] Usertype;
        ArrayList<Object[]> list=new ArrayList<>();
        //Iforgot this
         SheetName sheetnameannotation=testmethod.getAnnotation(SheetName.class);
         if(sheetnameannotation!=null)
         {
             sheetname=sheetnameannotation.value();
         }
         else {
             sheetname="default";
         }
         UserType usertypeannotaton=testmethod.getAnnotation(UserType.class);
         if(usertypeannotaton!=null) {
             Usertype = usertypeannotaton.value();
         }
         else {
             Usertype= new String[0];
         }
         try(FileInputStream fis=new FileInputStream("input//input.xlsx"); XSSFWorkbook workbook=new XSSFWorkbook(fis))
         {
             XSSFSheet sheet=workbook.getSheet("Sauce");
             int testcasenamecol=-1; int usernamecol=-1; int passwordcol=-1; int usertypecol=-1;
             Row toprow=sheet.getRow(0);
             for(Cell cell:toprow) {
                 if (cell.getStringCellValue().equalsIgnoreCase("TestCase")) {
                     testcasenamecol = cell.getColumnIndex();
                 }
                 if (cell.getStringCellValue().equalsIgnoreCase("Username")) {
                     usernamecol = cell.getColumnIndex();
                 }
                 if (cell.getStringCellValue().equalsIgnoreCase("Password")) {
                     passwordcol = cell.getColumnIndex();
                 }
                 if (cell.getStringCellValue().equalsIgnoreCase("Usertype")) {
                     usertypecol = cell.getColumnIndex();
                 }
             }
                 if(testcasenamecol==-1||usernamecol==-1||usertypecol==-1||passwordcol==-1)
                 {
                     throw new RuntimeException("No such column header found in the input excel testcasename:"+testcasenamecol+" username:"+usernamecol+" password: "+passwordcol+" usertype:"+usertypecol);
                 }
                 int lastrownum=sheet.getLastRowNum();
                 for(int i=0;i<lastrownum;i++)
                 {
                     Row row=sheet.getRow(i);
                     if(row.getCell(testcasenamecol).getStringCellValue().equalsIgnoreCase(testmethod.getName()))
                     {
                         for(int j=0;j<Usertype.length;j++) {
                             if(Usertype[j].equalsIgnoreCase(row.getCell(usertypecol).getStringCellValue())) {
                                 String username = row.getCell(usernamecol).getStringCellValue();
                                 String password = row.getCell(passwordcol).getStringCellValue();
                                 list.add(new Object[] {username, password});
                             }
                         }
                     }
                 }
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         return list.toArray(new Object[0][0]);
    }
}
