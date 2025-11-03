class CazaEstelar{
    
    constructor(numeroSerie,vidaMax,fuerzaAtaque){
        this.numeroSerie = numeroSerie;
        this.vidaActual = vidaMax;
        this.vidaMax = vidaMax;
        this.fuerzaAtaque = fuerzaAtaque;
    }

    disparar(cazaEstelar){
        console.log("disparando desde la Nave");
        let dmg = this.calcularAtaque();
        cazaEstelar.vidaActual -= dmg;
        console.log("daño de " + dmg);
    }

    reparar(){
        console.log("reparando nave");
        if ((this.vidaMax - this.vidaActual) < 4){
            this.vidaActual = this.vidaMax;
        } else this.vidaActual += 4;

       
    }

    calcularAtaque(){
        return this.fuerzaAtaque + Math.ceil(Math.random()*10);

    }
}