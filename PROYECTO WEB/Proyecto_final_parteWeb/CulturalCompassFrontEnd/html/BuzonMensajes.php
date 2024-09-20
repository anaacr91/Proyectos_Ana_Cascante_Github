<?php   
session_start();
if(isset($_SESSION['role_id']) && isset($_SESSION['is_active'])){
include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php'; 
$db = new DB();
?>
  <!DOCTYPE html>
  <html lang="en">
    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <link
        rel="stylesheet"
        media="screen"
        href="./../css/bootstrap.min.css"
        type="text/css"
      />
      <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
        integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
        crossorigin="anonymous"
        referrerpolicy="no-referrer"
      />
      <script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
      <title>Buzon de mensajes</title>
      <script src="./../js/bootstrap.bundle.min.js"></script>
      

    </head>
    <body>
    <?php

 switch($_SESSION['role_id']){
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
            } 

?>


    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Mensajes</h1>
        
      </div>   




<body> <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="col-xs-12">
             <div class="card">
            <div class="card-header">

                <?php


            $pdo =$db->connect();
            $query = "SELECT * FROM `mensaje` WHERE  rol='".$_SESSION['role_id']."'  and id_user='".$_SESSION['id']."'";
            $statement = $pdo->query($query);
            $mensajes = $statement->fetchAll(PDO::FETCH_ASSOC);
            if (!empty($mensajes)) {
                // Loop through messages or pick the first one
                $mensaje = $mensajes[0]; // Assuming you want the first message
                foreach ($mensajes as $mensaje) {
        ?>
              <h1 class="card-title">Mensaje para <?php echo $mensaje["nombre"] ?> </h1>
              <div class="col-sm-12">
                  </div><!-- /.col -->
              <div>
                    
                </div>
            </div>
               <div class="card-body">

                <center><textarea class="form-control" cols="150" mensaje="5"> Mensaje: <?php echo $mensaje["mensaje"] ?></textarea></center>
                <br>

               <h4>Responder</h4>
               <br>
               <div class="container-fluid">
                <center>
                <div class="mensaje">
                <center><textarea class="form-control" cols="150" mensaje="5"> Email: <?php echo $mensaje["email"] ?></textarea></center></div>
               <div class="mensaje">            
               <center><textarea class="form-control" cols="150" mensaje="5">Telefono: <?php echo $mensaje["numero"] ?></textarea></center></div>
      
            </div></div>
            </center>

                
                
                <br>

              <h5>Para responder este mensaje haga click<a href="<?php echo "Contacto.php?id=" .$_SESSION['id']?>"> Aqui </a></h5>
    </div>
        </div>
 
 </div><!--Panel cierra-->
 
</div>
</div>
</div>

          </div>
          <?php }
} else {
    echo "<p>No messages found.</p>";
}
?>

<?php

}else{

header('location: Login.php');

}

?>