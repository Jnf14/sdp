package com.github.gogetters.letsgo.game

import com.github.gogetters.letsgo.game.exceptions.IllegalMoveException
import com.github.gogetters.letsgo.game.util.InputDelegate

open class DelegatedPlayer(override val color: Stone, private val inputDelegate: InputDelegate): Player {

    override fun requestMove(board: BoardState): Move {
        return Move(color, inputDelegate.getLatestInput())
    }

    override fun notifyIllegalMove(illegalMove: IllegalMoveException) {
        return
    }

}