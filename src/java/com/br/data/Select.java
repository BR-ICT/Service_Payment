/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.data;

import MForms.Utils.MNEHelper;
import MForms.Utils.MNEProtocol;
import MvxAPI.MvxSockJ;
import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import java.awt.event.WindowEvent;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import static com.br.utility.Constant.dbname;
import static com.br.utility.Constant.dbM3Name;

/**
 *
 * @author Wattana
 */
public class Select {

    public static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:21008/mne/servlet/MvxMCSvt"; //PRD
//    public static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:22008/mne/servlet/MvxMCSvt";   // TST 
    static MvxSockJ sock;
    private static String appServer;
    private static int appPort;
    private static String m3id;
    private static String m3pw;
    String chkpms300 = "no";

    public static JSONArray getCompany() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT CCCONO,CCDIVI,CCCONM,'\"'|| TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCCONM) || '\"' AS COMPANY\n"
                        + "FROM " + dbM3Name + ".CMNDIV\n"
                        + "WHERE CCDIVI != ''\n"
                        + "ORDER BY CCCONO";
                System.out.println("SelectCompany\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("CCCONO", mRes.getString(1).trim());
                    mMap.put("CCDIVI", mRes.getString(2).trim());
                    mMap.put("CCCONM", mRes.getString(3).trim());
                    mMap.put("COMPANY", mRes.getString(4).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getItemData(String divi, String cono) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT TRIM(EAAITM)||' : '||TRIM(EATX40) AS resultforitem  \n"
                        + "  FROM " + dbM3Name + ".FCHACC \n"
                        + "  WHERE EAAITP =  2 \n"
                        + "AND   EADIVI = '" + divi + "' \n"
                        + "AND EACONO = '" + cono + "'\n"
                        + "ORDER BY EAAITM";
                System.out.println("Get Item Data\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vAutofill", mRes.getString("resultforitem").trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray checkCostcenter(String divi, String cono, String costcenter) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String costcentercode = get_SemiColonValue0(costcenter);
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT COUNT(EAAITM) AS COUNTS FROM M3FDBPRD.FCHACC \n"
                        + "WHERE EAAITP =  2 \n"
                        + "AND   EADIVI = '" + cono + "' \n"
                        + "AND EACONO = '" + divi + "'\n"
                        + "AND EAAITM = '" + costcentercode + "'";
                System.out.println("Get Item Data\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("Check", mRes.getString("COUNTS").trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray checkSupplier(String cono, String divi, String Supplier) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String Suppliercode = get_SemiColonValue0(Supplier);
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT COUNT(IDSUNO) as COUNTS\n"
                        + "FROM M3FDBPRD.CIDMAS \n"
                        + " WHERE IDSTAT = '20'\n"
                        + " AND IDCONO='" + cono + "'   \n"
                        + " AND IDSUNO = '" + Suppliercode + "'";
                System.out.println("Get Item Data\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("Check", mRes.getString("COUNTS").trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static String GetDateFormatSet(String dateToset) {
        String formatted = dateToset.replace("-", "");
        return formatted;
    }

    public static String GetDateDecmalCurrenttime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public static String get_SemiColonValue0(String TextFieldto) {
        if (TextFieldto.contains(":")) {
            String TextFieldtos[] = TextFieldto.split(":");
            return TextFieldtos[0]; // GET COST CENTER
        }
        return TextFieldto;
    }

    public static String CheckDuplicateSupInvoice(String cono, String divi, String sup, String inv, String srn) throws Exception {
        String[] supplierbeforesplit = sup.split(":");
        String supplier = supplierbeforesplit[0];
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String check = "false";
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT COUNT(*) AS CHK\n"
                        + "FROM " + dbname + ".SERVICEHEAD\n"
                        + "WHERE EPRH_SUNO = '" + supplier.trim() + "'\n"
                        + "AND EPRH_INVSU = '" + inv.trim() + "'\n"
                        + "AND EPRH_PHNO != '" + srn + "'\n"
                        + "AND EPRH_CONO = '" + cono + "'\n"
                        + "AND EPRH_DIVI = '" + divi + "'";
                System.out.println("CheckDuplicateSupInvoice\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                if (mRes.next()) {
                    if (mRes.getInt("CHK") > 0) {
                        check = "true";
                    } else {
                        Map<String, Object> mMap = new HashMap<>();
                        check = "false";
                    }

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return check;

    }

    public static JSONArray GetReferenceGRN_Bank(String supplier, String cono) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
//        String code = "TESTING";
        supplier = get_SemiColonValue0(supplier.trim());
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT IRYRE1  AS EPRA_PARM\n"
                        + "FROM " + dbM3Name + ".CIDREF \n"
                        + "WHERE IRRFID = 'BANK'\n"
                        + "AND IRCONO =" + cono + "\n"
                        + "AND IRSUNO ='" + supplier + "' ";
                System.out.println("GetReferenceGRN_Bank\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                if (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vBankdetail", mRes.getString("epra_parm").trim());
                    mJSonArr.put(mMap);
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray get3prh(String cono, String divi, String supplierdata) throws Exception {
        String[] supplierbeforesplit = supplierdata.split(":");
        String supplier = supplierbeforesplit[0];
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();
                String query1 = "select ctparm    FROM " + dbM3Name + ".csytab WHERE  CTDIVI='" + divi + "' AND ctstco = 'DEPT' AND ctstky = '" + supplier + "'  AND ctcono='" + cono + "'";
                String query2 = "select substr(s2stid,2,3)as bucode   FROM " + dbM3Name + ".fstlin where s2divi='" + divi + "' AND s2cono='" + cono + "' AND s2aitm = '" + supplier + "'";
                String query3 = "select ctparm    FROM " + dbM3Name + ".csytab WHERE CTDIVI='" + divi + "' AND ctstco = 'DEPT' AND ctstky = '" + supplier + "'  AND ctcono='" + cono + "'";
                System.out.println("Getdata1\n" + query1);
                System.out.println("Getdata2\n" + query2);
                System.out.println("Getdata3\n" + query3);
                ResultSet mRes1 = stmt.executeQuery(query1);
                ResultSet mRes2 = stmt2.executeQuery(query2);
                ResultSet mRes3 = stmt3.executeQuery(query3);
                Map<String, Object> mMap = new HashMap<>();
                if (mRes1.next()) {
                    mMap.put("prh_bu", mRes1.getString(1).trim());
                }
                if (mRes2.next()) {
                    mMap.put("prh_fac", mRes2.getString(1).trim());
                }
                if (mRes3.next()) {
                    mMap.put("prh_whs", mRes3.getString(1).trim());
                }
                mJSonArr.put(mMap);
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray insertNewItemPRV(String EPPANO, String txtitemcode) throws Exception {

        String[] itemalldats = txtitemcode.split(":");
        String itemcode = itemalldats[0];
        String itemname = itemalldats[1];
        String LineNumbers = "1";
        String StrInsertDetail;
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        Connection conn2 = ConnectDB2.ConnectionDB();
        Statement stmt2 = conn2.createStatement();
        try {
            if (conn != null) {
//                RHCONO, RHDIVI, RHWARE, RHPERI
                Statement stmt = conn.createStatement();
                String StrGetMaxLine = "SELECT MAX(EPRL_PNLI)+1 AS LINENUM\n"
                        + "FROM " + dbname + ".SERVICELINE \n"
                        + "WHERE  EPRL_PHNO='" + EPPANO + "'";
                ResultSet mRes = stmt.executeQuery(StrGetMaxLine);
                while (mRes.next()) {
                    LineNumbers = mRes.getString("LINENUM");
                }
                try {
                    int lineconvert = Integer.parseInt(LineNumbers);
                } catch (Exception e) {
                    LineNumbers = "1";
                }
                StrInsertDetail = "INSERT INTO " + dbname + ".SERVICELINE\n"
                        + "(EPRL_PHNO,EPRL_PNLI,EPRL_ITNO,\n"
                        + "EPRL_DESC,EPRL_QTY,EPRL_UPRICE,";
                stmt2.execute(StrInsertDetail);
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> mMap = new HashMap<>();
            mMap.put("result", "nok");
            mMap.put("message", e);
            mJSonArr.put(mMap);
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();

            }
            if (conn2 != null) {
                conn2.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getOrderDataServices(String supplierdata, String cono, String divi) throws Exception {
        String[] supplierbeforesplit = supplierdata.split(":");
        String supplier = supplierbeforesplit[0];
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "SELECT TRIM(EPRH_COCE) ||' : '||EATX40 AS COCENEW,IDSUNO||' : '||IDSUNM AS NEWSUNO\n"
                        + "," + dbname + ".SERVICEHEAD.*  FROM " + dbname + ".SERVICEHEAD \n"
                        + "LEFT JOIN " + dbM3Name + ".FCHACC ON\n"
                        + "TRIM(EPRH_COCE)  = TRIM(EAAITM)\n"
                        + "AND " + dbname + ".SERVICEHEAD.EPRH_CONO = EACONO \n"
                        + "AND " + dbname + ".SERVICEHEAD.EPRH_DIVI = EADIVI\n"
                        + "LEFT  JOIN  " + dbM3Name + ".CIDMAS ON\n"
                        + "IDSUNO = EPRH_SUNO \n"
                        + "AND IDCONO = " + dbname + ".SERVICEHEAD.EPRH_CONO\n"
                        + "AND EACONO = IDCONO\n"
                        + "WHERE EPRH_PHNO = '" + supplierdata + "'\n"
                        + "AND " + dbname + ".SERVICEHEAD.EPRH_CONO = '" + cono + "'\n"
                        + "AND " + dbname + ".SERVICEHEAD.EPRH_DIVI = " + divi;
                System.out.println("Getdata1\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("supplierlist", mRes1.getString("EPRH_PHNO"));
                    mMap.put("costcenter", mRes1.getString("COCENEW"));
                    mMap.put("supplier", mRes1.getString("NEWSUNO"));
                    mMap.put("Purpose", mRes1.getString("EPRH_PURPOS"));
                    String RQDate1 = mRes1.getString("EPRH_RQSDT");
                    String RQDate = RQDate1.substring(0, 4) + "-" + RQDate1.substring(4, 6) + "-" + RQDate1.substring(6, 8);
                    mMap.put("vRQDate", RQDate);
                    String requireDate1 = mRes1.getString("EPRH_RQRDT");
                    String requireDate = requireDate1.substring(0, 4) + "-" + requireDate1.substring(4, 6) + "-" + requireDate1.substring(6, 8);
                    mMap.put("vRequireDate", requireDate);
                    String invdatenew1 = mRes1.getString("EPRH_INVDT");
                    String invdatenew = invdatenew1.substring(0, 4) + "-" + invdatenew1.substring(4, 6) + "-" + invdatenew1.substring(6, 8);
                    mMap.put("invdate", invdatenew);
                    mMap.put("ContractNo", mRes1.getString("EPRH_APTNO"));
                    mMap.put("prh_fac", mRes1.getString("EPRH_BU"));
                    mMap.put("remark", mRes1.getString("EPRH_REM1"));
                    mMap.put("reqby", mRes1.getString("EPRH_REQBY"));
                    mMap.put("discount", mRes1.getString("EPRH_DISC"));
                    mMap.put("vat", mRes1.getString("EPRH_VTCD"));
                    mMap.put("vat2", mRes1.getString("EPRH_VTAMT"));
                    mMap.put("prh_bu", mRes1.getString("EPRH_FAC"));
                    mMap.put("prh_whs", mRes1.getString("EPRH_WHS"));
                    mMap.put("supinv", mRes1.getString("EPRH_INVSU"));
                    String Stattt = "";
                    Stattt = mRes1.getString("EPRH_STAT").trim();
                    mMap.put("totalall", mRes1.getString("EPRH_TOTAL"));
                    mMap.put("vService", supplierdata);;
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getOrderDataPRV(String ordernum, String cono, String divi) throws Exception {
        String[] ordernumbeforesplit = ordernum.split(":");
        String ordernum1 = ordernumbeforesplit[0];
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "select TRIM(eaaitm)||' : '||TRIM(eatx40) AS costcenter,\n"
                        + "idsuno||' : '||idsunm AS supplier," + dbname + ".PAYMENTHEAD.*    \n"
                        + "FROM " + dbname + ".PAYMENTHEAD \n"
                        + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPPA_COCE AND EACONO = EPPA_CONO AND EADIVI=EPPA_DIVI\n"
                        + "LEFT JOIN " + dbM3Name + ".cidmas ON  EPRA_SUNO = idsuno AND IDCONO = EPPA_CONO\n"
                        + "WHERE EPPA_NO ='" + ordernum + "'\n"
                        + "AND eaaitp = 2 and   EPPA_CONO = '" + cono + "'\n"
                        + "AND idstat = '20' AND EPPA_DIVI ='" + divi + "'\n"
                        + "Order by idsuno";
                System.out.println("Getdata1\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vpaymentremark", mRes1.getString("EPRA_PARM"));
                    mMap.put("costcenter", mRes1.getString("costcenter"));
                    mMap.put("supplier", mRes1.getString("supplier"));
                    String datenow1 = mRes1.getString("EPPA_DATE");
                    String datenow = datenow1.substring(0, 4) + "-" + datenow1.substring(4, 6) + "-" + datenow1.substring(6, 8);
                    mMap.put("vDate", datenow);
                    mMap.put("paymentmethod", mRes1.getString("EPPA_PAMT"));
                    String duedate1 = mRes1.getString("EPPA_DUEDT");
                    String duedate = duedate1.substring(0, 4) + "-" + duedate1.substring(4, 6) + "-" + duedate1.substring(6, 8);
                    mMap.put("vDuedate", duedate);
                    mMap.put("deductdesc", mRes1.getString("EPRA_ADVREF"));
                    mMap.put("deductamount", mRes1.getString("EPRA_ADVAMT"));
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getsupplierlist(String cono) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "SELECT IDSUNO||' : '||IDSUNM   \n"
                        + "FROM " + dbM3Name + ".CIDMAS \n"
                        + " WHERE  IDSTAT = '20'\n"
                        + " AND IDCONO='" + cono + "'   \n"
                        + " ORDER BY IDSUNO";
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("supplierlist", mRes1.getString(1).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getitemlist(String cono) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "SELECT TRIM(MMITNO)||' : ' ||MMITDS    \n"
                        + "FROM " + dbM3Name + ".MITMAS \n"
                        + "WHERE SUBSTR(MMITNO,1,2) = 'OH' \n"
                        + "AND MMCONO='" + cono + "' \n"
                        + "AND MMITTY IN ('OH') \n"
                        + " ORDER BY MMITNO";
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("itemlist", mRes1.getString(1).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getdetailfromgrn(String cono, String divi, String grn) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String supplier = "";
        String costcenter = "";
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "SELECT GRN,IDSUNO||' : '||IDSUNM AS IASUNO,TRIM(EAAITM)||' : '||TRIM(EATX40) AS IBCOCE\n"
                        + "FROM " + dbname + ".sum_grn01\n"
                        + "LEFT JOIN " + dbM3Name + ".CIDMAS\n"
                        + "ON IDCONO = IACONO\n"
                        + "LEFT JOIN " + dbM3Name + ".FCHACC \n"
                        + "ON EACONO = IACONO\n"
                        + "AND EADIVI = '" + divi + "'\n"
                        + "WHERE GRN = '" + grn + "'\n"
                        + "AND IACONO  = '" + cono + "'\n"
                        + "AND IDSTAT = '20'\n"
                        + "AND IDSUNO = IASUNO\n"
                        + "AND EAAITP =  2 \n"
                        + "AND EAAITM = IBCOCE";
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("supplier", mRes1.getString("IASUNO").trim());
                    mMap.put("costcenter", mRes1.getString("IBCOCE").trim());

                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray showCompletedSRN(String cono, String app, String costcenter, String divi, String user) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String costcenter2 = null;
        if (costcenter.contains(":")) {
            String[] itemalldats = costcenter.split(":");
            costcenter2 = itemalldats[0];

        }
        String query1 = null;
        String sqlgrn = "";
        switch (cono) {
            case "10":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_GRNPAY)";
                break;
            case "100":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYAT)";
                break;
            case "300":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYMA)";
                break;
            case "200":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYBA)";
                break;
            case "400":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYFC)";
                break;
            case "500":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYNS)";
                break;
            case "600":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYWT)";
                break;
            default:
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_GRNPAY)";
                break;
        }
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                if (app.equals("GRN")) {
                    query1 = "SELECT GRN||' : '||ICSUDO AS GRN,ICTRDT\n"
                            + "FROM " + dbname + ".sum_grn01 a\n"
                            + "WHERE DAYS (CURRENT_DATE) - DAYS (DATE(\n"
                            + "    SUBSTR(CHAR(ICTRDT), 1, 4) || '-' ||\n"
                            + "    SUBSTR(CHAR(ICTRDT), 5, 2) || '-' ||\n"
                            + "    SUBSTR(CHAR(ICTRDT), 7, 2)\n"
                            + ")) < 31\n"
                            + "--AND SUBSTRING(GRN,0,3) IN ('20','48','45','42','44','43','99','98')\n"
                            //                            + "and a.grn not in (select grnp_grn  FROM BRLDTA0100.EPR_GRNPAY)\n"
                            + sqlgrn + "\n"
                            + "AND a.grn NOT IN (SELECT GRNP_GRN FROM " + dbname + ".PAYMENTLINEGRN a\n"
                            + "JOIN  " + dbname + ".PAYMENTHEAD b ON a.GRNP_NO = b.EPPA_NO\n"
                            + "AND b.EPRA_STAT = '10' \n"
                            + "AND b.EPRA_STAT != '99'\n"
                            + "AND GRNP_CONO = b.EPPA_CONO \n"
                            + "AND GRNP_DIVI = b.EPPA_DIVI AND b.EPPA_CONO =" + cono + " AND b.EPPA_DIVI = " + divi + ")\n"
                            + "AND IBCOCE = TRIM('" + costcenter2 + "')\n"
                            + "AND IACONO = " + cono + "\n"
                            + "ORDER BY ICTRDT DESC,GRN DESC";

                } else {
                    query1 = "SELECT EPRH_PHNO||' : '||EPRH_PURPOS,EPRH_PURPOS \n"
                            + "FROM " + dbname + ".SERVICEHEAD  AS A\n"
                            + "WHERE A.eprh_stat = '50'\n"
                            + "AND A.EPRH_CONO = '" + cono + "'\n"
                            + "AND a.EPRH_DIVI = '" + divi + "'\n"
                            + "AND a.EPRH_PHNO NOT IN (SELECT B.EPRA_PHNO	\n"
                            + "FROM " + dbname + ".PAYMENTLINE AS B,\n"
                            + "" + dbname + ".PAYMENTHEAD  AS C\n"
                            + "WHERE A.EPRH_CONO = B.EPPA_CONO \n"
                            + "AND B.EPPA_DIVI = A.EPRH_DIVI \n"
                            + "AND  C.EPPA_CONO = B.EPPA_CONO \n"
                            + "AND C.EPPA_DIVI = B.EPPA_DIVI\n"
                            + "AND C.EPPA_NO = B.EPPA_NO \n"
                            + "AND A.EPRH_PHNO = B.EPRA_PHNO \n"
                            + "AND C.EPPA_CONO  ='" + cono + "' \n"
                            + "AND C.EPPA_DIVI  ='" + divi + "'\n"
                            + "AND C.EPRA_STAT != 99)\n"
                            + "AND a.EPRH_TYPE = '" + app + "' \n"
                            + "AND UPPER(EPRH_REQBY)  = '" + user + "'";
                }

                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("RQNo", mRes1.getString(1).trim());
//                    mMap.put("Label", mRes1.getString(2).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static Boolean checkAuth(String user) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        boolean auth = false;
//        if (costcenter.contains(":")) {
//            String[] itemalldats = costcenter.split(":");
//            costcenter2 = itemalldats[0];
//
//        }
        String query1 = null;
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                query1 = "SELECT TRIM(ST_COSTC)  FROM " + dbname + ".STAFFLIST\n"
                        + "WHERE UPPER(ST_N6L3)  = UPPER('" + user + "')";
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    String cost = mRes1.getString(1).trim();
                    if (cost.equals("S8")) {
                        auth = true;
                    }
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return auth;

    }

    public static JSONArray checkPRN(String cono, String divi, String PRNcode, String app, String costcenter) throws Exception {
        boolean check = true;
        String itemcode = null;
        String existornot = "notexist";
        String query1 = null;
        if (PRNcode.contains(":")) {
            String[] itemalldats = PRNcode.split(":");
            PRNcode = itemalldats[0];

        }
//        else {
////            check = false;
//            existornot = "notexist";
//        }

        String sqlgrn = "";
        switch (cono) {
            case "10":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_GRNPAY)";
                break;
            case "100":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYAT)";
                break;
            case "300":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYMA)";
                break;
            case "200":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYBA)";
                break;
            case "400":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYFC)";
                break;
            case "500":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYNS)";
                break;
            case "600":
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_LPAYWT)";
                break;
            default:
                sqlgrn = "and a.grn not in (select grnp_grn  FROM " + dbname + ".EPR_GRNPAY)";
                break;
        }

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                if (app.equals("GRN")) {
                    query1 = "SELECT CASE WHEN COUNT(GRN) > 0\n"
                            + "THEN 'EXIST' ELSE 'NOTEXIST' END AS EXISTANCE\n"
                            + "FROM " + dbname + ".SUM_GRN01 A\n"
                            + "WHERE ICTRDT > YEAR(CURRENT_DATE) - 2\n"
                            //                            + "AND A.GRN NOT IN (SELECT GRNP_GRN  FROM BRLDTA0100.EPR_GRNPAY)\n"
                            + sqlgrn + "\n"
                            + "AND A.GRN NOT IN (SELECT  GRNP_GRN FROM " + dbname + ".PAYMENTLINEGRN A\n"
                            + "JOIN  " + dbname + ".PAYMENTHEAD B ON A.GRNP_NO = B.EPPA_NO\n"
                            + "AND B.EPRA_STAT = '10'"
                            + "AND GRNP_CONO = EPPA_CONO AND GRNP_DIVI = EPPA_DIVI\n"
                            + "AND EPRA_REF2 = 'GRN' AND GRNP_CONO = '" + cono + "' AND  GRNP_DIVI = '" + divi + "')\n"
                            + "AND VARCHAR(GRN, 255) = '" + PRNcode + "'";
                } else {
                    query1 = "SELECT CASE WHEN count(EPRH_PHNO) > 0 THEN 'EXIST' ELSE 'NOTEXIST' END AS existance\n"
                            + "FROM " + dbname + ".SERVICEHEAD A WHERE eprh_stat = '50'\n"
                            + "AND EPRH_CONO = " + cono + "\n"
                            + "AND  EPRH_DIVI = " + divi + "\n"
                            + "AND EPRH_PHNO NOT IN (SELECT B.EPRA_PHNO	\n"
                            + "FROM " + dbname + ".PAYMENTLINE AS B,\n"
                            + "" + dbname + ".PAYMENTHEAD  AS C\n"
                            + "WHERE A.EPRH_CONO = B.EPPA_CONO \n"
                            + "AND B.EPPA_DIVI = A.EPRH_DIVI \n"
                            + "AND  C.EPPA_CONO = B.EPPA_CONO \n"
                            + "AND C.EPPA_DIVI = B.EPPA_DIVI\n"
                            + "AND C.EPPA_NO = B.EPPA_NO \n"
                            + "AND A.EPRH_PHNO = B.EPRA_PHNO \n"
                            + "AND C.EPPA_CONO  ='" + cono + "' \n"
                            + "AND C.EPPA_DIVI  ='" + divi + "'\n"
                            + "AND C.EPRA_STAT != 99)\n"
                            + "AND VARCHAR(EPRH_PHNO) ='" + PRNcode + "'";
                }
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    String result = mRes1.getString(1).trim();

                    if (result.equals("EXIST")) {
                        existornot = "exist";
                    } else if (result.equals("NOTEXIST")) {
                        existornot = "notexist";
                    }
                }
                Map<String, Object> mMap = new HashMap<>();
                mMap.put("result", existornot);
                mJSonArr.put(mMap);

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray checkITEM(String cono, String divi, String itemcode) throws Exception {
        boolean check = true;
        String itemcode2 = "";
        String existornot = null;
        String query1 = null;
        if (itemcode.contains(":")) {
            String[] itemalldats = itemcode.split(":");
            itemcode = itemalldats[0];

        }

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                query1 = " SELECT CASE WHEN COUNT(TRIM(MMITNO)) > 0\n"
                        + " THEN 'EXIST' ELSE 'NOTEXIST' END AS EXISTANCE\n"
                        + "FROM " + dbM3Name + ".MITMAS \n"
                        + "WHERE TRIM(MMITNO) =  '" + itemcode + "'"
                        + "AND MMCONO = '" + cono + "'";

                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    String result = mRes1.getString("Existance").trim();

                    if (result.equals("EXIST")) {
                        existornot = "exist";
                    } else if (result.equals("NOTEXIST")) {
                        existornot = "notexist";
                    }
                }

                Map<String, Object> mMap = new HashMap<>();
                mMap.put("result", existornot);
                mJSonArr.put(mMap);

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray showLinkedNo(String cono, String orderno) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "SELECT A.EPRA_PHNO,B.EPRH_PURPOS FROM " + dbname + ".PAYMENTLINE A\n"
                        + "JOIN " + dbname + ".SERVICEHEAD b ON B.EPRH_PHNO = A.EPPA_NO\n"
                        + "WHERE A.EPPA_NO =" + orderno + " AND EPRH_CONO = '10'";
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("RQNo", mRes1.getString(1).trim());
                    mMap.put("DataDetail", mRes1.getString(1).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getordernum(String user, String app, String cono, String divi) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "select EPRH_PHNO,EPRH_PHNO || ' | ' ||EPRH_PURPOS \n"
                        + " FROM " + dbname + ".SERVICEHEAD \n"
                        + " WHERE upper(EPRH_REQBY) like upper('%" + user + "%') \n"
                        + " AND EPRH_STAT NOT IN ('50','99') \n"
                        + " AND EPRH_TYPE = '" + app + "'\n"
                        + " AND EPRH_CONO = '" + cono + "'\n"
                        + " AND EPRH_DIVI ='" + divi + "' \n"
                        + "ORDER BY EPRH_PHNO";
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vOrderdata", mRes1.getString(1).trim());
                    mMap.put("vOrdernum", mRes1.getString(2).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getordernumPRV(String app, String cono, String divi, String user) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query1 = "select EPPA_NO,EPPA_NO||' | '||EPRA_PARM S\n"
                        + "FROM " + dbname + ".PAYMENTHEAD\n"
                        + "WHERE   upper(EPRA_REQBY) like upper('%" + user + "%')   \n"
                        + "and EPRA_REF2='" + app + "' AND EPRA_STAT NOT IN('10','99') AND EPPA_CONO = '" + cono + "' AND EPPA_DIVI = '" + divi + "'";
                System.out.println("Getsupplier\n" + query1);
                ResultSet mRes1 = stmt.executeQuery(query1);
                while (mRes1.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vOrderdata", mRes1.getString(1).trim());
                    mMap.put("vOrdernum", mRes1.getString(2).trim());
                    mJSonArr.put(mMap);
                }
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Company() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT CCCONO,CCDIVI,CCCONM,'\"'|| TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCCONM) || '\"' AS COMPANY\n"
                        + "FROM " + dbM3Name + ".CMNDIV\n"
                        + "WHERE CCDIVI != ''\n"
                        + "ORDER BY CCCONO";
                System.out.println("SelectCompany\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("CCCONO", mRes.getString(1).trim());
                    mMap.put("CCDIVI", mRes.getString(2).trim());
                    mMap.put("CCCONM", mRes.getString(3).trim());
                    mMap.put("COMPANY", mRes.getString(4).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    static private void callPPS200A(String xPoNo, String xPODATE, String tv1, String tv3, String tv5, String tv7, String tv8, String tv9, String tv10, String cono, String divi) {

        try {

            //m3id = "MPM_1A1";
            //m3id = "MVXSECOFR"; 
            m3id = "PUR_1A1";
            m3pw = "lawson@90";
            appServer = "192.200.9.190";
            appPort = 16105;
            int i = 0;

            MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
            mne.logInToM3(Integer.parseInt(cono), divi, m3id, m3pw);

            if (!mne.logInToM3(Integer.parseInt(cono), divi, m3id, m3pw)) {
                System.out.println(" Can not login to M3 system");
            }

            String PPS200ID = mne.runM3Pgm("PPS200");
            System.out.println("PPS200_ID:" + PPS200ID);

            if ((PPS200ID).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม PPS200 ได้");
            }
            //---------------------------------------------------------
            if (mne.panel.equals("PPS200/A")) {

                mne.setField("WAFACI", "1A1");
                mne.setField("WAWHLO", "A31");
                mne.setField("WAPUNO", xPoNo);
                mne.setField("WASUNO", "");
                mne.setField("WADWDT", xPODATE);
                mne.setField("WWPSEQ", "T");
                mne.selectOption("2");

                mne.setField("CMDTP", "KEY");
                mne.setField("FCS", "WWPSEQ");
                mne.setField("Txei", "0");

                String tv2 = "", tv4 = "", tv6 = "";
                //mne.setField("TX60",xPoNo+ "\n" + tv1 + "\n" + tv2 + "\n" + tv3 + "\n" + tv4 + "\n" + tv5 + "\n" + tv6 + "\n" + tv7 + "\n" + tv8 + "\n" + tv9 + "\n" + tv10 + "\n");
                mne.setField("TX60", tv1 + "\n" + tv2 + "\n" + tv3 + "\n" + tv4 + "\n" + tv5 + "\n" + tv6 + "\n" + tv7 + "\n" + tv8 + "\n" + tv9 + "\n" + tv10 + "\n");
                mne.setField("Lncd", "GB");
                mne.pressKey(MNEProtocol.KeyEnter);
                mne.pressKey(MNEProtocol.KeyF03);

                mne.pressKey(MNEProtocol.KeyF03);
                mne.closeProgram(PPS200ID);
            }
            //--------------------------------------------------------
            mne.pressKey(MNEProtocol.KeyF03);
            mne.closeProgram(PPS200ID);

        } catch (Exception e) {
            if (sock != null) {
                System.out.println("ERR: " + sock.mvxGetLastError());
            }
        }
    }

    public static Date GetDecmalTodate(int startDateString) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        Date startDate;
        try {
            String DateString = String.valueOf(startDateString);
            startDate = df.parse(DateString);
            String newDateString = df.format(startDate);
            //   System.out.println(newDateString);
            return startDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray getItemfromRqNo(String rqnum, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT EPRL_PHNO, EPRL_PNLI, TRIM(EPRL_ITNO)||' : '||MMITDS AS EPRL_ITNO, EPRL_DESC, EPRL_QTY,    \n"
                        + "EPRL_UPRICE, EPRL_VTCD,EPRL_QTY*EPRL_UPRICE AS TOTALBVAT,  EPRL_VAT, EPRL_AMT, EPRL_REM1, EPRL_STAT  \n"
                        + "FROM " + dbname + ".SERVICELINE AS A," + dbM3Name + ".MITMAS AS B\n"
                        + "WHERE A.EPRL_CONO = " + cono + "\n"
                        + "AND A.EPRL_DIVI = " + divi + "\n"
                        + "AND EPRL_PHNO ='" + rqnum + "'  \n"
                        + "AND MMITNO = EPRL_ITNO\n"
                        + "AND B.MMCONO = A.EPRL_CONO \n"
                        + "ORDER BY EPRL_PNLI";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    String value1 = mRes.getString("EPRL_PNLI");
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String value2 = mRes.getString("EPRL_ITNO");
                    if (value2 != null) {
                        value2 = value2.trim();
                    }
                    String value3 = mRes.getString("EPRL_DESC");
                    if (value3 != null) {
                        value3 = value3.trim();
                    }
                    String value4 = mRes.getString("EPRL_QTY");
                    if (value4 != null) {

                        value4 = value4.trim();
                    }
                    String value5 = mRes.getString("EPRL_UPRICE");
                    if (value5 != null) {
                        value5 = value5.trim();
                    }
                    String value6 = mRes.getString("EPRL_VTCD");
                    if (value6 != null) {
                        value6 = value6.trim();
                    }
                    String value7 = mRes.getString("TotalBVat");
                    if (value7 != null) {
                        value7 = value7.trim();
                    }
                    String value8 = mRes.getString("EPRL_VAT");
                    if (value8 != null) {
                        value8 = value8.trim();
                    }
                    String value9 = mRes.getString("EPRL_AMT");
                    if (value9 != null) {
                        value9 = value9.trim();
                    }
                    Map<String, Object> mMap = new HashMap<>();
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
//                    String value4new = String.valueOf(new DecimalFormat("##.##").format(Math.round(parseFloat(value4) * 100.0) / 100.0));
                    String value4new = String.valueOf(decimalFormat.format((Double.parseDouble(value4) * 1000) / 1000));
                    String value5new = String.valueOf(decimalFormat.format((Double.parseDouble(value5) * 1000) / 1000));
//                    String value5new = String.valueOf(new DecimalFormat("##.##").format(parseFloat(value5)));
                    String value7new = String.valueOf(decimalFormat.format((Double.parseDouble(value7) * 1000) / 1000));
//                    String value7new = String.valueOf(new DecimalFormat("##.##").format(parseFloat(value7)));
                    mMap.put("RNo", value1);
                    mMap.put("Ritemcode", value2);
                    mMap.put("Rdesciprion", value3);
                    mMap.put("Rqty", value4new);
                    mMap.put("Rupprice", value5new);
                    mMap.put("Rvatpercentage", value6);
                    mMap.put("Rtotalvat", value7new);
                    mMap.put("Rvat", value8);
                    mMap.put("Ramount", value9);
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getServicehistory(String startDate,
            String endDate, String cono,
            String divi, String Status,
            String user, String mode,
            String serviceno) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String newStartDate = "";
        String newEndDate = "";
        if (mode.equals("Date")) {
            newStartDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
            newEndDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);
        }
        try {
            if (conn != null) {
                //-------------- GET HISTORY FOR THE ----------------------------
                Statement stmt = conn.createStatement();
                String query = "";
                if (mode.equals("Date")) {
                    query = "SELECT F.EGVONO as  EGVONO,D.*\n"
                            + "FROM (\n"
                            + "SELECT EPRH_PHNO,ROW_NUMBER() OVER(PARTITION BY A.EPRH_PHNO) as ROWNO,A.EPRH_TYPE\n"
                            + "--,A.EPRH_PHNO\n"
                            + ",B.EPPA_NO,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter,EPRH_PURPOS\n"
                            + ",EPRH_BU,A.EPRH_INVDT ,EPRH_APTNO,EPRH_TOTAL\n"
                            + ",EPRH_DISC,EPRH_VTCD,EPRH_STAT,IDSUNO||' : '||IDSUNM AS Supplier,EPRH_INVSU,EPRH_REM1,\n"
                            + "--EPRH_INVDT,\n"
                            + "CASE WHEN EPRH_STAT = 20 THEN 'Normal' \n"
                            + "WHEN EPRH_STAT = 50 THEN 'Submitted' \n"
                            + "WHEN EPRH_STAT = 99 THEN 'Canceled' \n"
                            + "END AS STATUS\n"
                            + "FROM " + dbname + ".SERVICEHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = EPRH_DIVI\n"
                            + "AND A.EPRH_CONO = EACONO \n"
                            + "AND EAAITM = EPRH_COCE\n"
                            + "AND EAAITP =  2\n"
                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
                            + "ON IDCONO = EPRH_CONO AND EPRH_SUNO = IDSUNO\n"
                            + "LEFT JOIN  " + dbname + ".PAYMENTLINE B\n"
                            + "ON B.EPRA_PHNO = A.EPRH_PHNO\n"
                            + "AND A.EPRH_CONO = B.EPPA_CONO\n"
                            + "AND A.EPRH_DIVI = B.EPPA_DIVI\n"
                            + "WHERE EPRH_INVDT BETWEEN  " + newStartDate + " AND " + newEndDate + "\n"
                            + "AND UPPER(EPRH_OWAPP) LIKE '" + user + "'\n"
                            + "AND EPRH_CONO = " + cono + " AND EPRH_DIVI = " + divi + "\n";
                    if (!Status.equals("")) {
                        query += " AND EPRH_STAT = '" + Status + "'";
                    }
                    query = query + ") D LEFT JOIN (\n"
                            + " SELECT EGVONO,EGCONO,EGDIVI,EGYEA4,EGAIT4\n"
                            + " FROM  M3FDBPRD.FGLEDG\n"
                            + " WHERE EGTRCD = 40\n"
                            + " AND EGCONO = " + cono + "\n"
                            + " AND EGDIVI = " + divi + "\n"
                            + ") F ON F.EGYEA4 = SUBSTRING(D.EPRH_INVDT,1,4) AND F.EGAIT4 = CHAR(D.EPRH_PHNO) AND F.EGAIT4 =  CHAR(D.EPRH_PHNO)\n"
                            + "ORDER BY D.EPRH_INVDT ASC,EPRH_PHNO";

                    // CHANGE QUERY TO NEW ONE
//                    query = "SELECT EPRH_PHNO,ROW_NUMBER() OVER(PARTITION BY A.EPRH_PHNO) as ROWNO,A.EPRH_TYPE,A.EPRH_PHNO,B.EPPA_NO,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter,EPRH_PURPOS\n"
//                            + ",EPRH_BU,EPRH_INVDT ,EPRH_APTNO,EPRH_TOTAL\n"
//                            + ",EPRH_DISC,EPRH_VTCD,EPRH_STAT,IDSUNO||' : '||IDSUNM AS Supplier,EPRH_INVSU,EPRH_REM1,EPRH_INVDT,\n"
//                            + "CASE WHEN EPRH_STAT = 20 THEN 'Normal' \n"
//                            + "WHEN EPRH_STAT = 50 THEN 'Submitted' \n"
//                            + "WHEN EPRH_STAT = 99 THEN 'Canceled' \n"
//                            + "END AS STATUS\n"
//                            + "FROM " + dbname + ".SERVICEHEAD A\n"
//                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = EPRH_DIVI\n"
//                            + "AND A.EPRH_CONO = EACONO \n"
//                            + "AND EAAITM = EPRH_COCE\n"
//                            + "AND EAAITP =  2\n"
//                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
//                            + "ON IDCONO = EPRH_CONO AND EPRH_SUNO = IDSUNO\n"
//                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B\n"
//                            + "ON B.EPRA_PHNO = A.EPRH_PHNO\n"
//                            + "AND A.EPRH_CONO = B.EPPA_CONO\n"
//                            + "AND A.EPRH_DIVI = B.EPPA_DIVI\n"
//                            + "WHERE EPRH_INVDT BETWEEN " + newStartDate + " AND " + newEndDate + "\n"
//                            + "AND UPPER(EPRH_OWAPP) LIKE '" + user + "'\n"
//                            + "AND EPRH_CONO = " + cono + " AND EPRH_DIVI = " + divi + "\n";
//                    query = "SELECT EPRH_TYPE,EPRH_PHNO,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter,EPRH_PURPOS\n"
//                            + ",EPRH_BU,EPRH_INVDT ,EPRH_APTNO,EPRH_TOTAL\n"
//                            + ",EPRH_DISC,EPRH_VTCD,EPRH_STAT,IDSUNO||' : '||IDSUNM AS Supplier,EPRH_INVSU,EPRH_REM1,EPRH_INVDT,\n"
//                            + "CASE WHEN EPRH_STAT = 20 THEN 'Normal' \n"
//                            + "WHEN EPRH_STAT = 50 THEN 'Submitted' \n"
//                            + "WHEN EPRH_STAT = 99 THEN 'Canceled' \n"
//                            + "END AS STATUS\n"
//                            + "FROM " + dbname + ".SERVICEHEAD\n"
//                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = EPRH_DIVI\n"
//                            + "AND EPRH_CONO = EACONO \n"
//                            + "AND EAAITM = EPRH_COCE\n"
//                            + "AND EAAITP =  2\n"
//                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
//                            + "ON IDCONO = EPRH_CONO AND EPRH_SUNO = IDSUNO\n"
//                            + "WHERE EPRH_INVDT BETWEEN " + newStartDate + " AND " + newEndDate + "\n"
//                            + "AND UPPER(EPRH_OWAPP) LIKE '" + user + "'\n"
//                            + "AND EPRH_CONO =" + cono + " AND EPRH_DIVI = " + divi + "\n";
                } else if (mode.equals("Num")) {
                    query = "SELECT F.EGVONO as  EGVONO,D.*\n"
                            + "FROM (\n"
                            + "SELECT EPRH_PHNO,ROW_NUMBER() OVER(PARTITION BY A.EPRH_PHNO) as ROWNO,A.EPRH_TYPE\n"
                            + "--,A.EPRH_PHNO\n"
                            + ",B.EPPA_NO,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter,EPRH_PURPOS\n"
                            + ",EPRH_BU,A.EPRH_INVDT ,EPRH_APTNO,EPRH_TOTAL\n"
                            + ",EPRH_DISC,EPRH_VTCD,EPRH_STAT,IDSUNO||' : '||IDSUNM AS Supplier,EPRH_INVSU,EPRH_REM1,\n"
                            + "--EPRH_INVDT,\n"
                            + "CASE WHEN EPRH_STAT = 20 THEN 'Normal' \n"
                            + "WHEN EPRH_STAT = 50 THEN 'Submitted' \n"
                            + "WHEN EPRH_STAT = 99 THEN 'Canceled' \n"
                            + "END AS STATUS\n"
                            + "FROM " + dbname + ".SERVICEHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = EPRH_DIVI\n"
                            + "AND A.EPRH_CONO = EACONO \n"
                            + "AND EAAITM = EPRH_COCE\n"
                            + "AND EAAITP =  2\n"
                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
                            + "ON IDCONO = EPRH_CONO AND EPRH_SUNO = IDSUNO\n"
                            + "LEFT JOIN  " + dbname + ".PAYMENTLINE B\n"
                            + "ON B.EPRA_PHNO = A.EPRH_PHNO\n"
                            + "AND A.EPRH_CONO = B.EPPA_CONO\n"
                            + "AND A.EPRH_DIVI = B.EPPA_DIVI\n"
                            + "WHERE EPRH_PHNO = '" + serviceno + "'\n"
                            + "AND UPPER(EPRH_OWAPP) LIKE '" + user + "'\n"
                            + "AND EPRH_CONO =" + cono + " AND EPRH_DIVI =" + divi + "\n"
                            + ") D LEFT JOIN (\n"
                            + " SELECT EGVONO,EGCONO,EGDIVI,EGYEA4,EGAIT4\n"
                            + " FROM  M3FDBPRD.FGLEDG\n"
                            + " WHERE EGTRCD = 40\n"
                            + " AND EGCONO =" + cono + "\n"
                            + " AND EGDIVI =" + divi + " \n"
                            + ") F ON F.EGYEA4 = SUBSTRING(D.EPRH_INVDT,1,4) AND F.EGAIT4 = CHAR(D.EPRH_PHNO) AND F.EGAIT4 =  CHAR(D.EPRH_PHNO)\n"
                            + "ORDER BY D.EPRH_INVDT ASC,EPRH_PHNO \n";
//                    query = "SELECT EPRH_PHNO,ROW_NUMBER() OVER(PARTITION BY A.EPRH_PHNO) as ROWNO,A.EPRH_TYPE,A.EPRH_PHNO,B.EPPA_NO,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter,EPRH_PURPOS\n"
//                            + ",EPRH_BU,EPRH_INVDT ,EPRH_APTNO,EPRH_TOTAL\n"
//                            + ",EPRH_DISC,EPRH_VTCD,EPRH_STAT,IDSUNO||' : '||IDSUNM AS Supplier,EPRH_INVSU,EPRH_REM1,EPRH_INVDT,\n"
//                            + "CASE WHEN EPRH_STAT = 20 THEN 'Normal' \n"
//                            + "WHEN EPRH_STAT = 50 THEN 'Submitted' \n"
//                            + "WHEN EPRH_STAT = 99 THEN 'Canceled' \n"
//                            + "END AS STATUS\n"
//                            + "FROM " + dbname + ".SERVICEHEAD A\n"
//                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = EPRH_DIVI\n"
//                            + "AND A.EPRH_CONO = EACONO \n"
//                            + "AND EAAITM = EPRH_COCE\n"
//                            + "AND EAAITP =  2\n"
//                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
//                            + "ON IDCONO = EPRH_CONO AND EPRH_SUNO = IDSUNO\n"
//                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B\n"
//                            + "ON B.EPRA_PHNO = A.EPRH_PHNO\n"
//                            + "AND A.EPRH_CONO = B.EPPA_CONO\n"
//                            + "AND A.EPRH_DIVI = B.EPPA_DIVI\n"
//                            + "WHERE EPRH_PHNO = '" + serviceno.trim() + "'\n"
//                            + "AND UPPER(EPRH_OWAPP) LIKE '" + user + "'\n"
//                            + "AND EPRH_CONO =" + cono + " AND EPRH_DIVI = " + divi + "\n";
                }

//                query += "ORDER BY SUBSTRING(EPRH_PHNO,4,5)";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    String rowno = mRes.getString("ROWNO");
                    if (rowno != null) {
                        rowno = rowno.trim();
                    }
                    String value1 = mRes.getString("EPRH_PHNO");
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String paymentno = mRes.getString("EPPA_NO");
                    if (paymentno != null) {
                        paymentno = paymentno.trim();
                    }
                    String value2 = mRes.getString("EPRH_PURPOS");
                    if (value2 != null) {
                        value2 = value2.trim();
                    }
                    String value3 = mRes.getString("EPRH_TYPE");
                    if (value3 != null) {
                        value3 = value3.trim();
                    }
                    String value4 = mRes.getString("COSTCENTER");

                    String value5 = mRes.getString("Supplier");
                    if (value5 != null) {
                        value5 = value5.trim();
                    }
                    String value6 = mRes.getString("EPRH_INVSU");
                    if (value6 != null) {
                        value6 = value6.trim();
                    }
                    String value7 = mRes.getString("EPRH_REM1");
                    if (value7 != null) {
                        value7 = value7.trim();
                    }
                    String value8 = mRes.getString("EPRH_INVDT");
                    if (value8 != null) {
                        value8 = value8.trim();
                    }
                    String value9 = mRes.getString("EPRH_TOTAL");
                    if (value9 != null) {
                        value9 = value9.trim();
                    }
                    String value10 = mRes.getString("EPRH_DISC");
                    if (value10 != null) {
                        value10 = value10.trim();
                    }
                    String value11 = mRes.getString("EPRH_VTCD");
                    if (value11 != null) {
                        value11 = value11.trim();
                    }
                    String Value12 = mRes.getString("STATUS");
                    if (Value12 != null) {
                        Value12 = Value12.trim();
                    }
                    String Voucher = mRes.getString("EGVONO");
                    if (Voucher != null) {
                        Voucher = Voucher.trim();
                    }
                    //GET COST CENTER

                    Map<String, Object> mMap = new HashMap<>();

                    mMap.put("RSERVICENO", value1);
                    mMap.put("RROWNO", rowno);
                    mMap.put("RPAYMENTNO", paymentno);
                    mMap.put("RPURPOSE", value2);
                    mMap.put("RTYPE", value3);
                    mMap.put("RCOSTCENTER", value4);
                    mMap.put("RSUPPLIER", value5);
                    mMap.put("RSUPINVOICE", value6);
                    mMap.put("RREMARK", value7);
                    mMap.put("RINVDATE", value8);
                    mMap.put("RTOTAL", value9);
                    mMap.put("RDiscount", value10);
                    mMap.put("RVat", value11);
                    mMap.put("RStatus", Value12);
                    mMap.put("RVOUCHER", Voucher);
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getPaymenthistory(String startDate, String endDate,
            String cono, String divi,
            String Status, String user,
            String mode, String prvno) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String newStartDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
        String newEndDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);
        String query = "";
        try {
            if (conn != null) {
                //-------------- GET HISTORY FOR THE ----------------------------
                Statement stmt = conn.createStatement();
                if (mode.equals("Date")) {
                    query = "SELECT F.EGVONO as  EGVONO,D.*,CHAR(D.SERVICES)\n"
                            + "--,CHAR(D.SRN_NO)\n"
                            + "FROM (\n"
                            + "SELECT A.EPPA_NO,ROW_NUMBER() OVER(PARTITION BY A.EPPA_NO) AS ROWNUMBER,COALESCE(B.EPRA_PHNO,C.GRNP_GRN) AS SERVICES,A.EPRA_REF2,A.EPRA_PARM,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter\n"
                            + ",IDSUNO||' : '||IDSUNM AS Supplier,A.EPPA_DUEDT,EPPA_DATE,\n"
                            + "CASE WHEN A.EPPA_PAMT = 1 THEN 'Cash'\n"
                            + "WHEN A.EPPA_PAMT = 2 THEN 'Cheque No.'\n"
                            + "WHEN A.EPPA_PAMT = 3 THEN 'Bank Transfer'\n"
                            + "END AS PAYMENTMETHOD\n"
                            + ",A.EPRA_ADVREF,A.EPRA_ADVAMT,\n"
                            + "CASE WHEN A.EPRA_STAT = '00' THEN 'NORMAL'\n"
                            + "WHEN A.EPRA_STAT = '10' THEN 'SUBMITTED'\n"
                            + "WHEN A.EPRA_STAT = '99' THEN 'CANCALED' END AS STATUS\n"
                            + "FROM  " + dbname + ".PAYMENTHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = A.EPPA_DIVI\n"
                            + "AND EACONO =  A.EPPA_CONO\n"
                            + "AND EAAITM = A.EPPA_COCE\n"
                            + "AND EAAITP =  2\n"
                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
                            + "ON IDCONO = A.EPPA_CONO \n"
                            + "AND A.EPRA_SUNO = IDSUNO\n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B\n"
                            + "ON B.EPPA_NO = A.EPPA_NO\n"
                            + "AND A.EPPA_CONO = B.EPPA_CONO\n"
                            + "AND A.EPPA_DIVI = B.EPPA_DIVI \n"
                            + "LEFT JOIN \n"
                            + "" + dbname + ".PAYMENTLINEGRN C\n"
                            + "ON A.EPPA_CONO = C.GRNP_CONO\n"
                            + "AND A.EPPA_DIVI = C.GRNP_DIVI\n"
                            + "AND C.GRNP_NO = A.EPPA_NO\n"
                            + "WHERE A.EPPA_DUEDT BETWEEN " + newStartDate + "\n"
                            + "AND " + newEndDate + "\n"
                            + "AND UPPER(A.EPRA_REQBY) LIKE '" + user + "'\n"
                            + "AND A.EPPA_CONO = " + cono + " AND A.EPPA_DIVI =" + divi + "\n";
                    if (!Status.equals("")) {
                        query += "AND A.EPRA_STAT = '" + Status + "'";
                    }
                    query = query + ") D LEFT JOIN (\n"
                            + " SELECT EGVONO,EGCONO,EGDIVI,EGYEA4,EGAIT4\n"
                            + " FROM  M3FDBPRD.FGLEDG\n"
                            + " WHERE EGTRCD = 40\n"
                            + " AND EGCONO =" + cono + "\n"
                            + " AND EGDIVI =" + divi + "\n"
                            + ") F ON F.EGYEA4 = SUBSTRING(D.EPPA_DATE,1,4) AND F.EGAIT4 = CHAR(D.SERVICES) \n"
                            + "--AND F.EGAIT4 =  CHAR(D.SRN_NO)\n"
                            + "ORDER BY EPPA_DATE ASC,EPPA_NO";

                    // OLD MONITORING TO FIX THE ROLLBACK
//                    query = "SELECT A.EPPA_NO,ROW_NUMBER() OVER(PARTITION BY A.EPPA_NO) AS ROWNUMBER,COALESCE(B.EPRA_PHNO,C.GRNP_GRN) AS SERVICES,A.EPRA_REF2,A.EPRA_PARM,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter\n"
//                            + ",IDSUNO||' : '||IDSUNM AS Supplier,A.EPPA_DUEDT,\n"
//                            + "CASE WHEN A.EPPA_PAMT = 1 THEN 'Cash'\n"
//                            + "WHEN A.EPPA_PAMT = 2 THEN 'Cheque No.'\n"
//                            + "WHEN A.EPPA_PAMT = 3 THEN 'Bank Transfer'\n"
//                            + "END AS PAYMENTMETHOD\n"
//                            + ",A.EPRA_ADVREF,A.EPRA_ADVAMT,\n"
//                            + "CASE WHEN A.EPRA_STAT = '00' THEN 'NORMAL'\n"
//                            + "WHEN A.EPRA_STAT = '10' THEN 'SUBMITTED'\n"
//                            + "WHEN A.EPRA_STAT = '99' THEN 'CANCALED' END AS STATUS\n"
//                            + "FROM  " + dbname + ".PAYMENTHEAD A\n"
//                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = A.EPPA_DIVI\n"
//                            + "AND EACONO =  A.EPPA_CONO\n"
//                            + "AND EAAITM = A.EPPA_COCE\n"
//                            + "AND EAAITP =  2\n"
//                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
//                            + "ON IDCONO = A.EPPA_CONO \n"
//                            + "AND A.EPRA_SUNO = IDSUNO\n"
//                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B\n"
//                            + "ON B.EPPA_NO = A.EPPA_NO\n"
//                            + "AND A.EPPA_CONO = B.EPPA_CONO\n"
//                            + "AND A.EPPA_DIVI = B.EPPA_DIVI \n"
//                            + "LEFT JOIN \n"
//                            + "" + dbname + ".PAYMENTLINEGRN C\n"
//                            + "ON A.EPPA_CONO = C.GRNP_CONO\n"
//                            + "AND A.EPPA_DIVI = C.GRNP_DIVI\n"
//                            + "AND C.GRNP_NO = A.EPPA_NO\n"
//                            + "WHERE A.EPPA_DUEDT BETWEEN " + newStartDate + "\n"
//                            + "AND " + newEndDate + "\n"
//                            + "AND UPPER(A.EPRA_REQBY) LIKE '" + user + "'\n"
//                            + "AND A.EPPA_CONO =" + cono + " AND A.EPPA_DIVI =" + divi;
//                    query = "SELECT EPPA_NO,EPRA_REF2,EPRA_PARM,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter\n"
//                            + ",IDSUNO||' : '||IDSUNM AS Supplier,EPPA_DUEDT,\n"
//                            + "CASE WHEN EPPA_PAMT = 1 THEN 'Cash'\n"
//                            + "WHEN EPPA_PAMT = 2 THEN 'Cheque No.'\n"
//                            + "WHEN EPPA_PAMT = 3 THEN 'Bank Transfer'\n"
//                            + "END AS PAYMENTMETHOD\n"
//                            + ",EPRA_ADVREF,EPRA_ADVAMT,\n"
//                            + "CASE WHEN EPRA_STAT = '00' THEN 'NORMAL'\n"
//                            + "WHEN EPRA_STAT = '10' THEN 'SUBMITTED'\n"
//                            + "WHEN EPRA_STAT = '99' THEN 'CANCALED' END AS STATUS\n"
//                            + "FROM  " + dbname + ".PAYMENTHEAD\n"
//                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = EPPA_DIVI\n"
//                            + "AND EACONO =  EPPA_CONO\n"
//                            + "AND EAAITM = EPPA_COCE\n"
//                            + "AND EAAITP =  2\n"
//                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
//                            + "ON IDCONO = EPPA_CONO \n"
//                            + "AND EPRA_SUNO = IDSUNO\n"
//                            + "WHERE EPPA_DUEDT BETWEEN " + newStartDate + "\n"
//                            + "AND " + newEndDate + "\n"
//                            + "AND UPPER(EPRA_REQBY) LIKE '" + user + "'\n"
//                            + "AND EPPA_CONO = " + cono + " AND EPPA_DIVI =" + divi;
                } else if (mode.equals("Num")) {

                    query = "SELECT F.EGVONO as  EGVONO,D.*,CHAR(D.SERVICES)\n"
                            + "--,CHAR(D.SRN_NO)\n"
                            + "FROM (\n"
                            + "SELECT A.EPPA_NO,ROW_NUMBER() OVER(PARTITION BY A.EPPA_NO) AS ROWNUMBER,COALESCE(B.EPRA_PHNO,C.GRNP_GRN) AS SERVICES,A.EPRA_REF2,A.EPRA_PARM,TRIM(EAAITM)||' : '||TRIM(EATX40) AS Costcenter\n"
                            + ",IDSUNO||' : '||IDSUNM AS Supplier,A.EPPA_DUEDT,EPPA_DATE,\n"
                            + "CASE WHEN A.EPPA_PAMT = 1 THEN 'Cash'\n"
                            + "WHEN A.EPPA_PAMT = 2 THEN 'Cheque No.'\n"
                            + "WHEN A.EPPA_PAMT = 3 THEN 'Bank Transfer'\n"
                            + "END AS PAYMENTMETHOD\n"
                            + ",A.EPRA_ADVREF,A.EPRA_ADVAMT,\n"
                            + "CASE WHEN A.EPRA_STAT = '00' THEN 'NORMAL'\n"
                            + "WHEN A.EPRA_STAT = '10' THEN 'SUBMITTED'\n"
                            + "WHEN A.EPRA_STAT = '99' THEN 'CANCALED' END AS STATUS\n"
                            + "FROM  " + dbname + ".PAYMENTHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".FCHACC ON EADIVI = A.EPPA_DIVI\n"
                            + "AND EACONO =  A.EPPA_CONO\n"
                            + "AND EAAITM = A.EPPA_COCE\n"
                            + "AND EAAITP =  2\n"
                            + "LEFT JOIN " + dbM3Name + ".CIDMAS \n"
                            + "ON IDCONO = A.EPPA_CONO \n"
                            + "AND A.EPRA_SUNO = IDSUNO\n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B\n"
                            + "ON B.EPPA_NO = A.EPPA_NO\n"
                            + "AND A.EPPA_CONO = B.EPPA_CONO\n"
                            + "AND A.EPPA_DIVI = B.EPPA_DIVI \n"
                            + "LEFT JOIN \n"
                            + "" + dbname + ".PAYMENTLINEGRN C\n"
                            + "ON A.EPPA_CONO = C.GRNP_CONO\n"
                            + "AND A.EPPA_DIVI = C.GRNP_DIVI\n"
                            + "AND C.GRNP_NO = A.EPPA_NO\n"
                            + "WHERE A.EPPA_NO =" + prvno.trim() + "\n"
                            + "AND UPPER(A.EPRA_REQBY) LIKE '" + user + "'\n"
                            + "AND A.EPPA_CONO = " + cono + " AND A.EPPA_DIVI =" + divi + "\n"
                            + ") D LEFT JOIN (\n"
                            + " SELECT EGVONO,EGCONO,EGDIVI,EGYEA4,EGAIT4\n"
                            + " FROM  M3FDBPRD.FGLEDG\n"
                            + " WHERE EGTRCD = 40\n"
                            + " AND EGCONO =" + cono + "\n"
                            + " AND EGDIVI =" + divi + "\n"
                            + ") F ON F.EGYEA4 = SUBSTRING(D.EPPA_DATE,1,4) AND F.EGAIT4 = CHAR(D.SERVICES) \n"
                            + "--AND F.EGAIT4 =  CHAR(D.SRN_NO)\n"
                            + "ORDER BY EPPA_DATE ASC,EPPA_NO";
                }

//                query += "\n ORDER BY SUBSTRING(EPPA_NO,4,5)";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    String value1 = mRes.getString("EPPA_NO");
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String servicesvalue = mRes.getString("SERVICES");
                    if (servicesvalue != null) {
                        servicesvalue = servicesvalue.trim();
                    }
                    String rownumber = mRes.getString("ROWNUMBER");
                    if (rownumber != null) {
                        rownumber = rownumber.trim();
                    }
                    String value2 = mRes.getString("EPRA_REF2");
                    if (value2 != null) {
                        value2 = value2.trim();
                    }
                    String value3 = mRes.getString("EPRA_PARM");
                    if (value3 != null) {
                        value3 = value3.trim();
                    }
                    String value4 = mRes.getString("COSTCENTER");

                    String value5 = mRes.getString("SUPPLIER");
                    if (value5 != null) {
                        value5 = value5.trim();
                    }
                    String value6 = mRes.getString("EPPA_DUEDT");
                    if (value6 != null) {
                        value6 = value6.trim();
                    }
                    String value7 = mRes.getString("PAYMENTMETHOD");
                    if (value7 != null) {
                        value7 = value7.trim();
                    }
                    String value8 = mRes.getString("EPRA_ADVREF");
                    if (value8 != null) {
                        value8 = value8.trim();
                    }
                    String value9 = mRes.getString("EPRA_ADVAMT");
                    if (value9 != null) {
                        value9 = value9.trim();
                    }
                    String value10 = mRes.getString("STATUS");
                    if (value10 != null) {
                        value10 = value10.trim();
                    }
                    String voucher = mRes.getString("EGVONO");
                    if (voucher != null) {
                        voucher = voucher.trim();
                    }
                    //GET COST CENTER

                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("RPAYMENTNO", value1);
                    mMap.put("RROWNO", rownumber);
                    mMap.put("RSERVICENO", servicesvalue);
                    mMap.put("RTYPE", value2);
                    mMap.put("RPREMARK", value3);
                    mMap.put("RCOSTCENTER", value4);
                    mMap.put("RSUPPLIER", value5);
                    mMap.put("RDUEDATE", value6);
                    mMap.put("RPAYMENT", value7);
                    mMap.put("RDESC", value8);
                    mMap.put("RAMOUNT", value9);
                    mMap.put("RStatus", value10);
                    mMap.put("RVOUCHER", voucher);
//                    mMap.put("RVat", value11);
//                    mMap.put("RStatus", Value12);
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getServicesRollback(String startDate, String endDate, String cono, String divi, String Status, String mode, String serviceno) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String newStartDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
        String newEndDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);
        String query = "";
        try {
            if (conn != null) {
                //-------------- GET HISTORY FOR THE ----------------------------
                Statement stmt = conn.createStatement();
                if (mode.equals("Date")) {
                    query = "SELECT EPRH_PHNO,ROW_NUMBER() OVER(PARTITION BY A.EPRH_PHNO) as ROWNO,EPRH_TYPE,B.EPPA_NO\n"
                            + ",EPRH_CONO,EPRH_DIVI,TRIM(eaaitm)||':'||TRIM(eatx40) AS EPRH_COCE\n"
                            + ",idsuno||':'||idsunm AS EPRH_SUNO\n"
                            + ",EPRH_PURPOS,EPRH_TRDT,EPRH_RQSDT\n"
                            + ",EPRH_APTNO,EPRH_OWAPP\n"
                            + ",EPRH_TOTAL,EPRH_DISC,EPRH_VTCD\n"
                            + ",EPRH_INVSU,\n"
                            + "CASE \n"
                            + "	WHEN EPRH_STAT = '20' THEN 'Normal'\n"
                            + "	WHEN EPRH_STAT = '50' THEN 'Submitted'\n"
                            + "	WHEN EPRH_STAT = '99' THEN 'Canceled'\n"
                            + "END AS EPRH_STAT\n"
                            + "FROM " + dbname + ".SERVICEHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPRH_COCE\n"
                            + "LEFT JOIN " + dbM3Name + ".cidmas ON  EPRH_SUNO = idsuno\n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B ON\n"
                            + "B.EPPA_CONO  = A.EPRH_CONO\n"
                            + "AND B.EPPA_DIVI = A.EPRH_DIVI\n"
                            + "AND A.EPRH_PHNO = B.EPRA_PHNO\n"
                            + "WHERE EPRH_CONO = " + cono + "\n"
                            + "AND EPRH_DIVI = " + divi + "\n"
                            + "AND EPRH_RQSDT BETWEEN " + newStartDate + " AND " + newEndDate + "\n"
                            + "AND eaaitp = 2 and   eadivi = '" + divi + "'\n"
                            + "AND idstat = '20' AND idcono='" + cono + "'";

//                    query = "SELECT EPRH_PHNO,EPRH_TYPE\n"
//                            + ",EPRH_CONO,EPRH_DIVI,TRIM(eaaitm)||':'||TRIM(eatx40) AS EPRH_COCE\n"
//                            + ",idsuno||':'||idsunm AS EPRH_SUNO\n"
//                            + ",EPRH_PURPOS,EPRH_TRDT,EPRH_RQSDT\n"
//                            + ",EPRH_APTNO,EPRH_OWAPP\n"
//                            + ",EPRH_TOTAL,EPRH_DISC,EPRH_VTCD\n"
//                            + ",EPRH_INVSU,\n"
//                            + "CASE \n"
//                            + "	WHEN EPRH_STAT = '20' THEN 'Normal'\n"
//                            + "	WHEN EPRH_STAT = '50' THEN 'Submitted'\n"
//                            + "	WHEN EPRH_STAT = '99' THEN 'Canceled'\n"
//                            + "END AS EPRH_STAT\n"
//                            + "FROM " + dbname + ".SERVICEHEAD\n"
//                            + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPRH_COCE\n"
//                            + "LEFT JOIN " + dbM3Name + ".cidmas ON  EPRH_SUNO = idsuno\n"
//                            + "WHERE EPRH_CONO = " + cono + "\n"
//                            + "AND EPRH_DIVI = " + divi + "\n"
//                            + "AND EPRH_RQSDT BETWEEN " + newStartDate + " AND " + newEndDate + "\n"
//                            + "AND eaaitp = 2 and   eadivi = '" + divi + "'\n"
//                            + "AND idstat = '20' AND idcono='" + cono + "'";
                    if (!Status.equals("")) {
                        query += "\n AND EPRH_STAT = '" + Status + "'";
                    }
                } else if (mode.equals("Num")) {
                    query = "SELECT EPRH_PHNO,ROW_NUMBER() OVER(PARTITION BY A.EPRH_PHNO) as ROWNO,EPRH_TYPE,B.EPPA_NO\n"
                            + ",EPRH_CONO,EPRH_DIVI,TRIM(eaaitm)||':'||TRIM(eatx40) AS EPRH_COCE\n"
                            + ",idsuno||':'||idsunm AS EPRH_SUNO\n"
                            + ",EPRH_PURPOS,EPRH_TRDT,EPRH_RQSDT\n"
                            + ",EPRH_APTNO,EPRH_OWAPP\n"
                            + ",EPRH_TOTAL,EPRH_DISC,EPRH_VTCD\n"
                            + ",EPRH_INVSU,\n"
                            + "CASE \n"
                            + "	WHEN EPRH_STAT = '20' THEN 'Normal'\n"
                            + "	WHEN EPRH_STAT = '50' THEN 'Submitted'\n"
                            + "	WHEN EPRH_STAT = '99' THEN 'Canceled'\n"
                            + "END AS EPRH_STAT\n"
                            + "FROM " + dbname + ".SERVICEHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPRH_COCE\n"
                            + "LEFT JOIN " + dbM3Name + ".cidmas ON  EPRH_SUNO = idsuno\n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B ON\n"
                            + "B.EPPA_CONO  = A.EPRH_CONO\n"
                            + "AND B.EPPA_DIVI = A.EPRH_DIVI\n"
                            + "AND A.EPRH_PHNO = B.EPRA_PHNO\n"
                            + "WHERE EPRH_CONO = " + cono + "\n"
                            + "AND EPRH_DIVI = " + divi + "\n"
                            + "AND EPRH_PHNO = " + serviceno + "\n"
                            + "AND eaaitp = 2 and   eadivi = '" + divi + "'\n"
                            + "AND idstat = '20' AND idcono='" + cono + "'";

//                    query = "SELECT EPRH_PHNO,EPRH_TYPE\n"
//                            + ",EPRH_CONO,EPRH_DIVI,TRIM(eaaitm)||':'||TRIM(eatx40) AS EPRH_COCE\n"
//                            + ",idsuno||':'||idsunm AS EPRH_SUNO\n"
//                            + ",EPRH_PURPOS,EPRH_TRDT,EPRH_RQSDT\n"
//                            + ",EPRH_APTNO,EPRH_OWAPP\n"
//                            + ",EPRH_TOTAL,EPRH_DISC,EPRH_VTCD\n"
//                            + ",EPRH_INVSU,\n"
//                            + "CASE \n"
//                            + "	WHEN EPRH_STAT = '20' THEN 'Normal'\n"
//                            + "	WHEN EPRH_STAT = '50' THEN 'Submitted'\n"
//                            + "	WHEN EPRH_STAT = '99' THEN 'Canceled'\n"
//                            + "END AS EPRH_STAT\n"
//                            + "FROM " + dbname + ".SERVICEHEAD\n"
//                            + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPRH_COCE\n"
//                            + "LEFT JOIN " + dbM3Name + ".cidmas ON  EPRH_SUNO = idsuno\n"
//                            + "WHERE EPRH_CONO = " + cono + "\n"
//                            + "AND EPRH_DIVI = " + divi + "\n"
//                            
//                            + "AND eaaitp = 2 and   eadivi = '" + divi + "'\n"
//                            + "AND idstat = '20' AND idcono='" + cono + "'";
                }
//                query += "\n ORDER BY SUBSTRING(EPPA_NO,4,5)";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    String value1 = mRes.getString("EPRH_PHNO");
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String value2 = mRes.getString("EPRH_TYPE");
                    if (value2 != null) {
                        value2 = value2.trim();
                    }
                    String rowno = mRes.getString("ROWNO");
                    if (rowno != null) {
                        rowno = rowno.trim();
                    }
                    String Paymentno = mRes.getString("EPPA_NO");
                    if (Paymentno != null) {
                        Paymentno = Paymentno.trim();
                    }
                    String value3 = mRes.getString("EPRH_CONO");
                    if (value3 != null) {
                        value3 = value3.trim();
                    }
                    String value4 = mRes.getString("EPRH_DIVI");
                    if (value4 != null) {
                        value4 = value4.trim();
                    }
                    String value5 = mRes.getString("EPRH_COCE");
                    if (value5 != null) {
                        value5 = value5.trim();
                    }
                    String value6 = mRes.getString("EPRH_SUNO");
                    if (value6 != null) {
                        value6 = value6.trim();
                    }
                    String value7 = mRes.getString("EPRH_PURPOS");
                    if (value7 != null) {
                        value7 = value7.trim();
                    }
                    String value8 = mRes.getString("EPRH_TRDT");
                    if (value8 != null) {
                        value8 = value8.trim();
                    }
                    String value9 = mRes.getString("EPRH_RQSDT");
                    if (value9 != null) {
                        value9 = value9.trim();
                    }
                    String value10 = mRes.getString("EPRH_APTNO");
                    if (value10 != null) {
                        value10 = value10.trim();
                    }
                    String value11 = mRes.getString("EPRH_OWAPP");
                    if (value11 != null) {
                        value11 = value11.trim();
                    }
                    String value12 = mRes.getString("EPRH_TOTAL");
                    if (value12 != null) {
                        value12 = value12.trim();
                    }
                    String value13 = mRes.getString("EPRH_DISC");
                    if (value13 != null) {
                        value13 = value13.trim();
                    }
                    String value14 = mRes.getString("EPRH_VTCD");
                    if (value14 != null) {
                        value14 = value14.trim();
                    }
                    String value15 = mRes.getString("EPRH_INVSU");
                    if (value15 != null) {
                        value15 = value15.trim();
                    }
                    String value16 = mRes.getString("EPRH_STAT");
                    if (value16 != null) {
                        value16 = value16.trim();
                    }

                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("RSERVICENO", value1);
                    mMap.put("RTYPE", value2);
                    mMap.put("RCONO", value3);
                    mMap.put("RROWNO", rowno);
                    mMap.put("RPAYMENTNO", Paymentno);
                    mMap.put("RDIVI", value4);
                    mMap.put("RCOSTCENTER", value5);
                    mMap.put("RSUPPLIER", value6);
                    mMap.put("RPURPOSE", value7);
                    mMap.put("RLASTUPDATE", value8);
                    mMap.put("RCONTACT", value10);
                    mMap.put("RREQTESTDATE", value9);
                    mMap.put("RBY", value11);
                    mMap.put("RTOTAL", value12);
                    mMap.put("RDISCOUNT", value13);
                    mMap.put("RTAXES", value14);
                    mMap.put("RSUBINVOICE", value15);
                    mMap.put("RSTATUS", value16);
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray getPaymentRollback(String startDate, String endDate, String cono, String divi, String Status, String mode, String paymentno) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String newStartDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
        String newEndDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);
        String query = "";
        try {
            if (conn != null) {
                //-------------- GET HISTORY FOR THE ----------------------------
                Statement stmt = conn.createStatement();
                if (mode.equals("Date")) {
                    query = "SELECT A.EPPA_NO,ROW_NUMBER() OVER(PARTITION BY A.EPPA_NO) AS ROWNUMBER,COALESCE(B.EPRA_PHNO,C.GRNP_GRN) AS EPRA_PHNO ,EPRA_REF2,A.EPPA_CONO,A.EPPA_DIVI\n"
                            + ",TRIM(eaaitm)||':'||TRIM(eatx40) AS EPRH_COCE\n"
                            + ",idsuno||':'||idsunm AS EPRH_SUNO,\n"
                            + "EPRA_PARM,EPRA_ADVREF,EPRA_REQBY,\n"
                            + "CASE WHEN A.EPRA_STAT =  '00' THEN 'Normal'\n"
                            + " WHEN A.EPRA_STAT =  '10' THEN 'Submitted'\n"
                            + " WHEN A.EPRA_STAT = '99' THEN 'Cancalled'\n"
                            + "END AS STATUS\n"
                            + "FROM " + dbname + ".PAYMENTHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPPA_COCE\n"
                            + "LEFT JOIN " + dbM3Name + ".cidmas ON EPRA_SUNO  = idsuno\n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B  ON \n"
                            + "A.EPPA_CONO = B.EPPA_CONO\n"
                            + "AND A.EPPA_DIVI = B.EPPA_DIVI \n"
                            + "AND B.EPPA_NO = A.EPPA_NO \n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINEGRN C ON\n"
                            + "C.GRNP_CONO = A.EPPA_CONO \n"
                            + "AND C.GRNP_DIVI = A.EPPA_DIVI\n"
                            + "AND C.GRNP_NO = A.EPPA_NO\n"
                            + "WHERE A.EPPA_CONO = '" + cono + "' AND A.EPPA_DIVI = '" + divi + "'\n"
                            + "AND EPPA_DUEDT BETWEEN " + newStartDate + " AND " + newEndDate + "\n"
                            + "AND eaaitp = 2 and   eadivi = '" + divi + "'\n"
                            + "AND idstat = '20' AND idcono= '" + cono + "'";

//                    query = "SELECT EPPA_NO,EPRA_REF2,EPPA_CONO,EPPA_DIVI\n"
//                            + ",TRIM(eaaitm)||':'||TRIM(eatx40) AS EPRH_COCE\n"
//                            + ",idsuno||':'||idsunm AS EPRH_SUNO,\n"
//                            + "EPRA_PARM,EPRA_ADVREF,EPRA_REQBY,\n"
//                            + "CASE WHEN EPRA_STAT =  '00' THEN 'Normal'\n"
//                            + " WHEN EPRA_STAT =  '10' THEN 'Submitted'\n"
//                            + " WHEN EPRA_STAT = '99' THEN 'Cancalled'\n"
//                            + "END AS STATUS\n"
//                            + "FROM " + dbname + ".PAYMENTHEAD \n"
//                            + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPPA_COCE\n"
//                            + "LEFT JOIN " + dbM3Name + ".cidmas ON EPRA_SUNO  = idsuno\n"
//                            + "WHERE EPPA_CONO = '" + cono + "' AND EPPA_DIVI = '" + divi + "'\n"
//                            + "AND EPPA_DUEDT BETWEEN " + newStartDate + " AND " + newEndDate + "\n"
//                            + "AND eaaitp = 2 and   eadivi = '" + divi + "'\n"
//                            + "AND idstat = '20' AND idcono='" + cono + "'";
                    if (!Status.equals("")) {
                        query += "\n AND EPRA_STAT = '" + Status + "'";
                    }
                } else if (mode.equals("Num")) {
                    query = "SELECT A.EPPA_NO,ROW_NUMBER() OVER(PARTITION BY A.EPPA_NO) AS ROWNUMBER,COALESCE(B.EPRA_PHNO,C.GRNP_GRN) AS EPRA_PHNO ,EPRA_REF2,A.EPPA_CONO,A.EPPA_DIVI\n"
                            + ",TRIM(eaaitm)||':'||TRIM(eatx40) AS EPRH_COCE\n"
                            + ",idsuno||':'||idsunm AS EPRH_SUNO,\n"
                            + "EPRA_PARM,EPRA_ADVREF,EPRA_REQBY,\n"
                            + "CASE WHEN A.EPRA_STAT =  '00' THEN 'Normal'\n"
                            + " WHEN A.EPRA_STAT =  '10' THEN 'Submitted'\n"
                            + " WHEN A.EPRA_STAT = '99' THEN 'Cancalled'\n"
                            + "END AS STATUS\n"
                            + "FROM " + dbname + ".PAYMENTHEAD A\n"
                            + "LEFT JOIN " + dbM3Name + ".fchacc ON eaaitm = EPPA_COCE\n"
                            + "LEFT JOIN " + dbM3Name + ".cidmas ON EPRA_SUNO  = idsuno\n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINE B  ON \n"
                            + "A.EPPA_CONO = B.EPPA_CONO\n"
                            + "AND A.EPPA_DIVI = B.EPPA_DIVI \n"
                            + "AND B.EPPA_NO = A.EPPA_NO \n"
                            + "LEFT JOIN " + dbname + ".PAYMENTLINEGRN C ON\n"
                            + "C.GRNP_CONO = A.EPPA_CONO \n"
                            + "AND C.GRNP_DIVI = A.EPPA_DIVI\n"
                            + "AND C.GRNP_NO = A.EPPA_NO\n"
                            + "WHERE A.EPPA_CONO = '" + cono + "' AND A.EPPA_DIVI = '" + divi + "'\n"
                            + "AND A.EPPA_NO =" + paymentno + "\n"
                            + "AND eaaitp = 2 and   eadivi = '" + divi + "'\n"
                            + "AND idstat = '20' AND idcono='" + cono + "'";
                }
                //                query += "\n ORDER BY SUBSTRING(EPPA_NO,4,5)";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    String value1 = mRes.getString("EPPA_NO");
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String rowno = mRes.getString("ROWNUMBER");
                    if (rowno != null) {
                        rowno = rowno.trim();
                    }
                    String serviceno = mRes.getString("EPRA_PHNO");
                    if (serviceno != null) {
                        serviceno = serviceno.trim();
                    }
                    String value2 = mRes.getString("EPRA_REF2");
                    if (value2 != null) {
                        value2 = value2.trim();
                    }
                    String value3 = mRes.getString("EPPA_CONO");
                    if (value3 != null) {
                        value3 = value3.trim();
                    }
                    String value4 = mRes.getString("EPPA_DIVI");
                    if (value4 != null) {
                        value4 = value4.trim();
                    }
                    String value5 = mRes.getString("EPRH_COCE");
                    if (value5 != null) {
                        value5 = value5.trim();
                    }
                    String value6 = mRes.getString("EPRH_SUNO");
                    if (value6 != null) {
                        value6 = value6.trim();
                    }
                    String value7 = mRes.getString("EPRA_PARM");
                    if (value7 != null) {
                        value7 = value7.trim();
                    }
                    String value8 = mRes.getString("EPRA_ADVREF");
                    if (value8 != null) {
                        value8 = value8.trim();
                    }
                    String value9 = mRes.getString("EPRA_REQBY");
                    if (value9 != null) {
                        value9 = value9.trim();
                    }
                    String value10 = mRes.getString("STATUS");
                    if (value10 != null) {
                        value10 = value10.trim();
                    }

                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("RPAYMENTNO", value1);
                    mMap.put("ROWNO", rowno);
                    mMap.put("RSERVICE", serviceno);
                    mMap.put("RTYPE", value2);
                    mMap.put("RCONO", value3);
                    mMap.put("RDIVI", value4);
                    mMap.put("RCOSTCENTER", value5);
                    mMap.put("RSUPPLIER", value6);
                    mMap.put("RPREMARK", value7);
                    mMap.put("RDEDUCTDESC", value8);
                    mMap.put("RBY", value9);
                    mMap.put("RSTATUS", value10);
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray SavePurchaseDetail(String rqnum) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                String query = "SELECT * FROM " + dbname + ".PAYMENTLINE\n"
                        + "WHERE EPPA_NO = '" + rqnum + "'";
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    String value1 = mRes.getString("EPRA_PHNO");
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String query2 = "select eprh_trdt,eprh_grn,eprh_invsu,eprh_invdt,eprh_total,eprh_disc\n"
                            + ",eprh_vtamt,eprh_total-eprh_disc+eprh_vtamt as totalAll,eprh_chkdt\n"
                            + ",eprh_phno,EPRH_PURPOS as EPRH_DESCRIPTION\n"
                            + "FROM " + dbname + ".SERVICEHEAD\n"
                            + "WHERE eprh_stat = '50' AND  eprh_phno=" + value1;
                    ResultSet mRes2 = stmt2.executeQuery(query2);
                    while (mRes2.next()) {
                        String value2 = mRes2.getString("eprh_trdt");
                        String value3 = mRes2.getString("eprh_grn");
                        String value4 = mRes2.getString("eprh_chkdt");
                        String value5 = mRes2.getString("eprh_invsu");
                        String value6 = mRes2.getString("eprh_invdt");
                        String value7 = mRes2.getString("eprh_total");
                        String value8 = mRes2.getString("eprh_disc");
                        String value9 = mRes2.getString("eprh_vtamt");
                        String value10 = mRes2.getString("totalAll");
                        if (value2 != null) {
                            value2 = value2.trim();
                        }
                        if (value3 != null) {
                            value3 = value3.trim();
                        }
                        if (value4 != null) {
                            value4 = value4.trim();
                        }
                        if (value5 != null) {
                            value5 = value5.trim();
                        }
                        if (value6 != null) {
                            value6 = value6.trim();
                        }
                        if (value7 != null) {
                            value7 = value7.trim();
                        }
                        if (value8 != null) {
                            value8 = value8.trim();
                        }
                        if (value9 != null) {
                            value9 = value9.trim();
                        }
                        if (value10 != null) {
                            value10 = value10.trim();
                        }
                        Map<String, Object> mMap = new HashMap<>();
//                    String value4new = String.valueOf(new DecimalFormat("##.##").format(Math.round(parseFloat(value4) * 100.0) / 100.0));
//                    String value5new = String.valueOf(new DecimalFormat("##.##").format(Math.round(parseFloat(value5) * 100.0) / 100.0));
//                    String value7new = String.valueOf(new DecimalFormat("##.##").format(Math.round(parseFloat(value7) * 100.0) / 100.0));
                        mMap.put("Rernno", value1);
                        mMap.put("Rerndate", value2);
                        mMap.put("Rerngrn", value3);
                        mMap.put("Rerngrndate", value4);
                        mMap.put("Rinvno", value5);
                        mMap.put("Rinvdate", value6);
                        mMap.put("Rtotal", value7);
                        mMap.put("Rdiscount", value8);
                        mMap.put("Rvat", value9);
                        mMap.put("Rtotalall", value10);
                        mJSonArr.put(mMap);

                    }
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static double Double2digitReturn(double number) {

        try {
            String numberBeforeconvert = String.valueOf(new DecimalFormat("##.##").format(Math.round(number * 100.0) / 100.0));
            double numberreturn = Double.parseDouble(numberBeforeconvert);
            return numberreturn;
        } catch (Exception e) {
            return 0;
        }

    }

    public static JSONArray getItemfromRqNoPRV(String rqnum, String app, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String query2 = null;

        try {
            if (conn != null) {
                ResultSet mRes2 = null;
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                String query = null;
                if (app.equals("GRN")) {
                    query = "SELECT ROW_NUMBER() OVER(ORDER BY GRNP_GRN) AS ORDERNO,a.* FROM " + dbname + ".PAYMENTLINEGRN a\n"
                            + "WHERE GRNP_NO = '" + rqnum + "'\n"
                            + "AND GRNP_CONO =" + cono + " AND GRNP_DIVI =" + divi;
                } else {
                    query = "SELECT ROW_NUMBER() OVER(ORDER BY EPRA_PHNO) AS ORDERNO,a.* FROM " + dbname + ".PAYMENTLINE a\n"
                            + "JOIN " + dbname + ".PAYMENTHEAD b ON a.EPPA_NO = b.EPPA_NO AND a.EPPA_CONO = b.EPPA_CONO  AND A.eppa_divi = b.EPPA_DIVI \n"
                            + "WHERE A.EPPA_CONO = " + cono + " AND B.EPPA_DIVI = " + divi + "\n"
                            + "AND B.EPPA_NO ='" + rqnum + "'";
                }

                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {

                    String value1 = null;
                    if (app.equals("GRN")) {
                        value1 = mRes.getString("GRNP_NO");
                    } else {
                        value1 = mRes.getString("EPRA_PHNO");
                    }
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String value11 = mRes.getString("ORDERNO");
                    if (value11 != null) {
                        value11 = value11.trim();
                    }
                    if (!app.equals("GRN")) {
                        query2 = "select eprh_trdt,eprh_grn,eprh_invsu,eprh_invdt,eprh_total,eprh_disc\n"
                                + ",eprh_vtamt,eprh_total-eprh_disc+eprh_vtamt as totalAll,eprh_chkdt\n"
                                + ",eprh_phno,EPRH_PURPOS as EPRH_DESCRIPTION\n"
                                + "FROM " + dbname + ".SERVICEHEAD\n"
                                + "WHERE eprh_stat = '50' AND  eprh_phno=" + value1 + "\n"
                                + "AND EPRH_CONO =" + cono + " AND EPRH_DIVI =" + divi;
                        mRes2 = stmt2.executeQuery(query2);
                    }

                    if (app.equals("GRN")) {

                        Map<String, Object> mMap = new HashMap<>();

//                            value4 = Double2digitReturn(Double.parseDouble(grnp_cost));
                        mMap.put("Rernno", mRes.getString("GRNP_GRN"));
                        mMap.put("Rerndate", mRes.getString("GRNP_GRND"));
                        mMap.put("pono", mRes.getString("GRNP_PO"));
                        mMap.put("cost", mRes.getString("GRNP_COST"));
                        mMap.put("Rinvno", mRes.getString("GRNP_INVC"));
                        mMap.put("Rdescription", mRes.getString("GRNP_DESC").trim());
                        mMap.put("Rdiscount", mRes.getString("GRNP_DISC").trim());
                        mMap.put("Charge", mRes.getString("GRNP_CHAR"));
                        mMap.put("Amount", mRes.getString("GRNP_AMTB"));
                        mMap.put("Rvat", mRes.getString("GRNP_VATT"));
                        mMap.put("totalamount", mRes.getString("GRNP_AMTT"));
                        mMap.put("Rno", mRes.getString("ORDERNO"));
                        mJSonArr.put(mMap);

                    } else {
                        while (mRes2.next()) {
                            Map<String, Object> mMap = new HashMap<>();
                            String value2 = mRes2.getString("eprh_trdt");
                            String value3 = mRes2.getString("eprh_grn");
                            String value4 = mRes2.getString("eprh_chkdt");
                            String value5 = mRes2.getString("eprh_invsu");
                            String value6 = mRes2.getString("eprh_invdt");
                            String value7 = mRes2.getString("eprh_total");
                            String value8 = mRes2.getString("eprh_disc");
                            String value9 = mRes2.getString("eprh_vtamt");
                            String value10 = mRes2.getString("totalAll");

                            if (value2 != null) {
                                value2 = value2.trim();
                            }
                            if (value3 != null) {
                                value3 = value3.trim();
                            }
                            if (value4 != null) {
                                value4 = value4.trim();
                            }
                            if (value5 != null) {
                                value5 = value5.trim();
                            }
                            if (value6 != null) {
                                value6 = value6.trim();
                            }
                            if (value7 != null) {
                                value7 = value7.trim();
                            }
                            if (value8 != null) {
                                value8 = value8.trim();
                            }
                            if (value9 != null) {
                                value9 = value9.trim();
                            }
                            if (value10 != null) {
                                value10 = value10.trim();
                            }

                            mMap.put("Rno", value11);
                            mMap.put("Rernno", value1);
                            mMap.put("Rerndate", value2);
                            mMap.put("Rerngrn", value3);
                            mMap.put("Rerngrndate", value4);
                            mMap.put("Rinvno", value5);
                            mMap.put("Rinvdate", value6);
                            mMap.put("Rtotal", value7);
                            mMap.put("Rdiscount", value8);
                            mMap.put("Rvat", value9);
                            mMap.put("Rtotalall", value10);
                            mJSonArr.put(mMap);
//                   
                        }

                    }
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static ResultSet GetDetail_OFFSET(String cono, String PONO) {
        try {
//            readTableWithJson rdjson = new readTableWithJson();
//            rdjson.GetJsonData();
            Connection conn = ConnectDB2.ConnectionDB();
            Statement sta = conn.createStatement();
            String Sql1 = " SELECT sum(COALESCE(d.o_qty,0)) AS o_qty,sum(COALESCE(d.o_weight,0)) AS o_weight,(sum(COALESCE(d.o_amount,0)) + COALESCE(ld_exp1,0)) + COALESCE(ff.ld_exp2,0)   AS o_amount,iacono \n"
                    + ",COALESCE(d.icrepns,'')icrepns,COALESCE(d.ictrdt,0)ictrdt,d.iapuno\n"
                    + "FROM (\n"
                    + "SELECT K.*,L.ld_exp1\n"
                    + "FROM (\n"
                    + "SELECT B.*\n"
                    + ",CASE WHEN  (b.ibpuun = 'PCS' and (substr(b.ibitno,1,6) = 'CF1010' OR  substr(b.ibitno,1,6) = 'CF1011'  OR substr(b.ibitno,1,6) = 'CF2010' or  substr(b.ibitno,1,6) = 'CF3010') ) THEN COALESCE(icrpqa,0) \n"
                    + "WHEN (b.ibpuun <> 'PCS' and (substr(b.ibitno,1,6) = 'CF1010' or substr(b.ibitno,1,6) = 'CF2010' or substr(b.ibitno,1,6) = 'CF1011'  or substr(b.ibitno,1,6) = 'CF3010' ) )THEN  COALESCE(c_weight,0)\n"
                    + "WHEN (substr(b.ibitno,1,6) <> 'CF1010') or (substr(b.ibitno,1,6) <> 'CF1011' ) OR (substr(b.ibitno,1,6) <> 'CF2010') or (substr(b.ibitno,1,6) <> 'CF3010' ) THEN 0 END AS o_qty\n"
                    + ",CASE WHEN  (b.ibpuun = 'PCS' and (substr(b.ibitno,1,6) = 'CF1010' or substr(b.ibitno,1,6) = 'CF1011' or  substr(b.ibitno,1,6) = 'CF2010' or  substr(b.ibitno,1,6) = 'CF3010') ) THEN  COALESCE(c_weight,0) \n"
                    + "WHEN (b.ibpuun <> 'PCS' and (substr(b.ibitno,1,6) = 'CF1010' or substr(b.ibitno,1,6) = 'CF2010' or substr(b.ibitno,1,6) = 'CF3010' or substr(b.ibitno,1,6) = 'CF1011' ) )THEN  COALESCE(icrpqa,0)\n"
                    + "WHEN (substr(b.ibitno,1,6) <> 'CF1010') or  (substr(b.ibitno,1,6) <> 'CF1011') or (substr(b.ibitno,1,6) <> 'CF2010') or (substr(b.ibitno,1,6) <> 'CF3010' ) THEN 0 END AS o_weight\n"
                    + ",CASE WHEN (substr(b.ibitno,1,6)  =  'CF1010') or (substr(b.ibitno,1,6) = 'CF2010') or (substr(b.ibitno,1,6) = 'CF3010')or (substr(b.ibitno,1,6) = 'CF1011')     THEN round(COALESCE(b.icrpqa,0)*COALESCE(b.ibpupr,0),2)\n"
                    + "ELSE   0 END AS o_amount \n"
                    + "FROM ( \n"
                    + "select RTRIM(iapuno) iapuno,iacono\n"
                    + "                from    " + dbM3Name + ".mphead\n"
                    //                    + "                where iasuno = '" + SUNO.trim() + "'\n"
                    + "                WHERE   iapust >= '75'\n"
                    + "                and  iapusl >= '15'\n"
                    + "                and  iapust <> '99'\n"
                    + "                AND IAPUNO = '" + PONO.trim() + "'\n"
                    + "                order by iapuno \n"
                    + "                ) A LEFT JOIN \n"
                    + "  ( select RTRIM(iapuno) iapuno,ibpnli,ibitno,ibpupr,RTRIM(ibpuun) ibpuun,iborqa,icrpqa,icrepn,ictrdt,substr((icrepn),1,7) icrepns,COALESCE(mtcawe,0) as c_weight,iacono\n"
                    + "               from   " + dbM3Name + ".mphead," + dbM3Name + ".mpline," + dbM3Name + ".mplind," + dbM3Name + ".mittra      \n"
                    + "               where  iapuno = ibpuno\n"
                    + "               AND mtridn = iapuno  \n"
                    + "               AND mtrepn = icrepn\n"
                    + "                 and  ibpuno = icpuno\n"
                    + "                 and  ibpnli = icpnli    AND  ibcono = iacono\n"
                    + "                 AND  iccono = iacono \n"
                    + "               order by ictrdt,icrepn \n"
                    + "               ) B ON B.iapuno = A.iapuno AND  B.iacono = A.iacono ) K LEFT JOIN \n"
                    + "               (select COALESCE(ld_exp1,0)ld_exp1 ,A.ibpuno ,A.ibcono \n"
                    + "     from   brldta0100.ld_exp1 A )  L ON  L.ibpuno = K.iapuno   AND  k.IACONO = l.ibcono ) D LEFT JOIN\n"
                    + "    ( select COALESCE(ld_exp2,0)ld_exp2 ,ibpuno ,ibcono \n"
                    + "     from   brldta0100.ld_exp2 ) FF ON ff.ibpuno = D.iapuno AND  D.IACONO = ff.ibcono \n"
                    + "     WHERE D.IACONO =" + cono + "\n"
                    + "         GROUP BY icrepns,ictrdt,iapuno,ld_exp1,ld_exp2,iacono\n"
                    + "               ORDER BY ictrdt,icrepns";

            ResultSet rs1 = sta.executeQuery(Sql1);
//            System.out.println(Sql1);
            return rs1;
        } catch (Exception ex) {
//            Logger.getLogger(SrnForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String GetDateFormatSetShowString(Date dateToset) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formatted = format1.format(dateToset);
        return formatted;
    }

    public static boolean checkTypeGRN(String GRN, String cono) {
        try {
            Connection conn = ConnectDB2.ConnectionDB();
            Statement sta = conn.createStatement();
            boolean type = false;
            String Sql1 = "SELECT  DISTINCT IAORTY\n"
                    + "from   " + dbM3Name + ".mphead," + dbM3Name + ".mpline," + dbM3Name + ".mplind," + dbM3Name + ".mittra      \n"
                    + "where  iapuno = ibpuno\n"
                    + "AND substr((icrepn),1,7)  = '" + GRN.trim() + "'\n"
                    + "AND ICCONO = '" + cono + "'\n"
                    + "AND mtridn = iapuno  \n"
                    + "AND mtrepn = icrepn\n"
                    + "and  ibpuno = icpuno\n"
                    + "AND IAORTY = '608'\n"
                    + "and  ibpnli = icpnli    AND  ibcono = iacono\n"
                    + "AND  iccono = iacono ";

            ResultSet rs1 = sta.executeQuery(Sql1);
            if (rs1.next()) {
                type = true;
            }
            return type;
        } catch (Exception ex) {
//            Logger.getLogger(SrnForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static JSONArray getItemfromRqNoPRV2(String rqnum) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                String query2 = "select eprh_trdt,eprh_grn,eprh_invsu,eprh_invdt,eprh_total,eprh_disc\n"
                        + ",eprh_vtamt,eprh_total-eprh_disc+eprh_vtamt as totalAll,eprh_chkdt\n"
                        + ",eprh_phno,EPRH_PURPOS as EPRH_DESCRIPTION\n"
                        + "FROM " + dbname + ".SERVICEHEAD\n"
                        + "WHERE eprh_stat = '50' AND  eprh_phno=" + rqnum;
                ResultSet mRes2 = stmt2.executeQuery(query2);
                while (mRes2.next()) {
                    String value2 = mRes2.getString("eprh_trdt");
                    String value3 = mRes2.getString("eprh_grn");
                    String value4 = mRes2.getString("eprh_chkdt");
                    String value5 = mRes2.getString("eprh_invsu");
                    String value6 = mRes2.getString("eprh_invdt");
                    String value7 = mRes2.getString("eprh_total");
                    String value8 = mRes2.getString("eprh_disc");
                    String value9 = mRes2.getString("eprh_vtamt");
                    String value10 = mRes2.getString("totalAll");
                    if (value2 != null) {
                        value2 = value2.trim();
                    }
                    if (value3 != null) {
                        value3 = value3.trim();
                    }
                    if (value4 != null) {
                        value4 = value4.trim();
                    }
                    if (value5 != null) {
                        value5 = value5.trim();
                    }
                    if (value6 != null) {
                        value6 = value6.trim();
                    }
                    if (value7 != null) {
                        value7 = value7.trim();
                    }
                    if (value8 != null) {
                        value8 = value8.trim();
                    }
                    if (value9 != null) {
                        value9 = value9.trim();
                    }
                    if (value10 != null) {
                        value10 = value10.trim();
                    }
                    Map<String, Object> mMap = new HashMap<>();
//                    String value4new = String.valueOf(new DecimalFormat("##.##").format(Math.round(parseFloat(value4) * 100.0) / 100.0));
//                    String value5new = String.valueOf(new DecimalFormat("##.##").format(Math.round(parseFloat(value5) * 100.0) / 100.0));
//                    String value7new = String.valueOf(new DecimalFormat("##.##").format(Math.round(parseFloat(value7) * 100.0) / 100.0));
//                        mMap.put("Rernno", value1);
                    mMap.put("Rerndate", value2);
                    mMap.put("Rerngrn", value3);
                    mMap.put("Rerngrndate", value4);
                    mMap.put("Rinvno", value5);
                    mMap.put("Rinvdate", value6);
                    mMap.put("Rtotal", value7);
                    mMap.put("Rdiscount", value8);
                    mMap.put("Rvat", value9);
                    mMap.put("Rtotalall", value10);
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static void importInvoiceData1(String month, String year) throws Exception {
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String query1;
        String nextmonth = Integer.toString(parseInt(month) + 1);
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
//                Statement stmt2 = conn.createStatement();
//                Statement stmt3 = conn.createStatement();
//                Statement stmt4 = conn.createStatement();
                for (int i = 1; i <= 6; i++) {
                    if (i == 1) {
                        //For first time only
                        query1 = "INSERT INTO BRLDTA0100.BP_STDATE(BPS_CONO,BPS_CUNO,BPS_STDT,BPS_RD)\n"
                                + "SELECT DISTINCT BPS_CONO,BPS_CUNO, BPS_FNDT,'1'\n"
                                + "FROM BRLDTA0100.BP_STDATE\n"
                                + "WHERE MONTH(DATE(TIMESTAMP_FORMAT(cast(BPS_STDT as varchar(8)), 'YYYYMMDD'))) <> MONTH(DATE(TIMESTAMP_FORMAT(cast(BPS_FNDT as varchar(8)), 'YYYYMMDD')))\n"
                                + "AND MONTH(DATE(TIMESTAMP_FORMAT(cast(BPS_FNDT as varchar(8)), 'YYYYMMDD')))  = " + nextmonth;
                    } else {
                        //For other times
                        query1 = "INSERT INTO BRLDTA0100.BP_STDATE(BPS_CONO,BPS_CUNO,BPS_STDT)\n"
                                + "SELECT DISTINCT BPS_CONO,BPS_CUNO, BPS_FNDT\n"
                                + "FROM BRLDTA0100.BP_STDATE\n"
                                + "WHERE MONTH(DATE(TIMESTAMP_FORMAT(cast(BPS_FNDT as varchar(8)), 'YYYYMMDD'))) = MONTH(DATE(TIMESTAMP_FORMAT(cast(BPS_STDT as varchar(8)), 'YYYYMMDD')))\n"
                                + "AND BPS_RD = (SELECT MAX(BPS_RD) FROM BRLDTA0100.BP_STDATE WHERE MONTH(DATE(TIMESTAMP_FORMAT(cast(BPS_STDT as varchar(8)), 'YYYYMMDD'))) = '" + nextmonth + "')\n"
                                + "AND MONTH(DATE(TIMESTAMP_FORMAT(cast(BPS_STDT as varchar(8)), 'YYYYMMDD'))) = '" + nextmonth + "'";
                    }
                    //UPDATE END DATE OF START DATE TABLE USING MASTER TABLE
//                    String query2 = "UPDATE BRLDTA0100.BP_STDATE C\n"
//                            + "SET BPS_FNDT = (SELECT YEAR(CHAR(DATE(TIMESTAMP_FORMAT(cast( C.BPS_STDT as varchar(8)), 'YYYYMMDD'))+ B.BPM_RINV DAYS - 1 DAYS )) * 10000\n"
//                            + "+ MONTH(CHAR(DATE(TIMESTAMP_FORMAT(cast( C.BPS_STDT as varchar(8)), 'YYYYMMDD'))+ B.BPM_RINV DAYS - 1 DAYS )) * 100\n"
//                            + "+ DAY(CHAR(DATE(TIMESTAMP_FORMAT(cast( C.BPS_STDT as varchar(8)), 'YYYYMMDD'))+ B.BPM_RINV DAYS - 1 DAYS))  AS ENDDATE\n"
//                            + "FROM BRLDTA0100.BP_STDATE A\n"
//                            + "JOIN BRLDTA0100.BP_MASTER B\n"
//                            + "ON B.BPM_CUNO = A.BPS_CUNO\n"
//                            + "AND B.BPM_CONO = A.BPS_CONO\n"
//                            + "AND C.BPS_CUNO = A.BPS_CUNO\n"
//                            + "AND C.BPS_CONO = A.BPS_CONO\n"
//                            + " FETCH FIRST 1 ROW ONLY)\n"
//                            + " WHERE  MONTH(CHAR(DATE(TIMESTAMP_FORMAT(cast( C.BPS_STDT as varchar(8)), 'YYYYMMDD')))) = " + nextmonth;
//
//                    //Create period
//                    String query3 = "Create period\n"
//                            + "UPDATE BRLDTA0100.BP_STDATE\n"
//                            + "SET BPS_PR = SUBSTRING(BPS_STDT,3,8) || SUBSTRING(BPS_FNDT,3,8)\n"
//                            + "WHERE  MONTH(CHAR(DATE(TIMESTAMP_FORMAT(cast(BPS_STDT as varchar(8)), 'YYYYMMDD')))) =" + nextmonth;
//
//                    //Create round date
//                    String query4 = "UPDATE BRLDTA0100.BP_STDATE T1\n"
//                            + "SET BPS_RD = (\n"
//                            + "SELECT COUNT(*)\n"
//                            + "FROM BRLDTA0100.BP_STDATE T2\n"
//                            + "WHERE T2.BPS_CUNO=T1.BPS_CUNO\n"
//                            + "AND DATE(TIMESTAMP_FORMAT(cast( T2 .BPS_STDT as varchar(8)), 'YYYYMMDD')) <=  DATE(TIMESTAMP_FORMAT(cast( T1 .BPS_STDT as varchar(8)), 'YYYYMMDD')) \n"
//                            + "AND MONTH(CHAR(DATE(TIMESTAMP_FORMAT(cast(T2.BPS_STDT as varchar(8)), 'YYYYMMDD')))) =" + nextmonth + "\n"
//                            + "GROUP BY T2.BPS_CUNO )\n"
//                            + "WHERE MONTH(CHAR(DATE(TIMESTAMP_FORMAT(cast(T1.BPS_STDT as varchar(8)), 'YYYYMMDD')))) =" + nextmonth;
                    System.out.println(query1);
                    stmt.executeQuery(query1);
                    // ResultSet mRes = stmt.executeQuery(query1);
//                    mRes = stmt2.executeQuery(query2);
//                    mRes = stmt3.executeQuery(query3);
//                    mRes = stmt4.executeQuery(query4);
                }
//                Map<String, Object> mMap = new HashMap<>();
//                mMap.put("result", "ok");
//                mMap.put("message", "update complete");
//                mJSonArr.put(mMap);
            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> mMap = new HashMap<>();
            mMap.put("result", "nok");
            mMap.put("message", e);
            mJSonArr.put(mMap);
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

    }

//---------------------------------------------------------------------------------
    public static String callPPS300A(String cono, String divi, JSONArray mJSonArr) {
        String xSHDATE = "", xPO = "", xPNLI = "", xQTY = "", xCATW = "", xCARNO = "", place = "", whs = "";
        //String xPNLI="",xQTY="",xCATW="",xCARNO="";
        String GRNDATE, GRNNO = null;

        try {
            //m3id = "MPM_1A1";
            //m3id = "MVXSECOFR"; 
            m3id = "PUR_1A1";
            m3pw = "lawson@90";
            appServer = "192.200.9.190";
            appPort = 16105;
            int i = 0;

            MNEHelper mne = new MNEHelper(appServer, appPort, mneLogOnUrl);
            mne.logInToM3(Integer.parseInt(cono), divi, m3id, m3pw);

            if (!mne.logInToM3(Integer.parseInt(cono), divi, m3id, m3pw)) {
                System.out.println(" Can not login to M3 system");
            }

            String PPS300_LD = mne.runM3Pgm("PPS300");
            System.out.println("PPS300_LD:" + PPS300_LD);

            if ((PPS300_LD).equals("")) {
                System.out.println(" ไม่สามารถเปิดโปรแกรม PPS300 ได้");
            }

            //---------------------------------------------------------
            if (mne.panel.equals("PPS300/A")) {

                for (i = 0; i <= mJSonArr.length() - 1; i++) {
                    xSHDATE = mJSonArr.getJSONObject(i).getString("RSHDATE").trim();
                    xPO = mJSonArr.getJSONObject(i).getString("RSUPP").trim();
                    xPNLI = mJSonArr.getJSONObject(i).getString("RTRUCK").trim();
                    xQTY = mJSonArr.getJSONObject(i).getString("RQTY").trim();
                    xCATW = mJSonArr.getJSONObject(i).getString("RUPRICE").trim();
                    xCARNO = mJSonArr.getJSONObject(i).getString("RLDI").trim();
                    place = mJSonArr.getJSONObject(i).getString("RLOTNO").trim();

                    if ("10".equals(cono) && "WTF".equals(place)) {
                        whs = "A45";
                    } else {
                        whs = "A31";
                    }

                    GRNDATE = xSHDATE.substring(6, 8) + xSHDATE.substring(4, 6) + xSHDATE.trim().substring(2, 4);
                    float xpQTY = Float.parseFloat(xQTY);
                    float xpCATW = Float.parseFloat(xCATW);

                    mne.setField("WWWHLO", whs);
                    mne.setField("WWTRDT", GRNDATE);
                    mne.setField("WWRESP", "PUR_1A1");
                    mne.setField("WWPUNO", xPO);
                    mne.setField("WWPNLI", xPNLI);
                    mne.setField("WWSUDO", "INV-" + xPO);

                    mne.pressKey(MNEProtocol.KeyEnter);
                    System.out.println(mne.getMsg());

                    int c = 0;
                    while ((mne.panel.equals("PPS300/A")) && (c <= 3)) {
                        mne.setField("WWTRDT", GRNDATE);
                        c++;
                        mne.pressKey(MNEProtocol.KeyEnter);
                        System.out.println(mne.getMsg());
                    }

                    if (mne.panel.equals("PPS300/E")) {
                        System.out.println("OK OK :PPS300/E");

                        mne.setField("WBRVQA", xpQTY);
                        mne.setField("WWCAWE", xpCATW);
                        //mne.setField("WLWHSL","A31100");
                        GRNNO = mne.getFieldMap("WWREPN").getValue().substring(0, 7);
//                        jTextGrnno.setText(GRNNO);
                        mne.pressKey(MNEProtocol.KeyEnter);
                        System.out.println(mne.getMsg());

                        c = 0;
                        while ((mne.panel.equals("PPS300/E")) && (c <= 3)) {
                            c++;
                            mne.pressKey(MNEProtocol.KeyEnter);
                            System.out.println(mne.getMsg());
                        }

                        if (mne.panel.equals("PPS300/A")) {
                            System.out.println("UPdate : PPS300/A ");
                            updateFAR_LQLDUCK1(xSHDATE, xCARNO, cono);
                            updateFAR_GRNCTL(xPO, xPNLI, GRNNO, cono);
                        }
                        //mne.closeProgram(PPS300_LD);     
                    }

                }

            }
            //mne.selectOption("2");
            mne.pressKey(MNEProtocol.KeyF03);
            mne.closeProgram(PPS300_LD);
        } catch (Exception e) {
            if (sock != null) {
                System.out.println("ERR: " + sock.mvxGetLastError());
            }
        }

        return GRNNO;
    }
//----------------------------------------------------------------------------------

    private static void updateFAR_GRNCTL(String xPONO, String xPOLN, String xGRNNO, String cono) {
        try {
            Connection conn = ConnectDB2.ConnectionDB();
            Statement sta = conn.createStatement();

            String Sql = "update BRLDTA0100.FAR_GRNCTL\n"
                    + "SET grnc_grn = '" + xGRNNO + "'\n"
                    + "WHERE CONOID = '" + cono + "'\n"
                    + "AND grnc_po = '" + xPONO + "'\n"
                    + "AND grnc_pnli = '" + xPOLN + "'";
            sta.executeUpdate(Sql);

        } catch (Exception ex) {
            Logger.getLogger(Select.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
//----------------------------------------------------------------------------------

    private static void updateFAR_LQLDUCK1(String xSHDATE, String xCarNo, String cono) {
        try {
            Connection conn = ConnectDB2.ConnectionDB();
            Statement sta = conn.createStatement();

            String Sql = "UPDATE BRLDTA0100.FAR_LQLDUCK01\n"
                    + "SET LQLI_STATUS = '97'\n"
                    + "WHERE CONOID = '" + cono + "'\n"
                    + "AND LQLI_STATUS = '95'\n"
                    + "AND SUBSTRING(CHAR(LQLI_SHDATE,ISO),0,5) || SUBSTRING(CHAR(LQLI_SHDATE,ISO),6,2) || SUBSTRING(CHAR(LQLI_SHDATE,ISO),9,2) = '" + xSHDATE + "'\n"
                    + "AND LQLI_TURCK = " + xCarNo;

            System.out.println(Sql);
            sta.executeUpdate(Sql);

        } catch (Exception ex) {
            Logger.getLogger(Select.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
//---------------------------------------------------------------------------------

    public static JSONArray Facility(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT CFFACI,CFFACN,CFFACI || ' : ' || CFFACN AS FACILITY \n"
                        + "FROM " + dbM3Name + ".CFACIL\n"
                        + "WHERE CFCONO = '" + cono + "'\n"
                        + "AND CFDIVI = '" + divi + "'";
                System.out.println("SelectFacility\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("CFFACI", mRes.getString(1).trim());
                    mMap.put("CFFACN", mRes.getString(2).trim());
                    mMap.put("FACILITY", mRes.getString(3).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static String getAddonLine(String cono, String divi, String whs, String period) throws Exception {

        Connection conn = ConnectSQLServer.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT COALESCE(MAX(RALINE),0) + 1 AS LINE\n"
                        + "FROM BRLAS400.dbo.T_RENTALADDON\n"
                        + "WHERE RACONO = '" + cono + "'\n"
                        + "AND RADIVI = '" + divi + "'\n"
                        + "AND RAWARE = '" + whs + "'\n"
                        + "AND RAPERI = '" + period + "'";
                System.out.println("getAddonLine\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    return mRes.getString("LINE").trim();

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return null;

    }

    public static JSONArray FacilityInventory(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT A1CONO,A1DIVI,A1CODE,A1DESC,A1NAME,A1TYPE,A1MUUN,A1RATE,COALESCE(A1REF1,'') AS A1REF1,COALESCE(A1REF2,'') AS A1REF2,A1CODE || ' : ' || A1DESC AS ALLOCATION\n"
                        + "FROM BRLDTA0100.A1CASU\n"
                        + "WHERE A1CONO = '" + cono + "'\n"
                        + "AND A1DIVI = '" + divi + "'\n"
                        + "AND A1CODE = '300'\n"
                        + "ORDER BY A1CODE";
                System.out.println("SelectFacility\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("A1CONO", mRes.getString(1).trim());
                    mMap.put("A1DIVI", mRes.getString(2).trim());
                    mMap.put("A1CODE", mRes.getString(3).trim());
                    mMap.put("A1DESC", mRes.getString(4).trim());
                    mMap.put("A1NAME", mRes.getString(5).trim());
                    mMap.put("A1TYPE", mRes.getString(6).trim());
                    mMap.put("A1MUUN", mRes.getString(7).trim());
                    mMap.put("A1RATE", mRes.getString(8).trim());
                    mMap.put("A1REF1", mRes.getString(9).trim());
                    mMap.put("A1REF2", mRes.getString(10).trim());
                    mMap.put("ALLOCATION", mRes.getString(11).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Type(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT ATCONO,ATDIVI,ATTYPE,ATNAME,ATREF1,ATTYPE || ' : ' || ATNAME AS TYPE\n"
                        + "FROM BRLDTA0100.A1TYPE\n"
                        + "WHERE ATCONO = '" + cono + "'\n"
                        + "AND ATDIVI = '" + divi + "'\n"
                        + "ORDER BY ATTYPE";
                System.out.println("SelectType\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("ATCONO", mRes.getString(1).trim());
                    mMap.put("ATDIVI", mRes.getString(2).trim());
                    mMap.put("ATTYPE", mRes.getString(3).trim());
                    mMap.put("ATNAME", mRes.getString(4).trim());
                    mMap.put("ATREF1", mRes.getString(5).trim());
                    mMap.put("TYPE", mRes.getString(6).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Level(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT A2CONO,A2DIVI,A2CODE,A2STID,A2SLVL,A2DESC,COALESCE(A2REF1,'') AS A2REF1,COALESCE(A2REF2,'') AS A2REF2\n"
                        + "FROM BRLDTA0100.A2CASL\n"
                        + "WHERE A2CONO = '" + cono + "'\n"
                        + "AND A2DIVI = '" + divi + "'\n"
                        + "ORDER BY A2CODE";
                System.out.println("SelectLevel\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("A2CONO", mRes.getString(1).trim());
                    mMap.put("A2DIVI", mRes.getString(2).trim());
                    mMap.put("A2CODE", mRes.getString(3).trim());
                    mMap.put("A2STID", mRes.getString(4).trim());
                    mMap.put("A2SLVL", mRes.getString(5).trim());
                    mMap.put("A2DESC", mRes.getString(6).trim());
                    mMap.put("A2REF1", mRes.getString(7).trim());
                    mMap.put("A2REF2", mRes.getString(8).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Period(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT PECONO,PEDIVI,PECODE,PEYEA4,PEMONT,PEDESC\n"
                        + "FROM BRLDTA0100.APERIO\n"
                        + "WHERE PECONO = '" + cono + "'\n"
                        + "AND PEDIVI = '" + divi + "'\n"
                        + "ORDER BY PECODE,PEYEA4,PEMONT";
                System.out.println("SelectPeriod\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("PECONO", mRes.getString(1).trim());
                    mMap.put("PEDIVI", mRes.getString(2).trim());
                    mMap.put("PECODE", mRes.getString(3).trim());
                    mMap.put("PEYEA4", mRes.getString(4).trim());
                    mMap.put("PEMONT", mRes.getString(5).trim());
                    mMap.put("PEDESC", mRes.getString(6).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray WorkCenter(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT A3CONO,A3DIVI,A3CODE,A3STID,CHAR(A3SLVL) AS A3SLVL,A3AITM,A3ASTR,A3ADES,A3METY,A3UEHR,A3MELA,A3PERS,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "ORDER BY A3CODE,A3STID,A3AITM";
                System.out.println("SelectWorkCenter\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("A3CONO", mRes.getString(1).trim());
                    mMap.put("A3DIVI", mRes.getString(2).trim());
                    mMap.put("A3CODE", mRes.getString(3).trim());
                    mMap.put("A3STID", mRes.getString(4).trim());
                    mMap.put("A3SLVL", mRes.getString(5).trim());
                    mMap.put("A3AITM", mRes.getString(6).trim());
                    mMap.put("A3ASTR", mRes.getString(7).trim());
                    mMap.put("A3ADES", mRes.getString(8).trim());
                    mMap.put("A3METY", mRes.getString(9).trim());
                    mMap.put("A3UEHR", mRes.getString(10).trim());
                    mMap.put("A3MELA", mRes.getString(11).trim());
                    mMap.put("A3PERS", mRes.getString(12).trim());
                    mMap.put("A3ACCT", mRes.getString(13).trim());
                    mMap.put("A3AAC1", mRes.getString(14).trim());
                    mMap.put("A3AAC2", mRes.getString(15).trim());
                    mMap.put("A3ABOI", mRes.getString(16).trim());
                    mMap.put("A3ARE1", mRes.getString(17).trim());
                    mMap.put("A3ARE2", mRes.getString(18).trim());
                    mMap.put("A3ARE3", mRes.getString(19).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray LevelDetail(String cono, String divi, String code, String struct, String level) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT A3CONO,A3DIVI,A3CODE,A3STID,A3SLVL,A3AITM,A3ASTR,A3ADES,A3METY,A3UEHR,A3MELA,A3PERS,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3STID = '" + struct + "'\n"
                        + "AND A3SLVL = '" + level + "'\n"
                        + "ORDER BY A3CODE,A3STID,A3AITM";
                System.out.println("SelectLevelDetail\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("A3CONO", mRes.getString(1).trim());
                    mMap.put("A3DIVI", mRes.getString(2).trim());
                    mMap.put("A3CODE", mRes.getString(3).trim());
                    mMap.put("A3STID", mRes.getString(4).trim());
                    mMap.put("A3SLVL", mRes.getString(5).trim());
                    mMap.put("A3AITM", mRes.getString(6).trim());
                    mMap.put("A3ASTR", mRes.getString(7).trim());
                    mMap.put("A3ADES", mRes.getString(8).trim());
                    mMap.put("A3METY", mRes.getString(9).trim());
                    mMap.put("A3UEHR", mRes.getString(10).trim());
                    mMap.put("A3MELA", mRes.getString(11).trim());
                    mMap.put("A3PERS", mRes.getString(12).trim());
                    mMap.put("A3ACCT", mRes.getString(13).trim());
                    mMap.put("A3AAC1", mRes.getString(14).trim());
                    mMap.put("A3AAC2", mRes.getString(15).trim());
                    mMap.put("A3ABOI", mRes.getString(16).trim());
                    mMap.put("A3ARE1", mRes.getString(17).trim());
                    mMap.put("A3ARE2", mRes.getString(18).trim());
                    mMap.put("A3ARE3", mRes.getString(19).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Bills(String cono, String divi, String period, String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT E1CONO, E1DIVI, E1CODE,TRIM(b.A1CODE || ' : ' || b.A1DESC) AS DESC, E1PECO, E1METS, E1METE, E1EPQT, E1EPAT, E1EVAT, E1ETAT, E1RAAT\n"
                        + "FROM BRLDTA0100.E1ALLO a,BRLDTA0100.A1CASU b\n"
                        + "WHERE E1CONO = '" + cono + "'\n"
                        + "AND E1DIVI = '" + divi + "'\n"
                        + "AND E1PECO = '" + period + "'\n"
                        + "AND E1TYPE = '" + type + "'\n"
                        + "AND b.A1CONO = E1CONO\n"
                        + "AND b.A1CODE = E1CODE\n"
                        + "ORDER BY E1PECO,E1CODE";
                System.out.println("SelectBills\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("E1CONO", mRes.getString(1).trim());
                    mMap.put("E1DIVI", mRes.getString(2).trim());
                    mMap.put("E1CODE", mRes.getString(3).trim());
                    mMap.put("DESC", mRes.getString(4).trim());
                    mMap.put("E1PECO", mRes.getString(5).trim());
                    mMap.put("E1METS", mRes.getString(6).trim());
                    mMap.put("E1METE", mRes.getString(7).trim());
                    mMap.put("E1EPQT", mRes.getString(8).trim());
                    mMap.put("E1EPAT", mRes.getString(9).trim());
                    mMap.put("E1EVAT", mRes.getString(10).trim());
                    mMap.put("E1ETAT", mRes.getString(11).trim());
                    mMap.put("E1RAAT", mRes.getString(12).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray BillsDetail(String cono, String divi, String code, String period) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT E1CONO, E1DIVI, E1CODE, E1PECO, E1METS, E1METE, E1EPQT, E1EPAT, E1EVAT, E1ETAT, E1RAAT\n"
                        + "FROM BRLDTA0100.E1ALLO\n"
                        + "WHERE E1CONO = '" + cono + "'\n"
                        + "AND E1DIVI = '" + divi + "'\n"
                        + "AND E1CODE = '" + code + "'\n"
                        + "AND E1PECO = '" + period + "'\n"
                        + "ORDER BY E1PECO,E1CODE";
                System.out.println("SelectBills\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("E1CONO", mRes.getString(1).trim());
                    mMap.put("E1DIVI", mRes.getString(2).trim());
                    mMap.put("E1CODE", mRes.getString(3).trim());
                    mMap.put("E1PECO", mRes.getString(4).trim());
                    mMap.put("E1METS", mRes.getString(5).trim());
                    mMap.put("E1METE", mRes.getString(6).trim());
                    mMap.put("E1EPQT", mRes.getString(7).trim());
                    mMap.put("E1EPAT", mRes.getString(8).trim());
                    mMap.put("E1EVAT", mRes.getString(9).trim());
                    mMap.put("E1ETAT", mRes.getString(10).trim());
                    mMap.put("E1RAAT", mRes.getString(11).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Allocation(String cono, String divi, String code, String period, String fromstatus, String tostatus, String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT E2CONO,E2DIVI,E2CODE,E2STID,E2SLVL,A2DESC,E2PECO,E2STAT\n"
                        + "FROM BRLDTA0100.E2ALLO,BRLDTA0100.A2CASL\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        + "AND E2TYPE = '" + type + "'\n"
                        + "AND A2CONO = E2CONO\n"
                        + "AND A2DIVI = E2DIVI\n"
                        + "AND A2STID = E2STID\n"
                        + "AND A2CODE = E2CODE\n"
                        + "AND E2STAT BETWEEN '" + fromstatus + "' AND '" + tostatus + "'\n"
                        + "GROUP BY E2CONO,E2DIVI,E2CODE,E2STID,E2SLVL,A2DESC,E2PECO,E2STAT\n"
                        + "ORDER BY E2CODE,E2PECO,E2STID,E2SLVL";
                System.out.println("SelectAllocation\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("E2CONO", mRes.getString(1).trim());
                    mMap.put("E2DIVI", mRes.getString(2).trim());
                    mMap.put("E2CODE", mRes.getString(3).trim());
                    mMap.put("E2STID", mRes.getString(4).trim());
                    mMap.put("E2SLVL", mRes.getString(5).trim());
                    mMap.put("A2DESC", mRes.getString(6).trim());
                    mMap.put("E2PECO", mRes.getString(7).trim());
                    mMap.put("E2STAT", mRes.getString(8).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray AllocationDetail(String cono, String divi, String code, String struct, String level, String period, String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT E2CONO,E2DIVI,E2CODE,E2STID,E2SLVL,E2AITM,A3ADES,E2PECO,E2METS,E2METE,E2UEHR,E2HOUR,CAST(E2EPQT AS DECIMAL(15,2)) AS E2EPQT,CAST(E2PERS AS DECIMAL(15,2)) AS E2PERS,ROUND(E2EAQT,4) AS E2EAQT,ROUND(E2EAAT,4) AS E2EAAT,E2STAT\n"
                        + "FROM BRLDTA0100.E2ALLO,BRLDTA0100.A3CAWC\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2STID = '" + struct + "'\n"
                        + "AND E2SLVL = '" + level + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        + "AND E2TYPE = '" + type + "'\n"
                        + "AND A3CONO = E2CONO\n"
                        + "AND A3DIVI = E2DIVI\n"
                        + "AND A3CODE = E2CODE\n"
                        + "AND A3AITM = E2AITM\n"
                        + "ORDER BY E2STID,E2SLVL,E2AITM";
                System.out.println("SelectAllocationDetail\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("E2CONO", mRes.getString(1).trim());
                    mMap.put("E2DIVI", mRes.getString(2).trim());
                    mMap.put("E2CODE", mRes.getString(3).trim());
                    mMap.put("E2STID", mRes.getString(4).trim());
                    mMap.put("E2SLVL", mRes.getString(5).trim());
                    mMap.put("E2AITM", mRes.getString(6).trim());
                    mMap.put("A3ADES", mRes.getString(7).trim());
                    mMap.put("E2PECO", mRes.getString(8).trim());
                    mMap.put("E2METS", mRes.getString(9).trim());
                    mMap.put("E2METE", mRes.getString(10).trim());
                    mMap.put("E2UEHR", mRes.getString(11).trim());
                    mMap.put("E2HOUR", mRes.getString(12).trim());
                    mMap.put("E2EPQT", mRes.getString(13).trim());
                    mMap.put("E2PERS", mRes.getString(14).trim());
                    mMap.put("E2EAQT", mRes.getString(15).trim());
                    mMap.put("E2EAAT", mRes.getString(16).trim());
                    mMap.put("E2STAT", mRes.getString(17).trim());
                    mJSonArr.put(mMap);

                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray Voucher(String cono, String divi, String code, String period, String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT NO,a.A3ACCT AS ACCC,a.A3AAC1 AS COST,'' AS BONO,SUBSTRING(A3ADES,0,9) AS REF1,A3ARE2 AS REF2,A3ARE3 AS REF3,'Accu. ' || TRIM(c.A1DESC) ||  ' for ' || TRIM(b.E2PECO) AS VOUC,ROUND(b.E2EAAT,4) AS AMT,b.E2STAT\n"
                        + "FROM\n"
                        + "(SELECT NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM \n"
                        + "(SELECT '1' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '2'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM) a\n"
                        + "UNION ALL \n"
                        + "(SELECT '2' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC a\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '1'\n"
                        + "AND A3ASTR = '0'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM)) AS a\n"
                        + "INNER JOIN\n"
                        + "(SELECT E2CONO, E2DIVI, E2CODE, E2STID, E2SLVL, E2AITM, E2PECO, E2METS, E2METE, E2UEHR, E2HOUR, E2EPQT, E2PERS, E2EAQT, E2EAAT, E2STAT\n"
                        + "FROM BRLDTA0100.E2ALLO a\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        + "AND E2STAT = '10'\n"
                        + "AND E2TYPE = '" + type + "'\n"
                        //+ "AND E2EAAT > 0\n"
                        + ") AS b\n"
                        + "ON b.E2AITM = a.A3AITM\n"
                        + "INNER JOIN\n"
                        + "(SELECT A1CONO, A1DIVI, A1CODE, A1DESC, A1NAME, A1TYPE, A1MUUN, A1RATE, A1REF1, A1REF2\n"
                        + "FROM BRLDTA0100.A1CASU) AS c\n"
                        + "ON c.A1CONO = b.E2CONO\n"
                        + "AND c.A1DIVI = b.E2DIVI\n"
                        + "AND c.A1CODE = b.E2CODE\n"
                        + "UNION ALL\n"
                        + "SELECT '3' AS NO,c.A1REF1 AS ACCC,'' AS COST,'' AS BONO,'HO' AS REF1,CHAR(b.E2PECO) AS REF2,'' AS REF3,'Accu. ' || TRIM(c.A1DESC) ||  ' for ' || TRIM(b.E2PECO) AS VOUC,ROUND(SUM(E2EAAT) * -1,4) AS AMT,b.E2STAT\n"
                        + "FROM BRLDTA0100.A3CAWC a,BRLDTA0100.E2ALLO b,BRLDTA0100.A1CASU c\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3ASTR = '0'\n"
                        + "AND b.E2CONO = A3CONO\n"
                        + "AND b.E2DIVI = A3DIVI\n"
                        + "AND b.E2CODE = A3CODE\n"
                        + "AND b.E2AITM = A3AITM\n"
                        + "AND b.E2PECO = '" + period + "'\n"
                        + "AND b.E2STAT = '10'\n"
                        + "AND b.E2TYPE = '" + type + "'\n"
                        + "AND c.A1CONO = A3CONO\n"
                        + "AND c.A1DIVI = A3DIVI\n"
                        + "AND c.A1CODE = A3CODE\n"
                        + "GROUP BY c.A1REF1,c.A1DESC,b.E2PECO,b.E2STAT\n"
                        + "ORDER BY NO";
                System.out.println("Voucher\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("NO", mRes.getString(1).trim());
                    mMap.put("ACCC", mRes.getString(2).trim());
                    mMap.put("COST", mRes.getString(3).trim());
                    mMap.put("BONO", mRes.getString(4).trim());
                    mMap.put("REF1", mRes.getString(5).trim());
                    mMap.put("REF2", mRes.getString(6).trim());
                    mMap.put("REF3", mRes.getString(7).trim());
                    mMap.put("VOUC", mRes.getString(8).trim());
                    mMap.put("AMT", mRes.getString(9).trim());
                    mMap.put("E2STAT", mRes.getString(10).trim());
                    mJSonArr.put(mMap);
//                    System.out.println(mJSonArr);
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static JSONArray VoucherVariance(String cono, String divi, String code, String period, String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT NO,a.A3ACCT AS ACCC,COALESCE(a.A3AAC1,'') AS COST,'' AS BONO,SUBSTRING(A3ADES,0,9) AS REF1,A3ARE2 AS REF2,A3ARE3 AS REF3,'Var. ' || TRIM(c.A1DESC) ||  ' for ' || SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),0,5)||SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),6,2) AS VOUC,ROUND(b.E2EAAT,4) AS AMT,b.E2STAT\n"
                        + "FROM\n"
                        + "(SELECT NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM \n"
                        + "(SELECT '1' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '2'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM) a\n"
                        + "UNION ALL \n"
                        + "(SELECT '2' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC a\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '1'\n"
                        + "AND A3ASTR = '0'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM)) AS a\n"
                        + "INNER JOIN\n"
                        + "(SELECT E2CONO, E2DIVI, E2CODE, E2STID, E2SLVL, E2AITM, E2PECO, E2METS, E2METE, E2UEHR, E2HOUR, E2EPQT, E2PERS, E2EAQT, E2EAAT, E2STAT\n"
                        + "FROM BRLDTA0100.E2ALLO a\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        + "AND E2STAT = '10'\n"
                        + "AND E2TYPE = '" + type + "'\n"
                        //+ "AND E2EAAT > 0\n"
                        + ") AS b\n"
                        + "ON b.E2AITM = a.A3AITM\n"
                        + "INNER JOIN\n"
                        + "(SELECT A1CONO, A1DIVI, A1CODE, A1DESC, A1NAME, A1TYPE, A1MUUN, A1RATE, A1REF1, A1REF2, A1REF3\n"
                        + "FROM BRLDTA0100.A1CASU) AS c\n"
                        + "ON c.A1CONO = b.E2CONO\n"
                        + "AND c.A1DIVI = b.E2DIVI\n"
                        + "AND c.A1CODE = b.E2CODE\n"
                        + "UNION ALL\n"
                        + "SELECT '3' AS NO,c.A1REF2 AS ACCC,COALESCE(c.A1REF3,'') AS COST,'' AS BONO,'HO' AS REF1,CHAR(b.E2PECO) AS REF2,'' AS REF3,'Var. ' || TRIM(c.A1DESC) ||  ' for ' || SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),0,5)||SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),6,2) AS VOUC,ROUND(SUM(E2EAAT) * -1,4) AS AMT,b.E2STAT\n"
                        + "FROM BRLDTA0100.A3CAWC a,BRLDTA0100.E2ALLO b,BRLDTA0100.A1CASU c\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3ASTR = '0'\n"
                        + "AND b.E2CONO = A3CONO\n"
                        + "AND b.E2DIVI = A3DIVI\n"
                        + "AND b.E2CODE = A3CODE\n"
                        + "AND b.E2AITM = A3AITM\n"
                        + "AND b.E2PECO = '" + period + "'\n"
                        + "AND b.E2STAT = '10'\n"
                        + "AND b.E2TYPE = '" + type + "'\n"
                        + "AND c.A1CONO = A3CONO\n"
                        + "AND c.A1DIVI = A3DIVI\n"
                        + "AND c.A1CODE = A3CODE\n"
                        + "GROUP BY c.A1REF2,c.A1REF3,c.A1DESC,b.E2PECO,b.E2STAT\n"
                        + "ORDER BY NO";
                System.out.println("Voucher\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("NO", mRes.getString(1).trim());
                    mMap.put("ACCC", mRes.getString(2).trim());
                    mMap.put("COST", mRes.getString(3).trim());
                    mMap.put("BONO", mRes.getString(4).trim());
                    mMap.put("REF1", mRes.getString(5).trim());
                    mMap.put("REF2", mRes.getString(6).trim());
                    mMap.put("REF3", mRes.getString(7).trim());
                    mMap.put("VOUC", mRes.getString(8).trim());
                    mMap.put("AMT", mRes.getString(9).trim());
                    mMap.put("E2STAT", mRes.getString(10).trim());
                    mJSonArr.put(mMap);
//                    System.out.println(mJSonArr);
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static List<String> RSVoucher(String cono, String divi, String code, String period, String type) {

        List<String> getList = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = ConnectDB2.ConnectionDB();
            Statement stmt = conn.createStatement();
            String query = "SELECT NO,a.A3ACCT AS ACCC,a.A3AAC1 AS COST,'' AS BONO,SUBSTRING(A3ADES,0,9) AS REF1,A3ARE2 AS REF2,A3ARE3 AS REF3,'Accu. ' || TRIM(c.A1DESC) ||  ' for ' || TRIM(b.E2PECO) AS VOUC,ROUND(b.E2EAAT,4) AS AMT,b.E2STAT\n"
                    + "FROM\n"
                    + "(SELECT NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM \n"
                    + "(SELECT '1' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM BRLDTA0100.A3CAWC\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3SLVL = '2'\n"
                    + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "ORDER BY A3STID,A3AITM) a\n"
                    + "UNION ALL \n"
                    + "(SELECT '2' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM BRLDTA0100.A3CAWC a\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3SLVL = '1'\n"
                    + "AND A3ASTR = '0'\n"
                    + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "ORDER BY A3STID,A3AITM)) AS a\n"
                    + "INNER JOIN\n"
                    + "(SELECT E2CONO, E2DIVI, E2CODE, E2STID, E2SLVL, E2AITM, E2PECO, E2METS, E2METE, E2UEHR, E2HOUR, E2EPQT, E2PERS, E2EAQT, E2EAAT, E2STAT\n"
                    + "FROM BRLDTA0100.E2ALLO a\n"
                    + "WHERE E2CONO = '" + cono + "'\n"
                    + "AND E2DIVI = '" + divi + "'\n"
                    + "AND E2CODE = '" + code + "'\n"
                    + "AND E2PECO = '" + period + "'\n"
                    + "AND E2STAT = '10'\n"
                    + "AND E2TYPE = '" + type + "'\n"
                    + "AND E2EAAT > 0\n"
                    + ") AS b\n"
                    + "ON b.E2AITM = a.A3AITM\n"
                    + "INNER JOIN\n"
                    + "(SELECT A1CONO, A1DIVI, A1CODE, A1DESC, A1NAME, A1TYPE, A1MUUN, A1RATE, A1REF1, A1REF2\n"
                    + "FROM BRLDTA0100.A1CASU) AS c\n"
                    + "ON c.A1CONO = b.E2CONO\n"
                    + "AND c.A1DIVI = b.E2DIVI\n"
                    + "AND c.A1CODE = b.E2CODE\n"
                    + "UNION ALL\n"
                    + "SELECT '3' AS NO,c.A1REF1 AS ACCC,'' AS COST,'' AS BONO,'HO' AS REF1,CHAR(b.E2PECO) AS REF2,'' AS REF3,'Accu. ' || TRIM(c.A1DESC) ||  ' for ' || TRIM(b.E2PECO) AS VOUC,ROUND(SUM(E2EAAT) * -1,4) AS AMT,b.E2STAT\n"
                    + "FROM BRLDTA0100.A3CAWC a,BRLDTA0100.E2ALLO b,BRLDTA0100.A1CASU c\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3ASTR = '0'\n"
                    + "AND b.E2CONO = A3CONO\n"
                    + "AND b.E2DIVI = A3DIVI\n"
                    + "AND b.E2CODE = A3CODE\n"
                    + "AND b.E2AITM = A3AITM\n"
                    + "AND b.E2PECO = '" + period + "'\n"
                    + "AND b.E2STAT = '10'\n"
                    + "AND b.E2TYPE = '" + type + "'\n"
                    + "AND c.A1CONO = A3CONO\n"
                    + "AND c.A1DIVI = A3DIVI\n"
                    + "AND c.A1CODE = A3CODE\n"
                    + "GROUP BY c.A1REF1,c.A1DESC,b.E2PECO,b.E2STAT\n"
                    + "ORDER BY NO";
            System.out.println("RSVoucher\n" + query);
            ResultSet mRes = stmt.executeQuery(query);

            while (mRes.next()) {
                getList.add(
                        mRes.getString("NO").trim() + " ; "
                        + mRes.getString("ACCC").trim() + " ; "
                        + mRes.getString("COST").trim() + " ; "
                        + mRes.getString("BONO").trim() + " ; "
                        + mRes.getString("REF1").trim() + " ; "
                        + mRes.getString("REF2").trim() + " ; "
                        + mRes.getString("REF3").trim() + " ; "
                        + mRes.getString("VOUC").trim() + " ; "
                        + mRes.getString("AMT").trim() + " ; "
                        + mRes.getString("E2STAT").trim());
            }
            return getList;

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
        return null;
    }

    public static List<String> RSVoucherVariance(String cono, String divi, String code, String period, String type) {

        List<String> getList = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = ConnectDB2.ConnectionDB();
            Statement stmt = conn.createStatement();
            String query = "SELECT NO,a.A3ACCT AS ACCC,a.A3AAC1 AS COST,'' AS BONO,SUBSTRING(A3ADES,0,9) AS REF1,A3ARE2 AS REF2,A3ARE3 AS REF3,'Var. ' || TRIM(c.A1DESC) ||  ' for ' || SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),0,5)||SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),6,2) AS VOUC,ROUND(b.E2EAAT,4) AS AMT,b.E2STAT\n"
                    + "FROM\n"
                    + "(SELECT NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM \n"
                    + "(SELECT '1' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM BRLDTA0100.A3CAWC\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3SLVL = '2'\n"
                    + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "ORDER BY A3STID,A3AITM) a\n"
                    + "UNION ALL \n"
                    + "(SELECT '2' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM BRLDTA0100.A3CAWC a\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3SLVL = '1'\n"
                    + "AND A3ASTR = '0'\n"
                    + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "ORDER BY A3STID,A3AITM)) AS a\n"
                    + "INNER JOIN\n"
                    + "(SELECT E2CONO, E2DIVI, E2CODE, E2STID, E2SLVL, E2AITM, E2PECO, E2METS, E2METE, E2UEHR, E2HOUR, E2EPQT, E2PERS, E2EAQT, E2EAAT, E2STAT\n"
                    + "FROM BRLDTA0100.E2ALLO a\n"
                    + "WHERE E2CONO = '" + cono + "'\n"
                    + "AND E2DIVI = '" + divi + "'\n"
                    + "AND E2CODE = '" + code + "'\n"
                    + "AND E2PECO = '" + period + "'\n"
                    + "AND E2STAT = '10'\n"
                    + "AND E2TYPE = '" + type + "'\n"
                    //                    + "AND E2EAAT > 0\n"
                    + ") AS b\n"
                    + "ON b.E2AITM = a.A3AITM\n"
                    + "INNER JOIN\n"
                    + "(SELECT A1CONO, A1DIVI, A1CODE, A1DESC, A1NAME, A1TYPE, A1MUUN, A1RATE, A1REF1, A1REF2, A1REF3\n"
                    + "FROM BRLDTA0100.A1CASU) AS c\n"
                    + "ON c.A1CONO = b.E2CONO\n"
                    + "AND c.A1DIVI = b.E2DIVI\n"
                    + "AND c.A1CODE = b.E2CODE\n"
                    + "UNION ALL\n"
                    + "SELECT '3' AS NO,c.A1REF2 AS ACCC,c.A1REF3 AS COST,'' AS BONO,'HO' AS REF1,CHAR(b.E2PECO) AS REF2,'' AS REF3,'Var. ' || TRIM(c.A1DESC) ||  ' for ' || SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),0,5)||SUBSTRING(CHAR(DATE(SUBSTRING(b.E2PECO,0,5)||'-'||SUBSTRING(b.E2PECO,5,2)||'-'||'01') - 1 MONTH, ISO),6,2) AS VOUC,ROUND(SUM(E2EAAT) * -1,4) AS AMT,b.E2STAT\n"
                    + "FROM BRLDTA0100.A3CAWC a,BRLDTA0100.E2ALLO b,BRLDTA0100.A1CASU c\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3ASTR = '0'\n"
                    + "AND b.E2CONO = A3CONO\n"
                    + "AND b.E2DIVI = A3DIVI\n"
                    + "AND b.E2CODE = A3CODE\n"
                    + "AND b.E2AITM = A3AITM\n"
                    + "AND b.E2PECO = '" + period + "'\n"
                    + "AND b.E2STAT = '10'\n"
                    + "AND b.E2TYPE = '" + type + "'\n"
                    + "AND c.A1CONO = A3CONO\n"
                    + "AND c.A1DIVI = A3DIVI\n"
                    + "AND c.A1CODE = A3CODE\n"
                    + "GROUP BY c.A1REF2,c.A1REF3,c.A1DESC,b.E2PECO,b.E2STAT\n"
                    + "ORDER BY NO";
            System.out.println("RSVoucherVariance\n" + query);
            ResultSet mRes = stmt.executeQuery(query);

            while (mRes.next()) {
                getList.add(
                        mRes.getString("NO").trim() + " ; "
                        + mRes.getString("ACCC").trim() + " ; "
                        + mRes.getString("COST").trim() + " ; "
                        + mRes.getString("BONO").trim() + " ; "
                        + mRes.getString("REF1").trim() + " ; "
                        + mRes.getString("REF2").trim() + " ; "
                        + mRes.getString("REF3").trim() + " ; "
                        + mRes.getString("VOUC").trim() + " ; "
                        + mRes.getString("AMT").trim() + " ; "
                        + mRes.getString("E2STAT").trim());
            }
            return getList;

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
        return null;
    }

    public static JSONArray Inventory(String cono, String divi, String code, String period, String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT NO,a.A3ACCT AS ACCC,a.A3AAC1 AS COST,A3ADES AS DESC,A3ARE1 AS REF1,A3ARE2 AS REF2,A3ARE3 AS REF3,'Accu. ' || TRIM(c.A1DESC) ||  ' for ' || TRIM(b.E2PECO) AS VOUC,ROUND(b.E2EAQT,4) AS QTY,b.E2STAT\n"
                        + "FROM\n"
                        + "(SELECT NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM \n"
                        + "(SELECT '1' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '2'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM) a\n"
                        + "UNION ALL \n"
                        + "(SELECT '2' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC a\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '1'\n"
                        + "AND A3ASTR = '0'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM)) AS a\n"
                        + "INNER JOIN\n"
                        + "(SELECT E2CONO, E2DIVI, E2CODE, E2STID, E2SLVL, E2AITM, E2PECO, E2METS, E2METE, E2UEHR, E2HOUR, E2EPQT, E2PERS, E2EAQT, E2EAAT, E2STAT\n"
                        + "FROM BRLDTA0100.E2ALLO a\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        + "AND E2STAT = '10'\n"
                        //+ "AND E2EAAT > 0\n"
                        + "AND E2TYPE = '" + type + "'\n"
                        + ") AS b\n"
                        + "ON b.E2AITM = a.A3AITM\n"
                        + "INNER JOIN\n"
                        + "(SELECT A1CONO, A1DIVI, A1CODE, A1DESC, A1NAME, A1TYPE, A1MUUN, A1RATE, A1REF1, A1REF2\n"
                        + "FROM BRLDTA0100.A1CASU) AS c\n"
                        + "ON c.A1CONO = b.E2CONO\n"
                        + "AND c.A1DIVI = b.E2DIVI\n"
                        + "AND c.A1CODE = b.E2CODE\n"
                        + "ORDER BY NO";
                System.out.println("Inventory\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("NO", mRes.getString(1).trim());
                    mMap.put("ACCC", mRes.getString(2).trim());
                    mMap.put("COST", mRes.getString(3).trim());
                    mMap.put("DESC", mRes.getString(4).trim());
                    mMap.put("REF1", mRes.getString(5).trim());
                    mMap.put("REF2", mRes.getString(6).trim());
                    mMap.put("REF3", mRes.getString(7).trim());
                    mMap.put("VOUC", mRes.getString(8).trim());
                    mMap.put("QTY", mRes.getString(9).trim());
                    mMap.put("E2STAT", mRes.getString(10).trim());
                    mJSonArr.put(mMap);
//                    System.out.println(mJSonArr);
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

    public static List<String> RSInventory(String cono, String divi, String code, String period) {

        List<String> getList = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = ConnectDB2.ConnectionDB();
            Statement stmt = conn.createStatement();
            String query = "SELECT NO,a.A3ACCT AS ACCC,a.A3AAC1 AS COST,A3ADES AS DESC,A3ARE1 AS REF1,A3ARE2 AS REF2,A3ARE3 AS REF3,'Accu. ' || TRIM(c.A1DESC) ||  ' for ' || TRIM(b.E2PECO) AS VOUC,ROUND(b.E2EAQT,4) AS QTY,b.E2STAT\n"
                    + "FROM\n"
                    + "(SELECT NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM \n"
                    + "(SELECT '1' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM BRLDTA0100.A3CAWC\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3SLVL = '2'\n"
                    + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "ORDER BY A3STID,A3AITM) a\n"
                    + "UNION ALL \n"
                    + "(SELECT '2' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "FROM BRLDTA0100.A3CAWC a\n"
                    + "WHERE A3CONO = '" + cono + "'\n"
                    + "AND A3DIVI = '" + divi + "'\n"
                    + "AND A3CODE = '" + code + "'\n"
                    + "AND A3SLVL = '1'\n"
                    + "AND A3ASTR = '0'\n"
                    + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                    + "ORDER BY A3STID,A3AITM)) AS a\n"
                    + "INNER JOIN\n"
                    + "(SELECT E2CONO, E2DIVI, E2CODE, E2STID, E2SLVL, E2AITM, E2PECO, E2METS, E2METE, E2UEHR, E2HOUR, E2EPQT, E2PERS, E2EAQT, E2EAAT, E2STAT\n"
                    + "FROM BRLDTA0100.E2ALLO a\n"
                    + "WHERE E2CONO = '" + cono + "'\n"
                    + "AND E2DIVI = '" + divi + "'\n"
                    + "AND E2CODE = '" + code + "'\n"
                    + "AND E2PECO = '" + period + "'\n"
                    + "AND E2STAT = '10'\n"
                    + "AND E2EAQT > 0\n"
                    + ") AS b\n"
                    + "ON b.E2AITM = a.A3AITM\n"
                    + "INNER JOIN\n"
                    + "(SELECT A1CONO, A1DIVI, A1CODE, A1DESC, A1NAME, A1TYPE, A1MUUN, A1RATE, A1REF1, A1REF2\n"
                    + "FROM BRLDTA0100.A1CASU) AS c\n"
                    + "ON c.A1CONO = b.E2CONO\n"
                    + "AND c.A1DIVI = b.E2DIVI\n"
                    + "AND c.A1CODE = b.E2CODE\n"
                    + "ORDER BY NO";
            System.out.println("RSInventory\n" + query);
            ResultSet mRes = stmt.executeQuery(query);

            while (mRes.next()) {
                getList.add(
                        mRes.getString("NO").trim() + " ; "
                        + mRes.getString("ACCC").trim() + " ; "
                        + mRes.getString("COST").trim() + " ; "
                        + mRes.getString("DESC").trim() + " ; "
                        + mRes.getString("REF1").trim() + " ; "
                        + mRes.getString("REF2").trim() + " ; "
                        + mRes.getString("REF3").trim() + " ; "
                        + mRes.getString("VOUC").trim() + " ; "
                        + mRes.getString("QTY").trim() + " ; "
                        + mRes.getString("E2STAT").trim());
            }
            return getList;

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
        return null;
    }

    public static List<String> RSInvenLot(String cono, String divi, String whs, String item) {

        List<String> getList = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = ConnectDB2.ConnectionDB();
            Statement stmt = conn.createStatement();
            String query = "SELECT MLITNO,MLBANO,DECIMAL(MLSTQT-MLALQT,10,2) ONHAND\n"
                    + "FROM " + dbM3Name + ".MITLOC\n"
                    + "WHERE MLCONO = '" + cono + "'\n"
                    + "AND MLWHLO = '" + whs + "'\n"
                    + "AND MLSTAS = 2\n"
                    + "AND MLITNO = '" + item + "'\n"
                    + "ORDER BY MLPRDT";
            System.out.println("RSInvenLot\n" + query);
            ResultSet mRes = stmt.executeQuery(query);

            while (mRes.next()) {
                getList.add(
                        mRes.getString("MLITNO").trim() + " ; "
                        + mRes.getString("MLBANO").trim() + " ; "
                        + mRes.getString("ONHAND").trim());
            }
            return getList;

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
        return null;
    }

    public static JSONArray Monitor(String cono, String divi, String code, String period, String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT NO,a.A3ACCT AS ACCC,a.A3AAC1 AS COST,'' AS BONO,A3ADES AS REF1,A3ARE2 AS REF2,A3ARE3 AS REF3,'Accu. ' || TRIM(c.A1DESC) ||  ' for ' || TRIM(b.E2PECO) AS VOUC,ROUND(b.E2EAQT,4) AS QTY,ROUND(b.E2EAAT,4) AS AMT,b.E2STAT\n"
                        + "FROM\n"
                        + "(SELECT NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM \n"
                        + "(SELECT '1' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '2'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM) a\n"
                        + "UNION ALL \n"
                        + "(SELECT '2' AS NO,A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "FROM BRLDTA0100.A3CAWC a\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3SLVL = '1'\n"
                        + "AND A3ASTR = '0'\n"
                        + "GROUP BY A3STID,A3AITM,A3ADES,A3ACCT,A3AAC1,A3AAC2,A3ABOI,A3ARE1,A3ARE2,A3ARE3\n"
                        + "ORDER BY A3STID,A3AITM)) AS a\n"
                        + "INNER JOIN\n"
                        + "(SELECT E2CONO, E2DIVI, E2CODE, E2STID, E2SLVL, E2AITM, E2PECO, E2METS, E2METE, E2UEHR, E2HOUR, E2EPQT, E2PERS, E2EAQT, E2EAAT, E2STAT\n"
                        + "FROM BRLDTA0100.E2ALLO a\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        //+ "AND E2STAT = '10'\n"
                        //+ "AND E2EAAT > 0\n"
                        + "AND E2TYPE = '" + type + "'\n"
                        + ") AS b\n"
                        + "ON b.E2AITM = a.A3AITM\n"
                        + "INNER JOIN\n"
                        + "(SELECT A1CONO, A1DIVI, A1CODE, A1DESC, A1NAME, A1TYPE, A1MUUN, A1RATE, A1REF1, A1REF2\n"
                        + "FROM BRLDTA0100.A1CASU) AS c\n"
                        + "ON c.A1CONO = b.E2CONO\n"
                        + "AND c.A1DIVI = b.E2DIVI\n"
                        + "AND c.A1CODE = b.E2CODE\n"
                        + "ORDER BY NO";
                System.out.println("Monitor\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("NO", mRes.getString(1).trim());
                    mMap.put("ACCC", mRes.getString(2).trim());
                    mMap.put("COST", mRes.getString(3).trim());
                    mMap.put("BONO", mRes.getString(4).trim());
                    mMap.put("REF1", mRes.getString(5).trim());
                    mMap.put("REF2", mRes.getString(6).trim());
                    mMap.put("REF3", mRes.getString(7).trim());
                    mMap.put("VOUC", mRes.getString(8).trim());
                    mMap.put("QTY", mRes.getString(9).trim());
                    mMap.put("AMT", mRes.getString(10).trim());
                    mMap.put("E2STAT", mRes.getString(11).trim());
                    mJSonArr.put(mMap);
//                    System.out.println(mJSonArr);
                }

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return mJSonArr;

    }

}
