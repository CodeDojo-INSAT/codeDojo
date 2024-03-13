
if (!customElements.get("course-card")) {
    class CourseCard extends HTMLElement {
        constructor() {
            super();
        }
    
        #completedIcon = `<i class="fi fi-rs-check"></i>`;
        #lockedIcon = `<i class="fi fi-rs-lock"></i>`;
    
        connectedCallback() {
            var statusText = this.getAttribute("status");
            var icon;
            var buttonValue;
            if (statusText === "1") {
                icon = this.#completedIcon;
                buttonValue = "View";
            }
            else if (statusText === "-1") {
                icon = this.#lockedIcon;
                buttonValue = "Start";
            }
    
            var titleText = this.getAttribute("title");
            var descText = this.getAttribute("desc");
            var id = this.getAttribute("id");
    
            var template = `<div class="icon">
                                ${icon}
                            </div>
                            <div class="course-details">
                                <span class="title">${titleText}</span>
                                <span class="desc">${descText}</span>
                            </div>
                            <div class="course-button"><a href="/codeDojo/u/course/editor?level=${id}">${buttonValue}</a></div>`;
                            
            var div = document.createElement("div");
            div.className = "course-card";
            div.innerHTML = template;
            
            this.appendChild(div);
        }
    }

    customElements.define("course-card", CourseCard);
}

function listCourses(response) {
    response = JSON.parse(response);
    console.log(response);
    renderProgressBar(response);

    var template = `<course-card status="#status#" title="#title#" desc="#desc#" id="#id#"></course-card>`;
    
    var html = "";
    var len = response.length;
    for (let i=0; i<len; i++) {
        let res = response[i];
        html += template.replace("#status#", res.isCompleted === "true" ? 1 : -1)
        .replace("#title#", res.Title)
        .replace("#desc#", res.description)
        .replace("#id#", res.LevelID);
    }
    _(".course-list-wrapper").innerHTML = html;
}

function renderProgressBar(response) {
    let totalCourse = response.length;
    let completed = 0;
    response.forEach(res => {
        if (res.isCompleted === "true") {
            completed++;
        }
    });

    _(".completed-course").textContent = completed;
    _(".total-course").textContent = totalCourse;

    let percentage = Math.ceil((completed / totalCourse)*100);

    _(".progress-bar .bar").style.width = percentage + "%";
    _(".pb-legend .percentage").textContent = percentage + "%";
}

function setCurrentLessonTitle(response) {
    response = JSON.parse(response);

    _(".pb-legend .lesson-name").textContent = response["title"];
}

doGet("/codeDojo/services/course/getCourse", setCurrentLessonTitle);
doGet("/codeDojo/services/course/getCourseMetaData", listCourses);