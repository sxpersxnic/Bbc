import {useAuthRedirect} from "../lib/authredirect";
import {useSession} from "../lib/session";
import {PrimeReactProvider, PrimeReactContext} from "primereact/api";
import 'primereact/resources/themes/lara-dark-purple/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import SockJS from "sockjs-client";
import {over} from "stompjs";
import {useRouter} from "next/router";
import Header from "../components/Header/Header";
import "../styles/App/_app.css";
import Navigation from "../components/Navigation/Navigation";
import Activity from "./navigation/activity";
import ChatDefaultPage from "./navigation/chat";
import Team from "./navigation/team";
import Task from "./navigation/task";
import Calendar from "./navigation/calendar";
import Phone from "./navigation/phone";
import {useEffect, useRef, useState} from "react";
import {loadAllUser} from "../lib/user";
import {Client} from "@stomp/stompjs";

export default function App({Component, pageProps}) {
    const {session, isLoaded, isSignedIn} = useSession();
    const router = useRouter();
    const [users, setUseres] = useState();
    const [connected, setConnected] = useState(false);
    const [stompClient, setStompClient] = useState(null);
    const elements = [
        {element: <ChatDefaultPage  client={stompClient} />, name: "Chat", icon: "comments"},
        {element: <Team client={stompClient}/>, name: "Team", icon: "users"},
        {element: <Task/>, name: "Task", icon: "list-check"}
    ];

    const header = useRef(null);


    useEffect(() => {
        console.log(stompClient)
    }, [stompClient]);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/api/v1/ws');
        const client = Stomp.over(socket);
        client.connect({}, (frame) => {
            console.log('Connected: ' + frame);
        });

        setStompClient(client);

        return () => {
            if (client) {
                client.disconnect(() => {
                    console.log('Disconnected');
                });
            }
        };
    }, []);

    const sendMessage = () => {
        if (stompClient) {
            fetch("http://localhost:8080/api/v1/ws/chat.send", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem("token")}`
                },
                body: JSON.stringify({
                    "chatId": 1,
                    "content": "Hello world"
                })
            })
        }
    };


    useAuthRedirect(pageProps);
    return (
        isLoaded && (
            <PrimeReactProvider>
                <div className="h-screen align-content-center">
                    <div className="h-screen overflow-y-hidden">

                        <div>
                            <main>
                                {(!pageProps.secured || isSignedIn) && <Component {...pageProps} />}
                            </main>
                        </div>


                        {!router.asPath.startsWith("/auth") && isSignedIn &&
                            <div>
                                <Navigation elements={elements}/>
                                <Header ref={header}/>
                            </div>
                        }
                    </div>

                </div>
            </PrimeReactProvider>
        )
    );
}
