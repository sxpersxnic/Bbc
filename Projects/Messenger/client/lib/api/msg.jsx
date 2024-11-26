const URL = process.env.NEXT_PUBLIC_API_URL

export async function getAllMessages() {
    const response = await fetch(`${URL}/api/chat/messages`, {
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

export async function create({ context }) {
    const response = await fetch(`${URL}/api/chat/messages/create`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ context: context })
    })

    if (!response.ok) {
        throw new Error("An error ocurred while fetching")
    }

    const data = await response.json();
    return data
}