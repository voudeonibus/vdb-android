package com.voudeonibus.views.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.voudeonibus.R;
import com.voudeonibus.enums.ETypeActivity;
import com.voudeonibus.enums.ETypeHeader;
import com.voudeonibus.event.MessageEvent;

import com.voudeonibus.event.MessageEventObject;
import com.voudeonibus.models.api.Line;
import com.voudeonibus.views.components.CategoryTutorialFragment;
import com.voudeonibus.views.components.HeaderBase;
import com.voudeonibus.views.components.HeaderDetailsLine;
import com.voudeonibus.views.components.HeaderNormal;

import com.voudeonibus.views.components.LoadingFragment;
import com.voudeonibus.views.components.NotificationFragment;
import com.voudeonibus.views.components.ChoiceCategoryFragment;
import com.voudeonibus.views.components.Sidebar;
import com.voudeonibus.views.utils.TranstionBackground;

import de.greenrobot.event.EventBus;

public class BaseActivity extends FragmentActivity {

    protected int idLayout = R.layout.a_base;


    public static int HEIGHT_HEADER;
    private View wrapper;
    private RelativeLayout mainContent;
    private Sidebar sidebar;

    private HeaderNormal fragmentHeaderNormal;
    private HeaderDetailsLine fragmentHeaderDetailsLine;

    protected String labelBackButton;

    protected boolean isCategory;

    protected String title;
    protected int color;
    private boolean executeSidebar = false;

    private int animationOnTransition = 0;
    protected boolean showTitleImg;

    public ETypeActivity eTypeActivity = ETypeActivity.MAIN;
    public ETypeHeader eTypeHeader = ETypeHeader.NORMAL;


    public NotificationFragment fragment_notification;
    public LoadingFragment fragment_loading;
    public ChoiceCategoryFragment fragment_choice_category;
    public CategoryTutorialFragment fragment_category_tutorial;

    private CallbackManager callbackManager;

    private View.OnClickListener clickCustomBackListener;

    public BaseActivity() {
        super();
    }

    public BaseActivity(ETypeActivity eTypeActivity) {
        this();
        this.eTypeActivity = eTypeActivity;
    }

    public BaseActivity(boolean executeSidebar) {
        this();
        this.executeSidebar = executeSidebar;
    }

    public BaseActivity(String title) {
        this();
        this.title = title;
    }

    public BaseActivity(String title, boolean executeSidebar) {
        this(title);
        this.executeSidebar = executeSidebar;
    }

    public BaseActivity(String title, ETypeActivity eTypeActivity) {
        this(title);
        this.eTypeActivity = eTypeActivity;
    }

    public BaseActivity(String title, ETypeActivity eTypeActivity, View.OnClickListener clickCustomBackListener) {
        this(title, eTypeActivity);
        this.clickCustomBackListener = clickCustomBackListener;
    }

    public BaseActivity(String title, ETypeActivity eTypeActivity, boolean executeSidebar) {
        this(title, eTypeActivity);
        this.executeSidebar = executeSidebar;
    }

    public BaseActivity(String title, int color) {
        this(title);
        this.color = color;
    }

    public BaseActivity(String title, int color, boolean executeSidebar) {
        this(title, color);
        this.executeSidebar = executeSidebar;
    }

    public BaseActivity(String title, int color, ETypeActivity eTypeActivity) {
        this(title, color);
        this.eTypeActivity = eTypeActivity;
    }

    public BaseActivity(String title, int color, ETypeActivity eTypeActivity, boolean executeSidebar) {
        this(title, color, eTypeActivity);
        this.executeSidebar = executeSidebar;
    }

    public BaseActivity(ETypeHeader eTypeHeader) {
        this();
        this.eTypeHeader = eTypeHeader;
    }

