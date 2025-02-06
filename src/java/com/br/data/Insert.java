/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.data;

import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import static com.br.data.Select.Double2digitReturn;
import static com.br.data.Select.GetDateDecmalCurrenttime;
import static com.br.data.Select.GetDateFormatSet;
import static com.br.data.Select.GetDateFormatSetShowString;
import static com.br.data.Select.GetDecmalTodate;
import static com.br.data.Select.GetDetail_OFFSET;
import static com.br.data.Select.checkTypeGRN;
import static com.br.data.Select.get_SemiColonValue0;
import static java.lang.Integer.parseInt;
import static com.br.utility.Constant.dbname;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jettison.json.JSONArray;
import static com.br.utility.Constant.dbname;
import static com.br.utility.Constant.dbM3Name;

/**
 *
 * @author Wattana
 */
public class Insert {

    public static JSONArray SavePrvForm(
            String EPPA_NO, String EPPA_DATE, String EPPA_DUEDT,
            String EPPA_SUNO, String EPPA_COCE, String EPPA_PAMT,
            String EPPA_PARM, String EPPA_REQBY, String EPPA_APPBY,
            String EPPA_APPDT, String EPPA_ADVREF,
            String EPPA_ADVAMT, String EPPA_REF1, String EPPA_REF2,
            String EPPA_REF3, String EPPA_STAT, String currentdate, String app,
            String cono, String divi
    ) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        JSONArray mJSonArr2 = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String digit2year = currentdate.substring(2, 4);
        Integer digit2year_EPRH_PHNO_int = Integer.parseInt(digit2year) + 43;
        String digit2yearnew = Integer.toString(digit2year_EPRH_PHNO_int);
        String EPRH_GN = null;
        String EPRH_PO = null;
        String EPRH_NO = null;
        String StrUpdateSrnNo;
        String StrInsertHeader;
        String Process = "";

