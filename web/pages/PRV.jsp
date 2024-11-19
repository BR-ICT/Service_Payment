<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    if (session.getAttribute("cono") == null) {
//        response.sendRedirect("login.jsp");
    }
%>

<style>
    .right-align {
        float: right;
    }
    label.plussign {
        color: #006400;
    }
    label.redtext {
        color: red;
    }
    .btn.btn-preview.right-align {
        background-color: purple; /* Change this to the desired color */
        /* You can also customize other styles such as text color, border, etc. */
        color: white;
        border: 1px solid darkgreen;
    }
    .btn.btn-printgrn.right-align {
        background-color: orange; /* Change this to the desired color */
        /* You can also customize other styles such as text color, border, etc. */
        color: white;
        border: 1px solid darkgreen;
    }
    td.limitext{
        white-space: nowrap;
        width: 100px;
        overflow: hidden;
        text-overflow:ellipsis;
        /*text-overflow: ellipsis !important;*/
    }

    button {
        outline: none !important;
        border: hidden;
        background: springgreen;
    }

    .ui-widget *, .ui-widget input, .ui-widget select, .ui-widget button {
        font-family: 'Helvetica Neue Light', 'Open Sans', Helvetica;
        font-size: 14px;
        font-weight: 300 !important;
    }

    .details-form-field input,
    .details-form-field select {
        width: 250px;
        float: right;
    }

    .details-form-field {
        margin: 25px 0;
    }

    .details-form-field:first-child {
        margin-top: 10px;
    }

    .details-form-field:last-child {
        margin-bottom: 10px;
    }

    .details-form-field button {
        display: block;
        width: 100px;
        margin: 0 auto;
    }

    input.error, select.error {
        border: 1px solid #ff9999;
        background: #ffeeee;
    }

    label.error {
        float: right;
        margin-left: 100px;
        font-size: .8em;
        color: #ff6666;
    }

    .form-control{
        display:block;
        width:100%;
        height:27px;
        padding:2px 0px;
        font-size:14px;
        line-height:1.42857143;
        color:#555;
        background-color:#fff;
        background-image:none;
        border:1px solid #ccc;
        border-radius:4px;
        -webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);
        box-shadow:inset 0 1px 1px rgba(0,0,0,.075);
        -webkit-transition:border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
        -o-transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s;
        transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s

    }

    .form-control:focus{
        border-color:#66afe9;
        outline:0;
        -webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
        box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
    }

    .form-control::-moz-placeholder{
        color:#999;
        opacity:1
    }

    .form-control:-ms-input-placeholder{
        color:#999
    }

    .form-control::-webkit-input-placeholder{
        color:#999
    }

    .form-control::-ms-expand{
        background-color:transparent;
        border:0
    }

    .form-control[disabled],.form-control[readonly],fieldset[disabled] .form-control{
        background-color:#eee;
        opacity:1
    }

    .form-control[disabled],fieldset[disabled] .form-control{
        cursor:not-allowed
    }

    td.limitext{
        white-space: nowrap;
        width: 100px;
        overflow: hidden;
        text-overflow:ellipsis;
        /*text-overflow: ellipsis !important;*/
    }

    .wrap-login100 {
        width: 100%;
        background: pink;
        border-radius: 2px;
        overflow: hidden;
        padding: 10px 10px 10px 10px;

        box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -moz-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -webkit-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -o-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -ms-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
    }

    td.lvgb{
        background-color: #bdd4ff !important;
    }

    td.lvgb-yello{
        background-color: #fff3bd !important;
    }

    td.lvgb-green{
        background-color: #72fc9e !important;
    }

    .container-w100{
        width: 100%;
        padding-left: 10px;
        padding-right: 10px;

    }

    .jsgrid-table {
        border-collapse: separate;
    }

    .jsgrid-grid-body td, .jsgrid-grid-header td, .jsgrid-grid-header th {
        border-left: 0;
        border-top: 0;
    }
    .middle-container {
        text-align: center;
    }
    .margin-right-div {
        text-align: right;
    }

</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>

