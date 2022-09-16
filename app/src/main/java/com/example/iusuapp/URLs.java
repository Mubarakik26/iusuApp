package com.example.iusuapp;

public class URLs {

    //private static final String ROOT_URL = "http://10.11.1.125/iusu_app_conn_v4/";
    private static final String ROOT_URL = "https://iusuapp.000webhostapp.com/iusuappapis/";




    private static final String LOGIN_SIGNUP_API = ROOT_URL+"login_signup_api.php/?apicall=";
    private static final String ANNOUNCEMENT_API = ROOT_URL+"announcement_api.php/?apicall=";
    private static final String EVENT_API = ROOT_URL+"event_api.php/?apicall=";
    private static final String NEWS_API = ROOT_URL+"news_api.php/?apicall=";
    private static final String FAVORITE_NEWS_API = ROOT_URL+"favorite_news_api.php/?apicall=";
    private static final String FAVORITE_EVENTS_API = ROOT_URL+"favorites_events_api.php/?apicall=";
    private static final String GUILD_OFFICIAL_API = ROOT_URL+"guild_official_api.php/?apicall=";
    private static final String GUILD_POSTS_API = ROOT_URL+"guild_posts_api.php/?apicall=";
    private static final String INSERT_PROFILE_IMAGE_API = ROOT_URL+"insert_profile_image_api.php/?apicall=";
    private static final String COMPLAINT_API = ROOT_URL+"complaint_api.php/?apicall=";
    private static final String COMPLAINT_COMMENT_API = ROOT_URL+"complaint_comment_api.php/?apicall=";

//COMPLAINT API
//COMPLAINT COMMENT API
//FAVORITE NEWS API
//FAVORITE EVENTS API

    //LOGIN AND REGSTER update profile
    public static final String URL_CREATE_ACCOUNT = LOGIN_SIGNUP_API + "signup";
    public static final String URL_LOGIN= LOGIN_SIGNUP_API + "login";
    public static final String UPDATE_PROFILE= LOGIN_SIGNUP_API + "update_profile";

    //PROFILE PICTURE
    public static final String URL_UPLOAD_PROFILE_PIC=INSERT_PROFILE_IMAGE_API+"uploadpic";

    //NEWS
    public static final String URL_NEWS_UPLOAD=NEWS_API+"make_post";
    public static final String URL_NEWS_FETCH=NEWS_API+"getnews";
    public static final String URL_LATEST_NEWS_FETCH=NEWS_API+"latest_news";

    //ANNOUNCEMENT
    public static final String URL_ANN_UPLOAD=ANNOUNCEMENT_API+"make_announcement";
    public static final String URL_ANN_GET_ANN=ANNOUNCEMENT_API+"getannouncements";
    public static final String URL_ANN_UPDATE=ANNOUNCEMENT_API+"update_announcement";
    public static final String URL_LATEST_ANNOUNCEMENTS=ANNOUNCEMENT_API+"latest_announcements";

    //EVENT
    public static final String URL_EVENT_UPLOAD=EVENT_API+"create_event";
    public static final String URL_GET_EVENT=EVENT_API+"getevents";
    public static final String URL_UPDATE_EVENT=EVENT_API+"update_event";
    public static final String URL_LATEST_EVENT=EVENT_API+"latest_events";


    //guild official

    public static final String URL_ADD_GUILD_OFFICIAL=GUILD_OFFICIAL_API+"add_guild_official";
    public static final String URL_FETCH_GUILD_OFFICIAL=GUILD_OFFICIAL_API+"fetch_guild_officials";
    public static final String URL_UPDATE_GUILD_OFFICIAL=GUILD_OFFICIAL_API+"update_guild_officials";

    //GUILD POSTS
    public static final String URL_ADD_GUILD_POST=GUILD_POSTS_API+"add_guild_posts";
    public static final String URL_FETCH_GUILD_POSTS=GUILD_POSTS_API+"fetch_guild_posts";
    public static final String URL_FETCH_GUILD_POST_TITLES=GUILD_POSTS_API+"fetch_guild_posttitles";
    public static final String URL_UPDATE_GUILD_POST=GUILD_POSTS_API+"update_guild_posts";


    //COMPLAINT API
    public static final String URL_ADD_COMPLAINT=COMPLAINT_API+"make_complaint";
    public static final String URL_UPDATE_COMPLAINT=COMPLAINT_API+"update_complaint";
    public static final String URL_FETCH_STUDENT_COMPLAINTS=COMPLAINT_API+"get_student_complaints";
    public static final String URL_FETCH_GUILDPOST_COMPLAINTS=COMPLAINT_API+"get_guildpost_complaints";



    //COMPLAINT COMMENT API
    public static final String URL_ADD_COMMENT_OFFICIAL=COMPLAINT_COMMENT_API+"make_comment_official";
    public static final String URL_ADD_STUDENT_COMMENT=COMPLAINT_COMMENT_API+"make_comment_student";
    public static final String URL_FETCH_COMMENTS=COMPLAINT_COMMENT_API+"get_comments";



    //FAVORITE NEWS API
    public static final String URL_ADD_TO_FAVORITE_NEWS=FAVORITE_NEWS_API+"add_to_favorite";
    public static final String URL_FETCH_FAVORITE_NEWS=FAVORITE_NEWS_API+"get_favorites";
    public static final String URL_REMOVE_FROM_FAVORITE_NEWS=FAVORITE_NEWS_API+"remove_from_favorites";



    //FAVORITE EVENTS API
    public static final String URL_ADD_TO_FAVORITE_EVENTS=FAVORITE_EVENTS_API+"add_to_favorite";
    public static final String URL_FETCH_FAVORITE_EVENTS=FAVORITE_EVENTS_API+"get_favorites";
    public static final String URL_REMOVE_FROM_FAVORITE_EVENTS=FAVORITE_EVENTS_API+"remove_from_favorites";


}
