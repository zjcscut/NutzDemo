<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="${pageContext.request.contextPath}"
       scope="request"/>
<%--<script type="text/javascript">--%>
<%--function goBack() {--%>
<%--$('#customerPersonerBack').click();--%>
<%--}--%>
<%--</script>--%>
<div class="pageContent">
    <a style="display: none;" id="customerPersonerBack"
       rel="customer_edit" target="dialog" mask="true" width="640" maxable="false" minable="false" resizable="false"
       height="500"
       title="编辑用户信息" maxable="false"></a>

    <form method="post" action="<%=request.getContextPath()%>/userManage/relation/edit"
          class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">

            <p>
                <label>姓名：</label>
                <input name="name" type="text" value="${pre.name}"/>
            </p>
            <p>
                <label>关系：</label>
                <input name="relation" type="text" value="${pre.relation}"/>
            </p>

            <input name="id" value="${pre.id}" hidden>

        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保 存</button>
                        </div>
                    </div>
                </li>

                <%--<li>--%>
                <%--<div class="buttonActive">--%>
                <%--<div class="buttonContent">--%>
                <%--<button type="button" onclick="goBack();">上一步</button>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</li>--%>

                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>

    </form>
</div>


