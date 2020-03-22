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

        <style>
            #mapid {
                height: 80%;
            }
        </style>
    </head>
    <body>
        <div id="mapid"></div>
        <script>
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
    </body>
</html>

<script>

</script>