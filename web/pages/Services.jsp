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

    label.plussign {
        color: #006400;
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
    /*    #costcenter{
            width: 100px;
        }*/
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



        <label id="vappname" for="txtPeriod" style="color: red; font-size: 18px;">App name</label>

        <br>
        <label for="txtPeriod"  class="right-align" style="color: red;">หมายเหตุ : กดปุ่ม OK เพื่อ Confirm Submit (หลักจากปิด Report)</label> 
        <br>
        <br>
        <button id="vSubmit" class="btn btn-primary right-align" type="submit" form="" disabled>Change Mode:Submit</button>
        <br>
        <br>
        <button id="vCancelMode" class="btn btn-danger right-align" type="submit" form="" disabled>Change Mode:Cancel</button>
        <br>
        <div class="margin-right-div">
        </div>
        <label>Services No.</label>
        <select  name="ordernum" id="vOrdernum" style="width: 393px;">
            <option value="" selected="selected">Select Ordernum</option>
        </select>
        <button id="vRefresh" class="btn btn-success" type="submit" form="">Refresh</button>
        <br>
        <label for="txtPeriod">Request Date</label>
        <input id="vRQDate" name="RQdate" type = "date" style="margin: 0px 0px 0px 25px;" required>
        <!--<button id="vPaymentRQ" class="btn btn-success right-align" type="submit" form="">Payment Request</button>-->
        <label for="txtPeriod">Due Date</label>
        <input id="vRequireDate" name="Requiredate" type = "date" required>
        <label for="txtPeriod">Date:</label>
        <input id="vDate" name="date" type="date" style="width: 170px;" disabled>
        <label for="txtPeriod">Contract No.</label>
        <input id="ContractNo" name="Requiredate">
        <br>
        <label for="txtPeriod">Purpose/Sample</label>
        <input id="Purpose" name="purpose" style="margin: 0px 0px 0px 4px;">
        <br>
        <label for="txtPeriod">Cost center</label>
        <!--<input id="costcenter" name="costcenter" list="costcenterlist" style="width: 350px; margin: 0px 0px 0px 35px;">-->
        <!--<input id="costcenter" name="costcenter" list="costcenterlist" style="width: 350px; margin: 0px 0px 0px 35px;">-->
        <select name="costcenter" id="costcenter" style="width: 350px; margin: 0px 0px 0px 35px;">
            <option value=""    selected="selected">Select Costcenter</option>
        </select>
        <!--        <datalist id="costcenterlist">
                    <option value="">Select Costcenter!</option>
                </datalist>-->
        <input id="prh_fac" name="prh_fac" style="width: 30px;" disabled>
        <input id="prh_bu" name="prh_bu" style="width: 30px;" disabled>
        <input id="prh_whs" name="prh_whs" style="width: 30px;" disabled>
        <br>
        <label for="txtPeriod">Supplier</label>
        <select name="supplier" id="supplier" style="width: 350px; margin: 0px 0px 0px 35px;">
            <option value=""    selected="selected">Select Supplier</option>
        </select>
        <!--        <input id="supplier" name="supplier" list="supplierlist" style="width: 260px;margin: 0px 0px 0px 59px;">
                <datalist id="supplierlist">
                    <option value="Test">Select Supplier</option>
                </datalist>-->
        <label for="txtPeriod" style="margin: 0px 0px 0px 10px;">Sup. Invoice</label>
        <input id="supinv" name="supinv" style="width: 150px;" maxlength="15">
        <br>
        <label for="txtPeriod" >Remark</label>
        <input id="remark" name="remark" style="margin: 0px 0px 0px 60px;">
        <label for="txtPeriod" style="margin: 0px 0px 0px 100px;">Invoice Date</label>
        <input id="invdate" name="invdate" type = "date" style="width: 150px;">
        <br>
        <label for="txtPeriod">Requested By</label>
        <input id="reqby" name="reqby" style="width: 100px;margin: 0px 0px 0px 20px;" disabled>
        <input id="reqby2" name="reqby2" style="width: 120px;" disabled>
        <label for="txtPeriod" style="margin: 0px 0px 0px 95px;">Total</label>
        <input id="total" name="total" style="width: 150px;" disabled>
        <br>
        <label for="txtPeriod" style="margin: 0px 0px 0px 405px;">Discount</label>
        <input type="number" id="discount" name="discount" style="width: 150px;">
        <br>
        <button id="vSave" class="btn btn-success" type="submit" form="" style="margin: 0px 0px 0px 100px;">Save Data</button>
        <button id="vCancel" class="btn btn-success" type="submit" form="">Clear</button>
        <label for="txtPeriod" style="margin: 0px 0px 0px 165px;">Vat%</label>
        <input type="number" id="vat" name="vat" style="width: 50px;">
        <input id="vat2"  name="vat2" style="width: 95px;" disabled >       
        <input type="checkbox" id="vExtra" name="extramode" value="extra">
        <label id="vExtralab" for="txtPeriod" style="color: red; font-size: 14px;">Extra</label>
        <br>
        <label for="txtPeriod" style="margin: 0px 0px 0px 410px;">Total All</label>
        <input id="totalall" name="totalall" style="width: 152px;" disabled>
        <br>
        <label for="txtPeriod" style="color: red;">หมายเหตุ : กรุณากดปุ่ม</label> 
        <label for="txtPeriod" class="plussign" style="font-size: 20px;">+</label>   
        <label for="txtPeriod" style="color: red;">เพื่อเพิ่ม Item (หลักจากเลือกเลข Service No. แล้ว)</label> 
    </div>

    <div id="jsGrid"></div>
    <input id="sumbvat" name="sumbvat" style="margin: 0px 0px 0px 1000px;width: 150px; text-align: right" disabled="">
    <input id="sumvat" name="vattotal" style="margin: 0px 0px 0px 0px;width: 150px; text-align: right"  disabled="">
    <input id="sumamount" name="totalsum" style="margin: 0px 0px 0px 0px;width: 150px; text-align: right"  disabled="">
</section>

<script>
    var cono = "<%out.print(session.getAttribute("cono"));%>";
    var divi = "<%out.print(session.getAttribute("divi"));%>";
    var test = "<%out.print(session.getAttribute("test"));%>";
    var mode = "search";
    var user = "<%out.print(session.getAttribute("user"));%>";
    var mode = "new";
    var app = GetURLParameter('app');
    var updaterequesttype = "normal";
    var extramodecheck = false;
    var itemList = ['sss', 'xxxx'];
//-----------------------------------------------NUMBER FIELD START -----------------------------------------------
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
///---------------------------------------END OF DECIMAL FIELD------------------------------


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
                    source: itemList,
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
            editing: false,
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
                            path: "getItemfromRqNo",
                            rqnum: $("#vOrdernum").val(),
                            cono: cono,
                            divi: divi
                        },
                        async: false,
                        timeout: 60000
                    }).done(function (response) {
                        console.log(response);
                        response = $.grep(response, function (item) {
                            return(!filter.RNo || (item.RNo.indexOf(filter.RNo) > -1))
                                    && (!filter.Ritemcode || (item.Ritemcode.indexOf(filter.Ritemcode) > -1))
                                    && (!filter.Rdesciprion || (item.Rdesciprion.indexOf(filter.Rdesciprion) > -1))
                                    && (!filter.Rqty || (item.Rqty.indexOf(filter.Rqty) > -1))
                                    && (!filter.Rupprice || (item.Rupprice.indexOf(filter.Rupprice) > -1))
                                    && (!filter.Rvatpercentage || (item.Rvatpercentage.indexOf(filter.Rvatpercentage) > -1))
                                    && (!filter.Rtotalvat || (item.Rtotalvat.indexOf(filter.Rtotalvat) > -1))
                                    && (!filter.Rvat || (item.Rvat.indexOf(filter.Rvat) > -1))
                                    && (!filter.Ramount || (item.Ramount.indexOf(filter.Ramount) > -1));
                            console.log(data.resolve(response));
                        });
                        data.resolve(response);
                        console.log(response);
                        console.log("response");
                        setTimeout(function () {
                            calculateSum();
                        }, 50);
                    });
                    return data.promise();
                },
                // INSERT ITEM NEW
                insertItem: function (item) {
                    var itemcode = item.Ritemcode;
                    var desciprtion = item.Rdesciprion;
                    var qty = item.Rqty;
                    var upprice = item.Rupprice;
                    var vatpercentage = $("#vat").val();
                    var desciprtion2 = desciprtion.length;
                    var ordernum = $("#vOrdernum").val();
                    if (extramodecheck) {
                        var vatin = item.Rvat;
                    }
                    console.log("Description" + desciprtion);
                    console.log("qty" + item.Rqty);
                    if (ordernum === "") {
                        alert("please choose ordernum");
                        return;
                    }
                    if (itemcode === "") {
                        alert("Please Select an Item");
                        $("#jsGrid").jsGrid("loadData");
                        return;
                    } else if (desciprtion === "") {
                        alert("Please fill a Description");
                        $("#jsGrid").jsGrid("loadData");
                        return;
                    } else if (qty === undefined || qty === "") {
                        alert("Please fill Quantity");
                        $("#jsGrid").jsGrid("loadData");
                        return;
                    } else if (upprice === undefined || upprice === "") {
                        alert("Please Choose upprice");
                        $("#jsGrid").jsGrid("loadData");
                        return;
                    }
                    if (extramodecheck) {
                        if (vatin === undefined || vatin === "") {
                            alert("Please put VAT for extra");
                            $("#jsGrid").jsGrid("loadData");
                            return;
                        }
                    }
                    if (qty === "") {
                        qty = "0";
                    }
                    if (upprice === "") {
                        upprice = "0";
                    }
                    if (vatpercentage === "") {
                        vatpercentage = "0";
                    }
                    if (desciprtion2 > 255) {
                        alert('Description maximum 255');
                        return;
                    }
                    var totalbvat = (qty * upprice);
                    if (extramodecheck) {
                        var vatamount = vatin;
                    } else if (!extramodecheck) {
                        var vatamount = parseFloat((totalbvat * (vatpercentage / 100.00)).toFixed(2));
                    }
                    var totalamount = parseFloat((totalbvat + vatamount).toFixed(2));
                    var rqno = $("#vOrdernum").val();
                    $.ajax({
                        url: './Action',
                        type: 'GET',
                        dataType: 'json',
                        data: {
                            path: "checkITEM",
                            cono: cono,
                            divi: divi,
                            itemcode: item.Ritemcode
                        },
                        async: false
                    }).done(function (response) {
                        $.each(response, function (i, obj) {
                            var result = obj.result;
                            console.log("result" + result)
                            if (result === 'notexist') {
                                alert("Item " + item.Ritemcode + " not Found");
                                $("#jsGrid").jsGrid("loadData");
                                return;
                            } else {

                                formData = {};
                                formData.requestno = rqno;
                                formData.itemcode = itemcode;
                                formData.desciprtion = desciprtion;
                                formData.qty = qty;
                                formData.upprice = upprice;
                                formData.vatpercentage = vatpercentage;
                                formData.totalbvat = totalbvat;
                                formData.vatamount = vatamount;
                                formData.totalamount = totalamount;
                                formData.cono = cono;
                                formData.divi = divi;
                                formData.path = "insertNewItemService";
                                $.ajax({
                                    url: './Action',
                                    type: 'POST',
                                    dataType: 'json',
                                    data: formData,
                                    async: false
                                });
                                $("#jsGrid").jsGrid("loadData");
                                var timer2 = setInterval(function () {
                                    SetCalculateDetailModel();
                                }, 1000);
                                var timer3 = setInterval(function () {
                                    updaterequesttype = "special";
                                    savetheheader();
                                    clearInterval(timer3);
                                }, 1100);
                                setTimeout(function () {
                                    calculateSum();
                                }, 50);
                            }
                        })



                    })
                }
                ,
                updateItem: function (item) {
//                alert(item.RDTOTA_KGS);
//                console.log(item);
//                formData = {};
//                formData.ordernum = $("#vOrdernum").val();
//                formData.cono = cono;
//                formData.divi = divi;
//                formData.app = app;
//                formData.vat = item.Rvat;
//                formData.number = item.RNo;
//                formData.path = "updateVat";
//                $.ajax({
//                    url: './Action',
//                    type: 'POST',
//                    dataType: 'json',
//                    data: formData,
//                    async: false
//                });
//                $("#jsGrid").jsGrid("loadData");
//                var timer2 = setInterval(function () {
//                    SetCalculateDetailModel();
//                }, 1000);
//                var timer3 = setInterval(function () {
//                    updaterequesttype = "special";
//                    savetheheader();
//                    clearInterval(timer3);
//                }, 1100);
                },
                deleteItem: function (item) {
//                alert(item.RDTOTA_KGS);
//                console.log(item);
                    formData = {};
                    formData.item = item.Ritemcode;
                    formData.ordernum = $("#vOrdernum").val();
                    formData.cono = cono;
                    formData.divi = divi;
                    formData.app = app;
                    formData.number = item.RNo;
                    formData.path = "deleteSRNLine";
                    $.ajax({
                        url: './Action',
                        type: 'POST',
                        dataType: 'json',
                        data: formData,
                        async: false
                    });
                    $("#jsGrid").jsGrid("loadData");
                    var timer2 = setInterval(function () {
                        SetCalculateDetailModel();
                    }, 1000);
                    var timer3 = setInterval(function () {
                        updaterequesttype = "special";
                        savetheheader();
                        clearInterval(timer3);
                    }, 1100);
                }},
            fields: [{type: "control", width: 30,
                    itemTemplate: function (_, item) {
                        if (item.IsTotal)
                            return "";
                        return jsGrid.fields.control.prototype.itemTemplate.apply(this, arguments);
                    }},
                {title: "No.", name: "RNo", css: "limitext", type: "text", editing: false, align: "center", inserting: false, width: 30},
//            {title: "Item Code", name: "Ritemcode", css: "limitext", type: "text", editing: false, align: "left", width: 200
//                , insertTemplate: function (value) {
//                    // Create an input element with a datalist for insertion
//                    var reitem = this._insertAuto = $input = $("<input>").attr("type", "text").attr("list", "itemList");
//                    var $datalist = $("<datalist>").attr("id", "itemList");
//                    // Add options to the datalist
//                    var options = [];
//                    $.ajax({
//                        url: './Action',
//                        type: 'GET',
//                        dataType: 'json',
//                        data: {
//                            path: "getitemlist",
//                            cono: cono},
//                        async: false
//                    }).done(function (response) {
//                        console.log(response);
//                        $('#itemlist').empty().append('<option value="" selected="selected">Select Year!</option>');
//                        $.each(response, function (i, obj) {
//                            options.push(obj.itemlist);
//                        });
//                    });
//                    for (var i = 0; i < options.length; i++) {
//                        $datalist.append($("<option>").attr("value", options[i]));
//                    }
//                    return $("<div>").append($input).append($datalist);
//                }, insertValue: function () {
//                    var revalue = this._insertAuto.val();
//                    return revalue;
//                }
////                filterTemplate: function (value) {
////                    // Create an input element with a datalist for insertion
////                    var reitem = this._insertAuto = $input = $("<input>").attr("type", "text").attr("list", "itemList");
////                    var $datalist = $("<datalist>").attr("id", "itemList");
////                    // Add options to the datalist
////                    var options = [];
////                    $.ajax({
////                        url: './Action',
////                        type: 'GET',
////                        dataType: 'json',
////                        data: {
////                            path: "getitemlist",
////                            cono: cono},
////                        async: false
////                    }).done(function (response) {
////                        console.log(response);
//////                        warehouse = response;
////                        $('#itemlist').empty().append('<option value="" selected="selected">Select Year!</option>');
////                        $.each(response, function (i, obj) {
////                            options.push(obj.itemlist);
////                        });
////                    });
////                    for (var i = 0; i < options.length; i++) {
////                        $datalist.append($("<option>").attr("value", options[i]));
////                    }
////                    return $("<div>").append($input).append($datalist);
////                }
////                , filterValue: function () {
////                    var revalue = this._insertAuto.val();
////                    return revalue;
////                }
//            },
                {title: "Item Code", name: "Ritemcode", css: "limitext", type: "myTagField", editing: false, align: "left", width: 75},
                {title: "Description", name: "Rdesciprion", css: "limitext", type: "text", editing: false, align: "left", width: 100},
                {title: "Qty", name: "Rqty", css: "limitext", type: "decimal", editing: false, align: "right", width: 100},
                {title: "Up Price", name: "Rupprice", css: "limitext", type: "decimal", editing: false, align: "right", width: 100},
                {title: "Vat(%)", name: "Rvatpercentage", css: "limitext", type: "decimal", editing: false, inserting: false, align: "right", width: 100},
                {title: "Tota.(B.Vat)", name: "Rtotalvat", css: "limitext", type: "decimal", editing: false, inserting: false, align: "right", width: 100},
                {title: "Vat", name: "Rvat", css: "limitext", type: "decimal", editing: true, align: "right", inserting: false, width: 100},
                {title: "Amount", name: "Ramount", css: "limitext", type: "decimal", editing: false, align: "right", inserting: false, width: 100}]});
    });

    $("#vSearch").click(function () {
        $("#jsGrid").jsGrid("loadData");
    });
    $("#vPaymentRQ").click(function () {
        window.open("?page=PRV&app=" + app, "_blank");
    });
    $("#vAddRQ").click(function AddRQpress() {
//        alert('test');
        window.open("?page=PRVform", "_blank");
    });
    $("#vSave").click(function () {
        SetCalculateDetailModel();
        CheckDataToSave();
        modechangecheck();
    }

    );
    $("#vCustomeridlab").hide();
    $("#vCustomer").hide();
    $("#vAdd").hide();
    $("#vDeletecustomer").hide();
    $("#costcenter").select2();
    $("#supplier").select2();

    $("#vRefresh").click(function refresh() {
        getOrdernum();
        Reset();
    });
    $("#vCancel").click(function () {
        Reset();
        modechangecheck();
    });
    $("#vSubmit").click(function () {
        SetCalculateDetailModel();
        UpdateSRNumbers();
        $("#vOrdernum").prop("disabled", true);
        $("#vRefresh").prop("disabled", true);
        var ordernum = encodeURIComponent($("#vOrdernum").val());
        if (app === "ERN") {
            var url = "Report?SrnNumber=" + ordernum + "&report=Ernform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        } else if (app === "NEN") {
            var url = "Report?NENNumber=" + ordernum + "&report=Nenform" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
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
                            path: "changeSRNstatus",
                            EPRH_PHNO: $("#vOrdernum").val(),
                            cono: cono,
                            divi: divi,
                            status: "50"
                        },
                        async: false,
                        timeout: 60000
                    }).done(function (response) {
//                        var timer4 = setInterval(function () {
                        var ordernum = $("#vOrdernum").val();
//                            console.log(response);
                        $("#vOrdernum").prop("disabled", false);
                        $("#vRefresh").prop("disabled", false);
                        alert(ordernum + " has been submitted");
                        Reset();
//                        }, 1000);
                    });
                    window.open("?page=PRV&app=" + app, "_blank");
                } else {
                    $("#vOrdernum").prop("disabled", false);
                    $("#vRefresh").prop("disabled", false);
//                    $("#vRefresh").prop("disabled", false);
                }

            }
        }, 1000);
    });
    $("#vCancelMode").click(function () {
        if (window.confirm('You about about to Cancel Order: ' + $("#vOrdernum").val())) {
            $.ajax({
                url: './Action',
                type: 'GET',
                dataType: 'text',
                data: {
                    path: "changeSRNstatus",
                    cono: cono,
                    divi: divi,
                    EPRH_PHNO: $("#vOrdernum").val(),
                    status: "99"
                },
                async: false,
                timeout: 60000
            }).done(function (response) {
                var ordernum = $("#vOrdernum").val();
                console.log(response);
                alert(ordernum + " has been canceled");
                Reset();
            });
        }
    });
    function calculateSum() {
        console.log("testcalculatesum");
        var totalbvat = 0;
        var totalvat = 0;
        var totalamount = 0;
        var jsGridData = $("#jsGrid").jsGrid("option", "data");
        console.log(jsGridData);
        for (var i = 0; i < jsGridData.length; i++) {
            totalbvat += parseFloat(jsGridData[i].Rtotalvat);
            var formattedtotalbvat = totalbvat.toFixed(2);
            totalvat += parseFloat(jsGridData[i].Rvat);
            var formattedtotalvat = totalvat.toFixed(2);
            totalamount += parseFloat(jsGridData[i].Ramount);
            var formattedtotalamount = totalamount.toFixed(2);
        }
        console.log(formattedtotalbvat);
        console.log(formattedtotalvat);
        console.log(formattedtotalamount);
        // Update the input field with the sum
        $("#sumbvat").val(formattedtotalbvat);
        $("#sumvat").val(formattedtotalvat);
        $("#sumamount").val(formattedtotalamount);
    }

    $("#vExtra").change(function () {
        var testextra = $('input[name="extramode"]').is(":checked");
        const columns = $("#jsGrid").jsGrid("option", "fields");
        console.log(testextra);
        if (testextra) {
//            $("#totalall").prop("disabled", false);
            $("#vat").val("7");
            extramodecheck = true;
            columns[8].inserting = true;
//            columns[8].editing = false;

        } else if (!testextra) {
            $("#totalall").prop("disabled", true);
            extramodecheck = false;
            columns[8].inserting = false;
            $("#vat").val("0");
        }
        modechangecheck();
        SetCalculateDetailModel()
    });
    $("#vOrdernum").change(function () {
        var ordernum = $("#vOrdernum").val();
        console.log(ordernum);
//        $("#vService").val(ordernum);
        if (ordernum === "") {
            Reset();
        }
        serviceChange();
        modechangecheck();
    });
    $("#vService").change();
    $("#costcenter").change(function () {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "get3prh",
                supplierdata: $("#costcenter").val(),
                cono: cono,
                divi: divi
            },
            async: false
        }).done(function (response) {
            console.log(response);
            $.each(response, function (i, obj) {
                $("#prh_bu").val(obj.prh_bu);
                $("#prh_fac").val(obj.prh_fac);
                $("#prh_whs").val(obj.prh_whs);
            });
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
//        console.log(response);
//        warehouse = response;
//        $('#costcenterlist').empty().append('<option value="" selected="selected">Select Costcenter!</option>');
//        $.each(response, function (i, obj) {
//            var div_data = "<option>" + obj.vAutofill + "</option>";
//            $(div_data).appendTo('#costcenterlist');
//        });
//        
        $('#costcenter').empty().append('<option value="" selected="selected">Select Costcenter!</option>');
        $.each(response, function (i, obj) {
            var div_data = "<option value=" + obj.costcode + " >" + obj.vAutofill + "</option>";
            $(div_data).appendTo('#costcenter');
        });
    });
    function getsupplier() {
//GET DATA FOR Supplier
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
            $('#supplier').empty().append('<option value="" selected="selected">Select Supplier!</option>');
            $.each(response, function (i, obj) {
                var div_data = "<option value=" + obj.suppliercode + " >" + obj.supplierlist + "</option>";
                $(div_data).appendTo('#supplier');
            });
//            $('#supplierlist').empty().append('<option value="" selected="selected">Select Year!</option>');
//            $.each(response, function (i, obj) {
//                var div_data = "<option>" + obj.supplierlist + "</option>";
//                $(div_data).appendTo('#supplierlist');
//            });
        });
    }
