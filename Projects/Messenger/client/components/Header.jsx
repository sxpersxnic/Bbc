import React from 'react'
import styles from './Header.module.css';
import { useSession } from "@/lib/hooks/session"
import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Link from 'next/link';
import { getUserById } from '@/lib/api/auth';

export default function Header() {
    const [username, setUsername] = useState()
    const { session, signOut } = useSession()
    const user = session.user
    const router = useRouter()

    useEffect(() => {
        if (user) {
            getUsername()
        }
    }, [user])

    const getUsername = async () => {
        const response = await getUserById(user)
        setUsername(response.username)
    }

    const handleClick = async (e) => {
        e.preventDefault()
        signOut()
        router.push("/")
    }

    return (
        <nav className={styles.header}>
            <div className={styles.logoContainer}>
                <Link href="/" className={styles.logo}>MyCode</Link>
            </div>
                {!user && 
                    <div className={styles.buttonBar}>
                        <Link href="/user/login" className={styles.leftLink}><button className={styles.leftBtn} key="login">Login</button></Link>
                        <Link href="/user/register" className={styles.rightLink}><button className={styles.rightBtn} key="register">Register</button></Link>
                    </div>
                }
                {user &&
                    <div className={styles.buttonBar}>
                        <Link href="/chat" className={styles.leftLink}><button className={styles.leftBtn} key="chat">Chat</button></Link>
                        <Link href={`/user/${user}/profile`} className={styles.rightLink}><button className={styles.rightBtn} key="profile">{username}</button></Link>
                    </div> 
                }      
        </nav>
    );
}