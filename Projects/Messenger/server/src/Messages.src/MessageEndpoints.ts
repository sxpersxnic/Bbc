import { Request, Response } from 'express';
import EHttpMethod from '../API.src/EHttpMethod';
import IApiEndpoint from '../API.src/IApiEndpoints';
import { StatusCodes } from 'http-status-codes';
import IMessage from './IMessage';
import MessageMgmt from '../MessageMgmt';

const MessageEndpoints: Array<IApiEndpoint> = [
    {
        url: "/api/chat/messages",
        method: EHttpMethod.GET,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                let message: IMessage | IMessage[] | undefined


                message = await MessageMgmt.getAllMessages();

                return res.status(StatusCodes.OK).send(JSON.parse(JSON.stringify(message)));

            }
            catch (error) {
                return res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    message: `Internal Server Error: ${error}`,
                });
            }
        }
    },
    {
        url: "/api/chat/messages/create",
        method: EHttpMethod.POST,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                if (
                    req.body.context != undefined
                ) {
                    const message: IMessage = {
                        constructor: { name: "RowDataPacket" },

                        context: `${req.body.context}`
                    }

                    MessageMgmt.createMessage(message);

                    return res.status(StatusCodes.OK).send({
                        message: `Created Message succesfully`
                    });
                }
                else {
                    return res.status(StatusCodes.BAD_REQUEST).send({
                        message: `Insufficient Data for creating message.`,
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
        url: "/api/chat/messages/:id",
        method: EHttpMethod.GET,
        handler: async function (req: Request, res: Response): Promise<Response> {
            try {
                const id: string | undefined = req.params.id;

                let message: IMessage | undefined;

                if (id && id !== "") {
                    message = await MessageMgmt.getMessageById(id);
                }

                return res.status(StatusCodes.OK).send(JSON.parse(JSON.stringify(message)));
            }
            catch (error) {
                return res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    message: `Internal Server Error: ${error}`,
                });
            }
        }
    }
]

export default MessageEndpoints;
