let loginBtn = document.querySelector('#login-btn');

loginBtn.addEventListener('click', async () => {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    const URL = 'http://localhost:8081/login';

    const jsonString = JSON.stringify({
        "username": usernameInput.value,
        "password": passwordInput.value
    });

    let res = await fetch(URL, {
        method: 'POST',
        body: jsonString,
        // credentials: 'include' // THIS IS VERY IMPORTANT IF you choose to use HttpSession. This is what tells the browser
                                    // To retain the HttpSession cookie that will be subsequently sent in all later requests
    });

    // Get the token and store the token into localStorage
    // LocalStorage is accessible from anywhere in the browser
    let token = res.headers.get('Token');
    localStorage.setItem('jwt', token);

    if (res.status === 200) {
        let user = await res.json();
        console.log("role  " + user.userRole);
        localStorage.setItem('user_id', user.id); // Keep track of the user id in the localStorage
        localStorage.setItem('user_role', user.userRole);
        if (user.userRole == 1) {
            window.location = '/admin.html';
        } else if (user.userRole == 2) {
            window.location = '/employee.html';
        }
    } else {
        let errorMsg = await res.text();
        console.log(errorMsg);

        let errorElement = document.querySelector('#error-msg');
        errorElement.innerText = errorMsg;
        errorElement.style.color = 'red';
    }
});