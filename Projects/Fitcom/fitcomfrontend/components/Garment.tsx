import { GestureResponderEvent, StyleSheet, TouchableOpacity } from "react-native";
import CustomColors from "../lib/CustomColors";
import { GarmentType, ReactPropsType } from "../lib/CustomTypes";
import { capitaliseFirstLetter } from "../lib/help";
import DefaultText from "./DefaultText";

type GarmentPreviewPropTypes = {
    onPress?: (event: GestureResponderEvent) => void
} & GarmentType & ReactPropsType

export default function Garment({ onPress, name, colour, category, id, style, variant, ...props }: GarmentPreviewPropTypes) {
    return (
        <TouchableOpacity onPress={onPress} style={{
            backgroundColor: CustomColors[variant ? variant : 'preview'],
            ...defaultStyle.container,
            ...style
        }} {...props}>
            <DefaultText style={{ color: CustomColors[variant ? "on" + capitaliseFirstLetter(variant) as keyof typeof CustomColors : 'onPreview'] }}>{name}</DefaultText>
            <DefaultText style={{ color: CustomColors[variant ? "on" + capitaliseFirstLetter(variant) as keyof typeof CustomColors : 'onPreview'] }}>{category.toLowerCase()}</DefaultText>
            <DefaultText style={{ color: CustomColors[variant ? "on" + capitaliseFirstLetter(variant) as keyof typeof CustomColors : 'onPreview'] }}>{colour}</DefaultText>
        </TouchableOpacity>
    )
}

const defaultStyle = StyleSheet.create({
    container: {
        borderRadius: 8,
        height: 64,
        width: 64,
        padding: 8,
        justifyContent: "center",
        flexDirection: "column"
    }
})