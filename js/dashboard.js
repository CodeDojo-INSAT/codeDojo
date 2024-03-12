constants.WELCOME_NAME_SPAN = _("#name");
// const content = document.querySelector(".content-wrapper");

function setName(name) {
    constants.WELCOME_NAME_SPAN.textContent = name;
}

function setDailyStreak(response) {
  response = JSON.parse(response);

  if (response.status) {
    _(".prog #daily-streak").textContent = response.data.streak;
  }
}

function setCourseTitle(response) {
  response = JSON.parse(response);

  if (response.title) {
    _(".progress .title h3").textContent = response.title;
  }
}

doGet("/codeDojo/services/auth/get_name.dojo", setName);
doGet("/codeDojo/services/dq/get_user_streak.dojo", setDailyStreak);
doGet("/codeDojo/services/course/getCourse", setCourseTitle);


_(".course-progress-box button").addEventListener("click", function() {
  console.log("button clicked");
  window.location.href = "/codeDojo/u/course/editor";
})



// const canvas = document.getElementById('flameCanvas');
//   const ctx = canvas.getContext('2d');

//   // Set canvas size to fit the div
//   canvas.width = 264;
//   canvas.height = 214;

//   let particles = [];
//   const numberOfParticles = 200;

//   class Particle {
//     constructor(){
//       // Adjust the spread and position of particles for the new canvas size
//       this.x = canvas.width / 2 + (Math.random() * canvas.width * 0.75 - canvas.width * 0.375);
//       this.y = canvas.height;
//       this.size = Math.random() * 5 + 1;
//       this.speedY = Math.random() * -3 - 1;
//       this.speedX = Math.random() - 0.5;
//       // this.color = 'rgba(227, ' + (Math.random() * 60 + 66) + ', ' + (Math.random() * 20 + 52) + ', 1)';
//       this.color = `rgba(${Math.floor(Math.random() * 156)}, ${Math.floor(192 + Math.random() * 63)}, ${255}, ${Math.random()})`
//     }
//     update(){
//       this.y += this.speedY;
//       this.x += this.speedX;
//       if (this.size > 0.1) this.size -= 0.05;
//     }
//     draw(){
//       ctx.beginPath();
//       ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
//       ctx.fillStyle = this.color;
//       ctx.fill();
//     }
//   }

//   function init() {
//     for (let i = 0; i < numberOfParticles; i++) {
//       particles.push(new Particle());
//     }
//   }

//   function animate() {
//     ctx.clearRect(0, 0, canvas.width, canvas.height);
//     for (let i = 0; i < particles.length; i++) {
//       particles[i].update();
//       particles[i].draw();

//       if (particles[i].size <= 0.1) {
//         particles.splice(i, 1);
//         i--;
//         particles.push(new Particle());
//       }
//     }
//     requestAnimationFrame(animate);
//   }

//   init();
//   animate();