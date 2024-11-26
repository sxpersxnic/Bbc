// CustomButton.jsx
import React from 'react';
import { GestureResponderEvent, StyleProp, StyleSheet, TouchableOpacity } from 'react-native';
import CustomColors from '../lib/CustomColors';
import { ReactPropsType } from '../lib/CustomTypes';
import { capitaliseFirstLetter } from '../lib/help';
import DefaultText from './DefaultText';


type CustomButtonProps = {
  onPress?: (event: GestureResponderEvent) => void,
  textStyle?: StyleProp<any>,
  disabled?: boolean | undefined
} & ReactPropsType

export default function CustomButton({ children, style, variant, onPress, textStyle, disabled }: CustomButtonProps) {

  return (
    <TouchableOpacity onPress={onPress} style={{ backgroundColor: CustomColors[variant ? variant : 'primary'], ...styles.container, ...style }} disabled={disabled}>
      <DefaultText style={{ color: CustomColors[variant ? "on" + capitaliseFirstLetter(variant) as keyof typeof CustomColors : 'onPrimary'], ...textStyle }}>{children}</DefaultText>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  container: {
    borderRadius: 50,
    height: 36,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 8,
    margin: 8
  },
});