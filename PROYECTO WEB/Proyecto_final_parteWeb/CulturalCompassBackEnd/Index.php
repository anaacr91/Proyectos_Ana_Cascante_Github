<?php 
session_start();
session_destroy();

header('location: ../CulturalCompassFrontEnd/html/Login.php');

?>