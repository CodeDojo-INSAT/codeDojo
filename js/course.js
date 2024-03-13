constants.urlParams = new URLSearchParams(window.location.search);
var hasSubmission = false;

if (constants.urlParams.get("level")) {
    user_level = constants.urlParams.get("level");
}


doGet("/codeDojo/views/editor", onLoadPage, {func: doGet, param: `/codeDojo/services/course/getCourse${user_level ? "?level="+user_level:""}`,param1: render, pageName: "Course Editor"}, showTopNavLoader, hideTopNavLoader);


_(".top-nav .back-icon").addEventListener("click", function() {
    window.location.href = "/codeDojo/u/course";
});

function submitCode() {
    var value = constants.model.getValue();
    if (!value) {
        window.location.reload();
    }
    let data = { "code": value, "level": constants.user_level };
    // doAjax("/codeDojo/services/course/check_answer.dojo", data);
    doPost("/codeDojo/services/course/check_answer.dojo", data, renderResponse, showLoader, hideLoader);
}

_(".top-nav .back-icon").addEventListener("click", function() {
    window.location.href = "/codeDojo/u/course";
});



function render(response) {
    response = JSON.parse(response);
    if (user_level) 
        doGet("/codeDojo/services/course/getSubmission?level="+user_level, renderFromSubmission);
    constants.user_level = response["level"];
    // console.log(constants.user_level);
    _(".description .title h2").textContent = response["title"];
    _(".description").innerHTML = marked.parse(response["questionDescription"]);
    if (!hasSubmission) {
        if (localStorage.getItem("code")) {
            constants.questionCode = localStorage.getItem("courseCode");
        }
        else {
            constants.questionCode = response["questionCode"];
        }
    }
}

function renderFromSubmission(response) {
    response = JSON.parse(response);

    if (response.code) {
        hasSubmission = true;
        constants.questionCode = response.code
    }
}

function makeBackup() {
    if (autosave) {
        constants.model.onDidChangeContent(() => {
            localStorage.setItem("courseCode", constants.model.getValue());
        });
    }
    else {
        console.log("Storage Cleared");
        localStorage.length = 0;
    }
}
// doGet(`/codeDojo/services/course/getCourse${user_level ? "?level="+user_level: ""}`, render);
