const sidebar = document.querySelectorAll(".sidebar li");
// const sidebarBtn = document.querySelectorAll(".sidebar a");
// const sidebar_start = document.querySelector(".hover-effect");

sidebar.forEach(n => {
    n.addEventListener("click", function (e) {
        // console.log(e.target)
        document.querySelectorAll(".sidebar .active").forEach(e => {
            e.classList.remove("active");
        });
        if (e.target.href) {
            e.target.classList.toggle("active");
        }
    });
});

// sidebar_start.addEventListener("mouseenter", function(event) {
//     sidebar.classList.add("hovered");
// });

// sidebar.addEventListener("mouseleave", function(event) {
//     sidebar.classList.remove("hovered");
// });

