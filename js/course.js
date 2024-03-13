// constants.urlParams = new URLSearchParams(window.location.search);

// if (constants.urlParams.get("level")) {
//     user_level = constants.urlParams.get("level");
// }


doGet("/codeDojo/views/editor", onLoadPage, {func: doGet, param: `/codeDojo/services/course/getCourse`, pageName: "Course Editor"}, showTopNavLoader, hideTopNavLoader);

_(".top-nav .back-icon").addEventListener("click", function() {
    window.location.href = "/codeDojo/u/course";
});

function submitCode() {
    var value = constants.model.getValue();
    if (!value) {
        window.location.reload();
    }
    let data = { "code": value, "level": user_level };
    // doAjax("/codeDojo/services/course/check_answer.dojo", data);
    doPost("/codeDojo/services/course/check_answer.dojo", data, renderResponse, showLoader, hideLoader);
}

_(".top-nav .back-icon").addEventListener("click", function() {
    window.location.href = "/codeDojo/u/course";
});



function render(response) {
    response = JSON.parse(response);
    user_level = response["level"];
    _(".description .title h2").textContent = response["title"];
    _(".description").innerHTML = marked.parse(response["questionDescription"]);
    if (localStorage.getItem("code")) {
        constants.questionCode = localStorage.getItem("code");
    }
    else {
        constants.questionCode = response["questionCode"];
    }
}

doGet("/codeDojo/services/course/getCourse", render);
