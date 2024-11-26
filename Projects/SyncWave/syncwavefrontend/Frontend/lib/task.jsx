const URL = "http://localhost:8080/api/v1/taskuser";
const TASKURL = "http://localhost:8080/api/v1/"

const loadToken = async() => {
    const token = await window.localStorage.getItem("token")
    console.log(token)
    if(token) return token
}
export async function addNewTaskAndUsers(task) {
    const { title, description, dueAt, userIds } = task;

    const requestBody = JSON.stringify({ title, description, dueAt, userIds });
    console.log('Request Body:', requestBody);

    const response = await fetch(`${TASKURL}task`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Authorization': `Bearer ${await loadToken()}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, description, dueAt, userIds })
    });

    console.log('Response:', response);

    if (!response.ok) {
        throw new Error('An error occurred while adding Task and Users');
    }

    let data = await response.json();
    return data;
}


export async function loadAllTaskByUser() {
    const response = await fetch(`${URL}/taskuserId`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${await loadToken()}`
        }
    });
    
    if (!response.ok) {
        throw new Error(`An error occured while fetching current Tasks}`);
    }

    let data = await response.json();
    return data;
}
export async function patchTaskStatus(taskStatus, id) {
    const response = await fetch(`${URL}/update/${id}`, {
        method: 'PATCH',
        headers: {
            'Accept':'application/json',
            'Authorization' : `Bearer ${await loadToken()}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({taskStatus})
    });

    if (!response.ok) {
        throw new Error(`An error occured while fetching current Tasks}`);
    }

    let data = await response.json();
    return data;
}


export async function loadDoneTasksById(id){
    const response = await fetch(`${URL}/tasks/${id}`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${await loadToken()}`
        }
    })

    if (!response.ok){
        throw new Error(`An error occured while fetching Tasks: ${id}`)
    }

    let data = await response.json();
    return data;
}