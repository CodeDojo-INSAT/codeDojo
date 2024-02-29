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
var autosave = false;

constants.DESCRIPTION_TITLE = document.querySelector(".description .title");
constants.DESCRIPTION = document.querySelector(".description > p");

function initEditor() {
    require.config({ paths: { 'vs': '/codeDojo/js/vs' } });
    require(['vs/editor/editor.main'], function () {
        constants.editor = monaco.editor.create(document.querySelector('#editor-container'), {
            value: `${constants.questionCode !== "" && constants.questionCode !== undefined ? constants.questionCode : "public class Main {\n   public static void main(String[] args) {\n      //Your code goes here\n   }\n}"}`,
            language: 'java',
            theme: 'vs-dark',
            fontFamily: "Arial, sans-serif",
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


function setLoadFromFile(eve) {
    var file;
    if ((file = eve.target.files[0])) {
        document.querySelector(".filename").textContent = file.name;
        var reader = new FileReader();

        reader.onload = function (event) {
            console.log(event.target.result);
            constants.model.setValue(event.target.result);
        }
        reader.readAsText(file, "UTF-8");
    }
}

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
    if (autosave) {
        constants.model.onDidChangeContent(() => {
            localStorage.setItem("code", constants.model.getValue());
        });
    }
    else {
        console.log("Storage Cleared");
        localStorage.length = 0;
    }
}

//Terminal scripts

var username = "arjun";
var prompt;

constants.colors = {
    reset: '\x1b[0m',
    black: '\x1b[0;30m',
    red: '\x1b[0;31m',
    green: '\x1b[0;32m',
    yellow: '\x1b[0;33m',
    blue: '\x1b[0;34m',
    magenta: '\x1b[0;35m',
    cyan: '\x1b[0;36m',
    white: '\x1b[0;37m',
    bold: '\x1b[1m'
};

constants.TERMINAL_CONTAINER = document.querySelector(".terminal");


constants.TERMINAL_OPTIONS = {
    cursorBlink: true,
    fontFamily: "monospace",
    fontSize: 16,
    windowsMode: true,
    theme: {
        background: '#000000',
        foreground: '#00FF00' // Green font color
    }
}

constants.TERMINAL = new Terminal(constants.TERMINAL_OPTIONS);

function fitToContainer() {
    let containerHeight = constants.TERMINAL_CONTAINER.clientHeight;
    let lineHeight = getLineHeight();
    let rows = Math.floor(containerHeight / lineHeight);
    constants.TERMINAL_OPTIONS.rows = rows;
    constants.TERMINAL.resize(constants.TERMINAL.cols, rows);
}

function getLineHeight() {
    var rowDiv = document.querySelector(".xterm-rows > div");
    var lineHeight = rowDiv.style.lineHeight;
    return lineHeight.slice(0, -2);
}

//open a constants.TERMINAL to that given container
constants.TERMINAL.open(constants.TERMINAL_CONTAINER);
fitToContainer();


//create prompt for constants.TERMINAL
function createShellPrompt() {
    constants.TERMINAL.write("\r\n\x1b[1;32m");
    constants.TERMINAL.write((prompt = `${username}@codeDojo:~$ `));
    constants.TERMINAL.write("\x1b[0m");
}

constants.TERMINAL.prompt = createShellPrompt;
constants.TERMINAL.prompt();

var curr_line = "";
function handleInputs(e) {
    const clientX = constants.TERMINAL.buffer.active.cursorX;

    console.log(clientX);
    if(e.domEvent.keyCode === 8 && clientX > prompt.length) {
        curr_line = curr_line.slice(0, -1);
        constants.TERMINAL.write('\b \b');
    }
    else if (e.domEvent.keyCode === 13) {
        if (checkCommand(curr_line)) {
            constants.TERMINAL.prompt();
        }
        curr_line = '';
    }
    else if (e.key.length === 1) {
        if (clientX === prompt.length) {
            curr_line = "";
            curr_line += e.key;
        }
        curr_line += e.key;
        constants.TERMINAL.write(e.key);
    }
}

constants.TERMINAL.onKey(handleInputs);

document.querySelector(".action-btns").addEventListener("click", function(e) {
    if (e.target.classList.contains("minimize")) {
        document.querySelector(".terminal").style.display = "none";
        document.querySelector("#editor-container").classList.add("terminal-hided");
    }
    else if (e.target.classList.contains("maximize")) {
        document.querySelector(".terminal").style.display = "unset";
        document.querySelector("#editor-container").classList.remove("terminal-hided");

    }
})

function checkCommand(command) {
    var needPrompt = true;
    command = command.slice(1);

    var term = constants.TERMINAL;
    var colors = constants.colors;

    // console.log(command);
    switch (command) {
        case "clear":
            term.prompt();
            term.clear();
            needPrompt = false;
            break;
        case "ls":
            term.writeln("\n");
            term.writeln(`\t${colors.red}${colors.bold}[!] Not yet no completions. Nothing to show.${colors.reset}`);
            break;
        default:
            break;
    }

    return needPrompt;
}

fetchData("/codeDojo/services/course/getCourse?level=1");
initEditor();

setTimeout(() => {
    constants.model = constants.editor.getModel();
    makeBackup();
}, 500);
document.querySelector(".upload-file").addEventListener("change", setLoadFromFile);