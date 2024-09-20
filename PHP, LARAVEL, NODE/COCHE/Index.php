<?php

require_once('CocheElectrico.php');
require_once('CocheGasolina.php');

$cocheElectrico = new CocheElectrico('1234ABC', 'Rojo', 'Tesla', 60000, 0, 500, 12);
$cocheGasolina = new CocheGasolina('5678DEF', 'Azul', 'Ford', 20000, 0, 60, 0.15);


echo $cocheElectrico->toString() . "<br>";
echo $cocheGasolina->toString() . "<br>";

echo "Precio coche eléctrico: " . $cocheElectrico->calcPrecio() . "<br>";
echo "Precio coche de gasolina: " . $cocheGasolina->calcPrecio() . "<br>";

$cargaElectrico = $cocheElectrico->calcCarga(100);
$cargaGasolina = $cocheGasolina->calcCarga(100);
echo "Carga coche eléctrico: " . $cargaElectrico['porcentajeBateria'] . "%, Horas para cargar: " . $cargaElectrico['horasParaCargar'] . "<br>";
echo "Carga coche de gasolina: " . $cargaGasolina . " litros<br>";

echo "Gases emitidos por coche eléctrico: " . $cocheElectrico->calcGasesEmitidos() . "<br>";
echo "Gases emitidos por coche de gasolina: " . $cocheGasolina->calcGasesEmitidos() . "<br>";

echo "Gases emitidos por coche eléctrico (100 km): " . $cocheElectrico->calcGasesEmitidos(100) . "<br>";
echo "Gases emitidos por coche de gasolina (100 km): " . $cocheGasolina->calcGasesEmitidos(100) . "<br>";

?> 