package com.aura.utils;

import com.opencsv.CSVReader;
import org.testng.annotations.DataProvider;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class csvDataProvider {
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        List<Object[]> dataList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("src/test/resources/data/logindata.csv"))) {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                dataList.add(line);
            }
        }
        return dataList.toArray(new Object[0][0]);
    }
}