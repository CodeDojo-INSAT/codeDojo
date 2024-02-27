constants.WELCOME_NAME_SPAN = document.getElementById("name");
// const content = document.querySelector(".content-wrapper");


document.addEventListener("DOMContentLoaded", function() {
    fetch ("/codeDojo/services/auth/get_name.dojo")
    .then(response => {
        if (response.ok) {
            return response.text();
        }
    })
    .then(data => {
        constants.WELCOME_NAME_SPAN.textContent = data;
    })
})