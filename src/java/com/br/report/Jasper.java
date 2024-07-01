/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.report;

import com.br.connection.ConnectDB2;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 *
 * @author ACHARD
 */
public class Jasper {

    private JasperReport jasperReport;
    private JasperDesign jasperDesign;
    private Map param;
    Connection conn;

    public Jasper(String name, String path, Map parameters) throws JRException, Exception {
        conn = ConnectDB2.ConnectionDB();
        System.out.println(path + "" + name);
        jasperDesign = JRXmlLoader.load(path + "" + name);
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        param = parameters;
    }

    public void getreport(OutputStream outStream) throws JRException, Exception {
        JasperPrint jasp = JasperFillManager.fillReport(jasperReport, param, conn);
        JasperExportManager.exportReportToPdfStream(jasp, outStream);
    }
    
}
