import { GarmentType } from "lib/CustomTypes"
import ECategory from "lib/ECategory"

type NewGarmentType = {
    name: string,
    colour: string,
    category: ECategory
}

const URL = process.env.SPRING_API_URL

export async function getAllGarments() {
    const response = await fetch(`${URL}/garments`)

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function getGarmentById(id: string) {
    const response = await fetch(`${URL}/garments/${id}`)

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function createGarment(garment: NewGarmentType, token: any | null) {
    const response = await fetch(`${URL}/garments`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "content-type": "application/json"
        },
        body: JSON.stringify(garment)
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function updateGarment(garment: GarmentType, token: any | null) {
    const response = await fetch(`${URL}/garments/${garment.id}`, {
        method: "PUT",
        headers: {
            "Authorization": `Bearer ${token}`,
            "content-type": "application/json"
        },
        body: JSON.stringify(garment)
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }

    const data = await response.json()
    return data
}

export async function deleteGarment(id: string, token: any | null) {
    const response = await fetch(`${URL}/garments/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })

    if (!response.ok) {
        throw new Error("An error occured while fetching")
    }
}
