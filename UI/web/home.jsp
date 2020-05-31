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
                <a class="navbar-brand" href="./graphics.jsp">
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
    <div class="row" style="width: 100%;height: 93%;">
        <!-- State -->
        <div class="position-absolute border rounded text-center mt-4 p-2 shadow" style="width:23%; background: transparent; margin-left: 2%">
            <div>&Eacute;tat du micro-contr&ocirc;leur</div>
            <div>
                <img src="./img/off.png">
                <span>&Eacute;teint</span>
            </div>
        </div>

        <!-- Image + Messages -->
        <div class="col" style="margin-top: 7%;height: 70%;margin-left: 2%;padding: 0;">
            <!-- === AIRFLOW === -->
            <div class="border shadow rounded" style="background: transparent; padding: 6% 3% 3% 3%;">
                <!-- Title -->
                <div class="position-absolute" style="text-align: center;width: 51%;top: -16px;font-size: large;font-weight: 500;background: #5dcee0;color: #f2f2f2;">
                    <img src="./img/bio_icon_airflow_v2.svg" alt="" height="35">
                    <span>A&eacute;ration du milieu</span>
                </div>
                <div class="list-group">
                    <!-- DO2 -->
                    <div class="list-group-item">
                        <div>Dissolution O<sub>2</sub><span class="float-right"><span>40</span> %</span></div>

                    </div>
                    <!-- flow -->
                    <div class="list-group-item">
                        <span>Gaz de sortie</span>
                        <div>Dioxyg&egrave;ne (O<sub>2</sub>) : <span class="float-right"><span>30</span> %</span></div>
                        <div>Dioxyde de carbone (CO<sub>2</sub>) : <span class="float-right"><span>60</span> %</span></div>
                    </div>
                    <!-- valves -->
                    <div class="list-group-item">
                        <span>Débit d'air</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" min="0" max="100" id="rangeDO2" oninput="inputDO2.value=rangeDO2.value">
                            </div>
                            <div class="col-3">
                                <input class="form-control" type="number" step="1" min="0" max="100" id="inputDO2" value="50" name="inputDO2" oninput="rangeDO2.value=inputDO2.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- === PH === -->
            <div class="border shadow rounded" style="background: transparent;margin-top: 10%; padding: 6% 3% 3% 3%;">
                <!-- Title -->
                <div class="position-absolute" style="text-align: center;width:60%;top:302px;font-size:large;font-weight: 500;background: #73e0c6;color: #f2f2f2;">
                    <img src="./img/bio_icon_ph_v2.svg" alt="" height="35">
                    <span>Acidit&eacute;/Basicit&eacute; du milieu</span>
                </div>
                <div class="list-group">
                    <div class="list-group-item" style="background:white">
                        <div>
                            <span>pH du milieu</span>
                            <span class="float-right">Solution <span>neutre</span></span>
                        </div>
                        <ul class="list-group list-group-horizontal" style="margin-top: 2%;">
                            <li class="list-group-item phScale">0</li>
                            <li class="list-group-item phScale">1</li>
                            <li class="list-group-item phScale">2</li>
                            <li class="list-group-item phScale">3</li>
                            <li class="list-group-item phScale">4</li>
                            <li class="list-group-item phScale">5</li>
                            <li class="list-group-item phScale">6</li>
                            <li class="list-group-item phScale active">7</li>
                            <li class="list-group-item phScale">8</li>
                            <li class="list-group-item phScale">9</li>
                            <li class="list-group-item phScale">10</li>
                            <li class="list-group-item phScale">11</li>
                            <li class="list-group-item phScale">12</li>
                            <li class="list-group-item phScale">13</li>
                            <li class="list-group-item phScale">14</li>
                        </ul>
                    </div>
                    <!-- Actuator pH IO -->
                    <div class="list-group-item" style="background:white">
                        <span>R&eacute;gulation du pH</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" min="0" max="14" id="rangePH" oninput="inputPH.value=rangePH.value">
                            </div>
                            <div class="col-3">
                                <input class="form-control" type="number" step="1" min="0" max="14" id="inputPH" value="7" name="inputPH" oninput="rangePH.value=inputPH.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 4 windows (state + parameters) -->
        <div class="col-6" style="height: 95%;">
            <div class="text-center mt-2" style="width:100%;height: 100%;">
                <img src="img/bioreactor2.svg" alt="" style="height: 100%;position: absolute;z-index: 1;left: 10%;">
                <img src="img/bioreactor_shadow_rotator.svg" alt="" style="visibility:hidden;height: 100%;position: absolute;z-index: 2;left: 10%;">
                <img src="img/bioreactor_shadow_Airflow.svg" alt="" style="visibility:hidden;height: 100%;position: absolute;z-index: 2;left: 10%;">
                <img src="img/bioreactor_shadow_IO.svg" alt="" style="visibility:hidden;height: 100%;position: absolute;z-index: 2;left: 10%;">
                <img src="img/bioreactor_shadow_Temp_Do_pH.svg" alt="" style="visibility:hidden;height: 100%;position: absolute;z-index: 2;left: 10%;">
                <img src="img/bioreactor_shadow_TMFC.svg" alt="" style="visibility:hidden;height: 100%;position: absolute;z-index: 2;left: 10%;">
            </div>
        </div>
        <!-- Values (Airflow + Temperature) + State of micro -->
        <div class="col-3" style="margin-top: 2%;height: 62%;padding-top: 1%;">
            <!-- === AGITATOR === -->
            <div class="border shadow rounded" style="background: transparent;margin-top: 10%; padding: 6% 3% 3% 3%;">
                <!-- Title -->
                <div class="position-absolute" style="text-align: center;width: 27%;top: 47px;font-size:large;font-weight: 500;background: #5acbe1;color: #f2f2f2;margin-left: 2%;">
                    <img src="./img/bio_icon_agitator_v2.svg" alt="" height="35">
                    <span>Agitateur</span>
                </div>
                <div class="list-group">
                    <div class="list-group-item" style="background:white">
                        <div>
                            <span>Vitesse de rotation</span>
                            <span class="float-right"><span>800</span> tr/min</span>
                        </div>
                    </div>
                    <div class="list-group-item" style="background:white">
                        <span>Agitation</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" min="0" max="1200" id="rangeAgitation" oninput="inputAgitation.value=rangeAgitation.value">
                            </div>
                            <div class="col-3">
                                <input class="form-control" type="number" step="10" min="0" max="1200" id="inputAgitation" value="600" name="inputAgitation" oninput="rangeAgitation.value=inputAgitation.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- === TEMPERATURE === -->
            <div class="border shadow rounded" style="background: transparent;margin-top: 10%; padding: 6% 3% 3% 3%;">
                <div class="position-absolute" style="text-align:center;width: 49%;top: 273px;font-size:large;font-weight: 500;background: #6cdace;color: #f2f2f2;margin-left: 2%;">
                    <img src="./img/bio_icon_temperature_v2.svg" alt="" height="35">
                    <span>Temp&eacute;rature du milieu</span>
                </div>
                <div class="list-group">
                    <div class="list-group-item" style="background:white">
                        <div>
                            <span>Temp&eacute;rature de la cuve</span>
                            <span class="float-right"><span>60</span> &deg;C</span>
                        </div>
                    </div>
                    <div class="list-group-item" style="background:white">
                        <span>Couverture chauffante</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" min="0" max="90" id="rangeTemp" oninput="inputTemp.value=rangeTemp.value">
                            </div>
                            <div class="col-3">
                                <input class="form-control" type="number" step="1" min="0" max="90" id="inputTemp" value="45" name="inputTemp" oninput="rangeTemp.value=inputTemp.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
<%--            <div class="border rounded mt-5 p-2 shadow" style="background:white">--%>
<%--                Buttons--%>
<%--                <div style="bottom: 10px;right: 10px;">--%>
<%--                    <input type="submit" class="btn btn-primary" value="OK">--%>
<%--                </div>--%>
<%--            </div>--%>
        </div>
    </div>

<%@include file="includes/functions-js.jsp"%>
<%@include file="includes/transition-js.jsp"%>
<%@include file="includes/graph-js.jsp"%>
</body>
