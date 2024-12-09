let eye_not_see = document.getElementsByClassName("eye")[0];
let eye_see = document.getElementsByClassName("eye-see")[0];
let password = document.getElementById("password");

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