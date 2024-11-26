import { useRouter } from "expo-router";
import { StyleSheet, TouchableOpacity } from "react-native";
import CustomColors from "../lib/CustomColors";
import { OutfitPreviewType, ReactPropsType } from "../lib/CustomTypes";
import { capitaliseFirstLetter } from "../lib/help";
import DefaultText from "./DefaultText";

export default function Outfit({ name, id, styleCategory, style, variant, ...props }: OutfitPreviewType & ReactPropsType) {
    const router = useRouter();

    return (
        <TouchableOpacity onPress={() => router.push('/outfits/' + id)} style={{
            backgroundColor: CustomColors[variant ? variant : 'preview'],
            ...defaultStyle.container,
            ...style
        }} {...props}>
            <DefaultText style={{ color: CustomColors[variant ? "on" + capitaliseFirstLetter(variant) as keyof typeof CustomColors : 'onPreview'] }}>{name}</DefaultText>
            <DefaultText style={{ color: CustomColors[variant ? "on" + capitaliseFirstLetter(variant) as keyof typeof CustomColors : 'onPreview'] }}>{styleCategory}</DefaultText>
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