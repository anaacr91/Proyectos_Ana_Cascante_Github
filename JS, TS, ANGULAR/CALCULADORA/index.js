
document.getElementById("sum").addEventListener('click', () => {

   
    var numA = Number(document.getElementById('numA').value);
    var numB = Number(document.getElementById("numB").value);

  
    if (!isNaN(numA) && !isNaN(numB)){

        
        if (Number.isInteger(numA) && Number.isInteger(numB)) {
           
            document.getElementById('result').value = numA + numB;
        } else {
            alert('Alguno de los valores introducidos no es entero')
        }
    
    } else {
        alert('Alguno de los valores introducidos no es numero')
    }

});
document.getElementById("sub").addEventListener('click', ()=>{
    
    var numA = Number(document.getElementById('numA').value);
    var numB = Number(document.getElementById("numB").value);

    
    if (!isNaN(numA) && !isNaN(numB)){

       
        if (Number.isInteger(numA) && Number.isInteger(numB)) {
        
            document.getElementById('result').value = numA - numB;
        } else {
            alert('Alguno de los valores introducidos no es entero')
        }

    } else {
        alert('Alguno de los valores introducidos no es numero')
    }
});
document.getElementById("mult").addEventListener('click',()=>{

var numA = Number(document.getElementById('numA').value);
var numB = Number(document.getElementById("numB").value);


if (!isNaN(numA) && !isNaN(numB)){

    
    if (Number.isInteger(numA) && Number.isInteger(numB)) {
       
        document.getElementById('result').value = numA * numB;
    } else {
        alert('Alguno de los valores introducidos no es entero')
    }

} else {
    alert('Alguno de los valores introducidos no es numero')
}
});
document.getElementById("div").addEventListener('click', ()=>{

var numA = Number(document.getElementById('numA').value);
var numB = Number(document.getElementById("numB").value);


if (!isNaN(numA) && !isNaN(numB)){

    
    if (Number.isInteger(numA) && Number.isInteger(numB)) {
        
            if(numB!=0){
       
        document.getElementById('result').value = numA / numB;
            }else{
                alert('El dividendo no puede ser 0')
            }
    } else {
        alert('Alguno de los valores introducidos no es entero')
    }

} else {
    alert('Alguno de los valores introducidos no es numero')
}
});
document.getElementById("exp").addEventListener('click', ()=>{

var numA = Number(document.getElementById('numA').value);
var numB = Number(document.getElementById("numB").value);


if (!isNaN(numA) && !isNaN(numB)){

    
    if (Number.isInteger(numA) && Number.isInteger(numB)) {
       
        document.getElementById('result').value = numA ** numB;
    } else {
        alert('Alguno de los valores introducidos no es entero')
    }

} else {
    alert('Alguno de los valores introducidos no es numero')
}
});











