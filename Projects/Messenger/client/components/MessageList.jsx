import { getAllMessages } from "@/lib/api/msg";
import Message from "./Message";
import { useEffect, useState } from "react";

export default function MessageList({ socket }) {
    const [messages, setMessages] = useState([]);

    const loadMessages = async () => {
        const response = await getAllMessages();
        setMessages(response);
    }

    useEffect(() => {
        loadMessages();
    }, [])

    socket.on("response", (arg) => {
        console.debug(arg);
        loadMessages();
    });

    return (
        <div>
            {
                messages.map((msg, i) => {
                    return (
                        <Message context={msg.context} key={i} />
                    )
                })
            }
        </div>
    )
}