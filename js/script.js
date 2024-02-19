// const xhr = new XMLHttpRequest();
const webapp = "codeDojo";
const content = document.querySelector(".content-wrapper");
const link_tags = document.querySelector(".links");


function _(id) {
    return document.querySelector(id);
}

function getEndpoint(cookie) {
    let endpoint = cookie.split(";");
    let value;
    endpoint.forEach(element => {
        let pairs = element.split("=");
        if (pairs[0] === "endPoint") {
            value = pairs[1];
        }
    });
    return value;
}

const cookies = document.cookie;

if (cookies != "") {
    const endpoint = getEndpoint(cookies).split("/");
    console.log(endpoint[endpoint.length-1]);

    fetch(`/${webapp}/views/${endpoint[endpoint.length-1]}`)
    .then(response => {
        if (response.ok) {
            return response.text();
        }
    })
    .then(html => {
        content.innerHTML = html;
    });
}

link_tags.addEventListener("click", function(e) {
    e.preventDefault();

    // console.log("Default prevented");
    window.history.pushState({}, "", "quiz");
    fetch("/codeDojo/views/quiz")
    .then(response => {
        if (response.ok) {
            return response.text();
        }
    })
    .then (html => {
        // console.log(html);
        content.innerHTML = html;
    })

})