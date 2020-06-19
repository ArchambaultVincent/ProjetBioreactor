<%--
  Created by IntelliJ IDEA.
  User: clort
  Date: 14/05/2020
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="includes/header.jsp"%>
<%
    String paramPh = request.getParameter("inputPH");
    String paramTemp = request.getParameter("inputTemp");
    String paramDO2 = request.getParameter("inputDO2");

    String valueOpti = request.getParameter("valueOptimisation");
    String valueFault = request.getParameter("valueFaultDetection");
%>
<body style="background: linear-gradient(#4fc2ef, #91f9a5);">
<!-- Navigation bar (Change parameters + view graphics) -->
<nav class="navbar navbar-expand-lg" style="background:white">
    <div class="collapse navbar-collapse " id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="navbar-brand" href="#">
                Graphiques
                <img src="img/bio_Graph.svg" width="35" height="35" class="d-inline-block align-top" alt="" loading="lazy">
            </a>
            <a class="navbar-brand" href="#">
                Param&egrave;tres
                <img src="img/bio_Settings.svg" width="35" height="35" class="d-inline-block align-top" alt="" loading="lazy">
            </a>
        </div>
    </div>
</nav>
<!-- Visualisation -->
<div class="row" style="width: 100%;height: 93%;">
    <div class="col-1"></div>
    <div class="col-10" style="height: 95%">
        <div class="card" style="height: 100%;background: rgba(255, 255, 255, 0.75);margin-top: 1%;">
            <div class="card-body" style="height: 100%">
                <canvas id="do2-Chart" style="max-width: 100%;"></canvas>
            </div>
        </div>
    </div>
</div>

<%@include file="includes/functions-js.jsp"%>
<%@include file="includes/transition-js.jsp"%>
<%@include file="includes/graph-js.jsp"%>
</body>
