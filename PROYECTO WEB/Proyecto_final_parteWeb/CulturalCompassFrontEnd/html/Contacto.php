<?php
include '../../CulturalCompassBackEnd/Controladores/La-carta.php';
$cart = new Cart;
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

  <script src="./../js/bootstrap.bundle.min.js"></script>
      <title>Formulario de contacto</title>
      


    </head>
    <body class="layout-navbar-fixed layout-top-nav layout-footer-fixed hold-transition">

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

<br>
<br>

	    <div class="col-md-6 offset-lg-3 ">
      <center><h4 class="mb-3">Rellena tus datos para comunicarte con nosotros</h4></center>
      <form method="POST" action="../../CulturalCompassBackEnd/Controladores/mensaje.php" class="needs-validation" novalidate>
        <input type="hidden" name="latitude" id="latitude" value="">
        <input type="hidden" name="longitude" id="longitude" value="">
        <input name="nombre" type="hidden"  id="nombre" value="<?php echo $_SESSION['username'] ?>"  >
        <input name="email" type="hidden"  id="email" value="<?php echo $_SESSION['email'] ?>">
         
        <div class="row">
        <div class="col-md-12 mb-3">
          <label for="Telf">Teléfono</label>
          <input name="telf" type="number" step="any" class="form-control" id="Telf" placeholder="tu teléfono"  required>
          <div class="invalid-feedback">
            Por favor el campo no puede estar vacio.
          </div>
        </div>        
       
        <div class="col-md-12 mb-3">
          <label for="rol">Para</label>
         <select class="form-control" name="rol" id="rol" required>
            <option value="">Selecciona una opción</option>
            <option value="2">Organización</option>
            <option value="3">Administrador</option>
         </select>
        </div> 
        <div class="form-group col-lg-6"> 
        <select name="tipos" id="tipos" class="form-control" required=""></select> 
        </div> 
        
        
        </div>
        <div class="row">
        <div class="w-100"><br></div>
        <div class="mb-3">
          <label for="address">Dirección</label>
          <input name="direccion" type="text" class="form-control" id="address" placeholder="Coloca tu dirección aquí" required>
          <div class="invalid-feedback"> 
          	Por favor introduzca su direccion.
          </div>
        </div>
        </div>
         <hr class="mb-4">
        <div class="col-12">
        <label for="mensaje">Mensaje</label>
        <textarea required id="mensaje" placeholder="Escribe tu requerimiento aquí " name="mensaje" cols="30" rows="3" class="form-control"></textarea>
        <div class="invalid-feedback">
            Por favor el mensaje no puede estar vacio.
          </div>
  
        
</div> <!-- div --> 
<hr class="mb-4"> 
<button type="submit" class="btn  btn-block" style="background-color: #000000; padding: 8px 2px 0px 2px" ><h4><font style=" color: #ffffff;">Enviar</h4></font> </button>
</form>



  
  </div>
  </body>
</html>

  <script>
                          $(document).ready(function(e){
                            $("#rol").change(function(){
                              var parametros= "id="+$("#rol").val();
                              $.ajax({
                                data: parametros,
                                url: 'ajax_peticiones.php',
                                type: 'post',
                                beforeSend: function (){},
                                success: function(response){
                                  $("#tipos").html(response);

                                }
                              });

                            }) 

                          });

                        </script>
