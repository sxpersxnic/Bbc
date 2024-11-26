import EHttpStatus from './EHttpStatus';

const URL = process.env.SPRING_API_URL;

interface User {
    username: string;
    email: string;
    password: string;
}

export async function getAllUsers(): Promise<User[]> {
    const response = await fetch(`${URL}/users`, {
        method: 'GET',
        headers: {},
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json();
    return data;
};

export async function getUserById(id: string): Promise<User> {
    const response = await fetch(`${URL}/users/${id}`, {
        method: 'GET',
        headers: {},
    });

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json();
    return data;
};

export async function signUp(user: { username: string; email: string; password: string; }): Promise<User | EHttpStatus.CONFLICT> {
    const response = await fetch(`${URL}/auth/signup`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    });

    if (response.status == 409) {
        return EHttpStatus.CONFLICT;
    }

    if (!response.ok) {
        throw new Error("An error occured while fetching");
    }

    const data = await response.json();
    return data;
}

export async function login(credentials: {
    username?: string;
    email?: string;
    password: string;
}) {
    const response = await fetch(`${URL}/auth/signin`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(credentials),
    });

    if (response.status == 400) {
        return EHttpStatus.BAD_REQUEST;
    }

    if (!response.ok) {
        throw new Error("An error occured while fetching");
    }

    const data = await response.json();
    return data;
}

export async function deleteUser(id: string): Promise<User> {
    const response = await fetch(`${URL}/users/delete/${id}`, {
        method: 'DELETE',
        headers: {},
    });

    if (!response.ok) {
        throw new Error("An error occured while fetching");
    }

    const data = await response.json();
    return data;
}