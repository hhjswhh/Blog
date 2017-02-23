<%--
  Created by IntelliJ IDEA.
  User: 77239
  Date: 2017/2/6/0006
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <link href="//cdn.bootcss.com/materialize/0.98.0/css/materialize.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/materialize/0.98.0/js/materialize.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            <c:if test="${not empty error }">
            Materialize.toast("用户名或密码错误", 2000, "rounded")
            </c:if>
        });
        function check() {
            var username = $("#username").val();
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();
            if (username == "") {
                Materialize.toast("用户名不能为空", 2000, "rounded");
                return false;
            }
            if (password == "") {
                Materialize.toast("密码不能为空", 2000, "rounded");
                return false;
            }
            if (confirmPassword == "") {
                Materialize.toast("确认密码不能为空", 2000, "rounded");
                return false;
            }
            if (password != null && confirmPassword != null && password != "" && password != confirmPassword) {
                Materialize.toast("两次密码输入不一致", 2000, "rounded");
                return false;
            }
            return true;
        }
    </script>
</head>
<body class="grey lighten-4">

<div class="row">
    <div class="col s4"></div>
    <div class="col s4">
        <jsp:include page="${mainPage}"></jsp:include>
    </div>
    <div class="col s4"></div>
</div>

</body>
</html>
