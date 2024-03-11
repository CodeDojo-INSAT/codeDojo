loadPage("/codeDojo/views/editor", fetchData, "/codeDojo/services/course/getCourse?level=1", "Course Editor");
_(".top-nav .back-icon").addEventListener("click", function() {
    window.location.href = "/codeDojo/u/course";
});

function submitCode() {
    let data = { "code": constants.model.getValue(), "level": user_level };
    doAjax("/codeDojo/services/course/check_answer.dojo", data);
}

_(".top-nav .back-icon").addEventListener("click", function() {
    window.location.href = "/codeDojo/u/course";
});

fetchData("/codeDojo/services/course/getCourse?level=1");

function fetchData(url) {
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status == 200) {
                var respone = JSON.parse(xhr.responseText);
                render(respone);
            }
        }
    }

    xhr.open("GET", url, true);
    xhr.send();
}

function render(response) {
    _(".description .title h2").textContent = response["title"];
    _(".description > p").textContent = response["questionDescription"];
    if (localStorage.getItem("code")) {
        constants.questionCode = localStorage.getItem("code");
    }
    else {
        constants.questionCode = response["questionCode"];
    }
}