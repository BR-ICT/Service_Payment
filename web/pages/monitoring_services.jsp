<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    if (session.getAttribute("cono") == null) {
//        response.sendRedirect("login.jsp");
    }
%>

<style>

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
        background: #ADD8E6;
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

</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>

<section class="container-w100">
    <div class="wrap-login100" style="width: 100%;margin-bottom: 0px;">
        <label for="Startdate">Start Invoice Date: </label>
        <input id="Startdate" name="startdate" type = "date" style="width: 150px;">
        <label for="Serviceno">Services: </label>
        <input id="Serviceno" name="Serviceno" type = "text" style="width: 150px;" disabled="">
        <br>
        <label for="Enddate">End Invoice Date: </label>
        <input id="Enddate" name="enddate" type = "date" style="width: 150px;margin: 0px 0px 0px 7px">
        <label for="vStatus">Status:</label>
        <select style="width: 100px" name="Status"  id="vStatus">
            <option value=""  selected="selected">ALL STATUS</option>
            <option value="20">Normal</option>
            <option value="50" >Submitted</option>
            <option value="99">Canceled</option>
        </select>
        <button id="vSearch" class="btn btn-success" type="submit" form="">Search</button>
        <input type="checkbox" id="vMode" name="mode" value="mode">
        <label id="vMode" for="txtPeriod" style="font-size: 14px;">Per Order No.</label>


    </div>
    <div id="jsGrid"></div>
</section>

