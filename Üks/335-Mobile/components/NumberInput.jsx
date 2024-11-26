import { View, TextInput, StyleSheet, Text } from 'react-native';

export default function NumberInput({label, value, onChangeText}) {
    return (
        <View style={styles.cntr}>
            <Text style={styles.txt}>{label}</Text>
            <TextInput style={styles.inp} value={value === null ? value = '' : value.toString()} onChangeText={onChangeText} inputMode='numeric'  placeholder="Enter limit..."></TextInput>
        </View>
    )
}

const styles = StyleSheet.create({
    cntr: {
        width: 240, 
        margin: 15
    },
    inp: {
        height: 48,
        marginTop: 5,
        marginBottom: 5,
        fontSize: 24,
        borderWidth: 2,
        borderRadius: 10,
        padding: 10,
    },
    txt: {
        fontSize: 15,
        color: '#000',
    },
});