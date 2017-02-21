<%--
  Created by IntelliJ IDEA.
  User: 张秦遥
  Date: 2017/2/20/0020
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col s10">
        <div class="card-panel hoverable">
            <c:choose>
                <c:when test="${curUser==null }">
                    <div class="center"><p>登陆后可评论</p></div>
                </c:when>
                <c:otherwise>
                    <form action="message?action=save&type=${type}&pid=${pid}&articleId=${articleId}" method="post">
                        <div class="input-field col s12">
                            <textarea id="comment" name="comment" class="materialize-textarea"></textarea>
                            <label for="comment">评论</label>
                        </div>
                        <div class="center">
                            <button class="btn waves-effect waves-light green hoverable" type="submit" name="action">发布评论</button>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>