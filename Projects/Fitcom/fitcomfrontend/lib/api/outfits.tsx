import { OutfitType } from "lib/CustomTypes"

const URL = process.env.SPRING_API_URL

export async function getAllOutfits() {
    const response = await fetch(`${URL}/outfits`)

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function getOutfitById(id: string) {
    const response = await fetch(`${URL}/outfits/${id}`)

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function createOutfit(outfit: OutfitType, token: any | null) {
    const response = await fetch(`${URL}/outfits`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "content-type": "application/json"
        },
        body: JSON.stringify(outfit)
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function updateOutfit(outfit: OutfitType, token: any | null) {
    const response = await fetch(`${URL}/outfits/${outfit.id}`, {
        method: "PUT",
        headers: {
            "Authorization": `Bearer ${token}`,
            "content-type": "application/json"
        },
        body: JSON.stringify(outfit)
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function deleteOutfit(id: string, token: any | null) {
    const response = await fetch(`${URL}/outfits/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }
}
