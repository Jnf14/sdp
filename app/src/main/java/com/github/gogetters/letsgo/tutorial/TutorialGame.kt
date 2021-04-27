package com.github.gogetters.letsgo.tutorial

import com.github.gogetters.letsgo.game.*
import com.github.gogetters.letsgo.game.Game

/**
 * A game that is used in the tutorial and that helps the given local player to learn to play Go.
 * Note that points do not matter for this game.
 */
internal class TutorialGame(private val localPlayer: TutorialLocalPlayer, private val tutorialPlayer: TutorialPlayer): Game(Board.Size.SMALL, 0.0, tutorialPlayer, localPlayer) {
    private val DEFAULT_TURN = TutorialStep(-1, true, false, emptyList(), emptyList(), emptyList())
    private var turnCount: Int = 0
    private var tutorialSteps: List<TutorialStep> = emptyList()

    init {
        // create all the steps of the tutorial
        tutorialSteps += TutorialStep(2, false, true, emptyList(), emptyList(), emptyList())
        tutorialSteps += TutorialStep(4, false, true, listOf(Move(Stone.WHITE, Point(1, 2))), listOf(Move(Stone.BLACK, Point(2, 2))), emptyList())
        // TODO
    }

    fun nextStep(): Pair<TutorialStep, BoardState> {
        turnCount++
        val step = currentStep()
        val boardState = super.reinitBoard(step.playedStones)
        localPlayer.setRecommendedMoves(step.recommendedMoves)
        tutorialPlayer.setMoves(step.tutorialPlayerMoves)
        return Pair(step, boardState)
    }
    
    fun tutorialPlayerIsNext(): Boolean {
        return super.nextPlayer.color == tutorialPlayer.color
    }

    private fun currentStep(): TutorialStep {
        var currentStep = DEFAULT_TURN
        for (step in tutorialSteps) {
            if (step.turnNumber == turnCount)
                currentStep = step
        }
        return currentStep
    }

    /**
     * Represents a step in the tutorial that can display text, the board (with some recommended moves)
     */
    class TutorialStep(val turnNumber: Int, val displayText: Boolean,  val displayBoard: Boolean, val playedStones: List<Move>, val recommendedMoves: List<Move>, val tutorialPlayerMoves: List<Point>) {
        init {
            assert(displayText || displayBoard)
        }
    }
}