<script>
    var cono = <%out.print(session.getAttribute("cono"));%>
    var divi = <%out.print(session.getAttribute("divi"));%>
    var user = "<%out.print(session.getAttribute("user"));%>";
    var mode = "Date";




    var NumberField = jsGrid.NumberField;

    function DecimalField(config) {
        NumberField.call(this, config);
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


    $("#jsGrid").jsGrid({
        width: "100%",
        height: "auto",
        editing: true,
        sorting: true,
        paging: true,
        filtering: true,
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
                        path: "getServicehistory",
                        cono: cono,
                        divi: divi,
                        startdate: $("#Startdate").val(),
                        enddate: $("#Enddate").val(),
                        status: $("#vStatus").val(),
                        user: user,
                        mode: mode,
                        serviceno: $("#Serviceno").val()

                    },
                    async: false,
                    timeout: 60000
                }).done(function (response) {
                    console.log(response);
                    response = $.grep(response, function (item) {
                        return(!filter.RStatus || (item.RStatus.indexOf(filter.RStatus) > -1))
                                && (!filter.RSERVICENO || (item.RSERVICENO.indexOf(filter.RSERVICENO) > -1))
                                && (!filter.RROWNO || (item.RROWNO.indexOf(filter.RROWNO) > -1))
                                && (!filter.RPAYMENTNO || (item.RPAYMENTNO.indexOf(filter.RPAYMENTNO) > -1))
                                && (!filter.RVOUCHER || (item.RVOUCHER.indexOf(filter.RVOUCHER) > -1))
                                && (!filter.RPURPOSE || (item.RPURPOSE.indexOf(filter.RPURPOSE) > -1))
                                && (!filter.RTYPE || (item.RTYPE.indexOf(filter.RTYPE) > -1))
                                && (!filter.RCOSTCENTER || (item.RCOSTCENTER.indexOf(filter.RCOSTCENTER) > -1))
                                && (!filter.RSUPPLIER || (item.RSUPPLIER.indexOf(filter.RSUPPLIER) > -1))
                                && (!filter.RSUPINVOICE || (item.RSUPINVOICE.indexOf(filter.RSUPINVOICE) > -1))
                                && (!filter.RREMARK || (item.RREMARK.indexOf(filter.RREMARK) > -1))
                                && (!filter.RINVDATE || (item.RINVDATE.indexOf(filter.RINVDATE) > -1))
                                && (!filter.RTOTAL || (item.RTOTAL.indexOf(filter.RTOTAL) > -1))
                                && (!filter.RDiscount || (item.RDiscount.indexOf(filter.RDiscount) > -1))
                                && (!filter.RVat || (item.RVat.indexOf(filter.RVat) > -1));

                        console.log(data.resolve(response));

                    });
                    data.resolve(response);

                    console.log(response);
                    console.log("response");
                });
                return data.promise();
            },
            insertItem: function (item) {
//                console.log(item);
//                formData = {};
//                formData.company = item.RCOMPANY;
//                formData.customerid = item.RCUSTOMERID;
//                formData.customertype = item.RCUSTOMERTYPE;
//                formData.roundinv = item.ROUNDINV;
//                formData.roundbill = item.ROUNDBILL;
//                formData.roundpay = item.ROUNDPAY;
//                formData.path = "addFinanceMaster";
//                $.ajax({
//                    url: './Action',
//                    type: 'POST',
//                    dataType: 'json',
//                    data: formData,
//                    async: false
//                });
//                $("#jsGrid").jsGrid("loadData");
            },
            updateItem: function (item) {
//                alert(item.RVOUCHER);
                if (!item.RVOUCHER === null) {
                    alert("This service Number has already assigned the voucher and cannot change the status");
                    return
                    $("#jsGrid").jsGrid("loadData");
                }
//                 console.log(item);
                formData = {};
                formData.serviceno = item.RSERVICENO;
                formData.cono = cono;
                formData.divi = divi;
                formData.status = item.RStatus;
                formData.path = "rollbackservices";
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: formData,
                    async: false
                }).done(function (response) {
                    alert(item.RSERVICENO + " has Been updated");
                });
                $("#jsGrid").jsGrid("loadData");
            },
            deleteItem: function (item) {
//                alert(item.RDTOTA_KGS);
//                console.log(item);
//                formData = {};
//                formData.company = item.RCOMPANY;
//                formData.customerid = item.RCUSTOMERID;
//                formData.path = "deleteFinanceMaster";
//                $.ajax({
//                    url: './Action',
//                    type: 'POST',
//                    dataType: 'json',
//                    data: formData,
//                    async: false
//                });
//                $("#jsGrid").jsGrid("loadData");
            }

        },
        fields: [
            {type: "control", width: 30,
                itemTemplate: function (value, item) {
                    // Customize the control column here
                    // Return an empty string to remove the delete button
                    return "";
                }},
            {title: "status", name: "RStatus", css: "limitext", type: "text", editing: true, align: "Right", width: 100
                ,
                editTemplate: function (value) {
                    // Create an input element with a datalist for insertion
                    var reitem = this._insertAuto = $input = $("<input>").attr("type", "text").attr("list", "itemList");
                    var $datalist = $("<datalist>").attr("id", "itemList");
                    // Add options to the datalist
                    $('#itemlist').empty().append('<option value="" selected="selected">Select Year!</option>');

                    $datalist.append($("<option>").attr("value", "Normal"));
                    $datalist.append($("<option>").attr("value", "Submitted"));
                    $datalist.append($("<option>").attr("value", "Canceled"));
                    return $("<div>").append($input).append($datalist);
                }, editValue: function () {
                    var revalue = this._insertAuto.val();
                    return revalue;
                }
            },
            {title: "Services No.", name: "RSERVICENO", css: "limitext", type: "text", editing: false, align: "left", width: 70},
            {title: "Row No.", name: "RROWNO", css: "limitext", type: "text", editing: false, align: "left", width: 70},
            {title: "PAYMENT No.", name: "RPAYMENTNO", css: "limitext", type: "text", editing: false, align: "left", width: 70},
            {title: "Voucher", name: "RVOUCHER", css: "limitext", type: "text", editing: false, align: "left", width: 70},
            {title: "Purpose/Sample", name: "RPURPOSE", css: "limitext", type: "text", editing: false, align: "left", width: 100},
            {title: "Type", name: "RTYPE", css: "limitext", type: "text", editing: false, align: "left", width: 50},
            {title: "Cost center", name: "RCOSTCENTER", css: "limitext", type: "text", editing: false, align: "left", width: 250},
            {title: "Supplier", name: "RSUPPLIER", css: "limitext", type: "text", editing: false, align: "left", width: 250},
            {title: "Sup Invoice", name: "RSUPINVOICE", css: "limitext", type: "text", editing: false, align: "left", width: 100},
            {title: "Remark", name: "RREMARK", css: "limitext", type: "text", editing: false, align: "left", width: 100},
            {title: "Invoice Date", name: "RINVDATE", css: "limitext", type: "text", editing: false, align: "right", width: 100},
            {title: "Total", name: "RTOTAL", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "Discount", name: "RDiscount", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "Vat(%)", name: "RVat", css: "limitext", type: "text", editing: false, align: "right", width: 50}


        ]

    });

    $("#vMode").change(function () {
        if (mode === "Date") {
//            $("#vMode").text("Mode:Number Filter");
            mode = "Num";
            $("#Startdate").prop("disabled", true);
            $("#Enddate").prop("disabled", true);
            $("#vStatus").prop("disabled", true);
            $("#Serviceno").prop("disabled", false);

        } else if (mode === "Num") {
//            $("#vMode").text("Mode:Date Filter");
            mode = "Date";
            $("#Startdate").prop("disabled", false);
            $("#Enddate").prop("disabled", false);
            $("#vStatus").prop("disabled", false);
            $("#Serviceno").prop("disabled", true);
        }
    });
    $("#vSearch").click(function () {
        $("#jsGrid").jsGrid("loadData");
    }
    );
    //------------USE TO SHOW THE CURRENT START AND END DATE OF THE MONTH
    function displayCurrentDate() {
        var currentDate = new Date();
        var startDate = new Date();
        var endDate = new Date();
        // GET CURRENTDATE
        var month = String(currentDate.getMonth() + 1).padStart(2, '0');
        var day = String(currentDate.getDate()).padStart(2, '0');
        var year = currentDate.getFullYear();
// GET END OF THE MONTH
        var lastDayOfMonth = new Date(year, month + 1, 0);
        var lastday = String(lastDayOfMonth.getDate()).padStart(2, '0');

        var formattedlastDate = year + '-' + month + '-' + lastday;
//        var formattedDate = year + '-' + month + '-' + day;
        var startDate = year + '-' + month + '-' + '01';
        endDate = year + '-' + month + '-' + day;
        $("#Startdate").val(startDate);
        $("#Enddate").val(endDate);
    }
    displayCurrentDate();
</script>  
