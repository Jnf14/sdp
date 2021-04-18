package com.github.gogetters.letsgo.game

import android.util.Log
import com.github.gogetters.letsgo.game.util.InputDelegate
import com.github.gogetters.letsgo.game.exceptions.IllegalMoveException

open class LocalPlayer(color: Stone, private val inputDelegate: InputDelegate): Player(color) {

    override fun requestMove(board: BoardState): Move {
        return Move(color, inputDelegate.latestInput)
    }

    override fun notifyIllegalMove(illegalMove: IllegalMoveException) {
        Log.d("LOCAL_PLAYER", "PLAYER HAS PLAYED ILLEGAL MOVE", illegalMove)

    }

}