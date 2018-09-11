package com.album.janez.album.activity.main;

public interface ActionBarEventListener {

    /**
     * Set title to action bar.
     *
     * @param title to be set on action bar
     */
    void setTitle(String title);

    /**
     * Show back button on action bar.
     */
    void showBackButton();

    /**
     * Hide back button on action bar.
     */
    void hideBackButton();

    /**
     * Close search menu item and clear query text.
     */
    void closeSearch();
}
