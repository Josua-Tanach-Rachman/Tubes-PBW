// Password
let eye_not_see = document.getElementsByClassName("eye")[0];
let eye_see = document.getElementsByClassName("eye-see")[0];
let password = document.getElementById("input-password");

eye_not_see.addEventListener('click', showPass);
eye_see.addEventListener('click', hidePass);

function showPass (even){
    eye_not_see.style.visibility = 'hidden';
    eye_see.style.visibility = 'visible'
    password.type = "text"
}

function hidePass (even){
    eye_not_see.style.visibility = 'visible';
    eye_see.style.visibility = 'hidden'
    password.type = "password"
}

// ConfrimPassword
let eye_not_see2 = document.getElementsByClassName("eye2")[0];
let eye_see2 = document.getElementsByClassName("eye-see2")[0];
let password2 = document.getElementById("input-re-password");

eye_not_see2.addEventListener('click', showPass2);
eye_see2.addEventListener('click', hidePass2);

function showPass2 (even){
    eye_not_see2.style.visibility = 'hidden';
    eye_see2.style.visibility = 'visible'
    password2.type = "text"
}

function hidePass2 (even){
    eye_not_see2.style.visibility = 'visible';
    eye_see2.style.visibility = 'hidden'
    password2.type = "password"
}

let button = document.getElementById("buttonRegister");
let warn = document.getElementById("warn-text");
button.addEventListener('click', checkSamePass);
function checkSamePass (){
    if (password.value != password2.value){
        warn.style.visibility = 'visible';
    }
    else {
        warn.style.visibility = 'hidden';
    }
}