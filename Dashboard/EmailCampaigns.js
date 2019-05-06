camp=[];

function populateCampaigns(serverCamps){
    for(idx in serverCamps){
        camp[idx] = {name: serverCamps[idx], selected: false};
    }
    if(serverCamps.length > 0){
        camp[0].selected = true
        syncCampList()
        selectCamp(0)
    }
}

function selectCamp(idx){
    for(camp_idx in camp){
        camp[camp_idx].selected = false;
    }
    camp[idx].selected = true;
    document.getElementById("campaign_name").innerText = camp[idx].name;

    syncCampList()
    populateTable()
}

function populateTable(){
    table = document.getElementById('table-content');
    while (table.hasChildNodes()) {
        table.removeChild(table.childNodes[0]);
    }

    // Debug
    visits = [
        {
            "headers": {
                "cookie": "HasVisited=True; JSESSIONID=A7527E59F04629EF1234E56F80AB30AC",
                "postman-token": "bfd7f88b-4743-47a7-9fc4-66eda3a1b7d1",
                "host": "localhost:8080",
                "connection": "keep-alive",
                "cache-control": "no-cache",
                "accept-encoding": "gzip, deflate",
                "user-agent": "PostmanRuntime/7.11.0",
                "accept": "*/*"
            },
            "cookies": {
                "HasVisited": "True",
                "JSESSIONID": "A7527E59F04629EF1234E56F80AB30AC"
            },
            "timestamp": 1557104404136,
            "pixel": {
                "id": 13,
                "email": "adam.clark2@maine.edu",
                "campaign": "aaaa"
            }
        },
        {
            "headers": {
                "cookie": "HasVisited=True; JSESSIONID=A7527E59F04629EF1234E56F80AB30AC",
                "postman-token": "bfd7f88b-4743-47a7-9fc4-66eda3a1b7d1",
                "host": "localhost:8080",
                "connection": "keep-alive",
                "cache-control": "no-cache",
                "accept-encoding": "gzip, deflate",
                "user-agent": "PostmanRuntime/7.11.0",
                "accept": "*/*"
            },
            "cookies": {
                "HasVisited": "True",
                "JSESSIONID": "A7527E59F04629EF1234E56F80AB30AC"
            },
            "timestamp": 1557104404136,
            "pixel": {
                "id": 13,
                "email": "adam.clark2@maine.edu",
                "campaign": "aaaa"
            }
        },
        {
            "headers": {
                "cookie": "HasVisited=True; JSESSIONID=A7527E59F04629EF1234E56F80AB30AC",
                "postman-token": "bfd7f88b-4743-47a7-9fc4-66eda3a1b7d1",
                "host": "localhost:8080",
                "connection": "keep-alive",
                "cache-control": "no-cache",
                "accept-encoding": "gzip, deflate",
                "user-agent": "PostmanRuntime/7.11.0",
                "accept": "*/*"
            },
            "cookies": {
                "HasVisited": "True",
                "JSESSIONID": "A7527E59F04629EF1234E56F80AB30AC"
            },
            "timestamp": 1557107506865,
            "pixel": {
                "id": 13,
                "email": "adam.clark2@maine.edu",
                "campaign": "aaaa"
            }
        }
    ];

    for(visit_idx in visits){
        tr = document.createElement("tr");
        email_td = document.createElement("td");
        email_td.innerText = visits[visit_idx].pixel.email;

        hasvisited_td = document.createElement("td");
        hasvisited_td.innerText = "Yes";

        date_td = document.createElement("td");
        date_td.innerText = getDate(visits[visit_idx].timestamp);

        OS_td = document.createElement("td");
        OS_td.innerText = "Unknown OS"

        browser_td = document.createElement("td");
        browser_td.innerText = "Unknown Browser"

        loc_td = document.createElement("td");
        loc_td.innerText = "Unknown Location"

        isp_td = document.createElement("td");
        isp_td.innerText =  "Unknown ISP"

        tr.appendChild(email_td)
        tr.appendChild(hasvisited_td)
        tr.appendChild(date_td)
        tr.appendChild(OS_td)
        tr.appendChild(browser_td)
        tr.appendChild(loc_td)
        tr.appendChild(isp_td)

        table.appendChild(tr)
    }
}

function getDate(unixTimestapMilis){
    var months_arr = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    date = new Date(unixTimestapMilis)

    ret = {
        year: date.getFullYear(),
        month: months_arr[date.getMonth()],
        day: date.getDate(),

        hour: date.getHours(),
        minutes: ("0" + date.getMinutes()).substr(-2)
    }

    return ret.month + " " + ret.day + " " + ret.year + " -- " + ret.hour + ":" + ret.minutes;
}



function syncCampList(){
    campList = document.getElementById('camplist');
    while (campList.hasChildNodes()) {
        campList.removeChild(campList.childNodes[0]);
    }

    for(idx in camp){
        div = document.createElement("div");
        div.className += " btn col-12 "
        if(camp[idx].selected){
            div.className += " btn-dark"
            div.innerText = camp[idx].name;
        }else{
            div.className += " btn-secondary"
            div.innerText = camp[idx].name;
        }

        const idx_c = idx;
        div.addEventListener('click', function(){
            selectCamp(idx_c)
        })

        campList.appendChild(div)
    }
}

function ec_loadCamps(){
    getAllCampaigns(populateCampaigns);
}