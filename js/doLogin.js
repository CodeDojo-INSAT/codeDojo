const login_form = document.getElementById("login-form");

login_form.addEventListener("submit", function(e) {
    e.preventDefault();
    let data = {"un": login_form.username.value, "ps": login_form.password.value}

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
        if (data === "false\n") {
            generate_otp();
            // window.location.href = "verify";
        }
        else if (data === "true\n") {
            window.location.href = "/codeDojo/u/dashboard";
        }
    });
})

function generate_otp() {
    // xhr.open("GET", "/codeDojo/services/auth/generate_otp.dojo");
    // xhr.send();
    fetch("/codeDojo/services/auth/generate_otp.dojo").then(response => {
        if (response.ok) {
            return response.text();
        }
    }).then(data => {
        window.location.href = "verify";
    });
}