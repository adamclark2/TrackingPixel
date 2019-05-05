
// serverRoot address without triling slash
// ex "http://localhost:8080"
serverRoot = "http://localhost:8080"

function isLoggedIn(onServerResponse){
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            onServerResponse(true)
        }else if (this.readyState == 4 && this.status >= 400 && this.status <= 499){
            onServerResponse(false)
        }
    };
    req.open("GET", serverRoot + "/auth/login", true);
    req.withCredentials = true
    req.send();
}

function requireLogin(isLoggedIn){
    if(!isLoggedIn){
        if(!window.location.href.includes("index.html")){
            window.location.href = ("index.html");
        }
    }
}

isLoggedIn(requireLogin)

function login(username, pass, onServerResponse){
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            onServerResponse(true, false)
        }else if (this.readyState == 4 && this.status >= 400 && this.status <= 499){
            onServerResponse(false, false)
        }
    };
    req.open("POST", serverRoot + "/auth/login", true);
    req.withCredentials = true
    req.setRequestHeader("username", username)
    req.setRequestHeader("password", pass)
    req.send();
}

function logout(onServerResponse){
    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            onServerResponse()
        }
    };
    req.open("POST", serverRoot + "/auth/logout", true);
    req.withCredentials = true
    req.send();
}