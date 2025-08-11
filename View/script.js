const url = "http://localhost:8080/task/users/2";
function hideloader(){
    document.getElementById("loading").style.display = "none";
}

function show(tasks){
    let tab = `<thead>
    <th scope="col">#</th>
    <th scope="col">Description</th>
    <th scope="col">Username</th>
    <th scope="col">User Id</th>
    </thead>`;

    for(let task of tasks){
        tab += `
            <tr>
                <th scope="row">${task.id}</th>
                <td>${task.description}</td>
                <td>${task.user.userName}</td>
                <td>${task.userId}</td>
            </tr>`
    }
    document.getElementById("tasks").innerHTML = tab;

}

async function getAPI(url){
    const response = await fetch(url, {
        method: "GET"});
    var data = await response.json();
    console.log(data);
    if(response){
        hideloader();
    }
    show(data);
}

getAPI(url);