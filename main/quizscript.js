//region Global
var labelPoints,
    labelTime,
    title;

var buttons;
var buttonsText;

var bonus,
    num,
    tp;

var varations;
var questions;
var answers;

var count,
    points,
    time;

var debug;

var json;

var index;

var correctAnswers;
//endregion

//region Helpers
function alertDebug(string) {
    if (debug) {
        alert(string);
    }
}

function bind(id) { // shortcut for document.getElementById
    return document.getElementById(id);
}

function make(type) {
    return document.createElement(type);
}

function rng(max) {
    return Math.floor(Math.random() * max);
}
//endregion

//region Infrastructure
function init() {
    labelPoints = bind("points");
    labelTime = bind("time");
    title = bind("question");

    buttons = [bind("button1"), bind("button2"), bind("button3"), bind("button4")];
    buttonsText = ["", "", "", ""];

    bonus = sessionStorage.getItem("bonus");

    num = sessionStorage.getItem("num");

    tp = sessionStorage.getItem("tp");

    index = 0;

    answers = [];
    questions = [];

    switch (tp) {
        default:
            var types = "{\"typos\":[{\"typo\":\"Agua\",\"bonus\":[{\"typo\":\"Agua\",\"bonus\":0.5},{\"typo\":\"Dragón\",\"bonus\":0.5},{\"typo\":\"Fuego\",\"bonus\":2},{\"typo\":\"Planta\",\"bonus\":0.5},{\"typo\":\"Roca\",\"bonus\":2},{\"typo\":\"Tierra\",\"bonus\":2}]},{\"typo\":\"Bicho\",\"bonus\":[{\"typo\":\"Fuego\",\"bonus\":0.5},{\"typo\":\"Lucha\",\"bonus\":0.5},{\"typo\":\"Planta\",\"bonus\":2},{\"typo\":\"Psíquico\",\"bonus\":2},{\"typo\":\"Veneno\",\"bonus\":2},{\"typo\":\"Volador\",\"bonus\":0.5}]},{\"typo\":\"Dragón\",\"bonus\":[{\"typo\":\"Dragón\",\"bonus\":2}]},{\"typo\":\"Eléctrico\",\"bonus\":[{\"typo\":\"Agua\",\"bonus\":2},{\"typo\":\"Dragón\",\"bonus\":0.5},{\"typo\":\"Eléctrico\",\"bonus\":0.5},{\"typo\":\"Planta\",\"bonus\":0.5},{\"typo\":\"Tierra\",\"bonus\":0},{\"typo\":\"Volador\",\"bonus\":2}]},{\"typo\":\"Fantasma\",\"bonus\":[{\"typo\":\"Fantasma\",\"bonus\":2},{\"typo\":\"Normal\",\"bonus\":0},{\"typo\":\"Psíquico\",\"bonus\":0}]},{\"typo\":\"Fuego\",\"bonus\":[{\"typo\":\"Agua\",\"bonus\":0.5},{\"typo\":\"Bicho\",\"bonus\":2},{\"typo\":\"Dragón\",\"bonus\":0.5},{\"typo\":\"Fuego\",\"bonus\":0.5},{\"typo\":\"Hielo\",\"bonus\":2},{\"typo\":\"Planta\",\"bonus\":2},{\"typo\":\"Roca\",\"bonus\":0.5}]},{\"typo\":\"Hielo\",\"bonus\":[{\"typo\":\"Agua\",\"bonus\":0.5},{\"typo\":\"Dragón\",\"bonus\":2},{\"typo\":\"Hielo\",\"bonus\":0.5},{\"typo\":\"Planta\",\"bonus\":2},{\"typo\":\"Tierra\",\"bonus\":2},{\"typo\":\"Volador\",\"bonus\":2}]},{\"typo\":\"Lucha\",\"bonus\":[{\"typo\":\"Bicho\",\"bonus\":0.5},{\"typo\":\"Fantasma\",\"bonus\":0},{\"typo\":\"Hielo\",\"bonus\":2},{\"typo\":\"Normal\",\"bonus\":2},{\"typo\":\"Psíquico\",\"bonus\":0.5},{\"typo\":\"Roca\",\"bonus\":2},{\"typo\":\"Veneno\",\"bonus\":0.5},{\"typo\":\"Volador\",\"bonus\":0.5}]},{\"typo\":\"Normal\",\"bonus\":[{\"typo\":\"Fantasma\",\"bonus\":0},{\"typo\":\"Roca\",\"bonus\":0.5}]},{\"typo\":\"Planta\",\"bonus\":[{\"typo\":\"Agua\",\"bonus\":2},{\"typo\":\"Bicho\",\"bonus\":0.5},{\"typo\":\"Dragón\",\"bonus\":0.5},{\"typo\":\"Fuego\",\"bonus\":0.5},{\"typo\":\"Planta\",\"bonus\":0.5},{\"typo\":\"Roca\",\"bonus\":2},{\"typo\":\"Tierra\",\"bonus\":2},{\"typo\":\"Veneno\",\"bonus\":0.5},{\"typo\":\"Volador\",\"bonus\":0.5}]},{\"typo\":\"Psíquico\",\"bonus\":[{\"typo\":\"Lucha\",\"bonus\":2},{\"typo\":\"Psíquico\",\"bonus\":0.5},{\"typo\":\"Veneno\",\"bonus\":2}]},{\"typo\":\"Roca\",\"bonus\":[{\"typo\":\"Bicho\",\"bonus\":2},{\"typo\":\"Fuego\",\"bonus\":2},{\"typo\":\"Hielo\",\"bonus\":2},{\"typo\":\"Lucha\",\"bonus\":0.5},{\"typo\":\"Tierra\",\"bonus\":0.5},{\"typo\":\"Volador\",\"bonus\":2}]},{\"typo\":\"Tierra\",\"bonus\":[{\"typo\":\"Bicho\",\"bonus\":0.5},{\"typo\":\"Eléctrico\",\"bonus\":2},{\"typo\":\"Fuego\",\"bonus\":2},{\"typo\":\"Planta\",\"bonus\":0.5},{\"typo\":\"Roca\",\"bonus\":2},{\"typo\":\"Veneno\",\"bonus\":2},{\"typo\":\"Volador\",\"bonus\":0}]},{\"typo\":\"Veneno\",\"bonus\":[{\"typo\":\"Bicho\",\"bonus\":2},{\"typo\":\"Fantasma\",\"bonus\":0.5},{\"typo\":\"Planta\",\"bonus\":2},{\"typo\":\"Roca\",\"bonus\":0.5},{\"typo\":\"Tierra\",\"bonus\":0.5},{\"typo\":\"Veneno\",\"bonus\":0.5}]},{\"typo\":\"Volador\",\"bonus\":[{\"typo\":\"Bicho\",\"bonus\":2},{\"typo\":\"Eléctrico\",\"bonus\":0.5},{\"typo\":\"Lucha\",\"bonus\":2},{\"typo\":\"Planta\",\"bonus\":2},{\"typo\":\"Roca\",\"bonus\":0.5}]}]}";
            createQuestions(types);
    }

    count = 0;
    points = 0;

    debug = false;

    time = 0;

    correctAnswers = 0;

    interval = setInterval( function() {
        timer()
    }, 10);

    setUpQuestion();

    for (i in buttons) {
        buttons[i].addEventListener("click", buttonClicked);
    }
}

