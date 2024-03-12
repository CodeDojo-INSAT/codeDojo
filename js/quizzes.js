
// let QUIZ_MODULE_JS_MARKER = "LOADED";
constants.current_quiz_obj = null;
constants.current_question = 0;
constants.SLICE_PNG = "/codeDojo/img/slash.png"
constants.TIED_PNG = "/codeDojo/img/tied.png"

constants.quizzesParent = document.querySelector(".quizlist")
constants.contentWrapper = document.querySelector(".content-wrapper")
constants.sectionWrapper = document.querySelector(".section-wrapper")
function reset_frame () {
    constants.contentWrapper.innerHTML = "";
}

constants.answers = []

function generate_submisssion_status_tag(passStatus) {
    let status_tag = document.createElement("div")
    status_tag.classList.add("completion-status", passStatus ? "pass" : "fail")
    status_tag.textContent = passStatus ? "COMPLETED" : "FAILED"

    return status_tag
}

function generate_completion_page(respObj){
    let passStatus = !respObj.checkSequence.includes("0")
    let completion_container = document.createElement("div");
    completion_container.classList.add("completion-card");

    let h1 = document.createElement('h1')
    h1.classList.add("completion-text")
    h1.textContent = passStatus ? "Quiz Completed!" : "Quiz Failed"
    completion_container.appendChild(h1)

    let completion_img = document.createElement("img");
    completion_img.classList.add("completion-img", passStatus ? "slice" : "tied");
    completion_img.src = passStatus ? constants.SLICE_PNG : constants.TIED_PNG

    completion_container.appendChild(completion_img)

    let quiz_info = document.createElement("quiz-info");
    quiz_info.classList.add("quiz-info")
    let h2 = document.createElement("h2")
    h2.classList.add("quiz-title-completed")
    h2.textContent = constants.current_quiz_obj.quizInfo.quizName
    quiz_info.appendChild(h2)


    quiz_info.appendChild(
        generate_date_tag(constants.current_quiz_obj.quizInfo.createdOn)
    )
 
    quiz_info.appendChild(
        generate_submisssion_status_tag(passStatus)
    )
    completion_container.appendChild(quiz_info)

    let score_info = document.createElement("p")
    score_info.classList.add("correct-count")
    score_info.innerHTML = `Score: <span class="correct-val">${respObj.correctCount}</span> / <span class="total-questions">${constants.current_quiz_obj.quizInfo.numQuestions}</span>`;
    completion_container.appendChild(score_info)

    let button_container = document.createElement("div")
    button_container.classList.add("button-container")

    let leave_button = document.createElement("div");
    leave_button.classList.add("button", passStatus ? "fill" : "stroke");
    leave_button.textContent = "Leave";

    leave_button.addEventListener("click", (ev) => {
        location.href = "/codeDojo/u/quiz";
        constants.current_quiz_obj = null;
    })

    button_container.appendChild(leave_button);

    let retry_button = document.createElement("div");
    retry_button.classList.add("button", passStatus ? "stroke" : "fill");
    retry_button.textContent = "Retry";
    retry_button.addEventListener("click", (ev) => {
        request_quiz_data(constants.current_quiz_obj.quizInfo.quizID)
    })
    button_container.appendChild(retry_button);

    
    completion_container.appendChild(button_container)

    return completion_container;

}

function display_completion_container(respObj){
    constants.sectionWrapper.textContent = "";
    constants.sectionWrapper.appendChild(
        generate_completion_page(respObj)
    )
}

function generate_option(opid, optionText){
    let optContainer = document.createElement('div');
    optContainer.classList.add("option", "clickable");
    optContainer.setAttribute("id", opid);

    optContainer.appendChild(generate_radio_button());

    let optionTxtEle = document.createElement("p");
    optionTxtEle.classList.add("option-text", "clickable");
    optionTxtEle.textContent = optionText;

    optContainer.appendChild(optionTxtEle);

    return optContainer;
    
}


function generate_radio_button(){

    
    let radioOuter = document.createElement('div');
    radioOuter.classList.add("radio", "clickable")

    let radioInner = document.createElement('div')
    radioInner.classList.add("radio-inner", "clickable")

    radioOuter.appendChild(radioInner)

    return radioOuter
}

function getSelectedOption() {
    let selectedOption = document.querySelector(".checked").parentElement.id
    constants.answers.push(
        {
            "questionID" : constants.current_quiz_obj.questions[constants.current_question].questionID,
            "optionID" : selectedOption
        }
    )
}

function checkAnswer(){
    request_payload = {
        "quizID" : constants.current_quiz_obj.quizInfo.quizID,
        "answers" : constants.answers
    }

    console.log(request_payload)

    let url = "/codeDojo/services/quiz/submitAnswers.dojo"
    let xhr = new XMLHttpRequest()

    let resp;
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            resp = JSON.parse(this.responseText)
            console.log(resp)
            display_completion_container(resp)
            constants.current_question = 0;
            constants.answers = []
        }
    }

    xhr.open("POST", url, true);
    xhr.send(JSON.stringify(request_payload));
}

