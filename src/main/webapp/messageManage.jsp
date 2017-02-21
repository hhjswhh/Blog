<%--
  Created by IntelliJ IDEA.
  User: 张秦遥
  Date: 2017/2/21/0021
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col s12">
        <div class="card-panel">
            <table class="centered">
                <thead>
                <tr>
                    <th>时间</th>
                    <th>消息类别</th>
                    <th>用户</th>
                    <th>内容</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="message" items="${messages}">
                    <tr class="hoverable">
                        <td>${message.pubDate }</td>
                        <td>${message.type}</td>
                        <td>${message.user.username}</td>
                        <td>${message.content}</td>
                        <td>
                            <a class="waves-effect waves-light btn red hoverable"
                               href="messageManage?action=delete&messageId=${message.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>