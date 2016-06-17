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

    <form method="post" action="<%=request.getContextPath()%>/userManage/add"
          class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">

            <p>
                <label>姓名：</label>
                <input name="name" type="text" minlength="5" class="required"/>
            </p>
            <p>
                <label>手机号：</label>
                <input name="phone" type="text" value="" class="number" maxlength="11" minlength="7"/>
            </p>
            <p>
                <label>邮箱：</label>
                <input name="email" type="text" value="" class="email"/>
            </p>

            <p>
                <label>出生日期：</label>
                <input type="text" name="birth" class="date" dateFmt="yyyy-MM-dd" readonly="true"/>
                <a class="inputDateButton" href="javascript:;">选择</a>
                <span class="info">yyyy-MM-dd</span>
            </p>

            <p>
                <label>住址:</label>

                <%--<select class="combox" name="province" id="w_combox_province"--%>
                <%--rel="w_combox_city" refUrl="${root}/userManage/query/area?pid={value}">--%>
                <select name="regionId" id="add_area_edit" onchange="getProvince()">
                    <c:forEach items="${regionList}" var="region" varStatus="status">
                        <option value="${region.id}"
                                <c:if test="${status.index eq 0}">selected</c:if>>${region.name}</option>
                    </c:forEach>
                </select>
                <select name="provinceId" id="add_province_edit" onchange="getCity()">

                </select>

                <select name="cityId" id="add_city_edit">
                </select>
            </p>

            <%--查找带回 --%>
            <p>
                <label>公司名称：</label>
                <input type="text" name="company" value="<c:if test="${not empty user.company}">${user.company}</c:if>"
                       lookupGroup=""/>
                <a class="btnLook"
                   href="${root}/userManage/company/list"
                   lookupGroup="">查找带回</a>
            </p>

            <%--是否有效--%>
            <%--<p>--%>
            <%--<select name="isEnable" id="isEnable">--%>
            <%--<option value="${user.isEnable}" selected>${user.enableDesc}</option>--%>
            <%--<c:choose>--%>
            <%--<c:when test="${user.isEnable eq 1}">--%>
            <%--<option value="${1 - user.isEnable}">--%>
            <%--无效--%>
            <%--</option>--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
            <%--<option value="${1 - user.isEnable}">--%>
            <%--有效--%>
            <%--</option>--%>
            <%--</c:otherwise>--%>
            <%--</c:choose>--%>
            <%--</select>--%>
            <%--</p>--%>

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
<script type="text/javascript">
    var path = "<%=request.getContextPath()%>";
    var areaid = "${empty user.regionId ? 100 : user.regionId}";
    var provinceid = "${empty user.provinceId ? 100: user.provinceId}";
    var cityid = "${empty user.cityId ? 100:user.cityId}";

    $(function () {
        getProvince(areaid)
        getCity(provinceid);
    });

    //根据所选区域获取省份
    function getProvince(region_id) {
//        debugger
//        var indexObj =
        $("#add_city_edit").empty();
        var regionId = $("#add_area_edit").val();
        console.log("regionId的值为---> " + regionId);
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
                $("#add_province_edit").empty();
//                debugger
                $.each(data, function (index, item) {
                    var selected = "";
                    if (index == 0) {
                        selected = " selected";
                    }
                    var option = "<option value='" + item.id + "' " + selected + ">" + item.name + "</option>";
                    $("#add_province_edit").append(option);
                });
//                $("#add_province_edit option:first").select();
                getCity(provinceid);
            }
        });
    }

    //根据所选省份获取城市
    function getCity(province_id) {
        debugger
        var provinceId = $("#add_province_edit").val();
        console.log("provinceId的值为---> " + provinceId);
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
                $("#add_city_edit").empty();
                debugger
                $.each(data, function (index, item) {
                    var selected = "";
                    if (index == 0) {
                        selected = " selected";
                    }
                    var option = "<option value='" + item.id + "'  " + selected + ">" + item.name + "</option>";
                    $("#add_city_edit").append(option);
                });
            }
        });
    }


</script>


