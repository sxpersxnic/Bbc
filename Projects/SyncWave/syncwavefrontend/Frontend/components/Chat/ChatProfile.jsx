import { TabMenu } from "primereact/tabmenu";
import {useEffect, useState} from "react";
import { Image } from "primereact/image";
import ChatContactUser from "./ChatUser";
import { OrganizationChart } from "primereact/organizationchart";
import {loadUserById} from "../../lib/user";
import {useSession} from "../../lib/session";

export default function ChatProfile({ chat, onChatIconClick }) {
  const [users, setUsers] = useState([]);
  const {session} = useSession();


  async function load() {
    let newUsers = []

    for (let userId of chat.userIds) {
      const user = await loadUserById(userId);
      if (session.user.id !== user.id) {
        newUsers.push(user);
      }
    }

    setUsers(newUsers);
  }

  useEffect(() => {
    load();
  }, []);


  const icons = [
    {
      id: 1,
      icon: "pi pi-comment",
      onclick: onChatIconClick,
    },
    {
      id: 2,
      icon: "pi pi-sitemap",
      onclick: () => setSelectedIndex(2),
    },
    {
      id: 3,
      icon: "pi pi-video",
    },
    {
      id: 4,
      icon: "pi pi-phone",
    },
  ];

  const [selectedIndex, setSelectedIndex] = useState(0);

  return (
    <div className="">
      <div className="flex flex-column">
        <div className="flex align-items-center">
          <div>
            <Image
              src={chat.img}
              alt="ChatImage"
              width="113px"
              className="m-3"
            />
          </div>
          {/* Icons */}
          <div className="flex flex-column justify-content-center w-auto">
            <h1>{chat.name}</h1>
            <div className="flex">
              {icons.map((icon) => (
                <i
                  key={icon.id}
                  className={`pi ${icon.icon} p-2 mr-5`}
                  style={{ fontSize: '1.5rem', cursor: 'pointer' }}
                  onClick={icon.onclick}
                ></i>
              ))}
            </div>
          </div>
        </div>
      </div>
      <div className="pt-4">
        {users && <ChatContactUser users={users}/>}
      </div>
    </div>
  );
}
