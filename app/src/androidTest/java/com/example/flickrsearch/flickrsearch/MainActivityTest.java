package com.example.flickrsearch.flickrsearch;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private SearchImageDataLoader mSearchDataLoader;
    private ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Provider.set(new MockProvider());
        mRule.launchActivity(null);
        mRule.getActivity();
    }

    @Test
    public void test_onListUpdated() throws Throwable {
        //-----------When---------------------------

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRule.getActivity().onListUpdated("test");
            }
        });

        //-----------Then---------------------------

        onView(withId(R.id.progress_bar_layout)).check(matches(not(isDisplayed())));
        onView(withId(R.id.result_text)).check(matches(isDisplayed()));
    }

    class MockProvider extends Provider {

        @Override
        SearchImageDataLoader getSearchImageDataLoader(@NonNull Context context) {
            if (mSearchDataLoader == null) {
                mSearchDataLoader = Mockito.mock(SearchImageDataLoader.class);
            }
            return mSearchDataLoader;
        }

        @Override
        SearchImageRepository getSearchImageRepository(@NonNull Context context) {
            try {
                return new MockRepository(context);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class MockRepository extends SearchImageRepository {
        final JSONObject mResult = new JSONObject();

        MockRepository(@NonNull Context context) throws JSONException {
            super(context);
            mResult.put("farm", "farm");
            mResult.put("server", "server");
            mResult.put("id", "id");
            mResult.put("secret", "secret");
        }

        @Override
        void setListener(@NonNull ImageListListener listener) { }

        @Override
        public List<ImageDataHolder> getImageDataHolders() {
            final List<ImageDataHolder> list = new ArrayList<>();
            list.add(new ImageDataHolder(mResult));
            return list;
        }
    }
}
