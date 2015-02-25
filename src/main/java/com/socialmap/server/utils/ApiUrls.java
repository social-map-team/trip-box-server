package com.socialmap.server.utils;

/**
 * Created by yy on 2/25/15.
 */
public class ApiUrls {
    public static final String ROOT = "/api";
    public static class User {
        public static final String ROOT                 = "/user";
        public static final String LOGIN                = "/login";     //POST
        public static final String LOGOUT               = "/logout";    //POST
        public static final String REGISTER             = "/register";  //POST
        public static final String UNREGISTER           = "/register";  //DELETE
        public static final String PROFILE              = "/profile";   //GET
        public static final String UPDATE_PROFILE       = "/profile";   //PUT
        public static final String UPLOAD_AVATAR        = "/avatar";    //PUT
        public static final String UPLOAD_BGIMAGE       = "/bgimage";   //PUT

        // friend
        public static final String MY_FRIENDS           = "/friends";   //GET
        public static final String ADD_FRIEND           = "/friend";    //POST
        public static final String DEL_FRIEND           = "/friend";    //DELETE

        // team
        public static final String MY_TEAMS             = "/teams";     //GET
        public static final String JOIN_TEAM            = "/team";      //POST
        public static final String QUIT_TEAM            = "/team";      //DELETE

        // search
        public static final String SEARCH               = "/search";    //GET

    }
    public static class Team {
        public static final String ROOT                 = "/team";
        public static final String SEARCH               = "/search";        //GET
        public static final String INFO                 = "{id}/info";      //GET
        public static final String MEMBERS              = "{id}/members";   //GET
    }
    public static class Image {
        public static final String ROOT                 = "/img";
        public static final String FETCH                = "/{id}";          //GET
    }


}
