const chai = require('chai');
const assert = chai.assert;
/**
 * test unitarios: n test a 1 sola funcion: pruebas unitarias
 * rdo q da comparas cn rdo q tienes
 * test funcionales: test a una coleccion
 * para cada una de las funcionalidades 1 conjunto de test
 * cada libreria de testeo ver  q funciones tienen
 * test-('nombre_test')
 * suite: conjunto de test en bloque
 * suite generica de unite tests, dentro tiene otros suites
 * cada suites tiene o suites o test
 * primer arg lo k te dan
 * 2o lo q esperas
 */
suite('Unit Tests', function () {
  suite('Basic Assertions', function () {
    // #1
    test('#isNull, #isNotNull', function () {
      assert.isNull(null, 'This is an optional error description - e.g. null is null');
      assert.isNotNull(1, '1 is not null');
    });
    // #2
    test('#isDefined, #isUndefined', function () {
      assert.isDefined(null, 'null is not undefined');
      assert.isUndefined(undefined, 'undefined IS undefined');
      assert.isDefined('hello', 'A string is not undefined');
    });
    /**
     * null= null
     * 0=number
     * undefined=undefined
     */
    // #3
    test('#isOk, #isNotOk', function () {
      assert.isNotOk(null, 'null is falsey');
      assert.isOk("I'm truthy", 'A string is truthy');
      assert.isOk(true, 'true is truthy');
    });
    /**
     * if object= da isok siempre cuando no sea nulo, indef, false
     * 
     */
    // #4
    test('#isTrue, #isNotTrue', function () {
      assert.isTrue(true, 'true is true');
      assert.isTrue(!!'double negation', 'Double negation of a truthy value is true');
      assert.isNotTrue({ value: 'truthy' }, 'Objects are truthy, but are not boolean values');
    });
  });

  // -----------------------------------------------------------------------------

  suite('Equality', function () {
    // #5
    test('#equal, #notEqual', function () {
      assert.equal(12, '12', 'Numbers are coerced into strings with ==');
      assert.notEqual({ value: 1 }, { value: 1 }, '== compares object references');
      assert.equal(6 * '2', '12');
      assert.notEqual(6 + '2', '12');
    });
    /**
     * en equal no comprueba el tipo ==
     * en strictEqual si comprueba el tipo ===
     * no se puede comparar arrays con == , da false
     */
    // #6
    test('#strictEqual, #notStrictEqual', function () {
      assert.notStrictEqual(6, '6');
      assert.strictEqual(6, 3 * 2);
      assert.strictEqual(6 * '2', 12);
      assert.notStrictEqual([1, 'a', {}], [1, 'a', {}]);
    });
    /**
     * se pueden pasar (js) de string a num si el string es un num
     * 6*'2' da 12 numerico, es strictEqual
     * comprueba el tipo despues de evaluarse
     * el rdo es el q se comprueva el tipo
     * en la ultima en pone 2 veces lo mismo, 
     * no se puede comparar array con un igual
     * se tienen k comprobar con un for-va dar false
     */
    // #7
    test('#deepEqual, #notDeepEqual', function () {
      assert.deepEqual({ a: '1', b: 5 }, { b: 5, a: '1' }, "The order of keys doesn't matter");
      assert.notDeepEqual({ a: [5, 6] }, { a: [6, 5] }, 'The order of array elements does matter');
    });
  });

  // -----------------------------------------------------------------------------

  function weirdNumbers(delta) {
    return 1 + delta - Math.random();
  }
  /**
   * math random dev num 0-1
   * 1+num recib+0/1
   */

  suite('Comparisons', function () {
    // #8
    test('#isAbove, #isAtMost', function () {
      assert.isAtMost('hello'.length, 5);
      assert.isAbove(1, 0);
      assert.isAbove(Math.PI, 3);
      assert.isAtMost(1 - Math.random(), 1);
    });
    // #9//revisar a partir 9 hasta el 18
    test('#isBelow, #isAtLeast', function () {
      assert.isAtLeast('world'.length, 5);
      //longitud no empieza de 0 , world da 5
      assert.isAtLeast(2 * Math.random(), 0);
      assert.isBelow(5 % 2, 2);
      //coges resto de la division entre 5/2, da 1 y lo comparas con 2
      assert.isBelow(2 / 3, 1);
    });
    // #10
    test('#approximately', function () {
      assert.approximately(weirdNumbers(0.5), 0.5, 1);
      assert.approximately(weirdNumbers(0.2), 0.2, 1);
    });
    //weirdnumbers: del 0,5 puede irse hasta el 1.5(1pt)
    //estara en un delta range, x arriba o por abajo, 1 punto de partida, 0 variar por arriba y por abajo
  });

  // -----------------------------------------------------------------------------

  const winterMonths = ['dec,', 'jan', 'feb', 'mar'];
  const backendLanguages = ['php', 'python', 'javascript', 'ruby', 'asp'];
  suite('Arrays', function () {
    // #11
    test('#isArray, #isNotArray', function () {
      assert.isArray('isThisAnArray?'.split(''), 'String.prototype.split() returns an array');
      assert.isNotArray([1, 2, 3].indexOf(2), 'indexOf returns a number');
      //split dev array, index dev indice
    });
    // #12
    test('Array #include, #notInclude', function () {
      assert.notInclude(winterMonths, 'jul', "It's summer in july...");
      assert.include(backendLanguages, 'javascript', 'JS is a backend language');
    });
  });

  // -----------------------------------------------------------------------------

  const formatPeople = function (name, age) {
    return '# name: ' + name + ', age: ' + age + '\n';
  };
  suite('Strings', function () {
    // #13
    test('#isString, #isNotString', function () {
      assert.isNotString(Math.sin(Math.PI / 4), 'A float is not a string');
      assert.isString(process.env.PATH, 'An env variable is a string (or undefined)');
      assert.isString(JSON.stringify({ type: 'object' }), 'JSON is a string');
      //path da 1 string donde se encuentra la path
    });
    // #14
    test('String #include, #notInclude', function () {
      assert.include('Arrow', 'row', "'Arrow' contains 'row'");
      assert.notInclude('dart', 'queue', "But 'dart' doesn't contain 'queue'");
      //dart no existe queue, miran las letras q estan en la primera estring, en alguna parte
    });
    // #15
    test('#match, #notMatch', function () {
      const regex = /^#\sname\:\s[\w\s]+,\sage\:\s\d+\s?$/;
      assert.match(formatPeople('John Doe', 35), regex);
      assert.notMatch(formatPeople('Paul Smith III', 'twenty-four'), regex);
    });
  });

  // -----------------------------------------------------------------------------

  const Car = function () {
    this.model = 'sedan';
    this.engines = 1;
    this.wheels = 4;
  };

  const Plane = function () {
    this.model = '737';
    this.engines = ['left', 'right'];
    this.wheels = 6;
    this.wings = 2;
  };

  const myCar = new Car();
  const airlinePlane = new Plane();

  suite('Objects', function () {
    // #16
    test('#property, #notProperty', function () {
      assert.notProperty(myCar, 'wings', "Cars don't have wings");
      assert.property(airlinePlane, 'engines', 'Planes have engines');
      assert.property(myCar, 'wheels', 'Cars have wheels');
    });
    // #17
    test('#typeOf, #notTypeOf', function () {
      assert.typeOf(myCar, 'object');
      assert.typeOf(myCar.model, 'string');
      assert.notTypeOf(airlinePlane.wings, 'string');
      assert.typeOf(airlinePlane.engines, 'array');
      assert.typeOf(myCar.wheels, 'number');
    });
    // #18
    test('#instanceOf, #notInstanceOf', function () {
      assert.notInstanceOf(myCar, Plane);
      assert.instanceOf(airlinePlane, Plane);
      assert.instanceOf(airlinePlane, Object);
      assert.notInstanceOf(myCar.wheels, String);
      //todos descienden de objetos, clase implicita
    });
  });

  // -----------------------------------------------------------------------------
});
