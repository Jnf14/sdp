package com.github.gogetters.letsgo.activities

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.gogetters.letsgo.database.EmulatedFirebaseTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FriendListActivityTest: EmulatedFirebaseTest() {

    @get:Rule
    var activityRule = ActivityScenarioRule(FriendListActivity::class.java)

    @After
    fun cleanUp() {
        activityRule.scenario.close()
    }

    @Test
    fun friendListTest() {}
}