import styles from "./create.module.css"

import { useState } from 'react';
import { useRouter } from 'next/router';
import { createPost } from '@/lib/api/posts';
import PostForm from "@/components/PostForm";
import { useSession } from "@/lib/hooks/session";

export default function PostsCreatePage() {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);
    const [errors, setErrors] = useState({});
    const defaultModel = { title: '', text: ''};
    const session = useSession();

    const handleSubmit = async (post) => {
        setIsLoading(true);
        setErrors(defaultModel);

        try {
            const result = await createPost(post, session.token);

            router.push(`/posts/${result.id}`);
        } catch (error) {
            console.error('Error creating post: ', error);

            setErrors({ general: 'An error occured while creating the post.'});
        }

        setIsLoading(false);
    };

    return (

        <div className={styles.postcreate}>
            <h1>Create new post</h1>
            <PostForm onSubmit={handleSubmit} isLoading={isLoading} errors={errors} />
        </div>
        
    ); 
}

export async function getStaticProps(context) {
    return {
        props: {
            secured: true
        }
    }
}