import { useRouter } from "expo-router"
import { useEffect, useState } from "react"
import { StyleSheet, TextInput, View } from "react-native"
import { GarmentType, OutfitType } from "../lib/CustomTypes"
import ECategory from "../lib/ECategory"
import { TestGarments, TestOutfits } from "../lib/TestData"
import { createOutfit, updateOutfit } from "../lib/api/outfits"
import { useSession } from "../lib/hooks/session"
import CustomButton from "./CustomButton"
import DefaultText from "./DefaultText"
import GarmentSelecter from "./GarmentSelecter"
import SelectedItem from "./SelectedItem"
import SelectedList from "./SelectedList"
import Space from "./Space"
import SubTitle from "./SubTitle"
import Title from "./Title"


const defaultModel: OutfitType = {
    name: "",
    styleCategory: "",
    topId: "",
    bottomId: "",
    shoesId: "",
    underwearId: "",
    socksId: "",
    beltId: "",
    ringsId: [],
    earringsId: [],
    headwearId: [],
    jacketId: "",
    bagsId: [],
    glovesId: [],
    braceletsId: [],
    necklacesId: []
}

type ErrorModel = {
    create?: string,
    name: string,
    styleCategory: string,
    topId: string,
    bottomId: string,
    shoesId: string,
    underwearId: string,
    socksId?: string,
    beltId?: string,
    ringsId?: string,
    earringsId?: string,
    headwearId?: string,
    jacketId?: string,
    bagsId?: string,
    glovesId?: string,
    braceletsId?: string,
    necklacesId?: string
}

type DeleteCategory = ECategory.TOP | ECategory.BOTTOM | ECategory.SHOES | ECategory.UNDERWEAR | ECategory.SOCKS | ECategory.BELT | ECategory.JACKET

const errorModel: ErrorModel = {
    name: "",
    styleCategory: "",
    topId: "",
    bottomId: "",
    shoesId: "",
    underwearId: "",
    socksId: "",
    beltId: "",
    ringsId: "",
    earringsId: "",
    headwearId: "",
    jacketId: "",
    bagsId: "",
    glovesId: "",
    braceletsId: "",
    necklacesId: ""
}

function validateModel(outfit: OutfitType) {
    const errors: ErrorModel = {
        name: "",
        styleCategory: "",
        topId: "",
        bottomId: "",
        shoesId: "",
        underwearId: "",
        socksId: "",
        beltId: "",
        ringsId: "",
        earringsId: "",
        headwearId: "",
        jacketId: "",
        bagsId: "",
        glovesId: "",
        braceletsId: "",
        necklacesId: ""
    }
    let isValid = true

    if (outfit.name.split("").length == 0 || outfit.name === undefined) {
        errors.name = "Name can't be empty"
        isValid = false
    }

    if (outfit.styleCategory.split("").length == 0 || outfit.styleCategory === undefined) {
        errors.styleCategory = "Style can't be empty"
        isValid = false
    }

    if (outfit.topId.split("").length == 0 || outfit.topId === undefined) {
        errors.topId = "Top must be selected"
        isValid = false
    }

    if (outfit.bottomId.split("").length == 0 || outfit.bottomId === undefined) {
        errors.bottomId = "Bottom must be selected"
        isValid = false
    }

    if (outfit.shoesId.split("").length == 0 || outfit.shoesId === undefined) {
        errors.shoesId = "Shoes must be selected"
        isValid = false
    }

    if (outfit.underwearId.split("").length == 0 || outfit.underwearId === undefined) {
        errors.underwearId = "Underwear must be selected"
        isValid = false
    }

    return { errors, isValid }
}


type OutFitFormProps = {
    outfitToEdit?: OutfitType
}

