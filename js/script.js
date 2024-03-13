const sidebarBtn = document.querySelectorAll(".sidebar a");
const host = "http://arjun:9090";
const webapp = "codeDojo";
const views_endpoint = `/${webapp}/views/`;
const content = document.querySelector(".page-content");
const link_tags = document.querySelector(".nav-links a");
const constants = {};
const page_name = document.querySelector(".current-page-name");


sidebarBtn.onclick = () => {
    sidebarBtn.classList.toggle("active");
};

function showTopNavLoader() {
    _(".top-nav-realanimation").style.display = "unset";
}

function hideTopNavLoader() {
    setTimeout(() => {
        _(".top-nav-realanimation").style.display = "none";
    }, 1000);
}

function onLoadPage(response, options) {
    var container = content;
    container.innerHTML = response;

    executeScripts(container);

    if (options.pageName) {
        setPageName(options.pageName);
    }
    if (typeof options.func === "function") {
        if (options.param) {
            options.func(options.param);
        }
        else {
            options.func();
        }
    }
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
        dynamicScripts.forEach(function (script) {
            document.removeChild(script);
        });
        dynamicScripts.length = 0;
    }
}

function removeDynamicScriptsAndCss() {
    if (dynamicStyles.length > 0) {
        dynamicStyles.forEach(function (style) {
            document.removeChild(style);
        });
        dynamicStyles.length = 0;
    }
}


//handle url if user enter directly in url bar.
function getUri(cookie) {
    var value;
    cookie.split(";").forEach(c => {
        let pairs = c.split("=");
        if (pairs[0].includes("reqEndpoint")) {
            value = pairs[1];
            console.log("value" + value)

        }
    });

    return value;
}

const cookies = document.cookie;

if (cookies != "") {
    const endpoint = getUri(cookies).split("/").slice(2).join("/");

    let url = `${views_endpoint}${endpoint}`;
    // loadPage(, function () {
    //     console.log("Page loaded succesfully");
    // });
    doGet(url, onLoadPage, {pageName: url}, showTopNavLoader, hideTopNavLoader);

}

//add listener to all sidebar url's
function listener(e) {
    e.preventDefault();

    let uri = getEndpoint(this.href);
    // console.log(uri);

    if (window.location.pathname !== uri) {
        let spilted_endpoint = uri.split("/");

        window.history.pushState({}, "", uri);
        // loadPage(convert_to_views(spilted_endpoint[spilted_endpoint.length - 1]));
        let url = convert_to_views(spilted_endpoint[spilted_endpoint.length - 1]);
        doGet(url, onLoadPage, {pageName: url}, showTopNavLoader, hideTopNavLoader);
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

    endpoint = splited_url[splited_url.length - 1];
    return `${views_endpoint}${endpoint}`;
}

function setPageName(endpoint) {
    var pageName;
    // console.log(endpoint);
    if (endpoint.includes("/")) {
        pageName = endpoint.split("/").at(-1);
        pageName = pageName.split(/[-,_!^&]+/).join(" ");
    }
    else {
        pageName = endpoint;
    }

    page_name.textContent = pageName;
    return pageName;
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

function loadScriptFromWeb(url) {
    var script = document.createElement('script');
    script.src = url;
    document.body.appendChild(script);
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

function _(selector) {
    return document.querySelector(selector);
}

addEventListenerToElements(".nav-links a", "click", listener);
window.addEventListener("popstate", function (event) {
    let url = convert_to_views(event.target.window.location.pathname);
    doGet(url, onLoadPage, {pageName: url}, showTopNavLoader, hideTopNavLoader);
})

function doGet(url, callback, param, showLoader, hideLoader) {
    if (url.includes("editor") && callback === "onLoadPage"){
        _("body .sidebar").style.display = "none";
    }
    if (typeof showLoader === "function") {
        showLoader();
    }

    fetch(url)
    .then(response => {
        if (response.url.match(/\/codeDojo\/auth\/login/)) {
            window.location.reload();
        }
        else if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        else {
            return response.text();
        }
    })
    .then(data => {
        if (typeof hideLoader === "function") {
            hideLoader();
        }
        if (typeof callback === "function") {
            if (param) {
                callback(data, param);
            } else {
                callback(data);
            }
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function doPost(url, params, callback, showLoader, hideLoader) {
    if (params && typeof params !== "object") {
        console.log("Params not valid, must be an object. From doPost");
        return;
    }

    if (typeof showLoader === "function") {
        showLoader();
    }

    fetch(url, {
        method: "POST",
        headers: {
            contentType: "application/json",
        },
        body: params ? JSON.stringify(params) : ""
    })
    .then (response => {
        if (!response.ok) {
            throw new Error("Network response not ok");
        }
        else {
            return response.text();
        }
    })
    .then(data => {
        if (typeof hideLoader === "function") {
            hideLoader();
        }

        if (typeof callback === "function") {
            callback(data);
        }

    })
    .catch(error => {
        console.error("Error: " + error);
    });
}


let MONTHS_SHORT = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec"
]

function reformat_datestr(str){
    let dtstr = str.split(" ").join("T")
    let date = new Date(dtstr)
    let newDateStr = `${MONTHS_SHORT[date.getMonth()]} ${date.getDate()}`
    if (date.getFullYear() != new Date().getFullYear()){
        newDateStr += ` ${date.getFullYear()}`
    }

    return newDateStr
    
}
    
addEventListenerToElements(".nav-links a", "click", listener);