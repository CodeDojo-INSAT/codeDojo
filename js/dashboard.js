const welcome_name_span = document.getElementById("name");


document.addEventListener("DOMContentLoaded", function() {
    fetch ("/codeDojo/services/auth/get_name.dojo")
    .then(response => {
        if (response.ok) {
            return response.text();
        }
    })
    .then(data => {
        welcome_name_span.textContent = data;
    })
})