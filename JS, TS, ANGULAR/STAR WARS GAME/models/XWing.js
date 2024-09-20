class XWing extends CazaEstelar{

    constructor(numeroSerie, vidaMax, fuerzaAtaque, R2D2Incorporado, escudoMaximo){
        super(numeroSerie,vidaMax,fuerzaAtaque);
        this.R2D2Incorporado = R2D2Incorporado;
        this.escudoMaximo = escudoMaximo;
        this.escudoActual = escudoMaximo;
    }

    reparar(){
        if (this.R2D2Incorporado=false){
            super.reparar();
        } else {
            // 1
            if(this.vidaActual<=this.vidaMax-4){
                super.reparar();
            }else if(this.vidaActual == this.vidaMax){
                this.escudoActual += 4;
                console.log("reparando escudo");
            } else { //repartir
                this.escudoActual += (4-(this.vidaMax-this.vidaActual));
                this.vidaActual=this.vidaMax;
                console.log("reparando escudo y nave");
            }
        }
    }
}