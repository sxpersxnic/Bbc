import { Dispatch, SetStateAction } from "react"
import { StyleSheet, View } from "react-native"
import { GarmentType, OutfitType } from "../lib/CustomTypes"
import ECategory from "../lib/ECategory"
import SelectedItem from "./SelectedItem"

type SelectedListPropsType = {
    allGarments: GarmentType[],
    idList: string[] | undefined,
    setGarments: Dispatch<SetStateAction<OutfitType>>,
    garments: OutfitType,
    category: ECategory.RING | ECategory.EARRING | ECategory.HEADWEAR | ECategory.BAG | ECategory.GLOVE | ECategory.BRACELET | ECategory.NECKLACE
}

export default function SelectedList({ allGarments, idList, setGarments, garments, category }: SelectedListPropsType) {

    const deleteItem = (index: number) => {
        switch (category) {
            case ECategory.RING: setGarments({ ...garments, ringsId: garments.ringsId?.filter((_, i) => i !== index) }); break;
            case ECategory.EARRING: setGarments({ ...garments, earringsId: garments.earringsId?.filter((_, i) => i !== index) }); break;
            case ECategory.HEADWEAR: setGarments({ ...garments, headwearId: garments.headwearId?.filter((_, i) => i !== index) }); break;
            case ECategory.BAG: setGarments({ ...garments, bagsId: garments.bagsId?.filter((_, i) => i !== index) }); break;
            case ECategory.GLOVE: setGarments({ ...garments, glovesId: garments.glovesId?.filter((_, i) => i !== index) }); break;
            case ECategory.BRACELET: setGarments({ ...garments, braceletsId: garments.braceletsId?.filter((_, i) => i !== index) }); break;
            case ECategory.NECKLACE: setGarments({ ...garments, necklacesId: garments.necklacesId?.filter((_, i) => i !== index) }); break;
        }
    }

    return (
        <View style={styles.container}>
            {
                idList?.map((item, i) => {
                    return (
                        <SelectedItem allGarments={allGarments} deleteItem={() => deleteItem(idList.indexOf(item))} itemKey={item} />
                    )
                })
            }
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        borderWidth: 1,
        marginVertical: 8,
        borderColor: "green",
        borderRadius: 4
    },

    itemContainer: {
        borderBottomWidth: 1,
        height: 40,
        alignItems: "center",
        flexDirection: "row",
        justifyContent: "space-between"
    },

    deleteButton: {
        borderRadius: 0,
        width: 36,
        backgroundColor: "none",
        margin: 0
    }
})