/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.data;

import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jettison.json.JSONArray;
import static com.br.utility.Constant.dbname;

/**
 *
 * @author Wattana
 */
public class Update {

    public static String changeSRNstatus(String cono, String divi, String EPRH_PHNO, String status) throws Exception {
        String Ordernum = "finished";
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String StrUpdateSrnNo;

        try {
            if (conn != null) {
                Statement stmt3 = conn.createStatement();
                //Update new number
                StrUpdateSrnNo = "UPDATE " + dbname + ".SERVICEHEAD \n"
                        + "SET EPRH_STAT='" + status + "'\n"
                        + "WHERE EPRH_PHNO=" + EPRH_PHNO + "\n"
                        + "AND EPRH_CONO = '" + cono + "'\n"
                        + "AND EPRH_DIVI = '" + divi + "'";
                stmt3.execute(StrUpdateSrnNo);

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

        return Ordernum;

    }

    public static String changePRVstatus(String cono, String divi, String EPPA_NO, String status
    ) throws Exception {
        String Ordernum = "finished";
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String StrUpdateSrnNo;

        try {
            if (conn != null) {
                Statement stmt3 = conn.createStatement();
                //Update new number
                StrUpdateSrnNo = "UPDATE " + dbname + ".PAYMENTHEAD\n"
                        + "SET EPRA_STAT='" + status + "'\n"
                        + "WHERE EPPA_NO=" + EPPA_NO + "\n"
                        + "AND EPPA_CONO = '" + cono + "'\n"
                        + "AND EPPA_DIVI = '" + divi + "'";
                stmt3.execute(StrUpdateSrnNo);

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

        return Ordernum;

    }

    public static String updatePRV(String cono, String divi,
            String ordernum, String grnno, String Invno, String Desc
    ) throws Exception {
        String Ordernum = "finished";
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String StrPRV;

        try {
            if (conn != null) {
                Statement stmt3 = conn.createStatement();
                //Update new number
                StrPRV = "UPDATE " + dbname + ".PAYMENTLINEGRN\n"
                        + "SET GRNP_INVC ='" + Invno + "' \n"
                        + ",GRNP_DESC='" + Desc + "'\n"
                        + "WHERE GRNP_NO =" + ordernum + "\n "
                        + "AND GRNP_GRN =" + grnno + "\n"
                        + "AND GRNP_CONO = '" + cono + "' AND \n"
                        + " GRNP_DIVI = '" + divi + "'";
                stmt3.execute(StrPRV);

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

        return Ordernum;

    }

    public static String UpdateSRNumbers(String cono, String divi,
            String ordernum, String total, String discount, String vat, String vatamt
    ) throws Exception {
        String Ordernum = "finished";
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String query;

        try {
            if (conn != null) {
                Statement stmt3 = conn.createStatement();
                //Update new number
                query = "UPDATE " + dbname + ".SERVICEHEAD\n"
                        + "SET EPRH_TOTAL =" + total + "\n"
                        + ",EPRH_DISC =" + discount + "\n"
                        + ",EPRH_VTCD =" + vat + "\n"
                        + ",EPRH_VTAMT =" + vatamt + "\n"
                        + "WHERE EPRH_CONO = " + cono + "\n"
                        + "AND EPRH_DIVI = " + divi + "\n"
                        + "AND EPRH_PHNO =" + ordernum;
                stmt3.execute(query);

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

        return Ordernum;

    }

    public static JSONArray rollbackservices(String servicesno, String cono, String divi, String status) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        Statement stmt1 = conn.createStatement();
        String statusno = "20";
        if (status.equals("Normal")) {
            statusno = "20";
        } else if (status.equals("Submitted")) {
            statusno = "50";
        } else if (status.equals("Canceled")) {
            statusno = "99";
        }
        try {
            if (conn != null) {
//                RHCONO, RHDIVI, RHWARE, RHPERI
                Statement stmt = conn.createStatement();
                String StrGetMaxLine = "UPDATE  " + dbname + ".SERVICEHEAD\n"
                        + "SET EPRH_STAT = '" + statusno + "'\n"
                        + "WHERE EPRH_PHNO = '" + servicesno + "'\n"
                        + "AND EPRH_CONO = '" + cono + "'\n"
                        + "AND EPRH_DIVI = '" + divi + "'";
                stmt1.execute(StrGetMaxLine);

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

        return mJSonArr;

    }

    public static JSONArray rollbackpayment(String paymentno, String cono, String divi, String status) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        Statement stmt1 = conn.createStatement();
        String statusno = "00";
        if (status.equals("Normal")) {
            statusno = "00";
        } else if (status.equals("Submitted")) {
            statusno = "10";
        } else if (status.equals("Canceled")) {
            statusno = "99";
        }
        try {
            if (conn != null) {
//                RHCONO, RHDIVI, RHWARE, RHPERI
                Statement stmt = conn.createStatement();
                String StrGetMaxLine = "UPDATE  " + dbname + ".PAYMENTHEAD\n"
                        + "SET EPRA_STAT = '" + statusno + "'\n"
                        + "WHERE EPPA_NO = '" + paymentno + "'\n"
                        + "AND EPPA_CONO = '" + cono + "'\n"
                        + "AND EPPA_DIVI = '" + divi + "'";
                stmt1.execute(StrGetMaxLine);

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

        return mJSonArr;

    }

}
