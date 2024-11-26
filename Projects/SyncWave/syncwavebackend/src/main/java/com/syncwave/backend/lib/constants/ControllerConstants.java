package com.syncwave.backend.lib.constants;

public class ControllerConstants {

    private static final String API_URL = "/api";
    private static final String API_VERSION = "/v1";
    private static final String API = API_URL + API_VERSION;

    // User
    public static final String USERCONTROLLER_PATH = API + "/users";
    public static final String UPDATE_PATH = "/update/{id}";
    public static final String DELETE_PATH = "/delete/{id}";
    public static final String ID_PATH = "/{id}";


    // Chat
    public static final String CHATCONTROLLER_PATH = API + "/chats";

    // Messaging
    public static final String WS_ENDPOINT = API + "/ws";
    public static final String QUEUE_DST = "/queue/messages";
    public static final String MSGS_DST = "/messages";
    public static final String CHAT = "/chat";
    public static final String SEND_MSG_PRX = "/chat.sendmsg";
    public static final String EDIT_MSG_PRX = "/chat.editmsg";
    public static final String DELETE_MSG_PRX = "/chat.deletemsg";
    public static final String ADD_USR_PRX = "/chat.add";

    //Team
    public static final String TEAMCONTROLLER_PATH = API + "/team";
    public static final String TEAM_BY_USER_PATH =  "/user/{userId}";
    public static final String TEAM_ID_PATH = "/{id}";
    public static final String TEAM_NAME_PATH = "/name/{name}";
    public static final String UPDATE_TEAM_PATH = "/update/{id}";

    //TeamRole
    public static final String TEAMROLECONTROLLER_PATH = API + "/teamrole";

    // Task
    public static final String TASKCONTROLLER_PATH = API + "/task";
    public static final String TASK_BY_USER_PATH =  "/task/{userId}";
    public static final String TASK_ID_PATH = "/{id}";
    public static final String TASK_NAME_PATH = "/name/{name}";
    public static final String UPDATE_TASK_PATH = "/update/{id}";
    // TaskUser
    public static final String TASKUSERCONTROLLER_PATH = API + "/taskuser";
    public static final String TASKUSER_ID_PATH = "/{id}";
    public static final String UPDATE_TASKUSER_PATH = "/update/{id}";


}