        String appnumber = null;
        if ("ERN".equals(app)) {
            appnumber = "1";
        }
        if ("NEN".equals(app)) {
            appnumber = "2";
        }
        if ("GRN".equals(app)) {
            appnumber = "3";
        }
        try {
            if (conn != null) {
                //GET 3 VALUES
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();
                EPPA_SUNO = get_SemiColonValue0(EPPA_SUNO);
                EPPA_COCE = get_SemiColonValue0(EPPA_COCE);
                if (EPPA_NO == "" && EPPA_NO.isEmpty()) {
                    Process = " Has been Created";
                    String query = "SELECT CASE WHEN \n "
                            + " CAST(MAX(EPPA_NO) AS DECIMAL(10,0)) > 0 THEN \n"
                            + "'" + digit2yearnew + "'||'" + appnumber + "'|| MAX(SUBSTRING(EPPA_NO,4,5))+1  \n"
                            + "ELSE '" + digit2yearnew + "'||'" + appnumber + "'||'00001' END AS EPRH_PHNO\n"
                            + "FROM  " + dbname + ".PAYMENTHEAD\n"
                            + "WHERE SUBSTRING(EPPA_NO,1,2) =" + digit2yearnew + "\n"
                            + "AND EPPA_CONO =" + cono + "\n"
                            + "AND EPPA_DIVI =" + divi + "\n"
                            + "AND SUBSTRING(EPPA_NO,3,1) =" + appnumber;
                    System.out.println("Get_PUR_SRNC\n" + query);
                    ResultSet mRes = stmt.executeQuery(query);
                    //GET 3 VALUES
                    while (mRes.next()) {
                        Map<String, Object> mMap = new HashMap<>();
                        EPRH_NO = mRes.getString("EPRH_PHNO");
                        mJSonArr.put(mMap);
                    }

                    StrInsertHeader = "   INSERT INTO " + dbname + ".PAYMENTHEAD (EPPA_NO, EPPA_DATE, EPPA_DUEDT,\n"
                            + "EPRA_SUNO, EPPA_COCE, EPPA_PAMT,   EPRA_PARM, EPRA_REQBY, EPRA_APPBY,\n"
                            + "EPRA_APPDT, EPRA_ADVREF, EPRA_ADVAMT,   EPRA_REF1, EPRA_REF2, EPRA_REF3, EPRA_STAT,EPPA_CONO,EPPA_DIVI)  \n"
                            + " VALUES ('" + EPRH_NO + "','" + EPPA_DATE.replace("-", "") + "','" + EPPA_DUEDT.replace("-", "") + " ',\n"
                            + "'" + EPPA_SUNO.trim().replace("'", "") + "','" + EPPA_COCE.trim().replace("'", "") + "','" + EPPA_PAMT + " ',\n"
                            + "'" + EPPA_PARM.trim().replace("'", "") + "','" + EPPA_REQBY + "','" + EPPA_APPBY + " ',\n"
                            + "null,'" + EPPA_ADVREF.trim().replace("'", "") + "','" + EPPA_ADVAMT + "',\n"
                            + "'" + EPPA_REF1 + "','" + EPPA_REF2 + "','" + EPPA_REF3 + " ',\n"
                            + "'" + EPPA_STAT + "'," + cono + "," + divi + ")";
                    stmt2.execute(StrInsertHeader);
                } else {
                    Process = " Has been Updated";
//                    String query = "DELETE FROM BRLDTA0100.PAYMENTLINE WHERE EPPA_NO=" + EPPA_NO;
//
//                    System.out.println("Get_PUR_SRNC\n" + query);
//                    stmt.execute(query);
                    //GET 3 VALUES

//                    Ordernum = EPRH_NO.trim() + " Has Been Updated";
                    StrInsertHeader = "UPDATE " + dbname + ".PAYMENTHEAD\n"
                            + " SET EPPA_NO='" + EPPA_NO + "',EPPA_DATE='" + EPPA_DATE.replace("-", "") + "',EPPA_DUEDT='" + EPPA_DUEDT.replace("-", "") + "', \n"
                            + "EPRA_SUNO='" + EPPA_SUNO + "',EPPA_COCE='" + EPPA_COCE + "',EPPA_PAMT='" + EPPA_PAMT + " ', \n"
                            + "EPRA_PARM='" + EPPA_PARM.trim().replace("'", "") + "',EPRA_REQBY='" + EPPA_REQBY + "',EPRA_APPBY='" + EPPA_APPBY + "', \n"
                            + "EPRA_ADVREF='" + EPPA_ADVREF + "'\n"
                            + ",EPRA_ADVAMT='" + EPPA_ADVAMT + "', \n"
                            + "EPRA_REF1='" + EPPA_REF1 + "'\n"
                            + ",EPRA_REF2='" + EPPA_REF2 + "'\n"
                            + ",EPRA_REF3='" + EPPA_REF3 + " ', \n"
                            + "EPRA_STAT='" + EPPA_STAT + "'\n"
                            + " WHERE EPPA_NO=" + EPPA_NO + "\n"
                            + "AND EPPA_CONO =" + cono + "\n"
                            + "AND EPPA_DIVI = " + divi;
                    EPRH_NO = EPPA_NO;
                    stmt2.execute(StrInsertHeader);
                }
                Map<String, Object> mMap2 = new HashMap<>();
                mMap2.put("Result", EPRH_NO + Process);
                mMap2.put("orderno", EPRH_NO);
                mJSonArr2.put(mMap2);

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

        return mJSonArr2;

    }

    public static JSONArray InsertPrvForm(
            String EPPA_NO, String EPRA_PHNO, String app, String cono, String divi
    ) throws Exception {
        String[] itemalldats = EPRA_PHNO.split(":");
        String itemcode = itemalldats[0].trim();

        JSONArray mJSonArr = new JSONArray();
        JSONArray mJSonArr2 = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String StrInsertHeader = null;

        try {
            if (conn != null) {
                String query2 = null;

                //GET 3 VALUES  
                if (app.equals("GRN")) {
                    query2 = " SELECT  SUBSTR(H.ICSUDO,1,15) AS GRNP_INVC,H.ICTRDT AS GRNP_GRND,H.ICPUNO AS GRNP_PO  \n"
                            + " ,H.IBCOCE AS GRNP_COST,H.AMT AS GRNP_AMTB   \n"
                            + " ,H.VAT AS GRNP_VATT,H.AMT+H.VAT AS GRNP_AMTT\n"
                            + " ,H.IASUNO AS EPRA_SUNO ,H.IBCOCE AS  EPPA_COCE\n"
                            + " FROM BRLDTA0100.SUM_GRN01 AS H \n"
                            + " WHERE H.GRN =" + itemcode + "\n"
                            + " AND IACONO = '" + cono + "'";
//                            + " and grn not in (select grnp_grn from BRLDTA0100.PAYMENTLINEGRN where grnp_no <> " + EPPA_NO + ")";
                } else {
                    query2 = "select eprh_trdt,eprh_grn,eprh_invsu,eprh_invdt,eprh_total,eprh_disc\n"
                            + ",eprh_vtamt,eprh_total-eprh_disc+eprh_vtamt as totalAll,eprh_chkdt\n"
                            + ",eprh_phno,EPRH_PURPOS as EPRH_DESCRIPTION\n"
                            + "FROM " + dbname + ".SERVICEHEAD\n"
                            + "WHERE eprh_stat = '50' AND\n"
                            + " eprh_phno='" + itemcode + "'\n"
                            + "AND EPRH_CONO =" + cono + "\n"
                            + "AND EPRH_DIVI =" + divi;
                }
                Statement stmt2 = conn.createStatement();
                ResultSet mRes2 = stmt2.executeQuery(query2);
                if (app.equals("GRN")) {
                    double amount = 0;
                    double totalamount = 0;
                    String Rerndate = null;
                    String pono = null;
                    String Rinvno = null;
                    String Rdescription = null;
                    double Rdiscount = 0;
                    double Charge = 0;
                    double Vat = 0;

                    String[] ValueOfGrn = new String[13];
                    String grnp_invc = null;
                    int grnp_grnd = 0;
                    String grnp_po = null;
                    String grnp_cost = null;
                    double grnp_amtb = 0;
                    double grnp_vatt = 0;
                    double grnp_amtt = 0;
                    String epra_suno = null;
                    String eppa_coce = null;
                    String Rernno = null;
                    double grnp_disc = 0;
                    double grnp_char = 0;
                    double po_qty = 0;
                    double grn_qty = 0;
                    String grn = null;
                    String grnp_desc = null;
                    while (mRes2.next()) {
                        grnp_invc = mRes2.getString("grnp_invc").trim();
                        grnp_grnd = mRes2.getInt("grnp_grnd");
                        grnp_po = mRes2.getString("grnp_po").trim();
                        grnp_cost = mRes2.getString("GRNP_COST");
                        grnp_amtb = mRes2.getDouble("grnp_amtb");
                        grnp_vatt = mRes2.getDouble("grnp_vatt");
                        grnp_amtt = mRes2.getDouble("grnp_amtt");
                        epra_suno = mRes2.getString("epra_suno");
                        eppa_coce = mRes2.getString("eppa_coce");
//                        String value10 = mRes2.getString("totalAll");
//                        if (value10 != null) {value10 = value10.trim();}

                        String Sql2 = " select sum(iaodam) as grnp_disc ";
                        Sql2 += " FROM " + dbM3Name + ".mphead";
                        Sql2 += " where iapuno = '" + grnp_po + "' AND iacono='" + cono + "' and iadivi='" + divi + "'   ";
                        Statement sta2 = conn.createStatement();
                        ResultSet rs2 = sta2.executeQuery(Sql2);
                        while (rs2.next()) {
                            grnp_disc = rs2.getDouble("grnp_disc");
                        }

                        String Sql3 = " select sum(round(ivceva,0)) as grnp_char ";
                        Sql3 += " FROM " + dbM3Name + ".mpoexp";
                        Sql3 += " where ivpuno  = '" + grnp_po + "' AND ivcono ='" + cono + "'   ";
                        Sql3 += " and   substr(ivceid,1,2) = 'IN'   and   ivpnli > 0";
                        Statement sta = conn.createStatement();
                        ResultSet rs3 = sta.executeQuery(Sql3);
                        while (rs3.next()) {
                            grnp_char = rs3.getDouble("grnp_char");
                        }

                        String Sql4 = " select sum(iborqa) as po_qty ";
                        Sql4 += " FROM " + dbM3Name + ".mpline";
                        Sql4 += " where ibpuno   = '" + grnp_po + "' AND ibcono  ='" + cono + "'   ";
                        sta = conn.createStatement();
                        ResultSet rs4 = sta.executeQuery(Sql4);
                        while (rs4.next()) {
                            po_qty = rs4.getDouble("po_qty");
                        }

                        String Sql5 = " SELECT ICCONO, GRN, GRN_QTY\n"
                                + "FROM BRLDTA0100.SUM_GRNQTY01\n"
                                + " where grn='" + grn + "'\n"
                                + " AND ICCONO = " + cono;
                        sta = conn.createStatement();
                        ResultSet rs5 = sta.executeQuery(Sql5);
                        while (rs5.next()) {
                            grn_qty = rs5.getDouble("grn_qty");
                        }

                        if (grnp_disc > 0) {
                            grnp_disc = (grnp_disc / po_qty) * grn_qty;
                        }

                        if (grnp_char > 0) {
                            grnp_char = (grnp_char / po_qty) * grn_qty;
                        }

                        double vat_r = 0;
                        if (grnp_disc + grnp_char > 0) {
                            vat_r = grnp_vatt / grnp_amtb;
                            grnp_amtb = grnp_amtb - grnp_disc + grnp_char; // round
                            grnp_vatt = grnp_amtb * vat_r;
                            grnp_amtt = grnp_amtb + grnp_vatt;
                        }

//                        grn = itemcode + 1;
                        String Sql6 = "SELECT MIN(IBPITT) as grnp_desc";
                        Sql6 += " FROM " + dbM3Name + ".mpline," + dbM3Name + ".mplind";
                        Sql6 += " where ibpuno = '" + grnp_po + "'   and icpnli = ibpnli  and icpnls = ibpnls    and icpuno = ibpuno "
                                + "    and iccono = ibcono   and iccono = '" + cono + "'    and icrepn like '" + itemcode + "%' ";

                        sta = conn.createStatement();
                        try {
                            ResultSet rs6 = sta.executeQuery(Sql6);
                            while (rs6.next()) {
                                grnp_desc = rs6.getString("grnp_desc").trim();
                            }
                        } catch (Exception e) {
                            grnp_desc = "";
                        }

                        if (grnp_disc + grnp_char > 0) {
                            grnp_amtb = 0;
                            grnp_vatt = 0;
                            grnp_amtt = 0;
                        }
//                        Map<String, Object> mMap = new HashMap<>();

                        ValueOfGrn[0] = grnp_invc;
                        ValueOfGrn[1] = GetDateFormatSetShowString(GetDecmalTodate(grnp_grnd));
                        ValueOfGrn[2] = grnp_po;
                        ValueOfGrn[3] = grnp_cost;
                        ValueOfGrn[4] = String.valueOf(Double2digitReturn(grnp_amtb));
                        ValueOfGrn[5] = String.valueOf(Double2digitReturn(grnp_vatt));
                        ValueOfGrn[6] = String.valueOf(Double2digitReturn(grnp_amtt));
                        ValueOfGrn[7] = epra_suno;
                        ValueOfGrn[8] = eppa_coce;
                        ValueOfGrn[9] = String.valueOf(grnp_disc);
                        ValueOfGrn[10] = String.valueOf(grnp_char);
                        ValueOfGrn[11] = grnp_desc;
                        ValueOfGrn[12] = itemcode;
                        double value4 = 0;
                        boolean typeGRN = false;
//                        if (cono.equalsIgnoreCase("600")) {
                        typeGRN = checkTypeGRN(itemcode, cono);
//                        }
                        if (typeGRN == true) {
                            System.err.println("OFFSET");
                            String suno = itemcode;
                            ResultSet off_set = GetDetail_OFFSET(cono, ValueOfGrn[2]);

                            if (off_set.next()) {
                                Double O_AMOUNT_1 = Double2digitReturn(Double.parseDouble(off_set.getString("O_AMOUNT")));
                                Double O_AMOUNT_2 = Double2digitReturn(Double.parseDouble(off_set.getString("O_AMOUNT")));

                                if (O_AMOUNT_1 == 0.00) {
                                    O_AMOUNT_1 = Double2digitReturn(Double.parseDouble(ValueOfGrn[4]));
                                    O_AMOUNT_2 = Double2digitReturn(Double.parseDouble(ValueOfGrn[6]));
                                }
                                try {
                                    amount = O_AMOUNT_1; //AMOUNT
                                } catch (Exception e) {
                                    amount = 0.00;//AMOUNT
                                }

                                try {
                                    totalamount = O_AMOUNT_2; //TOTAL AMOUNT   
                                } catch (Exception e) {
                                    totalamount = 0.00; //TOTAL AMOUNT
                                }
                            } else {
                                try {
                                    amount = Double2digitReturn(Double.parseDouble(ValueOfGrn[4]));//AMOUNT
                                } catch (Exception e) {
                                    amount = 0.00; //AMOUNT
                                }

                                try {
                                    totalamount = Double2digitReturn(Double.parseDouble(ValueOfGrn[6])); //TOTAL AMOUNT   
                                } catch (Exception e) {
                                    totalamount = 0.00; //TOTAL AMOUNT
                                }
                            }

                            Rerndate = ValueOfGrn[1]; //GRN DATE
                            pono = ValueOfGrn[2]; //PO NO
                            Rinvno = ValueOfGrn[3];
//                                model.setValueAt(ValueOfGrn[0], i, 4); //INVOICE NO

                            Rdescription = ValueOfGrn[11];

                            //+2
                            try {
                                Rdiscount = Double2digitReturn(Double.parseDouble(ValueOfGrn[9])); //DISCOUNT
                            } catch (Exception e) {
                                Rdiscount = 0.00; //DISCOUNT
                            }
                            try {
                                Charge = Double.parseDouble(ValueOfGrn[10]); //CHARGE 

                            } catch (Exception e) {
                                Charge = 0.00; //CHARGE
                            }

                            try {
                                Vat = Double2digitReturn(Double.parseDouble(ValueOfGrn[5])); //VATT

                            } catch (Exception e) {
                                Vat = 0.00; //VATT
                            }
                        } else {
                            try {
                                amount = Double2digitReturn(Double.parseDouble(ValueOfGrn[4])); //AMOUNT
                            } catch (Exception e) {
                                amount = 0.00; //AMOUNT
                            }

                            try {
                                totalamount = Double2digitReturn(Double.parseDouble(ValueOfGrn[6])); //TOTAL AMOUNT   
                            } catch (Exception e) {
                                totalamount = 0.00; //TOTAL AMOUNT
                            }
                        }

//                            value4 = Double2digitReturn(Double.parseDouble(grnp_cost));
                        Rernno = itemcode;
                        Rerndate = GetDateFormatSetShowString(GetDecmalTodate(grnp_grnd));
                        pono = grnp_po;
                        String cost = grnp_cost;
                        Rinvno = ValueOfGrn[0];
                        Rdescription = ValueOfGrn[11];
                        Rdiscount = Double.parseDouble(ValueOfGrn[9]);
                        Charge = Double.parseDouble(ValueOfGrn[10]);
                        Vat = Double.parseDouble(ValueOfGrn[5]);
                        StrInsertHeader = "INSERT INTO " + dbname + ".PAYMENTLINEGRN(GRNP_CONO,GRNP_DIVI,GRNP_NO,GRNP_GRN\n"
                                + ",GRNP_GRND,GRNP_PO,GRNP_INVC\n"
                                + ",GRNP_COST,GRNP_DESC,GRNP_DISC,GRNP_CHAR,GRNP_AMTB,GRNP_VATT,GRNP_AMTT)\n"
                                + "VALUES(" + cono + "," + divi + ",'" + EPPA_NO + "','" + Rernno + "'\n"
                                + "," + grnp_grnd + ",'" + grnp_po + "','" + grnp_invc + "','" + cost + "','" + grnp_desc + "'," + ValueOfGrn[9] + ",\n"
                                + "'" + grnp_char + " ', " + "'" + amount + "'," + Vat + "," + totalamount + ")";
//                        mJSonArr.put(mMap);
                    }
                } else {
                    StrInsertHeader = "INSERT INTO " + dbname + ".PAYMENTLINE"
                            + "(EPPA_NO, EPRA_PHNO, EPRA_STAT,EPPA_CONO,EPPA_DIVI) "
                            + "VALUES ('" + EPPA_NO + "','" + itemcode + "','00'," + cono + "," + divi + ")";
                }

                stmt2.execute(StrInsertHeader);

                //GET 3 VALUES
//                    Ordernum = EPRH_NO.trim() + " Has Been Updated";
                Map<String, Object> mMap2 = new HashMap<>();
                mMap2.put("Result", "Ok");
                mJSonArr2.put(mMap2);

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

        return mJSonArr2;

    }

    public static JSONArray insertNewItemService(String EPPANO,
            String txtitemcode,
            String txtDescription,
            String txtqty,
            String txtupprice,
            String txtvat,
            String totalbvat,
            String vatamount,
            String totalamount,
            String cono,
            String divi) throws Exception {

        String[] itemalldats = txtitemcode.split(":");
        String itemcode = itemalldats[0];
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
                String StrGetMaxLine = "SELECT MAX(EPRL_PNLI) + 1 AS LINENUM\n"
                        + "FROM " + dbname + ".SERVICELINE \n"
                        + "WHERE  EPRL_PHNO='" + EPPANO + "'\n"
                        + " AND  EPRL_CONO =" + cono + " \n"
                        + "AND EPRL_DIVI =" + divi;
                ResultSet mRes = stmt.executeQuery(StrGetMaxLine);
                while (mRes.next()) {
                    LineNumbers = mRes.getString("LINENUM");
                }
                try {
                    int lineconvert = Integer.parseInt(LineNumbers);
                } catch (Exception e) {
                    LineNumbers = "1";
                }
                StrInsertDetail = "INSERT INTO " + dbname + ".SERVICELINE"
                        + "(EPRL_PHNO,EPRL_PNLI,EPRL_ITNO, "
                        + "EPRL_DESC,EPRL_QTY,EPRL_UPRICE,"
                        + "EPRL_VTCD,EPRL_AMT,"
                        + "EPRL_STAT,EPRL_VAT,EPRL_CONO,EPRL_DIVI)";

                StrInsertDetail += "VALUES ('" + EPPANO + "','" + LineNumbers + "','" + itemcode + "', "
                        + "'" + txtDescription.replace("'", " ") + "','" + txtqty + "','" + txtupprice + " ', "
                        + "'" + txtvat + "','" + totalamount + " ','00','" + vatamount + "'," + cono + "," + divi
                        + ") ";
                stmt2.execute(StrInsertDetail);

                Map<String, Object> mMap2 = new HashMap<>();
                mMap2.put("Result", "Ok");
                mJSonArr.put(mMap2);
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

    public static JSONArray SaveSrnForm(
            String EPRH_COCE, String EPRH_PURPOS, String EPRH_RQSDT,
            String EPRH_RQRDT, String EPRH_APTNO, String EPRH_SUNO,
            String EPRH_BU, String EPRH_REM, String EPRH_REQBY,
            String EPRH_DISC, String EPRH_VTCD,
            String EPRH_VTAMT, String EPRH_FAC, String EPRH_WHS,
            String EPRH_INVSU, String EPRH_INVDT, String EPRH_STAT,
            String ServiceRequestNo, String EPRH_TOTAL, String currentdate, String app,
            String cono, String divi
    ) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        JSONArray mJSonArr2 = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String digit2year = currentdate.substring(2, 4);
        Integer digit2year_EPRH_PHNO_int = Integer.parseInt(digit2year) + 43;
        String digit2year_EPRH_PHNO = Integer.toString(digit2year_EPRH_PHNO_int);
        String EPRH_GN = null;
        String EPRH_PO = null;
        String EPRH_NO = null;
        String StrUpdateSrnNo;
        String StrInsertHeader;
        String Ordernum = "Ordernum";
        String Process = "";
//        EPRH_COCE = get_SemiColonValue0(EPRH_COCE);
//        EPRH_SUNO = get_SemiColonValue0(EPRH_SUNO);
        EPRH_RQSDT = GetDateFormatSet(EPRH_RQSDT);
        EPRH_RQRDT = GetDateFormatSet(EPRH_RQRDT);
        EPRH_INVDT = GetDateFormatSet(EPRH_INVDT);
        String appnumber = null;
        if ("ERN".equals(app)) {
            appnumber = "1";
        }
        if ("NEN".equals(app)) {
            appnumber = "2";
        }
        try {
            if (conn != null) {
                //GET 3 VALUES
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();
//                Statement stmt3 = conn.createStatement();

                if (ServiceRequestNo == "" && ServiceRequestNo.isEmpty()) {
                    Process = " Has been Created";
                    String query = "SELECT CASE WHEN  CAST(MAX(SUBSTRING(EPRH_PHNO,4,5)) AS DECIMAL(10,0)) > 0 \n"
                            + "THEN '" + digit2year + "'||'" + appnumber + "'|| MAX(SUBSTRING(EPRH_PHNO,4,5))+1 \n"
                            + "ELSE '" + digit2year + "'||'" + appnumber + "'||'00001' END AS EPRH_PHNO,\n"
                            + "CASE WHEN  CAST(MAX(SUBSTRING(EPRH_PO,4,5)) AS DECIMAL(10,0)) > 0 \n"
                            + "THEN '" + digit2year + "'||'1'||MAX(SUBSTRING(EPRH_PO,4,5))+1 \n"
                            + " ELSE '" + digit2year + "'||'1'||'00001' END AS EPRH_PO,\n"
                            + "CASE WHEN  CAST(MAX(SUBSTRING(EPRH_GRN,4,5)) AS DECIMAL(10,0)) > 0 \n"
                            + "THEN '" + digit2year + "'||'2'||MAX(SUBSTRING(EPRH_GRN,4,5))+1 \n"
                            + "ELSE '" + digit2year + "'||'2'||'00001' END AS EPRH_GRN\n"
                            + "FROM  " + dbname + ".SERVICEHEAD\n"
                            + "WHERE SUBSTRING(EPRH_PHNO,1,2) =" + digit2year + "\n"
                            + "AND EPRH_CONO  = " + cono + "	\n"
                            + "AND EPRH_DIVI = " + divi + "\n"
                            + "AND SUBSTRING(EPRH_PHNO,3,1) =" + appnumber;

                    System.out.println("Get_PUR_SRNC\n" + query);
                    ResultSet mRes = stmt.executeQuery(query);
                    //GET 3 VALUES
                    while (mRes.next()) {
                        Map<String, Object> mMap = new HashMap<>();
                        EPRH_NO = mRes.getString("EPRH_PHNO");
                        EPRH_PO = mRes.getString("EPRH_PO");
                        EPRH_GN = mRes.getString("EPRH_GRN");
                        mJSonArr.put(mMap);
                    }

                    StrInsertHeader = "INSERT INTO " + dbname + ".SERVICEHEAD\n"
                            + " (EPRH_PHNO,EPRH_COCE,EPRH_PURPOS\n"
                            + ",EPRH_TRDT,EPRH_RQSDT,EPRH_RQRDT,\n"
                            + "EPRH_APTNO,EPRH_SUNO,EPRH_OWAPP\n"
                            + ",EPRH_OWAPDT,EPRH_BU,EPRH_REM1,\n"
                            + "  EPRH_REQBY,EPRH_CHKBY,EPRH_CHKDT\n"
                            + ",EPRH_TOTAL,EPRH_DISC,EPRH_VTCD,\n"
                            + "EPRH_VTAMT,EPRH_PO,EPRH_GRN\n"
                            + ",EPRH_FAC,EPRH_WHS,EPRH_INVSU,\n "
                            + "EPRH_INVDT,EPRH_STAT,EPRH_CONO,EPRH_DIVI,EPRH_TYPE)\n"
                            + "VALUES ('" + EPRH_NO.trim() + "'\n"
                            + ",'" + EPRH_COCE.trim() + "'\n"
                            + ",'" + EPRH_PURPOS.trim().replace("'", "") + "',\n"
                            + "'" + GetDateDecmalCurrenttime() + "'\n"
                            + ",'" + EPRH_RQSDT + "','" + EPRH_RQRDT + "',\n"
                            + "'" + EPRH_APTNO.trim().replace("'", "") + "'\n"
                            + ",'" + EPRH_SUNO.trim().replace("'", "") + "'\n"
                            + ",'" + EPRH_REQBY.trim().replace("'", "") + "',\n"
                            + "'" + GetDateDecmalCurrenttime() + "'\n"
                            + ",'" + EPRH_BU.trim() + "'\n"
                            + ",'" + EPRH_REM.trim().replace("'", "") + "',\n"
                            + "'" + EPRH_REQBY.trim() + "'\n"
                            + ",'" + EPRH_REQBY.trim() + "'\n"
                            + ",'" + GetDateDecmalCurrenttime() + " ',\n"
                            + "'" + EPRH_TOTAL + "'\n"
                            + ",'" + EPRH_DISC + "'\n"
                            + ",'" + EPRH_VTCD + " ',\n"
                            + "'" + EPRH_VTAMT + "'\n"
                            + ",'" + EPRH_PO.trim() + "'\n"
                            + ",'" + EPRH_GN.trim() + "' ,\n"
                            + "'" + EPRH_FAC.trim() + "'\n"
                            + ",'" + EPRH_WHS.trim() + "'\n"
                            + ",'" + EPRH_INVSU.trim().replace("'", "") + "',\n"
                            + "'" + EPRH_INVDT + "'\n"
                            + ",'" + EPRH_STAT.trim() + "'\n"
                            + "," + cono + "," + divi + "\n"
                            + ",'" + app + "')";
                    stmt2.execute(StrInsertHeader);
                } else {
                    Process = " Has been Updated";
                    String query = "select EPRH_PHNO,EPRH_PO,EPRH_GRN"
                            + " FROM " + dbname + ".SERVICEHEAD"
                            + " WHERE EPRH_PHNO='" + ServiceRequestNo + "'";

                    System.out.println("Get_PUR_SRNC\n" + query);
                    ResultSet mRes = stmt.executeQuery(query);
                    //GET 3 VALUES
                    while (mRes.next()) {
                        Map<String, Object> mMap = new HashMap<>();
                        EPRH_NO = mRes.getString("EPRH_PHNO");
                        EPRH_PO = mRes.getString("EPRH_PO");
                        EPRH_GN = mRes.getString("EPRH_GRN");
                        mJSonArr.put(mMap);
                        Ordernum = EPRH_NO + " Has Been Updated";
                    }
                    Ordernum = EPRH_NO.trim() + " Has Been Updated";
                    StrInsertHeader = "UPDATE " + dbname + ".SERVICEHEAD\n"
                            + "SET EPRH_PHNO='" + EPRH_NO.trim() + "'\n"
                            + ",EPRH_COCE='" + EPRH_COCE.trim() + "'\n"
                            + ",EPRH_PURPOS='" + EPRH_PURPOS.trim().replace("'", "") + " '\n"
                            + ",EPRH_TRDT='" + GetDateDecmalCurrenttime() + "'\n"
                            + ",EPRH_RQSDT='" + EPRH_RQSDT + "'\n"
                            + ",EPRH_RQRDT='" + EPRH_RQRDT + "'\n"
                            + ",EPRH_APTNO='" + EPRH_APTNO.trim().replace("'", "") + "'\n"
                            + ",EPRH_SUNO='" + EPRH_SUNO.trim() + "'\n"
                            + ",EPRH_OWAPP='" + EPRH_REQBY.trim().replace("'", "") + "'\n"
                            + ",EPRH_OWAPDT='" + GetDateDecmalCurrenttime() + "'\n"
                            + ",EPRH_BU='" + EPRH_BU.trim() + "'\n"
                            + ",EPRH_REM1='" + EPRH_REM.trim().replace("'", "") + " ' \n"
                            + ",EPRH_REQBY='" + EPRH_REQBY.trim() + "'\n"
                            + ",EPRH_CHKBY='" + EPRH_REQBY.trim() + "'\n"
                            + ",EPRH_CHKDT='" + GetDateDecmalCurrenttime() + "'"
                            + ",EPRH_TOTAL='" + EPRH_TOTAL + "'\n"
                            + ",EPRH_DISC='" + EPRH_DISC + "'\n"
                            + ",EPRH_VTCD='" + EPRH_VTCD + " '\n"
                            + ",EPRH_VTAMT='" + EPRH_VTAMT + "'\n"
                            + ",EPRH_PO='" + EPRH_PO.trim() + "'\n"
                            + ",EPRH_GRN='" + EPRH_GN.trim() + " '\n"
                            + ",EPRH_FAC='" + EPRH_FAC + "'\n"
                            + ",EPRH_WHS='" + EPRH_WHS + "'\n"
                            + ",EPRH_INVSU='" + EPRH_INVSU.trim().replace("'", "") + " '\n"
                            + ",EPRH_INVDT='" + EPRH_INVDT + "'\n"
                            + ",EPRH_STAT='" + EPRH_STAT + "'"
                            + "WHERE EPRH_PHNO = " + EPRH_NO.trim() + "\n"
                            + "AND EPRH_CONO =" + cono + "\n"
                            + "AND EPRH_DIVI =" + divi;
                    System.out.println(StrInsertHeader);
                    stmt2.execute(StrInsertHeader);
                }
                Map<String, Object> mMap2 = new HashMap<>();
                mMap2.put("Result", EPRH_NO + Process);
                mMap2.put("orderno", EPRH_NO);
                mJSonArr2.put(mMap2);
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

        return mJSonArr2;

    }

}
