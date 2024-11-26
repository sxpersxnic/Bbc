import { Request, Response } from 'express';
import EHttpMethod from '../API.src/EHttpMethod';
import IApiEndpoint from '../API.src/IApiEndpoints';
import IUser from './IUser';
import UserMgmt from '../UserMgmt';
import { StatusCodes } from 'http-status-codes';
import { generateToken } from '../lib/jwt';

const UserEndpoints: Array<IApiEndpoint> = [
    {
        url: "/",
        method: EHttpMethod.GET,
        handler: async function (req: Request, res: Response): Promise<Response> {
            return res.status(StatusCodes.OK).send({
                message: process.env.WELCOME_MESSAGE
            });
        }
    },
    {
        url: "/api/users",
        method: EHttpMethod.GET,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                let user: IUser | IUser[] | undefined

                user = await UserMgmt.getAllUser();

                return res.status(StatusCodes.OK).send(JSON.parse(JSON.stringify(user)));
            }
            catch (error) {
                return res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    message: `Internal Server Error: ${error}`,
                });
            }
        }
    },
    {
        url: "/api/users/:id",
        method: EHttpMethod.GET,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                const id: string | undefined = req.params.id;

                let user: IUser | undefined;

                if (id && id !== "") {
                    user = await UserMgmt.getUserById(id);
                }

                return res.status(StatusCodes.OK).send(JSON.parse(JSON.stringify(user)));
            }
            catch (error) {
                return res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    message: `Internal Server Error: ${error}`,
                });
            }
        }
    },
    {
        url: "/api/register",
        method: EHttpMethod.POST,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                if (
                    req.body.username != undefined
                    && req.body.surname != undefined
                    && req.body.firstname != undefined
                    && req.body.email != undefined
                    && req.body.password != undefined
                ) {
                    const user: IUser = {
                        constructor: { name: "RowDataPacket" },

                        username: `${req.body.username}`,
                        surname: `${req.body.surname}`,
                        firstname: `${req.body.firstname}`,
                        email: `${req.body.email}`,
                        password: `${req.body.password}`
                    }

                    if (await UserMgmt.getUserByEmail(user.email) || await UserMgmt.getUserByUsername(user.username)) {
                        return res.status(StatusCodes.CONFLICT).send({
                            message: `User with given username or email already exists`
                        });
                    }

                    UserMgmt.createUser(user);

                    return res.status(StatusCodes.OK).send({
                        message: `Created User: ${user.username}`
                    });
                }
                else {
                    return res.status(StatusCodes.BAD_REQUEST).send({
                        message: `Insufficient Data for creating user.`,
                    });
                }
            }
            catch (error) {
                return res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    message: `Internal Server Error: ${error}`,
                });
            }
        }
    },
    {
        url: "/api/login",
        method: EHttpMethod.POST,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                if (
                    req.body.username != undefined
                    && req.body.password != undefined
                ) {
                    if (await UserMgmt.getUserByUsername(req.body.username) == undefined) {
                        return res.status(StatusCodes.BAD_REQUEST).send();
                    }

                    if (await UserMgmt.comparePassword(req.body.password, JSON.parse(JSON.stringify(await UserMgmt.getUserByUsername(req.body.username))).password)) {
                        const data = JSON.parse(JSON.stringify(await UserMgmt.getUserByUsername(req.body.username))).id
                        const token = generateToken(data)

                        return res.status(StatusCodes.OK).send({ token, user: data });
                    }
                    else {
                        return res.status(StatusCodes.BAD_REQUEST).send();
                    }
                }
                else if (
                    req.body.email != undefined
                    && req.body.password != undefined
                ) {
                    if (await UserMgmt.getUserByEmail(req.body.email) == undefined) {
                        return res.status(StatusCodes.BAD_REQUEST).send();
                    }

                    if (await UserMgmt.comparePassword(req.body.password, JSON.parse(JSON.stringify(await UserMgmt.getUserByEmail(req.body.email))).password)) {

                        const data = JSON.parse(JSON.stringify(await UserMgmt.getUserByEmail(req.body.email))).id
                        const token = generateToken(data)
                        return res.status(StatusCodes.OK).send({ token, user: data });
                    }
                    else {
                        return res.status(StatusCodes.BAD_REQUEST).send({
                            message: `Incorrect Password provided for user with email: ${req.body.email}`
                        });
                    }
                }
                else {
                    return res.status(StatusCodes.BAD_REQUEST).send({
                        message: `Insufficient Data for verifying user:
                        email: ${req.body.email},
                        username: ${req.body.username},
                        password: ${req.body.password},
                        user with same username: ${JSON.parse(JSON.stringify(await UserMgmt.getUserByEmail(req.body.username)))}`
                    })
                }
            }
            catch (error) {
                return res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    message: `Internal Server Error: ${error}`,
                });
            }
        }
    },
    {
        url: "/api/users/delete/:id",
        method: EHttpMethod.DELETE,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                const id: string | undefined = req.params.id;

                if (id && id !== "") {
                    await UserMgmt.deleteUser(id);
                }

                return res.status(StatusCodes.OK).send({
                    messages: `User with id ${id} deleted`
                });
            }
            catch (error) {
                return res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    message: `Internal Server Error: ${error}`,
                });
            }
        }
    }
]

export default UserEndpoints;
