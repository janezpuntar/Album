package com.album.janez.network;

import io.reactivex.Single;

public interface INetworkManager {

    /**
     * Check network connection.
     *
     * @return true if device is connected to the network otherwise false.
     */
    Single<Boolean> isOnline();
}
