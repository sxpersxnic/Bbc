import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { getPostById, updatePost } from "@/lib/api/posts";
import PostForm from "@/components/PostForm";
import { useSession } from "@/lib/hooks/session";


export default function PostsEditPage() {
    const router = useRouter()
    const { id } = router.query

    const [postToEdit, setPostToEdit] = useState(null);

    const session = useSession();
    
    useEffect(() => {
        const fetchPostData = async () => {
            try {
                const post = await getPostById(id);
                setPostToEdit(post);
            } catch (error) {
                console.error('Error fetching post: ', error);
            }
        };

        if (id) {
            fetchPostData();
        }
    }, [id]);

    const handleUpdatePost = async (updatedPost) => {
        try {
            await updatePost(updatedPost, session.token);

            router.push(`/posts/${id}`);
        } catch (error) {
            console.error("Error updating post: ", error);
        }
    };

    return (
        <div>
            <h1>Edit post with id: {id}</h1>
            {postToEdit && <PostForm postToEdit={postToEdit} onSubmit={handleUpdatePost} />}
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

export async function getStaticPaths(context) {
    return {
        fallback: true,
        paths: []
    }
}