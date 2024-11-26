import { useState } from 'react';
import { StyleSheet, TextInput, View } from 'react-native';
import EHttpStatus from '../lib/api/EHttpStatus';
import { login } from '../lib/api/auth';
import { useSession } from "../lib/hooks/session";
import CustomButton from './CustomButton';
import CustomCeckbox from './CustomCeckbox';
import CustomLink from './CustomLink';
import DefaultText from './DefaultText';
import SubTitle from './SubTitle';
import Title from './Title';
import { router } from 'expo-router';

type Model = {
  identification: string;
  password: string;
}

const defaultModel: Model = {
  identification: "",
  password: "",
};

type ErrorModel = {
  identification?: string;
  password?: string;
  login?: string; 
}

function validateModel(creds: Model) {
  const errors: ErrorModel = {
    identification: "",
    password: ""
  };

  let isValid = true;

  if (creds.identification.length == 0 || creds.identification == undefined) {
    errors.identification = "Email/Username can't be empty!";
    isValid = false;
  }

  if (creds.password.length == 0 || creds.password == undefined) {
    errors.password = "Password can't be empty!";
    isValid = false;
  }

  return { errors, isValid }
};

export default function SignInForm() {
  const { session, signIn } = useSession()
  const [isLoading, setIsLoading] = useState(false);
  const [creds, setCreds] = useState<Model>(defaultModel);
  const [errors, setErrors] = useState<ErrorModel>(defaultModel);
  const [pwdToggled, setPwdToggled] = useState(false);

  const togglePasswordVisibility = () => {
    if (!pwdToggled) {
      setPwdToggled(true)
    } else {
      setPwdToggled(false)
    }
  }

  const signInUser = async () => {
    if (!new RegExp(/^(?!.*@.*@)(?!.*\.\.)(^[a-zA-Z0-9._%+-]{1,64}@)([a-zA-Z0-9.-]+\.[a-zA-Z]{2,}(?:\.[a-zA-Z]{2,256})?)$/gm).test(creds.identification)) {
      return await login({ username: creds.identification, password: creds.password });
    } else {
      return await login({ email: creds.identification, password: creds.password });
    }
  };

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    setIsLoading(true);
    setErrors(defaultModel);

    const result = validateModel(creds);

    if (!result.isValid) {
      setErrors(result.errors);
      setIsLoading(false);
      return;
    }

    try {
      const validation = await signInUser();
      if (EHttpStatus.CONFLICT) {
        setErrors({ identification: "Invalid signin", password: "Invalid signin" });
        setIsLoading(false);
        return;
      }

      signIn({ token: validation.token, user: validation.user });
      setErrors(defaultModel);

    } catch (error) {
      setErrors({
        ...errors,
        login: "Login failed"
      });
      setIsLoading(false);
      router.replace("/user/2/profile")
    }
  };

  return session.user ? null : (
    <View style={styles.container}>

      <View style={styles.titleContainer}>
        <Title>Login</Title>
        {errors.login && <SubTitle style={{ ...styles.error, marginTop: 0 }} >{errors.login}</SubTitle>}
      </View>

      <DefaultText>Email/Username</DefaultText>
      <TextInput
        style={styles.input}
        value={creds.identification}
        placeholder="Email/Username"
        onChangeText={(v) => setCreds({ ...creds, identification: v })}
      />
      {errors.identification && <DefaultText style={styles.error}>{errors.identification}</DefaultText>}


      <DefaultText>Password</DefaultText>
      <TextInput
        style={styles.input}
        value={creds.password}
        placeholder="Password"
        onChangeText={(v) => setCreds({ ...creds, password: v })}
        secureTextEntry={pwdToggled}
      />
      {errors.password && <DefaultText style={styles.error}>{errors.password}</DefaultText>}

      <CustomCeckbox value={pwdToggled} onChange={togglePasswordVisibility} text={"Show Password"} />

      <CustomButton onPress={handleSubmit} disabled={isLoading} >{isLoading ? "Pending" : "Sign In"}</CustomButton>

      <View style={styles.linkContainer}>
        <CustomLink href='/user/register'>Don't have an account?</CustomLink>
      </View>


    </View>
  )
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