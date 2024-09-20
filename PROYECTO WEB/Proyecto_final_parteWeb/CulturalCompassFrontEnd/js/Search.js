function searchFunction(){
   
    let value = $('.text-value').val();
    

    window.location.href = "/Grupo11-2024-Proyecto-Final-main/proyecto_final_parteWeb/CulturalCompassFrontEnd/html/search.php?search=" + value;
}

var markersData = [
  
  {
      position: {lat: 41.3916047, lng: 2.1621674},
      title: "Casa Batllo",
      label: "Casa Batllo-Passeig Gracia 43",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  },
  {
      position: {lat: 41.3902, lng: 2.15977},
      title: "Aribau Immersive Space",
      label: "Aribau Immersive Space",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  },
  {
      position: {lat: 41.4131805, lng: 2.1285481},
      title: "CosmoCaixa",
      label: "CosmoCaixa",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  }
  ,
  {
      position: {lat: 41.3719, lng: 2.15151},
      title: "Fira de Barcelona",
      label: "Fira de Barcelona",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  }
  ,
  {
      position: {lat:41.403, lng: 2.1882},
      title: "Glories Catalanes",
      label: "Glories Catalanes",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  }
  ,
  {
      position: {lat: 41.3937, lng: 2.11832},
      title: "Club Tenis",
      label: "Club Tenis",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  }
  ,
  {
      position: {lat: 41.5722, lng: 2.256},
      title: "Montmeló Circuito",
      label: "Montmeló Circuito",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  }
  ,
  {
      position: {lat: 41.4091, lng: 2.22276},
      title: "Parc del Forum",
      label: "Parc del Forum",
      iconUrl: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
  }
  
];


//import { MarkerClusterer } from 'https://unpkg.com/@googlemaps/markerclusterer@latest';
//import { MarkerClusterer } from "@googlemaps/markerclustererplus";
var MarkerClusterer = window.MarkerClusterer;
window.initMap= function() {
    

   // const { AdvancedMarkerElement } = await google.maps.marker.importLibrary("marker");

    var coordinates= {
        lat: 41.23196,
        lng: 2.09324
    };
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 10,
      center: coordinates,
      scrollwheel: false
    });

    var markers = [];
    markersData.forEach(function(markerData) {
    var marker= new google.maps.Marker({
        position: markerData.position,
      map: map,
      title: markerData.title,
      icon: {
        url:  markerData.iconUrl,
        labelOrigin: new google.maps.Point(75, 32),
        size: new google.maps.Size(32, 32),
        anchor: new google.maps.Point(16, 32)
      },
      label: {
        text:  markerData.label,
        color: "#C70E20",
        fontWeight: "bold"
      }
    });
    var clusterer = new MarkerClusterer(map, [marker]);
    imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m';
  });

}

  window.addEventListener('load', initMap);