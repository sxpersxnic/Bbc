import { Image } from "primereact/image";
import { Dialog } from "primereact/dialog";
import { useState } from "react";
import ChatProfile from "./ChatProfile";

export default function ChatHeader({ chat }) {
  const [visible, setVisible] = useState(false);
  const handleChatIconClick = () => {
    setVisible(false);
  };

  return (
    <div className="card border-noround-top border-x-none p-2">
      <div className="flex align-items-center justify-content-start">
        <Image
          src={chat.img}
          zoomSrc={chat.img}
          alt="ChatImage"
          width="48px"
          className="m-3"
          onClick={() => setVisible(true)}
        />
        <h2 onClick={() => setVisible(true)}>{chat.name}</h2>
        <div className="">
          <Dialog
            header="Profile"
            visible={visible}
            className="w-6"
            draggable={false}
            onHide={() => setVisible(false)}
          >
            <ChatProfile chat={chat} onChatIconClick={handleChatIconClick} />
          </Dialog>
        </div>
      </div>
    </div>
  );
}