<section class="container-w100">


    <div class="wrap-login100" style="width: 100%;margin-bottom: 0px;">

        <label for="txtPeriod" id="vappname" style="color: red; font-size: 18px;" >app name</label>
        <br>
        <label for="txtPeriod"  class="right-align" style="color: red;">หมายเหตุ : กดปุ่ม OK เพื่อ Confirm Submit (หลังจากปิด Report)</label> 
        <!--<div class="margin-right-div">-->
        <br>
        <br>
        <button id="vSubmit" class="btn btn-primary right-align" type="submit" form="" disabled>Change Mode:Submit</button>
        <br>
        <BR>
        <button id="vCancelMode" class="btn btn-danger right-align" type="submit" form="" disabled>Change Mode:Cancel</button>
        <br>
        <br>
        <button id="vPreview" class="btn btn-preview right-align" type="submit" form="" disabled>Preview</button>
        <br>
        <br>


        <!--        <label for="txtPeriod">Pr-Number:</label>
                <input id="vPRnum" name="prnum" disabled="">
                <input type="radio" name="status" value="Normal" id="vNormal">
                <label for="Status">Normal</label>
                <br>
        
                <input type="radio" name="status" value="Submit" id="vSubmit">
                <label for="Status">Submit</label>
        
                <input type="radio" name="status" value="Cancel" id="vCancel">
                <label for="Status">Cancel</label>-->

        <!--</div>-->
        <br>
        <label>PRV No.</label>
        <select  name="ordernum" id="vOrdernum" style="width: 393px;">
            <option value="" selected="selected">Select Ordernum</option>
        </select>
        <button id="vRefresh" class="btn btn-success" type="submit" form="">Refresh</button>


        <input type="checkbox" id="vDatafromnum" name="datafromnum" value="datafromnum">
        <label id="vExtralab" for="txtPeriod" style="color: red; font-size: 14px;">Create from GRN</label>
        <br>    
        <label for="GRNnofordata" id = "GRNnofordatalab">GRN no. </label>
        <input id="GRNnofordata" name="GRNnofordata" style="width: 260px;" disabled="">
        <button id="vaddgrnfromnum" class="btn btn-success" type="submit" >Get GRN Detail</button><!--
        -->


        <br>

        <label for="txtPeriod">Cost Center</label>
        <input id="costcenter" name="costcenter" list="costcenterlist" style="margin: 0px 0px 0px 40px;width: 260px;">
        <datalist id="costcenterlist">
            <option value="">Select Costcenter!</option>
        </datalist>
        <label for="txtPeriod">Date:</label>
        <input id="vDate" name="date" type="date" disabled="" style="margin: 0px 0px 0px 60px;">   
        <br>
        <label for="txtPeriod">Supplier</label>
        <input id="supplier" name="supplier" list="supplierlist" style="margin: 0px 0px 0px 65px;width: 260px;">
        <datalist id="supplierlist">
            <option value="Test">Select Supplier</option>
        </datalist>
        <label for="txtPeriod">Due Date:</label>
        <input id="vDuedate" name="duedate" type="date" style="margin: 0px 0px 0px 30px;">
        <br>
        <label for="txtPeriod">Payment Method</label>
        <input type="radio" name="paymentmethod" value="Cash" id="vCash">
        <label for="Status">Cash</label>
        <input type="radio" name="paymentmethod" value="Chequeno" id="vChequeno">
        <label for="Status">Cheque No.</label>
        <input type="radio" name="paymentmethod" value="BankTransfer" id="vBankTransfer">
        <label for="Status">Bank Transfer</label>
        <label style="margin: 0px 0px 0px 50px;">Payment Remark</label>
        <input id="vpaymentremark" name="paymentremark" style="width: 300px;margin: 0px 0px 0px 4px;" maxlength="60">
        <br>
        <label for="txtPeriod" >Requester</label>
        <input id="requester" name="requester" style="width: 150px;margin: 0px 0px 0px 50px;" disabled="">
        <input id="requester2" name="requester2" style="width: 150px;" disabled="">
        <br>
        <label for="txtPeriod">Deduct Desc</label>
        <input id="deductdesc" name="deductdesc" style="width: 150px;margin: 0px 0px 0px 30px;">
        <label for="txtPeriod">Deduct Amount</label>
        <input id="deductamount" name="deductamount" type="number" style="width: 150px;">
        <br>
        <button id="vsave" class="btn btn-success" type="submit" form="" style="margin: 0px 0px 0px 80px;">Save Data</button>
        <button id="vcancel" class="btn btn-success" type="submit" form="" >Clear</button>
        <br>      
        <label for="txtPeriod" style="color: red;">หมายเหตุ : กดปุ่ม </label>
        <label for="txtPeriod" class="plussign" style="font-size: 20px;">+</label>   
        <label for="txtPeriod" id="describeforprv" style="color: red;">เพื่อเพิ่ม Service No. (หลักจากเลือกเลข Payment No.) และ Status ของ Service No. ต้องเป็น Submit แล้วเท่านั้น</label> 
    </div>

    <div id="jsGrid"></div>
    <input id="totalsum" name="totalsum" style="margin: 0px 0px 0px 730px;width: 175px; text-align: right" disabled="">
    <input id="vattotal" name="vattotal" style="margin: 0px 0px 0px 180px;width: 175px; text-align: right"  disabled="">
    <input id="totalallsum" name="totalsum" style="margin: 0px 0px 0px 20px;width: 175px; text-align: right"  disabled="">
</section>

<script>
    var pulldatafromgrn = false;
    var cono = "<%out.print(session.getAttribute("cono"));%>";
    var divi = "<%out.print(session.getAttribute("divi"));%>";
    var prvno = "";
    var mode = "search";
    var user = "<%out.print(session.getAttribute("user"));%>";
    var app = GetURLParameter('app');
    var payment = "";
    var itemlistPP = ['sss', 'xxxx'];
    var ordernum = "";
    var lastestgrninbox = "";
    var grnfromnum = false;
//     ArrayList<String> itemlist2 = new ArrayList<String>();

    var NumberField = jsGrid.NumberField;
    function DecimalField(config) {
        NumberField.call(this, config);
    }
    if (app !== "GRN") {
        Getnewitemlist();
    }

    DecimalField.prototype = new NumberField({

        step: 0.01,
        filterValue: function () {
            return this.filterControl.val() ? parseFloat(this.filterControl.val()) : undefined;
        },
        insertValue: function () {
            return this.insertControl.val() ? parseFloat(this.insertControl.val()) : undefined;
        },
        editValue: function () {
            return this.editControl.val() ? parseFloat(this.editControl.val()) : undefined;
        },
        _createTextBox: function () {
            return NumberField.prototype._createTextBox.call(this)
                    .attr("step", this.step);
        }
    });
    jsGrid.fields.decimal = jsGrid.DecimalField = DecimalField;
