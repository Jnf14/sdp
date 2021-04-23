package com.github.gogetters.letsgo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.gogetters.letsgo.database.Database
import com.google.firebase.FirebaseApp
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    init {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        FirebaseApp.initializeApp(appContext)
    }

    @Test
    fun disableLocationSharingDoesntThrow() {
        Database.goOffline()
        Database.disableLocationSharing()
        Database.purgeOutstandingWrites()
        Database.goOnline()
    }

    @Test
    fun getAllLocationsDoesntThrow() {
        Database.goOffline()
        Database.getAllLocations()
        Database.disableLocationSharing()
        Database.goOnline()
    }

    @Test
    fun listenToMessages() {
        Database.goOffline()

        val chatId = "fakeChatId"

        val listener = Database.addMessagesListener(chatId) {
            // do nothing
        }

        Database.sendMessage("fakeSenderId", chatId, "fakeText", {
            // do nothing
        }, {
            // do nothing
        })

        Database.removeMessagesListener(chatId, listener)

        Database.goOnline()
    }

    fun writeValue() {
        Database.goOffline()

        Database.writeValue("asdf", "fakeval", {
            Database.purgeOutstandingWrites()
        }, {
            Database.purgeOutstandingWrites()
        })

        Database.goOnline()
    }
}