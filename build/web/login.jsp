<%-- 
    Document   : index
    Created on : Jun 13, 2019, 4:29:51 PM
    Author     : Wattana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<style>

    input {
        outline: none;
        border: none;
    }

    button {
        outline: none !important;
        border: none;
        background: transparent;
    }
    .login-container {
        text-align: center;
    }
    .link-container {
        text-align: center;
    }

</style>
<html>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="assets/Bootstrap-3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
    <link rel="stylesheet" type="text/css" href="assets/animate/animate.css">
    <link rel="stylesheet" type="text/css" href="assets/css-hamburgers/hamburgers.min.css">
    <link rel="stylesheet" type="text/css" href="assets/animsition/css/animsition.min.css">
    <link rel="stylesheet" type="text/css" href="assets/select2/select2.min.css">
    <link rel="stylesheet" type="text/css" href="assets/util.css">
    <link rel="stylesheet" type="text/css" href="assets/main.css">
    <script type="text/javascript" src="assets/jQuery-3.3.1/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="assets/Bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="assets/animsition/js/animsition.min.js"></script>
    <script src="assets/Bootstrap-3.3.7/js/popper.js"></script>
    <script src="assets/Bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="assets/select2/select2.min.js"></script>
    <script src="assets/countdowntime/countdowntime.js"></script>
    <script src="assets/main.js"></script>
    <link rel="shortcut icon" href="images/icons/favicon.ico"/>
    <title>Login For Services Payment</title>


    <body>

        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100">
                    <form class="login100-form validate-form" action="Login" method="POST">
                        <img src="images/duck.jpg" class="center-block" width="180" height="180" alt="duck"/>
                        <br/>
                        <div class="login-container">
                            <h4>Login For Services Payment</h4>
                        </div>
                        <br/>
                        <div class="wrap-input100 validate-input">
                            <input class="input100" type="text" name="username" id="vUsername" placeholder="Username">
                        </div>

                        <div class="wrap-input100 validate-input">
                            <input class="input100" type="password" name="password" id="vPassword" placeholder="Password">
                        </div>

                        <div class="form-group">
                            <select class="form-control form-control-user" name="company" id="vCompany">
                                <option value="" selected="selected">Select Company</option>
                            </select>
                        </div>

                        <p style="color: red">${msg}</p>
                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn">Login</button>
                            </div>


                        </div>

                    </form>
                        <form>
                            <a href="ManualSRN_PRV.pdf" target="_blank"><li style="color: blue;"><u>Manual</u></li></a>
                        </form> 
<!--                        <form>
                            <a href="http://192.200.9.189:8080/UserRequest/?Destination=RequestUser"><li style="color: blue;"><u>Sign Up</u></li></a>
                        </form> -->
                        <form>
                            <a href="http://192.200.9.189:8080/UserRequest/?page=/ResetPW"><li style="color: blue;"><u>Reset Password</u></li></a>
                        </form>
<!--                        <form>
                            <a href="http://192.200.9.189:8080/UserRequest/?Destination=ChangePW"><li style="color: blue;"><u>Change Password</u></li></a>
                        </form>-->
                </div>
            </div>
        </div>

    </body>
</html>

<script>

    $(document).ready(function () {
    <% System.out.println("Path : " + request.getContextPath());%>

        $.ajax({
            url: "./Action",
            type: "GET",
            dataType: "json",
            data: {
                path: "getCompany"
            },
            success: function (getdata) {
                $.each(getdata, function (i, obj) {
                    var div_data = "<option value=" + obj.COMPANY + ">" + obj.CCCONO + " : " + obj.CCDIVI + " : " + obj.CCCONM + "</option>";
                    //console.log(div_data)
                    $(div_data).appendTo('#vCompany');
                });
            }
        });

    });


////GET AUTH FOR ITEM
//    $.ajax({
//        url: './Action',
//        type: 'GET',
//        dataType: 'json',
//        data: {
//            path: "getItemData"
//        },
//        async: false
//    }).done(function (response) {
//        console.log(response);
//        warehouse = response;
//        $('#costcenterlist').empty().append('<option value="" selected="selected">Select Year!</option>');
//        $.each(response, function (i, obj) {
//            var div_data = "<option>" + obj.vAutofill + "</option>";
//            $(div_data).appendTo('#costcenterlist');
//        });
//    });

</script>
