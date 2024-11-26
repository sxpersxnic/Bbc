// IconButton.tsx
import { MaterialIcons } from '@expo/vector-icons';
import React from 'react';
import { GestureResponderEvent, StyleSheet, TouchableOpacity } from 'react-native';
import CustomColors from '../lib/CustomColors';
import { ReactPropsType } from '../lib/CustomTypes';
import DefaultText from './DefaultText';


type IconButtonProps = {
  text?: string
  iconName: keyof typeof MaterialIcons.glyphMap
  iconSize?: number
  onPress?: (event: GestureResponderEvent) => void
} & ReactPropsType

export default function IconButton({
  text,
  iconName,
  iconSize,
  style,
  variant,
  onPress
}: IconButtonProps) {
  const capitalizeFirstLetter = (string: string) => {
    return string.charAt(0).toUpperCase() + string.slice(1);
  };

  const textColor = CustomColors[variant ? "on" + capitalizeFirstLetter(variant) as keyof typeof CustomColors : 'onPrimary'];

  return (
    <TouchableOpacity onPress={onPress} style={{ backgroundColor: CustomColors[variant ? variant : 'primary'], ...styles.container, ...style }}>
      <MaterialIcons name={iconName} size={iconSize} color={textColor} />
      <DefaultText style={{ marginLeft: 8, color: textColor }}>{text}</DefaultText>
    </TouchableOpacity>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    height: 48,
    borderRadius: 12,
    padding: 8,
  },
});