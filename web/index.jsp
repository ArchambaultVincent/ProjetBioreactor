<%--
  Created by IntelliJ IDEA.
  User: clort
  Date: 14/05/2020
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.net.Socket" %>
<%@ page import="java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp"%>
<%@ include file="modal.jsp"%>
<%@ include file="modal_fdi.jsp"%>
<body style="background: #f4f4f4">
    <!-- Navigation bar (Change parameters + view graphics) -->
    <nav class="navbar navbar-expand-lg" style="z-index: 1; height: 7%">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto"></ul>
            <ul class="list-group list-group-horizontal" id="navSwitch">
                <li class="list-group-item active" id="navBioreactor">
                    <span>Bior&eacute;acteur</span>
                </li>
                <li class="list-group-item" id="navGraphics">
                    <span>Graphiques</span>
                </li>
            </ul>
            <a data-toggle="modal" data-target="#modalSettings">
                <img src="img/bio_Settings.svg" alt="">
            </a>
        </div>
    </nav>
    <!-- Visualisation -->
    <div class="row" id="rowBioreactor" style="width: 100%;height: 93%;">
        <!-- Lines -->
        <svg class="position-fixed" id="lines-bio" style="top:0; left:0; height:100%; width:100%">
            <!-- Airflow -->
            <line id="line-airOut"      x1="450" y1="230" x2="1075" y2="270" stroke="#2c5aa0" stroke-width="15"/>
            <line id="line-sensor-air"  x1="450" y1="300" x2="1015" y2="370" stroke="#2c5aa0" stroke-width="15"/>
            <line id="line-airIn"       x1="450" y1="370" x2="800" y2="450" stroke="#2c5aa0" stroke-width="15"/>
            <!-- pH -->
            <line id="line-sensor-ph"   x1="450" y1="540" x2="1030" y2="370" stroke="#2c5aa0" stroke-width="15"/>
            <line id="line-ph"          x1="450" y1="630" x2="830" y2="280" stroke="#2c5aa0" stroke-width="15"/>
            <!-- Liquid -->
            <line id="line-liquidOut"   x1="1445" y1="220" x2="940" y2="760" stroke="#2c5aa0" stroke-width="15"/>
            <line id="line-liquidIn"    x1="1445" y1="280" x2="990" y2="270" stroke="#2c5aa0" stroke-width="15"/>
            <!-- Agitator -->
            <line id="line-agitator"    x1="1445" y1="470" x2="950" y2="420" stroke="#2c5aa0" stroke-width="15"/>
            <!-- Temperature -->
            <line id="line-temperature" x1="1445" y1="670" x2="1044" y2="405" stroke="#2c5aa0" stroke-width="15"/>
            <line id="line-heater"      x1="1445" y1="730" x2="1120" y2="670" stroke="#2c5aa0" stroke-width="15"/>
        </svg>
        <!-- State -->
        <div class="position-absolute border rounded text-center p-2 shadow" style="width:23%; background: transparent; margin-left: 2%;margin-top: -2%;">
            <div>&Eacute;tat du micro-contr&ocirc;leur</div>
            <div>
                <img src="img/off.png">
                <span>&Eacute;teint</span>
            </div>
        </div>

        <!-- Image + Messages -->
        <div class="col" style="margin-top: 2%;height: 70%;margin-left: 2%;padding: 0;">
            <!-- === AIRFLOW === -->
            <!-- Title -->
            <div class="subtitle" id="title-airflow">
                <%@include file="includes/bio_icon_airflow.jsp" %>
                <span>A&eacute;ration du milieu</span>
            </div>
            <div class="settings airflow" id="window-airflow">
                <div class="list-group">
                    <!-- flow  -->
                    <div class="list-group-item" id="sensor-AirOut">
                        <span>Gaz de sortie</span>
                        <div>Dioxyg&egrave;ne (O<sub>2</sub>) : <span class="float-right"><span id="o2Value"></span>N/A</span></div>
                        <div>Dioxyde de carbone (CO<sub>2</sub>) : <span class="float-right"><span id="co2Value"></span>N/A</span></div>
                    </div>
                    <!-- DO2 -->
                    <div class="list-group-item" id="sensor-AirIn">
                        <div>Dissolution O<sub>2</sub><span class="float-right"><span id="do2Value">40</span> %</span></div>
                    </div>
                    <!-- valves -->
                    <div class="list-group-item" id="actuator-AirIn">
                        <span>Débit d'air (%)</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" id="rangeDO2" min="0" max="100" oninput="inputDO2.value=rangeDO2.value">
                            </div>
                            <div class="col-3">
                                <input type="number" class="form-control" id="inputDO2" step="1" min="0" max="100" value="50" name="inputDO2" oninput="rangeDO2.value=inputDO2.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- === PH === -->
            <!-- Title -->
            <div class="subtitle" id="title-ph">
                <%@include file="includes/bio_icon_ph.jsp" %>
                <span>Acidit&eacute;/Basicit&eacute; du milieu</span>
            </div>
            <div class="settings ph" id="window-ph">
                <div class="list-group">
                    <div class="list-group-item" id="sensor-ph" style="background:white">
                        <div>
                            <span>pH du milieu</span>
                            <span class="float-right">Solution <span>neutre</span></span>
                        </div>
                        <ul id="phValues" class="list-group list-group-horizontal" style="margin-top: 2%;">
                            <li class="list-group-item phScale">0</li>
                            <li class="list-group-item phScale">1</li>
                            <li class="list-group-item phScale">2</li>
                            <li class="list-group-item phScale">3</li>
                            <li class="list-group-item phScale">4</li>
                            <li class="list-group-item phScale">5</li>
                            <li class="list-group-item phScale">6</li>
                            <li class="list-group-item phScale">7</li>
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
                    <div class="list-group-item" id="actuator-ph" style="background:white">
                        <span>R&eacute;gulation du pH</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" id="rangePH" min="0" max="14" oninput="inputPH.value=rangePH.value">
                            </div>
                            <div class="col-3">
                                <input class="form-control" type="number" id="inputPH" step="1" min="0" max="14" value="7" name="inputPH" oninput="rangePH.value=inputPH.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- SVG Image Bioreactor -->
        <div class="col-6" style="height: 95%;z-index: -1;">
            <div class="text-center mx-auto mt-2" style="width:85%;height: 100%;">
                <%@include file="includes/bio_main.jsp" %>
            </div>
        </div>
        <!-- WINDOWS Agitator & Temperature -->
        <div class="col-3" style="margin-top: 2%;height: 62%;padding-top: 1%;">
            <!-- === LIQUID === -->
            <!-- Title -->
            <div class="subtitle" id="title-liquid">
                <%@include file="includes/bio_icon_liquid.jsp" %>
                <span>Entr&eacute;e/Sortie de milieu</span>
            </div>
            <div class="settings liquid" id="window-liquid">
                <div class="list-group">
                    <!-- Liquid Out -->
                    <div class="list-group-item" id="sensor-liquidOut">
                        <div>Soutirage de milieu<span class="float-right"><span id="liquidValue"></span> N/A</span></div>
                    </div>
                    <!-- Liquid In -->
                    <div class="list-group-item" id="actuator-liquidIn">
                        <span>Injection de milieu frais</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" id="rangeliquid" min="0" max="100" oninput="inputliquid.value=rangeliquid.value">
                            </div>
                            <div class="col-3">
                                <input type="number" class="form-control" id="inputliquid" step="1" min="0" max="100" value="50" name="inputliquid" oninput="rangeliquid.value=inputliquid.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- === AGITATOR === -->
            <!-- Title -->
            <div class="subtitle" id="title-agitator">
                <%@include file="includes/bio_icon_agitator.jsp" %>
                <span>Agitateur</span>
            </div>
            <div class="settings agitator" id="window-agitator">
                <div class="list-group" id="agitator">
                    <div class="list-group-item" id="sensor-agitator" style="background:white">
                        <div>
                            <span>Vitesse de rotation</span>
                            <span class="float-right"><span id="agitatorValue">800</span> tr/min</span>
                        </div>
                    </div>
                    <div class="list-group-item" id="actuator-agitator" style="background:white">
                        <span>Agitation (tr/min)</span>
                        <div class="row">
                            <div style="width: 74%;padding: 0px 15px;">
                                <input type="range" class="custom-range" id="rangeAgitation" min="0" max="1200" oninput="inputAgitation.value=rangeAgitation.value">
                            </div>
                            <div class="col">
                                <input class="form-control" type="number" id="inputAgitation" step="10" min="0" max="1200" value="600" name="inputAgitation" oninput="rangeAgitation.value=inputAgitation.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- === TEMPERATURE === -->
            <!-- Title -->
            <div class="subtitle" id="title-temperature">
                <%@include file="includes/bio_icon_temperature.jsp" %>
                <span>Temp&eacute;rature du milieu</span>
            </div>
            <div class="settings temperature" id="window-temperature">
                <div class="list-group">
                    <div class="list-group-item" id="sensor-temperature" style="background:white">
                        <div>
                            <span>Temp&eacute;rature de la cuve</span>
                            <span class="float-right"><span id="tempValue">60</span> &deg;C</span>
                        </div>
                    </div>
                    <div class="list-group-item" id="heater" style="background:white">
                        <span>Couverture chauffante (&deg;C)</span>
                        <div class="row">
                            <div class="col">
                                <input type="range" class="custom-range" id="rangeTemp" min="0" max="90" oninput="inputTemp.value=rangeTemp.value">
                            </div>
                            <div class="col-3">
                                <input class="form-control" type="number" id="inputTemp" step="1" min="0" max="90" value="45" name="inputTemp" oninput="rangeTemp.value=inputTemp.value">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="border rounded mt-5 p-2 shadow" style="background:white">
                Buttons
                <div style="bottom: 10px;right: 10px;">
                    <input id="simulationSend" type="button" class="btn btn-primary" value="Send">
                    <input id="simulationRequest" type="button" class="btn btn-primary" value="Request">
                    <span id="simResponse"></span>
                </div>
            </div>
        </div>
    </div>

    <!-- Graphics -->
    <div class="row" id="rowGraphics" style="width: 100%;height: 93%; display: none;">
        <!-- SVG -->
        <div style="width: 23%;">
            <!-- X axis -->
            <div class="position-absolute" style="bottom: 16%;left: 24.5%;">
                <%@include file="includes/bio_graph_X.jsp" %>
            </div>
            <!-- Y axis -->
            <div class="position-absolute" style="bottom: 18.1%;left: 23.5%; z-index: 1;">
                <%@include file="includes/bio_graph_Y.jsp" %>
            </div>
            <div class="float-right">
                <!-- DO -->
                <%@include file="includes/bio_graph_scale_do.jsp" %>
                <!-- ph -->
                <%@include file="includes/bio_graph_scale_ph.jsp" %>
                <!-- Temperature -->
                <%@include file="includes/bio_graph_scale_temperature.jsp" %>
                <!-- Agitator -->
                <%@include file="includes/bio_graph_scale_agitator.jsp" %>
                <!-- Growth -->
                <%@include file="includes/bio_graph_scale_growth.jsp" %>
            </div>
        </div>
        <!-- Charts -->
        <div style="width: 74%;height:93%;">
            <div class="card border-0" style="height: 100%;background: transparent;margin-top: 1%;">
                <div class="card-body" style="height: 100%">
                    <canvas id="do2-Chart" style="max-width: 100%;"></canvas>
                    <canvas id="ph-Chart" style="display:none; max-width: 100%;"></canvas>
                    <canvas id="temp-Chart" style="display:none; max-width: 100%;"></canvas>
                    <canvas id="agitator-Chart" style="display:none; max-width: 100%;"></canvas>
                    <canvas id="growth-Chart" style="display:none; max-width: 100%;"></canvas>
                </div>
            </div>
        </div>
        <!-- Pick -->
<%--        List to pick which axis to display--%>
        <div class="position-absolute" id="pickSettings" style="bottom: 40%;left: 1%;">
            <div class="pickSettings settings">
                <div class="list-group" id="pickSetting" style="">
                    <div class="list-group-item" id="pickDo">
                        <span>DO</span>
                    </div>
                    <div class="list-group-item" id="pickPh">
                        <span>pH</span>
                    </div>
                    <div class="list-group-item" id="pickTemperature">
                        <span>Temp&eacute;rature</span>
                    </div>
                    <div class="list-group-item" id="pickAgitation">
                        <span>Agitation</span>
                    </div>
                    <div class="list-group-item" id="pickGrowth">
                        <span>Croissance</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%@include file="includes/functions-js.jsp"%>
<%@include file="includes/graph-js.jsp"%>
</body>