//Get Order Number
    function getOrdernum() {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getordernum",
                user: user,
                app: app,
                cono: cono,
                divi: divi
            },
            async: false
        }).done(function (response) {
            console.log(response);
            warehouse = response;
            $('#vOrdernum').empty().append('<option value="" selected="selected">Select Ordernum</option>');
            $.each(response, function (i, obj) {
                var div_data = "<option value=" + obj.vOrderdata + ">" + obj.vOrdernum + "</option>";
                $(div_data).appendTo('#vOrdernum');
            });
        });
    }

//Reset everything after submit,update or print
    function Reset() {


        displayCurrentDate();
        getOrdernum();
        $("#vService").val("");
//        $("#vRQDate").val("");
        $("#vRequireDate").val("");
        $("#ContractNo").val("");
        $("#Purpose").val("");
        $("#costcenter").val("").trigger('change.select2');
        ;
        $("#prh_fac").val("");
        $("#prh_bu").val("");
        $("#prh_whs").val("");
        $("#supplier").val("").trigger('change.select2');
        ;
        $("#supinv").val("");
        $("#remark").val("");
        $("#invdate").val("");
        $("#total").val("");
        $("#discount").val("0");
        $("#vat").val("0");
        $("#vat2").val("");
        $("#totalall").val("");
        $("#sumbvat").val("");
        $("#sumvat").val("");
        $("#sumamount").val("");
        mode = "new";
        $("#vExtra").prop("checked", false)
        clearTable();
    }
    ;
