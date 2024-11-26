import { login } from "@/lib/api/auth";
import { useSession } from "@/lib/hooks/session"
import { useRouter } from "next/router";
import styles from "./LoginForm.module.css";
import { useRef, useState } from "react";
import Link from "next/link"

const defaultModel = {
    identification: "",
    password: ""
}

function validateModel(creds) {
    const errors = {
        identification: "",
        password: ""
    }

    let isValid = true;

    if (creds.identification.split("").length == 0 || creds.identification == undefined) {
        errors.identification = "Email/Username can't be empty!";
        isValid = false;
    }

    if (creds.password.split("").length == 0 || creds.password == undefined) {
        errors.password = "Password can't be empty!"
        isValid = false;
    }

    return { errors, isValid }
}

export default function LoginForm() {
    const { session, signIn } = useSession()
    const router = useRouter()
    const [isLoading, setIsLoading] = useState(false)

    const [creds, setCreds] = useState(defaultModel);
    const [errors, setErrors] = useState(defaultModel);
    const passwordField = useRef();
    const [pwdToggled, setPwdToggled] = useState(false);

    const handleChange = (e) => {
        switch (e.target.name) {
            case "identification": setCreds({ ...creds, identification: e.target.value }); break;
            case "password": setCreds({ ...creds, password: e.target.value }); break;
        }
    }

    // - if password is visible, replace characters with placeholders
    // - if password is not visible, make it visible
    const togglePasswordVisibility = () => {
        if (passwordField.current.type == "password") {
            passwordField.current.type = "text;"
            setPwdToggled(true)
        }
        else {
            passwordField.current.type = "password";
            setPwdToggled(false)
        }
    }

    // - check if value in username/email field is an email or username
    // - try to login with the given credentials
    // - return the value valid or invalid to tell if login worked 
    const loginUser = async () => {
        if (!new RegExp(/^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/).test(creds.identification)) {
            return await login({ username: creds.identification, password: creds.password });
        }
        else {
            return await login({ email: creds.identification, password: creds.password });
        }

    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true)
        setErrors(defaultModel);

        const result = validateModel(creds);

        if (!result.isValid) {
            setErrors(result.errors);
            setIsLoading(false)
            return
        }

        try {

            const validation = await loginUser();
            if (validation == "invalid") {
                setErrors({ identification: "Invalid login", password: "Invalid login" });
                setIsLoading(false)
                return
            }
            signIn({ token: validation.token, user: validation.user })
            setErrors(defaultModel)
            const url = router.query.url
            if (url) {
                router.push(url)
            } else {
                router.push("/")
            }
        } catch (error) {
            setErrors({
                ...errors,
                login: "Login failed"
            })
            setIsLoading(false)
        }

    }
    return session.user ? null : (
        <div className={styles.login}>
            <h1 className={styles.title}>Login</h1>
            
            {errors.login && <h2 className={styles.error}>{errors.login}</h2>}

            <form onSubmit={handleSubmit} className={styles.loginForm}>
                <fieldset className={styles.fieldset}>
                    <input type="text" name="identification" className={styles.input_field} onChange={(e) => handleChange(e)} placeholder="Email/Username:" value={creds.identification} autoComplete="email" required />
                    {errors.identification && <div className={styles.error}>{errors.identification}</div>}
                </fieldset>

                <fieldset className={styles.fieldset}>
                    <input type="password" name="password" className={styles.input_field} ref={passwordField} onChange={(e) => handleChange(e)} placeholder="Password:" value={creds.password} autoComplete="current-password" required />
                    {errors.password && <div className={styles.error}>{errors.password}</div>}
                </fieldset>
                <div className={styles.container}>
                    <div className={styles.showPwd}>
                        <input type="checkbox" onClick={togglePasswordVisibility} className={styles.checkBox}/>
                        <p className={styles.checkBoxTxt}>{pwdToggled ? "Hide Password" : "Show Password"}</p>
                    </div>
                    <button className={styles.submitBtn} disabled={isLoading} type="submit">
                        <span>{isLoading ? "Loading..." : "Login"}</span>
                    </button>
                </div>
                <div className={styles.registerLink}>
                        <p className={styles.txt}>Dont have an account? </p>
                        <Link href="/user/register" className={styles.link}>Register</Link>
                </div>
            </form>
        </div>
    )
}