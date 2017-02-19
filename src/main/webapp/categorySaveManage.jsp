<%--
  Created by IntelliJ IDEA.
  User: 77239
  Date: 2017/2/16/0016
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    function notEmpty() {
        var name = document.getElementById("name").value;
        if (name == null || name == "") {
            Materialize.toast("类别名称不能为空", 2000, "rounded");
            return false;
        }
        return true;
    }
</script>
<div class="row">
    <div class="col s12">
        <div class="card-panel">
            <form action="categoryManage?action=save&categoryId=${category.categoryId}" method="post"
                  onsubmit="return notEmpty()">
                <div class="input-field col s6">
                    <input id="name" name="name" type="text" class="validate" value="${category.categoryName}">
                    <label for="name">类别名</label>
                </div>
                <br>
                <button class="btn waves-effect waves-light green" type="submit">保存</button>
            </form>
        </div>
    </div>
</div>