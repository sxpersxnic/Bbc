export default function Calendar() {

    return (

        <>
            <h1>Hello Calendar</h1>
        </>

    )

}

export async function getStaticProps(context) {
    return {
        props: {
            secured: true
        }
    }
}