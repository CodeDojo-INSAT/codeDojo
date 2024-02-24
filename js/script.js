// const xhr = new XMLHttpRequest();
const webapp = "codeDojo";
const views_endpoint = "/codeDojo/views/"
const content = document.querySelector(".content-wrapper");
// const link_tags = document.querySelector(".links");


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

// link_tags.addEventListener("click", function(e) {
//     e.preventDefault();

//     // console.log("Default prevented");
//     let uri = getEndpoint(this.href);
//     console.log(uri);

//     if (window.location.pathname !== uri) {
//         let spilted_endpoint = uri.split("/");

//         window.history.pushState({}, "", uri);
//         renderPage(convert_to_views(spilted_endpoint[spilted_endpoint.length-1]))
//     }
// })

function getEndpoint(url) {
    let parsedUrl = new URL(url);
    return parsedUrl.pathname;
}


window.addEventListener("popstate", function(event) {
    renderPage(convert_to_views(event.target.window.location.pathname))
})


function renderPage(url) {
    fetch(url)
        .then(response => {
            if (response.ok) {
                return response.text();
            }
        })
        .then (html => {
            content.innerHTML = html;
        })
}

function convert_to_views(url) {
    let splited_url = url.split("/");

    endpoint = splited_url[splited_url.length-1]
    return `${views_endpoint}${endpoint}`
}