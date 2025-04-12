function Utils() {
   this.ready = function (fn) {
      if (typeof fn !== 'function') {
         return;
      }

      if (document.readyState === 'complete') {
         return fn();
      }

      document.addEventListener('DOMContentLoaded', fn, false);
   };

   this.ajax = function (options, cb) {
      const xmlhttp = new XMLHttpRequest();

      xmlhttp.onreadystatechange = function () {
         if (xmlhttp.readyState === 4){
           if(Math.floor(xmlhttp.status/100) === 2) {
             var results = xmlhttp.responseText;
             var type = xmlhttp.getResponseHeader('Content-Type');
             if(type.match('application/json')) {
               results = JSON.parse(results);
             }
             cb(null, results);
           } else {
             cb(xmlhttp);
           }
         }
      };

      const method = options.method || 'get';
      let url = options.url || '/';

      if(url.charAt(url.length - 1) === '/')
        url = url.slice(0,url.length-1);

      if (options.data) {
        let query;
        let contentType = "application/x-www-form-urlencoded";
        if(options.type && options.type === 'json') {
          query = JSON.stringify(options.data);
          contentType = "application/json";
        } else {
          query = [];
          for (let key in params) {
            query.push(key + '=' + encodeURIComponent(params[key]));
            query.push('&');
          }
          query.pop();
          query = query.join('');
        }

        switch(method.toLowerCase()){
          case 'get':
            url += ('?' + query);
            xmlhttp.open(method, url, true);
            xmlhttp.send();
            break;
          case 'put':
          case 'patch':
          case 'delete':
          case 'post':
            xmlhttp.open(method, url, true);
            xmlhttp.setRequestHeader("Content-type", contentType);
            xmlhttp.send(query);
            break;
          default:
            return;
        }
      } else {
        xmlhttp.open(method, url, true);
        xmlhttp.send();
      }

   };
}

const utils = new Utils();

utils.ready(function() {

  const form = document.getElementById('f1');
  const input = document.getElementById('i1');
  const div = document.getElementById('tn');
  form.addEventListener('submit', function(e){
    e.preventDefault();
    if(input.value) {
      const options = {
        method: 'put',
        url: '/travellers',
        type: 'json',
        data: {surname: input.value}
      };
      div.innerHTML = '<p>Loading...</p>';
      utils.ajax(options, function(err, res) {
        if(err) return console.log(err);
        div.innerHTML = '<p>first name: <span id="name">' + res.name + '</span><p>' +
          '<p>last name: <span id="surname">' + res.surname + '</span><p>' +
          '<p>dates: <span id="dates">' + res.dates + '</span><p>';
      });
    }
  });
});
/**
 * 
Este código define una función Utils que actúa como un conjunto de utilidades en JavaScript, 
y luego crea una instancia de esa utilidad llamada utils. 
Además, hay un evento que espera a que el documento HTML esté completamente cargado antes de ejecutar ciertas operaciones, 
y un formulario con un evento asociado para realizar una solicitud AJAX cuando se envía el formulario.

Aquí está el desglose del código:

Definición de la función Utils:

Utils es una función constructora que encapsula varias utilidades en un solo objeto.
this.ready es un método que toma una función fn como argumento y ejecuta esa función cuando el evento DOMContentLoaded del documento ocurre. 
Si el documento ya está completamente cargado, la función se ejecuta inmediatamente.
this.ajax es un método para realizar solicitudes AJAX utilizando el objeto XMLHttpRequest. 
Toma un objeto options que especifica la configuración de la solicitud y una función de devolución de llamada cb que se ejecuta después de que se complete la solicitud.
Creación de una instancia de Utils:

Se crea una instancia de Utils llamada utils.
Espera a que el documento esté listo:

Se utiliza utils.ready para esperar a que el documento esté completamente cargado antes de ejecutar el código dentro de la función de devolución de llamada. 
Esto garantiza que el código relacionado con el DOM se ejecute después de que el DOM esté disponible.
Manejo de un formulario:

Se selecciona un formulario del documento con el id 'f1', un campo de entrada con el id 'i1', y un div con el id 'tn'.
Se agrega un evento de escucha al formulario que captura el evento de envío.
Dentro del manejador de eventos, se previene el envío del formulario por defecto (e.preventDefault()).
Se verifica si hay un valor en el campo de entrada (input.value).
Si hay un valor, se construye un objeto options que especifica una solicitud PUT al servidor, utilizando el contenido del campo de entrada como datos JSON. 
Se actualiza el contenido del div con un mensaje de carga.
Se realiza una solicitud AJAX utilizando utils.ajax, y cuando se completa la solicitud, se actualiza el contenido del div con los datos recibidos del servidor.
En resumen, este código establece una estructura para manejar solicitudes AJAX, garantiza que el código relacionado con el DOM 
se ejecute después de que el documento esté completamente cargado y utiliza eventos para realizar acciones específicas cuando se envía un formulario.
 */