//---------------------------------- My tag Field use to put Setting to our Autofill------------------------

    $(function (e) {

        var myTagField = function (config) {
            jsGrid.Field.call(this, config);
        };
        myTagField.prototype = new jsGrid.Field({
            sorter: function (tag1, tag2) {
                return tag1.localeCompare(tag2);
            },
            itemTemplate: function (value) {

                return value;
            },
            insertTemplate: function (value) {
                var grid = this._grid;
                var teamField = this._grid.fields[11];
                var reitem = this._insertAuto = $("<input>").autocomplete({
                    source: itemlistPP,
                    minLength: 0, // Set the minimum length to 0 to show suggestions without typing
                    select: function (event, ui) {
                        let text = ui.item.value;
                        // alert(text);
                    }
                });
                // Trigger the autocomplete suggestions when the input field is focused
                reitem.on('focus', function () {
                    reitem.autocomplete('search', '');
                });
                return reitem;
            },
            itemTemplate: function (value) {

                return value;
            },
            insertValue: function () {
                var revalue = this._insertAuto.val();
                return revalue;
            }

        });
        jsGrid.fields.myTagField = myTagField;
        //-------------------------------NORMAL JSGRID DATA--------------------------------
        $("#jsGrid").jsGrid({
            width: "100%",
            height: "auto",
            editing: true,
            sorting: true,
            paging: true,
            filtering: false,
            pageSize: 25,
            heading: true,
            inserting: false,
            selecting: true,
            pageLoading: false,
            onItemUpdating: function (args) {
                previousItem = args.previousItem;
            },
            onRefreshed: function (args) {
            },
            controller: {
                loadData: function (filter) {
                    console.log(filter);
                    var data = $.Deferred();
                    $.ajax({
                        type: 'GET',
                        url: './Action',
                        dataType: 'json',
                        data: {
                            path: "getItemfromRqNoPRV",
                            rqnum: $("#vOrdernum").val(),
                            app: app,
                            cono: cono,
                            divi: divi
                        },
                        async: false,
                        timeout: 60000
                    }).done(function (response) {
                        console.log(response);
                        response = $.grep(response, function (item) {
                            return(!filter.Rernno || (item.Rernno.indexOf(filter.Rernno) > -1))
                                    && (!filter.Rerndate || (item.Rerndate.indexOf(filter.Rerndate) > -1))
                                    && (!filter.pono || (item.pono.indexOf(filter.pono) > -1))
                                    && (!filter.cost || (item.cost.indexOf(filter.cost) > -1))
                                    && (!filter.Rerngrn || (item.Rerngrn.indexOf(filter.Rerngrn) > -1))
                                    && (!filter.Rerngrndate || (item.Rerngrndate.indexOf(filter.Rerngrndate) > -1))
                                    && (!filter.Rinvno || (item.Rinvno.indexOf(filter.Rinvno) > -1))
                                    && (!filter.Rinvdate || (item.Rinvdate.indexOf(filter.Rinvdate) > -1))
                                    && (!filter.Rtotal || (item.Rtotal.indexOf(filter.Rtotal) > -1))
                                    && (!filter.Rdiscount || (item.Rdiscount.indexOf(filter.Rdiscount) > -1))
                                    && (!filter.Charge || (item.Charge.indexOf(filter.Charge) > -1))
                                    && (!filter.Rvat || (item.Rvat.indexOf(filter.Rvat) > -1))
                                    && (!filter.Rtotalall || (item.Rtotalall.indexOf(filter.Rtotalall) > -1));
                            console.log(data.resolve(response));
                        });
                        data.resolve(response);
                        console.log(response);
                        console.log("response");
                        setTimeout(function () {
                            calculateSum();
                        }, 50);
                    }

                    );
                    return data.promise();
                },
// INSERT ITEM FUNCTION
                insertItem: function (item) {
                    console.log("testing supplier costcenter");
                    console.log($("#supplier").val());
                    console.log($("#costcenter").val());
                    formData = {};
                    formData.company = item.RCOMPANY;
                    $.ajax({
                        url: './Action',
                        type: 'GET',
                        dataType: 'json',
                        data: {
                            path: "checkPRN",
                            cono: cono,
                            divi: divi,
                            PRNcode: item.Rernno,
                            app: app,
                            costcenter: $("#costcenter").val(),
                            supplier: $("#supplier").val()
                        },
                        async: false
                    }).done(function (response) {
                        $.each(response, function (i, obj) {
                            var result = obj.result;
                            if (result === 'notexist') {
                                alert("Data " + app + " not Found");
                                $("#jsGrid").jsGrid("loadData");
                                return;
                            } else if (result === 'duplicated') {
                                alert("Invoice " + obj.invoice + " duplicated Found, the duplicated number of this Invoice is " + obj.dupgrn);
                                $("#jsGrid").jsGrid("loadData");
                                return;
                            } else {
                                var EPRA_PHNO = item.Rernno;
                                var rqnum = $("#vOrdernum").val();
                                $.ajax({
                                    url: './Action',
                                    type: 'POST',
                                    dataType: 'json',
                                    data: {
                                        path: "InsertPrvForm",
                                        EPRA_PHNO: rqnum,
                                        EPPA_NO: EPRA_PHNO,
                                        app: app,
                                        cono: cono,
                                        divi: divi
                                    },
                                    async: false
                                });
                                Getnewitemlist();
                                $("#jsGrid").jsGrid("loadData");
                            }

                        });
                    });
                },
// UPDATE ITEM
                updateItem: function (item) {
                    if (app === "GRN") {
                        console.log(item);
                        formData = {};
                        formData.ordernum = $("#vOrdernum").val();
                        formData.grnno = item.Rernno;
                        formData.Invno = item.Rinvno;
                        formData.Desc = item.Rdescription;
                        formData.path = "updatePRV";
                        formData.cono = cono;
                        formData.divi = divi;
                        $.ajax({
                            url: './Action',
                            type: 'POST',
                            dataType: 'json',
                            data: formData,
                            async: false
                        });
                    } else {
                        alert("cannot update SRN/NEN PRV");
                    }


                    $("#jsGrid").jsGrid("loadData");
                },
//DELETE ITEM
                deleteItem: function (item) {
                    var EPRA_PHNO = item.Rernno;
                    var rqnum = $("#vOrdernum").val();
                    $.ajax({
                        url: './Action',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            path: "DeletePrvForm",
                            EPRA_PHNO: rqnum,
                            EPPA_NO: EPRA_PHNO,
                            app: app,
                            cono: cono,
                            divi: divi
                        },
                        async: false
                    });
                    Getnewitemlist();
                    $("#jsGrid").jsGrid("loadData");
                }

            },
            fields: [
                {type: "control", width: 30},
                {title: "No.", name: "Rno", css: "limitext", type: "text", inserting: false, editing: false, align: "center", width: 30},
                // column 0 for  GRN
                {title: "Payment No.", name: "Rernno", css: "limitext", type: "myTagField", editing: false, align: "left", width: 75},
                // column 0 for GRN
                {title: "Payment Date.", name: "Rerndate", css: "limitext", type: "text", inserting: false, editing: false, align: "left", width: 100},
                //Column 1 for GRN
                {title: "Po No.", name: "pono", css: "limitext", type: "text", inserting: false, editing: false, align: "left", width: 75},
                //Column 2 for GRN
                {title: "Cost.", name: "cost", css: "limitext", type: "text", inserting: false, editing: false, align: "right", width: 50},
                {title: "ERN Date.", name: "Rerndate", css: "limitext", type: "text", inserting: false, editing: false, align: "left", visible: false},
                {title: "ERN Date (GRN)", name: "Rerngrndate", css: "limitext", type: "text", inserting: false, editing: false, align: "right", width: 100},
                //Column 3 for GRN
                {title: "Invoice No.", name: "Rinvno", css: "limitext", type: "text", inserting: false, editing: true, align: "right", width: 100},
                {title: "Invoice Date", name: "Rinvdate", css: "limitext", type: "text", inserting: false, editing: false, align: "right", width: 100},
                //Column 6 For GRN
                {title: "Description", name: "Rdescription", css: "limitext", type: "text", inserting: false, editing: true, align: "left", width: 250},
                {title: "Total", name: "Rtotal", css: "limitext", type: "decimal", inserting: false, editing: false, align: "right", width: 100},
                //Column 8 for GRN
                {title: "Discount", name: "Rdiscount", css: "limitext", type: "decimal", inserting: false, editing: false, align: "right", width: 100},
                //Column 9 for GRN
                {title: "Charge.", name: "Charge", css: "limitext", type: "decimal", inserting: false, editing: false, align: "right", width: 100},
                //Column 10 for GRN
                {title: "Amount.", name: "Amount", css: "limitext", type: "decimal", inserting: false, editing: false, align: "right", width: 100},
                //Column 11 for GRN
                {title: "Vat", name: "Rvat", css: "limitext", type: "decimal", inserting: false, editing: false, align: "right", width: 100},
                {title: "Total All.", name: "Rtotalall", css: "limitext", type: "decimal", inserting: false, editing: false, align: "right", width: 100},
                //Column 12 for GRN
                {title: "Total Amount", name: "totalamount", css: "limitext", type: "decimal", inserting: false, editing: false, align: "right", width: 100}

            ]

        });
    });
    $(function (e) {
        const columns = $("#jsGrid").jsGrid("option", "fields");
        if (app === "GRN") {

            columns[2].title = "GRN No.";
            columns[3].title = "GRN Date.";
//            columns[3].title = "GRN Date.";
            columns[6].visible = false;
            columns[7].visible = false;
            columns[9].visible = false;
            columns[11].visible = false;
            columns[16].visible = false;
            $("#totalsum").prop("style", "margin: 0px 0px 0px 1080px;width: 120px; text-align: right");
            $("#vattotal").prop("style", "margin: 0px 0px 0px 0px;width: 120px; text-align: right");
            $("#totalallsum").prop("style", "margin: 0px 0px 0px 0px;width: 120px; text-align: right");
            $("#describeforprv").text("เพื่อเพิ่ม GRN No. (หลักจากเลือกเลข Payment No.)");
        } else {
            $("#vDatafromnum").hide();
            $("#vExtralab").hide();
            $("#GRNnofordatalab").hide();
            $("#GRNnofordata").hide();
            $("#vaddgrnfromnum").hide();
            columns[4].visible = false;
            columns[5].visible = false;
            columns[8].editing = false;
            columns[7].visible = false;
            columns[10].visible = false;
            columns[13].visible = false;
            columns[14].visible = false;
            columns[17].visible = false;
        }
        $("#jsGrid").jsGrid("option", "fields", columns);
    });
    $("#vSubmit").click(function () {
        var ordernum = encodeURIComponent($("#vOrdernum").val());
        if (app === "ERN") {
            var url = "Report?PrvNumber=" + ordernum + "&report=Prvform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        } else if (app === "NEN") {
            var url = "Report?NENNumber=" + ordernum + "&report=PrvNenform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        } else if (app === "GRN") {
            var url = "Report?GRNNumber=" + ordernum + "&report=PrvGrnform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        }
        let myWindow = window.open(url, "_blank");
        var timer = setInterval(function () {
            if (myWindow.closed) {
                clearInterval(timer);
                // alert("closed");
                if (window.confirm(`Submit Confirm?`)) {
                    $.ajax({
                        url: './Action',
                        type: 'GET',
                        dataType: 'text',
                        data: {
                            path: "changePRVstatus",
                            EPPA_NO: $("#vOrdernum").val(),
                            status: "10",
                            cono: cono,
                            divi: divi
                        },
                        async: false,
                        timeout: 60000
                    }).done(function (response) {
                        var ordernum = $("#vOrdernum").val();
//                            console.log(response);
                        alert(ordernum + " has been submitted");
                        ResetPRV();
                        modechangecheckPRV();
                        getOrdernumPRV();
//                        console.log(response);
                    });
                }
            }
        }, 1000);
    });
    $("#vDatafromnum").change(function changedatafromnum() {
        if (grnfromnum) {
            grnfromnum = false;
            console.log("turn it off");
            $("#GRNnofordata").prop("disabled", true);
            $("#vOrdernum").prop("disabled", false);
            $("#GRNnofordatalab").hide();
            $("#GRNnofordata").hide();
            $("#vaddgrnfromnum").hide();
        } else if (!grnfromnum) {
            grnfromnum = true;
            console.log("turn it on");
            $("#GRNnofordatalab").show();
            $("#GRNnofordata").show();
            $("#vaddgrnfromnum").show();
            $("#GRNnofordata").prop("disabled", false);
            $("#vOrdernum").prop("disabled", true);
        }
    });
    $("#GRNnofordatalab").hide();
    $("#GRNnofordata").hide();
    $("#vaddgrnfromnum").hide();
    $("#vaddgrnfromnum").click(function () {
        var GRNNO = $("#GRNnofordata").val();
        console.log(GRNNO);
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'text',
            data: {
                path: "getdetailfromgrn",
                cono: cono,
                divi: divi,
                grn: GRNNO
            },
            async: false,
            timeout: 60000
        }).done(function (response) {
            console.log(response);
            var responseObject = JSON.parse(response);
            $.each(responseObject, function (i, obj) {
                $("#supplier").val(obj.supplier);
                $("#costcenter").val(obj.costcenter);
            });
        });
    });
    $("#vPreview").click(function () {
        var ordernum = encodeURIComponent($("#vOrdernum").val());
        if (app === "ERN") {
            var url = "Report?PrvNumber=" + ordernum + "&report=Prvform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        } else if (app === "NEN") {
            var url = "Report?NENNumber=" + ordernum + "&report=PrvNenform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        } else if (app === "GRN") {
            var url = "Report?GRNNumber=" + ordernum + "&report=PrvGrnform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        }
        let myWindow = window.open(url, "_blank");
        var timer = setInterval(function () {

        }, 1000);
    });
    $("#vCancelMode").click(function () {
//        test("value");
        if (window.confirm('You about about to Cancel Order: ' + $("#vOrdernum").val())) {
            $.ajax({
                url: './Action',
                type: 'GET',
                dataType: 'text',
                data: {
                    path: "changePRVstatus",
                    EPPA_NO: $("#vOrdernum").val(),
                    status: "99",
                    cono: cono,
                    divi: divi
                },
                async: false,
                timeout: 60000
            }).done(function (response) {
                var ordernum = $("#vOrdernum").val();
                alert(ordernum + " has been Canceled");
                ResetPRV();
                modechangecheckPRV();
                getOrdernumPRV();
            });
        }
    });
//Get Order Number
    function getOrdernumPRV() {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getordernumPRV",
                app: app,
                cono: cono,
                divi: divi,
                user: user
            },
            async: false
        }).done(function (response) {
            console.log(response);
            $('#vOrdernum').empty().append('<option value="" selected="selected">Select Ordernum</option>');
            $.each(response, function (i, obj) {
                var div_data = "<option value=" + obj.vOrderdata + ">" + obj.vOrdernum + "</option>";
                $(div_data).appendTo('#vOrdernum');
            });
        });
    }





    $("#vsave").click(function checkforsave() {

        lastestgrninbox = $("#GRNnofordata").val();
        var duedate = $("#vDuedate").val();
        var costcenter = $("#costcenter").val();
        var supplier = $("#supplier").val();
        var Cash = $('input[name="paymentmethod"][value="Cash"]').is(":checked");
        var Chequeno = $('input[name="paymentmethod"][value="Chequeno"]').is(":checked");
        var BankTransfer = $('input[name="paymentmethod"][value="BankTransfer"]').is(":checked");
        var deductdesc = $("#deductdesc").val();
        var check;
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "checkCostcenter",
                divi: divi,
                cono: cono,
                costcenter: $("#costcenter").val()
            },
            async: false
        }).done(function (response) {
            console.log("Response checkdup" + response);
//            Costcenter = response;
//            console.log(checkdup);
            $.each(response, function (i, obj) {
                var result = obj.Check;
                console.log("Result of costcenter" + result);
                if (result === "0") {
                    alert('Costcenter Does not existed!');
                    check = true;
                    return;
                }
            });
        });
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "checkSupplier",
                divi: divi,
                cono: cono,
                supplier: $("#supplier").val()
            },
            async: false
        }).done(function (response) {
//            console.log("Response checkdup" + response);
//            Costcenter = response;
//            console.log(checkdup);
            $.each(response, function (i, obj) {
                var result = obj.Check;
                console.log("Result of Supplier" + result);
                if (result === "0") {
                    alert('Supplier Does not existed!');
                    check = true;
                    return;
                }
            });
        });
        if (check === true) {
            return;
        }
        if (duedate === "") {
            alert('Please Check Due Date');
            return;
        }
        if (costcenter === "") {
            alert("Please Check Cost center");
            return;
        }
        if (supplier === "") {
            alert("Please Check Supplier");
            return;
        }
        if (Cash === false && Chequeno === false && BankTransfer === false) {
            alert("Please Check Payment Method");
            return;
        }
        if (deductdesc.length > 30) {
            alert("Please Check Deduct To Maximum 30 digit");
            return;
        }



        if (window.confirm('Would You Like to Save Purchase Request?'))
        {
            var EPPA_NO = $("#vOrdernum").val();
//            console.log(EPPA_NO);
            var EPPA_COCE = $("#costcenter").val(); // GET COST CENTER
//            console.log(EPPA_COCE);
            var EPPA_SUNO = $("#supplier").val();
//            console.log(EPPA_SUNO);
            var EPPA_DATE = $("#vDate").val(); // GET PURPOSE SAMPLE
//            console.log(EPPA_DATE);
            var EPPA_DUEDT = $("#vDuedate").val();
//            console.log(EPPA_DUEDT);
            var EPPA_PARM = $("#vpaymentremark").val();
//            console.log(EPPA_PARM);
            var EPPA_REQBY = $("#requester").val();
            var EPPA_APPBY = "";
            var EPPA_APPDT = "";
            var EPPA_ADVREF = $("#deductdesc").val();
            var EPPA_REF1 = "1";
            var EPPA_REF2 = app;
            var EPPA_REF3 = "";
            var EPPA_STAT = "00";
            var EPPA_ADVAMT = 0;
            var EPPA_PAMT = "0";
            EPPA_ADVAMT = $("#deductamount").val();
            console.log("cash" + Cash);
            console.log("cheque" + Chequeno);
            console.log("BankTransfer" + BankTransfer);
            if (Cash === true) {
                EPPA_PAMT = "1";
            }

            if (Chequeno === true) {
                EPPA_PAMT = "2";
            }

            if (BankTransfer === true) {
                EPPA_PAMT = "3";
            }
            console.log(EPPA_PAMT);
            if ($("#vOrdernum").val() === "")
            {
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "SavePrvForm",
                        EPPA_NO: EPPA_NO,
                        EPPA_DATE: EPPA_DATE,
                        EPPA_DUEDT: EPPA_DUEDT,
                        EPPA_SUNO: EPPA_SUNO,
                        EPPA_COCE: EPPA_COCE,
                        EPPA_PAMT: EPPA_PAMT,
                        EPPA_PARM: EPPA_PARM,
                        EPPA_REQBY: EPPA_REQBY,
                        EPPA_APPBY: EPPA_APPBY,
                        EPPA_APPDT: EPPA_APPDT,
                        EPPA_ADVREF: EPPA_ADVREF,
                        EPPA_ADVAMT: EPPA_ADVAMT,
                        EPPA_REF1: EPPA_REF1,
                        EPPA_REF2: EPPA_REF2,
                        EPPA_REF3: EPPA_REF3,
                        EPPA_STAT: EPPA_STAT,
                        currentdate: EPPA_DATE,
                        app: app,
                        cono: cono,
                        divi: divi},
                    async: false
                }).done(function (response) {
                    $.each(response, function (i, obj) {
                        var result = obj.Result;
                        alert(result);
                        console.log("response" + response[1]);
                        console.log("result" + result);
                        prvno = obj.orderno;
                    });
                    ResetPRV();
                    modechangecheckPRV();
                    getOrdernumPRV();
                    $("#vOrdernum").val(prvno);
                    PRVchange();
                    setTimeout(function () {
                        modechangecheckPRV();
                        Getnewitemlist();
                    }, 10);
                });
                //IF THE USER PULL FROM GRN NUM
                if (grnfromnum) {
                    $.ajax({
                        url: './Action',
                        type: 'GET',
                        dataType: 'json',
                        data: {
                            path: "checkPRN",
                            cono: cono,
                            divi: divi,
                            PRNcode: lastestgrninbox,
                            app: app,
                            costcenter: $("#costcenter").val(),
                            supplier: $("#supplier").val()
                        },
                        async: false
                    }).done(function (response) {
                        $.each(response, function (i, obj) {
                            var result = obj.result;
                            if (result === 'notexist') {
                                alert("Data " + app + " not Found");
                                $("#jsGrid").jsGrid("loadData");
                                return;
                            } else {
                                var EPRA_PHNO = lastestgrninbox;
                                var rqnum = $("#vOrdernum").val();
                                $.ajax({
                                    url: './Action',
                                    type: 'POST',
                                    dataType: 'json',
                                    data: {
                                        path: "InsertPrvForm",
                                        EPRA_PHNO: rqnum,
                                        EPPA_NO: EPRA_PHNO,
                                        app: app,
                                        cono: cono,
                                        divi: divi
                                    },
                                    async: false
                                });
                                Getnewitemlist();
                                $("#jsGrid").jsGrid("loadData");
                                clearGetFromGRN();
                            }

                        });
                    });
                }
            } else {

                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "SavePrvForm",
                        EPPA_NO: EPPA_NO,
                        EPPA_DATE: EPPA_DATE,
                        EPPA_DUEDT: EPPA_DUEDT,
                        EPPA_SUNO: EPPA_SUNO,
                        EPPA_COCE: EPPA_COCE,
                        EPPA_PAMT: EPPA_PAMT,
                        EPPA_PARM: EPPA_PARM,
                        EPPA_REQBY: EPPA_REQBY,
                        EPPA_APPBY: EPPA_APPBY,
                        EPPA_APPDT: EPPA_APPDT,
                        EPPA_ADVREF: EPPA_ADVREF,
                        EPPA_ADVAMT: EPPA_ADVAMT,
                        EPPA_REF1: EPPA_REF1,
                        EPPA_REF2: EPPA_REF2,
                        EPPA_REF3: EPPA_REF3,
                        EPPA_STAT: EPPA_STAT,
                        currentdate: EPPA_DATE,
                        app: app,
                        cono: cono,
                        divi: divi},
                    async: false
                }).done(function (response) {
                    $.each(response, function (i, obj) {
                        var result = obj.Result;
                        alert(result);
                        ResetPRV();
                        prvno = obj.orderno;
                        modechangecheckPRV();
                        getOrdernumPRV();
                        $("#vOrdernum").val(prvno);
                        PRVchange();
                        setTimeout(function () {
                            modechangecheckPRV();
                            Getnewitemlist();
                        }, 10);
//

//                    }
                    });
                });
            }

        }
    });
    function calculateSum() {
        if (app === "GRN") {
            var totalsum = 0;
            var vattotal = 0;
            var totalallsum = 0;
            var jsGridData = $("#jsGrid").jsGrid("option", "data");
            console.log(jsGridData);
            for (var i = 0; i < jsGridData.length; i++) {
                totalsum += parseFloat(jsGridData[i].Amount);
                var formattedtotalsum = totalsum.toFixed(2);
                vattotal += parseFloat(jsGridData[i].Rvat);
                var formattedvattotal = vattotal.toFixed(2);
                totalallsum += parseFloat(jsGridData[i].totalamount);
                var formattedtotalallsum = totalallsum.toFixed(2);
            }
        } else {
            var totalsum = 0;
            var vattotal = 0;
            var totalallsum = 0;
            var jsGridData = $("#jsGrid").jsGrid("option", "data");
            console.log(jsGridData);
            for (var i = 0; i < jsGridData.length; i++) {
                totalsum += parseFloat(jsGridData[i].Rtotal);
                var formattedtotalsum = totalsum.toFixed(2);
                vattotal += parseFloat(jsGridData[i].Rvat);
                var formattedvattotal = vattotal.toFixed(2);
                totalallsum += parseFloat(jsGridData[i].Rtotalall);
                var formattedtotalallsum = totalallsum.toFixed(2);
            }
        }


        // Update the input field with the sum
        $("#totalsum").val(formattedtotalsum);
        $("#vattotal").val(formattedvattotal);
        $("#totalallsum").val(formattedtotalallsum);
    }



    $("#costcenter").change(function () {
        console.log("testcostcenter");
        if (app !== "GRN") {
            Getnewitemlist();
        }
    }
    );
    $("#vCash").click(function changepayment1() {
        console.log($("#vCash").val());
    });
    $("#vChequeno").click(function changepayment2() {
        console.log($("#vChequeno").val());

    });
    $("#vBankTransfer").click(function changepayment3() {
        var EPPA_SUNO = $("#supplier").val();
        console.log(EPPA_SUNO);
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "GetReferenceGRN_Bank",
                supplier: EPPA_SUNO,
                cono: cono
            },
            async: false
        }).done(function (response) {
            $.each(response, function (i, obj) {
                $("#vpaymentremark").val(obj.vBankdetail);
            });
        });
    });
    $.ajax({
        url: './Action',
        type: 'GET',
        dataType: 'json',
        data: {
            path: "getsupplierlist",
            cono: cono
        },
        async: false
    }).done(function (response) {
        console.log(response);
        warehouse = response;
        $('#supplierlist').empty().append('<option value="" selected="selected">Select Year!</option>');
        $.each(response, function (i, obj) {
            var div_data = "<option>" + obj.supplierlist + "</option>";
            $(div_data).appendTo('#supplierlist');
        });
    });
    //GET DATA FOR ITEM
    $.ajax({
        url: './Action',
        type: 'GET',
        dataType: 'json',
        data: {
            path: "getItemData",
            divi: divi,
            cono: cono
        },
        async: false
    }).done(function (response) {
        console.log(response);
        warehouse = response;
        $('#costcenterlist').empty().append('<option value="" selected="selected">Select Year!</option>');
        $.each(response, function (i, obj) {
            var div_data = "<option>" + obj.vAutofill + "</option>";
            $(div_data).appendTo('#costcenterlist');
        });
    });
    $("#vOrdernum").change(function () {
        var ordernum = $("#vOrdernum").val();
        console.log(ordernum);
        PRVchange();
        setTimeout(function () {
            modechangecheckPRV();
            Getnewitemlist();
        }, 10);
    });

    $("#supplier").change(function () {
        var BankTransfer = $('input[name="paymentmethod"][value="BankTransfer"]').is(":checked");
        if (BankTransfer) {
            var EPPA_SUNO = $("#supplier").val();
            console.log("Supplier CHanged");
            $.ajax({
                url: './Action',
                type: 'GET',
                dataType: 'json',
                data: {
                    path: "GetReferenceGRN_Bank",
                    supplier: EPPA_SUNO,
                    cono: cono
                },
                async: false
            }).done(function (response) {
                $.each(response, function (i, obj) {
                    console.log(obj.vBankdetail)
                    $("#vpaymentremark").val(obj.vBankdetail);
                });
            });
        }
    });
    function PRVchange() {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getOrderDataPRV",
                serviceno: $("#vOrdernum").val(),
                cono: cono,
                divi: divi
            },
            async: false
        }).done(function (response) {
            $.each(response, function (i, obj) {
                $("#vpaymentremark").val(obj.vpaymentremark);
                $("#costcenter").val(obj.costcenter);
                $("#supplier").val(obj.supplier);
                $("#vDate").val(obj.vDate);
                $("#vDuedate").val(obj.vDuedate);
                $("#deductdesc").val(obj.deductdesc);
                $("#deductamount").val(obj.deductamount);
                payment = obj.paymentmethod;
                console.log("payment" + payment);
                if (payment === "1") {
                    $("input[value='Cash']").prop("checked", true);
                } else if (payment === "2") {
                    console.log("second is true");
                    $("input[value='Chequeno']").prop("checked", true);
                } else if (payment === "3") {
                    console.log("third is true");
                    $("input[value='BankTransfer']").prop("checked", true);
                }
                ;
            });
        });
    }
    ;
    function modechangecheckPRV() {
        var ordernum = $("#vOrdernum").val();
        if (ordernum === '') {
            mode = "new";
            console.log("reset");
            ResetPRV();
            $("#vsave").text("Save Data");
            $("#vSubmit").prop("disabled", true);
            $("#vCancelMode").prop("disabled", true);
            $("#vPreview").prop("disabled", true);
            $("#vDatafromnum").prop("disabled", false);
            $("#jsGrid").jsGrid({
                // your jsGrid configuration options
                onRefreshing: function (args) {
                    args.cancel = true;
                    args.grid.inserting = false;
                }
            });
        } else {
            mode = "edit";
            console.log("chosen");
//            $("#jsGrid").jsGrid("loadData");

            $("#vDatafromnum").prop("disabled", true);
            $("#vsave").text("Update Data");
            $("#vSubmit").prop("disabled", false);
            $("#vCancelMode").prop("disabled", false);
            $("#vPreview").prop("disabled", false);
            $("#jsGrid").jsGrid("loadData");
            $("#jsGrid").jsGrid({
                // your jsGrid configuration options
                onRefreshing: function (args) {
                    args.cancel = true;
                    args.grid.inserting = true;
                }
            });
//            setTimeout(function () {
//                SetCalculateDetailModel();
//            }, 10); // Delay of 1000 milliseconds (1 seconds)
        }
    }
    ;
    $("#vRefresh").click(function refreshfunction() {
        ResetPRV();
        modechangecheckPRV();
        getOrdernumPRV();
    });
    function ResetPRV() {
        payment = "";
        displayCurrentDate();
        $("#vOrdernum").val("");
        $("#costcenter").val("");
        $("#supplier").val("");
        $("#vDuedate").val("");
        $("#vpaymentremark").val("");
        $("input[value='Cash']").prop("checked", false);
        $("input[value='Chequeno']").prop("checked", false);
        $("input[value='BankTransfer']").prop("checked", false);
        $("#deductdesc").val("");
        $("#deductamount").val("0");
        $("#totalsum").val("");
        $("#vattotal").val("");
        $("#totalallsum").val("");
        mode = "new";
        $("#vDatafromnum").prop("checked", false);
        clearTable();
    }
    ;
    function clearGetFromGRN() {
        grnfromnum = false;
        console.log("turn it off");
        $("#GRNnofordata").prop("disabled", true);
        $("#vOrdernum").prop("disabled", false);
        $("#GRNnofordatalab").hide();
        $("#GRNnofordata").hide();
        $("#vaddgrnfromnum").hide();
    }

    function displayCurrentDate() {
        var currentDate = new Date();
        var month = String(currentDate.getMonth() + 1).padStart(2, '0');
        var day = String(currentDate.getDate()).padStart(2, '0');
        var year = currentDate.getFullYear();
        var formattedDate = year + '-' + month + '-' + day;
//        var formattedDate = '2024' + '-' + '01' + '-' + '20';
        $("#vDate").val(formattedDate);
    }

    var jsGridInstance = $("#jsGrid").jsGrid({});
    function clearTable() {
        jsGridInstance.jsGrid("option", "data", []);
        jsGridInstance.jsGrid("refresh");
    }

    getOrdernumPRV();
    displayCurrentDate();
    $('#requester').val(user);
    function GetURLParameter(sParam)
    {
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        for (var i = 0; i < sURLVariables.length; i++)
        {
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] == sParam)
            {
                return sParameterName[1];
            }
        }
    }
    $("#vcancel").click(function () {
        ResetPRV();
        modechangecheckPRV();
        clearGetFromGRN();
    });
    console.log(app);
    if (app === "ERN") {
        $("#vappname").text("Payment Request for ERN");
    } else if (app === "NEN") {
        $("#vappname").text("Payment Request for NEN");
    } else if (app === "GRN") {
        $("#vappname").text("(GRN) Payment Request System");
    }


    $(document).ready(function () {
        if (app !== "GRN") {
            Getnewitemlist();
        }
    });
    function Getnewitemlist()
    {
//        itemlistPP.push('xxxx');
//        itemlistPP = [];
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "showCompletedSRN",
                cono: cono,
                app: app,
                costcenter: $("#costcenter").val(),
                divi: divi,
                user: user
            },
            async: false
        }).done(function (response) {
            itemlistPP.splice(0, itemlistPP.length);
            $.each(response, function (i, obj) {
                itemlistPP.push(response[i].RQNo);
            });
            console.log("test" + itemlistPP);
        });
    }
    $("#deductamount").val("0");
</script>  
