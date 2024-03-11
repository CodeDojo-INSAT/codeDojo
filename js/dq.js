setPageName("Daily Question");
// console.log(constants.data);
_(".container .button").addEventListener("click", function() {
    loadPage("/codeDojo/views/editor", renderDT, JSON.parse(localStorage.getItem("dqData")), "DQ Editor");
    window.history.pushState({}, "", "daily_question?editor=show");
})

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