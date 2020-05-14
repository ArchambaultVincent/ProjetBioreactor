<%--
  Created by IntelliJ IDEA.
  User: Loic
  Date: 28/02/2020
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
  <%@include file="includes/header.jsp"%>
<body>
<div class="container-fluid" style="background: linear-gradient(#4fc2ef, #91f9a5); height: 100vh;width:100vw;padding-top: 10%;">
  <div class="border rounded position-absolute" style="background: white; height:500px; width: 70%; left:15%;">
    <!-- Title -->
    <div class="text-center">
      <h1 class="align-content-center">Bior&eacute;acteur</h1>
    </div>
    <!-- Mode -->
    <div style="padding-left: 35%;">
      <div class="list-group list-group-horizontal-lg" id="list-modes" role="tablist">
        <a class="list-group-item list-group-item-action active" id="list-man" href="#" style="width: 200px; text-align: center;">Mode manuel</a>
        <a class="list-group-item list-group-item-action" id="list-auto" href="#" style="width: 200px; text-align: center;">Mode automatique</a>
      </div>
    </div>

<%--    <div class="tab-content text-center" id="nav-tabContent">--%>
<%--      <div class="tab-pane active" id="text-man" role="tabpanel">Le mode manuel permet de choisir les param&egrave;tres du bior&eacute;acteur.</div>--%>
<%--      <div class="tab-pane" id="text-auto" role="tabpanel">Le mode automatique permet d'optimiser la culture de bio masse et d'&eacute;viter les incidents du bior&eacute;acteur.</div>--%>
<%--    </div>--%>
    <!-- Form -->
    <div class="row pl-2">
      <!-- Parameters -->
      <div class="col-6">
        <!-- Subtitle -->
        <div class="text-center">
          <h3>Param&egrave;tres</h3>
        </div>
        <div>
          <form>
            <label for="rangePH">pH</label>
            <div class="row">
              <div class="col">
                <input type="range" class="custom-range" min="0" max="14" id="rangePH" oninput="inputPH.value=rangePH.value">
              </div>
              <div class="col-2">
                <input class="form-control" type="number" step="1" min="0" max="14" id="inputPH" value="7" oninput="rangePH.value=inputPH.value">
              </div>
            </div>
            <label for="rangeTemp">Temp&eacute;rature - &deg;C</label>
            <div class="row">
              <div class="col">
                <input type="range" class="custom-range" min="0" max="100" id="rangeTemp" oninput="inputTemp.value=rangeTemp.value">
              </div>
              <div class="col-2">
                <input class="form-control" type="number" step="1" min="0" max="100" id="inputTemp" value="50" oninput="rangeTemp.value=inputTemp.value">
              </div>
            </div>
            <label for="rangeDO2">DO<spans>2</spans> - %</label>
            <div class="row">
              <div class="col">
                <input type="range" class="custom-range" min="0" max="100" id="rangeDO2" oninput="inputDO2.value=rangeDO2.value">
              </div>
              <div class="col-2">
                <input class="form-control" type="number" step="1" min="0" max="100" id="inputDO2" value="50" oninput="rangeDO2.value=inputDO2.value">
              </div>
            </div>
          </form>
        </div>
      </div>
      <!-- Automation -->
      <div class="col-6">
        <!-- Subtitle -->
        <div class="text-center">
          <h3>Intelligences Artificielles</h3>
        </div>
        <!-- Switches -->
        <div style="margin-top: 100px;padding-left: 35%;">
        <!-- Optimisation switch -->
          <div class="custom-control custom-switch">
            <input type="checkbox" class="custom-control-input" id="switchOptimisation">
            <label class="custom-control-label" for="switchOptimisation">Optimisation</label>
          </div>
          <!-- Fault Detection switch -->
          <div class="custom-control custom-switch">
            <input type="checkbox" class="custom-control-input" id="switchFaultDetection">
            <label class="custom-control-label" for="switchFaultDetection">D&eacute;tection d'erreur</label>
          </div>
        </div>
      </div>
    </div>
    <!-- Buttons -->
    <div class="position-absolute" style="bottom: 10px;right: 10px;">
      <button type="button" class="btn btn-primary">Annuler</button>
      <button type="button" class="btn btn-primary">Lancer</button>
    </div>
  </div>


</div>
<%@include file="includes/functions-js.jsp"%>
<%@include file="includes/transition-js.jsp"%>
<%@include file="includes/graph-js.jsp"%>
</body>
