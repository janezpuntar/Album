package com.album.janez.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.album.janez.R;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class NetworkManager {

    private Context context;

    public NetworkManager(Context context) {
        this.context = context;
    }

    public Single<Boolean> isOnline() {

        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                try {
                    NetworkInfo netInfo = cm.getActiveNetworkInfo();

                    if (netInfo != null && netInfo.isConnected()) {
                        emitter.onSuccess(true);
                    } else {
                        emitter.onError(new Throwable(context.getString(R.string.no_internet_connection)));
                    }
                } catch (NullPointerException e) {
                    emitter.onError(e);
                }
            }
        });
    }

}
