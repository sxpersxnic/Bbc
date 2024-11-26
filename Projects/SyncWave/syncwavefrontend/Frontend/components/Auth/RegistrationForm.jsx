import {useSession} from "../../lib/session";
import {useRouter} from "next/router";
import {useRef, useState} from "react";
import {register} from "../../lib/auth";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import {StyleClass} from 'primereact/styleclass';
import {Password} from "primereact/password";
import Link from "next/link";
import {Toast} from "primereact/toast";
import {Image} from "primereact/image";
import Logo from "../../resources/images/syncwaveLogo.svg";

const defaultModel = {
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
}
function validateModel(model) {
    const errors = {
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
    };

    let isValid = true;

    if (!model.username || model.username.split("").length === 0 || !new RegExp("^[A-Za-z0-9_-]\\w{3,255}$").test(model.username)) {
        errors.username = "Email/Username cannot be empty!";
        isValid = false;
    }

    if (!model.email || model.email.split("").length === 0 || !new RegExp("^(?!.*@.*@)(?!.*\\.\\.)(^[a-zA-Z0-9._%+-]{1,64}@)([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?:\\.[a-zA-Z]{2,255})?)$").test(model.email)) {
        errors.email = "Email/Username cannot be empty!";
        isValid = false;
    }

    console.log(model)
    if (!model.password || model.password.split("").length === 0 || new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&*!])[A-Za-z\d@#$%^&*!]{8,}$").test(model.password)) {
        errors.password = "Password cannot be empty!";
        isValid = false;
    }

    if (!model.confirmPassword || model.confirmPassword.split("").length === 0 || new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&*!])[A-Za-z\d@#$%^&*!]{8,}$").test(model.confirmPassword) || !model.password === model.confirmPassword) {
        errors.confirmPassword = "Password cannot be empty!";
        isValid = false;
    }

    return {errors, isValid};
}

export default function RegistrationForm() {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);
    const [model, setModel] = useState(defaultModel);
    const [errors, setErrors] = useState(defaultModel);
    const [failed, setFailed] = useState(false);
    const toast = useRef(null)

    const handleChange = (e) => {
        setErrors(defaultModel)
        setFailed(false);
        setModel({...model, [e.target.name]: e.target.value?.trim()});
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setErrors(defaultModel);

        const result = validateModel(model);
        console.log(result)
        if (!result.isValid) {
            setErrors(result.errors);
            if (result.errors.username !== "") {
                toast.current.show({severity: "error", summary: "Registration failed.", detail: "Username is invalid.", life: 3000});
            }
            if (result.errors.email !== "") {
                toast.current.show({severity: "error", summary: "Registration failed.", detail: "Email is invalid.", life: 3000});
            }
            if (result.errors.password !== "") {
                toast.current.show({severity: "error", summary: "Registration failed.", detail: "Password is invalid.", life: 3000});
            }
            if (result.errors.confirmPassword !== "") {
                toast.current.show({severity: "error", summary: "Registration failed.", detail: "The password is not the same.", life: 3000});
            }
            setIsLoading(false);
            return;
        }

        try {
            const response = await register({
                username: model.username,
                email: model.email,
                password: model.password,
                confirmPassword: model.confirmPassword
            });
            setIsLoading(false)
            alert("Registration successful")
            const url = router.query.url
            if (url) {
                await router.push(url)
            } else {
                await router.push("/")
            }
        } catch (e) {
            setIsLoading(false);
            if (e.status === 401) {
                setErrors({
                    username: "Invalid login",
                    email: "Invalid login",
                    password: "Invalid login",
                    confirmPassword: "Invalid login"
                });
            }
        }
    }

    return (
        <div className="surface-card p-4 shadow-2 border-round w-full lg:w-6">
            <Toast  ref={toast}/>
            <div className="text-center mb-5">
                <Image src={Logo.src} width="60px"/>
                <div className="text-900 text-3xl font-medium mb-3">Welcome</div>
                <span className="text-600 font-medium line-height-3">Already have an account?</span>
                <Link href={"/auth/login"} className="font-medium no-underline ml-2 text-blue-500 cursor-pointer">Login!</Link>
            </div>
            <div>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="username" className="block text-900 font-medium mb-2">Username</label>
                    <InputText id="username" type="text" placeholder="Username" className="w-full mb-3"
                               name={"username"} value={model.username} onChange={(e) => handleChange(e)} tooltip="Enter your Username" tooltipOptions={{ position: 'top' }}/>

                    <label htmlFor="email" className="block text-900 font-medium mb-2">Email</label>
                    <InputText id="email" type="text" placeholder="Email" className="w-full mb-3"
                               name={"email"} value={model.email} onChange={(e) => handleChange(e)} tooltip="Enter your Email" tooltipOptions={{ position: 'top' }}/>

                    <label htmlFor="password" className="block text-900 font-medium mb-2">Password</label>
                    <Password id={"password"} type="password" placeholder="Password" className="w-full mb-3" inputClassName="w-full"
                              value={model.password}
                              name={"password"} onChange={(e) => handleChange(e)} tooltip="Enter your Password" tooltipOptions={{ position: 'top' }}/>
                    <label htmlFor="confirmPassword" className="block text-900 font-medium mb-2">Confirm
                        password</label>
                    <InputText id={"confirmPassword"} type="password" placeholder="Confirm Password"
                               className="w-full mb-3" value={model.confirmPassword}
                               name={"confirmPassword"} onChange={(e) => handleChange(e)} tooltip="Confirm your Password" tooltipOptions={{ position: 'top' }}/>
                    {/*<Password placeholder={"Confirm password"} className="w-full mb-3" value={model.confirmPassword} name={"confirmPassword1"} onChange={(e) => handleChange(e)}/>*/}

                    <Button label="Sign Up" icon="pi pi-user" className="w-full"/>
                </form>
            </div>
        </div>
    )

}