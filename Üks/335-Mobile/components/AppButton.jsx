import { Pressable, StyleSheet, Text } from 'react-native';

export default function AppButton({ onPress, children }) {
    return (
        <Pressable onPress={onPress} style={styles.btn}>
            <Text style={styles.txt}>{children}</Text>
        </Pressable>
    )
}

const styles = StyleSheet.create({
    btn: {
        width: 200,
        elevation: 8,
        backgroundColor: '#009ee3',
        borderRadius: 20,
        paddingVertical: 10,
        paddingHorizontal: 12,
        margin: 10,
    },
    txt: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#fff',
        alignSelf: 'center',
    },
});