import Link from "next/link"
import Router, { useRouter } from "next/router"
import { useSession } from "../lib/session";
export default function IndexPage() {

    const router = useRouter()
    const {isSignedIn} = useSession()

    return (
        <div>
            {!isSignedIn && 
            <p>
            <Link href="/auth/Login">To Login</Link>
            </p>}
            
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