//Use to clear table
    var jsGridInstance = $("#jsGrid").jsGrid({});
    function clearTable() {
        jsGridInstance.jsGrid("option", "data", []);
        jsGridInstance.jsGrid("refresh");
    }

//Use to change or check the mode of current action such as Editing or making new order
    function modechangecheck() {
        var ordernum = $("#vOrdernum").val();
        if (ordernum === '') {
            mode = "new";
//            Reset();
            $("#vSubmit").prop("disabled", true);
            $("#vCancelMode").prop("disabled", true);
            $("#sumbvat").val("");
            $("#sumvat").val("");
            $("#sumamount").val("");
            $("#vSave").text("Save Data");
            $("#jsGrid").jsGrid({
                // your jsGrid configuration options
                onRefreshing: function (args) {
                    args.cancel = true;
                    args.grid.inserting = false;
                }
            });
        } else {
            mode = "edit";
            $("#jsGrid").jsGrid("loadData");
            $("#vSubmit").prop("disabled", false);
            $("#vCancelMode").prop("disabled", false);
            $("#vSave").text("Update Data");
            $("#jsGrid").jsGrid({
                // your jsGrid configuration options
                onRefreshing: function (args) {
                    args.cancel = true;
                    args.grid.inserting = true;
                }
            });
            setTimeout(function () {
                SetCalculateDetailModel();
            }, 10); // Delay of 1000 milliseconds (1 seconds)
        }
    }
    ;
