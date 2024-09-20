<?php

abstract class Coche {
    protected $matricula;
    public $color;
    protected $marca;
    public $precioOriginal;
    public $kilometraje;

    public function __construct($matricula, $color, $marca, $precioOriginal, $kilometraje) {
        $this->matricula = $matricula;
        $this->color = $color;
        $this->marca = $marca;
        $this->precioOriginal = $precioOriginal;
        $this->kilometraje = $kilometraje;
    }
    public function getMatricula() {
        return $this->matricula;
    }

    public function getColor() {
        return $this->color;
    }

    public function getMarca() {
        return $this->marca;
    }

    public function getPrecioOriginal() {
        return $this->precioOriginal;
    }

    public function getKilometraje() {
        return $this->kilometraje;
    }

    public function setMatricula($matricula) {
        $this->matricula = $matricula;
    }

    public function setColor($color) {
        $this->color = $color;
    }

    public function setMarca($marca) {
        $this->marca = $marca;
    }

    public function setPrecioOriginal($precioOriginal) {
        $this->precioOriginal = $precioOriginal;
    }

    public function setKilometraje($kilometraje) {
        $this->kilometraje = $kilometraje;
    }


    public function toString() {
        return "Matrícula: {$this->matricula}, Color: {$this->color}, Marca: {$this->marca}, Precio Original: {$this->precioOriginal}, Kilometraje: {$this->kilometraje}";
    }

    public function calcPrecio() {
        return $this->precioOriginal - ($this->precioOriginal * ($this->kilometraje / 100));
    }

    abstract public function calcCarga($kilometros);
    abstract public function calcGasesEmitidos($kilometros = null);
}
?> 


