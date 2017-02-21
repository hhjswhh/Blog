<%--
  Created by IntelliJ IDEA.
  User: 张秦遥
  Date: 2017/2/20/0020
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var options = [
        {selector: '#comment', offset: 0, callback: function(el) {
            Materialize.showStaggeredList($(el));
        } },
    ];
    Materialize.scrollFire(options);
</script>
<div class="row">
    <div class="col s10">
        <div class="card-panel hoverable">
            <ul id="comment" class="collection">
                <c:forEach var="message" items="${messages}">
                    <li class="collection-item avatar" >
                        <img src="images/user/${message.user.image}" class="circle">
                        <span class="title">${message.user.username}</span>
                        <p>${message.content}</p>
                        <p class="secondary-content">${message.pubDate}</p>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

