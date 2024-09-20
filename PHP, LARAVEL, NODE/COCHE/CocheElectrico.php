<?php
require_once('Coche.php');

class CocheElectrico extends Coche {
    private $duracionBateria;
    private $horasCarga;

    public function __construct($matricula, $color, $marca, $precioOriginal, $kilometraje, $duracionBateria, $horasCarga) {
        parent::__construct($matricula, $color, $marca, $precioOriginal, $kilometraje);
        $this->duracionBateria = $duracionBateria;
        $this->horasCarga = $horasCarga;
    }
    public function getDuracionBateria() {
        return $this->duracionBateria;
    }

    public function setDuracionBateria($duracionBateria) {
        $this->duracionBateria = $duracionBateria;
    }

    public function getHorasCarga() {
        return $this->horasCarga;
    }

    public function setHorasCarga($horasCarga) {
        $this->horasCarga = $horasCarga;
    }
    public function toString() {
        return parent::toString() . ", Duración Batería: {$this->duracionBateria}, Horas Carga: {$this->horasCarga}";
    }

    public function calcCarga($kilometros) {
        $porcentajeBateria = ($kilometros / $this->duracionBateria) * 100;
        $horasParaCargar = ($porcentajeBateria / 100) * $this->horasCarga;
        return array('porcentajeBateria' => $porcentajeBateria, 'horasParaCargar' => $horasParaCargar);
    }

    public function calcGasesEmitidos($kilometros = null) {
        return 0;
    }
}

?> 