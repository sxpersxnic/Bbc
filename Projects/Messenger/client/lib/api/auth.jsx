const URL = process.env.NEXT_PUBLIC_API_URL

// get an array of all user objects
export async function getAllUsers() {
    const response = await fetch(`${URL}/api/users`, {
        method: 'GET',
        headers: {
        },
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json();
    return data

}

// get the user object with the given id
export async function getUserById(id) {
    const response = await fetch(`${URL}/api/users/${id}`, {
        method: 'GET',
        headers: {
        },
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json();
    return data

}

// create and save new user in database
export async function register({ username, surname, firstname, email, password }) {
    const response = await fetch(`${URL}/api/register`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, surname, firstname, email, password })
    })

    if (response.status == 409) {
        return "existing"
    }

    if (!response.ok) {
        throw new Error("An error ocurred while fetching")
    }
    
    const data = await response.json();
    return data

}

// login
export async function login({ username, email, password }) {
    const response = await fetch(`${URL}/api/login`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, email, password })
    })

    if (response.status == 400) {
        return "invalid";
    }

    if (!response.ok) {
        throw new Error("An error ocurred while fetching")
    }

    const data = await response.json();
    return data;

}

export async function deleteUser(id) {
    const response = await fetch(`${URL}/api/users/delete/${id}`, {
        method: 'DELETE',
        headers: {

        },
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json();
    return data
}