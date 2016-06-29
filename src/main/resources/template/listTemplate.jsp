<%@page pageEncoding="utf-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ppmoney" uri="PPmoneyTags" %>

<div class="pageHeader" style="border: 1px #B8D0D6 solid">
    <form onsubmit="return navTabSearch(this);" rel="pagerForm"
          method="post">
        <div class="searchBar clearfix">
            <div class="searchBox">
                <table class="searchContent" style="position: relative;">
                    <tr>
                        <%--查询条件--%>
                        @ReplaceQueryForm_list
                        <td>
                            <div class="search_btnBox">
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="submit" id="findBack">查找</button>
                                    </div>
                                </div>
                                <div class="buttonActive" style="margin-left: 5px;">
                                    <div class="buttonContent">
                                        <button type="button" class="empty">清空</button>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </form>

</div>

<div class="pageContent"
     style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
    <div class="panelBar">
        <ul class="toolBar">
            <li>
                <a class="icon"
                   href="<%=request.getContextPath() %>/@moduleName/@className/export?rnd=<%=Math.random()%>"
                   target="dwzExport" targettype="navTab" title="确实要导出这些记录吗?"><span>导出</span>
                </a>
            </li>
            <li><a class="add"
                   href="<%=request.getContextPath() %>/@moduleName/@className/Edit.do" rel="@className_edit"
                   target="dialog" mask="true" width="800" height="600"><span>新增</span></a>
            <li><a class="delete" target="selectedTodo" postType="string"
                   title="确定要删除吗?" rel="ids" href="<%=request.getContextPath() %>/@moduleName/@className/Del.do?ids=${ids}"><span>批量删除</span></a>
            </li>
        </ul>
    </div>
    <table class="table" width="100%">
        <thead>
        <tr>
            <th width="40" align="center"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
            @coltitle_list
            <th align="left">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty pm.result}">
                <tr>
                    <td>
                        <center>暂无记录</center>
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="list" items="${ pm.result }">
                    <tr>
                            <%-- 记录结果--%>
                        <td><input name="ids" type="checkbox" value="${list.@pkname}"></td>
                        @data_list
                        <td>
                            <a href="<%=request.getContextPath() %>/@moduleName/@className/Read.do?Id=${list.@pkname}"
                               target="dialog" width="800" height="600"
                               title="查看">查看</a>&nbsp;

                            <a href="<%=request.getContextPath() %>/@moduleName/@className/Edit.do?Id=${list.@pkname}"
                               target="dialog" width="800" height="600"
                               title="编辑" rel="@className_edit">编辑</a>&nbsp;

                        </td>

                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<!-- 分页 -->
<jsp:include page="/view/pub/page.jsp" flush="true">
    <jsp:param name="url" value="/dfxjd/finance/accountChecking/list"/>
</jsp:include>

<script src="static/js/adaptive.layout.table.js"></script>
<script language="javascript" src="static/js/lodop.js" charset="utf-8"></script>
<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0"
        style="margin:0px;padding:0px;"></object>
<embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0" style="margin:0px;padding:0px;"></embed>