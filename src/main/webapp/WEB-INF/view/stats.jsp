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

        <style>
            body, html {
                margin: 0;
                padding: 0;
                background: #3c3c3c;
            }

            #mapid {
                height: 80%;
                width: 50%;
            }

            #top_box {
            }

            #top_menu {
                width: 100%;
                height: 65pt;
            }

            #chart_box {
                position: relative;
                height: 500px;
                width: 500px;
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

            <c:forEach var="point" items="${data.points}">
                if ([${point.cases}] > 0) {
                    L.marker([<c:out value="${point.latitude}"/>, <c:out value="${point.longitude}"/>]).addTo(map)
                        .bindPopup("Country: <c:out value="${point.country_region}"/> </br>"
                                    + "Cases: <c:out value="${point.cases}"/> ")
                        .openPopup();
                }
            </c:forEach>

            map.setView([52, 19], 6);
        </script>

        <script>
            <%--DATA-CHART SCRIPT--%>

            let titles = [];
            <% String[] codes= (String[]) request.getAttribute("titles");
            if (codes !=null) {
                for(int i=0; i<codes.length; i++) {
            %>
            var code = '<%= codes[i] %>';
            titles[<%= i %>] = code;
            <%}
            }%>

            let ctx = document.getElementById('myChart').getContext('2d');
            let chart = new Chart(ctx, {
                // The type of chart we want to create
                type: 'line',

                // The data for our dataset
                data: {
                    labels: titles,
                    datasets: [{
                        label: 'My First dataset',
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: [0, 10, 5, 2, 20, 30, 45]
                    }]
                },

                options: {}
            });
        </script>
    </body>
</html>
