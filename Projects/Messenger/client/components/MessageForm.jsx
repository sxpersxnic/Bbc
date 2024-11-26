import { useEffect, useRef, useState } from "react";
import styles from "./MessageForm.module.css";
import { create } from "@/lib/api/msg";

const defaultModel = {
    context: ""
}


export default function MessageForm({ socket }) {
    const [context, setContext] = useState(defaultModel);
    const sendBtn = useRef();
    const msgField = useRef();

    useEffect(() => {
        if (msgField && msgField.current) {
            msgField.current.addEventListener("keypress", function (event) {
                if (event.key == "Enter") {
                    event.preventDefault();
                    sendBtn.current.click();
                }
            });
        }
    }, [msgField])

    const validateMessage = () => {
        if (context.context.split("").length == 0 || context.context == undefined) {
            return "invalid";
        }

        return "valid";
    }

    const sendMessage = async () => {
        const validation = validateMessage();

        if (validation == "invalid") {
            alert("Message can't be empty");
            return;
        }

        await create(context);
        socket.emit("message", "new message");
        setContext({ context: "" });
        document.getElementById("message_field").focus();
    }

    const handleChange = (e) => {
        setContext({ context: e.target.value });
    }

    return (
        
        <div className={styles.message_input_container}>
            <input type="text" name="message_field" id="message_field" className={styles.messageField} ref={msgField} value={context.context} onChange={(e) => handleChange(e)} placeholder="New message..." maxLength={500}/>                
            <button onClick={sendMessage} ref={sendBtn} className={styles.sendBtn}>
                <span>Send</span>
            </button>
        </div>
        
    )
}