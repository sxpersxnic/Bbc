import {loadUserById, loadUserByUsername} from "./user";
import {useSession} from "./session";

const URL = "http://localhost:8080/api/v1/ws";


export async function loadChat(id) {
    const response = await fetch(`${URL}/chat/${id}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });

    if (!response.ok) {
        throw new Error(`An error occured while fetching Chat with id: ${id}`);
    }

    const data = await response.json();
    return data;
}

export async function loadChats() {
    const response = await fetch(`${URL}/chat`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    })

    if (!response.ok) {
        throw new Error(`An error occured while fetching Chats`);
    }

    const data = await response.json();
    return data;
}

export async function loadMessage(id) {
    const response = await fetch(`${URL}/messages/${id}`);

    if (!response.ok) {
        throw new Error(`An error occured while fetching Message with id: ${id}`);
    }

    const data = await response.json();
    return data;
}

export async function loadMessagesByChatId(id) {
    const response = await fetch(`${URL}/messages/${id}`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });

    if (!response.ok) {
        throw new Error(`An error occured while fetching Messages in chat with id: ${id}`);
    }

    const data = await response.json();
    return data;
}

export async function sendMessage(chatId, content) {
    const response = await fetch("http://localhost:8080/api/v1/ws/chat.sendmsg", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({
            "content": content,
            "chatId": chatId
        })
    })

    if (!response.ok) {
        throw new Error(`An error occured while posting a Message into chat with id: ${chatId}`)
    }

    const data = await response.json();
    console.log(data)
    return data;
}

export async function updateMessage(message, newContent) {
    const response = await fetch(`http://localhost:8080/api/v1/ws/chat.editmsg/${message.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({
            "content": newContent,
            "chatId": message.chatId
        })
    })

    if (!response.ok) {
        throw new Error(`An error occured while updating a Message in chat with id: ${message.chatId}`)
    }

    const data = await response.json();
    return data;
}

export async function deleteMessage(messageId) {
    const response = await fetch(`http://localhost:8080/api/v1/ws/chat.deletemsg/${messageId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    })

    if (!response.ok) {
        throw new Error(`An error occured while deleting a Message in chat with id: ${messageId}`)
    }
}

export async function createGroupChat(chatname, usernames, teamId = null) {
    console.log(usernames)
    const userIds = [];
    for (const username of usernames) {
        const user = await loadUserByUsername(username);
        if (!userIds.includes(user.id)) {
            userIds.push(user.id);
        }
    }

    const response = await fetch(`${URL}/chat.add`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({
            "name": chatname,
            "isGroup": true,
            "userIds": userIds,
            "teamId": teamId
        })
    })

    if (!(response.status === 201)) {
        throw new Error(`There was a problem creating a Group chat with name: ${chatname} and users: ${usernames}`)
    }
}

export async function createPrivateMessage(creator, username, showError) {

    let userId;
    let userIdAsArray;

    try {
        userId = (await loadUserByUsername(username)).id;
        userIdAsArray = [creator, userId];

        const response = await fetch(`${URL}/chat.add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify({
                "name": "placeholder   ",
                "isGroup": false,
                "userIds": userIdAsArray
            })
        })

        if (!(response.status === 201)) {
            throw new Error(`There was a problem creating a Private chat with user: ${username}`)
        }
    } catch (e) {
        showError();
    }
}