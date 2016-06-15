<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="${pageContext.request.contextPath}"
       scope="request"/>

<form method="post" action="${root}/userManage/add" class="pageForm required-validate"
      onsubmit="return validateCallback(this,navTabAjaxDone);">

    <div class="pageFormContent" layoutH="56">
        <p>
            <label>姓名：</label>
            <input name="user_name" class="required" type="text" size="30" alt="请输入姓名"/>
        </p>
        <p>
            <label>手机号：</label>
            <input name="phone" type="text" value="" class="number" maxlength="11" minlength="8">
        </p>
        <p>
            <label>邮箱：</label>
            <input name="email" class="email" type="text" size="30"/>
        </p>
        <p>
            <label>出生日期：</label>
            <input type="text" name="birth" class="date"/>
        </p>
        <p>
            <label>住址:</label>
            <select class="combox" name="province" id="w_combox_province" onchange="selectCity()">
                <%--<c:if test="${not empty province}">--%>
                <%--<c:forEach var="item" items="${province}">--%>
                <%--<option value="${item.id}">${item.name}</option>--%>
                <%--</c:forEach>--%>
                <%--</c:if>--%>
            </select>

            <select class="combox" name="city" id="w_combox_city" onchange="selectRegion()">
                <%--<c:if test="${not empty area}">--%>
                <%--<c:forEach var="item" items="${area}">--%>
                <%--<option value="${item.id}">${item.name}</option>--%>
                <%--</c:forEach>--%>
                <%--</c:if>--%>
            </select>
            <select class="combox" name="region" id="w_combox_region">
                <%--<option value="all">所有区县</option>--%>
            </select>
        </p>

        <%--查找带回 --%>
        <p>
            <label>公司名称：</label>
            <input type="hidden" name="orgLookup.id" value="${orgLookup.id}"/>
            <input type="text" class="required" name="orgLookup.name" value="" suggestFields="name"
                   suggestUrl="${root}/userManage/company" lookupGroup="orgLookup"/>
            <a class="btnLook" href="${root}/userManage/company/list" lookupGroup="orgLookup">查找带回</a>
        </p>

    </div>

    <div class="formBar">
        <ul>
            <!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
            <li>
                <div class="buttonActive">
                    <div class="buttonContent">
                        <button type="submit">保存</button>
                    </div>
                </div>
            </li>
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

    $(function () {
        selectProvince();
        $("#w_combox_province").change(function () {
            selectCity();
        });
        $("#w_combox_city").change(function () {
            selectRegion();
        });
    });

    function selectProvince() {
        $.ajax(
                {
                    type: "post",
                    url: path + "/userManage/query/area",
                    data: {
                        pid: 0
                    },
                    success: function (data) {
                        $("#w_combox_province").empty();
                        $.each(data, function (index, item) {
                            var option = "<option value='" + item.id + "'>" + item.name + "</option>";
                            $("#w_combox_province").append(option);
                        });
                    }
                })
    }


    //根据选择的省级获取城市列表
    function selectCity() {
//        var provinceId = $("#w_combox_province").val();
        $.ajax({
            type: "post",
            url: path + "/userManage/query/area",
            data: {
                pid: $("#w_combox_province").attr("value")
            },
            dataType: "json",
            success: function (data) {
                $("#w_combox_city").empty();
                $.each(data, function (index, item) {
//                    var selected = "";
//                    if (item.provinceId == provinceid) {
//                        selected = "selected";
//                    }
                    var option = "<option value='" + item.id + "'>" + item.name + "</option>";
                    $("#w_combox_city").append(option);
                });
//                selectRegion();
            }
        });
    }
    //根据选择的城市获取地区列表
    function selectRegion() {
        $.ajax({
            type: "post",
            url: path + "/userManage/query/area",
            data: {
                pid: $("#w_combox_city").attr("value")
            },
            dataType: "json",
            success: function (data) {
                $("#w_combox_region").empty();
                $.each(data, function (index, item) {
//                    var selected = "";
//                    if (item.provinceId == provinceid) {
//                        selected = "selected";
//                    }
                    var option = "<option value='" + item.id + "'>" + item.name + "</option>";
                    $("#w_combox_region").append(option);
                });
            }
        });
    }
</script>