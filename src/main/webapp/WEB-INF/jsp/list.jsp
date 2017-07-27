<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>秒杀列表</title>
    <%@include file="common/head.jsp" %>
</head>
<body>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>

        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <td>名称</td>
                    <td>库存</td>
                    <td>开始时间</td>
                    <td>结束时间</td>
                    <td>创建时间</td>
                    <td>详情页</td>
                </tr>
                </thead>
                <tbody id="app-4">
                <tr v-for="sk in list">
                    <td>{{sk.name}}</td>
                    <td>{{sk.number}}</td>
                    <td>{{sk.startTime}}</td>
                    <td>{{sk.endTime}}</td>
                    <td>{{sk.createTIme}}</td>
                    <td><a class="btn btn-info"  :href ="'${pageContext.request.contextPath}/'+sk.seckillId+'/detail'" target="_blank">详情</a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div style="display: none" id="jsonList">
    ${jsonList}
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/1.12.2/jquery.js"></script>
<script src="https://cdn.bootcss.com/vue-strap/1.1.37/vue-strap.min.js"></script>
<script src="https://cdn.bootcss.com/vue/2.4.2/vue.js"></script>
<script>
    //没有使用ajax  为了兼容换行等特殊字符，先这样处理。
    var  list = JSON.parse($("#jsonList").html());
    //循环
    var app4 = new Vue({
        el: '#app-4',
        data: {
            list: list
        }
    });


</script>
</html>