    public BaseActivity(ETypeHeader eTypeHeader, boolean executeSidebar) {
        this(eTypeHeader);
        this.executeSidebar = executeSidebar;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(MessageEvent event){

        if (event == MessageEvent.HIDE_SIDEBAR || event == MessageEvent.SHOW_SIDEBAR) {
            this.sidebar.getSwipe().toggle();
        }

        if (event == MessageEvent.SHOW_FACEBOOK_PROFILE) {
            this.sidebar.showHideLogoff(true);
        } else if (event == MessageEvent.HIDE_FACEBOOK_PROFILE) {
            this.sidebar.showHideLogoff(false);
        }

        if (event == MessageEvent.APPLY_BACKGROUND_DEFAULT) {
            TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
            transition.startTransition(TranstionBackground.TIME);
        }

        if (event == MessageEvent.REVERSE_BACKGROUND) {
            TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
            transition.reverseTransition(TranstionBackground.TIME);
        }

//        if (event == MessageEvent.APPLY_BG_LEVEL_0) {
//            wrapper.getBackground().setLevel(0);
//        }
//
//        if (event == MessageEvent.APPLY_BG_LEVEL_1) {
//            wrapper.getBackground().setLevel(1);
//        }
//
//        if (event == MessageEvent.APPLY_BG_LEVEL_2) {
//            wrapper.getBackground().setLevel(2);
//        }
//
//        if (event == MessageEvent.APPLY_BG_LEVEL_3) {
//            wrapper.getBackground().setLevel(3);
//        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(idLayout);
        setLayoutElements();
        setContent();
        setClickListener();
        setFacebookRule();

        // Solução para deixar topo do Android com cores personalizadas

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        EventBus.getDefault().post(new MessageEventObject(MessageEvent.GET_VIEW, wrapper));
    }

    private void setLayoutElements() {
        mainContent = (RelativeLayout) findViewById(R.id.mainContent);

        fragmentHeaderNormal = (HeaderNormal) getSupportFragmentManager().findFragmentById(R.id.fragment_header_normal);
        fragmentHeaderDetailsLine = (HeaderDetailsLine) getSupportFragmentManager().findFragmentById(R.id.fragment_header_details_line);
        fragment_notification = (NotificationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_notification);
        fragment_loading = (LoadingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_loading);
        fragment_choice_category = (ChoiceCategoryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_choise_category);
        fragment_category_tutorial = (CategoryTutorialFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_category_tutorial);

        fragmentHeaderNormal.setAnimationOnTransition(animationOnTransition);
        fragmentHeaderDetailsLine.setAnimationOnTransition(animationOnTransition);
        fragmentHeaderNormal.setClickCustomBackListener(clickCustomBackListener);
        fragmentHeaderDetailsLine.setClickCustomBackListener(clickCustomBackListener);

        fragmentHeaderNormal.setExecuteSidebar(executeSidebar);
        fragmentHeaderDetailsLine.setExecuteSidebar(executeSidebar);

        sidebar = new Sidebar(this, executeSidebar);
        wrapper = findViewById(R.id.wrapper);

        fragmentHeaderNormal.setTitle(this.title);
        fragmentHeaderNormal.setColor(this.color);
        fragmentHeaderNormal.setSidebar(this.sidebar);
        fragmentHeaderNormal.setShowTitleImg(this.showTitleImg);

        fragmentHeaderDetailsLine.setTitle(this.title);
        fragmentHeaderDetailsLine.setColor(this.color);
        fragmentHeaderDetailsLine.setSidebar(this.sidebar);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
         fragmentHeaderNormal.removePaddingTop();
            fragmentHeaderDetailsLine.removePaddingTop();
        }
    }

    public void setLine(Line line) {
        fragmentHeaderDetailsLine.setLine(line);
    }


    private void setContent() {
        if (this.color != 0) {
            wrapper.setBackgroundColor(this.color);
        }

        ViewGroup.LayoutParams lp = wrapper.getLayoutParams();

//        if (eTypeHeader == ETypeHeader.DETAILS_LINE) {
//
//            lp.height = (int) getResources().getDimension(R.dimen.toolbar_height_details_line);
//
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.hide(fragmentHeaderNormal);
//            ft.show(fragmentHeaderDetailsLine);
//            ft.commit();
//        } else {

        lp.height = (int) getResources().getDimension(R.dimen.toolbar_height);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragmentHeaderNormal);
        ft.hide(fragmentHeaderDetailsLine);
        ft.commit();
//        }

        wrapper.setLayoutParams(lp);
        HEIGHT_HEADER = lp.height;
    }

    protected void reloadHeader() {
        setContent();
    }

    @Override
    public void setContentView(int id) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(id, this.mainContent);

    }

    private void setFacebookRule() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        EventBus.getDefault().post(MessageEvent.UPDATE_FACEBOOK_PROFILE);
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Teste", "deu errp");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("ErrorF", exception.toString());
                    }
                }
        );

        Profile profile = Profile.getCurrentProfile();

        if (profile != null) {
            this.sidebar.showHideLogoff(true);
        } else {
            this.sidebar.showHideLogoff(false);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setClickListener() {

    }

    public void setClickCustomBackListener(View.OnClickListener clickCustomBackListener) {
        this.clickCustomBackListener = clickCustomBackListener;

        if (fragmentHeaderNormal != null && fragmentHeaderDetailsLine != null) {
            fragmentHeaderNormal.setClickCustomBackListener(clickCustomBackListener);
            fragmentHeaderDetailsLine.setClickCustomBackListener(clickCustomBackListener);
        }
    }

    public void setLabelBackButton(String labelBackButton) {
        this.labelBackButton = labelBackButton;
        fragmentHeaderNormal.setLabelBackButton(this.labelBackButton);
        fragmentHeaderDetailsLine.setLabelBackButton(this.labelBackButton);
    }

    public void setAnimationOnTransition(int animationOnTransition) {
        this.animationOnTransition = animationOnTransition;
    }

    public void setTitle(String title) {
        this.title = title;
        fragmentHeaderNormal.setTitle(this.title);
        fragmentHeaderDetailsLine.setTitle(this.title);
    }

    @Override
    public void onBackPressed() {
        if (HeaderBase.NOTIFICATION_IS_OPEN) {
            EventBus.getDefault().post(MessageEvent.HIDE_NOTIFICATION);
        } else {
            super.onBackPressed();
        }
    }
}
