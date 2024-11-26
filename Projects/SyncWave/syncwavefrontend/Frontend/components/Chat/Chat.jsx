import {useEffect, useRef, useState} from "react";
import {loadChat} from "../../lib/chat";
import ChatHeader from "./ChatHeader";
import MessageDisplay from "./MessageDisplay";
import ChatBox from "./ChatBox";

export default function Chat({ id, newMessages, updatedMessages, deletedMessages, header=true, chatbox=true }) {
    const [chat, setChat] = useState();
    const display = useRef(null);

    useEffect(() => {
        if (id) {
            console.log(id)
            load(id);
        }
    }, [id]);


    async function load(id) {
        let c = await loadChat(id);
        setChat(c);
    }

    useEffect(() => {
        console.log("messages created!!");
    }, [newMessages]);

    useEffect(() => {
        console.log("messages updated!!");
    }, [updatedMessages]);

    useEffect(() => {
        console.log("messages deleted!!");
    }, [deletedMessages]);



    return (
        <div className="flex h-full flex-column justify-content-between">
            {chat && header && <ChatHeader chat={chat}/>}
            {chat &&
                <MessageDisplay
                    chatId={id}
                    newMessage={newMessages[id]}
                    updatedMessage={updatedMessages[id]}
                    deletedMessage={deletedMessages[id]}
                />}
            {chat && chatbox && <ChatBox ref={display} id={id}/>}
        </div>
    )
}