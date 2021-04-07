package com.automationanywhere.botcommand;

import com.automationanywhere.botcommand.data.impl.ListValue;
import org.testng.annotations.Test;

@Test
public class ListFiles_Test {
    String FilePath = "C:\\Users\\amrez\\Desktop\\TSME - peojects";
    ListFiles testCM = new ListFiles();
    ListValue<String> result = testCM.action(FilePath,"pdf");

    public ListValue<String> getResult() {
        //System.out.println(result);
        return result;
    }
}
