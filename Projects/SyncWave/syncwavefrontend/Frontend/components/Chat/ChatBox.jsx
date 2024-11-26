import {useEffect, useState} from "react";
import styles from "../../styles/Chat/chatbox.module.css"
import {sendMessage} from "../../lib/chat";
import {useSession} from "../../lib/session";
import {Toolbar} from "primereact/toolbar";
import {Button} from "primereact/button";
import {InputTextarea} from "primereact/inputtextarea";
import {useEventListener} from "primereact/hooks";
import {Card} from "primereact/card";

export default function ChatBox({id}) {
    const [value, setValue] = useState("");
    const {session} = useSession();
    const [shiftPressed, setShiftPressed] = useState(false);
    const [enterPressed, setEnterPressed] = useState(false);
    const [bindKeyDown, unbindKeyDown] = useEventListener({
        type: 'keydown',
        listener: (e) => {
            console.log(e.key)
            if (e.key == "Shift") {
                setShiftPressed(true);
            } else if (e.key == "Enter") {
                setEnterPressed(true);
                if (!shiftPressed) {
                    send();
                }
            }
        }
    });
    const [bindKeyUp, unbindKeyUp] = useEventListener({
        type: 'keyup',
        listener: (e) => {
            if (e.key == "Shift") {
                setShiftPressed(false);
            } else if (e.key == "Enter") {
                setEnterPressed(false);
            }
        }
    });

    useEffect(() => {
        bindKeyDown();
        bindKeyUp();

        return () => {
            unbindKeyDown();
            unbindKeyUp();
        };
    }, [bindKeyDown, bindKeyUp, unbindKeyDown, unbindKeyUp]);

    function isWhitespaceOrEmpty(str) {
        return /^\s*$/.test(str);
    }

    function send() {
        if (!isWhitespaceOrEmpty(value)) {
            const chatId = id;
            sendMessage(chatId, value);
            setValue("");
        }

    }

    return (
        <div className="card border-noround-bottom p-2 border-x-none">
            <div
                className="w-full flex flex-row justify-content-between align-items-center px-8">
                <InputTextarea
                    autoResize
                    rows={1}
                    className="w-full max-h-20rem my-2 p-2 mx-4 surface-hover overflow-y-auto"
                    value={value}
                    onChange={
                        (e) => {
                            if (enterPressed) {
                                if (shiftPressed) {
                                    setValue(e.target.value)
                                }
                            } else {
                                setValue(e.target.value)
                            }
                        }
                    }
                />
                <i
                    className=" pi pi-caret-right w-max mb-2 h-fit hover:text-primary text-2xl"
                    onClick={send}
                />
            </div>
        </div>

    )
}