class TieFighter extends CazaEstelar{

    constructor(numeroSerie, vidaMax, fuerzaAtaque){
        super(numeroSerie, vidaMax, fuerzaAtaque)
    }

    escogerAccion(ia){   
    
    if (ia==0){
        if (Math.random()<0.6){
            console.log("Los enemigos atacan");

            return "disparar";
        }else{
            console.log("los enemigos se reparan");

            return "reparar";
        }
    }else{
        if (Math.random()<0.1){
            console.log("Los enemigos atacan");
            return "disparar";
        }else{
            console.log("los enemigos se reparan");
            return "reparar";
        }
    }
    }
    
    disparar(xWing){
        console.log("xWing tiene " + xWing.escudoActual + " de escudo");

        if (xWing.escudoActual===0){
            console.log("escudo derribado")
            console.log("Como tiene escudo 0 disparamos directamente.");
            super.disparar(xWing)
            
        }
        else {
            const damage = super.calcularAtaque();
            console.log("daño de " + damage);
            if (xWing.escudoActual < damage){
                xWing.vidaActual -= (damage-xWing.escudoActual);
                xWing.escudoActual = 0;
            }else  if(xWing.escudoActual>=damage){
                xWing.escudoActual -= damage;          
            }
        }
   
    }
}