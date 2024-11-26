import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { SafeAreaView, StatusBar, View } from "react-native";
import GarmentForm from "../../../components/GarmentForm";
import Header from "../../../components/Header";
import IconButton from "../../../components/IconButton";
import { GarmentType } from "../../../lib/CustomTypes";
import { TestGarments } from "../../../lib/TestData";



export default function EditGarmentpage() {
    const router = useRouter();
    const { id } = useLocalSearchParams();
    const [garment, setGarment] = useState<GarmentType>();

    useEffect(() => {
        if (id == undefined) return;

        const load = () => {
            setGarment(TestGarments.find(i => i.id == id))
        }
        load();
    }, [id])

    return (
        <SafeAreaView>
            <StatusBar backgroundColor={"white"} />
            <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
                <Header>FitCom</Header>
                <IconButton onPress={() => router.push('/')} iconName='home' iconSize={24} style={{ backgroundColor: "none" }} variant='surface' />

            </View>

            <GarmentForm garmentToEdit={garment} />
        </SafeAreaView>
    )
}