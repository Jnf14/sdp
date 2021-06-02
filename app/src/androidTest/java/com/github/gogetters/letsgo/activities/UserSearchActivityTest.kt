package com.github.gogetters.letsgo.activities

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.github.gogetters.letsgo.database.EmulatedFirebaseTest
import com.github.gogetters.letsgo.database.user.LetsGoUser
import com.github.gogetters.letsgo.testUtil.TestUtils
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSearchActivityTest : EmulatedFirebaseTest() {

    val intent = Intent(ApplicationProvider.getApplicationContext(), UserSearchActivity::class.java)
    lateinit var scenario: ActivityScenario<UserSearchActivity>

    @Before
    fun init() {
        Intents.init()
        scenario = ActivityScenario.launch(intent)
        TestUtils.makeSureTestUserAuthenticated()
    }

    @After
    fun cleanUp() {
        Intents.release()
        scenario.close()
    }

    @Test
    fun existingMatchingUserIsDisplayed() {
        val nick = "Nicky"
        val otherUser = LetsGoUser("test2")
        otherUser.nick = nick
        Tasks.await(otherUser.uploadUserData())

        val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val searchField = device.findObject(UiSelector().className("android.widget.SearchView"))
        searchField.text = "Nic"
        val foundUser: UiObject = device.findObject(
            UiSelector().textContains(nick)
        )
        assertTrue(foundUser.exists())
    }

    @Test
    fun existingUnmatchingUserIsNotDisplayed() {
        val nick = "Nicky"
        val otherUser = LetsGoUser("test2")
        otherUser.nick = nick
        Tasks.await(otherUser.uploadUserData())

        val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val searchField = device.findObject(UiSelector().className("android.widget.SearchView"))
        searchField.text = "Random"
        val foundUser: UiObject = device.findObject(
            UiSelector().textContains(nick)
        )
        assertFalse(foundUser.exists())
    }

    @Test
    fun canSendFriendRequestToFoundUser() {
        val nick = "Nicky"
        val otherUser = LetsGoUser("test2")
        otherUser.nick = nick
        Tasks.await(otherUser.uploadUserData())
        val device: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val searchField = device.findObject(UiSelector().className("android.widget.SearchView"))
        searchField.click()
        searchField.text = "Nic"
        val foundUser: UiObject = device.findObject(
            UiSelector().textContains(nick).clickable(true)
        )
        if (foundUser.exists()) {
            foundUser.click()

        }
    }

    @Test
    fun friendIsNotDisplayed() {

    }

}