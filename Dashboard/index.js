
function init_index() {
    isLoggedIn(indexIsLoggedIn)
}

function indexIsLoggedIn(isLoggedIn){
    if(isLoggedIn){
        document.getElementById("login-form").hidden = true
        document.getElementById("btn-logoff").hidden = false
    }else{
        document.getElementById("login-form").hidden = false
        document.getElementById("btn-logoff").hidden = true
    }
}

function indexDoLogin(){
    pass = document.getElementById("password").value
    uname = document.getElementById("username").value

    login(uname, pass, afterLoginResponse)
}

function afterLoginResponse(isLoggedIn, isIPLocked){
    document.getElementById("Alert_AuthFail").hidden = true
    document.getElementById("Alert_Locked").hidden = true
    document.getElementById("btn-logoff").hidden = true
    document.getElementById("login-form").hidden = false

    if(isIPLocked){
        document.getElementById("Alert_Locked").hidden = false
    }

    if(isLoggedIn){
        document.getElementById("btn-logoff").hidden = false
        document.getElementById("login-form").hidden = true
    }else{
        document.getElementById("Alert_AuthFail").hidden = false
        document.getElementById("login-form").hidden = false
    }
}