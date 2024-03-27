function getTodayQuestion(response) {
    // hello
    // console.log("getTodayQuestion response "+ response);
    data = JSON.parse(response);
    // console.log("getTodayQuestion " + data);
    let url;
    if (!data.status) {
        // loadPage("/codeDojo/views/no_dq_ques");
        url = "/codeDojo/views/no_dq_ques";
        doGet(url, onLoadPage, {pageName: url}, showTopNavLoader, hideTopNavLoader);
    }
    else {
        localStorage.setItem("dqData", JSON.stringify(data));
        if (isShowingEditor(window.location.search)) {
            // loadPage("/codeDojo/views/editor", renderDT, data, "DQ Editor");
            url = "/codeDojo/views/editor";
            doGet(url, onLoadPage, {func: renderDT, param: data, pageName: "DQ Editor"}, showTopNavLoader, hideTopNavLoader);
        }
        else {
            url = "/codeDojo/views/show_today_question";
            // loadPage("/codeDojo/views/show_today_question", showPreview, data);
            doGet(url, onLoadPage, {func: showPreview, param: data, pageName: url}, showTopNavLoader, hideTopNavLoader);
        }
    }
}

_(".top-nav .back-icon").addEventListener("click", function () {
    window.location.href = "/codeDojo/u/daily_question";
});

// getTodayQuestion();

function isShowingEditor(url) {
    const urlParams = new URLSearchParams(url);

    var value;
    if ((value = urlParams.get("editor"))) {
        if (value === "show") {
            return true;
        }
    }
    else {
        return false;
    }
}

function renderDT(obj) {
    obj = JSON.parse(obj);
    _(".description .title h2").textContent = obj.data['title'];
    _(".description > p").textContent = obj.data['description'];


    if (localStorage.getItem("dqCode")) {
        constants.questionCode = localStorage.getItem("code");
    }
}

function submitCode() {
    let data = { "code": constants.model.getValue(), "level": user_level };
    // doAjax("/codeDojo/services/dq/submit_answer.dojo", data);
    doPost("/codeDojo/services/dq/submit_answer.dojo", data, renderResponse, showLoader, hideLoader);
}

function makeBackup() {
    if (autosave) {
        constants.model.onDidChangeContent(() => {
            localStorage.setItem("dqCode", constants.model.getValue());
        });
    }
    else {
        console.log("Storage Cleared");
        localStorage.length = 0;
    }
}

function showPreview(obj) {
    console.log("ShowPreview " + obj);
    obj = JSON.parse(obj);
    _(".container .question-title h2").textContent = obj.data["title"];
    _(".container .question-description p").textContent = obj.data["description"];

    if (obj.data["completed"] === true) {
        _(".container .question-status").classList.add("yes");
        _(".container .question-status").textContent = "completed";
        _(".container .button").textContent = "view";
    }
}

doGet("/codeDojo/services/dq/get_question.dojo", getTodayQuestion);