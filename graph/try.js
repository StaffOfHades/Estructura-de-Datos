var label = document.getElementById("answer");

var cadena = "b0011121b";

var LEFT = 0;
var RIGHT = 1;
var START = 0;        // Flags para los tres tipos de estado
var ACCEPT = 1;
var REJECT = 2;
var currentState;            // El estado actual de la maquina
var currentDirection;        // Direction 0 is to the left, 1 to the right
var cinta;                	// La cinta
var pointer;                // Apuntador actual con el indice del caracter que se esta leyendo
var states = {};            // Arreglo con todos los estados
var alfabeto = ['b', '#'];			// Arreglo con el alfabeto de entrada. Por definicion, b y # se agregan.

// las reglas de como se lee la cinta
function Rule(charIn, charOut, stateOut, direction) {
    // checar que los caracteres se encuentran en el lenguaje
    if ( !estaAlfabeto(charIn) ) {
        alert(charIn + " no esta en el alfabeto, problema con el enunciado");
    }

    if ( !estaAlfabeto(charOut) ) {
        alert(charOut + " no esta en el alfabeto, problema con el enunciado");
    }

    this.charIn = charIn;        // caracter que se lee
    this.charOut = charOut;      // caracter que se deja
    this.stateOut = stateOut;            // estado a donde te vas
    this.direction = direction;    // direcction hacia la que lees; 0 izquierda, 1 derecha
}

// Se leyo el caracter de entrada de esta regla, asi q corre
Rule.prototype.use = function() {
    cinta[pointer] = this.charOut;        // cambia el caracter que esta en la cinta
    currentState = states[this.stateOut];       // cambia el estado
    currentDirection = this.direction;        // cambia la dirrecion
};

// Diferentes estados, cada uno con su nombre individual
function State(nombre) {
    this.nombre = nombre;

    // booleanos que indican que tipos SI son
    this.start = false;
    this.accept = false;
    this.reject = false;

    // Crear el arreglo de reglas
    this.rules = {};
}

// agregar una regla al estado
State.prototype.addRule = function(rule) {
    this.rules[rule.charIn] = rule;     // agrega la regla con el indice siendo el caracter que se lee
};

// que el estado lea el caracter
State.prototype.read = function(char) {
    var rule =  this.rules[char];

    //Verificar q existe una regla, si no dile al usario
    if (rule != null) {
        rule.use();    // encuentra la regla que tiene el caracter de entrada que se mando
        // cada regla bajo cada estado individual es deterministico
    } else {
        alert("No existe regla para: " + char);
    }


};

// editar el tipo de estado que es, con las banderas
State.prototype.type = function(type) {
    if (type === START) {
        this.start = true;
    } else if (type === ACCEPT) {
        this.accept = true;
    } else if (type === REJECT) {
        this.reject = true;
    }
};

// Hardcoded, este deberia ser el parser
function read() {
    // Agregar el alfabeto
    alfabeto[alfabeto.length] = '0';
    alfabeto[alfabeto.length] = '1';

    // Crea todos los estados
    var state = new State("s");
    states[state.nombre] = state;
    state = new State("q");
    states[state.nombre] = state;
    state = new State("x");
    states[state.nombre] = state;

    // Agregar categoria de entrada al estado, y ponlo como estado inicial
    currentState = states["s"];
    states["s"].type(START);

    // Agregar categoria de aceptacion al estado
    states["s"].type(ACCEPT);

    // Agregar categoria de rechazo al estado
    states["x"].type(REJECT);

    // Aregar las reglas
    // Si estoy en el estado s, y leo un 0, dejo un 0, me muevo al estado s, y leo hacia la derercha
    states["s"].addRule(new Rule('0', '0', "s", RIGHT));

    // Si estoy en el estado s, y leo un 1, dejo un 1, me muevo al estado q, y leo hacia la derercha
    states["s"].addRule(new Rule('1', '1', "q", RIGHT));

    // Si estoy en el estado s, y leo una b, dejo la b, me muevo al estado s, y leo hacia la derercha
    states["s"].addRule(new Rule('b', 'b', "s", RIGHT));

    // Si estoy en el estado q, y leo un 0, dejo un 0, me muevo al estado q, y leo hacia la derercha
    states["q"].addRule(new Rule('0', '0', "q", RIGHT));

    // Si estoy en el estado q, y leo un 1, dejo un 1, me muevo al estado s, y leo hacia la derercha
    states["q"].addRule(new Rule('1', '1', "s", RIGHT));

    // Si estoy en el estado q, y leo una b, dejo la b, me muevo al estado x, y leo hacia la derercha
    states["q"].addRule(new Rule('b', 'b', "x", RIGHT));

}


// Verifica que la cadena esta en el lenjuage
function verify(cadena) {
    if ( cadenaAlfabeto(cadena) ) {
        this.cinta = cadena;		// Guarda la cadena
        var size = cinta.length; 	// Consigue su longitud

        pointer = 1;		// Empieza en el segudno elemento, pues se supone q la cadena empieza con el delimitor b

        // Mientras no estemos en un estado de rechazo,
        // y mientras estemos dentro de los limites de la cadena, corre
        while (!currentState.reject && pointer >= 0 && pointer < size) {
            // Para verificar, imprimir a al consola en que indce estas y en q estado
            console.log("Leyendo del indize " + pointer + ": " + cinta[pointer] + ", en el estado " + currentState.nombre);

            // Estado actual, usa tus reglas para leer el caracter actual
            currentState.read(cinta[pointer]);

            // Si la dirrecion de lectura es hacia al derecha, agrega 1 al indice
            // si es haica la izquiera, quitale 1
            if (RIGHT) {
                pointer++;
            } else {
                pointer--;
            }

        }

        // verficar que el estado actual no se uno no terminal
        if (currentState.reject && currentState.accept) {
            console.log("El estado actual no es un estado de rechazo o de aceptacion");
        }

        // El estado actual no es de rechazo y es de aceptaction
        return !currentState.reject && currentState.accept;
    }  else {
        console.log("La cadena tiene caracteres que no estan en el alfabeto de entrada");

        return false;
    }


}

function cadenaAlfabeto(cadena) {
    var esta = true;
    var i = 0;
    while (esta && (i < cadena.length) ) {
        esta = estaAlfabeto(cadena[i++]);
    }

    console.log(char + (esta ? " " : " no ") + " es parte del alfabeto");
    return esta;
}

function estaAlfabeto(char) {
    var esta = false;
    var i = 0;
    while (!esta && (i < alfabeto.length) ) {
        esta = char == alfabeto[i++];
    }
    console.log(char + (esta ? " " : " no ") + " es parte del alfabeto");
    return esta;
}

// Lee los estados y reglas
read();

// Muestra en una etiqueta el resultado
label.innerHTML = verify(cadena) ? "Cadena aceptada" : "Cadena rechazada";

