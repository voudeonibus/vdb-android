package com.voudeonibusapp.android.views.components;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.voudeonibusapp.android.R;
import com.voudeonibusapp.android.controller.WeatherController;
import com.voudeonibusapp.android.enums.ETime;
import com.voudeonibusapp.android.event.MessageEvent;
import com.voudeonibusapp.android.models.aux.Weather;
import com.voudeonibusapp.android.views.utils.TimeUtils;
import com.voudeonibusapp.android.views.utils.TranstionBackground;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;


public class WeatherView extends Fragment {

    protected View view;

    private Weather weather;

    private View wrapper;
    protected TextView weatherTempText;
    protected ImageView weatherThumbImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent == MessageEvent.UPDATE_WEATHER) {
            updateWeather();
        }

        if (messageEvent == MessageEvent.APPLY_BACKGROUND_DEFAULT) {
            TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
            transition.startTransition(TranstionBackground.TIME);
        }

        if (messageEvent == MessageEvent.REVERSE_BACKGROUND) {
            TransitionDrawable transition = (TransitionDrawable) wrapper.getBackground();
            transition.reverseTransition(TranstionBackground.TIME);
        }
    }

    private void updateWeather() {

        if (isOnline()) {

            try {
                weather = WeatherController.get();

                if (weather != null) {
                    this.weatherTempText.setText(String.valueOf((int) Math.round(weather.getTemp())) + "° C");

                    ETime eTime = TimeUtils.getTime();

                    Log.d("Ops", weather.getDescription());

                    switch (weather.getDescription()) {
                        case "clear sky":

                            if (eTime == ETime.DAY) {
                                this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_clear_day));
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);

                            } else {
                                this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_clear_nitght));
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }

                            break;
                        case "broken clouds":
                        case "few clouds":
                        case "overcast clouds":

                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_broken_clouds));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(3);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_3);
                            }

                            break;

                        case "scattered clouds":
                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_cloudy));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }

                            break;

                        case "rain":
                        case "shower rain":
                        case "heavy intensity drizzle rain":
                        case "shower rain and drizzle":
                        case "heavy shower rain and drizzle":
                        case "shower drizzle":
                        case "moderate rain":
                        case "heavy intensity rain":
                        case "very heavy rain":
                        case "extreme rain":
                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_rain));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }


                            break;

                        case "light intensity drizzle":
                        case "drizzle":
                        case "light rain":
                        case "heavy intensity drizzle":
                        case "light intensity drizzle rain":
                        case "drizzle rain":
                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_drizzle));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }

                            break;

                        case "mist":
                        case "smoke":
                        case "haze":
                        case "sand, dust whirls":
                        case "fog":
                        case "sand":
                        case "dust":
                        case "volcanic ash":
                        case "squalls":
                        case "tornado":
                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_fog));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }

                            break;

                        case "snow":
                        case "freezing rain":
                        case "light snow":
                        case "heavy snow":
                        case "sleet":
                        case "light rain and snow":
                        case "shower sleet":
                        case "rain and snow":
                        case "light shower snow":
                        case "shower snow":
                        case "heavy shower snow":
                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_hail));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }

                            break;

                        case "thunderstorm":
                        case "thunderstorm with light rain":
                        case "thunderstorm with rain":
                        case "thunderstorm with heavy rain":
                        case "light thunderstorm":
                        case "heavy thunderstorm":
                        case "ragged thunderstorm":
                        case "thunderstorm with light drizzle":
                        case "thunderstorm with drizzle":
                        case "thunderstorm with heavy drizzle":
                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_thunderstorm));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }


                            break;

                        default:
                            this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_clear_day));

                            if (eTime == ETime.DAY) {
                                wrapper.getBackground().setLevel(0);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);
                            } else {
                                wrapper.getBackground().setLevel(1);
                                EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);
                            }


                            break;
                    }

                } else {
                    setOffline();
                }
            } catch (Exception e) {
                Crashlytics.logException(e);
                setOffline();
            }
        } else {
            setOffline();
        }

    }

    private void setOffline() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String currentDateandTime = sdf.format(new Date());
        int hour = Integer.parseInt(currentDateandTime);

        if (hour < 12) {

            this.weatherTempText.setText("Bom dia");
            wrapper.getBackground().setLevel(0);
            EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);

        } else if (hour > 11 && hour < 18) {

            this.weatherTempText.setText("Boa tarde");
            wrapper.getBackground().setLevel(0);
            EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);

        } else if (hour > 17 && hour < 24) {

            this.weatherTempText.setText("Boa noite");
            wrapper.getBackground().setLevel(1);
            EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_1);

        } else {

            this.weatherTempText.setText("Bom dia");
            wrapper.getBackground().setLevel(0);
            EventBus.getDefault().post(MessageEvent.APPLY_BG_LEVEL_0);

        }


//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(20, 30, 0, 0);
//
//        this.weatherTempText.setLayoutParams(lp);

        this.weatherThumbImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_weather_offline));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.vou_agora_weather, container, false);

        setLayoutElements();
        setContent();

        return this.view;
    }

    private void setLayoutElements() {
        this.wrapper = this.view.findViewById(R.id.wrapper);
        this.weatherTempText = (TextView) this.view.findViewById(R.id.weatherTempText);
        this.weatherThumbImage = (ImageView) this.view.findViewById(R.id.weatherThumbImage);
    }

    private void setContent() {
        updateWeather();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
