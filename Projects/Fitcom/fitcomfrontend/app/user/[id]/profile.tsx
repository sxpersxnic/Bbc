import { MaterialIcons } from "@expo/vector-icons";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { Alert, SafeAreaView, StatusBar, StyleSheet, View } from "react-native";
import CustomButton from "../../../components/CustomButton";
import DefaultText from "../../../components/DefaultText";
import Header from "../../../components/Header";
import IconButton from "../../../components/IconButton";
import SubTitle from "../../../components/SubTitle";
import Title from "../../../components/Title";
import { UserType } from "../../../lib/CustomTypes";
import { TestUsers } from "../../../lib/TestData";
import { deleteUser } from "../../../lib/api/auth";
import { useSession } from "../../../lib/hooks/session";


export default function ProfilePage() {
    const router = useRouter();
    const { id } = useLocalSearchParams();
    const [user, setUser] = useState<UserType>();
    const { session, signOut } = useSession();

    useEffect(() => {
        if (id == undefined) return;

        const load = () => {
            setUser(TestUsers.find(i => i.id == id))
        }
        load()
    }, [id])


    const logoutCurrentUser = () => {
        signOut()
        router.replace("/")
    }

    const deleteCurrentUser = () => {
        Alert.alert(
            "Are you sure?",
            "You won't be able to recover your account later",
            [
                {
                    text: "Cancel",
                    onPress: () => { return; }
                },
                {
                    text: "Ok",
                    onPress: () => {
                        deleteUser(id as string);
                        signOut();
                        router.replace("/")
                    }
                }
            ]
        )
    }

    return (
        <SafeAreaView style={{ flex: 1 }}>
            <StatusBar backgroundColor={"white"} />
            <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
                <Header>FitCom</Header>
                <IconButton onPress={() => router.push('/')} iconName='home' iconSize={24} style={{ backgroundColor: "none" }} variant='surface' />
            </View>

            <View style={{ marginHorizontal: 16 }}>
                <View style={styles.titleContainer}>
                    <MaterialIcons name="account-circle" size={64} />
                    <Title>Profile</Title>
                </View>

                <View>
                    <SubTitle>Username</SubTitle>
                    <DefaultText style={styles.text}>{user?.username}</DefaultText>

                    <SubTitle>Email</SubTitle>
                    <DefaultText style={styles.text}>{user?.email}</DefaultText>

                    <SubTitle>Created At</SubTitle>
                    <DefaultText style={styles.text}>{user?.createdTime}</DefaultText>
                </View>

                <CustomButton onPress={logoutCurrentUser}>Logout</CustomButton>

                <CustomButton onPress={deleteCurrentUser}>Delete Account</CustomButton>

            </View>

        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    titleContainer: {
        alignItems: "center",
        marginBottom: 18
    },

    text: {
        fontSize: 16,
        marginBottom: 16
    }
})