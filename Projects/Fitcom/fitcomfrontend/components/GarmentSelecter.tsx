import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { ScrollView, StyleSheet, TextInput, View } from "react-native";
import { GarmentType, OutfitType } from "../lib/CustomTypes";
import ECategory from "../lib/ECategory";
import Garment from "./Garment";


type GarmentSelecterPropTypes = {
    category: ECategory,
    allGarments: GarmentType[],
    setGarments: Dispatch<SetStateAction<OutfitType>>,
    setBrowse: Dispatch<SetStateAction<boolean>>,
    garments: OutfitType
}

export default function GarmentSelecter({ category, allGarments, setGarments, setBrowse, garments }: GarmentSelecterPropTypes) {
    const [searchQuery, setSearchQuery] = useState("");
    const garmentsOfCategory = allGarments.filter(garment => garment.category === category)
    const [filteredGarmentsOfCategory, setFilteredGarmentsOfCategory] = useState<GarmentType[]>(garmentsOfCategory)

    useEffect(() => {
        if (searchQuery.split("").length === 0) {
            setFilteredGarmentsOfCategory(garmentsOfCategory)
        } else {
            setFilteredGarmentsOfCategory(garmentsOfCategory.filter(garment => garment.name.toLowerCase().includes(searchQuery.toLowerCase())))
        }
    }, [searchQuery])


    const selectGarment = (id: string) => {
        switch (category) {
            case ECategory.TOP: setGarments({ ...garments, topId: id }); break;
            case ECategory.BOTTOM: setGarments({ ...garments, bottomId: id }); break;
            case ECategory.SHOES: setGarments({ ...garments, shoesId: id }); break;
            case ECategory.UNDERWEAR: setGarments({ ...garments, underwearId: id }); break;
            case ECategory.SOCKS: setGarments({ ...garments, socksId: id }); break;
            case ECategory.BELT: setGarments({ ...garments, beltId: id }); break;
            case ECategory.RING: setGarments({ ...garments, ringsId: [...garments.ringsId || [], id] }); break;
            case ECategory.EARRING: setGarments({ ...garments, earringsId: [...garments.earringsId || [], id] }); break;
            case ECategory.HEADWEAR: setGarments({ ...garments, headwearId: [...garments.headwearId || [], id] }); break;
            case ECategory.JACKET: setGarments({ ...garments, jacketId: id }); break;
            case ECategory.BAG: setGarments({ ...garments, bagsId: [...garments.bagsId || [], id] }); break;
            case ECategory.GLOVE: setGarments({ ...garments, glovesId: [...garments.glovesId || [], id] }); break;
            case ECategory.BRACELET: setGarments({ ...garments, braceletsId: [...garments.braceletsId || [], id] }); break;
            case ECategory.NECKLACE: setGarments({ ...garments, necklacesId: [...garments.necklacesId || [], id] }); break;
        }
        setBrowse(false);
    }


    return (
        <View style={styles.container}>
            <TextInput placeholder="Search..." value={searchQuery} onChangeText={(v) => setSearchQuery(v)} />

            <ScrollView style={{ ...styles.container, height: 160 }} nestedScrollEnabled={true}>
                <View style={{ flexWrap: "wrap", flexDirection: "row", justifyContent: "center" }}>
                    {
                        filteredGarmentsOfCategory.map((garment, i) => {
                            return (
                                <Garment onPress={() => selectGarment(garment.id)} name={garment.name} colour={garment.colour} category={garment.category} id={garment.id} key={i} style={{ margin: 8 }} />
                            )
                        })
                    }
                </View>

            </ScrollView>
        </View>
    )

}

const styles = StyleSheet.create({
    container: {
        borderRadius: 8,
        borderWidth: 1
    }
})