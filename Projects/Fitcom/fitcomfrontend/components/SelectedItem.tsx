import { GestureResponderEvent, StyleSheet, View } from "react-native"
import { GarmentType } from "../lib/CustomTypes"
import CustomButton from "./CustomButton"
import DefaultText from "./DefaultText"

type SelectedItemPropsType = {
    allGarments: GarmentType[],
    deleteItem: (event: GestureResponderEvent) => void,
    itemKey: string
}


export default function SelectedItem({ allGarments, deleteItem, itemKey }: SelectedItemPropsType) {
    return (
        <View style={styles.itemContainer}>
            <DefaultText style={{ fontSize: 16, marginLeft: 8 }}>{allGarments.find(i => i.id == itemKey)?.name}</DefaultText>
            <CustomButton onPress={deleteItem} textStyle={{ color: "black" }} style={styles.deleteButton}>X</CustomButton>
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