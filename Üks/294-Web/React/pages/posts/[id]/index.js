import { useRouter } from "next/router";
import { getPostById, deletePost } from "@/lib/api/posts";
import { useEffect, useState } from "react";
import Link from "next/link";
import { useSession } from "@/lib/hooks/session";

export default function PostPage() {
    const router = useRouter()
    const { id } = router.query

    const [post, setPost] = useState(null);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const { isSignedIn, session } = useSession();

    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);

            try {
                const postData = await getPostById(id);
                setPost(postData);
            } catch (error) {
                setError(error);
            } finally {
                setIsLoading(false);
            }
        };

        if (id) {
            fetchData();
        }

        
    }, [id]);

    const handleDelete = async () => {
        try {
            await deletePost(id, session.token);

            router.push('/');
        } catch (error) {
            console.error('Error deleting post: ', error);
        }
    };

    return (
        <div>
            {isLoading && <p>Loading...</p>}

            {post && (
                <>
                    <h1>{post.title}</h1>
                    <p>{post.text}</p>

                    {isSignedIn && (
                        <>
                            <Link href={`/posts/${id}/edit`}>Edit</Link>
                            <button onClick={handleDelete}>Delete</button>
                        </>
                    )}
                </>
            )}
        </div>
    )
}