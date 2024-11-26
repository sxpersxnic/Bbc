const URL = "http://localhost:8080/api/v1";

const loadToken = async() => {
    const token = await window.localStorage.getItem("token")
    if(token) return token
}

export async function createNewTeam(name, initials, description) {
    const response = await fetch(`${URL}/team`, {
        method: 'POST',
        headers: {
            'Authorization' : `Bearer ${await loadToken()}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name, initials, description})
    });

    if (!response.ok) {
        throw new Error(`An error occured while fetching current Teams}`);
    }

    let data = await response.json();
    return data;
}

export async function addUsersToTeam(teamId, userIds) {
    const requestBody = JSON.stringify({ teamId, userIds });
    console.log('Request Body:', requestBody);
    const response = await fetch(`${URL}/team/teams/users/add`, {
        method: 'PUT',
        headers: {
            'Accept':'application/json',
            'Authorization' : `Bearer ${await loadToken()}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({teamId, userIds})
    });
    console.log(response)
    if (!response.ok) {
        throw new Error(`An error occured while adding Users}`);
    }

    let data = await response.json();
    return data;
}


export async function loadAllTeamsOfUserId(id) {
    const response = await fetch(`${URL}/team/user/${id}`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${await loadToken()}`
        }
    });

    if (!response.ok) {
        throw new Error(`An error occured while fetching Team by userId`);
    }

    let data = await response.json();
    return data;
}

export async function loadTeamById(id) {
    const response = await fetch(`${URL}/team/${id}`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${await loadToken()}`
        }
    });

    if (!response.ok) {
        throw new Error(`An error occured while fetching current Teams}`);
    }

    let data = await response.json();
    return data;
}

export async function loadTeamByUserId(id) {
    const response = await fetch(`${URL}/team/user/${id}`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${await loadToken()}`
        }
    });

    if (!response.ok) {
        throw new Error(`An error occured while fetching current Teams}`);
    }

    let data = await response.json();
    return data;
}