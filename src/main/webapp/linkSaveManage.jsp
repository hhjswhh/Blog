<%--
  Created by IntelliJ IDEA.
  User: 77239
  Date: 2017/2/22/0022
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function notEmpty() {
        var name = document.getElementById("name").value;
        var url = document.getElementById("url").value;
        if (name == null || name == "") {
            Materialize.toast("链接名不能为空", 2000, "rounded");
            return false;
        }
        if (url == null || url == "") {
            Materialize.toast("URL不能为空", 2000, "rounded");
            return false;
        }
        return true;
    }
</script>
<div class="row">
    <div class="col s12">
        <div class="card-panel">
            <form action="linkManage?action=save&linkId=${link.linkId}" method="post"
                  onsubmit="return notEmpty()">
                <div class="input-field col s6">
                    <input id="name" name="name" type="text" class="validate" value="${link.linkName}">
                    <label for="name">链接名</label>
                </div>
                <div class="input-field col s6">
                    <input id="url" name="url" type="text" class="validate" value="${link.url}">
                    <label for="url">URL</label>
                </div>
                <br><br><br><br>
                <div class="center">
                    <button class="btn waves-effect waves-light green" type="submit">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>