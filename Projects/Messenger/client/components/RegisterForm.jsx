import { useRef, useState } from "react";
import styles from "./RegisterForm.module.css"
import { register } from "@/lib/api/auth";
import { useRouter } from "next/router";
import { useSession } from "@/lib/hooks/session";
import Link from "next/link";

const defaultModel = {
    username: "",
    surname: "",
    firstname: "",
    email: "",
    password: "",
    conf_password: ""
}

function validateModel(creds) {
    const errors = {
        username: "",
        surname: "",
        firstname: "",
        email: "",
        password: "",
        conf_password: ""
    }

    let isValid = true;

    if (creds.username.split("").length == 0 || creds.username == undefined) {
        errors.username = "Username can't be empty!";
        isValid = false;
    }

    if (creds.surname.split("").length == 0 || creds.surname == undefined) {
        errors.surname = "Surname can't be empty!";
        isValid = false;
    }

    if (creds.firstname.split("").length == 0 || creds.firstname == undefined) {
        errors.firstname = "Firstname can't be empty!";
        isValid = false;
    }

    if (creds.email.split("").length == 0 || creds.email == undefined) {
        errors.email = "Email can't be empty and must be valid Email!";
        isValid = false;
    }
    else if (!new RegExp(/^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/).test(creds.email)) {
        errors.email = "Not a valid email"
    }

    if (creds.password.split("").length == 0 || creds.password == undefined || creds.password.length < 5) {
        errors.password = "Password can't be empty and must be at least 5 characters long!";
        isValid = false;
    }

    if (creds.conf_password.split("").length == 0 || creds.conf_password == undefined) {
        errors.conf_password = "Confirm Password!"
        isValid = false;
    }

    if (creds.password.split("").length != 0 && creds.conf_password.split("").length != 0 && creds.password != creds.conf_password) {
        errors.password = "Passwords do not match";
        errors.conf_password = "Passwords do not match";
        isValid = false;
    }

    return { errors, isValid }
}



export default function RegisterForm() {
    const { session } = useSession();
    const router = useRouter()
    const [isLoading, setIsLoading] = useState(false)

    const [pwdToggled, setPwdToggled] = useState(false);
    const [creds, setCreds] = useState(defaultModel);
    const [errors, setErrors] = useState(defaultModel);
    const passwordField = useRef();
    const passwordConfField = useRef();

    const handleChange = (e) => {
        switch (e.target.name) {
            case "username": setCreds({ ...creds, username: e.target.value }); break;
            case "surname": setCreds({ ...creds, surname: e.target.value }); break;
            case "firstname": setCreds({ ...creds, firstname: e.target.value }); break;
            case "email": setCreds({ ...creds, email: e.target.value }); break;
            case "password": setCreds({ ...creds, password: e.target.value }); break;
            case "password_conf": setCreds({ ...creds, conf_password: e.target.value }); break;
        }
    }

    // - if password is visible, replace characters with placeholders
    // - if password is not visible, make it visible
    const togglePasswordVisibility = () => {

        if (passwordField.current.type === "password" && passwordConfField.current.type === "password") {
            passwordField.current.type = "text";
            passwordConfField.current.type = "text"
            setPwdToggled(true)
        }
        else {
            passwordField.current.type = "password"
            passwordConfField.current.type = "password"
            setPwdToggled(false)
        }
    }

    // - try to register new user with given credentials and return the response
    const registerUser = async () => {
        return await register({ username: creds.username, surname: creds.surname, firstname: creds.firstname, email: creds.email, password: creds.password });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true)
        setErrors(defaultModel);

        const result = validateModel(creds);

        if (!result.isValid) {
            setErrors(result.errors)
            setIsLoading(false)
            return
        }

        try {
            const availability = await registerUser();
            if (availability == "existing") {
                setErrors("There already is an account with your given email or username")
                setIsLoading(false)
                return
            }
            setIsLoading(false)
            router.push("/")

        } catch (error) {
            setErrors({
                ...errors,
                login: "Login failed"
            })
            setIsLoading(false)
        }
    }


    return session.user ? null : (
        <div className={styles.register}>
            <h1 className={styles.title}>Create MyCode Account</h1>
                
            {errors.register && <h2 className={styles.error}>{errors.register}</h2>}

            <form onSubmit={handleSubmit} className={styles.registerForm}>
                <fieldset className={styles.fieldset}>
                    <input type="text" name="username" className={styles.input_field} onChange={(e) => handleChange(e)} placeholder="Username" />
                    {errors.username && <div className={styles.error}>{errors.username}</div>}
                </fieldset>

                <fieldset className={styles.fieldset}>
                    <input type="text" name="surname" className={styles.input_field} onChange={(e) => handleChange(e)} placeholder="Name" />
                    {errors.surname && <div className={styles.error}>{errors.surname}</div>}
                </fieldset>

                <fieldset className={styles.fieldset}>
                    <input type="text" name="firstname" className={styles.input_field} onChange={(e) => handleChange(e)} placeholder="Firstname" />
                    {errors.firstname && <div className={styles.error}>{errors.firstname}</div>}
                </fieldset>

                <fieldset className={styles.fieldset}>
                    <input type="text" name="email" className={styles.input_field} onChange={(e) => handleChange(e)} placeholder="Email" />
                    {errors.email && <div className={styles.error}>{errors.email}</div>}
                </fieldset>

                <fieldset className={styles.fieldset}>
                    <input type="password" name="password" className={styles.input_field} ref={passwordField} onChange={(e) => handleChange(e)} placeholder="Password" />
                    {errors.password && <div className={styles.error}>{errors.password}</div>}
                </fieldset>

                <fieldset className={styles.fieldset}>
                    <input type="password" name="password_conf" className={styles.input_field} ref={passwordConfField} onChange={(e) => handleChange(e)} placeholder="Confirm Password" />
                    {errors.conf_password && <div className={styles.error}>{errors.conf_password}</div>}
                </fieldset>

                <div className={styles.container}>
                    <div className={styles.showPwd}>
                        <input type="checkbox" onClick={togglePasswordVisibility} className={styles.checkBox}/>
                        <p className={styles.checkBoxTxt}>{pwdToggled ? "Hide Password" : "Show Password"}</p>
                    </div>
                    <button className={styles.submitBtn} disabled={isLoading} type="submit">
                        <span>{isLoading ? "Loading..." : "Register"}</span>
                    </button>
                </div>
                <div className={styles.registerLink}>
                        <p className={styles.txt}>Already have an account? </p>
                        <Link href="/user/login" className={styles.link}>Login</Link>
                </div>
            </form>
        </div>
    )
}