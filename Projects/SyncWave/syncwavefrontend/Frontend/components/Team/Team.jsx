import TeamHeader from "./TeamHeader";
import TeamNavigation from "./TeamNavigation";



export default function TeamMain({setSelectedTeamIndex, teams}){


    return(
        <div className="h-screen">
            <TeamHeader/>
            <TeamNavigation teams={teams} setSelectedTeamIndex={setSelectedTeamIndex}/>
        </div>
    )
}