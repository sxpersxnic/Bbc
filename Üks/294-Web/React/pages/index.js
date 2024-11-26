import { getAllPosts } from "@/lib/api/posts";
import styles from "./index.module.css"
import Link from "next/link";
import React, { useState, useEffect } from "react";

const URL = "http://localhost:9001/api/posts"

export default function IndexPage() {
    const [posts, setPosts] = useState([])

    useEffect(() => {
        const load = async function() {
            const posts = await getAllPosts()
            setPosts(posts)
        }

        load()
    }, [])


    return (
        <div className={styles.posts}>
            <h1>Welcome to my blog!</h1>
            <ul>
                {posts.map((post) => (
                    <li key={post.id}>
                        <Link href={`/posts/${post.id}`}>{post.title}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}