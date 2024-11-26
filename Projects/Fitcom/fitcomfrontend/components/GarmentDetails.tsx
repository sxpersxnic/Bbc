import { useLocalSearchParams, useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { StyleSheet, View } from "react-native";
import { GarmentType } from "../lib/CustomTypes";
import { TestGarments } from "../lib/TestData";
import CustomButton from "./CustomButton";
import SubTitle from "./SubTitle";
import Title from "./Title";



export default function GarmentDetails() {
    const router = useRouter();
    const { id } = useLocalSearchParams();
    const [garment, setGarment] = useState<GarmentType>()

    useEffect(() => {
        if (id == undefined) return;

        const load = () => {
            setGarment(TestGarments.find(i => i.id == id));
        }
        load();
    }, [id])


    return (
        <View style={styles.container}>

            <View style={styles.titleContainer}>
                <Title>{garment?.name}</Title>
            </View>

            <SubTitle>Colour: {garment?.colour}</SubTitle>

            <SubTitle>Category: {garment?.category.toLowerCase()}</SubTitle>

            <CustomButton onPress={() => router.push(`/garments/${id}/edit`)}>Edit</CustomButton>
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