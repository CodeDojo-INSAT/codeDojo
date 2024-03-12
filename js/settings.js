function displayUserDetails(obj) {
    obj = JSON.parse(obj);
    console.log(obj);
    if (obj.status === true) {
        _("#fname").placeholder = obj.fname;
        _("#lname").setAttribute("placeholder", obj.lname);
        _("#uname").setAttribute("placeholder", obj.uname);
        _("#email").setAttribute("placeholder", obj.email);
    }
}

doPost("/codeDojo/getUserAcc", null, displayUserDetails);

_(".acc").addEventListener("click", (e) => {e.classList.toggle("selected")});
_(".passwd").addEventListener("click", (e) => {e.classList.toggle("selected")});
_(".notify").addEventListener("click", (e) => {e.classList.toggle("selected")});
// getUserDetails();