export default function OutfitForm({ outfitToEdit }: OutFitFormProps) {
    const [garments, setGarments] = useState<OutfitType>(defaultModel)
    const [errors, setErrors] = useState<ErrorModel>(errorModel)
    const [isLoading, setIsLoading] = useState(false)
    const [allGarments, setAllGarments] = useState<GarmentType[]>(TestGarments)
    const { session } = useSession()
    const token = session.token
    const router = useRouter()

    const [isBrowseTop, setIsBrowseTop] = useState(false);
    const [isBrowseBottom, setIsBrowseBottom] = useState(false);
    const [isBrowseShoes, setIsBrowseShoes] = useState(false);
    const [isBrowseUnderwear, setIsBrowseUnderwear] = useState(false);
    const [isBrowseSocks, setIsBrowseSocks] = useState(false);
    const [isBrowseBelt, setIsBrowseBelt] = useState(false);
    const [isBrowseRings, setIsBrowseRings] = useState(false);
    const [isBrowseEarrings, setIsBrowseEarrings] = useState(false);
    const [isBrowseHeadwear, setIsBrowseHeadwear] = useState(false);
    const [isBrowseJacket, setIsBrowseJacket] = useState(false);
    const [isBrowseBags, setIsBrowseBags] = useState(false);
    const [isBrowseGloves, setIsBrowseGloves] = useState(false);
    const [isBrowseBracelets, setIsBrowseBracelets] = useState(false);
    const [isBrowseNecklaces, setIsBrowseNecklaces] = useState(false);


    // const loadGarments = async() => {
    //     const response = await getAllGarments();
    //     setAllGarments(response);
    // }

    // useEffect(() => {
    //     loadGarments()
    // }, [])

    const deleteItemFromSingleItemCategories = (category: DeleteCategory) => {
        switch (category) {
            case ECategory.TOP: setGarments({ ...garments, topId: "" }); break;
            case ECategory.BOTTOM: setGarments({ ...garments, bottomId: "" }); break;
            case ECategory.SHOES: setGarments({ ...garments, shoesId: "" }); break;
            case ECategory.UNDERWEAR: setGarments({ ...garments, underwearId: "" }); break;
            case ECategory.SOCKS: setGarments({ ...garments, socksId: "" }); break;
            case ECategory.BELT: setGarments({ ...garments, beltId: "" }); break;
            case ECategory.JACKET: setGarments({ ...garments, jacketId: "" }); break;
        }
    }

    const toggleIsBrowse = (category: number) => {
        switch (category) {
            case 1: {
                if (isBrowseTop) {
                    setIsBrowseTop(false)
                } else {
                    setIsBrowseTop(true)
                };
                break;
            }
            case 2: {
                if (isBrowseBottom) {
                    setIsBrowseBottom(false)
                } else {
                    setIsBrowseBottom(true)
                };
                break;
            }
            case 3: {
                if (isBrowseShoes) {
                    setIsBrowseShoes(false)
                } else {
                    setIsBrowseShoes(true)
                };
                break;
            }
            case 4: {
                if (isBrowseUnderwear) {
                    setIsBrowseUnderwear(false)
                } else {
                    setIsBrowseUnderwear(true)
                };
                break;
            }
            case 5: {
                if (isBrowseSocks) {
                    setIsBrowseSocks(false)
                } else {
                    setIsBrowseSocks(true)
                };
                break;
            }
            case 6: {
                if (isBrowseBelt) {
                    setIsBrowseBelt(false)
                } else {
                    setIsBrowseBelt(true)
                };
                break;
            }
            case 7: {
                if (isBrowseRings) {
                    setIsBrowseRings(false)
                } else {
                    setIsBrowseRings(true)
                };
                break;
            }
            case 8: {
                if (isBrowseEarrings) {
                    setIsBrowseEarrings(false)
                } else {
                    setIsBrowseEarrings(true)
                };
                break;
            }
            case 9: {
                if (isBrowseHeadwear) {
                    setIsBrowseHeadwear(false)
                } else {
                    setIsBrowseHeadwear(true)
                };
                break;
            }
            case 10: {
                if (isBrowseJacket) {
                    setIsBrowseJacket(false)
                } else {
                    setIsBrowseJacket(true)
                };
                break;
            }
            case 11: {
                if (isBrowseBags) {
                    setIsBrowseBags(false)
                } else {
                    setIsBrowseBags(true)
                };
                break;
            }
            case 12: {
                if (isBrowseGloves) {
                    setIsBrowseGloves(false)
                } else {
                    setIsBrowseGloves(true)
                };
                break;
            }
            case 13: {
                if (isBrowseBracelets) {
                    setIsBrowseBracelets(false)
                } else {
                    setIsBrowseBracelets(true)
                };
                break;
            }
            case 14: {
                if (isBrowseNecklaces) {
                    setIsBrowseNecklaces(false)
                } else {
                    setIsBrowseNecklaces(true)
                };
                break;
            }
        }
    }

    useEffect(() => {
        if (outfitToEdit != undefined) {
            setGarments(outfitToEdit)
        }
    }, [outfitToEdit])

    const handleSubmit = async () => {
        setIsLoading(true)
        setErrors(errorModel)

        const result = validateModel(garments)

        if (!result.isValid) {
            setErrors(result.errors)
            setIsLoading(false)
            return
        }

        if (garments.id) {
            try {
                const index = TestOutfits.indexOf(TestOutfits.find(i => i.id == outfitToEdit?.id) as OutfitType);
                TestOutfits.splice(index, 1, garments)
                // await updateOutfit(garments, token)
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
                setGarments({...garments, id: "new"})
                TestOutfits.push(garments)
                // await createOutfit(garments, token)
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
                <Title>{garments.id ? "Edit Outfit" : "Create outfit"}</Title>
                {errors.create && <SubTitle style={{ ...styles.error, marginTop: 0 }} >{errors.create}</SubTitle>}
            </View>

            <SubTitle>Top:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.topId ?
                    <SelectedItem allGarments={allGarments} deleteItem={() => deleteItemFromSingleItemCategories(ECategory.TOP)} itemKey={garments.topId} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Top...</DefaultText>
                    </View>
                }
                <Space height={8} />
                {errors.topId && <DefaultText style={styles.error}>{errors.topId}</DefaultText>}
                <CustomButton onPress={() => toggleIsBrowse(1)} >Browse</CustomButton>
            </View>

            {isBrowseTop && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.TOP} setGarments={setGarments} setBrowse={setIsBrowseTop} />
            )}

            <SubTitle>Bottom:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.bottomId ?
                    <SelectedItem allGarments={allGarments} deleteItem={() => deleteItemFromSingleItemCategories(ECategory.BOTTOM)} itemKey={garments.bottomId} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Bottom...</DefaultText>
                    </View>
                }
                <Space height={8} />
                {errors.bottomId && <DefaultText style={styles.error}>{errors.bottomId}</DefaultText>}
                <CustomButton onPress={() => toggleIsBrowse(2)} >Browse</CustomButton>
            </View>

            {isBrowseBottom && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.BOTTOM} setGarments={setGarments} setBrowse={setIsBrowseBottom} />
            )}

            <SubTitle>Shoes:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.shoesId ?
                    <SelectedItem allGarments={allGarments} deleteItem={() => deleteItemFromSingleItemCategories(ECategory.SHOES)} itemKey={garments.shoesId} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Shoes...</DefaultText>
                    </View>
                }
                <Space height={8} />
                {errors.shoesId && <DefaultText style={styles.error}>{errors.shoesId}</DefaultText>}
                <CustomButton onPress={() => toggleIsBrowse(3)} >Browse</CustomButton>
            </View>

            {isBrowseShoes && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.SHOES} setGarments={setGarments} setBrowse={setIsBrowseShoes} />
            )}

            <SubTitle>Underwear:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.underwearId ?
                    <SelectedItem allGarments={allGarments} deleteItem={() => deleteItemFromSingleItemCategories(ECategory.UNDERWEAR)} itemKey={garments.underwearId} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Underwear...</DefaultText>
                    </View>
                }
                <Space height={8} />
                {errors.underwearId && <DefaultText style={styles.error}>{errors.underwearId}</DefaultText>}
                <CustomButton onPress={() => toggleIsBrowse(4)} >Browse</CustomButton>
            </View>

            {isBrowseUnderwear && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.UNDERWEAR} setGarments={setGarments} setBrowse={setIsBrowseUnderwear} />
            )}

            <SubTitle>Socks:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.socksId ?
                    <SelectedItem allGarments={allGarments} deleteItem={() => deleteItemFromSingleItemCategories(ECategory.SOCKS)} itemKey={garments.socksId} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Socks...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(5)} >Browse</CustomButton>
            </View>

            {isBrowseSocks && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.SOCKS} setGarments={setGarments} setBrowse={setIsBrowseSocks} />
            )}

            <SubTitle>Belt:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.beltId ?
                    <SelectedItem allGarments={allGarments} deleteItem={() => deleteItemFromSingleItemCategories(ECategory.BELT)} itemKey={garments.beltId} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Belt...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(6)} >Browse</CustomButton>
            </View>

            {isBrowseBelt && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.BELT} setGarments={setGarments} setBrowse={setIsBrowseBelt} />
            )}

            <SubTitle>Rings:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.ringsId ?
                    <SelectedList allGarments={allGarments} idList={garments.ringsId} setGarments={setGarments} garments={garments} category={ECategory.RING} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Rings...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(7)} >Browse</CustomButton>
            </View>

            {isBrowseRings && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.RING} setGarments={setGarments} setBrowse={setIsBrowseRings} />
            )}

            <SubTitle>Earrings:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.earringsId ?
                    <SelectedList allGarments={allGarments} idList={garments.earringsId} setGarments={setGarments} garments={garments} category={ECategory.EARRING} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Earrings...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(8)} >Browse</CustomButton>
            </View>

            {isBrowseEarrings && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.EARRING} setGarments={setGarments} setBrowse={setIsBrowseEarrings} />
            )}

            <SubTitle>Headwear:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.headwearId ?
                    <SelectedList allGarments={allGarments} idList={garments.headwearId} setGarments={setGarments} garments={garments} category={ECategory.HEADWEAR} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Headwear...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(9)} >Browse</CustomButton>
            </View>

            {isBrowseHeadwear && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.HEADWEAR} setGarments={setGarments} setBrowse={setIsBrowseHeadwear} />
            )}

            <SubTitle>Jacket:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.jacketId ?
                    <SelectedItem allGarments={allGarments} deleteItem={() => deleteItemFromSingleItemCategories(ECategory.JACKET)} itemKey={garments.jacketId} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Jacket...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(10)} >Browse</CustomButton>
            </View>

            {isBrowseJacket && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.JACKET} setGarments={setGarments} setBrowse={setIsBrowseJacket} />
            )}

            <SubTitle>Bags:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.bagsId ?
                    <SelectedList allGarments={allGarments} idList={garments.bagsId} setGarments={setGarments} garments={garments} category={ECategory.BAG} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Bags...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(11)} >Browse</CustomButton>
            </View>

            {isBrowseBags && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.BAG} setGarments={setGarments} setBrowse={setIsBrowseBags} />
            )}

            <SubTitle>Gloves:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.glovesId ?
                    <SelectedList allGarments={allGarments} idList={garments.glovesId} setGarments={setGarments} garments={garments} category={ECategory.GLOVE} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Gloves...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(12)} >Browse</CustomButton>
            </View>

            {isBrowseGloves && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.GLOVE} setGarments={setGarments} setBrowse={setIsBrowseGloves} />
            )}

            <SubTitle>Bracelets:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.braceletsId ?
                    <SelectedList allGarments={allGarments} idList={garments.braceletsId} setGarments={setGarments} garments={garments} category={ECategory.BRACELET} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Bracelets...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(13)} >Browse</CustomButton>
            </View>

            {isBrowseBracelets && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.BRACELET} setGarments={setGarments} setBrowse={setIsBrowseBracelets} />
            )}

            <SubTitle>Necklaces:</SubTitle>
            <View style={{ flexDirection: "column" }}>
                {garments.necklacesId ?
                    <SelectedList allGarments={allGarments} idList={garments.necklacesId} setGarments={setGarments} garments={garments} category={ECategory.NECKLACE} />
                    :
                    <View style={styles.placeholderContainer}>
                        <DefaultText style={styles.placeholder}>Select Necklaces...</DefaultText>
                    </View>
                }
                <CustomButton onPress={() => toggleIsBrowse(14)} >Browse</CustomButton>
            </View>

            {isBrowseNecklaces && (
                <GarmentSelecter allGarments={allGarments} garments={garments} category={ECategory.NECKLACE} setGarments={setGarments} setBrowse={setIsBrowseNecklaces} />
            )}

            <SubTitle>Name:</SubTitle>
            <TextInput
                style={styles.input}
                value={garments.name}
                placeholder="Name your outfit..."
                onChangeText={(v) => setGarments({ ...garments, name: v })}
            />
            {errors.name && <DefaultText style={styles.error}>{errors.name}</DefaultText>}

            <SubTitle>Style:</SubTitle>
            <TextInput
                style={styles.input}
                value={garments.styleCategory}
                placeholder="Name the outfit style..."
                onChangeText={(v) => setGarments({ ...garments, styleCategory: v })}
            />
            {errors.styleCategory && <DefaultText style={styles.error}>{errors.styleCategory}</DefaultText>}

            <Space height={36} />

            <CustomButton onPress={handleSubmit} disabled={isLoading}>{isLoading ? "Pending" : outfitToEdit ? "Update" : "Create"}</CustomButton>

            <Space height={64} />


        </View>
    )
}


const styles = StyleSheet.create({
    container: {
        marginLeft: 16,
        marginRight: 16
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
    },

    placeholderContainer: {
        height: 40,
        borderWidth: 1,
        marginVertical: 8,
        justifyContent: "center",
        borderRadius: 4
    },

    placeholder: {
        fontSize: 16,
        marginLeft: 8,
        color: "grey"
    }
});

