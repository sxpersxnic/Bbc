import { createPost, updatePost } from "@/lib/api/posts"
import { useRouter } from "next/router"
import { useEffect, useState } from "react"
import styles from "./PostForm.module.css"
import { useSession } from "@/lib/hooks/session"
import Link from "next/link"

const defaultModel = {
    title: "",
    text: ""
}

function validateModel(post) {
    const errors = {
        title: post.title.trim() === "" ? "Title is required." : "",
        text: post.text.trim() === "" ? "Text is required." : ""
    };
    
    const isValid = Object.values(errors).every((error) => error === "");

    return { errors, isValid }
}

export default function PostForm({ postToEdit }) {
    const { session } = useSession()
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);
    const [errors, setErrors] = useState(defaultModel);
    const [post, setPost] = useState(defaultModel);

    useEffect(() => {

        if (postToEdit) {
            setPost(postToEdit);
        }

    }, [postToEdit])

    const handleChange = (e) => {
        setPost({
            ...post,
            [e.target.name]: e.target.value
        });

        setErrors({
            ...errors,
            [e.target.name]: ""
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault()
        setIsLoading(true)
        setErrors(defaultModel)

        const result = validateModel(post)

        if (!result.isValid) {
            setErrors(result.errors)
            setIsLoading(false)
            return;
        }
        
        try {
            if (post.id) {
                await updatePost(post, session.token);
                router.push(`/posts/${post.id}`);
            } else {
                const newPost = await createPost(post, session.token);
                router.push(`/posts/${newPost.id}`);
            }

        } catch (error) {
            console.error('Error submitting post: ', error);
            setErrors({ general: 'An error occured while submitting the post.'});
        }
        
        setIsLoading(false);
    };

    return (
        <div className={styles.postform}>
            <form onSubmit={handleSubmit}>
                <div className={styles.buttonbar}>
                    <Link href={`/`} className={styles.link}><button className={styles.cancelbutton}>Cancel</button></Link>
                    <button disabled={isLoading} className={styles.postbutton}>                      
                        {isLoading ? "...Loading" : "Post"}
                    </button>
                </div>
                
                <div className={styles.container}>
                    <div className={styles.textContainer}>
                        {errors.title && <div className={styles.error}>{errors.title}</div>}
                        <input type="text" name="title" onChange={handleChange} value={post.title} placeholder="Title:" />
                    </div>
                
                    <div className={styles.textContainer}>
                        {errors.text && <div className={styles.error}>{errors.text}</div>}
                        <textarea name="text" onChange={handleChange} value={post.text} placeholder="Text:"/>
                    </div>
                </div>
            </form>
        </div>
    );
}