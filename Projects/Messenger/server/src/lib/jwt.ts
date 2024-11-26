import jwt from "jsonwebtoken";
//@ts-ignore
const SECRET_KEY: string = process.env.SECRET_KEY;
const DEFAULT_EXPIRES = "2h"

export function generateToken(data: string) {
    return jwt.sign({ data }, SECRET_KEY, { expiresIn: DEFAULT_EXPIRES })
}