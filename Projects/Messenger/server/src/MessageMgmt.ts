import DB from './DB';
import IMessage from './Messages.src/IMessage';
import { ResultSetHeader } from 'mysql2';
import { v4 as random } from 'uuid';

export default class MessageMgmt {
    public static table: string = "messages"

    public static init(): void {
        DB.init();
        DB.createTableMessages();

        MessageMgmt.table = (process.env.DB_MESSAGE_TABLE ? process.env.DB_MESSAGE_TABLE : "messages");
    }

    public static async getAllMessages(): Promise<IMessage[]> {
        return new Promise((resolve, reject) => {
            DB.pool.query<IMessage[]>(`SELECT * FROM ${MessageMgmt.table} ORDER BY created_at ASC`, (err, res) => {
                if (err) reject(err)
                else resolve(res)
            })
        })
    }

    public static async getMessageById(id: string): Promise<IMessage | undefined> {
        return new Promise((resolve, reject) => {
            DB.pool.query<IMessage[]>(
                `SELECT * FROM ${MessageMgmt.table} WHERE id = ?`, [id],
                (err, res) => {
                    if (err) reject(err)
                    else resolve(res?.[0])
                }
            )
        })

    }

    public static async createMessage(message: IMessage): Promise<IMessage | undefined> {
        try {
            let id = random();
            let check_message = await this.getMessageById(id);

            while (check_message) {
                id = random();
                check_message = await this.getMessageById(id);
            }

            console.log(message)

            return new Promise(() => {
                DB.pool.query<ResultSetHeader>(
                    `INSERT INTO ${MessageMgmt.table} (id, context, created_at)
                    VALUES(?,?, CURRENT_TIMESTAMP)`,
                    [id, message.context],
                    console.error
                );
            })
        } catch (error) {
            console.error('An error occured while creating user: ', error);
        }
    }
}