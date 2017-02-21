<%--
  Created by IntelliJ IDEA.
  User: 77239
  Date: 2017/2/19/0019
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col s10">
        <div class="card hoverable">
            <div class="card-content">
                <span class="card-title">${article.title}</span>
                『${article.pubDate }』
                <span>|</span>
                <a href="/category?categoryId=${article.category.categoryId}">${article.category.categoryName}</a>
                <span>|</span>
                ${article.clicks + 1}&nbspViews
                <span>|</span>
                author：<a onclick="return false;" style="cursor: pointer;">${article.user.username}</a>
                <p>${article.content}</p>
            </div>
            <div class="card-action">
                上一篇：<a href="article?articleId=${preArticle.id}">${preArticle.title}</a>
                下一篇：<a href="article?articleId=${nextArticle.id}">${nextArticle.title}</a>
                转载注明：<a href="about">${article.user.username}</a>&gt;&gt;<a href="article?articleId=${article.id}">${article.title}</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="messageForm.jsp"></jsp:include>
<jsp:include page="messageList.jsp"></jsp:include>
