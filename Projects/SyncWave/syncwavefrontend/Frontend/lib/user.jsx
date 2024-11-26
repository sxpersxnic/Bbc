import { useSession } from "./session";

const URL = "http://localhost:8080/api/v1";
const JSONURL = "http://localhost:3001"


const loadToken = async() => {
    const token = await window.localStorage.getItem("token")
    if(token) return token
}
// JSON SERVER
export async function loadJsonUserByID(id) {
    const response = await fetch(`${JSONURL}/users/${id}`);

    if (!response.ok) {
        throw new Error(`An error occured while fetching User with id: ${id}`);
    }

    let data = await response.json();
    return data;
}
// BACKEND

export async function loadAllUser() {
    const response = await fetch(`${URL}/users`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${await loadToken()}`
        }
    });
    
    if (!response.ok) {
        throw new Error(`An error occured while fetching Users}`);
    }

    let data = await response.json();
    return data;
}

export async function loadUserById(id){
    const response = await fetch(`${URL}/users/${id}`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${localStorage.getItem("token")}`
        }
    })

    if (!response.ok){
        throw new Error(`An error occured while fetching User: ${id}`)
    }

    let data = await response.json();
    return data;
}

export async function loadUserByUsername(username) {
    const response = await fetch(`${URL}/users/name/${username}`, {
        method: 'GET',
        headers: {
            'Authorization' : `Bearer ${localStorage.getItem("token")}`
        }
    })

    if (!response.ok) {
        throw new Error(`An error occured while fetching User by username: ${username}`)
    }

    let data = await response.json();
    return data;
}