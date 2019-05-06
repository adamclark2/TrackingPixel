camp=[];

function populateCampaigns(serverCamps){
    for(idx in serverCamps){
        camp[idx] = {name: serverCamps[idx], selected: false};
    }
    if(serverCamps.length > 0){
        camp[0].selected = true
        syncCampList()
        document.getElementById("table-content").hidden = false
        document.getElementById("camplist").hidden = false
        selectCamp(0)
    }else {
        alert("Create a campaign")
        document.location.href = "createEmail.html"
    }
}

function btnRefresh(){
    ec_loadCamps()
}

function selectCamp(idx){
    for(camp_idx in camp){
        camp[camp_idx].selected = false;
    }
    camp[idx].selected = true;
    document.getElementById("campaign_name").innerText = camp[idx].name;

    syncCampList()
    removeTableItems()
    getPixelsWithoutVisitsForCampaign(populateNotVisited, camp[idx].name)
    getVisitsForCampaign(populateTable, camp[idx].name)
    
}

function removeTableItems(){
    table = document.getElementById('table-content');
    while (table.hasChildNodes()) {
        table.removeChild(table.childNodes[0]);
    }
}

function populateNotVisited(pixels){
    table = document.getElementById('table-content');
    for(visit_idx in pixels){
        tr = document.createElement("tr");
        email_td = document.createElement("td");

        a = document.createElement("a")
        a.href = getPixelAddress(pixels[visit_idx])
        a.innerText = pixels[visit_idx].email;
        email_td.appendChild(a);

        hasvisited_td = document.createElement("td");
        hasvisited_td.innerText = "NO";

        date_td = document.createElement("td");
        date_td.innerText = ""

        OS_td = document.createElement("td");
        OS_td.innerText = ""

        browser_td = document.createElement("td");
        browser_td.innerText = ""

        loc_td = document.createElement("td");
        loc_td.innerText = ""

        isp_td = document.createElement("td");
        isp_td.innerText =  ""

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

function populateTable(visits){
    table = document.getElementById('table-content');
    for(visit_idx in visits){
        tr = document.createElement("tr");
        email_td = document.createElement("td");

        a = document.createElement("a")
        a.href = getPixelAddress(visits[visit_idx].pixel)
        a.innerText = visits[visit_idx].pixel.email;
        email_td.appendChild(a);

        hasvisited_td = document.createElement("td");
        hasvisited_td.innerText = "Yes";

        date_td = document.createElement("td");
        date_td.innerText = getDate(visits[visit_idx].timestamp);

        OS_td = document.createElement("td");
        OS_td.innerText = visits[visit_idx].metadata.OS

        browser_td = document.createElement("td");
        browser_td.innerText = visits[visit_idx].metadata.browser

        loc_td = document.createElement("td");
        loc_td.innerText = visits[visit_idx].metadata.location

        isp_td = document.createElement("td");
        isp_td.innerText =  visits[visit_idx].metadata.isp

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