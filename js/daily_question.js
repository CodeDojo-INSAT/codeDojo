function getTodayQuestion() {
    var url = "/codeDojo/services/dq/get_question.dojo";
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status == 200) {
                data = JSON.parse(this.responseText);
                if (data.status === "failed") {
                    loadPage("/codeDojo/views/no_dq_ques");
                }
                else {
                    localStorage.setItem("dqData", JSON.stringify(data));
                    if (isShowingEditor(window.location.search)) {
                        loadPage("/codeDojo/views/editor", renderDT, data, "DQ Editor");
                    }
                    else {
                        loadPage("/codeDojo/views/show_today_question", showPreview, data);
                    }
                }
            }
        }
    }

    xhr.open("GET", url, true);
    xhr.send();
}

_(".top-nav .back-icon").addEventListener("click", function () {
    window.location.href = "/codeDojo/u/daily_question";
});

getTodayQuestion();

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
    _(".description .title h2").textContent = obj.data['title'];
    _(".description > p").textContent = obj.data['description'];
    if (localStorage.getItem("code")) {
        constants.questionCode = localStorage.getItem("code");
    }
}

function submitCode() {
    let data = { "code": constants.model.getValue(), "level": user_level };
    doAjax("/codeDojo/services/dq/submit_answer.dojo", data);
}

function showPreview(obj) {
    _(".container .question-title h2").textContent = obj.data["title"];
    _(".container .question-description p").textContent = obj.data["description"];
}