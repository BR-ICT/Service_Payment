
import com.br.data.Insert;
import com.br.data.Select;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Mahakit
 */
public class TestingClass {

    public int add(int numberA, int numberB) {
        return numberA + numberB;
    }

    public boolean CheckCostcenter(String divi, String cono, String costcenterinput, int expectedresult) throws Exception {
        JSONArray costcenter = Select.checkCostcenter(divi, cono, costcenterinput);
        JSONObject test = costcenter.getJSONObject(0);
//        String[] test2;
//        test2 = test.split(":");
        String test3;
        test3 = test.getString("Check");
        boolean result = true;
        int num = Integer.parseInt(test3);
        if (num > expectedresult) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public boolean Checksupplier(String divi, String cono, String supplierinput, int expectedresult) throws Exception {
        JSONArray supplier = Select.checkSupplier(divi, cono, supplierinput);
        JSONObject test = supplier.getJSONObject(0);
//        String[] test2;
//        test2 = test.split(":");
        String test3;
        test3 = test.getString("Check");
        boolean result = true;
        int num = Integer.parseInt(test3);
        if (num > expectedresult) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public String CheckServicesCreation(String divi, String cono, String supplierinput, String costcenterinput, int expectedresult) throws Exception {
        JSONArray supplier = Insert.SaveSrnForm(
                "A10",
                "Test2",
                "2024-04-24",
                "2024-04-09",
                "1234",
                "1015106001 : K.SAWAENG ARTSOMBOON",
                "B01",
                "Test",
                "MAHAKI_CHU",
                "0",
                "0",
                "0.00",
                "",
                "",
                "testtest10",
                "2024-04-09",
                "20",
                "",
                "0.00",
                "2024-04-24",
                "ERN",
                "10",
                "101");
        JSONObject test = supplier.getJSONObject(0);
//        String[] test2;
//        test2 = test.split(":");
        String test3;
        test3 = test.getString("orderno");
//        boolean result = true;
//        int num = Integer.parseInt(test3);
//        if (num > expectedresult) {
//            result = true;
//        } else {
//            result = false;
//        }
        return test3;
    }

    public boolean insertcheck(String divi, String cono, String ordernum) throws Exception {
        JSONArray supplier = Insert.insertNewItemService(
                ordernum,
                "OH105005 : จาระบี Trane ขนาด 5 ก.ก",
                "test",
                "10", "7", "0.00", "70", "0", "70", "10", "101");
        JSONObject test = supplier.getJSONObject(0);
//        String[] test2;
//        test2 = test.split(":");
        String test3 = "";
        test3 = test.getString("Result");
        boolean result = true;
//        int num = Integer.parseInt(test3);
        if (test3.equals("")) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    public String CheckPaymentCreation(String divi, String cono, String supplierinput, String costcenterinput, int expectedresult) throws Exception {
        JSONArray supplier = Insert.SavePrvForm("", "2024-04-25",
                "2024-04-16", "1010101007 : บริษัท ท็อปโปรดักส์ ซัพพลาย จำกัด", "A10 : PS Farm:East UCF",
                "3", "KBANK/ดาวคนอง/0801054994", "MAHAKI_CHU", "", "", "test", "0", "1", "ERN", "", "00", "2024-04-25",
                "ERN", "10", "101");
        JSONObject test = supplier.getJSONObject(0);
//        String[] test2;
//        test2 = test.split(":");
        String test3;
        test3 = test.getString("orderno");
//        boolean result = true;
//        int num = Integer.parseInt(test3);
//        if (num > expectedresult) {
//            result = true;
//        } else {
//            result = false;
//        }
        return test3;
    }

    public boolean insertcheckPRV(String divi, String cono, String ordernum, String orderPRV) throws Exception {
        JSONArray supplier = Insert.InsertPrvForm(orderPRV, ordernum, "ERN", "10", "101");
        JSONObject test = supplier.getJSONObject(0);
//        String[] test2;
//        test2 = test.split(":");
        String test3 = "";
        test3 = test.getString("Result");
        boolean result = true;
//        int num = Integer.parseInt(test3);
        if (test3.equals("")) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
        
