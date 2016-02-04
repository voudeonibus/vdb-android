package com.voudeonibusapp.android.views.components;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.aux.CallbackImg;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.views.utils.ImageUtils;

import java.util.Arrays;

import de.greenrobot.event.EventBus;

public class HeaderFacebook extends Fragment {

    private View view;

    /**
     * User information
     */
    private Profile profile;


    private ImageView userAvatar;
    private TextView userName;
    private TextView userStatusLogin;
    private Button btnFacebookLogin;

    private View viewNotLogged;
    private View viewLogged;
    private View wrapper;
    private View gradient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.f_header_facebook, container, false);

        setLayoutElements();
        settingUserDate();
        setClickListener();
        EventBus.getDefault().register(this);

        return this.view;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(MessageEvent event) {
        if (event == MessageEvent.UPDATE_FACEBOOK_PROFILE) {
            settingUserDate();
        }
    }

    private void setLayoutElements() {
        this.gradient = this.view.findViewById(R.id.gradient);
        this.wrapper = this.view.findViewById(R.id.wrapper);
        this.userAvatar = (ImageView) this.view.findViewById(R.id.userAvatar);

        this.viewLogged = this.view.findViewById(R.id.viewLogged);
        this.viewNotLogged = this.view.findViewById(R.id.viewNotLogged);

        this.userName = (TextView) this.view.findViewById(R.id.userName);
        this.userStatusLogin = (TextView) this.view.findViewById(R.id.userStatusLogin);
        this.btnFacebookLogin = (Button) this.view.findViewById(R.id.btnFacebookLogin);
    }

    /**
     * Start user date
     */
    private void settingUserDate() {
        try {
            profile = Profile.getCurrentProfile();

            if (profile != null) {
                viewNotLogged.setVisibility(View.GONE);
                viewLogged.setVisibility(View.VISIBLE);
                gradient.setVisibility(View.VISIBLE);

                ImageUtils.getImageBitmap(profile.getProfilePictureUri(200, 200).toString(), new CallbackImg() {
                    @Override
                    public void onEnd(Bitmap bitmap) {
                        HeaderFacebook.this.userAvatar.setImageBitmap(bitmap);
                    }
                });


                ImageUtils.getImageBitmap(profile.getProfilePictureUri(1000, 1000).toString(), new CallbackImg() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onEnd(Bitmap bitmap) {
                        HeaderFacebook.this.wrapper.setBackground(new BitmapDrawable(getResources(), ImageUtils.blurBitmap(bitmap, HeaderFacebook.this.getActivity())));
                    }
                });


//                new StackBlurManager

                this.userName.setText(profile.getFirstName());
                this.userStatusLogin.setText("Login via Facebook");

                EventBus.getDefault().post(MessageEvent.SHOW_FACEBOOK_PROFILE);
            } else {
                viewLogged.setVisibility(View.GONE);
                gradient.setVisibility(View.GONE);
                viewNotLogged.setVisibility(View.VISIBLE);
                wrapper.setBackground(null);
//                wrapper.setBackgroundColor(R.color.transparent);
                EventBus.getDefault().post(MessageEvent.HIDE_FACEBOOK_PROFILE);
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }

    }

    private void setClickListener() {
        this.btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(HeaderFacebook.this.getActivity(), Arrays.asList("public_profile", "user_friends"));
            }
        });
    }
}
