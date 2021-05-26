package com.github.gogetters.letsgo.game.util.ogs

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class OGSGameTest {

    @Test
    fun gameSerializesCorrectly() {
        val game = OGSGame("mygame")
        val json = game.toJSON()
        assertTrue(json != null)
        assertEquals(game.name, json.getString("name"))
        assertEquals(game.height, json.getString("height"))
        assertEquals(game.width, json.getString("width"))
    }

    @Test
    fun encodingAndDecodingResultsInSameGame() {
        val game = OGSGame("agame")
        assertEquals(game, OGSGame.fromJSON(game.toJSON()))
    }
}