//Check if data is legit and be able to save
    function CheckDataToSave() {
        console.log("checkdtatosave");
        //Request Date Check
        var RQdate = $("#vRQDate").val();
        if (RQdate === "") {
            alert('Please choose Request Date');
            return;
        }
        //Require Date Check
        var Require = $("#vRequireDate").val();
        if (Require === "") {
            alert('Please choose Require Date');
            return;
        }
        //Invoice Date
        var invdate = $("#invdate").val();
        if (invdate === "") {
            alert('Please choose Invoice Date');
            return;
        }
        //PURPOSE CHECK
        var purpose = $("#Purpose").val();
        var purpose2 = purpose.length;
        if (purpose2 > 255) {
            alert('Please Check Purpose To Maximum 255 digit');
            return;
        } else if (purpose === "") {
            alert('Please insert purpose');
            return;
        }
        ;
        //Sup invoice
        var supinvoice = $("#supinv").val();
        var supinvoice2 = supinvoice.length;
        if (supinvoice2 > 15) {
            alert('Please Check Maximum Invoice No 15 digit');
            return;
        } else if (supinvoice === "") {
            alert('Please choose Sup Invoice');
            return;
        }
        //remark
        var remark = $("#remark").val();
        var remark2 = remark.length;
        if (remark2 > 20) {
            alert('Please Check Maximum Remark No 20 digit');
            return;
        }
        //Cost center
        var costcenter = $("#costcenter").val();
        if (costcenter === "") {
            alert('Please Check Costcenter');
            return;
        }
        //Supplier
        var supplier = $("#supplier").val();
        if (supplier === "") {
            alert('Please Check Supplier');
            return;
        }
        //discount
        var discount = $("#discount").val();
        if (discount === "") {
            alert('Please Check Discount amt  Number Only');
            return;
        }
        //discount
        var vat = $("#vat").val();
        if (vat === "") {
            alert('Please Check Vat');
            return;
        }
//        var Costcenter = response;
        var check = false;
//        $.ajax({
//            url: './Action',
//            type: 'GET',
//            dataType: 'json',
//            data: {
//                path: "checkCostcenter",
//                divi: divi,
//                cono: cono,
//                costcenter: $("#costcenter").val()
//            },
//            async: false
//        }).done(function (response) {
//            console.log("Response checkdup" + response);
////            Costcenter = response;
////            console.log(checkdup);
//            $.each(response, function (i, obj) {
//                var result = obj.Check;
//                console.log("Result of costcenter" + result);
//                if (result === "0") {
//                    alert('Costcenter Does not existed!');
//                    check = true;
//                    return;
//                }
//            });
//        });
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

        //CONFIRM TO INITIATE
        var result = confirm("Would You Like to Save Store Request Note?");
        if (result === true) {
            savetheheader();
        } else {
        }
    }



    function savetheheader() {
        var ordernum = "";
        var currentdate = $("#vDate").val();
        var EPRH_COCE = $("#costcenter").val();
        var EPRH_SUNO = $("#supplier").val();
        var EPRH_PURPOS = $("#Purpose").val();
        var EPRH_RQSDT = $("#vRQDate").val();
        var EPRH_RQRDT = $("#vRequireDate").val();
        var EPRH_BU = $("#prh_fac").val();
        var EPRH_APTNO = $("#ContractNo").val();
        var EPRH_REM = $("#remark").val();
        var EPRH_REQBY = $("#reqby").val();
        var EPRH_DISC = $("#discount").val();
        var EPRH_VTCD = $("#vat").val();
        var EPRH_VTAMT = $("#vat2").val();
        var EPRH_FAC = $("#prh_bu").val();
        var EPRH_WHS = $("#prh_whs").val();
        var EPRH_INVSU = $("#supinv").val();
        var EPRH_INVDT = $("#invdate").val();
        var EPRH_STAT = "20";
        var EPRH_TOTAL = $("#total").val();
        var ServiceRequestNo = $("#vOrdernum").val();
        var checkSRNNo;
        var checkdup;
        if (EPRH_DISC === "") {
            EPRH_DISC = "0";
        }
        if (EPRH_VTCD === "") {
            EPRH_VTCD = "0";
        }
        if (EPRH_VTCD === "") {
            EPRH_VTCD = "0";
        }
        if (EPRH_VTAMT === "") {
            EPRH_VTAMT = "0";
        }
        if (EPRH_TOTAL === "") {
            EPRH_TOTAL = "0";
        }
        if (ServiceRequestNo === "") {
            checkSRNNo = "0";
        } else {
            checkSRNNo = ServiceRequestNo;
        }
        //GET DATA FOR ITEM
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "CheckDuplicateSupInvoice",
                sup: EPRH_SUNO,
                inv: EPRH_INVSU,
                srn: checkSRNNo,
                cono: cono,
                divi: divi
            },
            async: false
        }).done(function (response) {
            console.log("Response checkdup" + response);
            checkdup = response;
            console.log(checkdup);
        });
        if (String(checkdup) === "true") {
            console.log("checkdb is true");
            alert("Supplier and Invoice is duplicate, Please change.");
            return;
        }
        //WHEN IT'S NOT DUPLICATED
        else {

            if ($("#vOrdernum").val() === "")
            {
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "SaveSrnForm",
                        currentdate: currentdate,
                        EPRH_COCE: EPRH_COCE,
                        EPRH_PURPOS: EPRH_PURPOS,
                        EPRH_RQSDT: EPRH_RQSDT,
                        EPRH_RQRDT: EPRH_RQRDT,
                        EPRH_APTNO: EPRH_APTNO,
                        EPRH_SUNO: EPRH_SUNO,
                        EPRH_BU: EPRH_BU,
                        EPRH_REM: EPRH_REM,
                        EPRH_REQBY: EPRH_REQBY,
                        EPRH_DISC: EPRH_DISC,
                        EPRH_VTCD: EPRH_VTCD,
                        EPRH_VTAMT: EPRH_VTAMT,
                        EPRH_FAC: EPRH_FAC,
                        EPRH_WHS: EPRH_WHS,
                        EPRH_INVSU: EPRH_INVSU,
                        EPRH_INVDT: EPRH_INVDT,
                        EPRH_STAT: EPRH_STAT,
                        ServiceRequestNo: "",
                        EPRH_TOTAL: EPRH_TOTAL,
                        app: app,
                        cono: cono,
                        divi: divi
                    },
                    async: false
                }).done(function (response) {
                    $.each(response, function (i, obj) {
                        var result = obj.Result;
                        alert(result);
                        Reset();
                        console.log("response" + response[1]);
                        console.log("result" + result);
                        $("#vOrdernum").val(obj.orderno);
                        serviceChange();
                        modechangecheck();
                    });
//                    getOrdernum();

                });
            } else {

                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "SaveSrnForm",
                        currentdate: currentdate,
                        EPRH_COCE: EPRH_COCE,
                        EPRH_PURPOS: EPRH_PURPOS,
                        EPRH_RQSDT: EPRH_RQSDT,
                        EPRH_RQRDT: EPRH_RQRDT,
                        EPRH_APTNO: EPRH_APTNO,
                        EPRH_SUNO: EPRH_SUNO,
                        EPRH_BU: EPRH_BU,
                        EPRH_REM: EPRH_REM,
                        EPRH_REQBY: EPRH_REQBY,
                        EPRH_DISC: EPRH_DISC,
                        EPRH_VTCD: EPRH_VTCD,
                        EPRH_VTAMT: EPRH_VTAMT,
                        EPRH_FAC: EPRH_FAC,
                        EPRH_WHS: EPRH_WHS,
                        EPRH_INVSU: EPRH_INVSU,
                        EPRH_INVDT: EPRH_INVDT,
                        EPRH_STAT: EPRH_STAT,
                        ServiceRequestNo: ServiceRequestNo,
                        EPRH_TOTAL: EPRH_TOTAL,
                        app: app,
                        cono: cono,
                        divi: divi
                    },
                    async: false
                }).done(function (response) {
                    $.each(response, function (i, obj) {
                        var result = obj.Result;
                        if (updaterequesttype === "normal") {
                            alert(result);
                            Reset();
                        } else {
                            updaterequesttype = "normal";
                        }
                        console.log("response" + response[1]);
                        console.log("result" + result);
                        $("#vOrdernum").val(obj.orderno);
                        serviceChange();
                        modechangecheck();
                    });
                });
            }


        }
    }

