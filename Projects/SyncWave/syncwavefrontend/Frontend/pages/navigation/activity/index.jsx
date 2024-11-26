import {useState, useEffect} from "react";
import SockJS from "sockjs-client";
import {useSession} from "../../../lib/session";

export default function Activity(){



    return (

        <div>

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