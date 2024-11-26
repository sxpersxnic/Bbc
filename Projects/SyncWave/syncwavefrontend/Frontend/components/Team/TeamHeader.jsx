import {useState, useEffect} from 'react'
import {createNewTeam} from "../../lib/team";
import {Dialog} from "primereact/dialog";

export default function TeamHeader(){
    const [visible, setVisible] = useState(false);
    const [newTeamName, setNewTeamName] = useState('');
    const [newTeamInitials, setNewTeamInitials] = useState('');
    const [newTeamDescription, setNewTeamDescription] = useState('');

    async function handleCreateNewTeam(event) {
        event.preventDefault();
        try {
            const newTeam = await createNewTeam(newTeamName, newTeamInitials, newTeamDescription);
            // onTeamCreated(newTeam);
            alert("New Team was created")
            setVisible(false);
            setNewTeamName('');
            setNewTeamInitials('');
            setNewTeamDescription('');
        } catch (e) {
            console.error("Error creating new team:", e);
        }
    }

    return (
        <div className="border-noround border-none card p-2 flex flex-row justify-content-between top-0 sticky w-screen">
          <div className="flex align-items-center justify-content-start">
           <h3>Teams</h3>
          </div>
            <div className="flex align-items-center justify-content-end">
                <div className="flex flex-row align-items-center border-round-lg border-solid pr-5 pl-5 mr-8"
                     onClick={() => setVisible(true)}>
                    <i className="pi pi-user-plus" style={{fontSize: '1.5rem', marginRight: "12px"}}></i>
                    <p>Create Teams</p>
                </div>
            </div>
            <Dialog header="Create New Team" visible={visible} style={{width: '50vw'}} modal
                    onHide={() => setVisible(false)}>
                <form onSubmit={handleCreateNewTeam} className="flex flex-column mt-3 gap-3">
                    <div className="">
                    <label htmlFor="teamName">Team Name: </label>
                        <input
                            id="teamName"
                            className=""
                            type="text"
                            value={newTeamName}
                            onChange={(e) => setNewTeamName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="">
                        <label htmlFor="teamInitials">Team Initials: </label>
                        <input
                            id="teamInitials"
                            type="text"
                            value={newTeamInitials}
                            onChange={(e) => setNewTeamInitials(e.target.value)}
                            required
                        />
                    </div>
                    <div className="">
                        <label htmlFor="teamDescription">Team Description: </label>
                        <input
                            id="teamDescription"
                            type="text"
                            value={newTeamDescription}
                            onChange={(e) => setNewTeamDescription(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="p-button p-component " onClick={() => setVisible(false)}>Create Team</button>
                </form>
            </Dialog>

        </div>
      );
    }
    
