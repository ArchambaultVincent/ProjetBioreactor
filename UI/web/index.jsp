<%--
  Created by IntelliJ IDEA.
  User: Loic
  Date: 28/02/2020
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
  <%@include file="includes/header.jsp"%>
<body>
<div class="container-fluid" style="background: linear-gradient(#4fc2ef, #91f9a5); height: 100vh;width:100vw">
  <%@include file="includes/nav.jsp"%>
  <div class="row" style="padding-top: 3.25%;">
    <div class="col-9">
      <div class="border rounded shadow-sm mb-1" style="background-color: #e3f2fd;">
        <canvas id="do2-Chart" style="height:30vh; width:100%;"></canvas>
      </div>
      <div class="border rounded shadow-sm mb-1" style="background-color: #e3f2fd;">
        <canvas id="ph-Chart" style="height:30vh; width:100%;"></canvas>
      </div>
      <div class="border rounded shadow-sm" style="background-color: #e3f2fd;">
        <canvas id="temp-Chart" style="height:30vh; width:100%;"></canvas>
      </div>
    </div>
    <div class="col-3">
      <div class="border rounded" style="height:100%; background-color: #e3f2fd;">
        <ul class="list-group" >
          <li class="list-group-item border-0" style="background-color: #e3f2fd;"><strong>Stats</strong></li>
          <li class="list-group-item border-0" style="background-color: #e3f2fd;">DO2</li>
          <li class="list-group-item border-0" style="background-color: #e3f2fd;">pH</li>
          <li class="list-group-item border-0" style="background-color: #e3f2fd;">Temperature</li>
        </ul>
      </div>
    </div>
  </div>

</div>
<%@include file="includes/transition-js.jsp"%>
<%@include file="includes/graph-js.jsp"%>
</body>
