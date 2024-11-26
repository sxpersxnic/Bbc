import { getUserById, deleteUser } from "@/lib/api/auth";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { useSession } from "@/lib/hooks/session";
import styles from "./profile.module.css"

export default function ProfilePage() {
    const router = useRouter()
    const { id } = router.query
    const [user, setUser] = useState({})
    const { session, signOut } = useSession();

    useEffect(() => {
        if (id == undefined) return;

        const load = async () => {
            setUser(await getUserById(id))
        }
        load()
    }, [id])

    const deleteCurrentUser = () => {
        if (confirm("Are you sure?")) {
            deleteUser(id)
            signOut()
            router.push("/")
        }
    }

    const logOutCurrentUser = () => {
        //if (confirm(`Log out of ${user.username}`)) {
            signOut()
            router.push("/")
        //}
    }

    return (
        <div className={styles.profile}>
            <h2 className={styles.title}>{user.username}</h2>
            <div className={styles.subTitle}>
                <p className={styles.subTitleTxt}>Profile Created at:</p><p className={styles.creationDate}>{user.created_at}</p>
            </div>

            <div className={styles.userData1}>
                <div className={styles.user1}>
                    <label className={styles.label}>Username:</label><p>{user.username}</p>
                </div>
                <div className={styles.user1}>
                    <label className={styles.label}>Email:</label><p>{user.email}</p>
                </div>
            </div>
            <div className={styles.userData2}>
                <div className={styles.user2}>
                    <label className={styles.label}>Surname:</label><p>{user.surname}</p>
                </div>
                <div className={styles.user2}>
                    <label className={styles.label}>Firstname:</label><p>{user.firstname}</p>
                </div>
            </div>
            <div className={styles.btnBar}>
            <button onClick={() => logOutCurrentUser()} className={styles.logOut}>Log out</button>
            <button onClick={() => deleteCurrentUser()}className={styles.deleteAcc}>Delete account</button>
            </div>
            
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

export async function getStaticPaths(context) {
    return {
        fallback: true,
        paths: []
    }
}