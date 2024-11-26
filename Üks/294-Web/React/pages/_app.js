import Header from "@/components/Header"
import Link from "next/link"
import "./_app.css"
import { useSession } from "@/lib/hooks/session"
import { useAuthRedirect } from "@/lib/hooks/authredirect"
import Navigation from "@/components/Navigation"

export default function App({ Component, pageProps }) {
    const { isLoaded, isSignedIn } = useSession()
    useAuthRedirect(pageProps)
    return isLoaded && (
        <>
            <Header>
                <Link href="/">MyCode</Link>

                <Navigation/>

            </Header>

            <main className="page">
            { (!pageProps.secured || isSignedIn) && <Component {...pageProps}/>}            
            </main>
        </>
    )
}