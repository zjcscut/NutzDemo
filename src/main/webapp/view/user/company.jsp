<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"
       scope="request"/>
<form id="pagerForm" action="${root}/userManage/company/search">
    <input type="hidden" name="currentPage" value="${pm.currentPage}"/>
    <input type="hidden" name="numPerPage" value="${pm.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
    <input type="hidden" name="orderDirection" value="${param.orderDirection}"/>
</form>

<div class="pageHeader">
    <form rel="pagerForm" method="post" action="${root}/userManage/company/search"
          onsubmit="return dwzSearch(this, 'dialog');">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>编号</label>
                    <input class="textInput" name="id" value="" type="text">
                </li>
                <li>
                    <label>公司名称:</label>
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

    <table class="table" layoutH="118" targetType="dialog" width="100%">
        <thead>
        <tr>
            <th orderfield="id">公司编号</th>
            <th orderfield="name">公司名称</th>
            <th width="80">查找带回</th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${not empty pm.result}">
        <c:forEach var="item" items="${pm.result}">
        <tr>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>
                <a class="btnSelect" href="javascript:$.bringBack({company:'${item.name}'})"
                   title="查找带回">选择</a>
            </td>
            </c:forEach>
            </c:if>
        </tbody>
    </table>

</div>

<jsp:include page="/view/public/page.jsp">
    <jsp:param name="url" value="/userManage/company/search"/>
</jsp:include>
