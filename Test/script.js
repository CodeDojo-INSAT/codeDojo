// Define the class for the custom web component
class Terminal extends HTMLElement {
  constructor() {
    super();

    // Create a shadow DOM
    this.attachShadow({ mode: 'open' });

    // HTML template for the component
    this.shadowRoot.innerHTML = `

        <style>
          :host {
            display: block;
          }
  
          .terminal {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 600px;
            height: 400px;
            background-color: #333;
            border: 2px solid #ccc;
            border-radius: 8px;
            overflow: hidden;
          }
  
          .title-bar {
            background: linear-gradient(to bottom, #eaeaea, #d2d2d2);
            color: #000;
            padding: 5px;
            text-align: center;
            cursor: move;
            border: 1px solid black;
            display: flex;
            justify-content: space-between; /* Align items horizontally */
          }
  
          .title {
            font-weight: bold;
            flex-grow: 1; /* Take up available space */
          }
  
          .minimize, .close {
            background-color: transparent;
            border: none;
            color: #000;
            font-weight: bold;
            cursor: pointer;
            outline: none;
            text-align: right;
          }
  
          .content {
            height: calc(100% - 30px);
            padding: 10px;
            overflow-y: auto;
          }
  
          .input {
            display: flex;
          }
  
          .prompt {
            margin-right: 5px;
          }
  
          .user-input {
            flex: 1;
            background-color: transparent;
            border: none;
            color: #fff;
            outline: none;
          }
        </style>
        <div class="terminal">
          <div class="title-bar">
            <span class="title">CodeDojo-Terminal</span>
            <button class="minimize">-</button>
          </div>
          <div class="content">
            <div class="output"></div>
            <div class="input">
              <span class="prompt">uvchan@codeDojo$</span>
              <input type="text" class="user-input">
            </div>
          </div>
        </div>

      `;
  }

  connectedCallback() {

    // Add event listeners for minimize and close buttons
    this.shadowRoot.querySelector('.minimize').addEventListener('click', () => {
      this.shadowRoot.querySelector('.content').style.display = this.shadowRoot.querySelector('.content').style.display === 'none' ? 'block' : 'none';
    });

    this.shadowRoot.querySelector('.close').addEventListener('click', () => {
      this.style.display = 'none';
    });

    // Add event listener for handling user input
    this.shadowRoot.querySelector('.user-input').addEventListener('keyup', (event) => {
      // console.log("its working")
      if (event.key === 'Enter') {
        const userInput = this.shadowRoot.querySelector('.user-input');
        const output = this.shadowRoot.querySelector('.output');
        const inputText = userInput.value.trim();
        console.log(inputText)
        console.log(userInput)
        if (inputText !== '') {
          output.innerHTML += `<div><span class="prompt">uvchan@codeDojo $</span> ${inputText}</div>`;
          // Process input here if needed
          userInput.value = '';
        }
      }
    });
  }
}

// Define the custom element
customElements.define('terminal-code', Terminal);

const output = document.querySelector('.output');

document.querySelector('.user-input').addEventListener('keyup', (event) => {
  console.log("its working")

  if (event.key === 'Enter') {

    const userInput = document.querySelector('.user-input');
    const inputText = userInput.value.trim();

    if (inputText !== '') {
      output.innerHTML += `<div  class="out-prompt"><span class="prompt">uvchan@codeDojo $</span> ${inputText}</div>`;
      userPrompt(inputText);
      userInput.value = '';
    }

  }

});

let isMinimized = false;

function terminalBefore() {
  document.querySelector('.title-bar').style.visibility = 'hidden';
  document.querySelector('.content').style.visibility = 'hidden';
  document.querySelector('.bot').classList.add('terminali');
  document.querySelector('.bot').style.transition = '0.5s';
  setTimeout(()=>document.querySelector('.bot').style.transition = '0s',500);
  

}

function terminalAfter() {
  console.log("its asdasd");
  document.querySelector('.title-bar').style.visibility = 'visible';
  document.querySelector('.content').style.visibility = 'visible';
  document.querySelector('.bot').classList.remove('terminali');
}

document.querySelector(".bot").addEventListener('click', (event) => {
  console.log(event.target.classList)
  if ((event.target.classList.contains('minimize'))) {
    terminalBefore();
    isMinimized = true;
  } else {
    terminalAfter();
    isMinimized = false;
  }
});



function userPrompt(prompt) {

  let parts = prompt.split(" ");
  switch (parts[0]) {

    case "ls":
      console.log("ls");
      output.innerHTML += `<div class="out-prompt">No submission found [+] </div>`;
      break;

    case "clear":
      output.innerHTML = '';
      break;

    case "java":
      if (parts.length >= 2) {
        submitAjax("psvm", "1");
      }
      break;

    case "timepass":
      output.innerHTML += '<iframe src="https://1000webgames.com/games/trialsiceride/html5/" scrolling="no" allowfullscreen="" width="100%" height="100%" frameborder="0"></iframe>';
      break;

    case "cd":

      if (parts.length == 2) {
        window.location.href = "codeDojo/u/" + parts[1];
      }
      break;

    default:
      output.innerHTML += `<div class="out-prompt">Command not found you can prompt --help</div>`;
      break;
  }
}

function submitAjax(code, level) {

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

// document.querySelector(".bot").addEventListener('click', () => {
//     console.log("itsqqqqq");
//     if (document.querySelector('.title-bar').style.display === 'none'){

//         document.querySelector('.title-bar').style.display = document.querySelector('.content').style.display = ' ';
//         document.querySelector(".bot").classList.add('terminal');
//         document.querySelector(".bot").classList.remove('terminali');
//     }
// });

// var isMouseDown,initX,initY,height = draggable.offsetHeight,width = draggable.offsetWidth;

// draggable.addEventListener('mousedown', function(e) {
//   isMouseDown = true;
//   document.body.classList.add('no-select');
//   initX = e.offsetX;
//   initY = e.offsetY;
// })

// document.addEventListener('mousemove', function(e) {
//   if (isMouseDown) {
//     var cx = e.clientX - initX,
//         cy = e.clientY - initY;
//     if (cx < 0) {
//       cx = 0;
//     }
//     if (cy < 0) {
//       cy = 0;
//     }
//     if (window.innerWidth - e.clientX + initX < width) {
//       cx = window.innerWidth - width;
//     }
//     if (e.clientY > window.innerHeight - height+ initY) {
//       cy = window.innerHeight - height;
//     }
//     draggable.style.left = cx + 'px';
//     draggable.style.top = cy + 'px';
//   }
// })

// draggable.addEventListener('mouseup', function() {
//   isMouseDown = false;
//   document.body.classList.remove('no-select');
// })