<?php 
include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
include '../../CulturalCompassBackEnd/Controladores/La-carta.php';
$cart = new Cart;

// redirect to home if cart is empty
if($cart->total_items() <= 0){
    
    if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {
                header('Location: PanelAdmin.php');
        
    } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {
                header('Location: PanelOrg.php');
    
          
    } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {
                header ('Location: PanelUsuario.php');
    } 
    
} ?>
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

      <link rel="stylesheet" href="../css/style2.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

  <script src="./../js/bootstrap.bundle.min.js"></script>
      <title>Tramitar Tiquets</title>
      


    </head>
    <body>
    <?php
if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {
            include_once 'NavAdmin.php';?>
    <?php
} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {
            include_once 'NavOrg.php';?>

      <?php
} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {
            include_once 'NavUser.php';?>

      <?php
} else{
            include_once 'CabeceraHtml.php'?>
    <?php
}?>
<br><br><br><br><br>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">


            <div class="col-md-6 offset-lg-3 ">
      <h4 class="mb-3" >Ingrese sus datos para realizar el pedido </h4>
      <form method="POST" action="Cierre.php" class="needs-validation" novalidate>
        <input type="hidden" name="id" value="<?php echo $_SESSION['orderID'] ?>">
        <input type="hidden" name="latitude" id="latitude" value="">
        <input type="hidden" name="longitude" id="longitude" value="">
        

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="nombre"  >Nombres</label>
            <input name="nombre" type="text" class="form-control" id="nombre"    placeholder="Coloca tu nombre"  required>
            
          </div>
          <div class="col-md-6 mb-3">
            <label for="lastName"   >Apellidos</label>
            <input name="apellidos" type="text" class="form-control"   id="lastName" placeholder="Coloca tu apellido"  required>
            
          </div>
        </div>
        <div class="row">
        <div class="col-md-6 mb-3">
          <label for="Telf"  >Teléfono</label>
          <input name="telf" type="number" step="any" class="form-control"   id="Telf" placeholder="Coloca tu numero de teléfono" required>
          
        </div>        

        <div class="col-md-6 mb-3" >
          <label for="email"  >Correo <span class="text-muted"></span></label>
          <input name="email" type="email"  class="form-control" id="email" placeholder="Tucorreo@ejemplo.com" required>
          
        </div>
        </div>

        <div class="mb-3">
          <label for="address" >Dirección</label>
          <input name="direccion" type="text"  class="form-control" id="address" placeholder="Coloca tu dirección aquí" required>
         
        </div>
         <hr class="mb-4">
        <div class="col-12">
        <div class="row">

        
        <div class="col-6"> 
        <h5 class="mb-6" >Método de Pago</h5> 
        
        <div class="custom-control custom-checkbox">
          <input name="francos" value="Francos Suizos" type="checkbox" class="custom-control-input" id="francos">
          <label class="custom-control-label" for="francos" >Francos Suizos</label>
        </div> 
        <div class="custom-control custom-checkbox">
          <input name="dolares" value="Dolares" type="checkbox" class="custom-control-input" id="dolares">
          <label class="custom-control-label" for="dolares" >Dolares</label>
        </div>
        
        <div class="custom-control custom-checkbox">
          <input name="euros" value="Euros" type="checkbox" class="custom-control-input" id="euros">
          <label class="custom-control-label" for="euros" >Euros</label>
        </div> 
        <div class="custom-control custom-checkbox">
          <input name="libras" value="Libras" type="checkbox" class="custom-control-input" id="libras">
          <label class="custom-control-label" for="libras" >Libras</label>
        </div>
        <a href="https://buy.stripe.com/test_bIYcPfh1RalL2vm7ss" class="btn btn-block col-3" style="background-color: #000000; padding: 8px 3px 0px 2px" target="_blank" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">Tarjeta</font></h5></a> 
    </div>

        
        <div class="col-6">
            <h5 class="mb-6" >Forma de entrega</h5>
            <div class="custom-control custom-radio">
            <input id="No" name="delivery" value="0" type="radio" class="custom-control-input" checked >
            <label class="custom-control-label" for="No" >Delivery - Servicio a domicilio</label><br>
            <strong><i >(Nota: puede acarrear un costo adicional)</i></strong>
          </div>
          
          <div class="custom-control custom-radio">
            <input id="Si" name="delivery" value="1" type="radio" class="custom-control-input" >
            <label class="custom-control-label" for="Si" >Pickup</label><br>
            <strong><i >(Nota: Cliente retira en el local)</i></strong>
          </div>
          
         
          
      </div> 
  </div> <!-- row --> 
</div> <!-- div --> 
<hr class="mb-4"> 
        <center>
          <button class="btn btn-outline-success col-6 btn-lg btn-block" type="submit">Finalizar Pedido Aqui</button> <br>
        </center>
       
</form>
      <br>
<center>
<a href="vaciar.php" class="btn btn-outline-danger col-6 btn-lg btn-block">Vaciar la orden</a></center>
      

<br>
<br>






  </div>
  </div>
</div>

</div>
<!-- ./wrapper -->

<!-- REQUIRED SCRIPTS -->
<!-- jQuery -->
  <script>
      if (navigator.geolocation) { 
        navigator.geolocation.getCurrentPosition(

            function (position) {
        
            document.getElementById("latitude").value = position.coords.latitude;
            document.getElementById("longitude").value = position.coords.longitude;
       
      },
      function(){

        alert("¡Error! no se carga la Geolocalización.");
      }  

          ); 
        } else {

        alert("¡Error! Este navegador no soporta la Geolocalización.");
      }
      
      
      
    </script>
    

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
      

</body>
</html>
