/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.utility;

import MForms.Utils.MNEHelper;
import MForms.Utils.MNEProtocol;
import MvxAPI.MvxSockJ;
import com.br.data.Select;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Wattana
 */
public class ConnectM3 {

    protected static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:21008/mne/servlet/MvxMCSvt"; //PRD
    protected static int appPort = 16105; // PRD
//    protected static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:22008/mne/servlet/MvxMCSvt"; //TST
//    protected static int appPort = 16305; // TST
    protected static String appServer = "192.200.9.190";
    protected static String m3id = "MVXSECOFR";
    protected static String m3pw = "lawson@90";
    protected static MvxSockJ sock;

    public static double RoundUp(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static boolean ConnectM3(String cono, String divi) throws JSONException {
        System.out.println("ConnectM3.");
        boolean conn;
        try {

            MNEHelper mne = new MNEHelper("192.200.9.190", 16105, "https://bkrmvxm3.bangkokranch.com:21008/mne/servlet/MvxMCSvt");
            conn = mne.logInToM3(10, "101", "MVXSECOFR", "lawson@90");

            if (conn == true) {
                System.out.println("Login complete.");
            } else {
                System.out.println("Can not login to M3 system.");
            }

            return conn;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }

    public static JSONArray callGLS100(String cono, String divi, String code, String period, String date, String txtFunc, String txtVCText, String type) {
        double WWCUAM = 0;
        String xwcuam = "";
        String txtResult = "";
        DecimalFormat df = new DecimalFormat("0.00");
        JSONArray mJSonArr = new JSONArray();

        try {

            try {

                MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
                mne.logInToM3(Integer.valueOf(cono), divi, m3id, m3pw);

                if (!mne.logInToM3(Integer.valueOf(cono), divi, m3id, m3pw)) {
                    System.out.println(" Can not login to M3 system");
                }

                String GLS100ID = mne.runM3Pgm("GLS100");
                System.out.println("GLS100ID:" + GLS100ID);

                if ((GLS100ID).equals("")) {
                    System.out.println(" ไม่สามารถเปิดโปรแกรม MMS100 ได้");
                }

                //---------------------------------------------------------
                if (mne.panel.equals("GLS100/B")) {
                    System.out.println("GLS100/B");
                    System.out.println("txtFunc : " + txtFunc);
                    mne.setField("CMDTY", "LSTOPT");
                    mne.setField("CMDVAL", 1);
                    mne.setField("FCS", "WAFNCN");
                    mne.setField("WAFNCN", txtFunc);
                    mne.pressKey(MNEProtocol.KeyEnter);
                    System.out.println(mne.getMsg());
                    mne.setField("SELROWS", "R1");
                    mne.selectOption("1");
                    System.out.println(mne.getMsg());

                    if (mne.panel.equals("GLS100/E")) {
                        System.out.println("GLS100/E");
                        System.out.println("date, txtVCText : " + date + " : " + txtVCText);
                        mne.setField("WWGVTX", txtVCText);
                        mne.setField("WWACDT", date);
                        mne.pressKey(MNEProtocol.KeyEnter);
                        System.out.println(mne.getMsg());

                        int c = 0;
                        while ((mne.panel.equals("GLS100/E")) && (c <= 3)) {
                            c++;
                            mne.pressKey(MNEProtocol.KeyEnter);
                            System.out.println(mne.getMsg());

                        }

                        if (mne.panel.equals("GLS120/J1")) {
                            System.out.println("GLS120/J1");
                            double xsum = 0;
                            List<String> getListData;

                            if ("0".equals(type)) {
                                getListData = Select.RSVoucher(cono, divi, code, period, type);
                            } else {
                                getListData = Select.RSVoucherVariance(cono, divi, code, period, type);
                            }

                            for (int i = 0; i < getListData.size(); i++) {
//                                System.out.println(getListData);
                                String[] getData = getListData.get(i).split(";");
                                String WXAIT1 = getData[1].trim();
                                String WXAIT2 = getData[2].trim();
                                String WXAIT3 = getData[3].trim();
                                String WXAIT4 = getData[4].trim();
                                String WXAIT5 = getData[5].trim();
                                String WXAIT6 = getData[6].trim();
                                String WWVTXT = getData[7].trim();
                                xwcuam = getData[8].trim();

                                WWCUAM = Double.parseDouble(df.format(((Double.parseDouble(xwcuam) * 100.00) / 100.00)));
                                
                                if(!"2AF".equals(WXAIT1.substring(0,3))){
                                   xsum += WWCUAM; 
                                }
                                

                                mne.setField("WXAIT1", WXAIT1);
                                mne.setField("WXAIT2", WXAIT2);
                                mne.setField("WXAIT3", WXAIT3);
                                mne.setField("WXAIT4", WXAIT4);
                                mne.setField("WXAIT5", WXAIT5);
                                mne.setField("WXAIT6", WXAIT6);
                                mne.setField("WWVTXT", WWVTXT);
                                mne.setField("WWCUAM", WWCUAM);
                                System.out.println(WXAIT1 + " : " + WXAIT2 + " : " + WXAIT3 + " : " + WXAIT4 + " : " + WXAIT5 + " : " + WXAIT6 + " : " + WWVTXT + " : " + WWCUAM);
                                mne.pressKey(MNEProtocol.KeyEnter);
                                System.out.println(mne.getMsg());
                            }

                            mne.pressKey(MNEProtocol.KeyF03);
                            System.out.println("Total : " + xsum);

                        }
                    }

                    if (mne.getMsg() == null) {
                        System.out.println(mne.getMsg());
                        Map<String, Object> mMap = new HashMap<>();
                        mMap.put("txtResult", "error");
                        mJSonArr.put(mMap);
                        System.out.println(mJSonArr);
                    } else {
                        System.out.println(mne.getMsg());
                        txtResult = mne.getMsg();
                        Map<String, Object> mMap = new HashMap<>();
                        mMap.put("txtResult", txtResult);
                        mJSonArr.put(mMap);
                        System.out.println(mJSonArr);
                    }
                    mne.closeProgram(GLS100ID);
                    System.out.println("mne.closeProgram(" + mne.panel + ")");
                }

                //---------------------------------------------------------
                mne.pressKey(MNEProtocol.KeyF03);
                mne.pressKey(MNEProtocol.KeyF03);
                mne.pressKey(MNEProtocol.KeyF03);
                mne.closeProgram(GLS100ID);

            } catch (Exception ex) {
                if (sock != null) {
                    System.out.println("ERR: " + sock.mvxGetLastError());
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("txtResult", ex);
                    mJSonArr.put(mMap);
                    System.out.println(mJSonArr);
                }
            }

        } catch (Exception ex) {
            System.out.println("ERR: " + ex);
            Logger.getLogger(ConnectM3.class.getName()).log(Level.SEVERE, null, ex);
            Map<String, Object> mMap = new HashMap<>();
            mMap.put("txtResult", ex);
            mJSonArr.put(mMap);
            System.out.println(mJSonArr);
        }

        return mJSonArr;

    }

    public static JSONArray preMMS100(String cono, String divi, String code, String period, String date) {

        JSONArray mJSonArr = new JSONArray();
        List<String> getListData = Select.RSInventory(cono, divi, code, period);
        for (int i = 0; i < getListData.size(); i++) {
            System.out.println(getListData);
            String[] getData = getListData.get(i).split(";");
            String cost = getData[2].trim();
            String ref1 = getData[4].trim();
            String ref2 = getData[5].trim();
            String ref3 = getData[6].trim();
            String qty = getData[8].trim();

            String txtResult = callMMS100(cono, divi, date, cost, ref1, ref2, ref3, qty, "KGS");
            Map<String, Object> mMap = new HashMap<>();
            mMap.put("txtResult", txtResult);
            mJSonArr.put(mMap);
        }

        return mJSonArr;
    }

    public static String callMMS100(String cono, String divi, String date, String cost, String ref1, String ref2, String ref3, String qty, String unit) {

        try {

            String checked = "YES";
            String txtResult = "";

            try {

                MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
                mne.logInToM3(Integer.valueOf(cono), divi, m3id, m3pw);

                if (!mne.logInToM3(Integer.valueOf(cono), divi, m3id, m3pw)) {
                    System.out.println(" Can not login to M3 system");
                }

                String MMS100ID = mne.runM3Pgm("MMS100");
                System.out.println("MMS100ID:" + MMS100ID);

                if ((MMS100ID).equals("")) {
                    System.out.println(" ไม่สามารถเปิดโปรแกรม MMS100 ได้");
                }

                int i = 0;
                while (!mne.panel.equals("MMS100/B1")) {
                    i++;
                    if (i >= 3) {
                        mne.closeProgram(MMS100ID);
                        System.exit(0);
                    }

                    mne.pressKey(MNEProtocol.KeyF13);
                    mne.setField("WWSPIC", "B-Browse");
                    mne.setField("WWDSEQ", "E12");
                    mne.pressKey(MNEProtocol.KeyEnter);
                    mne.pressKey(MNEProtocol.KeyF03);
                    mne.runM3Pgm("MMS100");

                }

                if (mne.panel.equals("MMS100/B1")) {
                    System.out.println(mne.panel);
                    System.out.println("Order type : " + ref2);
                    mne.setField("WWFACI", "1A1");
                    mne.setField("W1TRTP", ref2);
                    mne.setField("_PSEQ", "E1");
                    mne.selectOption("1");
                    System.out.println(mne.getMsg());

                    //========= Begin order Type A71 ==================
                    if (mne.panel.equals("MMS100/E")) {
                        System.out.println(mne.panel);
                        System.out.println("Whs, Date, Costcenter : " + ref1 + " : " + " : " + date + " : " + cost);
                        mne.setField("WGWHLO", ref1.trim());
                        mne.setField("WGTRDT", date.trim());
                        mne.setField("WGDEPT", cost.trim());
                        mne.pressKey(MNEProtocol.KeyEnter);
                        System.out.println(mne.getMsg());

                        if (mne.getMsg() != null) {
                            String error = mne.getMsg();
                            mne.pressKey(MNEProtocol.KeyF03);
                            mne.closeProgram(MMS100ID);
                            return error;
                        }

                        int b = 0;
                        while ((mne.panel.equals("MMS100/F")) && (b <= 3)) {
                            b++;
                            System.out.println(mne.panel);
                            mne.pressKey(MNEProtocol.KeyEnter);
                            System.out.println(mne.getMsg());
                        }

                        if (b > 3) {
                            mne.closeProgram(MMS100ID);
                            System.exit(0);
                        }

                        if (mne.panel.equals("User-defined Text")) {
                            System.out.println(mne.panel);
                            mne.pressKey(MNEProtocol.KeyEnter);
                            System.out.println(mne.getMsg());
                        }

                        //-------------------------------------------------------
                        if (mne.panel.equals("MMS101/B1")) {
                            System.out.println(mne.panel);
                            System.out.println("item, qty, unit : " + ref3 + " : " + " : " + qty + " : " + unit);
                            float iQty = Float.valueOf(qty);
                            List<String> getListQtyInvenLot = Select.RSInvenLot(cono, divi, ref1, ref3);
                            for (int ii = 0; ii < getListQtyInvenLot.size(); ii++) {

                                if (iQty != 0) {
                                    String[] CheckQtyInvenLot = getListQtyInvenLot.get(ii).split(" ; ");
                                    String vLot = CheckQtyInvenLot[1].trim();
                                    float vQty = Float.valueOf(CheckQtyInvenLot[2]);
                                    System.out.println(vQty + " : " + vLot);

//                                    if (iQty <= vQty) {
//                                        System.out.println("Issue iQty : " + iQty);
//                                        iQty -= iQty;
//
//                                    } else {
//                                        System.out.println("Issue vQty : " + vQty);
//                                        iQty -= vQty;
//
//                                    }
                                    if (iQty <= vQty) {
                                        System.out.println("Issue iQty : " + iQty);
                                        mne.setField("WRITNO", ref3);
                                        mne.setField("WRTRQT", iQty);
                                        mne.setField("WRALUN", unit);
                                        txtResult = "Order No : " + mne.getFieldMap("MRTRNR").getValue();
                                        System.out.println("Order No : " + txtResult);
                                        mne.pressKey(MNEProtocol.KeyEnter);
                                        System.out.println(mne.getMsg());

                                        mne.setField("WRBANO", vLot);
                                        mne.pressKey(MNEProtocol.KeyEnter);
                                        iQty -= iQty;

                                    } else {
                                        System.out.println("Issue vQty : " + vQty);
                                        mne.setField("WRITNO", ref3);
                                        mne.setField("WRTRQT", vQty);
                                        mne.setField("WRALUN", unit);
                                        mne.setField("WRBANO", vLot);
                                        txtResult = "Order No : " + mne.getFieldMap("MRTRNR").getValue();
                                        System.out.println("Order No : " + txtResult);
                                        mne.pressKey(MNEProtocol.KeyEnter);
                                        System.out.println(mne.getMsg());

                                        mne.setField("WRBANO", vLot);
                                        mne.pressKey(MNEProtocol.KeyEnter);
                                        iQty -= vQty;

                                    }

//                                    int d = 0;
//                                    while ((!mne.panel.equals("MMS101/E")) && (d <= 3)) {
//                                        mne.pressKey(MNEProtocol.KeyEnter);
//                                        System.out.println(mne.getMsg());
//                                        d++;
//                                    }
//
//                                    if (mne.panel.equals("MMS101/E")) {
//                                        mne.setField("FCS", "WRDEPT"); //FCS	WRDEPT
//                                        mne.setField("WRDEPT", cost.trim()); //WRDEPT	SDSAASDS
//                                        mne.pressKey(MNEProtocol.KeyEnter);
//                                        System.out.println(mne.getMsg());
//                                    }
                                }

                            }

                            if (iQty == 0) {
                                System.out.println("iQty = 0 issue complete");
                                checked = "YES";
//                                txtResult = "Order No 000000000001";
                            } else {
                                System.out.println("Item : " + ref3 + " not enough");
                                checked = "NO";
                            }

                            if (mne.getMsg() == null) {
                                checked = "YES";
                            } else {
                                checked = "NO";
                            }
                            System.out.println("Status : " + checked);
                        }

                        int c = 0;
                        while ((mne.panel.equals("MMS101/B1")) && (c <= 3)) {
                            c++;
                            mne.pressKey(MNEProtocol.KeyF03);
                        }

                        if (c <= 3) {
                            if ("YES".equals(checked)) {
                                System.out.println("Post Data Completed.");
                            }
                            mne.closeProgram(MMS100ID);
                        }

                    }

                    // ===== End Order Type A71 =========================
                    mne.closeProgram(MMS100ID);

                }

                //---------------------------------------------------------
                mne.pressKey(MNEProtocol.KeyF03);
                mne.pressKey(MNEProtocol.KeyF03);
                mne.pressKey(MNEProtocol.KeyF03);
                mne.closeProgram(MMS100ID);
                System.out.println("mne.closeProgram(MMS100ID)");

                return txtResult;

            } catch (Exception e) {
                if (sock != null) {
                    System.out.println("ERR: " + sock.mvxGetLastError());
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ConnectM3.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String getCompany() throws JSONException {

        JSONObject mJsonObj = new JSONObject();
        JSONArray mJsonArr = new JSONArray();

        try {
            mJsonArr = Select.Company();
            //System.out.println(mJsonArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mJsonArr.length() > 0) {
            mJsonObj.put("data", mJsonArr);
            return mJsonObj.toString();
        } else {
            mJsonObj.put("status", "404");
            mJsonObj.put("message", "Data not found.");
            return mJsonObj.toString();
        }

    }

    public static String getFacility(String cono, String divi) throws JSONException {

        JSONObject mJsonObj = new JSONObject();
        JSONArray mJsonArr = new JSONArray();

        try {
            mJsonArr = Select.Facility(cono, divi);
//            System.out.println(mJsonArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mJsonArr.length() > 0) {
            mJsonObj.put("data", mJsonArr);
            return mJsonObj.toString();
        } else {
            mJsonObj.put("status", "404");
            mJsonObj.put("message", "Data not found.");
            return mJsonObj.toString();
        }

    }

//    public static String getAlloType() throws JSONException {
//
//        JSONObject mJsonObj = new JSONObject();
//        JSONArray mJsonArr = new JSONArray();
//
//        try {
//            mJsonArr = SelectDB2.AlloType();
////            System.out.println(mJsonArr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (mJsonArr.length() > 0) {
//            mJsonObj.put("data", mJsonArr);
//            return mJsonObj.toString();
//        } else {
//            mJsonObj.put("status", "404");
//            mJsonObj.put("message", "Data not found.");
//            return mJsonObj.toString();
//        }
//
//    }
}
