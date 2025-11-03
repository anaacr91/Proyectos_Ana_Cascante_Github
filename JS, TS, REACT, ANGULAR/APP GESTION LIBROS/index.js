
const regexemail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,3}$/;

let libros = [];

function generarTabla() {
    const elementos = document.getElementById("listaElementos");
    let miTablaString = "";
    libros.forEach((libro) => {
        miTablaString +=
            `<div id="libro_${libro.ISBN}">
        <br>Titulo: ${libro.titulo}
        ISBN: ${libro.ISBN}
        Editorial: ${libro.editorial}
        <button id="eliminar_${libro.ISBN}">Eliminar</button>
        <button id="info_${libro.ISBN}">Ver Info</button></div>`;

    });
    elementos.innerHTML = miTablaString;

    libros.forEach((libro) => {
        document.getElementById("eliminar_" + libro.ISBN).addEventListener("click", () => {
            document.getElementById("libro_" + libro.ISBN).remove();
            let index = -1;
            for (let i = 0; i < libros.length; i++) {
                if (libros[i].ISBN == libro.ISBN) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                libros.splice(index, 1);
                localStorage.setItem("libros", JSON.stringify(libros));
            }
        });
        document.getElementById("info_" + libro.ISBN).addEventListener("click", () => {
            document.getElementById("text-modal").innerHTML = `<strong>Titulo</strong>: ${libro.titulo}
            <br><strong>ISBN</strong> ISBN: ${libro.ISBN} <strong>Editorial</strong>: ${libro.editorial} `;
            const myModal = new bootstrap.Modal(document.getElementById('modal-info-libros'));
            myModal.show();
        });
    });
}


document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("cerrar-modal").addEventListener("click", () => {
        myModal.hide();
    });
    const email = document.getElementById("input-email");
    const errortext = document.getElementById("errortext");
    const formulario = document.getElementById("formularioLibros");
    const librosObtenidos = localStorage.getItem("libros");

    if (librosObtenidos != null) { libros = JSON.parse(librosObtenidos); }

    formulario.addEventListener("submit", (event) => {
        event.preventDefault();
        if (regexemail.test(email.value)) {
            const nuevoLibro = new Libro(
                document.getElementById("titulo").value,
                document.getElementById("ISBN").value,
                document.getElementById("editorial").value
            );
            libros.push(nuevoLibro);
            localStorage.setItem("libros", JSON.stringify(libros));
            formulario.reset();
            generarTabla();
        } else {
            errortext.innerText = "el email es invalido, no puedes añadir un nuevo libro";
        }
    });

    email.addEventListener("keydown", (event) => {
        if (event.key == "Enter") {
            if (regexemail.test(email.value)) {
                sessionStorage.setItem("emailform", email.value);

            } else {
                errortext.innerText = "el email es invalido";
                generarTabla();
            }
        }
    });
});
