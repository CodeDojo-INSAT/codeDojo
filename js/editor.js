var autosave = true;

function initEditor() {
    require.config({ paths: { 'vs': '/codeDojo/js/vs' } });
    require(['vs/editor/editor.main'], function () {
        monaco.editor = monaco.editor.create(document.querySelector('#editor-container'), {
            value: `${constants.questionCode !== "" && constants.questionCode !== undefined ? constants.questionCode : "public class Main {\n   public static void main(String[] args) {\n      //Your code goes here\n   }\n}"}`,
            language: 'java',
            theme: 'vs-dark',
            fontFamily: "'Roboto', sans-serif",
            fontSize: 20,
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


initEditor();

setTimeout(() => {
    constants.model = monaco.editor.getModel();
    if (!constants.model) {
        window.location.reload();
    }
    makeBackup();
}, 500);
document.querySelector(".upload-file").addEventListener("change", setLoadFromFile);



// If user submit
const submit_btn = document.querySelector("#submit");
const result_area = document.querySelector(".result-area");

submit_btn.addEventListener("click", function (e) {
    showElement(result_area);
})

function showElement(element) {
    console.log(element);
    let classLists = element.classList;
    console.log(classLists);
    if (classLists.contains("hide")) {
        classLists.remove("hide");
    }
    console.log(classLists);
}

function hideElement(element) {
    let classLists = element.classList;
    if (!classLists.contains("hide")) {
        classLists.add("hide");
    }
}

function showLoader() {
    _(".result-area .loader-page").style.display = "flex";
}

function hideLoader() {
    _(".result-area .loader-page").style.display = "none";
}

function renderResponse(response) {
    response = JSON.parse(response);
    console.log(response);
    if (response["status"] === false) {
        // console.log("response failed" + response);
        if (response.message.includes("compilation")) {
            setOutputForCompilationError();
            let res = response.data.split("\n");

            let html = "";
            res.forEach(e => {
                html += `<span>${e}</span>`;
            });
            _(".right .input .content").innerHTML = html;
        }
        else if (response.message.includes("time out") || response.message.includes("Timeout")) {
            setOutputForExecutionTimeout();
        }
        else {
            if (response.data.type && response.data.type === "oops") {
                renderOops(response);
            }
            else {
                renderTestcases(response.data.result, response.data.sampleTestcase);
            }
        }
    }
    else {
        // console.log("Response" + response);
        if (response.data.type && response.data.type === "oops") {
            renderOops(response);
        }
        else {
            renderTestcases(response.data.result, response.data.sampleTestcase);
        }
    }
}

function renderOops(response) {
    console.log(response);
    setOutputForOops();

    // console.log("status " + response["status"]);
    if (response.status) {
        _(".compiler-message .content").textContent = "Success";
        _("#result").innerText = "completed :)";
    }
    else {
        // console.log("status failed");
        _(".compiler-message .content").textContent = "Wrong answer";
        _("#result").innerText = "Failed :(";
    }
    _(".expected-output .content").textContent = response.status ? 0 : 1;
    if (response.data.error) {
        let error = response.data["error"];
        let html = "";
        if (typeof error === "string") {
            html = `<span>${error}</span>`;
        }
        else {
            error.forEach(e => {
                html += `<span>${e}</span>`;
            });
        }
        _(".right .input .content").innerHTML = html;
    }
    else {
        _(".right .input .content").innerHTML = `<span>${response["message"]}</span>`;
    }
}

function setOutputForExecutionTimeout() {
    hideElement(_(".expected-output"));
    hideElement(_(".input"));
    _(".compiler-message .content").innerHTML = `<span>Execution Timeout...</span>`;
}

function setOutputForCompilationError() {
    hideElement(_(".compiler-message"));
    showElement(_(".input"));
    showElement(_(".expected-output"));
    _(".input legend").textContent = "Compilation Error";
    _("#result").innerText = "Compilation Failed....";
    _(".result-area .left").style.display = "none";
    _(".result-area .right").style.width = "100%";
    _("#result").classList.remove("hide");
    // _(".compiler-message .content").innerHTML = `<span>Compilation failed</span>`;
    _(".expected-output legend").textContent = "Exit status";
    _(".expected-output .content").textContent = '1';
}

function setOutputForOops() {
    showElement(_(".compiler-message"));
    showElement(_(".input"));
    showElement(_(".expected-output"));
    _(".result-area .left").style.display = "none";
    _(".result-area .right").style.width = "100%";
    _("#result").classList.remove("hide");
    _(".input legend").textContent = "Compiler";
    _(".expected-output legend").textContent = "Exit status";
}

function setOutputForTestcases() {
    showElement(_(".compiler-message"));
    showElement(_(".input"));
    showElement(_(".expected-output"));
    _(".input legend").textContent = "Input";
    _(".result-area .left").style.display = "unset";
    _(".result-area .right").style.width = "75%";
    _(".compiler-message .content").innerHTML = `<span>Compilation Success</span>`;
}

function renderTestcases(arr, sampleTc) {
    setOutputForTestcases();
    var success = `<i class="fi fi-rs-check"></i>`;
    var wrong = `<i class="fi fi-rs-cross-small"></i>`;
    var template = `<div class="testcase #status#">
    <h4>Test Case #id#<span>#icon#</span></h4>
</div>`;

    var totalTestcases = arr.length;

    var html = "";
    let completed = 0;
    let i = 0;
    arr.forEach(t => {
        html += template.replace("#id#", i)
            .replace("#icon#", t == 1 ? success : wrong)
            .replace("#status#", t == 1 ? "correct-answer" : "wrong-answer");
        if (t === 1) {
            completed++;
        }
        i++;
    });
    _(".testcase-container").innerHTML = html;

    var message = "";
    if (completed == totalTestcases) {
        message = `${completed}/${totalTestcases} Test cases Passed :)`;
        _("#next").classList.remove("hide");
    }
    else {
        message = `${completed}/${totalTestcases} Test cases failed :(`;
    }

    _("#result").classList.remove("hide");
    _("#result").innerText = message;

    if (sampleTc) {
        let input = sampleTc[0].split("\n");
        let output = sampleTc[1].split("\n");
        console.log("out " + output);

        let in_html = "";
        let out_html = "";

        for (let i = 0; i < input.length; i++) {
            in_html += `<span>${input[i]}</span>`;
        }
        for (let j = 0; j < output.length; j++) {
            out_html += `<span>${output[j]}</span>`;
        }

        _(".input .content").innerHTML = in_html;
        _(".expected-output .content").innerHTML = out_html;
    }
}

function showResult() {
    _(".result-area").classList.remove("hide");
    submitCode();
    _(".editor").scrollTop = _(".editor").scrollHeight;
}

_("#submit").addEventListener("click", showResult);