function generate_date_tag(creationDate){
    let pubDateContainer = document.createElement('div')
    pubDateContainer.classList.add("quiz-pub-date")
    pubDateContainer.title = "Posted on " + creationDate
    let clock = document.createElement("i")
    clock.classList.add("fa-regular", "fa-calendar-days")

    let dateSpan = document.createElement("span")
    dateSpan.classList.add("date-val")
    dateSpan.textContent = reformat_datestr(creationDate)
    pubDateContainer.appendChild(clock)
    pubDateContainer.appendChild(dateSpan)

    return pubDateContainer;
}

function generate_quiz_info_header(qname, creationDate){
    let qiheader = document.createElement("div")
    qiheader.classList.add("quiz-info-header")

    let qiTitle = document.createElement("h2")
    qiTitle.classList.add("quiz-title-top")
    qiTitle.textContent = qname

    qiheader.appendChild(qiTitle)

    let pubDateContainer = generate_date_tag(creationDate)



    qiheader.appendChild(pubDateContainer)

    return qiheader
}


function generate_progress_header(totalQuestions){
    let progressContainer = document.createElement('div')
    progressContainer.classList.add("progress-header")

    let h2 = document.createElement("h2")

    h2.innerHTML = `Question <span class="q-num-progress">0</span>/<span class="q-num-total-progress">${totalQuestions}</span> Completed`

    progressContainer.appendChild(h2)

    return progressContainer
    
}

function generate_progress_bar(){
    let barOuter = document.createElement("div")
    barOuter.classList.add("quiz-questions-progress-bar")

    let barInner = document.createElement("div")

    barInner.classList.add("quiz-questions-progess-bar-filler")
    barInner.style.width = "1%";

    barOuter.appendChild(barInner)

    return barOuter;
}

function generate_question_box(qNo, questionText){
    let questionBox = document.createElement('div')
    questionBox.classList.add("quiz-question-box")

    let qNumP = document.createElement("p")
    qNumP.classList.add("quiz-question-text", "quiz-question-no")
    qNumP.textContent = qNo

    let qTextP = document.createElement('div')
    qTextP.classList.add("quiz-question-text","quiz-question-value")
    qTextP.textContent = questionText

    questionBox.appendChild(qNumP)
    questionBox.appendChild(qTextP)

    return questionBox

}

function generate_answer_box(){
    let answerBox = document.createElement("div")
    answerBox.classList.add("quiz-question-answer-box")

    return answerBox
}

function generate_submit_button(){
    let button = document.createElement('div')
    button.classList.add("submit-button")
    button.textContent = "Submit"
    button.onclick = submitQuestion
    return button
}


function update_question_data(){
    let curQ = constants.current_quiz_obj.questions[constants.current_question]
    let qID = curQ.questionID.slice(1)
    let questionText = curQ.questionText

    document.querySelector(".quiz-question-no").textContent = qID
    document.querySelector(".quiz-question-value").textContent = questionText

    let answerBox = document.querySelector(".quiz-question-answer-box")

    answerBox.textContent = '';

    curQ.options.forEach(function(ele) {
        answerBox.appendChild(
            generate_option(ele.OptionID, ele.OptionText)
        )
    })    

}

function update_progress_bar(){
    // let curQ = constants.current_quiz_obj.questions[constants.current_question]
    let totQ = constants.current_quiz_obj.quizInfo.numQuestions
    let qID = constants.current_question

    console.log(qID)
    document.querySelector(".q-num-progress").textContent = qID

    document.querySelector(".quiz-questions-progess-bar-filler")
    .style.width = Math.round(((qID) / totQ) * 100) + "%"


}


function submitQuestion(){
    let totQ = constants.current_quiz_obj.quizInfo.numQuestions
    getSelectedOption()
    if (constants.current_question <= totQ-1){
        constants.current_question++;
    }

    update_progress_bar()

    let button = document.querySelector(".submit-button")

    button.textContent = "next"

    button.onclick = nextQuestion
}

function nextQuestion(){
    if (!(constants.current_question < constants.current_quiz_obj.quizInfo.numQuestions)){
        checkAnswer()

    }
    update_question_data()

    let button = document.querySelector(".submit-button")

    button.textContent = "submit"

    button.onclick = submitQuestion
}

function show_results(){

}

function getQuizzes() {
    let url = "/codeDojo/services/quiz/getQuizzes.dojo"
    let xhr = new XMLHttpRequest()

    let resp;
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            resp = JSON.parse(this.responseText)
            console.log(resp)
            resp.forEach(element => {
                
                wrap_data_with_html(element)
                
        
            });
        }
    }

    xhr.open("GET", url, true);
    xhr.send();
}

