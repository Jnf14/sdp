package com.github.gogetters.letsgo.tutorial

import com.github.gogetters.letsgo.game.util.InputDelegate
import org.junit.Assert.assertTrue
import org.junit.Test

class TutorialGameTest {
    @Test
    fun defaultStepContainsNoStonesOnBoard() {
        val inputDelegate = InputDelegate()
        val player = TutorialLocalPlayer(inputDelegate)
        val tutorialPlayer = TutorialPlayer()
        val game = TutorialGame(player, tutorialPlayer)

        val (step, boardState) = game.nextStep()
        assertTrue(step.playedStones.isEmpty())
    }
}