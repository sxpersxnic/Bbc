import {useEffect, useRef, useState} from "react";
import Chat from "../../../components/Chat/Chat";
import {Button} from "primereact/button";
import {Dialog} from "primereact/dialog";
import {Dropdown} from "primereact/dropdown";
import {InputTextarea} from 'primereact/inputtextarea';
import {Toast} from 'primereact/toast';
import {Chips} from 'primereact/chips'
import {createGroupChat, createPrivateMessage, loadChats} from "../../../lib/chat";
import {useSession} from "../../../lib/session";
import {loadAllUser, loadUserByUsername} from "../../../lib/user";
import {MultiSelect} from "primereact/multiselect";

export default function ChatDefaultPage({client}) {
    const toast = useRef(null);
    const showError = () => {
        toast.current.show({severity: 'error', summary: 'Error', detail: 'User doesnt exist.', life: 3000});
    }
    const {session} = useSession();
    const [currentChatId, setCurrentChatId] = useState(-1);
    const [chatNameVal, setChatNameVal] = useState("");
    const [usernames, setUserNamesValue] = useState([]);
    const [usernamesAreValid, setUserNamesAreValid] = useState([]);
    const [creating, setCreating] = useState(false);
    const [chats, setChats] = useState(null);
    const [newMessages, setNewMessages] = useState({});
    const [updatedMessages, setUpdatedMessages] = useState({});
    const [deletedMessages, setDeletedMessages] = useState({});
    const [privateChatUserName, setPrivateChatUserName] = useState("");
    const [availableUsers, setAvailableUsers] = useState([]);


    useEffect(() => {
        load();
    }, []);

    async function load() {
        let chats = await loadChats();
        setChats(chats);
        if (chats.length > 0) {
            setCurrentChatId(chats[0].id)
            subscribe(chats)
        }

        let aUsers = await loadAllUser();
        let aUserNames = aUsers.map((user) => user.username).filter((username) => username !== session.user.username);
        setAvailableUsers([...aUserNames]);

    }


    const subscribe = (chats) => {
        chats.forEach((chat) => {
            console.log(chat)
            client.subscribe(`/chat/${chat.id}/queue`, (message) => {
                let newNewMessages = {...newMessages};
                newNewMessages[chat.id] = JSON.parse(message.body);
                setNewMessages(newNewMessages);
                moveToFront(chat);
            })
            client.subscribe(`/chat/${chat.id}/queue/update`, (message) => {
                let newUpdatedMessage = {...updatedMessages};
                newUpdatedMessage[chat.id] = JSON.parse(message.body);
                setUpdatedMessages(newUpdatedMessage);
            })
            client.subscribe(`/chat/${chat.id}/queue/delete`, (message) => {
                let newDeletedMessages = {...deletedMessages};
                newDeletedMessages[chat.id] = JSON.parse(message.body);
                setDeletedMessages(newDeletedMessages);
            })
        })
    }

    useEffect(() => {
        if (usernames.length > 0) {
            usernames.forEach((username, index) => {
                isUserNameValid(username, index);
            })
        }
    }, [usernames]);

    async function isUserNameValid(username, index) {
        try {
            await loadUserByUsername(username);
        } catch (e) {
            let validUserNames = [...usernames];
            validUserNames.splice(index, 1);
            setUserNamesValue(validUserNames);
        }
    }

    function moveToFront(chat) {
        setChats((prevChats) => {
            const filteredChats = prevChats.filter((c) => c.id !== chat.id);
            return [chat, ...filteredChats]
        })
    }
    return (
        <div className="flex w-full h-full overflow-hidden">
            <Dialog
                draggable={false}
                resizable={false}
                className="w-6 text-center"
                visible={creating}
                onHide={() => {
                    setCreating(false);
                    setChatNameVal("");
                    setUserNamesValue([]);
                }}
            >
                <h1>Create new Groupchat</h1>
                <p>Chat-name</p>
                <InputTextarea
                    autoResize
                    rows={1}
                    className="max-h-20rem my-2 p-2 mx-4 surface-hover overflow-y-auto"
                    value={chatNameVal}
                    onChange={
                        (e) => {
                            setChatNameVal(e.target.value);
                        }
                    }
                />
                <p>Usernames</p>
                <MultiSelect
                    value={usernames}
                    filter maxSelectedLabels={1}
                    onChange={(e) => setUserNamesValue(e.value)}
                    options={availableUsers}
                    optionLabel="username"
                    placeholder="Select Users"
                    className=""
                    display="comma"
                    valueTemplate={(option) => { return ( <div>{option}</div> ) }} itemTemplate={(option) => { return ( <p className="text-center p-0 m-0">{option}</p> ) }}
                />
                <div className="w-full pt-4">
                    <Button
                        className="w-6 text-center"
                        label="Create new Groupchat"
                        onClick={() => {
                            setCreating(false);
                            createGroupChat(chatNameVal, [...usernames, session.user.username]);
                        }}
                    />
                </div>


            </Dialog>

            <div className="card flex flex-column w-2 px-4 h-full overflow-y-scroll overflow-x-hidden border-noround align-items-start">
                <div className="flex flex-column w-full align-items-center justify-content-center">
                    <Button
                        className="w-full text-center mt-2"
                        label="Create new Groupchat"
                        onClick={() => {
                            setCreating(true)
                        }}
                    />
                    <Toast ref={toast}/>
                    <Dropdown value={privateChatUserName} onChange={(e) => setPrivateChatUserName(e.value)} options={availableUsers} optionLabel="name" placeholder="Select a User"
                              filter valueTemplate={(option) => { return ( <div>{option}</div> ) }} itemTemplate={(option) => { return ( <p className="text-center p-0 m-0">{option}</p> ) }} className="w-full h-min my-2 text-center " />
                    <Button label="Create Private Chat" onClick={() => {
                        if (privateChatUserName != "") {
                            createPrivateMessage(session.user.id, privateChatUserName, showError);
                            setPrivateChatUserName("")

                        }
                    }}/>

                </div>
                {chats && chats.map((chat, index) => {
                    return (
                        <div className={` ${chat.id === currentChatId && "text-primary"}`}>
                            <h1 className="text-xl" onClick={() => {
                                setCurrentChatId(chat.id)
                            }} >{chat.name}</h1>
                        </div>
                    )
                })}
            </div>
            <div className="w-10 h-full">
                {currentChatId !== -1 &&
                    <Chat
                        id={currentChatId}
                        newMessages={newMessages}
                        updatedMessages={updatedMessages}
                        deletedMessages={deletedMessages}
                    />}
            </div>
        </div>
    )
}