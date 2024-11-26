import {useEffect, useRef, useState} from "react";
import {loadMessage, loadMessagesByChatId} from "../../lib/chat";
import ChatMessage from "../Message/ChatMessage";
import {loadJsonUserByID, loadUserById} from "../../lib/user";
import styles from "../../styles/Message/message.module.css"

export default function MessageDisplay({chatId, newMessage, updatedMessage, deletedMessage}) {
    const [messages, setMessages] = useState(null);
    const [loaded, setLoaded] = useState(false);
    const messagesEnd = useRef(null);

    useEffect(() => {
        if (chatId) {
            load(chatId);
        }
    }, [chatId]);

    useEffect(() => {
        console.log(messages)
        if (messages) {
            scrollToBottom();
        }
    }, [messages]);

    useEffect(() => {
        if (newMessage != null && messages != null) {
            setMessages([...messages, newMessage]);
        }
    }, [newMessage]);

    useEffect(() => {
        console.log(updatedMessage)
        if (updatedMessage != null && messages != null) {
            let updatedMessages = [...messages];
            let updatedMessageIdx = messages.indexOf(messages.filter((message) => message.id === updatedMessage.id)[0]);
            updatedMessages[updatedMessageIdx] = updatedMessage;
            setMessages(updatedMessages);
        }
    }, [updatedMessage]);

    useEffect(() => {
        console.log(deletedMessage)
        if (deletedMessage != null && messages != null) {
            let deletedMessages = [...messages];
            let deletedMessageIdx = messages.indexOf(messages.filter((message) => message.id === deletedMessage.id)[0]);
            deletedMessages.splice(deletedMessageIdx, 1);
            setMessages(deletedMessages);
        }
    }, [deletedMessage]);



    async function load(chatId) {
        let msgs = await loadMessagesByChatId(chatId);
        setMessages(msgs);
        setLoaded(true);
    }


    const scrollToBottom = () => {
        messagesEnd.current?.scrollIntoView({ behavior: "instant" });
    }



    return (
        <div className="h-full w-full overflow-y-scroll border-round">
            {messages && messages.map((msg) => {
                return (
                    loaded && <ChatMessage message={msg} scrollToBottom={scrollToBottom} />
                )
            })}
            <div ref={messagesEnd}>
            </div>
        </div>
    )
}