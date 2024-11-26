import Header from "@/components/Header"
import { useSession } from "@/lib/hooks/session"
import { useAuthRedirect } from "@/lib/hooks/authdirect"
import "./_app.css"

export default function App({ Component, pageProps }) {
    const { isLoaded, isSignedIn } = useSession()
    useAuthRedirect(pageProps)
    return isLoaded && (
        <>
            <Header />

            <main className="page">
                {(!pageProps.secured || isSignedIn) && <Component {...pageProps} />}
            </main>
        </>
    )
}