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
<body>
    <!-- Navigation bar (Change parameters + view graphics) -->
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="navbar-brand" href="#">
                    <img src="./img/bio_Graph.svg" width="30" height="30" class="d-inline-block align-top" alt="" loading="lazy">
                    Graphiques
                </a>
            </li>
            <li class="nav-item active">
                <a class="navbar-brand" href="#">
                    <img src="./img/bio_Settings.svg.svg" width="30" height="30" class="d-inline-block align-top" alt="" loading="lazy">
                    Param&egrave;tres
                </a>
            </li>
        </ul>
    </div>
    <nav class="navbar navbar-expand-lg">
        <div class="collapse navbar-collapse " id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="navbar-brand" href="#">
                    Graphiques
                    <img src="./img/bio_Graph.svg" width="35" height="35" class="d-inline-block align-top" alt="" loading="lazy">
                </a>
                <a class="navbar-brand" href="#">
                    Param&egrave;tres
                    <img src="./img/bio_Settings.svg" width="35" height="35" class="d-inline-block align-top" alt="" loading="lazy">
                </a>
            </div>
        </div>
    </nav>
    <!-- Visualisation -->
    <div class="row">
        <!-- Values (Agitation + pH + Growth) -->
        <div class="col-4">
            <div class="border rounded mt-5 ml-3 mb-5 p-2">
                <img src="./img/bio_setting_agitation.png" alt="" width="32">
                <span>Agitation</span>
                <div><span></span> tr/min</div>
                <div>Some text</div>
            </div>

            <div class="border rounded mt-5 ml-3 mb-5 p-2">
                <img src="./img/bio_setting_ph.png" alt="" width="32">
                <span>pH</span>
                <div><span></span> - <span>Acide</span></div>
                <div>Some text</div>
            </div>

            <div class="border rounded mt-5 ml-3 p-2">
                <img src="./img/bio_setting_bacteria.png" alt="" width="32">
                <span>Croissance</span>
                <div><span></span> %</div>
                <div>Some text</div>
            </div>
        </div>
        <!-- Image + Messages -->
        <div class="col-4">
            <div class="text-center">
                <img src="./img/bioreactor.png" width="602" height="799" alt="" loading="lazy">
                <div>Messages</div>
            </div>
        </div>
        <!-- Values (Airflow + Temperature) + State of micro -->
        <div class="col-4">
            <div class="border rounded text-center mt-5 ml-3 p-2">
                <span>Micro-contr&ocirc;leur</span>
                <img src="./img/off.png">
                <div>State</div>
            </div>

            <div class="border rounded mt-5 ml-3 p-2">
                <img src="./img/bio_setting_bacteria.png" alt="" width="32">
                <span>Concentration en D02</span>
                <div><span></span> %</div>
                <div>Some text</div>
            </div>

            <div class="border rounded mt-5 ml-3 p-2">
                <img src="./img/bio_setting_bacteria.png" alt="" width="32">
                <span>Temp&eacute;rature</span>
                <div><span></span> &deg;C</div>
                <div>Some text</div>
            </div>
        </div>
    </div>

<%@include file="includes/functions-js.jsp"%>
<%@include file="includes/transition-js.jsp"%>
<%@include file="includes/graph-js.jsp"%>
</body>
