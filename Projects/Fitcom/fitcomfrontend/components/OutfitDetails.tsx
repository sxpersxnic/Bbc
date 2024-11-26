import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { StyleSheet, View } from "react-native";
import { OutfitType } from "../lib/CustomTypes";
import { TestOutfits } from "../lib/TestData";
import CustomButton from "./CustomButton";
import SubTitle from "./SubTitle";
import Title from "./Title";



export default function OutfitDetails() {
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
        <View style={styles.container}>

            <View style={styles.titleContainer}>
                <Title>{outfit?.name}</Title>
            </View>

            <SubTitle>Style: {outfit?.styleCategory}</SubTitle>

            <CustomButton onPress={() => router.push(`/outfits/${id}/edit`)}>Edit</CustomButton>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        marginHorizontal: 16
    },

    titleContainer: {
        alignItems: "center"
    }
})