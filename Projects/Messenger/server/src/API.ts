// Global imports
import express from "express";
import { GetVerificationKey, expressjwt } from "express-jwt";
import logger from "morgan";
import compression from "compression";
import path from "path";
import cors from "cors"
import { Server } from "socket.io";
import { createServer } from "http";
import dotenv from "dotenv";
// Local imports
import IApiEndpoints from "./API.src/IApiEndpoints";
import UserEndpoints from "./Users.src/UserEndpoints"
import MessageEndpoints from "./Messages.src/MessageEndpoints";
import EHttpMethod from "./API.src/EHttpMethod";
import UserMgmt from "./UserMgmt";
import MessageMgmt from "./MessageMgmt"

dotenv.config();
export default class API {
    public static SECRET_KEY: string | GetVerificationKey = process.env.SECRET_KEY as string | GetVerificationKey;
    public static DISABLE_AUTH = process.env.DISABLE_AUTH;
    public static PORT = process.env.PORT;
    public static HOST = process.env.HOST;
    public static CLIENT_PORT = process.env.CLIENT_PORT;
    public static CLIENT_HOST = process.env.CLIENT_HOST;
    public static app = express();
    public static httpServer = createServer(API.app);
    public static io = new Server(API.httpServer, {
        cors: {
            origin: "*"
        }
    });

    public static corsOptions = {
        origin: "http://localhost:3000"
    };
    public static configured: boolean = false;

    private static userEndpoints: Array<IApiEndpoints> = UserEndpoints;
    private static messageEndpoints: Array<IApiEndpoints> = MessageEndpoints;


    private static registerUserEndPoints(): void {
        API.userEndpoints.forEach((userEndpoint) => {
            switch (userEndpoint.method) {
                case EHttpMethod.GET:
                    API.app.get(userEndpoint.url, userEndpoint.handler);
                    break;
                case EHttpMethod.POST:
                    API.app.post(userEndpoint.url, userEndpoint.handler);
                    break;
                case EHttpMethod.PUT:
                    API.app.put(userEndpoint.url, userEndpoint.handler);
                    break;
                case EHttpMethod.DELETE:
                    API.app.delete(userEndpoint.url, userEndpoint.handler);
                    break;
                default:
                    throw `No valid HTTP-Method found for endpoint: ${userEndpoint.url}`;
            }
        })
    }

    private static registerMessageEndPoints(): void {
        API.messageEndpoints.forEach((messageEndpoint) => {
            switch (messageEndpoint.method) {
                case EHttpMethod.GET:
                    API.app.get(messageEndpoint.url, messageEndpoint.handler);
                    break;
                case EHttpMethod.POST:
                    API.app.post(messageEndpoint.url, messageEndpoint.handler);
                    break;
                default:
                    throw `No valid HTTP-Method found for endpoint: ${messageEndpoint.url}`;
            }
        })
    }

    public static configure(): void {
        try {
            API.app.use(logger("dev"))
            API.app.use(cors(this.corsOptions));
            API.app.use(express.static(path.join(__dirname, "public")))

            if (!API.DISABLE_AUTH) {
                API.app.use("/api", expressjwt({
                    secret: API.SECRET_KEY as GetVerificationKey,
                    algorithms: ["HS256"],

                }).unless({
                    method: EHttpMethod.GET,
                    path: "/api/register"
                }))
            }
            API.app.use(express.json());
            API.app.use(express.urlencoded({ extended: true }));

            UserMgmt.init();
            MessageMgmt.init();

            API.registerUserEndPoints();
            API.registerMessageEndPoints();

            API.app.use(compression());

            API.configured = true;
        }
        catch (error) {
            console.error(`Error occured: ${error}`);
        }
    }

    public static run(): void {
        if (!API.configured) {
            API.configure()
        }

        try {
            this.io.on("connection", (socket) => {
                console.debug(`a user connected with socket id: ${socket.id}`);
                socket.on("message", (arg) => {
                    console.debug(arg);
                    this.io.emit("response", "message received");
                });
                socket.on("disconnect", () => {
                    console.log("a user disconnected");
                });
            });

            API.httpServer.listen(API.PORT, (): void => {
                console.log(`Connected successfully on: http://${API.HOST}:${API.PORT}`);
            });

        }
        catch (error) {
            console.error(`Error occured: ${error}`);
        }
    }
}