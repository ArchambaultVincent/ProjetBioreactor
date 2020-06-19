<%--
  Created by IntelliJ IDEA.
  User: clort
  Date: 13/05/2020
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>

    // Switches for PID
    let pid1 = $('#switchPID1');
    let pid2 = $('#switchPID2');
    let pid3 = $('#switchPID3');
    let pid4 = $('#switchPID4');
    // Switches for IA
    let opti = $('#switchOptimisation');
    let fdi = $('#switchFaultDetection');

    // Get the values in the cookies
    $('#switchPID1').val(0);
    $('#switchPID2').val(0);
    $('#switchPID3').val(0);
    $('#switchPID4').val(0);
    $('#switchOptimisation').val(0);
    $('#switchFaultDetection').val(0);

    /* ==== FUNCTIONS ==== */
    function getSwitchPID1() {
        if ($('#switchPID1').is(":checked")) {
            $('#valuePID1').val(1);
            $('.listPID li:nth-child(1)').addClass('borderActive');
            $('.listPID li:nth-child(1)').css({backgroundColor: '#d0e0ff', color: '#007bff'});
            $('#settingPidDo').css({fill: '#007bff'});
            $('#actuator-AirIn').addClass('disabled');
            $('#actuator-AirIn').css({backgroundColor: '#d2d2d2'});
        } else {
            $('#valuePID1').val(0);
            $('.listPID li:nth-child(1)').removeClass('borderActive');
            $('.listPID li:nth-child(1)').css({backgroundColor: 'white', color: '#212529'});
            $('#settingPidDo').css({fill: '#808080'});
            $('#actuator-AirIn').removeClass('disabled');
            $('#actuator-AirIn').css({backgroundColor: 'white'});
        }
    }
    function getSwitchPID2() {
        if ($('#switchPID2').is(":checked")) {
            $('#valuePID2').val(1);
            $('.listPID li:nth-child(2)').addClass('borderActive');
            $('.listPID li:nth-child(2)').css({backgroundColor: '#d0e0ff', color: '#007bff'});
            $('#settingPidPh').css({fill: '#007bff'});
            $('#actuator-ph').addClass('disabled');
            $('#actuator-ph').css({backgroundColor: '#d2d2d2'});
        } else {
            $('#valuePID2').val(0);
            $('.listPID li:nth-child(2)').removeClass('borderActive');
            $('.listPID li:nth-child(2)').css({backgroundColor: 'white', color: '#212529'});
            $('#settingPidPh').css({fill: '#808080'});
            $('#actuator-ph').removeClass('disabled');
            $('#actuator-ph').css({backgroundColor: 'white'});
        }
    }
    function getSwitchPID3() {
        if ($('#switchPID3').is(":checked")) {
            $('#valuePID3').val(1)
            $('.listPID li:nth-child(3)').addClass('borderActive');
            $('.listPID li:nth-child(3)').css({backgroundColor: '#d0e0ff', color: '#007bff'});
            $('#settingPidTemp').css({fill: '#007bff'});
            $('#heater').addClass('disabled');
            $('#heater').css({backgroundColor: '#d2d2d2'});
        } else {
            $('#valuePID3').val(0)
            $('.listPID li:nth-child(3)').removeClass('borderActive');
            $('.listPID li:nth-child(3)').css({backgroundColor: 'white', color: '#212529'});
            $('#settingPidTemp').css({fill: '#808080'});
            $('#heater').removeClass('disabled');
            $('#heater').css({backgroundColor: 'white'});
        }
    }
    /* Change the value of INPUT HIDDEN of valueOptimisation */
    function getSwitchOpti() {
        if ($('#switchOptimisation').is(":checked")) {
            $('#valueOptimisation').val(1);
            $('.listAI li:nth-child(1)').addClass('borderActive');
            $('.listAI li:nth-child(1)').css({backgroundColor: '#d0e0ff', color: '#007bff'});
            $('#settingOpti').css({fill: '#007bff'})
        } else {
            $('#valueOptimisation').val(0);
            $('.listAI li:nth-child(1)').removeClass('borderActive');
            $('.listAI li:nth-child(1)').css({backgroundColor: 'white', color: '#212529'});
            $('#settingOpti').css({fill: '#808080'})
        }
    }
    /* Change the value of INPUT HIDDEN of valueFaultDetection */
    function getSwitchFault() {
        if ($('#switchFaultDetection').is(":checked")) {
            $('#valueFaultDetection').val(1)
            $('.listAI li:nth-child(2)').addClass('borderActive');
            $('.listAI li:nth-child(2)').css({backgroundColor: '#d0e0ff', color: '#007bff'});
            $('#settingFdi').css({fill: '#007bff'})
        } else {
            $('#valueFaultDetection').val(0)
            $('.listAI li:nth-child(2)').removeClass('borderActive');
            $('.listAI li:nth-child(2)').css({backgroundColor: 'white', color: '#212529'});
            $('#settingFdi').css({fill: '#808080'})
        }
    }

    /* === NAV SWITCHES === */
    $('#navBioreactor').on('click', function() {
        $("#navBioreactor").addClass('active');
        $("#rowBioreactor").show();
        $("#navGraphics").removeClass('active');
        $('#rowGraphics').hide();
    });
    $('#navGraphics').on('click', function() {
        $("#navGraphics").addClass('active');
        $('#rowGraphics').show();
        $("#navBioreactor").removeClass('active');
        $("#rowBioreactor").hide();
    });
    /* === SLIDERS === */
    /* ---- DO ---- */
    $('#rangeDO2, #inputDO2').on(('change'), function(value) {
        let valueDO = $('#inputDO2').val();
        $.ajax({
            url: 'ClientSendSocketUI',
            data: {
                type: "D",
                value: valueDO
            },
            success: function(response) {
                /* alerte success */
                console.log("inputDO2 on change");
            },
            error: function(response) {
                /* alerte error */
                console.log("error");
            }
        });
    });
    /* --- ph --- */
    $('#rangePH, #inputPH').on(('change'), function(value) {
        let valuePH = $('#rangePH').val();
        $.ajax({
            url: 'ClientSendSocketUI',
            data: {
                type: "P",
                value: valuePH
            },
            success: function(response) {
                /* alerte success */
                console.log("inputPH on change");
            },
            error: function(response) {
                /* alerte error */
                console.log("error");
            }
        });
    });
    /* --- Liquis --- */
    /* --- Agitator --- */
    $('#rangeAgitation, #inputAgitation').on(('change'), function(value) {
        let valueAgitation = $('#inputAgitation').val();
        $.ajax({
            url: 'ClientSendSocketUI',
            data: {
                type: "S",
                value: valueAgitation
            },
            success: function(response) {
                /* alerte success */
                console.log("inputAgitation on change");
            },
            error: function(response) {
                /* alerte error */
                console.log("error");
            }
        });
    });
    /* --- Temperature --- */
    $('#rangeTemp, #inputTemp').on(('change'), function() {
        let valueTemp = $('#inputTemp').val();
        $.ajax({
            url: 'ClientSendSocketUI',
            data: {
                type: "T",
                value: valueTemp
            },
            success: function(response) {
                /* alerte success */
                runSimulation();
                console.log("inputTemp on change");
            },
            error: function(response) {
                /* alerte error */
                console.log("error");
            }
        });
    });

    let newError = true;
    let previousValue = -1;


    $('#simulationRequest').on('click', function() {
        setInterval(request,3000);
    });

    function request() {
        $.ajax({
            url: 'ClientSocketUI',
            data: {
                opti: $('#valueOptimisation').val(),
                fdi: $('#valueFaultDetection').val()
            },
            success: function(responseText) {
                console.log("request succeed");
                /* === Update values === */
                let datas = responseText.split(";");
                /* -- ph -- */
                let phValue = parseInt(datas[0]);
                /* Remove all active element */
                for (let i = 0; i <= 14; i++) {
                    $('#phValues li').eq(i).removeClass("active");
                }
                $('#phValues li').eq(phValue).addClass("active");
                /* -- Temperature -- */
                $("#tempValue").text((datas[1]));
                /* -- Airflow -- */
                $("#do2Value").text(Math.round(datas[2]*100));
                /* -- Agitator -- */
                let rotation = $('#inputAgitation').val();
                $("#agitatorValue").text(rotation);
                /* === Update graphics === */

                graphChart.data.labels.push('');
                graphChart.data.datasets.forEach((dataset) => {
                    // Adding data
                    if (dataset.label == 'pH') {
                        dataset.data.push(datas[0]/14*100);
                    }
                    if (dataset.label == 'Temperature') {
                        dataset.data.push(datas[1]);
                    }
                    if (dataset.label == 'DO2') {
                        dataset.data.push(datas[2]*100);
                    }
                    if (dataset.label == 'Agitator') {
                        dataset.data.push(rotation/1200*100);
                    }
                    if (dataset.label == 'Growth') {
                        dataset.data.push(datas[3]*100*100);
                    }

                });
                graphChart.update();

                switch (datas[5]) {
                    // Temperature error
                    case "0":
                        $('#modalFdi').modal('show');
                        $('#fdiMessage').text('Erreur de température');
                        break;
                    // Temperature error
                    case "1":
                        $('#modalFdi').modal('show');
                        $('#fdiMessage').text('Erreur de température');
                        break;
                    // pH error
                    case "2":
                        if (newError) {
                            $('#modalFdi').modal('show');
                            $('#fdiMessage').text('Erreur de pH');
                            newError = false
                        }
                        break;
                        default:break;
                }
            },
            error: function(error) {
                console.log("request failed");
                console.log(error);
            }
        });
    }

    /* === HIGHLIGHTING Window === */
    /* --- Airflow --- */
    $('#window-airflow').hover(
        function() {
            $('#title-airflow').css({color:"#2c5aa0"});
            $('.svg-icon-airflow').css({fill:"#2c5aa0"});
            $('#window-airflow').css({borderColor:"#2c5aa0"});

        },
        function() {
            $('#title-airflow').css({color:"#808080"});
            $('.svg-icon-airflow').css({fill:"#808080"});
            $('#window-airflow').css({borderColor:"#808080"});
        }
    );
    /* --- pH --- */
    $('#window-ph').hover(
        function() {
            $('#title-ph').css({color:"#2c5aa0"});
            $('.svg-icon-ph').css({fill:"#2c5aa0"});
            $('#window-ph').css({borderColor:"#2c5aa0"});
        },
        function() {
            $('#title-ph').css({color:"#808080"});
            $('.svg-icon-ph').css({fill:"#808080"});
            $('#window-ph').css({borderColor:"#808080"});
        }
    );
    /* --- Liquid --- */
    $('#window-liquid').hover(
        function() {
            $('#title-liquid').css({color:"#2c5aa0"});
            $('.svg-icon-liquid').css({fill:"#2c5aa0"});
            $('#window-liquid').css({borderColor:"#2c5aa0"});
        },
        function() {
            $('#title-liquid').css({color:"#808080"});
            $('.svg-icon-liquid').css({fill:"#808080"});
            $('#window-liquid').css({borderColor:"#808080"});
        }
    );
    /* --- Agitator--- */
    $('#window-agitator').hover(
        function() {
            $('#title-agitator').css({color:"#2c5aa0"});
            $('.svg-icon-agitator').css({fill:"#2c5aa0"});
            $('#window-agitator').css({borderColor:"#2c5aa0"});
        },
        function() {
            $('#title-agitator').css({color:"#808080"});
            $('.svg-icon-agitator').css({fill:"#808080"});
            $('#window-agitator').css({borderColor:"#808080"});
        }
    );
    /* --- Temperature --- */
    $('#window-temperature').hover(
        function() {
            $('#title-temperature').css({color:"#2c5aa0"});
            $('.svg-icon-temperature').css({fill:"#2c5aa0"});
            $('#window-temperature').css({borderColor:"#2c5aa0"});
        },
        function() {
            $('#title-temperature').css({color:"#808080"});
            $('.svg-icon-temperature').css({fill:"#808080"});
            $('#window-temperature').css({borderColor:"#808080"});
        }
    );
    /* === HIGHLIGHTING SubTitle === */
    /* --- Airflow --- */
    $('#title-airflow').hover(
        function() {
            $('#window-airflow').css({borderColor:"#2c5aa0"});
            $('.svg-icon-airflow').css({fill:"#2c5aa0"});
            $('#title-airflow').css({color:"#2c5aa0"});
        },
        function() {
            $('#window-airflow').css({borderColor:"#808080"});
            $('.svg-icon-airflow').css({fill:"#808080"});
            $('#title-airflow').css({color:"#808080"});
        }
    );
    /* --- pH --- */
    $('#title-ph').hover(
        function() {
            $('#window-ph').css({borderColor:"#2c5aa0"});
            $('.svg-icon-ph').css({fill:"#2c5aa0"});
            $('#title-ph').css({color:"#2c5aa0"});
        },
        function() {
            $('#window-ph').css({borderColor:"#808080"});
            $('.svg-icon-ph').css({fill:"#808080"});
            $('#title-ph').css({color:"#808080"});
        }
    );
    /* --- Liquid --- */
    $('#title-liquid').hover(
        function() {
            $('#window-liquid').css({borderColor:"#2c5aa0"});
            $('.svg-icon-liquid').css({fill:"#2c5aa0"});
            $('#title-liquid').css({color:"#2c5aa0"});
        },
        function() {
            $('#window-liquid').css({borderColor:"#808080"});
            $('.svg-icon-liquid').css({fill:"#808080"});
            $('#title-liquid').css({color:"#808080"});
        }
    );
    /* --- Agitator--- */
    $('#title-agitator').hover(
        function() {
            $('#title-agitator').css({color:"#2c5aa0"});
            $('.svg-icon-agitator').css({fill:"#2c5aa0"});
            $('#window-agitator').css({borderColor:"#2c5aa0"});
        },
        function() {
            $('#title-agitator').css({color:"#808080"});
            $('.svg-icon-agitator').css({fill:"#808080"});
            $('#window-agitator').css({borderColor:"#808080"});
        }
    );
    /* --- Temperature --- */
    $('#title-temperature').hover(
        function() {
            $('#title-temperature').css({color:"#2c5aa0"});
            $('.svg-icon-temperature').css({fill:"#2c5aa0"});
            $('#window-temperature').css({borderColor:"#2c5aa0"});
        },
        function() {
            $('#title-temperature').css({color:"#808080"});
            $('.svg-icon-temperature').css({fill:"#808080"});
            $('#window-temperature').css({borderColor:"#808080"});
        }
    );
    /* === SVG === */ /* error aa0000 */
    /* --- Airflow --- */
    $('#sensor-AirOut').hover(
        function() {
            $('.svg-airOut').css({fill: "#2c5aa0"});
            $('#line-airOut').show();
            $('.svg-arrow-airOut').show();
            $('#sensor-AirOut').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-airOut').css({fill: "#808080"});
            $('#line-airOut').hide();
            $('.svg-arrow-airOut').hide();
            $('#sensor-AirOut').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    $('#actuator-AirIn').hover(
        function() {
            $('.svg-airIn').css({fill: "#2c5aa0", stroke: "#2c5aa0"});
            $('#line-airIn').show();
            $('.svg-arrow-airIn').show();
            $('#actuator-AirIn').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-airIn').css({fill: "#808080", stroke: "#808080"});
            $('#line-airIn').hide();
            $('.svg-arrow-airIn').hide();
            $('#actuator-AirIn').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    $('#sensor-AirIn').hover(
        function() {
            $('.svg-sensors').css({fill: "#2c5aa0"});
            $('.svg-text-do').css({fillOpacity:1, fill: "#2c5aa0ff"});
            $('#line-sensor-air').show();
            $('#sensor-AirIn').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-sensors').css({fill: "#808080"});
            $('.svg-text-do').css({fillOpacity:0, fill: "#00000000"});
            $('#line-sensor-air').hide();
            $('#sensor-AirIn').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    /* + text for sensor */
    /* --- pH --- */
    $('#sensor-ph').hover(
        function() {
            $('.svg-sensors').css({fill: "#2c5aa0"});
            $('.svg-text-ph').css({fillOpacity:1, fill: "#2c5aa0ff"});
            $('#line-sensor-ph').show();
            $('#sensor-ph').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-sensors').css({fill: "#808080"});
            $('.svg-text-ph').css({fillOpacity:0, fill: "#00000000"});
            $('#line-sensor-ph').hide();
            $('#sensor-ph').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    /* + text for sensor */
    $('#actuator-ph').hover(
        function() {
            $('.svg-ph').css({fill: "#2c5aa0"});
            $('#line-ph').show();
            $('.svg-arrow-phIn').show();
            $('#actuator-ph').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-ph').css({fill: "#808080"});
            $('#line-ph').hide();
            $('.svg-arrow-phIn').hide();
            $('#actuator-ph').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    /* --- Agitator--- */
    $('#sensor-liquidOut').hover(
        function() {
            $('.svg-liquidOut').css({fill: "#2c5aa0"});
            $('#line-liquidOut').show();
            $('.svg-arrow-liquidOut').show();
            $('#sensor-liquidOut').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-liquidOut').css({fill: "#808080"});
            $('#line-liquidOut').hide();
            $('.svg-arrow-liquidOut').hide();
            $('#sensor-liquidOut').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    $('#actuator-liquidIn').hover(
        function() {
            $('.svg-liquidIn').css({fill: "#2c5aa0"});
            $('#line-liquidIn').show();
            $('.svg-arrow-liquidIn').show();
            $('#actuator-liquidIn').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-liquidIn').css({fill: "#808080"});
            $('#line-liquidIn').hide();
            $('.svg-arrow-liquidIn').hide();
            $('#actuator-liquidIn').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    /* --- Agitator--- */
    $('#sensor-agitator').hover(
        function() {
            $('.svg-agitator').css({fill: "#2c5aa0"});
            $('#line-agitator').show();
            $('#sensor-agitator').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-agitator').css({fill: "#808080"});
            $('#line-agitator').hide();
            $('#sensor-agitator').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    $('#actuator-agitator').hover(
        function() {
            $('.svg-agitator').css({fill: "#2c5aa0"});
            $('#line-agitator').show();
            $('#actuator-agitator').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-agitator').css({fill: "#808080"});
            $('#line-agitator').hide();
            $('#actuator-agitator').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    /* --- Temperature --- */
    $('#sensor-temperature').hover(
        function() {
            $('.svg-sensors').css({fill: "#2c5aa0"});
            $('.svg-text-temperature').css({fillOpacity:1, fill: "#2c5aa0"});
            $('#line-temperature').show();
            $('#sensor-temperature').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-sensors').css({fill: "#808080"});
            $('.svg-text-temperature').css({fillOpacity:0, fill: "#000000"});
            $('#line-temperature').hide();
            $('#sensor-temperature').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    /* + text for sensor */
    $('#heater').hover(
        function() {
            $('.svg-heater').css({fill: "#2c5aa0", stroke: "#2c5aa0"});
            $('#line-heater').show();
            $('#heater').css({backgroundColor: '#d2f0ff', borderColor: '#2f81ff'});
        },
        function() {
            $('.svg-heater').css({fill: "#808080", stroke: "#808080"});
            $('#line-heater').hide();
            $('#heater').css({backgroundColor: 'white', borderColor: '#808080'});
        }
    );
    /* === LINES === */ /* error aa0000 */
    /* --- Airflow --- */
    /* --- pH --- */
    /* --- Agitator--- */
    /* --- Temperature --- */

    /* Display lines only if window is FHD */
    if(window.innerWidth == 1920) {
        $('#lines-bio').show()
    } else {
        $('#lines-bio').hide()
    }

    $( document ).on( "mousemove", function( event ) {
        $( "#simResponse" ).text( "pageX: " + event.pageX + ", pageY: " + event.pageY );
    });

    /* ====== GRAPH PAGE ====== */
    let doClicked = false;
    let phClicked = false;
    let temperatureClicked = false;
    let agitatorClicked = false;
    let growthClicked = false;

    /* -- DO -- */
    $('#pickDo').hover(
        function () {
            if (!doClicked) {
                $('#pickDo').addClass('hovered');
                $('#scaleDo').show();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'DO2') {
                        dataset.borderColor = "rgb(44, 90, 160, 1)";
                    }
                });
                graphChart.update();
            }
        },
        function () {
            if (!doClicked) {
                $('#pickDo').removeClass('hovered');
                $('#scaleDo').hide();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'DO2') {
                        dataset.borderColor = "rgb(128, 128, 128, 1)";
                    }
                });
                graphChart.update();
            }
        }
    );
    $('#pickDo').on('click', function() {
        if (doClicked) {
            $('#pickDo').removeClass('clicked');
            $('#pickDo').addClass('hovered');
            doClicked = false;
        } else {
            $('#pickDo').addClass('clicked');
            $('#pickDo').removeClass('hovered');
            $('#scalePh').hide();
            $('#scaleTemperature').hide();
            $('#scaleAgitator').hide();
            $('#scaleGrowth').hide();
            doClicked = true;
        }
        if (phClicked) {phClicked = false;}
        if (temperatureClicked) {temperatureClicked = false;}
        if (agitatorClicked) {agitatorClicked = false;}
        if (growthClicked) {growthClicked = false;}
        $('#pickPh').removeClass('clicked');
        $('#pickTemperature').removeClass('clicked');
        $('#pickAgitation').removeClass('clicked');
        $('#pickGrowth').removeClass('clicked');
    });

    /* -- pH -- */
    $('#pickPh').hover(
        function () {
            if (!phClicked) {
                $('#pickPh').addClass('hovered');
                $('#scalePh').show();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'pH') {
                        dataset.borderColor = "rgb(44, 90, 160, 1)";
                    }
                });
                graphChart.update();
            }
        },
        function () {
            if (!phClicked) {
                $('#pickPh').removeClass('hovered');
                $('#scalePh').hide();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'pH') {
                        dataset.borderColor = "rgb(128, 128, 128, 1)";
                    }
                });
                graphChart.update();
            }
        }
    );
    $('#pickPh').on('click', function() {
        if (phClicked) {
            $('#pickPh').removeClass('clicked');
            $('#pickPh').addClass('hovered');
            phClicked = false;
        } else {
            $('#pickPh').addClass('clicked');
            $('#pickPh').removeClass('hovered');
            $('#scaleDo').hide();
            $('#scaleTemperature').hide();
            $('#scaleAgitator').hide();
            $('#scaleGrowth').hide();
            phClicked = true;
        }
        if (doClicked) {doClicked = false;}
        if (temperatureClicked) {temperatureClicked = false;}
        if (agitatorClicked) {agitatorClicked = false;}
        if (growthClicked) {growthClicked = false;}
        $('#pickDo').removeClass('clicked');
        $('#pickTemperature').removeClass('clicked');
        $('#pickAgitation').removeClass('clicked');
        $('#pickGrowth').removeClass('clicked');
    });

    /* -- Temperature -- */
    $('#pickTemperature').hover(
        function () {
            if (!temperatureClicked) {
                $('#pickTemperature').addClass('hovered');
                $('#scaleTemperature').show();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'Temperature') {
                        dataset.borderColor = "rgb(44, 90, 160, 1)";
                    }
                });
                graphChart.update();
            }
        },
        function () {
            if (!temperatureClicked) {
                $('#pickTemperature').removeClass('hovered');
                $('#scaleTemperature').hide();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'Temperature') {
                        dataset.borderColor = "rgb(128, 128, 128, 1)";
                    }
                });
                graphChart.update();
            }
        }
    );
    $('#pickTemperature').on('click', function() {
        if (temperatureClicked) {
            $('#pickTemperature').removeClass('clicked');
            $('#pickTemperature').addClass('hovered');
            temperatureClicked = false;
        } else {
            $('#pickTemperature').addClass('clicked');
            $('#pickTemperature').removeClass('hovered');
            $('#scaleDo').hide();
            $('#scalePh').hide();
            $('#scaleAgitator').hide();
            $('#scaleGrowth').hide();
            temperatureClicked = true;
        }
        if (doClicked) {doClicked = false;}
        if (phClicked) {phClicked = false;}
        if (agitatorClicked) {agitatorClicked = false;}
        if (growthClicked) {growthClicked = false;}
        $('#pickDo').removeClass('clicked');
        $('#pickPh').removeClass('clicked');
        $('#pickAgitation').removeClass('clicked');
        $('#pickGrowth').removeClass('clicked');
    });

    /* -- Agitator -- */
    $('#pickAgitation').hover(
        function () {
            if(!agitatorClicked) {
                $('#pickAgitation').addClass('hovered');
                $('#scaleAgitator').show();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'Agitator') {
                        dataset.borderColor = "rgb(44, 90, 160, 1)";
                    }
                });
                graphChart.update();
            }
        },
        function () {
            if (!agitatorClicked) {
                $('#pickAgitation').removeClass('hovered');
                $('#scaleAgitator').hide();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'Agitator') {
                        dataset.borderColor = "rgb(128, 128, 128, 1)";
                    }
                });
                graphChart.update();
            }
        }
    );
    $('#pickAgitation').on('click', function() {
        if (agitatorClicked) {
            $('#pickAgitation').removeClass('clicked');
            $('#pickAgitation').addClass('hovered');
            agitatorClicked = false;
        } else {
            $('#pickAgitation').addClass('clicked');
            $('#pickAgitation').removeClass('hovered');
            $('#scaleDo').hide();
            $('#scalePh').hide();
            $('#scaleTemperature').hide();
            $('#scaleGrowth').hide();
            agitatorClicked = true;
        }
        if (doClicked) {doClicked = false;}
        if (phClicked) {phClicked = false;}
        if (temperatureClicked) {temperatureClicked = false;}
        if (growthClicked) {growthClicked = false;}
        $('#pickDo').removeClass('clicked');
        $('#pickPh').removeClass('clicked');
        $('#pickTemperature').removeClass('clicked');
        $('#pickGrowth').removeClass('clicked');
    });

    /* -- Growth -- */
    $('#pickGrowth').hover(
        function () {
            if (!growthClicked) {
                $('#pickGrowth').addClass('hovered');
                $('#scaleGrowth').show();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'Growth') {
                        dataset.borderColor = "rgb(44, 90, 160, 1)";
                    }
                });
                graphChart.update();
            }
        },
        function () {
            if (!growthClicked) {
                $('#pickGrowth').removeClass('hovered');
                $('#scaleGrowth').hide();
                graphChart.data.datasets.forEach((dataset) => {
                    if (dataset.label == 'Growth') {
                        dataset.borderColor = "rgb(128, 128, 128, 1)";
                    }
                });
                graphChart.update();
            }
        }
    );
    $('#pickGrowth').on('click', function() {
        if (growthClicked) {
            $('#pickGrowth').removeClass('clicked');
            $('#pickGrowth').addClass('hovered');
            growthClicked = false;
        } else {
            $('#pickGrowth').addClass('clicked');
            $('#pickGrowth').removeClass('hovered');
            $('#scaleDo').hide();
            $('#scalePh').hide();
            $('#scaleTemperature').hide();
            $('#scaleAgitator').hide();
            growthClicked = true;
        }
        if (doClicked) {doClicked = false;}
        if (phClicked) {phClicked = false;}
        if (temperatureClicked) {temperatureClicked = false;}
        if (agitatorClicked) {agitatorClicked = false;}
        $('#pickDo').removeClass('clicked');
        $('#pickPh').removeClass('clicked');
        $('#pickTemperature').removeClass('clicked');
        $('#pickAgitation').removeClass('clicked');
    });
</script>