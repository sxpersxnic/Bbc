import { Text, StyleSheet } from 'react-native';

export default function Counter({ count }) {
    return <Text style={styles.txt}>{count}</Text>
}

const styles = StyleSheet.create({
    txt: {
        fontSize: 50,
        fontWeight: 'bold',
    },
});