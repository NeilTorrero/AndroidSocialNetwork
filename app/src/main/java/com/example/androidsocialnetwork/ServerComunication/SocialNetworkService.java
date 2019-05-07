package com.example.androidsocialnetwork.ServerComunication;

import com.example.androidsocialnetwork.Model.Chatroom;
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.TokenUser;
import com.example.androidsocialnetwork.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SocialNetworkService {

    @POST("authenticate")
    Call<TokenUser> loginUser (@Body User login);

    @POST("register")
    Call<ResponseBody> registerUser(@Body User login);

    @GET("activate")
    Call<ResponseBody> activateAccount(@Body long key, @Header("Authorization") String authToken);

    @GET("account")
    Call<User> getAccount(@Header("Authorization") String authToken);

    @GET("my-profile")
    Call<Profile> getMyProfile(@Header("Authorization") String authToken);

    @POST("my-profile")
    Call<ResponseBody> updateMyProfile(@Body Profile profile,@Header("Authorization") String authToken);

    @GET("chatrooms")
    Call<Chatroom> getAllChatRooms(@Header("Authorization") String authToken);

    @POST("messages")
    Call<ResponseBody> sendMessage(@Body Chatroom message, @Header("Authorization") String authToken);






}
