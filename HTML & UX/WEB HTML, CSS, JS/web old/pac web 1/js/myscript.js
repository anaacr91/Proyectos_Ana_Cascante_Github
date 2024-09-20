function aviso(){
	alert ("AVISO LEGAL \n\
	Los autores de esta pagina web no se hacen responsables \n\
	del mal uso de la página \n\ ");
}

function avisoen(){
	alert ("LEGAL NOTICE \n\
	The authors of this website are not responsible \n\
	of the bad use of the page \n\ ")
}


function openMenu(){
	const oMenu = document.getElementsByClassName('oculto');
	for (element of oMenu){
		element.style.transform= 'translateX(0px)';
		element.style.display= 'block';
	}
	const barra = document.getElementsByClassName('barra');
	for (element of barra) {
		element.style.display='none';
	}
}
function closeMenu(){
	const cMenu = document.getElementsByClassName('oculto');
	for (element of cMenu){
		element.style.transform= 'translateX(1000px)';
		element.style.display= 'none';
	}
const barra = document.getElementsByClassName('barra');
	for (element of barra) {
		element.style.display='block';
	}
}

/*lenguaje*/

let actualLenguaje='es'

function setLenguaje(newLenguaje) {
	const oldLenguajeElements = document.getElementsByClassName(actualLenguaje);
	
	for (element of oldLenguajeElements){
		element.style.display= 'none';
	}
	actualLenguaje = newLenguaje;
	
	const newLenguajeElements = document.getElementsByClassName(actualLenguaje);
	for (element of newLenguajeElements) {
	element.style.display = 'block';
	}
	
}