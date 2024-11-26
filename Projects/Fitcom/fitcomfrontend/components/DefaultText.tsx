import { StyleSheet, Text } from "react-native";
import { ReactPropsType } from "../lib/CustomTypes";

export default function DefaultText({ style, children, ...props }: ReactPropsType) {
    return <Text style={{ ...defaultStyle.text, ...style }} {...props}>{children}</Text>
}

const defaultStyle = StyleSheet.create({
    text: {
        fontSize: 12
    }
})