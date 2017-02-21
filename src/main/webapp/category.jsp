<%--
  Created by IntelliJ IDEA.
  User: 77239
  Date: 2017/2/18/0018
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col s10">
        <h1>分类</h1>
        <ul class="collapsible popout">
            <c:forEach var="category" items="${categories}">
                <li>
                    <div class="collapsible-header <c:if test='${categoryId==category.id}'>active</c:if> ">${category.name}<span class="badge">${category.count}</span></div>
                    <div class="collapsible-body">
                        <table>
                            <tbody>
                            <c:forEach var="article" items="${articlesList[category.id]}">
                                <tr>
                                    <td>『${article.pubDate }』</td>
                                    <td><a href="article?articleId=${article.id}">${article.title}</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
