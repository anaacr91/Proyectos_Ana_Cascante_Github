<?php session_start();
include_once '../../CulturalCompassBackEnd/Controladores/ControladorBD.php'; 
$nombres    = $_POST['nombre'];
$email     = $_POST['email'];
$telf      = $_POST['telf'];
$direccion = $_POST['direccion'];
$mensaje  = $_POST['mensaje'];
$rol = $_POST['rol'];
$user= $_POST['tipos'];

$fecha = date('Y-m-d');
$hora = date("h:m:s");
$leido = 0;




$sentencia = $base_de_datos->prepare("INSERT INTO mensaje( `id_user`, `rol`, `nombre`, `numero`, `email`, `mensaje`, `fecha`, `hora`, `leido`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
$resultado = $sentencia->execute([$user, $rol, $nombres, $telf, $email, $mensaje, $fecha, $hora, $leido]);

if (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '3') {
    header('Location:  ../../CulturalCompassFrontEnd/html/PanelAdmin.php');

} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '2') {
    header('Location:  ../../CulturalCompassFrontEnd/html/PanelOrg.php');


} elseif (isset($_SESSION['role_id']) && $_SESSION['role_id'] == '1') {
    header ('Location:  ../../CulturalCompassFrontEnd/html/PanelUsuario.php');
} 
?>