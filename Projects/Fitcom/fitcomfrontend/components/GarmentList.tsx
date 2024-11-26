import { useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet, TextInput, View } from "react-native";
import ECategory from "../lib/ECategory";
import { TestGarments } from "../lib/TestData";
import Garment from "./Garment";


type GarmentType = {
    name: string,
    colour: string,
    category: ECategory,
    id: string
}

export default function GarmentList() {
    const [garments, setGarments] = useState<GarmentType[]>(TestGarments)
    const router = useRouter();
    const [searchQuery, setSearchQuery] = useState("");
    const [filteredGarments, setFilteredGarments] = useState<GarmentType[]>(TestGarments);

    // const loadGarments = async() => {
    //     const response = await getAllGarments();
    //     setGarments(response);
    // }

    // useEffect(() => {
    //     loadGarments()
    // }, [])

    useEffect(() => {
        if (searchQuery.split("").length === 0) {
            setFilteredGarments(TestGarments)
        } else {
            setFilteredGarments(TestGarments.filter(garment => garment.name.toLowerCase().includes(searchQuery.toLowerCase())))
        }
    }, [searchQuery])



    return (
        <View style={{ marginHorizontal: 16 }}>
            <TextInput placeholder="Search..." value={searchQuery} onChangeText={(v) => setSearchQuery(v)} />

            <ScrollView>
                <View style={defaultStyle.container}>
                    {
                        filteredGarments.map((garment, i) => {
                            return (
                                <Garment onPress={() => router.push("/garments/" + garment.id)} name={garment.name} colour={garment.colour} category={garment.category} id={garment.id} key={i} style={{ margin: 12 }} />
                            )
                        })
                    }
                </View>
            </ScrollView>
        </View>
    )
}

const defaultStyle = StyleSheet.create({
    container: {
        flexWrap: "wrap",
        flexDirection: "row",
        justifyContent: "center"
    }
})