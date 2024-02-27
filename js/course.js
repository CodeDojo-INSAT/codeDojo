// const submit_btn = _("#submit");
// const result_area = _(".result-area");
// const testcase_count = _("#count");
// const testcase_count_area = _("#result");
// const compiler_message = _(".compiler-message .content span");
// const expected_input = _(".input .content");
// const expected_input_legend = _(".input legend");
// const expected_output = _(".expected-output .content");

// const next_button = _("#next");
// const testcase_container = _(".testcase-container");

// const description_title = _(".description .title");
// const description = _(".description > p");
// let user_level;

// function _(ele) {
//     return document.querySelector(ele);
// }

// function initEditor() {
//     require.config({ paths: { 'vs': '/codeDojo/js/vs' } });
//     require(['vs/editor/editor.main'], function () {
//         window.editor = monaco.editor.create(document.querySelector('#editor-container'), {
//             value: `public class Main {\n   public static void main(String[] args) {\n      //Your code goes here\n   }\n}`,
//             language: 'java',
//             theme: 'vs-dark',
//             fontSize: 18,
//             minimap: {
//                 enabled: false,
//             },
//             automaticLayout: true,
//             scrollBeyondLastLine: false,
//             scrollbar: {
//                 verticalScrollbarSize: 5,
//                 horizontalScrollbarSize: 5,
//             },
//         });
//     });
//     getAjaxCallG("http://arjun:9090/codeDojo/services/course/getCourse?level=1");
// }

// initEditor();

// function submit() {
//     showElement(result_area);
//     if (window.editor) {
//         // console.log(window.editor);
//         let data = { "code": editor.getValue(), "level": user_level };
//         getAjaxCall("http://arjun:9090/codeDojo/services/course/check_answer.dojo", data);
//     }
// }

// function getAjaxCallG(url) {
//     fetch(url)
//         .then(response => {
//             if (response.ok) {
//                 return response.json();
//             }
//         })
//         .then(data => {
//             renderResponse(data);
//         })
// }

// function renderResponse(data) {
//     user_level = data["level"];
//     description_title.textContent = data["title"];
//     description.textContent = data["questionDescription"];
//     window.editor.setValue(data["questionCode"]);
// }

// function getAjaxCall(url, params) {
//     fetch(url, {
//         method: "POST",
//         headers: {
//             "content-type": "application/json",
//         },
//         body: JSON.stringify(params),
//     })
//         .then(response => {
//             if (response.ok) {
//                 return response.json();
//             }
//         })
//         .then(data => {
//             analyzeResponse(data);
//             console.log(data);
//         });
// }

// function analyzeResponse(data) {
//     let message;

//     if (data["status"] === "failed") {
//         if (data["message"] === "compilation error") {
//             showCompilationError(data);
//             message = "Compilation Error";
//         }
//         else {
//             let testcase_length = data["data"]["result"].length;
//             let correct_answer = count_correct_answer(data["data"]["result"]);
//             setSampleTestCases(data["data"]["sampleTestcase"]);

//             generateTestcaseCards(data["data"]["result"]);
//             showElement(testcase_count_area);
//             testcase_count.textContent = `${correct_answer}/${testcase_length}`;
//             message = "Wrong Answer";
//         }
//     }
//     else {
//         let testcase_length = data["data"]["result"].length;
//         let correct_answer = count_correct_answer(data["data"]["result"]);
//         setSampleTestCases(data["data"]["sampleTestcase"]);

//         generateTestcaseCards(data["data"]["result"]);
//         showElement(testcase_count_area);
//         testcase_count_area.textContent = "Test Cases Passed :)";
//         showElement(next_button);
//         message = "Correct Answer"
//     }
//     compiler_message.textContent = message;
// }

// function showCompilationError(data) {
//     showElement(testcase_count_area);
//     testcase_count_area.textContent = "Compilation Failed :(";
//     expected_input_legend.textContent = "Error";
//     expected_input.innerHTML = `<span>${data["data"]}</span>`;
//     hideElement(expected_output.parentElement);
//     expected_input.parentElement.style.maxHeight = "100%";
// }

// function setSampleTestCases(testcase) {
//     template = `<span>#content#</span>`
//     expected_input.innerHTML = template.replace("#content#", testcase[0]);
//     expected_output.innerHTML = template.replace("#content#", testcase[1]);
// }

// function generateTestcaseCards(arr) {
//     let template = `<div class="testcase #status#">
//                     <i></i> 
//                     <h3>Test Case #id#</h3>
//                 </div>`;
//     let html = "";
//     for (let i = 0; i < arr.length; i++) {
//         if (arr[i] === 0) {
//             html += template.replace("#status#", "fail")
//                 .replace("#id#", i);
//         }
//         else {
//             html += template.replace("#status#", "pass")
//                 .replace("#id#", i);
//         }
//         testcase_container.innerHTML = html;
//     }
// }

// function count_correct_answer(arr) {
//     let count = 0;
//     arr.forEach(element => {
//         if (element === 1) {
//             count++;
//         }
//     });
//     return count;
// }

// function hideElement(element) {
//     let classLists = element.classList;
//     if (!classLists.contains("hide")) {
//         classLists.add("hide");
//     }
// }

// function showElement(element) {
//     console.log(element);
//     let classLists = element.classList;
//     console.log(classLists);
//     if (classLists.contains("hide")) {
//         classLists.remove("hide");
//     }
//     console.log(classLists);
// }

// submit_btn.addEventListener("click", submit);
var user_level;

constants.DESCRIPTION_TITLE = document.querySelector(".description .title");
constants.DESCRIPTION = document.querySelector(".description > p");

function initEditor() {
    require.config({ paths: { 'vs': '/codeDojo/js/vs' } });
    require(['vs/editor/editor.main'], function () {
        constants.editor = monaco.editor.create(document.querySelector('#editor-container'), {
            value: `${constants.questionCode !== "" ? constants.questionCode : "public class Main {\n   public static void main(String[] args) {\n      //Your code goes here\n   }\n}"}`,
            language: 'java',
            theme: 'vs-dark',
            fontSize: 18,
            minimap: {
                enabled: false,
            },
            automaticLayout: true,
            scrollBeyondLastLine: false,
            scrollbar: {
                verticalScrollbarSize: 5,
                horizontalScrollbarSize: 5,
            },
        });
    });
}

function fetchData(url) {
    var xhr = new XMLHttpRequest();
    
    xhr.onreadystatechange = function() {
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
    constants.DESCRIPTION_TITLE.textContent = response["title"];
    constants.DESCRIPTION.textContent = response["questionDescription"];
    if (localStorage.getItem("code")) {
        constants.questionCode = localStorage.getItem("code");
    }
    else {
        constants.questionCode = response["questionCode"];
    }
}

function makeBackup() {
    constants.model.onDidChangeContent(() =>{
        localStorage.setItem("code", constants.model.getValue());
    });
}

fetchData("/codeDojo/services/course/getCourse?level=1");
initEditor();

setTimeout(() => {
    constants.model = constants.editor.getModel();
    makeBackup();
}, 500);