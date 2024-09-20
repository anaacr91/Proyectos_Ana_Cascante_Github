
<?php
session_start();
session_destroy();


$text_value = !empty($_GET['search']) ? $_GET['search'] : null;
if (!empty($text_value)) {
    $pdo = $db->connect();
    $query11 = "SELECT * FROM evento WHERE name_event LIKE '%" . $text_value . "%'";
    $eventos = $pdo->query($query11);
    $query12 = $eventos->fetchAll(PDO::FETCH_ASSOC);
}
include_once 'CabeceraFrond.php';
?>

<br><br>
      <img src="logo/logo.jpg" alt="Logo de Cultural Compass" height="100" style="margin-top: 1750px; margin-right: auto; margin-left: 400px;">
      
      <div class="container-fluid">
      <div class="container " style=" float: right; width: 50%;">
      
      <div class="container-map">
          <div id="map"></div>
      </div>
      </div>
      
      <div class="container mt-5" style="float: left; width: 50%;">
        
        <div class="row">
            <div class="col-md-6 " style="margin-right: auto; margin-left: 300px;">
                <h2>Bienvenido a Cultural Compass</h2>
                <p>En Cultural Compass, exploramos y promovemos una amplia variedad de eventos culturales en diferentes categorías, desde música hasta teatro y gastronomía.</p>
            </div>
          </div>

            <div class="row " >
            <div class="col-sm-6" style="margin-right: auto; margin-left: 300px;">
                <h3>¿Quiénes somos?</h3>
                <p>Somos un equipo apasionado por la cultura y las artes, comprometidos en conectar a las personas con eventos culturales en sus comunidades y más allá.</p>
            </div>
        </div>
        <div class="row">
          <div class="col-sm-6" style="margin-right: auto; margin-left: 300px;">
              <h2>Nuestros Eventos Recomendados</h2>
              <p>Explora nuestros eventos recomendados por tu ciudad </p>
          </div>
      </div>
    </div>
      </div>
  
    <br>

    <div class="album py-5 bg-body-tertiary">

      <div class="container">
      
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
      <?php try{
            $pdo =$db->connect();
            $query="SELECT * FROM evento";
            $statement = $pdo->query($query);
            $eventos = $statement->fetchAll(PDO::FETCH_ASSOC);

            foreach($eventos as $evento){ ?>
      <div class="col">
      <div class="card shadow-sm">
      <div style="height: 200px; width:200px;">
      <img src="../CulturalCompassBackEnd/Files/Img_event/<?php echo $evento['imagen'] ?>" class=" img-fluid img-thumbnail">
        </div>
      <div class="card-body">
      <p class="card-text"><?php echo $evento['name_event'] ?></p>
      <div class="d-flex justify-content-between align-items-center">
      <div class="btn-group">
        <a href="html/EventoDetalle.php?id_evento=<?php echo $evento['id']  ?>"  class="btn btn-sm btn-outline-secondary">Ver Evento</a>
      </div>
      <small class="text-body-secondary"><?php  echo "Fecha: ".date('d-m-Y',strtotime($evento['start_at'])) ?></small>
      </div>
      </div>
      </div>
      </div>

   <?php }
}catch(PDOException $e){
echo "error:". $e->getMessage();
}

?> 
     
      
    
    
      </div>
      </div>
      </div>
      <script  src="js/Search.js" ></script>
      
  <script src="js/bootstrap.bundle.min.js"></script>

</body>
</html>