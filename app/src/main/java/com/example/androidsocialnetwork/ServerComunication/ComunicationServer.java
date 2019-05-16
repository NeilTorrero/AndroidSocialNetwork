package com.example.androidsocialnetwork.ServerComunication;

import android.widget.Toast;

import com.example.androidsocialnetwork.Fragments.ChatFragment;
import com.example.androidsocialnetwork.Fragments.ChatListFragment;
import com.example.androidsocialnetwork.Fragments.FriendFragment;
import com.example.androidsocialnetwork.Fragments.ProfileFragment;
import com.example.androidsocialnetwork.Fragments.UserSolicitudes;
import com.example.androidsocialnetwork.LoginActivity;
import com.example.androidsocialnetwork.MainActivity;
import com.example.androidsocialnetwork.Model.Block;
import com.example.androidsocialnetwork.Model.Chatroom;
import com.example.androidsocialnetwork.Model.Gender;
import com.example.androidsocialnetwork.Model.Invitation;
import com.example.androidsocialnetwork.Model.Profile;
import com.example.androidsocialnetwork.Model.TokenUser;
import com.example.androidsocialnetwork.Model.User;
import com.example.androidsocialnetwork.Model.UserLogin;
import com.example.androidsocialnetwork.RegisterActivity;
import com.example.androidsocialnetwork.ThreadNotifications.ThreadNotification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

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
    private Profile userProfile;
    private ArrayList<Gender> genders;

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
        final Call<TokenUser> tokenUser = service.loginUser(new UserLogin(introducedUsername, true,introducedPassword));
        tokenUser.enqueue(new Callback<TokenUser>() {
            @Override
            public void onResponse(Call<TokenUser> call, Response<TokenUser> response) {
                if (response.isSuccessful()) {
                    TokenUser idToken = response.body();
                    ComunicationServer.getInstance().setTokenUser(idToken);
                    loginActivity.loginCorrect();
                    loginActivity.setExistsUser(true);
                    //comment
                    getMyProfile();
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

    public void getMyProfile() {
        Call<Profile> getMyProfile = service.getMyProfile("Bearer " + tokenUser.getIdToken());
        getMyProfile.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    userProfile = response.body();
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMyProfile(final ProfileFragment profileFragment) {
        Call<Profile> getMyProfile = service.getMyProfile("Bearer " + tokenUser.getIdToken());
        getMyProfile.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    profileFragment.updateProfile(response.body());
                    userProfile = response.body();
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

    public void getMyProfile(final FriendFragment profileFragment) {
        Call<Profile> getMyProfile = service.getMyProfile("Bearer " + tokenUser.getIdToken());
        getMyProfile.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    profileFragment.updateProfile(response.body());
                    userProfile = response.body();
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

        Profile auxP = profileFragment.getMyProfile();
        auxP.setBirthDate(birthDate);
        auxP.setHeight(height);
        auxP.setAboutMe(description);
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

        updateGender(gender, profileFragment); // Actualitzem el genere
    }

    public void getAllChatRooms(final ChatListFragment chatListFragment) {
        final ArrayList<Chatroom> chats = new ArrayList<>();
        Call<Chatroom[]> getAllChatRooms = service.getAllChatRooms("Bearer " + tokenUser.getIdToken());
        getAllChatRooms.enqueue(new Callback<Chatroom[]>() {
            @Override
            public void onResponse(Call<Chatroom[]> call, Response<Chatroom[]> response) {
                if (response.isSuccessful()) {
                    chats.addAll(Arrays.asList(response.body()));
                    chatListFragment.setAllChatRooms(chats);
                } else {
                    Toast.makeText(chatListFragment.getContext(), "Couldn't get the ChatRooms!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Chatroom[]> call, Throwable t) {
                Toast.makeText(chatListFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPendingInvites(final UserSolicitudes userSolicitudes) {
        Call<Invitation[]> getPendingInvites = service.getPendingInvites("Bearer " + tokenUser.getIdToken());
        getPendingInvites.enqueue(new Callback<Invitation[]>() {
            @Override
            public void onResponse(Call<Invitation[]> call, Response<Invitation[]> response) {
                if (response.isSuccessful()) {
                    ArrayList<Invitation> invitations = new ArrayList<>(Arrays.asList(response.body()));
                    userSolicitudes.setInvitations(invitations);
                } else {
                    Toast.makeText(userSolicitudes.getContext(), "Couldn't get the invitations!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Invitation[]> call, Throwable t) {
                Toast.makeText(userSolicitudes.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void changeInvitationState(int userId, final boolean state, final UserSolicitudes userSolicitudes) {
        Call<ResponseBody> changeInvitationState = service.changeInvitationState(userId, state,"Bearer " + tokenUser.getIdToken());
        changeInvitationState.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (state) {
                        Toast.makeText(userSolicitudes.getContext(), "Invitation accepted!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(userSolicitudes.getContext(), "Invitation declined!", Toast.LENGTH_LONG).show();
                    }
                    getPendingInvites(userSolicitudes);
                } else {
                    Toast.makeText(userSolicitudes.getContext(), "Couldn't get the invitations!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(userSolicitudes.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    // No se segur si es el chatFragment qui l'utilitzara o no
    public void sendMessage(String messageIn, String receiver, String sender, final ChatFragment fragment) {
        Chatroom message = new Chatroom();
    }

    public User getUserById(String userName) {
        Call<User> getUserId = service.getUserById(userName,"Bearer " + tokenUser.getIdToken());
        final User[] aux = new User[1];
        getUserId.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    aux[0] = response.body();
                } else {
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        return aux[0];
    }

    public ArrayList<User> getAllUsers() {
        final ArrayList<User>[] users = new ArrayList[]{new ArrayList<>()};
        Call<User[]> getAllUsers = service.getAllUsers("Bearer" + tokenUser.getIdToken());
        getAllUsers.enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    users[0] = new ArrayList<>(Arrays.asList(response.body()));
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
            }
        });
        return users[0];
    }

    public void inviteUser(final MainActivity mainActivity) {
        ArrayList<User> users = new ArrayList<>(getAllUsers());
        User auxUser = users.get(new Random().nextInt(users.size()));
        Call<ResponseBody> sendInvitation = service.inviteUser(auxUser.getId(), "Bearer " + tokenUser.getIdToken());
        sendInvitation.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mainActivity.getBaseContext(), "Correct register!!!", Toast.LENGTH_LONG).show();
                    // profileFragment.updateProfile(response.body());
                } else {
                    //Toast.makeText(profileFragment.getContext(), "Something happened!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public TokenUser getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(TokenUser tokenUser) {
        this.tokenUser = tokenUser;
    }

    public void blockUser(String blockUserName, final FriendFragment friendFragment) {
        Block block = new Block();
        Date data = new Date();
        block.setId(getBlocks()+1);
        block.setCreatedDate(data.toString());
        block.setSent(userProfile);
        block.setReceived(getProfileById(blockUserName));

        Call<ResponseBody> blockUser = service.blockUser(block,"Bearer " + tokenUser.getIdToken());
        blockUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(friendFragment.getContext(), "User Blocked!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(friendFragment.getContext(), "Something happened!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public int getBlocks() {
        final Integer[] ret = new Integer[1];
        Call<Integer> blocksCount = service.getBlocks("Bearer " + tokenUser.getIdToken());
        blocksCount.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    ret[0] = response.body();
                } else {
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
        return ret[0];
    }

    public Profile getProfileById(String userName) {
        final Profile[] retProfile = new Profile[1];
        User auxU = getUserById(userName);
        Call<Profile> getUserById = service.getUserProfileById(auxU.getId(),"Bearer " + tokenUser.getIdToken());
        getUserById.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    retProfile[0] = response.body();
                } else {
                }
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
            }
        });
        return retProfile[0];
    }

    public boolean arePendingInvites() {
        final boolean[] pending = {false};
        Call<Invitation[]> getPendingInvites = service.getPendingInvites("Bearer " + tokenUser.getIdToken());
        getPendingInvites.enqueue(new Callback<Invitation[]>() {
            @Override
            public void onResponse(Call<Invitation[]> call, Response<Invitation[]> response) {
                if (response.isSuccessful()) {
                    ArrayList<Invitation> invitations = new ArrayList<>(Arrays.asList(response.body()));
                    if (!invitations.isEmpty()) {
                        pending[0] = true;
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<Invitation[]> call, Throwable t) {
                //Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
        return pending[0];
    }

    public void getAllGenders(final ProfileFragment profileFragment) {
        Call<Gender[]> getAllGenders = service.getAllGenders("Bearer " + tokenUser.getIdToken());
        getAllGenders.enqueue(new Callback<Gender[]>() {
            @Override
            public void onResponse(Call<Gender[]> call, Response<Gender[]> response) {
                if (response.isSuccessful()) {
                    ArrayList<Gender> genders = new ArrayList<>(Arrays.asList(response.body()));
                    profileFragment.getAllGenders(genders);
                } else {
                }
            }
            @Override
            public void onFailure(Call<Gender[]> call, Throwable t) {
                //Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllGendersHere() {
        Call<Gender[]> getAllGenders = service.getAllGenders("Bearer " + tokenUser.getIdToken());
        getAllGenders.enqueue(new Callback<Gender[]>() {
            @Override
            public void onResponse(Call<Gender[]> call, Response<Gender[]> response) {
                if (response.isSuccessful()) {
                    genders = new ArrayList<>(Arrays.asList(response.body()));
                } else {
                }
            }
            @Override
            public void onFailure(Call<Gender[]> call, Throwable t) {
                //Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateGender(String gender, final ProfileFragment profileFragment) {

        boolean doUptade = true;
        Gender newGender = null;
        Gender oldGenderToUpdate = null;
        for (Gender g: genders) {
            if (g.getUsers().contains(userProfile)) {
                if (g.getType().equals(gender)) {
                    doUptade = false;
                } else {
                    g.getUsers().remove(userProfile);
                    oldGenderToUpdate = g;
                }
            }
            if (g.getType().equals(gender)) {
                newGender = g;
            }
        }

        if (doUptade) { // Nomes actualitzarem si s'ha canviat el genere
            Call<ResponseBody> updateOldGender = service.updateGender(oldGenderToUpdate, "Bearer " + tokenUser.getIdToken());
            updateOldGender.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
                }
            });

            Call<ResponseBody> updateNewGender = service.updateGender(newGender, "Bearer " + tokenUser.getIdToken());
            updateNewGender.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(profileFragment.getContext(), "Successful Gender Update!!", Toast.LENGTH_LONG).show();
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
                }
            });

            getAllGendersHere(); // Actualitzem els generes que tenim guardats
        }
    }

}

