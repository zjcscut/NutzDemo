<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"
       scope="request"/>
<form id="pagerForm" action="${root}/userManage/relation/search">
    <input type="hidden" name="currentPage" value="${pm.currentPage}"/>
    <input type="hidden" name="numPerPage" value="${pm.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
    <input type="hidden" name="orderDirection" value="${param.orderDirection}"/>
</form>

<div class="pageHeader">
    <form rel="pagerForm" method="post" action="${root}/userManage/relation/search"
          onsubmit="return navTabSearch(this);">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>编号</label>
                    <input class="textInput" name="id" value="" type="text">
                </li>
                <li>
                    <label>姓名</label>
                    <input class="textInput" name="name" value="" type="text">
                </li>
            </ul>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>

<div class="pageContent">
    <%--<div class="panelBar">--%>
    <%--<ul class="toolBar">--%>
    <%--&lt;%&ndash;跳转到添加新用户的界面时候要写入省市区内容&ndash;%&gt;--%>
    <%--<li><a class="add" href="${root}/userManage/add/page" target="navTab" title="添加亲属关系"><span>添加</span></a></li>--%>
    <%--<li><a title="确定要删除这些记录吗?" target="selectedTodo" rel="ids" href="${root}/userManage/relation/delete/list"--%>
    <%--class="delete"><span>批量删除</span></a></li>--%>
    <%--<li><a class="add" href="${root}/userManage/relation/page?id={sid_user}" target="navTab"--%>
    <%--title="修改用户信息"><span>修改</span></a>--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>

    <table class="table" layoutH="118" targetType="dialog" width="100%">
        <thead>
        <tr>
            <th orderfield="id">id</th>
            <th orderfield="name">姓名</th>
            <th orderfield="relation">关系</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${not empty pm.result}">
            <c:forEach var="item" items="${pm.result}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.relation}</td>
                    <td>
                        <a title="增加亲属" target="navTab" href="${root}/userManage/relation/add/page?uid=${uid}"
                           class="btnEdit">增加</a>
                        <a title="编辑亲属" target="navTab" href="${root}/userManage/relation/edit/page?id=${item.id}"
                           class="btnEdit">编辑</a>
                        <a title="删除亲属" target="ajaxTodo" href="${root}/userManage/relation/delete?id=${item.id}"
                           class="btnDel">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

</div>

<jsp:include page="/view/public/page.jsp">
    <jsp:param name="url" value="/userManage/relation/search"/>
</jsp:include>
