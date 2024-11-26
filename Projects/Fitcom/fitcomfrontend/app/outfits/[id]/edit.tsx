import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { SafeAreaView, ScrollView, StatusBar, View } from "react-native";
import Header from "../../../components/Header";
import IconButton from "../../../components/IconButton";
import OutfitForm from "../../../components/OutfitForm";
import { OutfitType } from "../../../lib/CustomTypes";
import { TestOutfits } from "../../../lib/TestData";



export default function EditOutfitPage() {
    const router = useRouter();
    const { id } = useLocalSearchParams();
    const [outfit, setOutfit] = useState<OutfitType>();

    useEffect(() => {
        if (id == undefined) return;

        const load = () => {
            setOutfit(TestOutfits.find(i => i.id == id))
        }
        load()
    }, [id])

    return (
        <SafeAreaView>
            <StatusBar backgroundColor={"white"} />
            <View style={{ flexDirection: "row", justifyContent: "space-between" }}>
                <Header>FitCom</Header>
                <IconButton onPress={() => router.push('/')} iconName='home' iconSize={24} style={{ backgroundColor: "none" }} variant='surface' />

            </View>

            <ScrollView>
                <OutfitForm outfitToEdit={outfit} />
            </ScrollView>
        </SafeAreaView>
    )
}