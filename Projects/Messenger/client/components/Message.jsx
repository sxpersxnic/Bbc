import styles from "./Message.module.css";
import { useSession } from "@/lib/hooks/session";
import { useState, useEffect } from "react";
import { getUserById } from '@/lib/api/auth';

export default function Message({ context }) {
    const [username, setUsername] = useState()
    const { session, signOut } = useSession()
    const user = session.user

    const getUsername = async () => {
        const response = await getUserById(user)
        setUsername(response.username)
    }

    useEffect(() => {
        if (user) {
            getUsername()
        }
    }, [user])

    return (
        <div className={styles.message}>
            <p className={styles.user}>{username}</p>
            <p className={styles.text}>{context}</p>
        </div>
    )
}