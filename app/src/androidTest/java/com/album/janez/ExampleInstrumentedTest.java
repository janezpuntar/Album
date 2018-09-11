package com.album.janez;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.album.janez.album.activity.main.MainActivity;
import com.album.janez.application.AlbumApplication;
import com.album.janez.application.AlbumModule;
import com.album.janez.application.DaggerAlbumComponent;
import com.album.janez.data.model.presentation.Album;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.album.janez", appContext.getPackageName());
    }

    /**
     * Verify that data exist on the internet.
     */
    @Test
    public void useNetwork() {
        List<Album> data = DaggerAlbumComponent.builder()
                .albumModule(new AlbumModule((AlbumApplication) rule.getActivity().getApplication())).build()
                .getAlbumRepository().getAlbums().blockingGet();

        assertNotNull(data);
        assertTrue(!data.isEmpty());
        assertEquals(100, data.size());
    }
}
