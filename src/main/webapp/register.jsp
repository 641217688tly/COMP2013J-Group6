<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>register</title>
    <link href="css/register.css" rel="stylesheet">

</head>
<body>

<div class="form-div">
    <div class="reg-content">
        <h1><b>Welcome to register</b></h1>
        <span>Already have an account?</span> <a href="login.jsp">Log in</a>
    </div>
    <form id="reg-form" action="registerServlet" method="post">

        <table>

            <tr>
                <td>User Name</td>
                <td class="inputs">
                    <input name="username" type="text" id="username">
                    <br>
                    <span id="username_err" class="err_msg">${register_msg}</span>
                </td>
            </tr>

            <tr>
                <td>Email</td>
                <td class="inputs">
                    <input name="email" type="email" id="email">
                    <br>
                    <span id="email_err" class="err_msg" style="display: none">邮箱格式有误</span>
                </td>
            </tr>

            <tr>
                <td>Password</td>
                <td class="inputs">
                    <input name="password" type="password" id="password">
                    <br>
                    <span id="password_err" class="err_msg" style="display: none">密码格式有误</span>
                </td>
            </tr>

        </table>

        <div class="buttons">
            <input value="register" type="submit" id="reg_btn">
        </div>
        <br class="clear">
    </form>

</div>
</body>
</html>
