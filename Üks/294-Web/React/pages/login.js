import { useState } from "react";
import { login } from "@/lib/api/authorization";
import { useSession } from "@/lib/hooks/session";
import { Router, useRouter } from "next/router";

export default function Login() {
    const router = useRouter()
    const [model, setModel] = useState({
        email: '',
        password: '',
    });

    const [isLoading, setIsLoading] = useState(false);
    const { signIn } = useSession();

    const handleChange = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        setModel({
            ...model,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);

        try {
            const result = await login({ email: model.email, password: model.password });
            
            signIn({
                token: result.token,
                user: result.user
            });

            const urlParams = new URLSearchParams(window.location.search)
            const url = urlParams.get("url")
            router.push(url)
            
        } catch (error) {
            console.error('Error during login: ', error);
        }

        setIsLoading(false);
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <label>Email:</label>
                    <input type="text" name="email" onChange={handleChange} value={model.email}/>
                </fieldset>

                <fieldset>
                    <label>Password:</label>
                    <input type="password" name="password" onChange={handleChange} value={model.password}/>
                </fieldset>

                <fieldset>
                    <input type="submit" value="Login" disabled={isLoading}/>
                </fieldset>
            </form>
        </div>
    );
}