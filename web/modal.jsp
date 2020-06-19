<%--
  Created by IntelliJ IDEA.
  User: Loïc TRAMIS
  Date: 13/06/2020
  Time: 01:26
  Copyright © 2020 All rights reserved.
--%>
<div class="modal fade" id="modalSettings" tabindex="-1" role="dialog" aria-labelledby="modalSettingLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <!-- Title -->
                <div class="mx-auto">
                    <img src="img/bio_Settings.svg" alt=""/>
                    <span>Param&egrave;tres</span>
                </div>
            </div>
            <div class="modal-body">
                <!-- PID -->
                <div class="row">
                    <div class="col-6">
                        <!-- Subtitle -->
                        <div class="text-center pb-2">
                            <%@include file="includes/settings/bio_setting_pid.jsp"%>
                            <span style="font-weight: 500;">Process ID</span>
                        </div>
                        <!-- Switches -->
                        <ul class="list-group listPID">
                            <li class="list-group-item" style="display: inline-flex;">
                                <div style="width: 15%;">
                                    <%@include file="includes/settings/bio_setting_pid_do.jsp"%>
                                </div>
                                <label for="switchPID1" style="text-align: left;margin: 0;width: 88%;padding-top: 5px;">A&eacute;ration</label>
                                <div class="custom-control custom-switch float-right" style="display: inline;padding-top: 5px;">
                                    <input type="checkbox" class="custom-control-input" id="switchPID1" name="switchPID1" onchange="getSwitchPID1()">
                                    <input type="hidden" id="valuePID1" name="valuePID1" value="0">
                                    <label class="custom-control-label" for="switchPID1"></label>
                                </div>
                            </li>
                            <li class="list-group-item" style="display: inline-flex;">
                                <div style="width: 15%;">
                                    <%@include file="includes/settings/bio_setting_pid_ph.jsp"%>
                                </div>
                                <label for="switchPID2" style="text-align: left;margin: 0;width: 88%;padding-top: 5px;">pH</label>
                                <div class="custom-control custom-switch float-right" style="display: inline;padding-top: 5px;">
                                    <input type="checkbox" class="custom-control-input" id="switchPID2" name="switchPID2" onchange="getSwitchPID2()">
                                    <input type="hidden" id="valuePID2" name="valuePID2" value="0">
                                    <label class="custom-control-label" for="switchPID2"></label>
                                </div>
                            </li>
                            <li class="list-group-item" style="display: inline-flex;">
                                <div style="width: 15%;">
                                    <%@include file="includes/settings/bio_setting_pid_temperature.jsp"%>
                                </div>
                                <label for="switchPID3" style="text-align: left;margin: 0;width: 88%;padding-top: 5px;">Temp&eacute;rature</label>
                                <div class="custom-control custom-switch float-right" style="display: inline;padding-top: 5px;">
                                    <input type="checkbox" class="custom-control-input" id="switchPID3" name="switchPID3" onchange="getSwitchPID3()">
                                    <input type="hidden" id="valuePID3" name="valuePID3" value="0">
                                    <label class="custom-control-label" for="switchPID3"></label>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!-- Automation -->
                    <div class="col-6">
                        <!-- Subtitle -->
                        <div class="text-center pb-2">
                            <%@include file="includes/settings/bio_setting_ia.jsp"%>
                            <span style="font-weight: 500;">Intelligences Artificielles</span>
                        </div>
                        <!-- Switches -->
                        <ul class="list-group listAI">
                            <!-- Optimisation switch -->
                            <li class="list-group-item" style="display: inline-flex;">
                                <div style="width: 15%;">
                                    <%@include file="includes/settings/bio_setting_ia_opti.jsp"%>
                                </div>
                                <label for="switchOptimisation" style="text-align: left;margin: 0;width: 88%;padding-top: 5px;">Optimisation</label>
                                <div class="custom-control custom-switch float-right" style="display: inline;padding-top: 5px;">
                                    <input type="checkbox" class="custom-control-input" id="switchOptimisation" name="switchOptimisation" onchange="getSwitchOpti()">
                                    <input type="hidden" id="valueOptimisation" name="valueOptimisation" value="0">
                                    <label class="custom-control-label" for="switchOptimisation"></label>
                                </div>
                            </li>
                            <li class="list-group-item" style="display: inline-flex;">
                                <!-- Fault Detection switch -->
                                <div style="width: 15%;">
                                    <%@include file="includes/settings/bio_setting_ia_fdi.jsp"%>
                                </div>
                                <label for="switchFaultDetection" style="text-align: left;margin: 0;width: 88%;padding-top: 5px;">D&eacute;tection d'erreur</label>
                                <div class="custom-control custom-switch float-right" style="display: inline;padding-top: 5px;">
                                    <input type="checkbox" class="custom-control-input" id="switchFaultDetection" name="switchFaultDetection" onchange="getSwitchFault()">
                                    <input type="hidden" id="valueFaultDetection" name="valueFaultDetection" value="0">
                                    <label class="custom-control-label" for="switchFaultDetection"></label>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>