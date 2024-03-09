function getUserDetails() {
    var url = "/codeDojo/getUserAcc";
    var xhr = new XMLHttpRequest();

    xhr.onload = ()=> {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status == 200) {
                displayUserDetails(JSON.parse(this.responseText));
            }
        }
    }

    xhr.open("POST", url, true);
    xhr.send();
}

function displayUserDetails(obj) {
    if (obj.status === true) {
        _("#fname").placeholder = obj.fname;
        _("#lname").setAttribute("placeholder", obj.lname);
        _("#uname").setAttribute("placeholder", obj.uname);
        _("#email").setAttribute("placeholder", obj.email);
    }
}


_(".acc").addEventListener("click", (e) => {e.classList.toggle("selected")});
_(".passwd").addEventListener("click", (e) => {e.classList.toggle("selected")});
_(".notify").addEventListener("click", (e) => {e.classList.toggle("selected")});
getUserDetails();
