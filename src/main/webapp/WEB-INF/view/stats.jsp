<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>COVID-19 Cases</title>
        <meta charset="UTF-8"/>
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
              integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
              crossorigin=""/>

        <script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"
                integrity="sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew=="
                crossorigin=""></script>

        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

        <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

        <style>
            body, html {
                margin: 0;
                padding: 0;
            }

            #top_menu {
                width: 100%;
                height: 65pt;
            }

            #top_box {
                height: 80%;
                width: 100%;
            }

            #mapid {
                height: 80%;
                width: 50%;
                float: left;
            }

            #chart_box {
                position: relative;
                height: 100%;
                width: 50%;
                float: right;
            }
        </style>
    </head>
    <body>
        <div id="top_menu"></div>
        <div id="top_box">
            <div id="mapid"></div>
            <div id="chart_box">
                <canvas id="myChart" height="500px" width="500px"></canvas>
            </div>
        </div>
        <script>
            <%--MAP SCRIPT--%>
            let map = L.map('mapid');

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);


            const instance = axios.create({
                baseURL: 'http://localhost:8080/CoronavirusStats/api',
                timeout: 1000
            });

            let data = instance.get('/points?onlyWithActiveCases=true').then(function (response) {
                for (let i = 0; i < response.data.length; i++) {
                    L.marker([response.data[i].latitude, response.data[i].longitude])
                        .addTo(map)
                        .bindPopup('Province/State: ' + response.data[i].province_state + '</br>'
                                    + 'Country/Region: ' + response.data[i].province_state + '</br>'
                                    + 'Cases: ' + response.data[i].cases)
                        .openPopup();
                }
            });

            map.setView([52, 19], 6);

        </script>

        <script>
            <%--DATA-CHART SCRIPT--%>


            let titles = [];



            let values = [0, 20];


            let ctx = document.getElementById('myChart').getContext('2d');
            let chart = new Chart(ctx, {
                // The type of chart we want to create
                type: 'line',

                // The data for our dataset
                data: {
                    labels: titles,
                    datasets: [{
                        label: 'Finland',
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: values
                    }]
                },

                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                }
            });
        </script>
    </body>
</html>
