<%-- 
    Document   : ep01r002
    Created on : Jul 11, 2019, 9:01:56 AM
    Author     : ACHARD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<style>

    .wrap-login100 {
        width: 390px;
        background: #fff;
        border-radius: 10px;
        overflow: hidden;
        padding: 30px 55px 33px 55px;
        box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -moz-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -webkit-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -o-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -ms-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
    }

    .form-label-group {
        text-align: -webkit-left;
    }

    .container {
        /*width: 970px;*/
        text-align: -webkit-center;
    }

</style>

<div class="container">
    <div class="wrap-login100" >

        <form id="detailsForm" class="login100-form validate-form" target="_blank" action="Report" method="GET">
            <h3>รายการตารางวันลูกค้า</h3>
            <br>
            <div class="form-label-group">

                <label for="date" id="vDatelab" style="">Start date:</label>
                <input type="date" id="vStartDate" name="startdate" style="">
                <br>
                <label for="date" id="vDatelab" style="">End date:</label>
                <input type="date" id="vEndDate" name="enddate" style="">
                <br>
                <label for="txtPeriod">TYPE: </label>
                <select style="width: 100px" name="Type"  id="Type" required>
                    <option value="" selected="selected">Select Type</option>
                    <option value="ERNNEN" >ERN & NEN</option>
                    <option value="GRN" >GRN</option>
                </select>
                <!--<label for="txtPeriod">Year :</label>-->
                <!--<select style="width: 75px" name="Year"  id="vYear" required>-->
                <!--<option value="" selected="selected">Select Year!</option>-->
                <!--</select>-->
                <!--<label for="txtPeriod">Month :</label>-->
                <!--                <select style="width: 50px" name="Month"  id="vMonth" required>
                                    <option value="" selected="selected">Select Month!</option>
                                </select>-->

            </div>
        </form>
        <form align="center">
            <hr class="my-4">
<!--            <button class="btn btn-primary mb-2" style="color:#FFFFFF;" form="detailsForm" name="report" value="customer_calender" type="submit">เรียกรายงาน</button>

            <button class="btn btn-primary mb-2" style="color:#FFFFFF;" form="detailsForm" name="report" value="customer_calenderXLSX" type="submit">เรียกรายงาน Excel</button>-->
            <button class="btn btn-primary mb-2" style="color:#FFFFFF;" form="detailsForm" name="report" value="Summary" type="submit">เรียกรายงาน</button>
            <button class="btn btn-primary mb-2" style="color:#FFFFFF;" form="detailsForm" name="report" value="SummaryXLSX" type="submit">เรียกรายงาน Excel</button>
        </form>

    </div>
</div>
<script>

    $("#vInvround").change(function () {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getYearHeader",
                invround: $("#vInvround").val()
            },
            async: false
        }).done(function (response) {
            console.log(response);
            warehouse = response;
            $('#vYear').empty().append('<option value="" selected="selected">Select Year!!</option>');
            $.each(response, function (i, obj) {

                var div_data = "<option value=" + obj.vYear + ">" + obj.vYear + "</option>";
                $(div_data).appendTo('#vYear');
            });
        });
    });
    $("#vYear").change(function () {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getMonthMaster",
                year: $("#vYear").val(),
                invround: $("#vInvround").val()
            },
            async: false
        }).done(function (response) {
            console.log(response);
            month = response;

            $('#vMonth').empty().append('<option value="" selected="selected">Select Month!</option>');
            $.each(response, function (i, obj) {
                var div_data = "<option value=" + obj.vMonth + ">" + obj.vMonth + "</option>";
                $(div_data).appendTo('#vMonth');
            });
//            $("#jsGrid").jsGrid("refresh");
//            $("#jsGrid").jsGrid("reset");
//            $("#jsGrid").jsGrid("render").done(function () {
//                console.log("rendering completed and data loaded");
//            });
        });
    })
</script>
