package com.example.androidsocialnetwork.ServerComunication;

import android.content.Context;
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
import com.example.androidsocialnetwork.Model.UserDTO;
import com.example.androidsocialnetwork.Model.UserLogin;
import com.example.androidsocialnetwork.RegisterActivity;
import com.example.androidsocialnetwork.ThreadNotifications.ThreadNotification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
                .baseUrl("http://android3.byted.xyz/api/")
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
                    ComunicationServer.getInstance().setTokenUser(response.body());
                    loginActivity.loginCorrect();
                    getMyProfileHere();
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

    public void getMyProfileHere() {
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

    public void getMyProfileMainActivity(final MainActivity mainActivity) {
        Call<Profile> getMyProfile = service.getMyProfile("Bearer " + tokenUser.getIdToken());
        getMyProfile.enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    mainActivity.setMyProfile(response.body());
                    userProfile = response.body();
                } else {
                    Toast.makeText(mainActivity.getBaseContext(), "Something happened!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(mainActivity.getBaseContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*public void getFriendProfile(final FriendFragment profileFragment) {
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
    }*/

    public void updateMyProfile (String birthDate, String gender, int height,int weight, String description,String imageUser, final ProfileFragment profileFragment ) {

        Profile auxP = profileFragment.getMyProfile();
        auxP.setBirthDate(birthDate);
        auxP.setHeight(height);
        auxP.setAboutMe(description);
        auxP.setPicture(imageUser);
        auxP.setWeight(weight);
        //updateGender(gender, profileFragment); // Actualitzem el genere

        getAllGenders(profileFragment);

        Gender gender1 = null;

        for (Gender g: genders) {
            if (g.getType().equals(gender)) {
                gender1 = g;
            }
        }
        auxP.setGender(gender1);
        Call<ResponseBody> updateMyProfile = service.updateMyProfile(auxP, "Bearer " + tokenUser.getIdToken());
        updateMyProfile.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(profileFragment.getContext(), "Correct update!!!", Toast.LENGTH_LONG).show();
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

    public void getUserById(String userName, final FriendFragment friendFragment) {
        Call<UserDTO> getUserId = service.getUserById(userName,"Bearer " + tokenUser.getIdToken());

        getUserId.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    friendFragment.updateProfile((UserDTO) response.body());
                } else {
                    System.out.println("hola");
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                System.out.println("hoalalal");
            }
        });
    }

    public ArrayList<User> inviteRandomUser(final MainActivity mainActivity, final Profile myProfile) {
        final ArrayList<User>[] users = new ArrayList[]{new ArrayList<>()};
        Call<User[]> getAllUsers = service.getAllUsers("Bearer " + tokenUser.getIdToken());
        getAllUsers.enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    users[0] = new ArrayList<>(Arrays.asList(response.body()));
                    inviteUser(mainActivity,users[0],myProfile);
                }

            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(mainActivity.getBaseContext(), "Something happened!", Toast.LENGTH_LONG).show();
            }
        });
        return users[0];
    }

    public void inviteUser(final MainActivity mainActivity, final ArrayList <User> users, final Profile myProfile) {
        //ArrayList<User> users = getAllUsers();
        final int randomNumber = (int) Math.abs((Math.random() * (users.size()-1))%20);
        User auxUser = users.get(randomNumber);
        Call<Profile> getUserById = service.getUserProfileById(auxUser.getId(),"Bearer " + tokenUser.getIdToken());
        getUserById.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Invitation invitation = new Invitation();
                    invitation.setAccepted(true);
                    invitation.setSent(myProfile);
                    final Profile p = response.body();
                    invitation.setReceived(response.body());
                    Call<Invitation> sendInvitation = service.invitePeople(invitation, "Bearer " + tokenUser.getIdToken());
                    sendInvitation.enqueue(new Callback<Invitation>() {
                        @Override
                        public void onResponse(Call<Invitation> call, Response<Invitation> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(mainActivity.getBaseContext(), "Send to the random user the invitation to connect!!", Toast.LENGTH_LONG).show();
                                mainActivity.changeChatInformation(users.get(randomNumber),p);
                                // profileFragment.updateProfile(response.body());
                            } else {
                                Toast.makeText(mainActivity.getBaseContext(), "Something happened!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Invitation> call, Throwable t) {
                            Toast.makeText(mainActivity.getBaseContext(), "Something happened!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                }
            }
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
            }
        });
    }


    public void gotoPreviousUser (final MainActivity mainActivity, final String userName) {
        //ArrayList<User> users = getAllUsers();
        Call<UserDTO> getUserId = service.getUserById(userName,"Bearer " + tokenUser.getIdToken());

        getUserId.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    final UserDTO u = ((UserDTO)response.body());
                    Call<Profile> getUserById = service.getUserProfileById(u.getId(),"Bearer " + tokenUser.getIdToken());
                    getUserById.enqueue(new Callback<Profile>() {
                        @Override
                        public void onResponse(Call<Profile> call, Response<Profile> response) {
                            if (response.isSuccessful()) {
                                mainActivity.reloadChatInformation(u,(Profile)response.body());
                                // profileFragment.updateProfile(response.body());
                            } else {
                            }
                        }
                        @Override
                        public void onFailure(Call<Profile> call, Throwable t) {
                        }
                    });
                } else {
                    System.out.println("hola");
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                System.out.println("hoalalal");
            }
        });

    }

    public TokenUser getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(TokenUser tokenUser) {
        this.tokenUser = tokenUser;
    }

    public void blockUser(Profile blockUserName, final Context friendFragment) {
        Block block = new Block();
        Date data = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        block.setCreatedDate(simpleDateFormat.format(data).toString());
        block.setSent(userProfile);
        block.setReceived(blockUserName);

        Call<ResponseBody> blockUser = service.blockUser(block,"Bearer " + tokenUser.getIdToken());
        blockUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(friendFragment, "User Blocked!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(friendFragment, "Something happened!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    /*public int getBlocks(final String blockUserName, final FriendFragment friendFragment) {
        final Integer[] ret = new Integer[1];
        final Call<Integer> blocksCount = service.getBlocks("Bearer " + tokenUser.getIdToken());
        blocksCount.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    ret[0] = response.body();
                    ComunicationServer.getInstance().blockUser(blockUserName,friendFragment,ret);
                } else {
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
        return ret[0];
    }*/

    public Profile getProfileById(int id, final FriendFragment friendFragment) {
        final Profile[] retProfile = new Profile[1];
        Call<Profile> getUserById = service.getUserProfileById(id,"Bearer " + tokenUser.getIdToken());
        getUserById.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    friendFragment.getProfileSuccesful((Profile) response.body());
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
                    setGenders(genders);
                } else {
                }
            }
            @Override
            public void onFailure(Call<Gender[]> call, Throwable t) {
                //Toast.makeText(profileFragment.getContext(), "Fatal Error!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setGenders(ArrayList<Gender> genders) {
        this.genders = genders;
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
            if (g.getUsers() != null) {

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

    public Profile getUserProfile() {
        return userProfile;
    }
}

