import Checkbox from "expo-checkbox"
import { ReactPropsType } from "lib/CustomTypes"
import { StyleProp, StyleSheet, View } from "react-native"
import DefaultText from "./DefaultText"


type CustomCheckboxProps = {
    onChange?: (value: boolean) => void,
    textStyle?: StyleProp<any>,
    boxStyle?: StyleProp<any>,
    text?: string
    disabled?: boolean
    value?: boolean
} & ReactPropsType

export default function CustomCeckbox({ onChange, text, style, textStyle, boxStyle, disabled, value = true }: CustomCheckboxProps) {
    return (
        <View style={{ ...defaultStyles.container, ...style }}>
            <Checkbox
                disabled={disabled}
                value={value}
                onValueChange={onChange}
                style={{ ...defaultStyles.checkbox, ...boxStyle }}
            />
            <DefaultText style={{ ...textStyle }}>{text}</DefaultText>
        </View>
    )
}

const defaultStyles = StyleSheet.create({
    container: {
        flexDirection: "row",
        marginBottom: 16
    },

    checkbox: {
        marginRight: 8
    }
})