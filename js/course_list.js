class CourseCard extends HTMLElement {
    constructor() {
        super();
        // this.attachShadow({mode: "open"});
    }

    #completedIcon = `<i class="fi fi-rs-check"></i>`;
    #lockedIcon = `<i class="fi fi-rs-lock"></i>`;

    connectedCallback() {
        var statusText = this.getAttribute("status");
        // console.log(this.#style);

        var icon;
        var buttonValue;
        if (statusText === "1") {
            icon = this.#completedIcon;
            buttonValue = "Completed";
        }
        else if (statusText === "-1") {
            icon = this.#lockedIcon;
            buttonValue = "Start";
        }

        var titleText = this.getAttribute("title");
        var descText = this.getAttribute("desc");

        var template = `<div class="icon">
                            ${icon}
                        </div>
                        <div class="course-details">
                            <span class="title">${titleText}</span>
                            <span class="desc">${descText}</span>
                        </div>
                        <div class="course-button"><a href="/codeDojo/u/course/editor">${buttonValue}</a></div>`;
        
        // var link = document.createElement("link");
        // link.setAttribute("rel", "stylesheet");
        // link.setAttribute("href", "/codeDojo/css/uicons-regular-straight.css");

        // this.shadowRoot.appendChild(link);
                        
        var div = document.createElement("div");
        div.className = "course-card";
        div.innerHTML = template;
        
        this.appendChild(div);

        // var style = document.createElement("style");
        // style.innerHTML = this.#style;

        // this.shadowRoot.appendChild(style);
    }

    // #style = `
    // .course-card {
    //     /* border: 1px solid green; */
    //     display: flex;
    //     align-items: center;
    //     padding: .5rem;
    //     margin: 1rem 0;
    // }
    
    // .course-card .icon {
    //     /* width: 3.125rem;
    //     height: 3.125rem; */
    //     background-color: var(--icon-bg);
    //     border-radius: 8px;
    //     font-size: 1.3rem;
    //     display: flex;
    //     justify-content: center;
    //     align-items: center;
    //     padding: 1rem;
    // }
    
    
    // .course-card .course-details {
    //     display: flex;
    //     flex-direction: column;
    //     /* width: 80%; */
    //     padding: .2rem .2rem .2rem 1rem; 
    //     line-height: 18px;
    // }
    
    // .course-card .course-details .title {
    //     font-size: 1.1rem;
    //     font-weight: 500;
    //     padding-bottom: .2rem;
    // }
    
    // .course-card .course-details .desc {
    //     color: #757575;
    //     font-weight: 400;
    // }
    
    // .course-card .course-button {
    //     margin-left: auto;
    //     margin-right: 1.3rem;
    //     padding: .5rem 1rem;
    //     font-size: 18px;
    //     font-weight: 600;
    //     border-radius: 8px;
    //     background-color: #262626;
    //     cursor: pointer;
    //     color: #f5f5f5;
    // }
    
    // .course-card .course-button:active {
    //     transform: scale(1.05);
    // }
    // `;
}

customElements.define("course-card", CourseCard);