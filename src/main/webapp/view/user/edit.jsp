<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="${pageContext.request.contextPath}"
       scope="request"/>


<div class="pageContent">

    <form method="post" action="<%=request.getContextPath()%>/userManage/save"
          class="pageForm? required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <div class="wrap">

                <fieldset>
                    <legend>基本信息</legend>
                    <p>
                        <label>主键：</label>
                        <input type="text" name="user.id" readonly="readonly"
                               value="<c:if test="${not empty  user.id}">${user.id}</c:if>"/>
                    </p>

                    <p>
                        <label>姓名：</label>
                        <input name="user.name" type="text" value="<c:if test="${not empty user.name}">${user.name}</c:if>"/>
                    </p>
                    <p>
                        <label>手机号：</label>
                        <input name="user.phone" type="text"
                               value="<c:if test="${not empty user.phone}">${user.phone}</c:if>"/>
                    </p>
                    <p>
                        <label>邮箱：</label>
                        <input name="user.email" type="text"
                               value="<c:if test="${not empty user.email}">${user.email}</c:if>"/>
                    </p>
                    <%--<p>--%>
                    <%--<label>出生日期：</label>--%>
                    <%--<<input name="user.birth" type="text"--%>
                    <%--value="<c:if test="${not empty user.birth}"><fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>"/>--%>
                    <%--</p>--%>
                    <p>
                        <label>住址:</label>

                        <%--<select class="combox" name="province" id="w_combox_province"--%>
                        <%--rel="w_combox_city" refUrl="${root}/userManage/query/area?pid={value}">--%>
                        <select name="user.regionId" id="area_edit" onchange="getProvince()">
                            <c:forEach items="${regionList}" var="region">
                                <c:if test="${empty user.regionId}">
                                    <option value="${region.id}"
                                            <c:if test="${user.regionId eq 1}">selected</c:if>>${region.name}</option>
                                </c:if>
                                <c:if test="${not empty user.regionId }">
                                    <option value="${region.id}" <c:if
                                            test="${region.id eq user.regionId}">selected</c:if>>${region.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <select name="user.provinceId" id="province_edit" onchange="getCity()">

                        </select>

                        <select name="user.cityId" id="city_edit">
                        </select>
                    </p>

                    <%--查找带回 --%>
                    <p>
                        <label>公司名称：</label>
                        <input type="text" name="user.company"
                               value="<c:if test="${not empty user.company}">${user.company}</c:if>"
                               lookupGroup=""/>
                        <a class="btnLook"
                           href="${root}/userManage/company/list"
                           lookupGroup="">查找带回</a>
                    </p>

                    <%--是否有效--%>
                    <p>
                        <select name="user.isEnable" id="isEnable">
                            <option value="${user.isEnable}" selected>${user.enableDesc}</option>
                            <c:choose>
                                <c:when test="${user.isEnable eq 1}">
                                    <option value="${1 - user.isEnable}">
                                        无效
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${1 - user.isEnable}">
                                        有效
                                    </option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </p>
                </fieldset>

                <fieldset>
                    <legend>亲属信息</legend>
                    <div>
                        <div class="panelBar">
                            <ul class="toolBar">
                                <li><a class="add" href="#"><span>添加</span></a></li>
                            </ul>
                        </div>
                        <table class="list nowrap dynamicDataGrid" addButId="zqAdd" addButton="新增" width="100%"
                               initCount="0">
                            <thead>
                            <tr>
                                <th type="text" name="proRelation[#index#].name" fieldClass="required">姓名
                                </th>
                                <th type="text" name="proRelation[#index#].relation" fieldClass="required">关系
                                </th>
                                <th type="delThisRow" defaultVal="" fieldClass="btnDel">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${proRelation}" varStatus="index">
                                <tr>
                                    <%--<td hidden><input type="text" name="proRelation.[${index.index}].userId"--%>
                                                      <%--value="${user.id}"></td>--%>
                                    <td><input type="text" name="proRelation.[${index.index}].name"
                                               value="${item.name}"></td>
                                    <td><input type="text" name="proRelation.[${index.index}].relation"
                                               value="${item.relation}"></td>
                                    <td><a href="javascript:void(0)" class="btnDel" defaultVal="">删除</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </fieldset>
                <%--</div>--%>

                <div class="formBar">
                    <div class="wrap clearfix">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="submit">保存修改</button>
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
                </div>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    var path = "<%=request.getContextPath()%>";
    var areaid = "${empty user.regionId ? 5 : user.regionId}";
    var provinceid = "${empty user.provinceId ? 19: user.provinceId}";
    var cityid = "${empty user.cityId ? 200:user.cityId}";

    $(function () {
        getProvince(areaid)
        getCity(provinceid);
    });

    //根据所选区域获取省份
    function getProvince(region_id) {
//        debugger
        $("#city_edit").empty();
        var regionId = $("#area_edit").val();
        if (!regionId) {
            regionId = region_id
        }
        $.ajax({
            type: "post",
            url: path + "/userManage/query/area",
            data: {
                pid: regionId
            },
            dataType: "json",
            success: function (data) {
                $("#province_edit").empty();
                $.each(data, function (index, item) {
                    var selected = "";
                    if (item.id == provinceid) {
                        selected = "selected";
                    }
                    var option = "<option value='" + item.id + "' " + selected + ">" + item.name + "</option>";
                    $("#province_edit").append(option);
                });
            }
        });
    }

    //根据所选省份获取城市
    function getCity(province_id) {
//        debugger
        var provinceId = $("#province_edit").val();
        if (!provinceId) {
            provinceId = province_id;
        }
        $.ajax({
            type: "post",
            url: path + "/userManage/query/area",
            data: {
                pid: provinceId
            },
            dataType: "json",
            success: function (data) {
                $("#city_edit").empty();
                $.each(data, function (index, item) {
                    var selected = "";
                    if (cityid == item.id) {
                        selected = "selected";
                    }
                    var option = "<option value='" + item.id + "'  " + selected + ">" + item.name + "</option>";
                    $("#city_edit").append(option);
                });
            }
        });
    }


</script>


