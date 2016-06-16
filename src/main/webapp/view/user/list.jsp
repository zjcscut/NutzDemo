<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="${pageContext.request.contextPath}"
       scope="request"/>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${root}/userManage/query" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        用户编号:
                        <input type="text" name="user_id"/>
                    </td>
                    <td>
                        用户姓名:
                        <input type="text" name="user_name"/>
                    </td>
                    <td>
                        出生日期:
                        <input type="text" name="birth" class="date"/>
                    </td>
                    <td>
                        联系电话:
                        <input type="text" name="phone" class="number" minlength="7" maxlength="11"/>
                    </td>
                    <td>
                        Email:
                        <input type="text" name="email" class="email"/>
                    </td>
                    <%--<td>--%>
                    <%--<label>住址:</label>--%>
                    <%--<select class="combox" name="province" ref="w_combox_city"--%>
                    <%--refUrl="demo/combox/city_{value}.html" rev="">--%>
                    <%--<option value="all">所有省市</option>--%>
                    <%--<option value="bj">北京</option>--%>
                    <%--<option value="sh">上海</option>--%>
                    <%--<option value="zj">浙江省</option>--%>
                    <%--</select>--%>
                    <%--<select class="combox" name="city" id="w_combox_city" ref="w_combox_region"--%>
                    <%--refUrl="demo/combox/region_{value}.html">--%>
                    <%--<option value="all">所有城市</option>--%>
                    <%--</select>--%>
                    <%--<select class="combox" name="region" id="w_combox_region"--%>
                    <%--refUrl="demo/combox/region_{value}.html">--%>
                    <%--<option value="all">所有区县</option>--%>
                    <%--</select>--%>
                    <%--</td>--%>
                    <td>
                        <label> 是否有效</label>
                        <select class="combox" name="is_enable">
                            <option value="1">有效</option>
                            <option value="0">无效</option>
                        </select>
                    </td>

                </tr>
            </table>
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

<div class="pageContent" id="table_list">
    <div class="panelBar">
        <ul class="toolBar">
            <%--跳转到添加新用户的界面时候要写入省市区内容--%>
            <li><a class="add" href="${root}/userManage/add/page" target="navTab" title="添加新用户"><span>添加</span></a></li>
            <li><a class="add" href="${root}/userManage/edit/page?id={sid_user}" target="navTab"
                   title="修改用户信息"><span>修改</span></a>
            </li>
            <li><a class="delete" href="${root}/userManage/delete?id={sid_user}" target="ajaxTodo"
                   title="确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
            <li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab"
                   title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="20">用户id</th>
            <th width="50">用户姓名</th>
            <th width="80">手机号码</th>
            <th width="20">是否有效</th>
            <th width="50">创建日期</th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${code == 1}">
            <c:forEach items="${pm.result}" var="item">
                <tr target="sid_user" rel="<c:if test="${not empty item.id}">${item.id}</c:if>">
                    <td><c:if test="${not empty item.id}">${item.id}</c:if></td>
                    <td><c:if test="${not empty item.name}">${item.name}</c:if></td>
                    <td><c:if test="${not empty item.phone}">${item.phone}</c:if></td>
                    <td><c:if test="${not empty item.enableDesc}">${item.enableDesc}</c:if></td>
                    <td><c:if test="${not empty item.createTime}">
                        <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </c:if></td>
                        <%--<td><a class="button" href="${root}/view/user/edit.jsp?id=${item.id}"><span>修改</span></a></td>--%>
                        <%--<td><a class="button" href="${root}/view/user/delete.jsp?id=${item.id}"><span>删除</span></a></td>--%>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <!-- 分页 -->
    <jsp:include page="/view/public/page.jsp" flush="true">
        <jsp:param name="url" value="/userManage/list"/>
    </jsp:include>

</div>