//Show current date
    function displayCurrentDate() {
        var currentDate = new Date();
        var month = String(currentDate.getMonth() + 1).padStart(2, '0');
        var day = String(currentDate.getDate()).padStart(2, '0');
        var year = currentDate.getFullYear();
        var formattedDate = year + '-' + month + '-' + day;
//        var formattedDate = '2024' + '-' + '01' + '-' + '20';
        $("#vDate").val(formattedDate);
        $("#vRQDate").val(formattedDate);
    }

//When change Service or ordernumber
    function serviceChange() {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getOrderDataServices",
                serviceno: $("#vOrdernum").val(),
                cono: cono,
                divi: divi
            },
            async: false
        }).done(function (response) {
            console.log("test");
            console.log(response);
            $.each(response, function (i, obj) {
//                $("#supplierlist").val(obj.supplierlist);
                $("#costcenter").val(obj.costcenter).trigger('change.select2');
                $("#supplier").val(obj.supplier).trigger('change.select2');
                $("#Purpose").val(obj.Purpose);
                $("#vRequireDate").val(obj.vRequireDate);
                $("#vRQDate").val(obj.vRQDate);
                $("#invdate").val(obj.invdate);
                $("#ContractNo").val(obj.ContractNo);
                $("#prh_fac").val(obj.prh_fac);
                $("#remark").val(obj.remark);
                $("#reqby").val(obj.reqby);
                $("#discount").val(obj.discount);
                $("#vat").val(obj.vat);
                $("#vat2").val(obj.vat2);
                $("#prh_bu").val(obj.prh_bu);
                $("#prh_whs").val(obj.prh_whs);
                $("#supinv").val(obj.supinv);
                $("#totalall").val(obj.totalall);
                $("#vService").val(obj.vService);
                ;
            });
        });
    }
    ;
