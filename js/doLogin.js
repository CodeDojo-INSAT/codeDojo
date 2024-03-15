const login_form = document.getElementById("login-form");
const signup_form = document.getElementById("signup-form");
login_form.addEventListener("click", function (e) {
    e.preventDefault();
    let data = { "un": document.getElementById("Login-username").value, "ps": document.getElementById("Login-password").value }

    fetch("/codeDojo/services/auth/login.dojo",
        {
            method: "POST",
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
        })
        
        .then(data => {
            console.log(data)
            if (data === "false\n") {
                generate_otp();
                // window.location.href = "verify";
            }
            else if (data === "true\n") {
                window.location.href = "/codeDojo/u/dashboard";
            }
        });
})



signup_form.addEventListener("click", function (e) {
    
    const usernameElement = document.getElementById("signup-username");
    const passwordElement = document.getElementById("signup-password");
    const emailElement = document.getElementById("signup-email");
    const firstnameElement = document.getElementById("signup-firstname");
    const lastnameElement = document.getElementById("signup-lastname");
    e.preventDefault();
    
    let data = {
        "un": usernameElement.value,
        "ps": passwordElement.value,
        "em": emailElement.value,
        "fn": firstnameElement.value,
        "ln": lastnameElement.value
    };

    fetch("/codeDojo/services/auth/signup.dojo",
        {
            method: "POST",
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
        })
        
        .then(data => {
            console.log(data)
            if (data === "false\n") {
                generate_otp();
                // window.location.href = "verify";
            }
            else if (data === "true") {
                window.location.reload();
            }
        });
})

function generate_otp() {
    fetch("/codeDojo/services/auth/generate_otp.dojo").then(response => {
        if (response.ok) {
            return response.text();
        }
    }).then(data => {
        window.location.href = "verify";
    });
}

function toggles() {
    let rememberme = document.querySelector(".remember-me");
    rememberme.addEventListener('click', () => {
        // console.log("work agu dhuuu");
        document.querySelector(".fa").classList.toggle("fa-check-square-o");
        document.querySelector(".fa").classList.toggle("fa-square-o");
    });

    let togglewrapper = document.querySelector(".login-toggle-container");
    let logintogggle = document.querySelector(".login-toggle");
    let signuptogggle = document.querySelector(".signup-toggle");
    let signup = document.querySelector(".login-container");
    let login = document.querySelector(".signup-container");
    let Loginninjaimg = document.querySelector(".login-ninja-img");
    let signupninjaimg = document.querySelector(".signup-ninja-img");

    togglewrapper.addEventListener('click', () => {
        signup.classList.toggle("toggle-translate");
        login.classList.toggle("toggle-translate");
        logintogggle.classList.toggle("login-toggled");
        signuptogggle.classList.toggle("login-toggled");
        Loginninjaimg.classList.toggle("z-index");
        signupninjaimg.classList.toggle("z-index");

    })
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}


function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function rememberMe() {

    setCookie("username", document.getElementById("Login-username").value, 30); 
    
}

function loadRememberMe() {
    let rememberedUsername = getCookie("username");
    if (rememberedUsername != "") {
        document.getElementById("Login-username").value = rememberedUsername;
        document.getElementById("rememberMein").className = "fa fa-check-square-o";
    }
}

window.onload = loadRememberMe;

toggles();

function fetchSvg(url,classname){

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(svgText => {
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = svgText.trim();
            const svgElement = tempDiv.querySelector('svg');
            const svgDataUrl = `data:image/svg+xml;base64,${btoa(svgElement.outerHTML)}`;
            console.log(svgDataUrl)
            document.querySelector('.'+classname).style.backgroundImage = `url(${svgDataUrl})`;
        })
        .catch(error => {
            console.error('There was a problem fetching the SVG:', error);
        });
}

fetchSvg("/codeDojo/img/moon.svg","moon")
fetchSvg("/codeDojo/img/test.svg","login-ninja-img")
fetchSvg("/codeDojo/img/signupninja.svg","signup-ninja-img")
fetchSvg("/codeDojo/img/Frame.svg","user-icon")
fetchSvg("/codeDojo/img/Frame.svg","singnup-usr")
fetchSvg("/codeDojo/img/Frame\(1\).svg","password-icon")
fetchSvg("/codeDojo/img/Frame\(1\).svg","signup-pas1")
fetchSvg("/codeDojo/img/Frame\(1\).svg","signup-pas")
fetchSvg("/codeDojo/img/mail.svg","email-icon")
fetchSvg("/codeDojo/img/mail.svg","email-icon")

