<?php 
session_start();
if(!isset($_SESSION['orderID'])) header("Location: salir.php");

$id = $_SESSION['orderID'] ;
include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php';
$sentencia2 = $base_de_datos->prepare("DELETE FROM orden_articulos WHERE order_id = ?;");
$resultado2 = $sentencia2->execute([$id]);
$sentencia = $base_de_datos->prepare("DELETE FROM orden WHERE id = ?;");
$resultado = $sentencia->execute([$id]);
include '../../CulturalCompassBackEnd/Controladores/La-carta.php';
$cart = new Cart;
$cart->destroy();
if($resultado === TRUE){
    if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {
        header('Location: PanelAdmin.php');

} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {
        header('Location: PanelOrg.php');

  
} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {
        header ('Location: PanelUsuario.php');
} 
	
}
else echo "Algo salió mal";

?>