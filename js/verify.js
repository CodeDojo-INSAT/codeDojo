const verify_form = document.getElementById("verify-form");

verify_form.addEventListener("submit", function(e) {
    e.preventDefault();

    let code = verify_form.otp.value;
    let flag = false;

    fetch(`/codeDojo/services/auth/verify_otp.dojo?vc=${code}`)
    .then(response => {
        if (response.ok) {
            return response.text();
        }
    })
    .then(data => {
        if (data === "verified\n") {
            console.log("verified");
            alert("verified");
            window.location.href = "/codeDojo/dashboard";   
            
        }
        else {
            alert("Please login again.");
            window.location.href = "/codeDojo/auth/login";
        }
    });

    console.log(flag);

})