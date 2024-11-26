const URL = "http://localhost:8080/api/v1/auth";

interface LoginRequest {
    principal: string;
    password: string;
}

interface RegisterRequest {
    email: string;
    password: string;
    username: string;
    confirmPassword: string;
}

interface LoginResponse {
    token: string;
    id: number;
    username: string;
    email: string;
}

interface RegisterResponse {
    token: string;
    user: {
        id: number;
        name: string;
        email: string;
    }
}

// @ts-ignore
export async function login({principal, password}: LoginRequest): Promise<LoginResponse> {
    const response: Response = await fetch(`${URL}/signin`, {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({principal, password})
    });

    if (!response.ok) {
        throw response
    }
    return await response.json();
}

// @ts-ignore
export async function register({
                                   email,
                                   password,
                                   username,
                                   confirmPassword
                               }: RegisterRequest): Promise<RegisterResponse> {
    const response: Response = await fetch(`${URL}/signup`, {
        method: "POST",
        headers: {
            "content-type": "application/json",
        },
        body: JSON.stringify({email, password, username, confirmPassword})
    });

    if (!response.ok) {
        throw response;
    }

    const data = await response.json();
    return data;
}