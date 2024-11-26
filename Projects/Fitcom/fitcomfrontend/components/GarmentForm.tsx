import { Picker } from "@react-native-picker/picker"
import { useRouter } from "expo-router"
import { useEffect, useState } from "react"
import { StyleSheet, TextInput, View } from "react-native"
import { GarmentType } from "../lib/CustomTypes"
import ECategory from "../lib/ECategory"
import { createGarment, updateGarment } from "../lib/api/garments"
import { useSession } from "../lib/hooks/session"
import CustomButton from "./CustomButton"
import DefaultText from "./DefaultText"
import SubTitle from "./SubTitle"
import Title from "./Title"
import { TestGarments } from "../lib/TestData"

type Model = {
    name: string,
    colour: string,
    category: ECategory
}

const defaultModel: Model = {
    name: "",
    colour: "",
    category: ECategory.NONE
}

type ErrorModel = {
    name: string,
    colour: string,
    category: string,
    create?: string
}

const errorModel: ErrorModel = {
    name: "",
    colour: "",
    category: ""
}

function validateModel(garment: Model) {
    const errors: ErrorModel = {
        name: "",
        colour: "",
        category: ""
    }
    let isValid = true

    if (garment.name.split("").length === 0 || garment.name === undefined) {
        errors.name = "Name can't be empty"
        isValid = false
    }

    if (garment.colour.split("").length === 0 || garment.colour === undefined) {
        errors.colour = "Colour can't be empty"
        isValid = false
    }

    if (garment.category === ECategory.NONE || garment.category === undefined) {
        errors.category = "Category must be valid and can't be none"
        isValid = false
    }

    return { errors, isValid }
}

type GarmentFormProps = {
    garmentToEdit?: GarmentType
}

export default function GarmentForm({ garmentToEdit }: GarmentFormProps) {
    const [details, setDetails] = useState<Model | GarmentType>(defaultModel)
    const [errors, setErrors] = useState<ErrorModel>(errorModel)
    const [isLoading, setIsLoading] = useState(false)
    const { session } = useSession()
    const token = session.token
    const router = useRouter()

    useEffect(() => {
        if (garmentToEdit != undefined) {
            setDetails(garmentToEdit)
        }
    }, [garmentToEdit])

    const handleSubmit = async () => {
        setIsLoading(true)
        setErrors(errorModel)

        const result = validateModel(details)

        if (!result.isValid) {
            setErrors(result.errors)
            setIsLoading(false)
            return
        }

        if ((details as GarmentType).id) {
            try {
                const index = TestGarments.indexOf(TestGarments.find(i => i.id == garmentToEdit?.id) as GarmentType)
                TestGarments.splice(index, 1, details as GarmentType)
                // await updateGarment(details as GarmentType, token)
                setErrors(errorModel)
                setIsLoading(false)
                router.replace("/")
            } catch (error) {
                setErrors({
                    ...errors,
                    create: "Changes failed"
                })
                setIsLoading(false)
            }
        } else {
            try {
                setDetails({...details, id: "new"})
                TestGarments.push(details as GarmentType);
                // await createGarment(details, token)
                setErrors(errorModel)
                setIsLoading(false)
                router.replace("/")
            } catch (error) {
                setErrors({
                    ...errors,
                    create: "Creation failed"
                })
                setIsLoading(false)
            }
        }
    }

    return (
        <View style={styles.container}>

            <View style={styles.titleContainer}>
                <Title>{(details as GarmentType).id ? "Edit Garment" : "Create garment"}</Title>
                {errors.create && <SubTitle style={{ ...styles.error, marginTop: 0 }} >{errors.create}</SubTitle>}
            </View>

            <SubTitle>Category:</SubTitle>
            <Picker
                selectedValue={details.category}
                onValueChange={(itemValue) => setDetails({ ...details, category: itemValue })}
            >
                <Picker.Item label="Top" value={ECategory.TOP} />
                <Picker.Item label="Bottom" value={ECategory.BOTTOM} />
                <Picker.Item label="Shoes" value={ECategory.SHOES} />
                <Picker.Item label="Underwear" value={ECategory.UNDERWEAR} />
                <Picker.Item label="Socks" value={ECategory.SOCKS} />
                <Picker.Item label="Belt" value={ECategory.BELT} />
                <Picker.Item label="Ring" value={ECategory.RING} />
                <Picker.Item label="Earring" value={ECategory.EARRING} />
                <Picker.Item label="Headwear" value={ECategory.HEADWEAR} />
                <Picker.Item label="Jacket" value={ECategory.JACKET} />
                <Picker.Item label="Bag" value={ECategory.BAG} />
                <Picker.Item label="Glove" value={ECategory.GLOVE} />
                <Picker.Item label="Bracelet" value={ECategory.BRACELET} />
                <Picker.Item label="Necklace" value={ECategory.NECKLACE} />
            </Picker>
            {errors.category && <DefaultText style={styles.error}>{errors.category}</DefaultText>}

            <SubTitle>Colour:</SubTitle>
            <TextInput style={styles.input} onChangeText={(v) => setDetails({ ...details, colour: v })} placeholder="Colour" value={details.colour} />
            {errors.colour && <DefaultText style={styles.error}>{errors.colour}</DefaultText>}

            <SubTitle>Name:</SubTitle>
            <TextInput style={styles.input} onChangeText={(v) => setDetails({ ...details, name: v })} placeholder="Name" value={details.name} />
            {errors.name && <DefaultText style={styles.error}>{errors.name}</DefaultText>}

            <CustomButton onPress={handleSubmit} disabled={isLoading}>{isLoading ? "Pending" : garmentToEdit ? "Update" : "Create"}</CustomButton>
        </View>
    )
}


const styles = StyleSheet.create({
    container: {
        marginHorizontal: 16
    },

    input: {
        height: 40,
        borderWidth: 1,
        marginVertical: 8
    },

    error: {
        color: 'red',
        marginTop: -8,
        marginBottom: 8
    },

    titleContainer: {
        alignItems: "center"
    }
});