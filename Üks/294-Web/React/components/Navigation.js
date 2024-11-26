import { useSession } from "@/lib/hooks/session";
import Link from "next/link"
import styles from "@/components/Navigation.module.css"

export default function Navigation() {
    const { session, isSignedIn, signOut } = useSession();

    return (
        <div className={styles.container}>

            <nav className={styles.create}>
                <Link href="/posts/create">Create Post</Link>
            </nav>

            <div className={styles.signedIn}>
                {isSignedIn && <span onClick={signOut}>Logout</span>}
            </div>

            <div className={styles.signedOut}>
                { !isSignedIn && <Link href="/login">Login</Link>}
            </div>
        </div>
    );
}