//Calculate data in the table for sum and other stuff
    function SetCalculateDetailModel() {
        var gridData = $("#jsGrid").jsGrid("option", "data");
        var TotalAmountAllReturn = 0;
        var TotalAmountReturn = 0;
        var TotalVatAmountReturn = 0;
        var vatper = 0;
        for (var i = 0; i < gridData.length; i++) {
            var Qty = parseFloat(gridData[i].Rqty);
            var Upprice = parseFloat(gridData[i].Rupprice);
            vatper = parseFloat(gridData[i].Rvatpercentage);
            var totalbvat = (Qty * Upprice);
            var vatamount = totalbvat * (vatper / 100.00);
            var totalamount = totalbvat + vatamount;
            TotalVatAmountReturn += parseFloat(vatamount.toFixed(2));
            TotalAmountReturn += parseFloat(totalbvat.toFixed(2));
            TotalAmountAllReturn += parseFloat(totalamount.toFixed(2));
        }

        var DiscountAmount = parseFloat($("#discount").val());
        var VatPerTidTee = parseFloat($("#vat").val());
        $("#total").val(TotalAmountReturn.toFixed(2));
        var txttotalall1 = parseFloat(TotalAmountAllReturn - DiscountAmount);
        $("#totalall").val(txttotalall1.toFixed(2));
//
        var totalTidtee = parseFloat($("#total").val());
        var VatTotalTidtee = (((totalTidtee - DiscountAmount) * VatPerTidTee) / 100);
        if (extramodecheck) {
            $("#vat2").val($("#sumvat").val());
            $("#totalall").val($("#sumamount").val() - DiscountAmount);
        } else {
            $("#vat2").val(VatTotalTidtee.toFixed(2));
            $("#totalall").val(((totalTidtee - DiscountAmount) + VatTotalTidtee).toFixed(2));
        }



    }
    function UpdateSRNumbers() {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "UpdateSRNumbers",
                cono: cono,
                divi: divi,
                rqnum: $("#vOrdernum").val(),
                total: $("#total").val(),
                discount: $("#discount").val(),
                vat: $("#vat").val(),
                vatamt: $("#vat2").val()
            },
            async: false
        }).done(function (response) {
            console.log("Response" + response);
        });
    }
    function GetURLParameter(sParam)
    {
        var sPageURL = window.location.search.substring(1);
        var sURLVariables = sPageURL.split('&');
        for (var i = 0; i < sURLVariables.length; i++)
        {
            var sParameterName = sURLVariables[i].split('=');
            if (sParameterName[0] === sParam)
            {
                return sParameterName[1];
            }
        }
    }

    console.log(app);
    if (app === "ERN") {
        $("#vappname").text("Expense Receipt Note(ERN)");
    } else if (app === "NEN") {
        $("#vappname").text("Non Expense Note(NEN)");
    }


    $.ajax({
        url: './Action',
        type: 'GET',
        dataType: 'json',
        data: {
            path: "getitemlist",
            cono: cono},
        async: false
    }).done(function (response) {
        itemList.splice(0, itemList.length);
        $.each(response, function (i, obj) {
            itemList.push(response[i].itemlist);
        });
        console.log("test" + itemList);
//        console.log(response);
//        $('#itemlist').empty().append('<option value="" selected="selected">Select Year!</option>');
//        $.each(response, function (i, obj) {
//            options.push(obj.itemlist);
    });

    Reset();
    getOrdernum();
    $("#reqby").val(user);
    getsupplier();
</script>  
