import Link from "next/link";

export default function TeamNavigation({setSelectedTeamIndex, teams}) {


  const handleTeamClick = (team) => {
    console.log(`Team ${team.name} was chosen`);
    setSelectedTeamIndex(team.id);
  };

  return (
    <div className="grid gap-5 p-5">
      {teams.map((team) => (
        <div
          key={team.id}
          className="border-solid border-round-lg p-2 text-center col-2 hover:bg-cyan-700"
          onClick={() => handleTeamClick(team)}
        >
          <h3>{team.initials}</h3>
          <p className="text-base">{team.name}</p>
          <p className="text-gray-400 text-sm">{team.description}</p>
        </div>
      ))}
    </div>
  );
}
