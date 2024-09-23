'use strict'
const express = require('express');
const app = express();
/**
 * paso 1ir a la carpeta del archivo+ npm i
 * ir al package.json- ver el nombre
 * npm run start
 * el script start ; start es el nombre del comando
 * express es una dependencia q crea un servidor
 * express es 1 importacion= require
 * */

const cors = require('cors');
const runner = require('./test-runner');

const bodyParser = require('body-parser');
app.use(bodyParser.json());

app.get('/', function (req, res) {
  res.sendFile(__dirname + '/views/index.html');
})

app.use(express.static(__dirname + '/public'));
/**
 * el inicio de la aplicacion ira siempre de public/ root proyecto
 * esta abriendo 1 servidor y esta sirviendo lo q esta en la carpeta public
 * tenemos 1 cliente js y style css en la carpeta public
 * luego nuestro servidor va a navegar a /hello
 */

app.get('/hello', function (req, res) {
  const name = req.query.name || 'Guest';
  res.type('txt').send('hello ' + name);
})

/**
 * define una variable con funcion
 * @param {*} req : lo q alguien demanda por http/request
 * @param {*} res : lo q devuelve/result
 */
const travellers = function (req, res) {
  let data = {};
  if (req.body && req.body.surname) {
    switch (req.body.surname.toLowerCase()) {
      case 'polo':
        data = {
          name: 'Marco',
          surname: 'Polo',
          dates: '1254 - 1324'
        };
        break;
      case 'colombo':
        data = {
          name: 'Cristoforo',
          surname: 'Colombo',
          dates: '1451 - 1506'
        };
        break;
      case 'vespucci':
        data = {
          name: 'Amerigo',
          surname: 'Vespucci',
          dates: '1454 - 1512'
        };
        break;
      case 'da verrazzano':
      case 'verrazzano':
        data = {
          name: 'Giovanni',
          surname: 'da Verrazzano',
          dates: '1485 - 1528'
        };
        break;
      default:
        data = {
          name: 'unknown'
        }
    }
  }
  res.json(data);
};
/**
 * luego se define 1 ruta a travelers
 * se hace una peticion put a /travelers y le pasamos el (travelers)
 */

app.route('/travellers')
  .put(travellers);

  /**
   * declara 1 var error
   * Este código es una parte de una aplicación Node.js que utiliza Express como marco de desarrollo y
   *  CORS para permitir solicitudes desde otros dominios. A continuación, explicaré el código por bloques:

let error;: Declara una variable llamada error. No se proporciona más información sobre cómo se utiliza 
o se actualiza esta variable en el código proporcionado.

app.get('/_api/get-tests', cors(), function (req, res, next) {...});: Define una ruta HTTP GET para
 la ruta '/_api/get-tests'. Utiliza el middleware CORS para permitir solicitudes desde otros dominios. 
 El primer manejador de la ruta toma tres parámetros: req (objeto de solicitud), res (objeto de respuesta) 
 y next (función que pasa la solicitud al siguiente middleware o manejador). En este manejador, se comprueba si hay un error. 
 Si existe un error, devuelve una respuesta JSON con el estado 'unavailable'. Si no hay un error, llama a la función next() para pasar al siguiente middleware.

function (req, res, next) {...}, function (req, res, next) {...}, function (req, res) {...}: 
Son tres manejadores de middleware adicionales encadenados. Cada uno de ellos realiza una tarea específica.

El segundo middleware verifica si existe un informe (runner.report).
 Si no hay informe, pasa la solicitud al siguiente middleware.

El tercer middleware está vinculado al evento 'done' del objeto runner
 (posiblemente un objeto EventEmitter). Cuando el evento 'done' se dispara, se ejecuta este middleware. 
 Dentro de este middleware, utiliza process.nextTick para asegurarse de que la respuesta se envíe 
 después de que se haya completado la ejecución actual del ciclo de eventos. Luego, envía una respuesta JSON aplicando la función testFilter al informe de runner.report y los parámetros de consulta req.query.type y req.query.n.

En resumen, este código establece una ruta en la aplicación Node.js que, cuando se accede a través
 de una solicitud GET, realiza algunas verificaciones basadas en el estado de error y la existencia de un 
 informe antes de enviar una respuesta JSON utilizando el informe filtrado por la función testFilter. 
 La respuesta puede diferir según si hay un error, si no hay informe o si el evento 'done' se ha disparado en runner.
   */
let error;
app.get('/_api/get-tests', cors(), function (req, res, next) {
  if (error)
    return res.json({ status: 'unavailable' });
  next();
},
  function (req, res, next) {
    if (!runner.report) return next();
    res.json(testFilter(runner.report, req.query.type, req.query.n));
  },
  function (req, res) {
    runner.on('done', function (report) {
      process.nextTick(() => res.json(testFilter(runner.report, req.query.type, req.query.n)));
    });
  });
/**
 * mensaje en la consola
 * declara puerto
 * abre servidor en ese puerto
 * ahi es cuando llama al runner
 * fichero test-runner.js
 * y muestra errores
 * server:todo lo q se hara
 * se llama al mtodo run de runner
 */

const port = process.env.PORT || 3000;
app.listen(port, function () {
  console.log("Listening on port " + port);
  console.log('Running Tests...');
  setTimeout(function () {
    try {
      runner.run();
    } catch (e) {
      error = e;
      console.log('Tests are not valid:');
      console.log(error);
    }
  }, 1500);
});


module.exports = app; // for testing

function testFilter(tests, type, n) {
  let out;
  switch (type) {
    case 'unit':
      out = tests.filter(t => t.context.match('Unit Tests'));
      break;
    case 'functional':
      out = tests.filter(t => t.context.match('Functional Tests'));
      break;
    default:
      out = tests;
  }
  if (n !== undefined) {
    return out[n] || out;
  }
  return out;
}
