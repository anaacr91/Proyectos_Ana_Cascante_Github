const chai = require("chai");
const assert = chai.assert;

const server = require("../server");

const chaiHttp = require("chai-http");
const { application } = require("express");
chai.use(chaiHttp);
/**
 * aqui se evalua un conjunto de cosas
 * se crea la pagina web: index.html
 * nos dirigimos a cada 1 de las pistas q tiene la pag
 * si se obt un codigo 200 y el texto es x=pasa y
 * comportamiento usuario final cara a la app
 * test unitario
 * automatizar abrirte nav, abrirte google.com
 * si ha dev codigo 200-todo ok y buscar barra busqueda-dar c200
 * todo va bien
 * test funcional
 * todo en conjunto en marcha
 * app web q se esta ejecutando
 * comprobar si la web esta haciendo peticiones y comprobar rdo q dev
 */
suite("Functional Tests", function () {
  this.timeout(5000);
  suite("Integration tests with chai-http", function () {
    // #1
    test("Test GET /hello with no name", function (done) {
      chai
        .request(server)
        .keepOpen()
        .get("/hello")
        .end(function (err, res) {
          assert.equal(res.status, 200);
          /**si todo ha ido bien, la app devuelve hello guest */
          assert.equal(res.text, "hello Guest");
          done();
        });
    });
    // #2//siguiente clase ; revisar test del 9 al 18+ hacer functional tests
    test("Test GET /hello with your name", function (done) {
      chai
        .request(server)
        .keepOpen()
        .get("/hello?name=ana")
        .end(function (err, res) {
          assert.equal(res.status, 200);
          assert.equal(res.text, "hello ana");
          done();
        });
    });
    // #3
    test('Send {surname: "Colombo"}', function (done) {
      chai
        .request(server)
        .keepOpen()
        .put("/travellers")
        //desarrollar, faltan cosas
        .send({
          name: "Cristoforo",
          surname: "Colombo",
        })
        .end(function (err, res) {
          console.log("@@@@");
          console.log(res.body);
          assert.equal(res.status, 200);
          assert.equal(res.body.name, "Cristoforo");
          assert.equal(res.body.surname, "Colombo");
          assert.equal(res.type, 'application/json');//es un texto
          done();
        });
    });
    // #4
    test('Send {surname: "da Verrazzano"}', function (done) {
       chai
        .request(server)
        .keepOpen()
        .put("/travellers")
      .send({
          surname: "da Verrazzano"
        })
        .end(function (err, res) {
        assert.equal(res.status, 200);
        assert.equal(res.body.name, "Giovanni");
        assert.equal(res.body.surname, "da Verrazzano");
        assert.equal(res.type, 'application/json');//es un texto
      done();
      });
    });
  });
});

const Browser = require("zombie");
//require importar libreria zombie
Browser.site = 'https://127.0.0.1'; 
// Your URL here
suite("Functional Tests with Zombie.js", function () {
  this.timeout(5000);
  //codigo de inicializacion
suiteSetup(function(done) {
  return Browser.visit('/', done);
});
  suite("Headless browser", function () {
    test('should have a working "site" property', function () {
      assert.isNotNull(browser.site);
    });
  });

  suite('"Famous Italian Explorers" form', function () {
    // #5
    test('Submit the surname "Colombo" in the HTML form', function (done) {
      assert.fail();

      done();
    });
    // #6
    test('Submit the surname "Vespucci" in the HTML form', function (done) {
      assert.fail();

      done();
    });
  });
});
