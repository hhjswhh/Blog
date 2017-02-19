<%--
  Created by IntelliJ IDEA.
  User: 77239
  Date: 2017/2/16/0016
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col s12">
        <div class="card-panel">
            <div class="row center">
                <div class="col s4">名称</div>
                <div class="col s1">时间</div>
                <div class="col s7">操作</div>
            </div>
            <hr>

            <ul class="collapsible popout">
                <c:forEach var="article" items="${articles}">
                    <li>
                        <div class="collapsible-header">
                            <div class="row center">
                                <div class="col s3">
                                        ${article.title }
                                </div>
                                <div class="col s3">
                                        ${article.pubDate}
                                </div>
                                <div class="col s6">
                                    <a href="articleManage?action=preSave&articleId=${article.id}"
                                       class="waves-effect waves-light btn green hoverable">修改</a>
                                    <a class="waves-effect waves-light btn red hoverable"
                                       href="articleManage?action=delete&articleId=${article.id}">删除</a>
                                </div>
                            </div>
                        </div>
                        <div class="collapsible-body">
                            <p>${article.content}</p>
                        </div>
                    </li>
                </c:forEach>
            </ul>

            <br>
            <div class="center">
                <a href="articleManage?action=preSave" class="waves-effect waves-light btn green hoverable">写文章</a>
            </div>
        </div>
    </div>
</div>