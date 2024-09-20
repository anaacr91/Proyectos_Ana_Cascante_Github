<?php

require_once('Coche.php');

class CocheGasolina extends Coche {
    private $capacidadTanque;
    private $emisionesKm;

    public function __construct($matricula, $color, $marca, $precioOriginal, $kilometraje, $capacidadTanque, $emisionesKm) {
        parent::__construct($matricula, $color, $marca, $precioOriginal, $kilometraje);
        $this->capacidadTanque = $capacidadTanque;
        $this->emisionesKm = $emisionesKm;
    }
    public function getCapacidadTanque() {
        return $this->capacidadTanque;
    }

    public function setCapacidadTanque($capacidadTanque) {
        $this->capacidadTanque = $capacidadTanque;
    }

    public function getEmisionesKm() {
        return $this->emisionesKm;
    }

    public function setEmisionesKm($emisionesKm) {
        $this->emisionesKm = $emisionesKm;
    }

    public function toString() {
        return parent::toString() . ", Capacidad Tanque: {$this->capacidadTanque}, Emisiones Km: {$this->emisionesKm}";
    }

    public function calcCarga($kilometros) {
        return $this->capacidadTanque - ($kilometros / $this->emisionesKm);
    }

    public function calcGasesEmitidos($kilometros = null) {
        if ($kilometros === null) {
            return $this->kilometraje * $this->emisionesKm;
        } else {
            return $kilometros * $this->emisionesKm;
        }
    }
}
?> 