import { StyleSheet } from "react-native"
import { ReactPropsType } from "../lib/CustomTypes"
import DefaultText from "./DefaultText"


export default function Title({ children, style, ...props }: ReactPropsType) {
    return <DefaultText style={{ ...defaultStyle.text, ...style }} {...props}>
        {children}
    </DefaultText>
}


const defaultStyle = StyleSheet.create({
    text: {
        fontSize: 20,
        fontWeight: "bold"
    }
})