package com.example.androidsocialnetwork.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.androidsocialnetwork.Callbacks.Callbacks;
import com.example.androidsocialnetwork.Model.DirectMessage;
import com.example.androidsocialnetwork.R;
import com.example.androidsocialnetwork.ServerComunication.ComunicationServer;
import com.example.androidsocialnetwork.Threads.ThreadPutImage;
import com.example.androidsocialnetwork.Threads.ThreadUpdateChat;
import com.example.androidsocialnetwork.Threads.ThreaduploadImageCloud;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class ChatFragment extends Fragment {

    private ImageView backButton;
    private ImageView profilePic;
    private TextView userName;
    private ImageView infoButton;
    private ImageView settingsButton;
    private TextView chatView;
    private ImageView galleryButton;
    private ImageView emojiButton;
    private EmojiconEditText chatText;
    private ImageView sendButton;
    private ScrollView scroll;
    private Callbacks mCallbacks;
    private String realusername;
    private LinearLayout relativeLayout;
    private Integer idUser;
    private final int PERMISSIONS_REQUEST = 1;
    private Activity activity;
    private boolean hePuestoImage;
    private ThreadUpdateChat threadUpdateChat;
    private String nameUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
        activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_layout, container, false);
        relativeLayout = v.findViewById(R.id.chat_layout_2);
        backButton = v.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.returnToMainMenu();
            }
        });

        scroll = v.findViewById(R.id.scroll_view_chat);


        profilePic = v.findViewById(R.id.profile_pic);


        userName = v.findViewById(R.id.userName);

        infoButton = v.findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (threadUpdateChat.isAlive()) {
                    threadUpdateChat.interrupt();
                }
                mCallbacks.obtainFriendInformation();
            }
        });



        final ChatFragment chatFragment = this;

        chatText = v.findViewById(R.id.emojicon_edit_text);

        galleryButton = v.findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 79);
            }
        });


        sendButton = v.findViewById(R.id.submit_btn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeMyMessages(chatText.getText().toString());
                ComunicationServer.getInstance().sendMessage(chatFragment,chatText.getText().toString(),idUser);
                chatText.setText("");
            }
        });

        ImageView emoticono = (ImageView) v.findViewById(R.id.emoji_btn);
        EmojiconEditText emojiconEditText = (EmojiconEditText) v.findViewById(R.id.emojicon_edit_text);
        ImageView send = (ImageView) v.findViewById(R.id.submit_btn);
        View root = (View) v.findViewById(R.id.root_view);
        EmojIconActions emojIconActions = new EmojIconActions(getContext(),root,emojiconEditText,emoticono,"#F44336","#e8e8e8","#f4f4f4");
        emojIconActions.ShowEmojIcon();



        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST);



        return v;
    }

    public void changeInformation(String nameUser, String photo,Integer id) {
        if (photo != null ) {
            byte[] imageBytes;
            imageBytes = Base64.decode(photo, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Glide.with(getContext()).asBitmap().load(decodedImage).apply(RequestOptions.circleCropTransform()).into(profilePic);
        }
        userName.setText(nameUser);
        idUser = id;
        ComunicationServer.getInstance().getMessagesYouAndFriend(this,idUser);
        scroll.setScrollY(View.FOCUS_DOWN);
        this.nameUser = nameUser;
    }


    public String getRealusername() {
        return realusername;
    }

    public void setRealusername(String realusername) {
        this.realusername = realusername;
    }


    public void includeChats (ArrayList <DirectMessage> messages) {
        relativeLayout.removeAllViews();
        //Toast.makeText(this.getContext(),"Messages loaded", Toast.LENGTH_SHORT).show();
        Collections.sort(messages, new Comparator<DirectMessage>() {
            @Override
            public int compare(DirectMessage o1, DirectMessage o2) {
                return o1.compareTo(o2);
            }
        });
        int i = 0;
        recorrido (0,messages);

    }

    public void recorrido (int i,ArrayList <DirectMessage> messages) {
        while (i < messages.size()) {

            if (!messages.get(i).getSender().getDisplayName().equals(nameUser)) {
                if (!messages.get(i).getUrl().equals("")) {
                    ThreadPutImage threadPutImage = new ThreadPutImage(messages.get(i).getUrl(),this,activity,i+1,messages,true, true);
                    hePuestoImage = false;
                    threadPutImage.start();
                    break;
                }
                else {
                    includeMyMessages(messages.get(i).getMessage());
                }
            }
            else {
                if (!messages.get(i).getUrl().equals("")) {
                    ThreadPutImage threadPutImage = new ThreadPutImage(messages.get(i).getUrl(),this,activity,i+1,messages,true,false);
                    hePuestoImage =false;
                    threadPutImage.start();
                    break;
                }
                else {
                    includeMessageFriend(messages.get(i).getMessage());
                }
            }
            i++;

        }
        if (i == messages.size()) {
            threadUpdateChat = new ThreadUpdateChat(this, idUser);
            threadUpdateChat.start();
        }
    }

    private void includeMessageFriend (String message) {
        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this.getContext());
        tv.setLayoutParams(lparams);
        tv.setBackgroundResource(R.drawable.incoming_message_bubble);
        tv.setText(message);
        tv.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tv.setLayoutParams(params);

        tv.setPadding(55,30,20,30);
        relativeLayout.addView(tv);
    }
    private void includeMyMessages (String message) {
        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(lparams);
        tv.setBackgroundResource(R.drawable.outgoing_message_bubble);
        tv.setText(message);
        tv.setGravity(Gravity.RIGHT);
        tv.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.RIGHT;
        tv.setLayoutParams(params);

        tv.setPadding(20,30,55,30);
        relativeLayout.addView(tv);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == 79){
            Uri imageUri = data.getData();
            //lastUri = imageUri.toString();
            //profilePhoto.setImageURI(imageUri);



            String[] proj = { MediaStore.Images.Media.DATA };

            String result = null;
            Cursor cursor = getContext().getContentResolver().query(imageUri, proj, null, null, null );
            if(cursor != null){
                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                    result = cursor.getString( column_index );
                }
                cursor.close();
            }


            ThreaduploadImageCloud threaduploadImageCloud = new ThreaduploadImageCloud(result,this);

            threaduploadImageCloud.start();


            //extraemos el drawable en un bitmap
            //Glide.with(getContext()).load(imageUri).apply(RequestOptions.circleCropTransform()).into(profilePhoto);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    }

    public void sendImage (String url,String type) {
        ComunicationServer.getInstance().sendImages (this,url,type,idUser);
    }

    public void loadImage (Bitmap bmp,ArrayList <DirectMessage> messages,int i,boolean b, boolean sender) {
        ImageView tv = new ImageView(getContext());
        ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lparams);
        if (sender) {
            tv.setBackgroundResource(R.drawable.outgoing_message_bubble);
        }
        else {
            tv.setBackgroundResource(R.drawable.incoming_message_bubble);
        }
        tv.setImageBitmap(bmp);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (sender) {
            params.gravity = Gravity.RIGHT;
        }
        tv.setLayoutParams(params);
        tv.getLayoutParams().height =300;
        tv.getLayoutParams().width = 300;

        tv.setPadding(20,30,55,30);
        relativeLayout.addView(tv);
        if (b) {
            recorrido(i, messages);
        }
    }


    public boolean isHePuestoImage() {
        return hePuestoImage;
    }

    public void setHePuestoImage(boolean hePuestoImage) {
        this.hePuestoImage = hePuestoImage;
    }

    public void mostrarImagenDespuesEnvio (String url) {
        ThreadPutImage threadPutImage = new ThreadPutImage(url,this,activity,0,new ArrayList<DirectMessage>(),false,true);
        threadPutImage.start();
    }
    public void disconnectThread() {
        if (threadUpdateChat != null) {
            if (threadUpdateChat.isAlive()) {
                threadUpdateChat.interrupt();
            }
        }
        else {
            Toast.makeText(getContext(),"Estava en NULL",Toast.LENGTH_SHORT).show();
        }
    }
}
