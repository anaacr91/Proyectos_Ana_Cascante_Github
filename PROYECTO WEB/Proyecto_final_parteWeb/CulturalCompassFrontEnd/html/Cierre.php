<?php
include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
include '../../CulturalCompassBackEnd/Controladores/La-carta.php';
$cart = new Cart;

if($cart->total_items() <= 0 OR $cart->total_items()=='' ){
    
    if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {
        header('Location: PanelAdmin.php');

} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {
        header('Location: PanelOrg.php');

  
} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {
        header ('Location: PanelUsuario.php');
} 
} 



if(!isset($_POST['francos'])){$francos ='';}else{$francos = $_POST['francos'];}
if(!isset($_POST['dolares'])){$dolares='';}else{$dolares= $_POST['dolares'];}
if(!isset($_POST['euros'])){$euros ='';}else{$euros = $_POST['euros'];}
if(!isset($_POST['libras'])){$libras  = '';}else{$libras= $_POST['libras'];}

$valores=array('#', '&', '/', '"');


$nombres   = $_POST['nombre'];
$apellidos = $_POST['apellidos'];
$email     = $_POST['email'];
$telf      = $_POST['telf'];
$direccion = $_POST['direccion'];
$delivery  = $_POST['delivery'];
$orden     = $_POST['id'];
$latitud   = $_POST['latitude'];
$longitude = $_POST['longitude'];
$maps      = 'https://www.google.com/maps/dir/?api=1&destination='.$latitud.','.$longitude;
$metodo = $francos.' '.$dolares.' '.$euros.' '.$libras; 


$sentencia = $db->query("INSERT INTO `clientes`(  `id_user`, `nombre`, `direccion`, `telefono`, `correo`, `pago`, `delivery`, `gps`, `orden`) VALUES ('".$_SESSION['id']."','".$nombres.' '.$apellidos."','".str_replace($valores,'N-',$direccion)."','".$telf."','".$email."','".$metodo."','".$delivery."','".$maps."','".$orden."')");

$peticion_id_cliente = $db->query("SELECT * FROM clientes WHERE orden='".$orden."'");
$id_cliente = $peticion_id_cliente->fetch_assoc();
$itemData_id_cliente = array('id' =>$id_cliente['id']);

$peticion2 = $db->query("UPDATE `orden` SET `id_cliente`='".$_SESSION['id']."', `metodo`='".$metodo."', `delivery`='".$delivery."'  WHERE id='".$orden."'");
$cart->destroy(); 
header('Location: PerfilPersonal.php');

?>