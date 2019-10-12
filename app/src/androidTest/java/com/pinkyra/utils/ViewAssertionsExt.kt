package com.pinkyra.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun ViewInteraction.hasItemCount(itemCount: Int): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(
        RecyclerView::class.java
    ) {

        override fun describeTo(description: Description) {
            description.appendText("has $itemCount items")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            return view.adapter?.itemCount == itemCount
        }
    }
}

fun ViewInteraction.isRefreshing(): Matcher<View> {
    return object : BoundedMatcher<View, SwipeRefreshLayout>(
        SwipeRefreshLayout::class.java
    ) {

        override fun describeTo(description: Description) {
            description.appendText("is refreshing")
        }

        override fun matchesSafely(view: SwipeRefreshLayout): Boolean {
            return view.isRefreshing
        }
    }
}