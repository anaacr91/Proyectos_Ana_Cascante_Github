<?php session_start();

if(isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='3' && $_SESSION['is_active']=='1'||isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='2' && $_SESSION['is_active']=='1'){
 include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
  $db = new DB();
  if(isset($_GET['id_location'])) {
  $id_location = $_GET['id_location'];
  try {
    $pdo = $db->connect();
      $query ="SELECT * FROM location WHERE id = :id_location";
      $statement= $pdo -> prepare($query);
      $statement->bindParam(':id_location', $id_location, PDO::PARAM_INT);
      $statement->execute();
      $location=$statement->fetch(PDO::FETCH_ASSOC);
      $locationdata= array(
        'id'=> $location['id'],
        'name_location'=>$location['name_location'],
        'additional_info'=>$location['additional_info'],
        'latitud'=>$location['latitud'],
        'longitud'=>$location['longitud'],
        'ubicationlink'=>$location['ubicationlink'],
        'redirectionlink'=>$location['redirectionlink'],

    );?>
   
   <!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
    integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
    <title>Gestionar Ubicaciones</title>
  </head>

  <body>
  <?php switch($_SESSION['role_id']){
                case '1':
                  include_once 'NavUser.php';
                    break;

                case '2':
                  include_once 'NavOrg.php';
                    break;

                case '3':
                  include_once 'NavAdmin.php';
                    break;
                default:
                   header ('location:../../CulturalCompassFrontEnd/html/login.php');
                    break;
            } ?>

<div class="container">
      <div class="row">
        <div class="col-sm-6">
           <h1>Administrar Ubicacion
            <br><br><?php echo $locationdata['name_location'] ?></h1>
        </div>
        <div class="col-sm-6">
          <i class="fa-solid fa-star"></i>

          <?php 
        if(isset($_SESSION['role_id']) && $_SESSION['role_id']=='3'){ ?>
      <a href="PanelAdmin.php" class="btn btn-primary">Ir Atrás</a>

    <?php }elseif(isset($_SESSION['role_id']) && $_SESSION['role_id']=='2'){ ?>
      <a href="PanelOrg.php" class="btn btn-primary">Ir Atrás</a>
    <?php } ?>
        </div>

        <div class="col-sm-6">
            <br><br>
            <h2>Datos Ubicacion</h2>

            <form >
              <div class="col-12 col-sm-10">
              <label for="nombre_ubicacion"> Nombre ubicación: </label>
              <input class="form-control" type="text" id="nombre_ubicacion" name="nombre_ubicacion" value="<?php echo $locationdata['name_location'] ?>"> 
              </div>
              <div class="col-12 col-sm-10">
                <label for="direccion">Dirección</label>
                  <textarea class="form-control" id="direccion" rows="3"><?php echo $locationdata['additional_info'] ?></textarea>
              </div>
              <div class="col-12 col-sm-10">
                <label for="latitud">Latitud</label>
                  <textarea class="form-control" id="latitud" rows="3"><?php echo $locationdata['latitud'] ?></textarea>
              </div>
              <div class="col-12 col-sm-10">
                <label for="longitud">Longitud</label>
                  <textarea class="form-control" id="longitud" rows="3"><?php echo $locationdata['longitud'] ?></textarea>
              </div>
              <div class="col-12 col-sm-10">
                <label for="ubicationlink">Link Ubicacion</label>
                  <textarea class="form-control" id="ubicationlink" rows="6"><?php echo $locationdata['ubicationlink'] ?></textarea>
              </div>
              <div class="col-12 col-sm-10">
                <label for="redirectionlink">Link Como llegar</label>
                  <textarea class="form-control" id="redirectionlink" rows="6"><?php echo $locationdata['redirectionlink'] ?></textarea>
              </div>
            
</form>
    <button type="button" id="modificarloc" 
    onclick="modificarloc(<?=$locationdata['id']?>)" class="btn btn-primary">Guardar cambios Ubicacion</button>
        <button type="button" onclick="borrarloc(<?=$locationdata['id']?>)" class="btn btn-primary">Borrar Ubicacion</button>
</div>

<script src="./../js/bootstrap.bundle.min.js"></script>
    <script src="./../js/GestionUbicaciones.js" defer></script>
  </body>
</html>

<?php
 } catch (PDOException $e) {
  echo "error:" . $e->getMessage();
}
}

}else{
header('location: Login.php');
}
?>



 