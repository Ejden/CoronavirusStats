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
        <script>
            const instance = axios.create({
                baseURL: 'http://localhost:8080/CoronavirusStats/api',
                timeout: 1000
            });
        </script>
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
            <%--DATA-CHART SCRIPT--%>
            let ctx = document.getElementById('myChart').getContext('2d');
            // == CHART DATA ==
            let country = 'No country selected';
            let titles = [];
            let values = [1,1];

            // == CONSTANTS ==
            let CHART_TYPE = 'line';
            let CHART_OPTIONS = {
                responsive: true,
                maintainAspectRatio: false
            };
            let CHART_DATA = {
                labels: titles,
                datasets: [{
                    label: country,
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    data: values
                }]
            };

            let chart = new Chart(ctx, {
                // The type of chart we want to create
                type: CHART_TYPE,
                // The data for our dataset
                data: CHART_DATA,
                // Options for chart
                options: CHART_OPTIONS
            });
        </script>
        <script>
            <%--MAP SCRIPT--%>
            let map = L.map('mapid');

            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);

            let data = instance.get('/points?onlyWithActiveCases=true').then(response => {
                for (let i = 0; i < response.data.length; i++) {
                    //content
                    let content;
                    if (response.data[i].province_state != null) {
                        content = 'Province: ' + response.data[i].province_state + '</br>';
                    }
                    content = 'Country: ' + response.data[i].country_region + '</br>'
                        + 'Cases: ' + response.data[i].cases;

                    //marker
                    L.marker([response.data[i].latitude, response.data[i].longitude])
                        .addTo(map)
                        .bindPopup(content)
                        .openPopup()
                        .on('click', function () {
                            instance.get('/casesHistory?id='+response.data[i].id).then(response2 => {
                                country = response.data[i].country_region;
                                titles = Array.from(Object.keys(response2.data));
                                values = Array.from(Object.values(response2.data));

                                chart = new Chart(ctx, {
                                    // The type of chart we want to create
                                    type: CHART_TYPE,
                                    // The data for our dataset
                                    data: {
                                        label: titles,
                                        datasets: [{
                                            label: response2.data,
                                            backgroundColor: 'rgb(255, 99, 132)',
                                            borderColor: 'rgb(255, 99, 132)',
                                            data: values
                                        }]
                                    },
                                    // Options for chart
                                    options: CHART_OPTIONS
                                })
                            })
                        })
                }
                map.setView([52, 19], 6);
            });
        </script>
    </body>
</html>
