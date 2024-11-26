import { RowDataPacket } from "mysql2";
import { v4 as uuidv4 } from 'uuid';

export default interface IMessage extends RowDataPacket {

    id?: string,
    context: string,
    created_at?: Date
}

export const generateUUID = (): string => {
    return uuidv4();
};