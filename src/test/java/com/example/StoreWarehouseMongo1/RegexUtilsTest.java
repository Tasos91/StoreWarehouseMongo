package com.example.StoreWarehouseMongo1;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.StoreWarehouseMongo1.helpers.RegexUtils.filePathRegex;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class RegexUtilsTest {

    @Test
    public void fileRegexTest() {
        boolean i = filePathRegex("https://myanatolimages.s3.eu-central-1.amazonaws.com/10847718_974571419229711_5476717499072881185_o.jpg");
        assertEquals(true, i);
        System.out.println("The filepath is: " + i);
    }
}
