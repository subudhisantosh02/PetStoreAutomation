package api.utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "Data")
    public String[][] getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/Userdata.xlsx";
        XLUtility xl = new XLUtility(path);

        int rownum = xl.getRowCount("Sheet1");  // Use correct casing
        int colcount = xl.getCellCount("Sheet1", 1);  // Read first data row

        String[][] apidata = new String[rownum - 1][colcount]; // exclude header row

        for (int i = 1; i < rownum; i++) { // start from row 1 (skip header at row 0)
            for (int j = 0; j < colcount; j++) {
                apidata[i - 1][j] = xl.getCellData("Sheet1", i, j);
            }
        }

        return apidata;
    }

    @DataProvider(name = "UserNames")
    public String[] getUserNames() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/Userdata.xlsx";
        XLUtility xl = new XLUtility(path);

        int rownum = xl.getRowCount("Sheet1");

        String[] apidata = new String[rownum - 1]; // exclude header

        for (int i = 1; i < rownum; i++) {
            apidata[i - 1] = xl.getCellData("Sheet1", i, 1); // Column index 1 (second column)
        }

        return apidata;
    }
}
