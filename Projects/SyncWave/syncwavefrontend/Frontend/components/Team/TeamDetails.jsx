import {Button} from "primereact/button";
import {Dialog} from "primereact/dialog";
import {useEffect, useState} from "react";
import {addUsersToTeam, loadTeamById} from "../../lib/team";
import {addNewTaskAndUsers} from "../../lib/task";
import {Dropdown} from "primereact/dropdown";
import {loadAllUser, loadUserById} from "../../lib/user";
import {MultiSelect} from "primereact/multiselect";
import {useSession} from "../../lib/session";
import {createGroupChat, loadChat} from "../../lib/chat";
import Chat from "../Chat/Chat";
import {InputTextarea} from "primereact/inputtextarea"
import ChatBox from "../Chat/ChatBox";

export default function TeamDetails({ setSelectedTeamIndex, team, client }) {
    const [visible, setVisible] = useState(false);
    const [taskVisible, setTaskVisible] = useState(false)
    const [userRoleVisible, setUserRoleVisible] = useState(false);
    const [newChannelVisible, setNewChannelVisible] = useState(false);
    const [newChannelName, setNewChannelName] = useState("");
    const {session} = useSession();
    const userId = session.user.id;
    const [userTeam, setUserTeam] = useState(null);
    const [users, setUsers] = useState([]);
    const [availableUsers, setAvailableUsers] = useState([]);
    const [selectedUsers, setSelectedUsers] = useState([]);
    const [selectedTaskUsers, setSelectedTaskUsers] = useState([]);
    const [channels, setChannels] = useState([]);
    const [newMessages, setNewMessages] = useState({});
    const [updatedMessages, setUpdatedMessages] = useState({});
    const [deletedMessages, setDeletedMessages] = useState({});
    const [currentChatId, setCurrentChatId] = useState(-1);
    const [newTitle, setNewTitle] = useState('');
    const [newDescription, setNewDescription] = useState('');
    const [assignUsers, setAssignUsers] = useState([]);
    const [taskUser, setTaskUser] = useState([]);

    const handleClick = () => {
        setSelectedTeamIndex(null);
    };

    console.log({users})

    async function handleAddUser(e) {
        e.preventDefault();
        const idsArray = selectedUsers.map(user => user.id);
        try {
            const newTeam = await addUsersToTeam(team.id, idsArray);
            console.log('New users added:', newTeam);
            setVisible(false);
            setSelectedUsers([]);
            loadData()
        } catch (e){
            console.log(e.error)
        }
    }

    const handleAddNewTaskAndUsers = async (e) => {
        e.preventDefault();

        const userIds = selectedTaskUsers.map(user => user.id)
        const task = {
            title: newTitle,
            description: newDescription,
            dueAt: new Date().toISOString(),
            userIds: userIds
        };

        try {
            const newTask = await addNewTaskAndUsers(task);
            console.log('New Task added:', newTask);
            setNewTitle('');
            setNewDescription('');
            setTaskVisible(false);
        } catch (error) {
            console.error('Error adding task:', error);
            // Handle error scenario
        }
    };

    const subscribe = (chats) => {
        chats.forEach((chat) => {
            console.log(chat)
            client.subscribe(`/chat/${chat.id}/queue`, (message) => {
                let newNewMessages = {...newMessages};
                newNewMessages[chat.id] = JSON.parse(message.body);
                setNewMessages(newNewMessages);
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

    const loadData = async () => {
        const fetchedTeam = await loadTeam(team.id);
        const users = await loadUserInTeams(fetchedTeam.memberIds);
        const allUsers = await loadAllUser();
        setTaskUser(allUsers)
        const available = await loadAllAvailableUsers(allUsers, fetchedTeam);
        let channels = [];
        console.log(team)
        for (let chatId of team.chatIds) {
            channels.push(await loadChat(chatId));
        }
        setUsers(users);
        setAvailableUsers(available);
        setChannels(channels);
        subscribe(channels);
    };

    useEffect(() => {
        loadData();
    }, [team.id, userId]);


    async function loadTeam(teamId) {
        try {
            let fetchedTeam = await loadTeamById(teamId);
            return fetchedTeam;
        } catch (error) {
            console.error("Error fetching team:", error);
        }
    }

    async function loadUserInTeams(memberIds) {
        try {
            let promises = memberIds.map(id =>  loadUserById(id))
            const usersArray = await Promise.all(promises);
            return usersArray.map(user => user.username)
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    }

    async function loadAllAvailableUsers(allUsers, userTeam) {
        try {
            let teamMemberIds = userTeam ? userTeam.memberIds : [];
            return allUsers.filter(user => !teamMemberIds.includes(user.id))
        } catch (error) {
            console.error("Error fetching all users:", error);
        }
    }


    return (
        <div className="flex w-full bottom-0 top-0 overflow-hidden">
            <div className="card flex flex-column w-2 p-1 h-full overflow-y-hidden border-noround align-items-start">
                <Button label="Back" onClick={handleClick} />
                <div className="card w-5rem h-5rem text-center align-content-center text-4xl mt-2">
                    <p className="m-0 p-0">{team.initials}</p>
                </div>
                <h1 className="m-0 mt-2">{team.name}</h1>
                <Button label="Create new Channel" onClick={() => {setNewChannelVisible(true);}}/>
                <Dialog header="Create new Channel" visible={newChannelVisible} onHide={() => {setNewChannelVisible(false); setNewChannelName("")}}>
                    <div className="flex flex-column">
                        <InputTextarea autoResize value={newChannelName} onChange={(e) => {setNewChannelName(e.target.value)}}/>
                        <Button label="Create" onClick={() => {createGroupChat(newChannelName, users, team.id); setNewChannelVisible(false);}}/>
                    </div>
                </Dialog>
                <p className="m-0 mt-3 font-bold">Channels</p>
                {channels.map((channel) => (
                    <div key={channel.id} onClick={() => {setCurrentChatId(channel.id)}}>
                        <p className={`m-0 ${channel.id === currentChatId && "text-primary"}`}>
                            {channel.name}
                        </p>
                    </div>
                ))}

            </div>
            <div className="w-full h-full flex flex-column">
                <div className="card flex flex-row border-noround border-left-none p-2 top-0 sticky w-full">
                    <div className="flex justify-content-between align-items-center w-full">
                        <div className="flex flex-row align-items-center">
                            <h3 className="m-0 mr-2">Team</h3>
                            {currentChatId !== -1 && <Button label="Back" onClick={() => {setCurrentChatId(-1)}}/>}
                        </div>

                        <div className="flex flex-row align-items-center border-round-lg pr-2 pl-2 mr-8">
                            <div className="flex flex-row justify-content-between align-items-center gap-3">
                                <Dropdown
                                    value={null}
                                    options={users}
                                    placeholder={"Users in " + team.name}
                                    className="w-full md:w-14rem"
                                    onChange={(e) => {
                                        if (true) {//TODO: CHECK IF USER OS ALLOWED TO EDIT ROLES
                                            setUserRoleVisible(e.value);
                                        }
                                    }}
                                />
                                <Dialog header={`Edit ${userRoleVisible}'s roles`} visible={userRoleVisible} onHide={() => {setUserRoleVisible(false)}}>
                                    <MultiSelect className="w-full" options={[/*TODO: ALL TEAM-ROLES*/]}></MultiSelect>
                                </Dialog>
                                {team.ownerId === session.user.id && <i className="pi pi-user-plus" style={{ fontSize: '1.5em', marginRight: "12px" }} onClick={() => setVisible(true)}></i>}
                                {team.ownerId === session.user.id && <i className="pi pi-plus" style={{ fontSize: '1.5em', marginRight: "12px" }} onClick={() => setTaskVisible(true)}></i>}
                            </div>
                        </div>
                    </div>
                </div>
                {currentChatId !== -1 ?
                    <div className="flex-grow-1 h-full overflow-auto">
                        <Chat
                            header={false}
                            id={currentChatId}
                            newMessages={newMessages}
                            updatedMessages={updatedMessages}
                            deletedMessages={deletedMessages}
                        />
                    </div>
                    :
                    <div className="p-2 h-full overflow-y-hidden">
                        <div>
                            <h1>Users:</h1>
                            <ul>
                                {users.map((user, index) => (
                                    <li key={index}>{user}</li>
                                ))}
                            </ul>
                        </div>
                    </div>
                }

            </div>
            <Dialog header="Add Users to Team" visible={visible} style={{width: '50vw'}} modal
                    onHide={() => setVisible(false)}>
                <form onSubmit={handleAddUser} className="flex flex-column mt-3 gap-3">
                    <div className="">
                        <label htmlFor="userIds">Select Users: </label>
                        <MultiSelect
                            value={selectedUsers}
                            filter maxSelectedLabels={1}
                            onChange={(e) => setSelectedUsers(e.value)}
                            options={availableUsers}
                            optionLabel="username"
                            placeholder="Select Users"
                            className="w-full md:w-20rem"
                            display="chip"
                        />
                    </div>
                    <button type="submit" className="p-button p-component" >Add Users</button>
                </form>
            </Dialog>
            <Dialog header="Add New Task and Users" visible={taskVisible} style={{width: '50vw'}} modal
                    onHide={() => setTaskVisible(false)}>
                <form onSubmit={handleAddNewTaskAndUsers} className="flex flex-column mt-3 gap-3">
                    <div className="">
                        <label htmlFor="teamName">Title: </label>
                        <input
                            id="teamName"
                            className=""
                            type="text"
                            value={newTitle}
                            onChange={(e) => setNewTitle(e.target.value)}
                            required
                        />
                    </div>
                    <div className="">
                        <label htmlFor="teamInitials">Description: </label>
                        <input
                            id="teamInitials"
                            type="text"
                            value={newDescription}
                            onChange={(e) => setNewDescription(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <MultiSelect
                            value={selectedTaskUsers}
                            filter maxSelectedLabels={1}
                            onChange={(e) => setSelectedTaskUsers(e.value)}
                            options={taskUser}
                            optionLabel="username"
                            placeholder="Select Users"
                            className="w-full md:w-20rem"
                            display="chip"
                        />
                    </div>
                    <button type="submit" className="p-button p-component">Create Task</button>
                </form>
            </Dialog>
        </div>
    );
}
