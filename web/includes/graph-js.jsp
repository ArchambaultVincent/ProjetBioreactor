<%--
  Created by IntelliJ IDEA.
  User: clort
  Date: 29/02/2020
  Time: 00:13
  To change this template use File | Settings | File Templates.
--%>
<script>
    let do2ctx = $('#do2-Chart');

    let graphChart = new Chart(do2ctx, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'DO2',
                data: [],
                borderColor: [
                    'rgb(128, 128, 128, 1)',
                ],
                borderWidth: 8,
                fill: false
            }, {
                label: 'pH',
                data: [],
                borderColor: [
                    'rgb(128, 128, 128, 1)',
                ],
                borderWidth: 8,
                fill: false
            }, {
                label: 'Temperature',
                data: [],
                borderColor: [
                    'rgb(128, 128, 128, 1)',
                ],
                borderWidth: 8,
                fill: false
            }, {
                label: 'Agitator',
                data: [],
                borderColor: [
                    'rgb(128, 128, 128, 1)',
                ],
                borderWidth: 8,
                fill: false
            }, {
                label: 'Growth',
                data: [],
                borderColor: [
                    'rgb(128, 128, 128, 1)',
                ],
                borderWidth: 8,
                fill: false
            }]
        },
        options: {
            elements: {
                point: {
                    radius: 0
                }
            },
            legend: {
                display: false
            },
            scales: {
                scaleShowLabels: false,
                xAxes: [{
                   gridLines: {
                       display: false,
                       drawBorder: false
                   }
                }],
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        min: 0,
                        max: 100,
                        fontColor: "transparent"
                    },
                    gridLines: {
                        display: false,
                        drawBorder: false
                    }
                }]
            }
        }
    });
</script>
