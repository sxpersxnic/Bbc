import {useSession} from "../../lib/session";
import {useRouter} from "next/router";
import {useEffect, useRef, useState} from "react";
import {login} from "../../lib/auth";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import Link from "next/link";
import Logo from '../../resources/images/syncwaveLogo.svg';
import {Image} from "primereact/image";
import {Toast} from "primereact/toast";


const defaultModel = {
    principal: "",
    password: "",
}

function validateModel(model) {
    const errors = {
        principal: "",
        password: "",
    };

    let isValid = true;

    if (!model.principal || model.principal.split("").length === 0) {
        errors.principal = "Email/Username cannot be empty!";
        isValid = false;
    }

    if (!model.password || model.password.split("").length === 0) {
        errors.password = "Password cannot be empty!";
        isValid = false;
    }

    return {errors, isValid};
}

export default function LoginForm() {
    const {session, signIn} = useSession();
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);
    const [model, setModel] = useState(defaultModel);
    const [errors, setErrors] = useState(defaultModel);
    const user = session.user;
    const [failed, setFailed] = useState(false);
    const toast = useRef(null);

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
            setIsLoading(false);
            toast.current.show({
                severity: "error",
                summary: "Login failed.",
                detail: "Principal or Password is invalid.",
                life: 3000
            });
            return;
        }

        try {
            const response = await login({principal: model.principal, password: model.password});
            setIsLoading(false)
            window.localStorage.setItem("token", response.token)
            signIn({token: response.token, user: response})
            const url = router.query.url
            if (url) {
                await router.push(url)
            } else {
                await router.push("/")
            }
        } catch (e) {
            setIsLoading(false);
            if (e.status === 401) {
                setErrors({principal: "Invalid login", password: "Invalid login"});
            }
            if (e.status === 404) {
                toast.current.show({
                    severity: "error",
                    summary: "Login failed.",
                    detail: "Principal or Password is wrong.",
                    life: 3000
                });
            }
        }
    }

    return (
        <div className="surface-card p-4 shadow-2 border-round w-full lg:w-6 h-fit">
            <Toast ref={toast}/>
            <div className="text-center mb-5">
                <Image src={Logo.src} width="60px"/>
                <div className="text-900 text-3xl font-medium mb-3">Welcome Back</div>
                <span className="text-600 font-medium line-height-3">Don't have an account?</span>
                <Link className="font-medium no-underline ml-2 text-blue-500 cursor-pointer" href={"/auth/register"}>Create
                    today!</Link>
            </div>
            <div>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="principal" className="block text-900 font-medium mb-2">Principal</label>
                    <InputText id="principal" type="text" placeholder="Principal" className="w-full mb-3"
                               name={"principal"} value={model.principal} onChange={(e) => handleChange(e)}
                               tooltip="Enter your Email or Username" tooltipOptions={{position: 'top'}}/>

                    <label htmlFor="password" className="block text-900 font-medium mb-2">Password</label>
                    <InputText type="password" placeholder="Password" className="w-full mb-3" value={model.password}
                               name={"password"} onChange={(e) => handleChange(e)} tooltip="Enter your Password"
                               tooltipOptions={{position: 'top'}}/>

                    {/*<div className="flex align-items-center justify-content-between mb-6">*/}
                    {/*    <a className="font-medium no-underline ml-2 text-blue-500 text-right cursor-pointer">Forgot your*/}
                    {/*        password?</a>*/}
                    {/*</div>*/}
                    <Button label="Sign In" icon="pi pi-user" className="w-full"/>
                </form>
            </div>
        </div>
    );
}
