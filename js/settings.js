function getUserDetails() {
    var url = "/codeDojo/getUserAcc";
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status == 200) {
                console.log(xhr.responseText);
                displayUserDetails(JSON.parse(xhr.responseText));
                user = (JSON.parse(xhr.responseText)).uname;
                console.log(user);
            }
        }
    };
    xhr.open("POST", url, true);
    xhr.send();

    // loadImage("/codeDojo/img/userprofiles/uvchan.png");
}


function displayUserDetails(obj) {
    if (obj.status === true) {
        _("#fname").setAttribute("placeholder", obj.fname);
        _("#lname").setAttribute("placeholder", obj.lname);
        _("#uname").setAttribute("placeholder", obj.uname);
        _("#email").setAttribute("placeholder", obj.email);
    }
}

function settingToggles() {

    let elements = document.querySelectorAll(".acc, .notify, .passwd");
    let innerelements = document.querySelectorAll(".account ,.password")
    elements.forEach(element => {
        element.addEventListener("click", (e) => {

            innerelements.forEach(e => {
                e.classList.remove("showTop");
            })
            elements.forEach(el => {
                el.classList.remove("selected");
            });

            document.querySelector(getCorresClassname(element.classList[0])).classList.add("showTop");
            element.classList.add("selected");
            console.log("working");
        });
    });

    function getCorresClassname(navclass) {
        if (navclass == "acc") {
            return ".account"
        }
        else if (navclass == "passwd") {
            return ".password"
        }
    }

}

function openFileInput() {
    document.getElementById('fileInput').click();
}


function updateImage(imgdata) {
    var url = "/codeDojo/services/UpdateProfilePhoto";
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status == 200) {
                console.log("Profile image updated successfully.");
            } else {
             
                console.error("Error updating profile image:", xhr.statusText);
            }
        }
    };
    
    xhr.onerror = function () {
        // Handle network errors
        console.error("Network error occurred while updating profile image.");
    };

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("{content:'"+imgdata+"'}");
}




document.getElementById('fileInput').addEventListener('change', function () {
    const file = this.files[0];
    console.log(file)
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {

            const imageData = e.target.result.split(",")[1];
            console.log("Image uploaded:", imageData);
            updateImage(imageData);
            setTimeout(()=>loadImage(`/codeDojo/img/userprofiles/uvchan.png`),1000)
            
        };
        reader.readAsDataURL(file);
    }
});

settingToggles()
getUserDetails()