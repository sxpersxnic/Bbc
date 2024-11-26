import MessageList from "@/components/MessageList";
import MessageForm from "@/components/MessageForm";
import { io } from "socket.io-client";
const socket = io("http://localhost:8080");

export default function ChatPage() {


    return (
        <div>
            <MessageList socket={socket} />
            <MessageForm socket={socket} />
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
