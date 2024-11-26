import { createPool, Pool } from 'mysql2';
import dotenv from 'dotenv';


dotenv.config()

export default class DB {
    private static HOST: string | undefined = process.env.DB_HOST;
    private static USER: string | undefined = process.env.DB_USER;
    private static PASSWORD: string | undefined = process.env.DB_PASSWORD;
    private static DATABASE: string | undefined = process.env.DB_DATABASE;
    private static PORT: number | undefined = (process.env.DB_PORT ? parseInt(process.env.DB_PORT) : undefined);
    public static pool: Pool;

    public static init(): void {
        try {
            DB.pool = createPool({
                host: DB.HOST,
                user: DB.USER,
                password: DB.PASSWORD,
                database: DB.DATABASE,
                port: DB.PORT
            })

            console.debug("MySQL Adapter-Pool created successfully")
        }
        catch (error) {
            console.error('[mysql.connectot][init][Error]: ', error);
            throw new Error('failed to initialize pool');
        }
    }

    public static checkPool(): void {
        try {
            if (!DB.pool) throw new Error('Pool was not created. Ensure pool is created when running the app.');
        }
        catch (error) {
            console.error('[mysql.connector][Pool][Error]', error)
        }
    }

    public static createTableUser(): void {
        try {
            DB.checkPool();

            DB.pool.getConnection((err, connection) => {
                if (err) {
                    console.error('[mysql.connector][Connection][Error]', err);
                    throw err;
                }

                const createUserTableQuery = `
                    CREATE TABLE IF NOT EXISTS users (
                        id VARCHAR(255) PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        surname VARCHAR(255) NOT NULL,
                        firstname VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        admin BOOLEAN,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                `;

                connection.query(createUserTableQuery, (queryErr) => {
                    connection.release();

                    if (queryErr) {
                        console.error('[mysql.connector][Query][Error]', queryErr);
                        throw queryErr;
                    }

                    console.debug('Users table created successfully');
                });
            });
        } catch (error) {
            console.error('[mysql.connector][createTableUser][Error]', error);
            throw error;
        }
    }

    public static createTableMessages(): void {
        try {
            DB.checkPool();

            DB.pool.getConnection((err, connection) => {
                if (err) {
                    console.error('[mysql.connector][Connection][Error]', err);
                    throw err;
                }

                const createMessagesTableQuery = `
                    CREATE TABLE IF NOT EXISTS messages (
                        id VARCHAR(255) PRIMARY KEY,
                        context VARCHAR(255) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        userId VARCHAR(255), 
                        FOREIGN KEY (userId) REFERENCES users(id)
                    )
                `;

                connection.query(createMessagesTableQuery, (queryErr) => {
                    connection.release();

                    if (queryErr) {
                        console.error('[mysql.connector][Query][Error]', queryErr);
                        throw queryErr;
                    }

                    console.debug('Messages table created successfully');
                });
            });
        }
        catch (error) {
            console.error('[mysql.connector][createTableMessages][Error]', error);
            throw error;
        }
    }
}