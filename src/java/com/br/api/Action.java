/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.api;

import com.br.data.Delete;
import com.br.data.Insert;
import com.br.data.Select;
import com.br.data.Update;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wattana
 */
public class Action extends HttpServlet {

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
            throws ServletException, IOException, Exception {
            request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String path = request.getParameter("path");
        // String path = request.getServletPath();
        System.out.println("path: " + path);

        switch (path) {
            case "getCompany":
                out.print(Select.getCompany());
                out.flush();
                break;

            case "getItemData":
                out.print(Select.getItemData(request.getParameter("divi"), request.getParameter("cono")));
                out.flush();
                break;
            case "GetReferenceGRN_Bank":
                out.print(Select.GetReferenceGRN_Bank(request.getParameter("supplier"), request.getParameter("cono")));
                out.flush();
                break;
            case "checkPRN":
                out.print(Select.checkPRN(request.getParameter("cono"),
                         request.getParameter("divi"),
                         request.getParameter("PRNcode"),
                         request.getParameter("app"),
                         request.getParameter("costcenter"),
                         request.getParameter("supplier")));
                out.flush();
                break;
            case "checkITEM":
                out.print(Select.checkITEM(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("itemcode")));
                out.flush();
                break;
//            case "checkauth":
//                out.print(Select.checkauth(request.getParameter("user")));
//                out.flush();
//                break;
            case "CheckDuplicateSupInvoice":
                out.print(Select.CheckDuplicateSupInvoice(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("sup"),
                        request.getParameter("inv"),
                        request.getParameter("srn")));
                out.flush();
                break;
            case "get3prh":
                out.print(Select.get3prh(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("supplierdata")));
                out.flush();
                break;

            case "getsupplierlist":
                out.print(Select.getsupplierlist(request.getParameter("cono")));
                out.flush();
                break;
            case "getitemlist":
                out.print(Select.getitemlist(request.getParameter("cono")));
                out.flush();
                break;
            case "checkCostcenter":
                out.print(Select.checkCostcenter(request.getParameter("cono"), request.getParameter("divi"), request.getParameter("costcenter")));
                out.flush();
                break;
            case "checkSupplier":
                out.print(Select.checkSupplier(request.getParameter("cono"), request.getParameter("divi"), request.getParameter("supplier")));
                out.flush();
                break;
            case "showCompletedSRN":
                out.print(Select.showCompletedSRN(request.getParameter("cono"),
                        request.getParameter("app"),
                        request.getParameter("costcenter"),
                        request.getParameter("divi"), request.getParameter("user")));
                out.flush();
                break;
            case "getdetailfromgrn":
                out.print(Select.getdetailfromgrn(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("grn")));
                out.flush();
                break;

            case "showLinkedNo":
                out.print(Select.showLinkedNo(request.getParameter("cono"),
                        request.getParameter("cono")));
                out.flush();
                break;
            case "getItemfromRqNo":
                out.print(Select.getItemfromRqNo(request.getParameter("rqnum"),
                        request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getItemfromRqNoPRV":
                out.print(Select.getItemfromRqNoPRV(request.getParameter("rqnum"),
                        request.getParameter("app"),
                        request.getParameter("cono"),
                        request.getParameter("divi")));
                out.flush();
                break;
            case "getItemfromRqNoPRV2":
                out.print(Select.getItemfromRqNoPRV2(request.getParameter("rqnum")));
                out.flush();
                break;
            case "getServicehistory":
                out.print(Select.getServicehistory(request.getParameter("startdate"),
                        request.getParameter("enddate"),
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("status"),
                        request.getParameter("user"),
                        request.getParameter("mode"),
                        request.getParameter("serviceno")));
                out.flush();
                break;
            case "getPaymenthistory":
                out.print(Select.getPaymenthistory(request.getParameter("startdate"),
                        request.getParameter("enddate"),
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("status"),
                        request.getParameter("user"),
                        request.getParameter("mode"),
                        request.getParameter("prvno")));
                out.flush();
                break;
            case "getServicesRollback":
                out.print(Select.getServicesRollback(request.getParameter("startdate"),
                        request.getParameter("enddate"),
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("status"),
                        request.getParameter("mode"),
                        request.getParameter("serviceno")));
                out.flush();
                break;
            case "getPaymentRollback":
                out.print(Select.getPaymentRollback(request.getParameter("startdate"),
                        request.getParameter("enddate"),
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("status"),
                        request.getParameter("mode"),
                        request.getParameter("paymentno")));
                out.flush();
                break;
            case "getOrderDataServices":
                out.print(Select.getOrderDataServices(request.getParameter("serviceno"),
                        request.getParameter("cono"),
                        request.getParameter("divi")));
                out.flush();
                break;
            case "getOrderDataPRV":
                out.print(Select.getOrderDataPRV(request.getParameter("serviceno"),
                        request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getordernum":
                out.print(Select.getordernum(request.getParameter("user"),
                        request.getParameter("app"),
                        request.getParameter("cono"),
                        request.getParameter("divi")));
                out.flush();
                break;
            case "getordernumPRV":
                out.print(Select.getordernumPRV(request.getParameter("app"),
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("user")));
                out.flush();
                break;

            // Insert
            case "addRentalDetail":
                out.flush();
                break;
            case "InsertPrvForm":
                out.print(Insert.InsertPrvForm(request.getParameter("EPRA_PHNO"),
                        request.getParameter("EPPA_NO"),
                        request.getParameter("app"),
                        request.getParameter("cono"),
                        request.getParameter("divi")
                ));
                out.flush();
                break;
            case "SavePrvForm":
                out.print(Insert.SavePrvForm(request.getParameter("EPPA_NO"),
                        request.getParameter("EPPA_DATE"),
                        request.getParameter("EPPA_DUEDT"),
                        request.getParameter("EPPA_SUNO"),
                        request.getParameter("EPPA_COCE"),
                        request.getParameter("EPPA_PAMT"),
                        request.getParameter("EPPA_PARM"),
                        request.getParameter("EPPA_REQBY"),
                        request.getParameter("EPPA_APPBY"),
                        request.getParameter("EPPA_APPDT"),
                        request.getParameter("EPPA_ADVREF"),
                        request.getParameter("EPPA_ADVAMT"),
                        request.getParameter("EPPA_REF1"),
                        request.getParameter("EPPA_REF2"),
                        request.getParameter("EPPA_REF3"),
                        request.getParameter("EPPA_STAT"),
                        request.getParameter("currentdate"),
                        request.getParameter("app"),
                        request.getParameter("cono"),
                        request.getParameter("divi")
                ));
                out.flush();
                break;
            case "SaveSrnForm":
                out.print(Insert.SaveSrnForm(request.getParameter("EPRH_COCE"),
                        request.getParameter("EPRH_PURPOS"),
                        request.getParameter("EPRH_RQSDT"),
                        request.getParameter("EPRH_RQRDT"),
                        request.getParameter("EPRH_APTNO"),
                        request.getParameter("EPRH_SUNO"),
                        request.getParameter("EPRH_BU"),
                        request.getParameter("EPRH_REM"),
                        request.getParameter("EPRH_REQBY"),
                        request.getParameter("EPRH_DISC"),
                        request.getParameter("EPRH_VTCD"),
                        request.getParameter("EPRH_VTAMT"),
                        request.getParameter("EPRH_FAC"),
                        request.getParameter("EPRH_WHS"),
                        request.getParameter("EPRH_INVSU"),
                        request.getParameter("EPRH_INVDT"),
                        request.getParameter("EPRH_STAT"),
                        request.getParameter("ServiceRequestNo"),
                        request.getParameter("EPRH_TOTAL"),
                        request.getParameter("currentdate"),
                        request.getParameter("app"),
                        request.getParameter("cono"),
                        request.getParameter("divi")
                ));
                out.flush();
                break;
            case "insertNewItemService":
                out.print(Insert.insertNewItemService(request.getParameter("requestno"),
                        request.getParameter("itemcode"), request.getParameter("desciprtion"),
                        request.getParameter("qty"), request.getParameter("upprice"),
                        request.getParameter("vatpercentage"), request.getParameter("totalbvat"),
                        request.getParameter("vatamount"), request.getParameter("totalamount"),
                        request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "insertNewItemPRV":
                out.print(Insert.insertNewItemService(request.getParameter("requestno"),
                        request.getParameter("itemcode"), request.getParameter("desciprtion"),
                        request.getParameter("qty"), request.getParameter("upprice"),
                        request.getParameter("vatpercentage"), request.getParameter("totalbvat"),
                        request.getParameter("vatamount"), request.getParameter("totalamount"),
                        request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            // Update
            case "updatePRV":
                out.print(Update.updatePRV(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("ordernum"),
                        request.getParameter("grnno"),
                        request.getParameter("Invno"),
                        request.getParameter("Desc")
                ));
                out.flush();
                break;
            case "UpdateSRNumbers":
                out.print(Update.UpdateSRNumbers(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("rqnum"),
                        request.getParameter("total"),
                        request.getParameter("discount"),
                        request.getParameter("vat"),
                        request.getParameter("vatamt")));
                out.flush();
                break;
            case "rollbackservices":
                out.print(Update.rollbackservices(request.getParameter("serviceno"),
                        request.getParameter("cono"), request.getParameter("divi"),
                        request.getParameter("status")));
                out.flush();
                break;
            case "rollbackpayment":
                out.print(Update.rollbackpayment(request.getParameter("paymentno"),
                        request.getParameter("cono"), request.getParameter("divi"),
                        request.getParameter("status")));
                out.flush();
                break;
            case "rollbackserviceshistory":
                out.print(Update.rollbackserviceshistory(request.getParameter("serviceno"),
                        request.getParameter("cono"), request.getParameter("divi"),
                        request.getParameter("status"), request.getParameter("voucher")));
                out.flush();
                break;
            case "rollbackpaymenthistory":
                out.print(Update.rollbackpaymenthistory(request.getParameter("paymentno"),
                        request.getParameter("cono"), request.getParameter("divi"),
                        request.getParameter("status"), request.getParameter("voucher")));
                out.flush();
                break;
            case "changeSRNstatus":
                out.print(Update.changeSRNstatus(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("EPRH_PHNO"),
                        request.getParameter("status")
                ));
                out.flush();
                break;

            case "changePRVstatus":
                out.print(Update.changePRVstatus(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("EPPA_NO"),
                        request.getParameter("status")
                ));
                out.flush();
                break;
            // Delete
            case "deleteSRNLine":
                out.print(Delete.deleteSRNLine(request.getParameter("item"), request.getParameter("ordernum"), request.getParameter("cono"), request.getParameter("divi"), request.getParameter("app"), request.getParameter("number")));
                out.flush();
                break;
            case "DeletePrvForm":
                out.print(Delete.DeletePrvForm(request.getParameter("EPRA_PHNO"),
                        request.getParameter("EPPA_NO"),
                        request.getParameter("app"),
                        request.getParameter("cono"),
                        request.getParameter("divi")
                ));
                out.flush();
                break;
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
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
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
