const sidebar = document.querySelector(".sidebar");
const sidebarBtn = document.querySelectorAll(".sidebar a");
const sidebar_start = document.querySelector(".hover-effect");

// sidebarBtn.onclick = () => {
//     sidebarBtn.classList.toggle("active");
// };

sidebar_start.addEventListener("mouseenter", function(event) {
    sidebar.classList.add("hovered");
});

// sidebar.addEventListener("mouseleave", function(event) {
//     sidebar.classList.remove("hovered");
// });

