
let enemies=[]
let player = null;
let partida=false;
const mensaje = document.getElementById('result');
const btnReparar =document.getElementById('Reparar');
const btnDisparar =document.getElementById('Disparar');

const id=document.getElementById('id');
const escudo=document.getElementById('escudoActual');
const vida=document.getElementById('vidaActual');
const ataque=document.getElementById('fuerzaAtaque');

const logTextarea = document.getElementById("logTextarea");
const originalConsoleLog = console.log;

btnReparar.addEventListener('click', () => {
    player.reparar();
    accionTieFighters();
    mostrarPlayer();
    mostrarEnemies();
    comprobarVida();
    actualizarXwing();
});

btnDisparar.addEventListener('click', () => { 
    console.log("disparando al tieFighter...");
    player.disparar(enemies[0]);
    if (enemies[0].vidaActual<=0){
        enemies.shift();
        console.log("un enemigo ha muerto");
    }
    console.log("comprobando numero de enemigos...");
    comprobarTieFighters();
    accionTieFighters();
    mostrarPlayer();
    mostrarEnemies();
    comprobarVida();
    actualizarXwing();

});

function nuevaPartida(){
    for (i=1; i<=5; i++){
        enemies.push( new TieFighter(i,10,2) )
    } 
    mostrarEnemies();
    let serialno = document.getElementById('serialno').value
    let R2D2 = document.getElementById('R2D2').checked 
    player= new XWing(serialno, 20, 4, R2D2, 10);
    partida = true;
    mensaje.value="Bienvenido al juego";
    mostrarPlayer();
    document.getElementById('formulario').style.display="none";
    actualizarXwing();
}

function comprobarPartida(){
if (!partida){
    exit();
}
}

function comprobarTieFighters(){
if (enemies.length===0){
    mensaje.value="Has ganado";
    window.location.href = "end.html";
}
return true;
}

function accionTieFighters(){
    let ia = 0;
    for (i=0; i<enemies.length; i++){
        if (enemies[i].vidaActual<5) ia = 1;
    }

    let accion = enemies[0].escogerAccion(ia);
    enemies[0][accion](player);
}

function actualizarXwing(){
    id.textContent=player.numeroSerie;
    escudo.textContent = player.escudoActual;
    vida.textContent= player.vidaActual;
    ataque.textContent=player.fuerzaAtaque;
}

function comprobarVida(){
    if (player.vidaActual<=0){
        mensaje.value="Has perdido";
        window.location.href="lost.html";
    }
}


console.log = function(message) {
    originalConsoleLog.apply(console, arguments);
    logTextarea.value += message + "\n";
};

function mostrarEnemies(){
    enemies.forEach(function(enemies){
    logTextarea.value += `id: ${enemies.numeroSerie}, fuerza max:${enemies.fuerzaAtaque}, vida: ${enemies.vidaActual} \n`;
    });
    };
    

    function mostrarPlayer(){
        logTextarea.value += `id: ${player.numeroSerie}, fuerza max:${player.fuerzaAtaque}, vida: ${player.vidaActual}, escudo: ${player.escudoActual} \n`;
        
        }