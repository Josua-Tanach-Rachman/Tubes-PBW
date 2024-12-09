document.querySelectorAll('.password').forEach(passwordDiv => {
    const eye_not_see = passwordDiv.querySelector('.eye');
    const eye_see = passwordDiv.querySelector('.eye-see');
    const passwordInput = passwordDiv.querySelector('input');

    eye_not_see.addEventListener('click', () => {
        passwordInput.type = "text";
        eye_not_see.style.visibility = 'hidden';
        eye_see.style.visibility = 'visible';
    });

    eye_see.addEventListener('click', () => {
        passwordInput.type = "password";
        eye_see.style.visibility = 'hidden';
        eye_not_see.style.visibility = 'visible';
    });
});
