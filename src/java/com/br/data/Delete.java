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
public class Delete {

    public static void Facility(String cono, String divi, String code, String desc, String name, String type, String muun, String rate, String ref1, String ref2) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.A1CASU\n"
                        + "WHERE A1CONO = '" + cono + "'\n"
                        + "AND A1DIVI = '" + divi + "'\n"
                        + "AND A1CODE = '" + code + "'";
                stmt.execute(query);

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

    }

    public static JSONArray DeletePrvForm(
            String EPPA_NO, String EPRA_PHNO, String app, String cono, String divi
    ) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        JSONArray mJSonArr2 = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                String StrDeleteHeader = null;
                //GET 3 VALUES
                Statement stmt2 = conn.createStatement();
                if (app.equals("GRN")) {
                    StrDeleteHeader = "DELETE FROM " + dbname + ".PAYMENTLINEGRN \n"
                            + "WHERE GRNP_NO = '" + EPPA_NO + "' \n"
                            + "AND GRNP_GRN = '" + EPRA_PHNO + "' \n"
                            + "AND GRNP_CONO = '" + cono + "' \n"
                            + "AND GRNP_DIVI = '" + divi + "'";
                } else {
                    StrDeleteHeader = "DELETE FROM " + dbname + ".PAYMENTLINE \n "
                            + "WHERE EPPA_NO = '" + EPPA_NO + "'\n "
                            + "AND EPRA_PHNO = '" + EPRA_PHNO + "'\n "
                            + "AND EPPA_CONO =" + cono + "\n"
                            + "AND EPPA_divi =" + divi;
                }
                System.out.println(StrDeleteHeader);
                stmt2.execute(StrDeleteHeader);

                //GET 3 VALUES
//                    Ordernum = EPRH_NO.trim() + " Has Been Updated";
                Map<String, Object> mMap2 = new HashMap<>();
                mMap2.put("Result", "ball");
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

    public static void Type(String cono, String divi, String code, String desc, String ref) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.A1TYPE\n"
                        + "WHERE ATCONO = '" + cono + "'\n"
                        + "AND ATDIVI = '" + divi + "'\n"
                        + "AND ATTYPE = '" + code + "'";
                stmt.execute(query);

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

    }

    public static void Level(String cono, String divi, String code, String struct, String level, String desc, String ref1, String ref2) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.A2CASL\n"
                        + "WHERE A2CONO = '" + cono + "'\n"
                        + "AND A2DIVI = '" + divi + "'\n"
                        + "AND A2CODE = '" + code + "'\n"
                        + "AND A2STID = '" + struct + "'";
                stmt.execute(query);

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

    }

    public static void Period(String cono, String divi, String code, String year, String month, String desc) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.APERIO\n"
                        + "WHERE PECONO = '" + cono + "'\n"
                        + "AND PEDIVI = '" + divi + "'\n"
                        + "AND PECODE = '" + code + "'";
                stmt.execute(query);

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

    }

    public static void WorkCenter(String cono, String divi, String code, String struct, String level, String itm, String str, String desc, String mety, String uehr, String mela, String per,
            String acc, String aac1, String aac2, String boi, String ref1, String ref2, String ref3) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.A3CAWC\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3STID = '" + struct + "'\n"
                        + "AND A3AITM = '" + itm + "'";
                stmt.execute(query);

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

    }

    public static void LevelWorkCenter(String cono, String divi, String code, String struct, String level, String itm, String str, String desc, String mety, String uehr, String mela, String per,
            String acc, String aac1, String aac2, String boi, String ref1, String ref2, String ref3) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "UPDATE BRLDTA0100.A3CAWC\n"
                        + "SET A3STID = 'null'\n"
                        + "WHERE A3CONO = '" + cono + "'\n"
                        + "AND A3DIVI = '" + divi + "'\n"
                        + "AND A3CODE = '" + code + "'\n"
                        + "AND A3AITM = '" + itm + "'";
                stmt.execute(query);

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

    }

    public static void Bills(String cono, String divi, String code, String period, String meterstart, String meterend, String qty, String amount, String vat, String total, String totalamt, String type) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.E1ALLO\n"
                        + "WHERE E1CONO = '" + cono + "'\n"
                        + "AND E1DIVI = '" + divi + "'\n"
                        + "AND E1CODE = '" + code + "'\n"
                        + "AND E1PECO = '" + period + "'\n"
                        + "AND E1TYPE = '" + type + "'";
                stmt.execute("Bills\n" + query);

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

    }

    public static void BillsVariance(String cono, String divi, String code, String period, String type) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.E1ALLO\n"
                        + "WHERE E1CONO = '" + cono + "'\n"
                        + "AND E1DIVI = '" + divi + "'\n"
                        + "AND E1CODE = '" + code + "'\n"
                        + "AND E1PECO = '" + period + "'\n"
                        + "AND E1TYPE = '" + type + "'";
                stmt.execute(query);

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

    }

    public static void Allocation(String cono, String divi, String code, String struct, String level, String itm, String period, String meterstart, String meterend, String rate, String hour, String qty, String pers, String amt, String total, String status, String type) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.E2ALLO\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        + "AND E2TYPE = '" + type + "'";
                stmt.execute(query);

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

    }

    public static void AllocationVariance(String cono, String divi, String code, String period, String type) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "DELETE FROM BRLDTA0100.E2ALLO\n"
                        + "WHERE E2CONO = '" + cono + "'\n"
                        + "AND E2DIVI = '" + divi + "'\n"
                        + "AND E2CODE = '" + code + "'\n"
                        + "AND E2PECO = '" + period + "'\n"
                        + "AND E2TYPE = '" + type + "'";
                stmt.execute(query);

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

    }

    public static String deleteSRNLine(String item, String ordernum, String cono, String divi, String app, String number) throws Exception {

        String respond = "";
        String[] itemsplited = item.split(":");
        String itemnew = itemsplited[0];
        String check = "0";
        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        Connection conn2 = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

//                if (month.equals("02")) {
//                    check = "1";
//                    respond = "Please do not delete starting month";
//                }
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn2.createStatement();
                String query = "DELETE FROM " + dbname + ".SERVICELINE \n"
                        + "WHERE EPRL_ITNO = '" + itemnew + "' \n"
                        + "AND EPRL_CONO =" + cono + "\n"
                        + "AND EPRL_DIVI =" + divi + "\n"
                        + "AND EPRL_PHNO =" + ordernum + "\n"
                        + "AND EPRL_PNLI =" + number;
                String query2 = "UPDATE " + dbname + ".SERVICELINE\n"
                        + "SET EPRL_PNLI = EPRL_PNLI - 1\n"
                        + "WHERE EPRL_PHNO = " + ordernum + " AND EPRL_PNLI >" + number;
                System.out.println(query);
                stmt.execute(query);
                stmt2.execute(query2);

                respond = "Delete succesfully";
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

        return respond;

    }

}
