import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View } from 'react-native';
import { useState, useEffect } from 'react';

// Local
import Counter from './components/Counter';
import AppButton from './components/AppButton';
import NumberInput from './components/NumberInput';

export default function App() {
  const [countValue, setCountValue] = useState(0);
  const [maxValue, setMaxValue] = useState(null);
  const [valid, setValid] = useState(true);

  useEffect(() => {
    const isValid = maxValue === null || /^-?\d*$/.test(maxValue.toString());
    console.log("Ln18 | isValid: " + isValid);
    setValid(isValid);
    console.log("Ln20 | valid: " + valid);
  }, [maxValue]);
  
  const increase = () => {
    console.log("Ln24 | countValue: " + countValue + " maxValue: " + maxValue);
    if (maxValue === null || countValue < maxValue) {
      console.log("Ln26 | countValue before increase: " + countValue);
      setCountValue(countValue + 1);
      console.log("Ln28 | countValue after increase: " + countValue);
    } else if (countValue >= maxValue) {
      console.log("Ln30 | maxValue reached (countValue is either bigger or equal to maxValue) countValue: " + countValue + " maxValue: " + maxValue);
      setCountValue(maxValue);
    }
  }

  const decrease = () => {
    console.log("Ln36 | countValue before decrease: " + countValue);
      setCountValue(countValue - 1);
      console.log("Ln38 | countValue after decrease: " + countValue);
  }

  const changeMaxValue = (value) => {
    console.log("Ln42 | value: " + value)
    if (value === '' || value === null) {
      setMaxValue(null);
    } else if (/^-?\d*$/.test(value.toString())) {
      setMaxValue(parseInt(value));
    }
  }
  return (
    <View style={styles.cntr}>
      <Counter count={countValue}/>
      <NumberInput label="Maximum" value={maxValue === null ? '' : maxValue.toString()} onChangeText={changeMaxValue} style={[styles.input, !valid && styles.inputError]}/>
        <View style={styles.btnCntr}>
          <AppButton onPress={increase}>Increase</AppButton>
          <AppButton onPress={decrease}>Decrease</AppButton>
        </View>
      <StatusBar style="auto"/>
    </View>
  );
}

const styles = StyleSheet.create({
  cntr: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  btnCntr: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  input: {
    color: '#ff0000',
    fontSize: 15,
    fontWeight: 'italic'
  },
  inputError: {
    color: '#ff0000',
    fontSize: 15,
    fontWeight: 'italic'
  },
});
