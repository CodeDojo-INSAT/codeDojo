setPageName("Daily Question");
_(".container .button").addEventListener("click", function() {
    let url = "/codeDojo/views/editor";
    doGet(url, onLoadPage, {func: renderDT, param: JSON.parse(localStorage.getItem("dqData")), pageName: "DQ Editor"}, showTopNavLoader, hideTopNavLoader);
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
    doPost("/codeDojo/services/dq/submit_answer.dojo", data, renderResponse, showLoader, hideLoader);
}