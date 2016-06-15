<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"
       scope="request"/>
<form id="pagerForm" action="${root}/usreManage/company/search">
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${model.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
    <input type="hidden" name="orderDirection" value="${param.orderDirection}"/>
</form>

<div class="pageHeader">
    <form rel="pagerForm" method="post" action="${root}/usreManage/company/search"
          onsubmit="return dwzSearch(this, 'dialog');">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>编号</label>
                    <input class="textInput" name="id" value="" readonly="readonly">
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

        <c:if test="${not empty company}">
            <c:forEach var="item" items="${company}">
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>
                    <a class="btnSelect" href="javascript:$.bringBack({id:'${item.id}', name:'${item.name}'})"
                       title="查找带回">选择</a>
                </td>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

    <div class="panelBar">
        <div class="pages">
            <span>每页</span>

            <select name="numPerPage" onchange="dwzPageBreak({targetType:'dialog', numPerPage:'10'})">
                <option value="10" selected="selected">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>
            <span>条，共2条</span>
        </div>
        <div class="pagination" targetType="dialog" totalCount="2" numPerPage="10" pageNumShown="1"
             currentPage="1"></div>
    </div>
</div>
