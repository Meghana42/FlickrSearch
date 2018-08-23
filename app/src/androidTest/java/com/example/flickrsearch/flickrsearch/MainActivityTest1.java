package com.example.flickrsearch.flickrsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import com.android.volley.toolbox.ImageLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

/**
 * Created by Meghana Mokashi
 * Copyright (c) 2018. All rights reserved.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest1 {
    private SearchImageDataLoader mSearchDataLoader;
    private SearchImageRepository mSearchImageRepository;

    private ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Provider.set(new MockProvider());

        mRule.launchActivity(null);
        mRule.getActivity();
    }

    @Test
    public void test_searchView() {
        final List<Pair<String, ImageLoader>> list = new ArrayList<>();
        list.add(new Pair<String, ImageLoader>("test", null));

        Mockito.doReturn(list).when(mSearchImageRepository).getImageDataList();
        onView(withId(R.id.search_box)).perform(closeSoftKeyboard(), typeText("test"));
        Mockito.doNothing().when(mSearchImageRepository).searchImages("test");
        //onView(withId(R.id.search_box)).perform(click());
        onView(withText(String.format(mRule.getActivity().getString(R.string.result_string), "test"))).check(matches(isDisplayed()));
    }

    @Test
    public void test_notify() throws Throwable {
        final List<Pair<String, ImageLoader>> list = new ArrayList<>();
        list.add(new Pair<String, ImageLoader>("test", null));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Mockito.doReturn(list).when(mSearchImageRepository).getImageDataList();
                onView(withId(R.id.search_box)).perform(closeSoftKeyboard(), typeText("test"));
                onView(withId(R.id.search_box)).perform(click());

                //mRule.getActivity().notifyDataSetChanged("test");
                onView(withText(String.format(mRule.getActivity().getString(R.string.result_string), "test"))).check(matches(isDisplayed()));
            }
        });

    }

    class MockProvider extends Provider {

        @Override
        SearchImageDataLoader getSearchImageDataLoader(@NonNull Context context, @NonNull Observer observer) {
            if (mSearchDataLoader == null) {
                mSearchDataLoader = Mockito.mock(SearchImageDataLoader.class);
            }
            return mSearchDataLoader;
        }

        @Override
        SearchImageRepository getSearchImageRepository(@NonNull Context context, @NonNull Observer observer) {
            if (mSearchImageRepository == null) {
                mSearchImageRepository = Mockito.mock(SearchImageRepository.class);
            }
            return mSearchImageRepository;
        }
    }

}