function wrap_data_with_html(element){
    let quizItem = document.createElement("quiz-item")
    quizItem.setAttribute("type", element.quizType);
    quizItem.setAttribute("title", element.quizName);
    quizItem.setAttribute("id", element.quizID)
    quizItem.setAttribute("status", element.submissionInfo.submissionStatus)

    let creationDate =  element.createdOn
    quizItem.setAttribute("date", creationDate)

     
    constants.quizzesParent.appendChild(quizItem)

}


constants.quizzesParent.addEventListener("click", function (ev) {
    if(ev.target.classList.contains("start-button")){
        reqId = ev.target.parentElement.parentElement.id;
        request_quiz_data(reqId)
        
    }
})


getQuizzes();



function request_quiz_data(quizID){
    let url = `/codeDojo/services/quiz/getQuestions.dojo?quizID=${quizID}`
    let xhr = new XMLHttpRequest()

    let resp;
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200){
            resp = JSON.parse(this.responseText)
            constants.current_quiz_obj = resp;
            console.log(constants.current_quiz_obj)
            load_quiz()
        }
    }

    xhr.open("GET", url, true);
    xhr.send();   
}

function load_quiz(){
    constants.sectionWrapper.textContent = '';

    constants.sectionWrapper.appendChild(
        generate_quiz_info_header(constants.current_quiz_obj.quizInfo.quizName, constants.current_quiz_obj.quizInfo.createdOn)
    )

    constants.sectionWrapper.appendChild(
        generate_progress_header(constants.current_quiz_obj.quizInfo.numQuestions)
    )

    constants.sectionWrapper.appendChild(
        generate_progress_bar()
    )

    constants.sectionWrapper.appendChild(
        generate_question_box(constants.current_question + 1, constants.current_quiz_obj.questions[constants.current_question].questionText)
    )

    let answerBox = generate_answer_box()

    constants.current_quiz_obj.questions[constants.current_question].options.forEach(function(ele) {
        answerBox.appendChild(
            generate_option(ele.OptionID, ele.OptionText)
        )
    })

    answerBox.addEventListener("click", function (ev) {
        
        if(ev.target.classList.contains("clickable")){
            document.querySelectorAll(".option").forEach(ele => {
            if (ele.id == ev.target.id || ele.id == ev.target.parentNode.id){
                ele.getElementsByClassName("radio")[0].classList.add("checked")
            }else{
                ele.getElementsByClassName("radio")[0].classList.remove("checked")
            }
        })}
    })
    constants.sectionWrapper.appendChild(answerBox)

    constants.sectionWrapper.appendChild(
        generate_submit_button()
    )
}



if (!customElements.get("quiz-item")) {
    class MyFirstCustomElement extends HTMLElement {
        constructor() {
            super();
            
        }
    
        connectedCallback() {
            console.log("Element called");
            this.createDiv();
        }
    
        attributeChangedCallback(name, oldValue, newValue) {
            console.log(`changed attr ${name} old: ${oldValue} to ${newValue}`);
        }
    
        static get observedAttributes() {
            return ["type"];
        }
    
        createDiv() {
            var type = this.getAttribute("type");
            var styleType = "tex-type";
            var typeText = "TEXT";
            if (type === "MCQ") {
                styleType = "mcq-type";
                typeText = type;
            }
    
            var title = this.getAttribute("title");
            var date = this.getAttribute("date");
            var completion_status = this.getAttribute("status");
            
            var div = document.createElement("div");
            div.classList.add("quiz-elem");
    
            let qtype_tag = document.createElement("div")
            qtype_tag.classList.add("qtype-tag", styleType)
            qtype_tag.textContent = typeText
            div.appendChild(qtype_tag)
    
            let q_info = document.createElement("div")
            q_info.classList.add("q-info")
            
            let q_title = document.createElement("p")
            q_title.textContent = title;
            q_title.classList.add("quiz-title")
            q_info.appendChild(q_title)
    
            let creationDateDiv = generate_date_tag(date)
            
            q_info.appendChild(creationDateDiv)
    
            let status_tag = document.createElement("div")
            status_tag.classList.add("completion-status", completion_status === "COMPLETED" ? "pass" : completion_status === "FAILED" ? "fail" : "NOT ATTEMTED" ? "yts" : "")
            status_tag.textContent = completion_status
            
            div.appendChild(q_info)
            div.appendChild(status_tag)
            
            let button = document.createElement("div")
            button.classList.add("start-button")
            button.textContent = "start"
    
            div.appendChild(button)
    
    
            this.appendChild(div);
        }
        
    }
    // console.log("ALready not defined");
    customElements.define("quiz-item", MyFirstCustomElement);
}
// console.log("Quiz loaded");