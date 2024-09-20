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
      <title>Orden de Pago</title>
      


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
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
    <div class="row">
        
        <div class="col-md-4 offset-lg-4">
            <br>
            <center><h1>Verifique Su Pedido</h1></center>
            <br>
        
      <h4 class="d-flex justify-content-between align-items-center mb-3">
        <span class="text-muted">Articulos en el carrito</span>
        
        <span class="badge badge-secondary badge-pill"><?php echo '+ '. $cart->total_items(); ?></span>
      </h4>
      <?php
        if($cart->total_items() > 0){
            //get cart items from session
            $cartItems = $cart->contents();
            foreach($cartItems as $item){
        ?>
      <ul class="list-group mb-3">
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div>
            <h6 class="my-0"><?php echo $item["name"]; ?></h6>
            <small class="text-muted"><?php echo $item["qty"].' Und'; ?> X <?php echo '$'.$item["price"].' USD'; ?></small>
          </div>
          <span class="text-muted"><?php echo '$'.$item["subtotal"].' USD'; ?></span>
        </li>
    <?php } }else{ ?>
        No hay articulos en tu carta......
        <?php } 
         if($cart->total_items() > 0){ ?>
        <li class="list-group-item d-flex justify-content-between">
          <span>Total </span>
          <strong><?php echo '$'.$cart->total().' USD'; ?></strong>
        </li>
      </ul>
       <?php } ?>

       <?php
if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {?>
      <a href="PanelAdmin.php"class="btn btn-block col-12" style="background-color: #000000; padding: 8px 3px 0px 2px" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">CONTINUA COMPRANDO</font></h5></a>

    <?php } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {?>
      <a href="PanelOrg.php" class="btn btn-block col-12" style="background-color: #000000; padding: 8px 3px 0px 2px" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">CONTINUA COMPRANDO</font></h5></a>

      <?php } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {?>
      <a href="PanelUsuario.php" class="btn btn-block col-12" style="background-color: #000000; padding: 8px 3px 0px 2px" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">CONTINUA COMPRANDO</font></h5></a>
<?php }?>
   
    <br>
    <br>
    <a href="../../CulturalCompassBackEnd/Controladores/AccionCarta.php?action=placeOrder" class="btn btn-success orderBtn col-12">Culminar Compra</a>
    </div>

  <br>
        </div>

          </div>
  </div>
  



</div>
<!-- ./wrapper -->


</body>
</html>