function stopTimer() {
    clearInterval(interval);
}

function timer() {
    time++;

    var msg = "Tiempo:\t";
    var res = time;

    var second = 100;
    var minute = second * 60;

    if (time > minute) {
        if (time < minute * 10) {
            msg += "0";
        }
        msg += Math.floor( res / minute) + ":";
        res = res % (minute);

    } else {
        msg += "00:";
    }
    if (time > second) {
        if (res < second * 10) {
            msg += "0";
        }
        msg += Math.floor(res / second) + ":";
        res = res % second;
    } else {
        msg += "00:";
    }
    msg += res;

    labelTime.innerHTML = msg;
}
//endregion

//region UI
function createQuestions(src) {
    json = JSON.parse(src).typos;
    var found;
    var n;
    var l;
    var next;

    size = Object.keys(json).length;

    for (var i = 0; i < num; i++) {
        found = false;
        l = 0;
        next;
        while (!found) {
            n = rng(size);
            while (next == null) {
                if (json[n].bonus[l].bonus == 2) {
                    next = json[n].bonus[l].typo;
                }
                l++;
                if (l >= Object.keys(json[n].bonus).length) {
                    l = 0;
                    n = rng(size);
                }
            }

            found = true;

            for (var j = 0; j < index; j++) {
                if (questions[j] == [json[n].typo] && answers == next) {
                    found = false;
                    next = null;
                }
            }

        }
        //alert(json[n].typo + " efectivo contra " + next);
        answers.push(next);
        questions.push(json[n].typo);
        next = null;
        index++;
    }

    index = 0;

}

function setUpQuestion() {
    var question = questions[index];

    title.innerHTML = "Contra que hace mas daño el tipo " + question + " ?";

    var i = 0;
    var correct = rng(Object.keys(buttons).length);
    var id;
    var type = answers[index];

    while (i < Object.keys(buttons).length) {
        var n = rng(size);
        if (i == correct) {
            buttonsText[i++] = type;
        } else {
            while (type == json[n].typo) {
                n = rng(size);
            }
            buttonsText[i++] = json[n].typo;
        }
    }

    for (i in buttons) {
        buttons[i].innerHTML = buttonsText[i];
    }
}

function buttonClicked() {
    var id = -1;
    i = -1;
    while (++i < 4 && id < 0) {
        alertDebug("id: " + i)
        if (buttons[i] == this) {
            id = i;
        }
    }
    var msg;
    if (buttonsText[id] == answers[index]) {
        var add = 1 * bonus
        points += add;
        labelPoints.innerHTML = "Puntos: " + points;
        msg = "La respuesta es correcta! Puntos +" + add;
        correctAnswers++;
    } else {
        msg = "La respuesta es incorrecta!";
    }
    alert(msg);

    index++;
    if (index < num) {
        setUpQuestion();
    } else {
        msg = "";
        var res = time;

        var second = 100;
        var minute = second * 60;

        if (time > minute) {
            if (time < minute * 10) {
                msg += "0";
            }
            msg += Math.floor( res / minute) + ":";
            res = res % (minute);

        } else {
            msg += "00:";
        }
        if (time > second) {
            if (res < second * 10) {
                msg += "0";
            }
            msg += Math.floor(res / second) + ":";
            res = res % second;
        } else {
            msg += "00:";
        }
        msg += res;


        alert("Felicidades, contestaste "  + correctAnswers +
            " respuetas correctas! \nTu tiempo final es de " + msg +
            "\nTu puntacion final es de " + points " puntos");
    }
}
//endergion

init();

