import { useRouter } from 'expo-router';
import React, { useState } from "react";
import { StyleSheet, TextInput, View } from "react-native";
import EHttpStatus from '../lib/api/EHttpStatus';
import { signUp } from "../lib/api/auth";
import CustomButton from "./CustomButton";
import CustomCeckbox from "./CustomCeckbox";
import CustomLink from "./CustomLink";
import DefaultText from "./DefaultText";
import SubTitle from "./SubTitle";
import Title from "./Title";

type Model = {
    username: string;
    email: string;
    password: string;
    conf_password: string;
}

const defaultModel: Model = {
    username: "",
    email: "",
    password: "",
    conf_password: ""
}

type ErrorModel = {
    register?: string
} & Model

const validateModel = (creds: Model) => {
    const errors: ErrorModel = {
        username: "",
        email: "",
        password: "",
        conf_password: ""
    };

    let isValid = true;

    if (creds.username.split("").length == 0 || creds.username == undefined) {
        errors.username = "Username can't be empty!";
        isValid = false;
    }
    else if (!new RegExp(/^[A-Za-z]{1,255}$/gm).test(creds.username)) {
        errors.username = "Invalid username!"
    }
    if (creds.email.split("").length == 0 || creds.email == undefined) {
        errors.email = "Email can't be empty and must be valid Email!";
        isValid = false;
    }
    else if (!new RegExp(/^(?!.*@.*@)(?!.*\.\.)(^[a-zA-Z0-9._%+-]{1,64}@)([a-zA-Z0-9.-]+\.[a-zA-Z]{2,}(?:\.[a-zA-Z]{2,256})?)$/gm).test(creds.email)) {
        errors.email = "Invalid email!"
    }

    if (creds.password.split("").length == 0 || creds.password == undefined) {
        errors.password = "Password can't be empty and must be at least 8 characters long!";
        isValid = false;
    }
    else if (!new RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&*!])[A-Za-z\d@#\$%\^&\*!]{8,}$/gm).test(creds.password)) {
        errors.password = "Password invalid! Please use a strong password.";
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

    return { isValid, errors }
}

export default function SignUpForm() {
    const [isLoading, setIsLoading] = useState(false);
    const router = useRouter();

    const [pwdToggled, setPwdToggled] = useState(false);
    const [confPwdToggled, setConfPwdToggled] = useState(false);
    const [creds, setCreds] = useState<Model>(defaultModel);
    const [errors, setErrors] = useState<ErrorModel>(defaultModel);

    const togglePasswordVisibility = () => {
        if (!pwdToggled) {
            setPwdToggled(true)
        }
        else {
            setPwdToggled(false)
        }
    }

    const togglePasswordConfVisibility = () => {
        if (!confPwdToggled) {
            setConfPwdToggled(true)
        }
        else {
            setConfPwdToggled(false)
        }
    }

    const signUpUser = async () => {
        return signUp({ username: creds.username, email: creds.email, password: creds.password });
    }
    const handleSubmit = async () => {
        setIsLoading(true)
        setErrors(defaultModel);

        const result = validateModel(creds);

        if (!result.isValid) {
            setErrors(result.errors)
            setIsLoading(false)
            return
        }

        try {
            const availability = await signUpUser();
            if (availability == EHttpStatus.CONFLICT) {
                setErrors({ ...errors, username: "There already is an account with your given email or username" });
                setIsLoading(false);
                return;
            }
            setIsLoading(false);
            router.replace('/')
        } catch (error) {
            setErrors({ ...errors, register: "Register failed" });
            setIsLoading(false);
            router.replace("/user/login")
        }
    }

    return (
        <View style={styles.container}>

            <View style={styles.titleContainer}>
                <Title>Register</Title>
                {errors.register && <SubTitle style={{ ...styles.error, marginTop: 0 }} >{errors.register}</SubTitle>}
            </View>

            <DefaultText>Username</DefaultText>
            <TextInput
                style={styles.input}
                value={creds.username}
                placeholder="Username"
                onChangeText={(v) => setCreds({ ...creds, username: v })}
            />
            {errors.username && <DefaultText style={styles.error}>{errors.username}</DefaultText>}

            <DefaultText>Email</DefaultText>
            <TextInput
                style={styles.input}
                value={creds.email}
                placeholder="Email"
                onChangeText={(v) => setCreds({ ...creds, email: v })}
            />
            {errors.email && <DefaultText style={styles.error}>{errors.email}</DefaultText>}

            <DefaultText>Password</DefaultText>

            <TextInput
                style={styles.input}
                value={creds.password}
                placeholder="Password"
                onChangeText={(v) => setCreds({ ...creds, password: v })}
                secureTextEntry={!pwdToggled}
            />
            {errors.password && <DefaultText style={styles.error}>{errors.password}</DefaultText>}


            <CustomCeckbox value={pwdToggled} onChange={togglePasswordVisibility} text={"Show Password"} />

            <DefaultText>Confirm Password</DefaultText>
            <TextInput
                style={styles.input}
                value={creds.conf_password}
                placeholder="Confirm Password"
                onChangeText={(v) => setCreds({ ...creds, conf_password: v })}
                secureTextEntry={!confPwdToggled}
            />
            {errors.conf_password && <DefaultText style={styles.error}>{errors.conf_password}</DefaultText>}

            <CustomCeckbox value={confPwdToggled} onChange={togglePasswordConfVisibility} text={"Show Password"} />

            <CustomButton onPress={handleSubmit} disabled={isLoading} >{isLoading ? "Pending" : "Sign Up"}</CustomButton>

            <View style={styles.linkContainer}>
                <CustomLink href="/user/login">Already have an account?</CustomLink>
            </View>

        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        marginLeft: 16,
        marginRight: 16
    },

    input: {
        height: 40,
        borderWidth: 1,
        marginVertical: 8
    },

    error: {
        color: 'red',
        marginTop: -8,
        marginBottom: 8
    },

    linkContainer: {
        alignItems: "center",
        marginVertical: 16
    },

    titleContainer: {
        alignItems: "center"
    }
});
