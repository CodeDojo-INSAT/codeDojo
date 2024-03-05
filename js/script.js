// // const xhr = new XMLHttpRequest();
// const webapp = "codeDojo";
// const views_endpoint = "/codeDojo/views/"
// const content = document.querySelector(".content-wrapper");
// // const link_tags = document.querySelector(".links");


// function _(id) {
//     return document.querySelector(id);
// }

// function getUri(cookie) {
//     let endpoint = cookie.split(";");
//     let value;
//     endpoint.forEach(element => {
//         let pairs = element.split("=");
//         if (pairs[0] === "reqEndpoint") {
//             value = pairs[1];
//         }
//     });
//     return value;
// }

// const cookies = document.cookie;

// if (cookies != "") {
//     const endpoint = getUri(cookies).split("/");
//     console.log(endpoint[endpoint.length - 1]);

//     loadPage(`/${webapp}/views/${endpoint[endpoint.length - 1]}`, function() {
//         console.log("Page loaded succesfully");
//     })
//     // fetch(`/${webapp}/views/${endpoint[endpoint.length - 1]}`)
//     //     .then(response => {
//     //         if (response.ok) {
//     //             return response.text();
//     //         }
//     //     })
//     //     .then(html => {
//     //         content.innerHTML = html;
//     //     });
// }

// // link_tags.addEventListener("click", function(e) {
// //     e.preventDefault();

// //     // console.log("Default prevented");
// //     let uri = getEndpoint(this.href);
// //     console.log(uri);

// //     if (window.location.pathname !== uri) {
// //         let spilted_endpoint = uri.split("/");

// //         window.history.pushState({}, "", uri);
// //         renderPage(convert_to_views(spilted_endpoint[spilted_endpoint.length-1]))
// //     }
// // })

// function loadPage(url, callback) {
//     var xhr = new XMLHttpRequest();

//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status == 200) {
//                 var container = content;
//                 container.innerHTML = xhr.responseText;

//                 executeScripts(container);

//                 if (typeof callback === 'function') {
//                     callback();
//                 }
//             }
//             else {
//                 console.error("Error loading page " + xhr.status);
//             }
//         }
//     };
//     xhr.open("GET", url, true);
//     xhr.send();
// }

// function executeScripts(container) {
//     var scripts = container.querySelectorAll("script");

//     scripts.forEach(function (script) {
//         var newScript = document.createElement('script');

//         newScript.textContent = script.textContent;
//         container.appendChild(newScript);
//     })
// }

// function getEndpoint(url) {
//     let parsedUrl = new URL(url);
//     return parsedUrl.pathname;
// }


// window.addEventListener("popstate", function (event) {
//     renderPage(convert_to_views(event.target.window.location.pathname))
// })


// function renderPage(url) {
//     fetch(url)
//         .then(response => {
//             if (response.ok) {
//                 return response.text();
//             }
//         })
//         .then(html => {
//             content.innerHTML = html;
//         })
// }

// function convert_to_views(url) {
//     let splited_url = url.split("/");

//     endpoint = splited_url[splited_url.length - 1]
//     return `${views_endpoint}${endpoint}`
// }

// function loadCss(filename) {
//     var link = document.createElement('link');
//     link.rel = 'stylesheet';
//     link.type = 'text/css';
//     link.href = `/codeDojo/css/${filename}`;
//     document.head.appendChild(link);
// }

// function loadScript(filename) {
//     var script = document.createElement('script');
//     script.src = `/codeDojo/js/${filename}`;
//     document.head.appendChild(script);
// }


const sidebarBtn = document.querySelectorAll(".sidebar a");
const host = "http://arjun:9090";
const webapp = "codeDojo";
const views_endpoint = `/${webapp}/views/`;
const content = document.querySelector(".page-content");
const link_tags = document.querySelector(".nav-links a");
const constants = {};


sidebarBtn.onclick = () => {
    sidebarBtn.classList.toggle("active");
};

//it loads the page from ajax and initiate the scripts that contains
function loadPage(url, callback) {
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status == 200) {
                var container = content;
                container.innerHTML = xhr.responseText;

                executeScripts(container);

                if (typeof callback === 'function') {
                    callback();
                }
            }
            else {
                console.error("Error loading page " + xhr.status);
            }
        }
    };
    xhr.open("GET", url, true);
    xhr.send();
}

var dynamicScripts = [];
var dynamicStyles = [];

function executeScripts(container) {
    var scripts = container.querySelectorAll("script");

    scripts.forEach(function (script) {
        var newScript = document.createElement('script');

        newScript.textContent = script.textContent;
        container.appendChild(newScript);
        script.parentNode.removeChild(script);
    });
}

//remove script before load script dynamically so it won't conflict other page scripts.
function removeDynamicScripts() {
    if (dynamicScripts.length > 0) {
        dynamicScripts.forEach(function(script) {
            document.removeChild(script);
        });
        dynamicScripts.length = 0;
    }
}

function removeDynamicScriptsAndCss() {

    if (dynamicStyles.length > 0) {
        dynamicStyles.forEach(function(style) {
            document.removeChild(style);
        });
        dynamicStyles.length = 0;
    }
}


//handle url if user enter directly in url bar.
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
    console.log(endpoint[endpoint.length - 1]);

    loadPage(`${views_endpoint}${endpoint[endpoint.length - 1]}`, function () {
        console.log("Page loaded succesfully");
    });
}

//add listener to all sidebar url's
function listener(e) {
    e.preventDefault();

    let uri = getEndpoint(this.href);
    console.log(uri);

    if (window.location.pathname !== uri) {
        let spilted_endpoint = uri.split("/");

        window.history.pushState({}, "", uri);
        loadPage(convert_to_views(spilted_endpoint[spilted_endpoint.length - 1]));
    }
}

function addEventListenerToElements(selector, eventType, listener) {
    var elements = document.querySelectorAll(selector);

    elements.forEach(element => {
        element.addEventListener(eventType, listener);
    });
}

function getEndpoint(url) {
    let parsedUrl = new URL(url);
    return parsedUrl.pathname;
}

function convert_to_views(url) {
    let splited_url = url.split("/");

    endpoint = splited_url[splited_url.length - 1]
    return `${views_endpoint}${endpoint}`;
}

function loadCss(filename) {
    var link = document.createElement('link');
    link.rel = 'stylesheet';
    link.type = 'text/css';
    link.href = `/codeDojo/css/${filename}`;
    document.head.appendChild(link);
    dynamicStyles.push(link);
}

function loadScript(filename, bool) {
    var script = document.createElement('script');
    script.src = `/codeDojo/js/${filename}`;
    if (bool) {
        script.type = "module";
    }
    document.head.appendChild(script);
    dynamicScripts.push(script);
}

function isAlreadyInitiated(script) {
    var isLoaded = false;
    var index = 0;
    var i = 0;
    dynamicScripts.forEach(function (loadedScript) {
        if (loadedScript.src === script.src) {
            console.log("Already loaded " + script.src);
            index = i;
            isLoaded = true;
        }
        i++;
    });
    return isLoaded, index;
}

addEventListenerToElements(".nav-links a", "click", listener);