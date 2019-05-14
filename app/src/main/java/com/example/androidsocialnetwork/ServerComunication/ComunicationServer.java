package com.example.androidsocialnetwork.ServerComunication;

import android.widget.Toast;

import com.example.androidsocialnetwork.Fragments.ChatFragment;
import com.example.androidsocialnetwork.Fragments.ProfileFragment;
import com.example.androidsocialnetwork.LoginActivity;
import com.example.androidsocialnetwork.Model.Chatroom;
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.TokenUser;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.RegisterActivity;

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
                    loginActivity.setExistsUser(true);
                } else {
                    loginActivity.loginIncorrect();
                    loginActivity.setExistsUser(false);
                }
            }

            @Override
            public void onFailure(Call<TokenUser> call, Throwable t) {
                loginActivity.connectionFailed();
                loginActivity.setExistsUser(false);
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

    public void getMyProfile(final ProfileFragment profileFragment) {
        Call<Profile> getMyProfile = service.getMyProfile("Bearer " + tokenUser.getIdToken());
        getMyProfile.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    profileFragment.setProfile(response.body());
                } else {
                    Toast.makeText(profileFragment.getContext(), "Something happened!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateMyProfile (String birthDate, String gender, int height, String description, final ProfileFragment profileFragment ) {
        Profile auxP = new Profile(description,birthDate, height, gender );
        Call<ResponseBody> updateMyProfile = service.updateMyProfile(auxP, "Bearer " + tokenUser.getIdToken());
        updateMyProfile.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(profileFragment.getContext(), "Correct update!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(profileFragment.getContext(), "Any of the parameters entered is not correct!!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void getAllChatRooms() {
    }

    // No se segur si es el chatFragment qui l'utilitzara o no
    public void sendMessage(String messageIn, String receiver, String sender, final ChatFragment fragment) {
        Chatroom message = new Chatroom();
    }


    public TokenUser getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(TokenUser tokenUser) {
        this.tokenUser = tokenUser;
    }
}
