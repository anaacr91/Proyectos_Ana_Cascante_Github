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
      <title>Carrito de Compras</title>
      


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
  <style>
    .container{padding: 0px;}
    input[type="number"]{width: 60px;}


    </style>
    <script>
    function updateCartItem(obj,id){
        $.get("../../CulturalCompassBackEnd/Controladores/AccionCarta.php", {action:"updateCartItem", id:id, qty:obj.value}, function(data){
            if(data == 'ok'){
                location.reload();
            }else{
                alert('Cart update failed, please try again.');
            }
        });
    }
    </script>


  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
         

        <br>
        <div class="col-lg-8 offset-lg-2 col-12 text-center">

            <div class="card">
              <div class="card-header">
                <h3 class="card-title"><font style="vertical-align: inherit;">
                  <font style="font-family: 'Roboto Mono', serif; color: #585655" >CARRITO DE COMPRAS</font></font></h3>
                </div>
              </div>
              <center>
                <div class="row">
                <div class="col-3 col-lg-3"><font style="font-family: 'Bebas Neue', serif;" >CANTIDAD</font></div> 
                <div class="col-3 col-lg-3"><font style="font-family: 'Bebas Neue', serif;" >EVENTO</font></div> 
                <div class="col-3 col-lg-3 text-sm-left text-lg-right "><font style="font-family: 'Bebas Neue', serif;" >SUB-TOTAL</font></div>
                <div class="col-2 col-lg-3"></div>
                </div>
                  
                  <div class="row">   
                  <?php if($cart->total_items() > 0){
            //get cart items from session
                  $cartItems = $cart->contents();
                  foreach($cartItems as $item){ ?>
              
              <div class="col-3 col-lg-3">
                <input type="number" class="form-control text-center col" value="<?php echo $item["qty"]; ?>" onchange="updateCartItem(this, '<?php echo $item["rowid"]; ?>')">
              </div>
              <div class="col-3 col-lg-3">
              <p style="font-family: 'Nunito', serif;"><?php echo $item["name"]; ?></p>
              </div>
              <div class="col-3 col-lg-3 text-right text-lg-right " align="right">
               <font style="font-family: 'Nunito', serif;"><?php echo '€'.$item["subtotal"]; ?> </font> 
              </div>
              <div class="col-3 col-lg-3">
                <a href="../../CulturalCompassBackEnd/Controladores/AccionCarta.php?action=removeCartItem&id=<?php echo $item["rowid"]; ?>" class="btn btn-danger" style="background-color: #000000;"  onclick="return confirm('Confirma eliminar?')"><i class="fa fa-trash fa-sm" style="color: #ffffff;"></i></a>
              </div>
            
        
        <?php } }else{ ?>
        <p>Tu carrito esta vacio.....</p>
        <?php } ?>
    
              
            <?php if($cart->total_items() > 0){ ?>
             
              <div class="col-3 col-lg-3"></div>
              <div class="col-3 col-lg-3"></div>
              <div class="col-6 col-lg-3 text-left text-lg-right" >
                 <h4><font style="font-family: 'Bebas Neue', serif;" >Total
           <?php echo '$'.$cart->total(); ?></font></h4>
              </div>
              <div class="col-lg-3"></div>
           
                     
           
            
            <?php } ?>
             </div> 
             </center> 
        
    
</div><!-- /.container-fluid -->
</div> <!-- /.content-header -->
<br>
 

<div class="row">
<div class="col-6 col-lg-6">
  <center>
  <?php
if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {?>
      <a href="PanelAdmin.php"class="btn btn-block col-12 col-lg-6" style="background-color: #000000; padding: 8px 3px 0px 2px" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">CONTINUA COMPRANDO</font></h5></a>

    <?php } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {?>
      <a href="PanelOrg.php" class="btn btn-block col-12 col-lg-6" style="background-color: #000000; padding: 8px 3px 0px 2px" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">CONTINUA COMPRANDO</font></h5></a>

      <?php } elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {?>
      <a href="PanelUsuario.php" class="btn btn-block col-12 col-lg-6" style="background-color: #000000; padding: 8px 3px 0px 2px" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">CONTINUA COMPRANDO</font></h5></a>
<?php }?>
   
    </center>
</div>
<div class="col-6 col-lg-6">
  <center>
    <a href="Pagos.php" class="btn btn-block col-12 col-lg-6" style="background-color: #000000; padding: 8px 3px 0px 2px" ><h5><font style="font-family: 'Bebas Neue', serif; color: #ffffff;">REALIZA TU PEDIDO</font></h5></a>
    </center>
</div>
</div>
      
    
  </div>
  </div>
  

</body>
</html>
