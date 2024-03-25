constants.WELCOME_NAME_SPAN = _("#name");
var user_level;
// const content = document.querySelector(".content-wrapper");

function setName(name) {
	constants.WELCOME_NAME_SPAN.textContent = name;
}

function setDailyStreak(response) {
	response = JSON.parse(response);

	if (response.status) {
		_(".prog #daily-streak .value").textContent = response.data.streak;
	}
}

function setCourseTitle(response) {
	response = JSON.parse(response);

	if (response.title) {
		_(".progress .title h3").textContent = response.title;
		user_level = response.level;
	}
}

function renderCourseProgress(response) {
	response = JSON.parse(response);

	let totalCourse = response.length;
	let completed = 0;
	response.forEach(res => {
		if (res.isCompleted === "true") {
			completed++;
		}
	});

	let percentage = Math.ceil((completed / totalCourse) * 100);

	_(".bar-wrapper .occupied").style.width = percentage + "%";
	_(".completed-percentage").textContent = percentage + "%";
}

function loadLeaderboard(response) {
	response = JSON.parse(response);

	let template = `<div class="user-card br">
	<p class="user-rank">#position#</p>
	<div class="user-container">

		<div class="lb-user-profile">
			<div class="profile-pic"><img src="/codeDojo/img/profile.jpeg" alt="profile"></div>
			<div class="user-details">
				<p class="lb-name">#firstname#</p>
				<p class="lb-userid">@#username#</p>
			</div>
		</div>
		<div class="points">
			<div class="pts">
				<i class="fi fi-rs-flame"></i>
				<span>#shurikan#</span>
			</div>
			<div class="pts">
				<i class="fi fi-rs-quiz-alt"></i>
				<span>0</span>
			</div>
			<div class="pts">
				<i class="fi fi-rs-e-learning"></i>
				<span>0</span>
			</div>
		</div>
	</div>
</div>`;

	let html = "";
	response.forEach(res => {
		res = JSON.parse(res);
		html += template.replace("#position#", res.position)
				.replace("#firstname#", res.firstname)
				.replace("#username#", res.username)
				.replace("#shurikan#", res.shurikan);
	});

	// console.log(html);
	_(".leader-board").innerHTML = html;
}

function updateQuizProgress(response) {
	response = JSON.parse(response);

	let total = response.quizCount;
	let completed = response.completedCount;
	_(".quiz-progress #quiz-prog").textContent = Math.ceil((completed/total)*100) + " %";
}

doGet("/codeDojo/services/auth/get_name.dojo", setName);
doGet("/codeDojo/services/dq/get_user_streak.dojo", setDailyStreak);
doGet("/codeDojo/services/course/getCourse", setCourseTitle);
doGet("/codeDojo/services/course/getCourseMetaData", renderCourseProgress);
doGet("/codeDojo/services/user/getLeaderboard", loadLeaderboard);
doGet("/codeDojo/services/quiz/getQuizProgress.dojo", updateQuizProgress);

_(".course-progress-box button").addEventListener("click", function () {
	console.log("button clicked");
	if (user_level) 
		window.location.href = "/codeDojo/u/course/editor?level="+user_level;
});