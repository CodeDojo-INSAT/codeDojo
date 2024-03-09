const output = document.querySelector('.output');
const startTime = performance.now();
console.log(startTime);
document.querySelector('.user-input').addEventListener('keyup', (event) => {
    if (event.key === 'Enter') {
        const userInput = document.querySelector('.user-input');
        const inputText = userInput.value.trim();

        if (inputText !== '') {
            output.innerHTML += `<div class="out-prompt"><span class="prompt">uvchan@codeDojo $</span> ${inputText}</div>`;
            this.userPrompt(inputText);
            userInput.value = '';
        }
    }
});

let isMinimized = false;

const terminalBefore = () => {
    document.querySelector('.title-bar').style.visibility = 'hidden';
    document.querySelector('.content').style.visibility = 'hidden';
    document.querySelector('.bot').classList.add('terminali');
    document.querySelector('.bot').style.transition = '0.5s';
    setTimeout(() => document.querySelector('.bot').style.transition = '0s', 500);
}

const terminalAfter = () => {
    console.log("its asdasd");
    document.querySelector('.bot').classList.remove('terminali');
    document.querySelector('.bot').style.transition = '0.5s';
    setTimeout(() => {
        document.querySelector('.bot').style.transition = '0s';
        document.querySelector('.title-bar').style.visibility = 'visible';
        document.querySelector('.content').style.visibility = 'visible';
    }
        , 1000);
}

document.querySelector(".bot").addEventListener('click', (event) => {
    console.log(event.target.classList)
    if ((event.target.classList.contains('minimize'))) {
        terminalBefore();
        isMinimized = true;
    } else {
        if (startTime + performance.now() >= 1000) {

            terminalAfter();
            isMinimized = false;

        }
    }
});

document.addEventListener('DOMContentLoaded', (event) => {
    const terminal = document.querySelector('.bot');

    let isDragging = false;
    let dragOffsetX, dragOffsetY;

    document.querySelector('.bot').addEventListener('mousedown', e => {
        isDragging = true;
        dragOffsetX = e.clientX - terminal.offsetLeft;
        dragOffsetY = e.clientY - terminal.offsetTop;
        console.log(e.clientX)
        console.log(e.clientY)
        console.log(terminal.offsetLeft)
        console.log(terminal.offsetTop)
        document.querySelector('.bot').style.cursor = 'grabbing';
    });

    document.addEventListener('mousemove', (e) => {
        if (!isDragging) return;
        e.preventDefault();

        requestAnimationFrame(() => {
            if (isDragging) {
                terminal.style.left = `${e.clientX - dragOffsetX}px`;
                terminal.style.top = `${e.clientY - dragOffsetY}px`;

            }
        });
    });

    document.addEventListener('mouseup', e => {
        isDragging = false;
        terminal.style.cursor = 'default';
    });
});

function userPrompt(prompt) {
    const output = document.querySelector('.output');

    let parts = prompt.split(" ");
    switch (parts[0]) {
        case "ls":
            output.innerHTML += `<div class="out-prompt">No submission found [+] </div>`;
            break;

        case "clear":
            output.innerHTML = '';
            break;

        case "java":
            if (parts.length >= 2) {
                this.submitAjax("psvm", "1");
            }
            break;

        case "timepass":
            output.innerHTML += '<iframe src="https://1000webgames.com/games/trialsiceride/html5/" scrolling="no" allowfullscreen="" width="100%" height="100%" frameborder="0"></iframe>';
            break;

        case "cd":
            if (parts.length == 2) {
                loadPage("/codeDojo/views/"+parts[1])
                window.history.pushState({},"","/codeDojo/u/"+parts[1])
                
            }
            break;

        default:
            output.innerHTML += `<div class="out-prompt">Command not found you can prompt --help</div>`;
            break;
    }
}

function submitAjax(code, level) {
    const output = document.querySelector('.output');

    fetch('/codeDojo/services/course/checkAnswer.dojo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            code: code,
            level: level
        }),
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });
}