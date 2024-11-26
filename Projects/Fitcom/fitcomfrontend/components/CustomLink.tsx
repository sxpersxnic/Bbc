import { useRouter } from "expo-router"
import { StyleSheet, Text } from "react-native"
import CustomColors from "../lib/CustomColors"
import { ReactPropsType } from "../lib/CustomTypes"

type CustomLinkProps = {
    href: string
} & ReactPropsType

export default function CustomLink({ children, style, href, ...props }: CustomLinkProps) {
    const router = useRouter()

    return <Text onPress={() => router.push(href)} style={{ ...defaultStyle.text, ...style }} {...props}>{children}</Text>
}

const defaultStyle = StyleSheet.create({
    text: {
        fontSize: 12,
        color: CustomColors.linkText
    }
})