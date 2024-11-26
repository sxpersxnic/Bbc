import { URL } from 'url';

const apiUrl = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:9001/api/posts';

export async function getAllPosts() {
    console.log(apiUrl)

    const response = await fetch(`${apiUrl}/posts`);

    if (!response.ok) {
        throw new Error("An error occurred while fetching posts");
    }

    return await response.json();
}

export async function getPostById(id) {
    const response = await fetch(`${apiUrl}/posts/${id}`);

    if (!response.ok) {
        throw new Error("An error occurred while fetching the post");
    }

    return await response.json();
}

async function handlePostRequest(url, method, body, token) {
    const requestOptions = {
        method,
        headers: {
            "content-type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(body)
    };

    const response = await fetch(`${apiUrl}${url}`, requestOptions);

    if (!response.ok) {
        throw new Error(`An error occurred while ${method.toLowerCase()} the post`);
    }

    return await response.json();
}

export function createPost(post, token) {
    return handlePostRequest("/posts", "POST", post, token);
}

export function updatePost(post, token) {
    return handlePostRequest(`/posts/${post.id}`, "PUT", post, token);
}

export async function deletePost(id, token) {
    const response = await fetch(`${apiUrl}/posts/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

    if (!response.ok) {
        throw new Error("An error occurred while deleting the post");
    }
}
