import { useRouter } from "expo-router";
import { SafeAreaView, ScrollView, StatusBar, View } from "react-native";
import Header from "../../components/Header";
import IconButton from "../../components/IconButton";
import SignInForm from "../../components/SignInForm";


export default function LoginPage() {
    const router = useRouter();

    return (
        <SafeAreaView style={{ flex: 1 }}>
            <StatusBar backgroundColor={"white"} />
            <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
                <Header>FitCom</Header>
                <IconButton onPress={() => router.push('/')} iconName='home' iconSize={24} style={{ backgroundColor: "none" }} variant='surface' />
            </View>

            <ScrollView>
                <SignInForm />
            </ScrollView>
        </SafeAreaView>
    )
}