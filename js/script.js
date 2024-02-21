// const xhr = new XMLHttpRequest();
const webapp = "codeDojo";
const content = document.querySelector(".content-wrapper");
const link_tags = document.querySelector(".links");


function _(id) {
    return document.querySelector(id);
}

function getUri(cookie) {
    let endpoint = cookie.split(";");
    let value;
    endpoint.forEach(element => {
        let pairs = element.split("=");
        if (pairs[0] === "reqEndpoint") {
            value = pairs[1];
        }
    });
    return value;
}

const cookies = document.cookie;

if (cookies != "") {
    const endpoint = getUri(cookies).split("/");
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
    let uri = getEndpoint(this.href);
    console.log(uri);

    if (window.location.pathname !== uri) {
        window.history.pushState({}, "", uri);

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
    }
})

function getEndpoint(url) {
    let parsedUrl = new URL(url);

    return parsedUrl.pathname;
}