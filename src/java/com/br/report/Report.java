/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.report;

import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import static com.br.utility.Constant.dbname;
import static com.br.utility.Constant.dbM3Name;

/**
 *
 * @author Wattana
 */
public class Report extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");

        String report = request.getParameter("report");
        System.out.println("report: " + report);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession session = request.getSession(true);
        String cono2 = session.getAttribute("cono").toString();
        String divi2 = session.getAttribute("divi").toString();
        String comp2 = session.getAttribute("comp").toString();
        String testornot = "";
        if (dbname.equals("BRLDTABK01")) {
            testornot = "_test";
        }
        Connection conn2 = null;
        Connection conn3 = null;
        Connection conn4 = null;
        Connection conn5 = null;
        switch (report) {
            case "Ernform":

                JasperDesign JPD4;
                try {
                    String SrnNumber = request.getParameter("SrnNumber");
                    conn4 = ConnectDB2.ConnectionDB();
                    Statement sta = conn4.createStatement();
                    String companyname = null;
                    String cono = request.getParameter("cono");
                    String divi = request.getParameter("divi");

                    String path3 = getServletContext().getRealPath("/jaspers/");

                    JPD4 = JRXmlLoader.load(path3 + "RptErnForm" + testornot + ".jrxml");
                    JasperReport jasperReport3 = JasperCompileManager.compileReport(JPD4);

                    conn3 = ConnectDB2.ConnectionDB();

                    Map parameters4 = new HashMap();
                    parameters4.put("ordernum", SrnNumber);
                    parameters4.put("imagesDir", path3);
                    parameters4.put("cono", cono);
                    parameters4.put("divi", divi);
                    JasperPrint jasp4 = JasperFillManager.fillReport(jasperReport3, parameters4, conn4);
                    ServletOutputStream ouputStream4 = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jasp4, ouputStream4);
                    ouputStream4.flush();
                    ouputStream4.close();

                } catch (JRException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }

            case "Nenform":

                JasperDesign JPD6;
                try {
                    String NenNum = request.getParameter("NENNumber");
                    conn4 = ConnectDB2.ConnectionDB();
                    Statement sta = conn4.createStatement();
                    String companyname = null;
                    String cono = request.getParameter("cono");
                    String divi = request.getParameter("divi");
                    String path3 = getServletContext().getRealPath("/jaspers/");

                    JPD6 = JRXmlLoader.load(path3 + "RptNenForm" + testornot + ".jrxml");
                    JasperReport jasperReport3 = JasperCompileManager.compileReport(JPD6);

                    conn3 = ConnectDB2.ConnectionDB();

                    Map parameters4 = new HashMap();
                    parameters4.put("ordernum", NenNum);
                    parameters4.put("imagesDir", path3);
                    parameters4.put("cono", cono);
                    parameters4.put("divi", divi);
                    JasperPrint jasp4 = JasperFillManager.fillReport(jasperReport3, parameters4, conn3);
                    ServletOutputStream ouputStream4 = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jasp4, ouputStream4);
                    ouputStream4.flush();
                    ouputStream4.close();

                } catch (JRException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }
            case "Prvform":

                JasperDesign JPD5;
                try {
                    String PrvNumber = request.getParameter("PrvNumber");
                    conn4 = ConnectDB2.ConnectionDB();
                    Statement sta = conn4.createStatement();
                    String companyname = null;
                    String cono = request.getParameter("cono");
                    String divi = request.getParameter("divi");
                    String path3 = getServletContext().getRealPath("/jaspers/");

                    JPD4 = JRXmlLoader.load(path3 + "RptPrvErnForm" + testornot + ".jrxml");
                    JasperReport jasperReport3 = JasperCompileManager.compileReport(JPD4);
                    File reportFile = new File(getServletContext().getRealPath("jaspers/customer_calender.jasper"));

                    conn3 = ConnectDB2.ConnectionDB();

                    Map parameters4 = new HashMap();
                    parameters4.put("ordernum", PrvNumber);
                    parameters4.put("imagesDir", path3);
                    parameters4.put("cono", cono);
                    parameters4.put("divi", divi);
                    JasperPrint jasp4 = JasperFillManager.fillReport(jasperReport3, parameters4, conn3);
                    ServletOutputStream ouputStream4 = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jasp4, ouputStream4);
                    ouputStream4.flush();
                    ouputStream4.close();

                } catch (JRException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }
            case "PrvNenform":

                JasperDesign JPD7;
                try {
                    String NENNumber = request.getParameter("NENNumber");
                    conn4 = ConnectDB2.ConnectionDB();
                    Statement sta = conn4.createStatement();
                    String companyname = null;
                    String cono = request.getParameter("cono");
                    String divi = request.getParameter("divi");
                    String companynamequery = "SELECT CCROW3 FROM " + dbM3Name + ".CMNDIV\n"
                            + "WHERE CCDIVI != '' AND CCCONO = '" + cono + "' AND CCDIVI = '" + divi + "'";
                    ResultSet mRes = sta.executeQuery(companynamequery);

                    while (mRes.next()) {
                        companyname = mRes.getString(1).trim();

                    }

//                    JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs1);
                    String path3 = getServletContext().getRealPath("/jaspers/");

                    JPD7 = JRXmlLoader.load(path3 + "RptPrvNenForm" + testornot + ".jrxml");
                    JasperReport jasperReport3 = JasperCompileManager.compileReport(JPD7);
                    File reportFile = new File(getServletContext().getRealPath("jaspers/customer_calender.jasper"));

                    conn3 = ConnectDB2.ConnectionDB();

                    Map parameters4 = new HashMap();
                    parameters4.put("imagesDir", path3);
                    parameters4.put("ordernum", NENNumber);
                    parameters4.put("cono", cono);
                    parameters4.put("divi", divi);
                    JasperPrint jasp4 = JasperFillManager.fillReport(jasperReport3, parameters4, conn3);
                    ServletOutputStream ouputStream4 = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jasp4, ouputStream4);
                    ouputStream4.flush();
                    ouputStream4.close();

                } catch (JRException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }
            case "PrvGrnform":

                JasperDesign JPD8;
                try {
                    conn5 = ConnectDB2.ConnectionDB();
                    Statement stmt = conn5.createStatement();
                    String companyname = null;
                    String GrnNumber = request.getParameter("GRNNumber");
                    String cono = request.getParameter("cono");
                    String divi = request.getParameter("divi");
                    String companynamequery = "SELECT CCROW3 FROM " + dbM3Name + ".CMNDIV\n"
                            + "WHERE CCDIVI != '' AND CCCONO = '" + cono + "' AND CCDIVI = '" + divi + "'";
                    ResultSet mRes = stmt.executeQuery(companynamequery);

                    while (mRes.next()) {
                        companyname = mRes.getString(1).trim();

                    }

                    conn4 = ConnectDB2.ConnectionDB();
                    Statement sta = conn4.createStatement();

//                    JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs1);
                    String path3 = getServletContext().getRealPath("/jaspers/");

                    JPD8 = JRXmlLoader.load(path3 + "RptgrnForm" + testornot + ".jrxml");
                    JasperReport jasperReport3 = JasperCompileManager.compileReport(JPD8);
                    File reportFile = new File(getServletContext().getRealPath("jaspers/customer_calender.jasper"));

                    conn3 = ConnectDB2.ConnectionDB();
                    Map parameters4 = new HashMap();
                    parameters4.put("imagesDir", path3);
                    parameters4.put("cono", cono);
                    parameters4.put("divi", divi);
                    parameters4.put("ordernum", GrnNumber);

                    JasperReport subjasperReport1 = JasperCompileManager.compileReport(path3 + "RptgrnForm_Costing" + testornot + ".jrxml");
                    JasperReport subjasperReport2 = JasperCompileManager.compileReport(path3 + "Rptgrn_subreportAccount" + testornot + ".jrxml");
                    parameters4.put("SUBREPORT_DIR1", subjasperReport1);
                    parameters4.put("SUBREPORT_DIR2", subjasperReport2);
                    JasperPrint jasp4 = JasperFillManager.fillReport(jasperReport3, parameters4, conn3);
                    ServletOutputStream ouputStream4 = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jasp4, ouputStream4);

                } catch (JRException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }
            case "SummaryXLSX":

                String type2 = request.getParameter("Type");
                String startdate = request.getParameter("startdate");
                String enddate = request.getParameter("enddate");

                startdate = startdate.substring(0, 4) + startdate.substring(5, 7) + startdate.substring(8, 10);
                enddate = enddate.substring(0, 4) + enddate.substring(5, 7) + enddate.substring(8, 10);
                System.out.println(startdate);
                System.out.println(enddate);
                System.out.println(request.getParameter("invoiceround"));
                JasperDesign JPD9;
                try {
                    String path2 = getServletContext().getRealPath("/jaspers/");

                    JPD9 = JRXmlLoader.load(path2 + "RP_MonthlyERNNEN.jrxml");
                    if (type2.equals("GRN")) {
                        JPD9 = JRXmlLoader.load(path2 + "RP_MonthlyGRN.jrxml");
                    }
                    JasperReport jasperReport2 = JasperCompileManager.compileReport(JPD9);
                    File reportFile = new File(getServletContext().getRealPath("jaspers/RP_MonthlyERNNEN.jasper"));

                    conn2 = ConnectDB2.ConnectionDB();

                    Map parameters2 = new HashMap();
                    parameters2.put("fromdate", startdate);
                    parameters2.put("todate", enddate);
                    parameters2.put("Company", comp2);
                    parameters2.put("cono", cono2);
                    parameters2.put("divi", divi2);

                    JasperPrint jasp = JasperFillManager.fillReport(jasperReport2, parameters2, conn2);
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + "Summary" + startdate + "_" + enddate + ".xlsx" + "\"");
                    JRXlsxExporter exporterXls2 = new JRXlsxExporter();
                    ServletOutputStream ouputStream2 = response.getOutputStream();
                    exporterXls2.setParameter(JRExporterParameter.JASPER_PRINT, jasp);
                    exporterXls2.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream2);
                    exporterXls2.exportReport();
                    ouputStream2.flush();
                    ouputStream2.close();

                } catch (JRException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Summary":
                String type = request.getParameter("Type");
                String startdate2 = request.getParameter("startdate");
                String enddate2 = request.getParameter("enddate");
                startdate2 = startdate2.substring(0, 4) + startdate2.substring(5, 7) + startdate2.substring(8, 10);
                enddate2 = enddate2.substring(0, 4) + enddate2.substring(5, 7) + enddate2.substring(8, 10);
                System.out.println(startdate2);
                System.out.println(enddate2);
                System.out.println(request.getParameter("invoiceround"));
                JasperDesign JPD10;
//                HttpSession session = request.getSession(true);
//                 session.getAttribute("cono");
                try {
                    String path3 = getServletContext().getRealPath("/jaspers/");

                    JPD10 = JRXmlLoader.load(path3 + "RP_MonthlyERNNEN.jrxml");
                    if (type.equals("GRN")) {
                        JPD10 = JRXmlLoader.load(path3 + "RP_MonthlyGRN.jrxml");
                    }
                    JasperReport jasperReport3 = JasperCompileManager.compileReport(JPD10);
                    File reportFile = new File(getServletContext().getRealPath("jaspers/Report_Billplacement_new.jasper"));

                    conn3 = ConnectDB2.ConnectionDB();

                    Map parameters3 = new HashMap();
                    parameters3.put("fromdate", startdate2);
                    parameters3.put("todate", enddate2);
                    parameters3.put("Company", comp2);
                    parameters3.put("cono", cono2);
                    parameters3.put("divi", divi2);

                    JasperPrint jasp3 = JasperFillManager.fillReport(jasperReport3, parameters3, conn3);
                    ServletOutputStream ouputStream3 = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jasp3, ouputStream3);
                    ouputStream3.flush();
                    ouputStream3.close();

                } catch (JRException ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

                } catch (Exception ex) {
                    Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                }
            default:
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
