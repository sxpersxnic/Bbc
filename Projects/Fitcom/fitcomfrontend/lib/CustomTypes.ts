import { ReactNode } from "react"
import { StyleProp } from "react-native"
import CustomColors from "./CustomColors"
import ECategory from "./ECategory"

export type ReactPropsType = {
    children?: ReactNode | string,
    style?: StyleProp<any>,
    variant?: keyof typeof CustomColors
}

export type GarmentType = {
    name: string,
    colour: string,
    category: ECategory,
    id: string
}

export type OutfitType = {
    id?: string,
    name: string,
    styleCategory: string,
    topId: string,
    bottomId: string,
    shoesId: string,
    underwearId: string,
    socksId?: string,
    beltId?: string,
    ringsId?: string[],
    earringsId?: string[],
    headwearId?: string[],
    jacketId?: string,
    bagsId?: string[],
    glovesId?: string[],
    braceletsId?: string[],
    necklacesId?: string[]
}

export type OutfitPreviewType = {
    name: string,
    id: string,
    styleCategory: string
}

export type UserType = {
    id: string,
    username: string,
    email: string,
    password: string,
    createdTime: string,
    updatedTime?: string
}