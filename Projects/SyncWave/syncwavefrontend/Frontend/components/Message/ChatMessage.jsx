import {useSession} from "../../lib/session";
import { Message } from "primereact/message";
import {useEffect, useState} from "react";
import {Dialog} from "primereact/dialog";
import { InputTextarea } from 'primereact/inputtextarea';
import {loadUserById} from "../../lib/user";
import {deleteMessage, updateMessage} from "../../lib/chat";


export default function ChatMessage({message, scrollToBottom}) {
    const { session } = useSession();
    const [own, setOwn] = useState(false);
    const [showOptions, setShowOptions] = useState(false);
    const [editing, setEditing] = useState(false);
    const [value, setValue] = useState("");
    const [author, setAuthor] = useState(null);

    useEffect(() => {
        if (author) {
            setOwn(author.id === session.user.id);
            scrollToBottom();
        }
    }, [author]);

    async function loadAuthor() {
        const author =  await loadUserById(message.senderId);
        setAuthor(author);
    }

    useEffect(() => {
        if (message) {
            loadAuthor();
        }
    }, [message]);

    return (
        <div className={`flex flex-column  mx-8 px-4 py-2 text-left justify-content-center align-items-${own ? "end" : "start"}`}>
            {author &&
                <div>
                    <Dialog
                        draggable={false}
                        resizable={false}
                        visible={editing}
                        onHide={() => {
                            setEditing(false);
                            setValue("");
                            /*TODO*/
                        }}
                    >
                        <div className="flex flex-column align-items-center">
                            <InputTextarea
                                autoResize
                                rows={1}
                                className="max-h-20rem my-2 p-2 mx-4 surface-hover overflow-y-auto"
                                value={value}
                                onChange={
                                    (e) => {
                                        setValue(e.target.value)
                                    }
                                }
                            />
                            <i
                                className=" pi pi-caret-right w-max mb-2 h-fit hover:text-primary text-2xl"
                                onClick={() => {
                                    updateMessage(message, value);
                                    setEditing(false);
                                }}
                            />
                        </div>
                    </Dialog>
                    <p className="m-0 text-xs">{author.username}</p>
                    <p className="m-0 text-xs">{message.time}</p>
                    <div className="flex">
                        {showOptions && own &&
                            <div
                                onMouseEnter={() => {setShowOptions(true)}}
                                onMouseLeave={() => {setShowOptions(false)}}
                                className="card flex flex-row align-items-center px-2 "
                            >
                                <div className="hover:text-primary px-1" onClick={() => {deleteMessage(message.id)}}>
                                    <i className="pi pi-trash "></i>
                                </div>
                                <div className="hover:text-primary px-1" onClick={() => {setEditing(!editing)}}>
                                    <i className="pi pi-pencil"></i>
                                </div>
                            </div>
                        }
                        <Message
                            onMouseEnter={() => {setShowOptions(true)}}
                            onMouseLeave={() => {setShowOptions(false)}}
                            severity="secondary"
                            text={message.content}
                            className={`card max-w-30rem shadow-2 ${own && "surface-200"}`}
                        />
                    </div>
                </div>
                }
        </div>

    )
}