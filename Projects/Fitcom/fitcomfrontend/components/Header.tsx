import { StyleProp, StyleSheet, View } from "react-native";
import { ReactPropsType } from "../lib/CustomTypes";
import DefaultText from "./DefaultText";

type HeaderProps = {
    textStyle?: StyleProp<any>,
    title?: string
} & ReactPropsType

export default function Header({ style, textStyle, children }: HeaderProps) {
    return (
        <View style={{ ...defaultStyle.view, ...style }}>
            <DefaultText style={{ ...defaultStyle.text, ...textStyle }}>
                {children}
            </DefaultText>
        </View>
    )
}

const defaultStyle = StyleSheet.create({
    text: {
        fontSize: 22,
        fontWeight: "bold",
        paddingLeft: 16
    },

    view: {
        height: 48,
        alignContent: "center"
    }
})