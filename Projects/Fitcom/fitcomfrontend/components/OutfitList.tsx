import { getAllOutfits } from "lib/api/outfits";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet, TextInput, View } from "react-native";
import Outfit from "./Outfit";
import { OutfitPreviewType, OutfitType } from "lib/CustomTypes";
import { TestOutfits } from "../lib/TestData";
import { useRouter } from "expo-router";

export default function OutfitList() {
    const [outfits, setOutfits] = useState<OutfitType[]>(TestOutfits)
    const router = useRouter();
    const [searchQuery, setSearchQuery] = useState("");
    const [filteredOutfits, setFilteredOutfits] = useState<OutfitType[]>(TestOutfits);

    // const loadOutfits = async() => {
    //     const response = await getAllOutfits();
    //     setOutfits(response);
    // }

    // useEffect(() => {
    //     loadOutfits()
    // }, [])

    useEffect(() => {
        if (searchQuery.split("").length === 0) {
            setFilteredOutfits(TestOutfits)
        } else {
            setFilteredOutfits(TestOutfits.filter(outfit => outfit.name.toLowerCase().includes(searchQuery.toLowerCase())))
        }
    }, [searchQuery])



    return (
        <View style={{ marginHorizontal: 16 }}>
            <TextInput placeholder="Search..." value={searchQuery} onChangeText={(v) => setSearchQuery(v)} />

            <ScrollView>
                <View style={defaultStyle.container}>
                    {
                        filteredOutfits.map((outfit, i) => {
                            return (
                                <Outfit name={outfit.name} styleCategory={outfit.styleCategory} id={outfit.id as string} key={i} style={{ margin: 12}} />
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