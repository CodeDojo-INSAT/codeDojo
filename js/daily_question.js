function getTodayQuestion() {
    var url = "/codeDojo/services/dq/get_question.dojo";
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status == 200) {
                data = JSON.parse(this.responseText);
                if (data.status !== "failed") {
                    loadPage("/codeDojo/views/editor", renderDT, data, "Daily Question Editor");
                }
            }
        }
    }

    xhr.open("GET", url, true);
    xhr.send();
}

getTodayQuestion();

_(".top-nav .back-icon").addEventListener("click", function () {
    window.location.href = "/codeDojo/u/dashboard";
});

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