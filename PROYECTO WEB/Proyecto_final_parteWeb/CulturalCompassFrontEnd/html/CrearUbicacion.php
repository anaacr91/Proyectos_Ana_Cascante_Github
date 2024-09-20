<?php session_start(); 
if(isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='3' && $_SESSION['is_active']=='1'||isset($_SESSION['role_id']) && isset($_SESSION['is_active']) && $_SESSION['role_id']=='2' && $_SESSION['is_active']=='1'){
  include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
   $db = new DB();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" media="screen" href="./../css/bootstrap.min.css" type="text/css" />
    <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" 
    integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
    crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
    <title>Crear nueva Ubicación</title>


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
          <br><br><br><br><br>
    <h1>Crear nueva Ubicación </h1>
    <br>
<form id="formularioUbicacion">

    <input type="hidden" id="id" value="0"> 

    <label for="name_location">Nombre Ubicacion:</label>
    <input type="text" id="name_location" name="name_location"><br><br>

    <label for="additional_info">Dirección:</label>
    <input type="text" id="additional_info" name="additional_info"><br><br>

    <label for="latitud">Latitud:</label>
    <textarea id="latitud" name="latitud"></textarea><br><br>

    <label for="longitud">longitud:</label>
    <textarea id="longitud" name="longitud"></textarea><br><br>
    
    <label for="ubicationlink">Link Mapa:</label>
    <textarea id="ubicationlink" name="ubicationlink"></textarea><br><br>

    <label for="redirectionlink">Link Como llegar:</label>
    <textarea id="redirectionlink" name="redirectionlink"></textarea><br><br>
    
    
    <button type="button" id="botonGuardarModificarLoc" onclick="guardarModificarLoc()" class="btn btn-primary" >Guardar Ubicacion</button>
</form>
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
  </div>
  </div>

  <script src="../js/CrearUbicacion.js" defer></script>
  <script src="./../js/bootstrap.bundle.min.js"></script>
        
</body>
</html>

<?php
}else{
header('location: Login.php');
}
?>