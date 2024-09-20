const regexemail= /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,3}$/;


document.addEventListener("DOMContentLoaded", () =>{
    const email = document.getElementById("input-email");
    const errortext= document.getElementById("errortext");

    email.addEventListener("keydown", (event) =>{
    if (event.key=="Enter"){
        if (regexemail.test(email.value)){
            sessionStorage.setItem("emailform",email.value);
        } else{
            errortext.innerText="el email es invalido;"
        }
    }
    })
});

