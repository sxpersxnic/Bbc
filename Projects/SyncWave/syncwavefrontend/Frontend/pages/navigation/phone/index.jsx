



export default function Phone(){

    return(

        <>
            <h1>Hello Phone</h1>
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