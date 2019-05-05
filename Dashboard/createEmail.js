emails=[];
emailCount=0;

function addEmail() {
    emailField = document.getElementById("email").value
    emails[emailCount] = emailField
    emailCount++

    syncEmailField()
}

function removeEmail(index) {
    emails.splice(index, 1);
    emailCount--
    syncEmailField()
}

function syncEmailField() {
    emailList = document.getElementById("email-list")
    while (emailList.hasChildNodes()) {
        emailList.removeChild(emailList.childNodes[0]);
    }

    for(email in emails){
        div = document.createElement("div")
        btn = document.createElement("div")
        span = document.createElement("span")

        btn.className += "btn btn-danger mr-5"
        btn.innerText = "X"

        const idx = email;
        btn.addEventListener('click', function(){
            removeEmail(idx)
        })
        span.innerText = "" + emails[email]
        div.className = "alert alert-success"

        div.appendChild(btn)
        div.appendChild(span)
        emailList.appendChild(div)
    }
}

function btnSend(){
    if(emailCount <= 0){
        alert("Add at least one email")
    }
}