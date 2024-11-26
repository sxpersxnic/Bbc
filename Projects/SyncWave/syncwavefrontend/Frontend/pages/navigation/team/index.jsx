import {useState, useEffect} from 'react'
import TeamMain from 'components/Team/Team'
import TeamDetails from "../../../components/Team/TeamDetails";
import {loadAllTeamsOfUserId} from "../../../lib/team";
import {useSession} from "../../../lib/session";
import {loadChat, loadChats} from "../../../lib/chat";

export default function Team({client}){

    const [selectedTeamIndex, setSelectedTeamIndex] = useState(null);
    const [userTeam, setUserTeams] = useState([])
    const {session} = useSession();
    const userId = session.user.id

    useEffect( () => {
        if (userTeam) {
            load(userId);
        }
    }, []);

    async function load(teamId) {
        let teams = await loadAllTeamsOfUserId(teamId);
        console.log(teams)
        setUserTeams(teams)
    }



    return(

        <div className="flex fixed w-full h-screen border-noround" style={{paddingBottom: "102.875px", paddingRight: "4.6%"}}>
            {selectedTeamIndex == null ? <TeamMain teams={userTeam} setSelectedTeamIndex={setSelectedTeamIndex}/> : <TeamDetails client={client} team={userTeam.find((team) => {return team.id == selectedTeamIndex})} setSelectedTeamIndex={setSelectedTeamIndex}/>}
        </div>

    )

}

export async function getStaticProps(context) {
    return {
        props: {
            secured: true
        }
    }
}