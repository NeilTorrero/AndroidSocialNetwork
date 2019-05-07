package com.example.androidsocialnetwork.ServerComunication;

import android.widget.Toast;

import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.TokenUser;
import com.example.androidsocialnetwork.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComunicationServer {
    private Retrofit retrofit;
    private SocialNetworkService service;
    private TokenUser tokenUser;
    private static ComunicationServer comunicationServer;

    public static ComunicationServer getInstance() {
        if (comunicationServer == null) {
            comunicationServer = new ComunicationServer();
        }
        return comunicationServer;
    }

    public ComunicationServer() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://android2.byted.xyz/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(SocialNetworkService.class);
    }

    public void registerUser(User user, final RegisterActivity registerActivity) {
        Call<ResponseBody> user_result = service.registerUser(user);
        user_result.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(registerActivity.getBaseContext(), "Correct register!!!", Toast.LENGTH_LONG).show();
                    registerActivity.returnLogin();
                } else {
                    Toast.makeText(registerActivity.getBaseContext(), "Any of the parameters entered is not correct!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(registerActivity.getBaseContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginUser(String introducedUsername, String introducedPassword, final LoginActivity loginActivity) {
        final Call<TokenUser> tokenUser = service.loginUser(new User(introducedUsername, introducedPassword));
        tokenUser.enqueue(new Callback<TokenUser>() {
            @Override
            public void onResponse(Call<TokenUser> call, Response<TokenUser> response) {
                if (response.isSuccessful()) {
                    ComunicationServer.getInstance().setTokenUser(response.body());
                    loginActivity.loginCorrect();
                } else {
                    loginActivity.loginIncorrect();
                }
            }

            @Override
            public void onFailure(Call<TokenUser> call, Throwable t) {
                loginActivity.connectionFailed();
            }
        });
    }

    public void activateAccount () {
        Call<ResponseBody> activateAccount = service.activateAccount(key,"Bearer " + tokenUser.getIdToken());
        activateAccount.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void getMyProfile() {
        Call<Profile> getMyProfile = service.getMyProfile("Bearer " + tokenUser.getIdToken());
        getMyProfile.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {

                } else {
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
            }
        });
    }

    public void updateMyProfile (String birthDate, String gender, int height, String description) {
        Profile auxP = new Profile(description,birthDate, height, gender );
        Call<ResponseBody> updateMyProfile = service.updateMyProfile(auxP, "Bearer " + tokenUser.getIdToken());
        updateMyProfile.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });


    }

    public void getAllChatRooms() {
    }






    public TokenUser getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(TokenUser tokenUser) {
        this.tokenUser = tokenUser;
    }
}
