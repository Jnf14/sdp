package com.github.gogetters.letsgo.game

/**
 * Serialization/Deserialization class used to communicate with any GTP compatible engine/website.
 * Contains helper classes that serialize into GTP-compatible commands.
 */
sealed class GTPCommand {

    //TODO: add ID values

    //TODO: turn de/serialize into tostring and tocommand, provide actual serialization

    // SETUP COMMANDS -------------------------
    data class BOARD_SIZE(val boardSize: Board.Size) : GTPCommand() {
        override fun toString(): String {
            return String.format("boardsize %d", boardSize.size)
        }
    }

    object CLEAR_BOARD : GTPCommand() {
        override fun toString(): String {
            return "clear_board"
        }
    }

    data class KOMI(val new_komi: Float) : GTPCommand() {
        override fun toString(): String {
            return String.format("komi %f", new_komi)
        }
    }

    data class FIXED_HANDICAP(val numberOfStones: Int) : GTPCommand() {
        override fun toString(): String {
            return String.format("fixed_handicap %d", numberOfStones)
        }
    }

    data class PLACE_FREE_HANDICAP(val numberOfStones: Int) : GTPCommand() {
        override fun toString(): String {
            return String.format("place_free_handicap %d", numberOfStones)
        }
    }

    data class SET_FREE_HANDICAP(val vertices: List<Point>) : GTPCommand() {
        //TODO: adapt toString for vertices
        override fun toString(): String {
            return String.format("place_free_handicap %s", vertices.toString())
        }
    }
    // CORE PLAY COMMANDS -------------------------

    data class PLAY(val move: Move) : GTPCommand() {
        //TODO: proper toString for moves
        override fun toString(): String {
            return String.format("play %s", move.toString())
        }
    }


    data class GENMOVE(val color: Stone) : GTPCommand() {
        //TODO: need to properly convert colors to strings, also handle empty case
        override fun toString(): String {
            return String.format("move %s", color.toString())
        }
    }

    object UNDO : GTPCommand() {
        override fun toString(): String {
            return "undo"
        }
    }


    /**
     * Serializes command with given id
     */
    fun toString(id: Int): String {
        //TODO: verify ID
        return String.format("%d %s", id, toString())
    }

    companion object {
        /**
         * Command deserialization helper function.
         */
        fun toCommand(s: String): GTPCommand {
            val decomposed = s.split(" ")

            //TODO: create new exception for this case
            if (decomposed.isEmpty())
                throw Error("no valid command detected")

            val command = decomposed[0]
            val args = decomposed.drop(1)

            return when (command) {
                "boardsize" -> if (args.isEmpty()) throw Error() else return BOARD_SIZE(
                    Board.Size.withSize(
                        args[0].toInt()
                    )
                )
                "clear_board" -> CLEAR_BOARD
                "komi" -> if (args.isEmpty()) throw Error() else return KOMI(args[0].toFloat())
                "fixed_handicap" -> if (args.isEmpty()) throw Error() else return FIXED_HANDICAP(
                    args[0].toInt()
                )
                "place_free_handicap" -> if (args.isEmpty()) throw Error() else return PLACE_FREE_HANDICAP(
                    args[0].toInt()
                )
                "set_free_handicap" -> if (args.isEmpty()) throw Error() else TODO("need to convert strings to points")
                "play" -> if (args.isEmpty()) throw Error() else PLAY(parseMove(args))
                "genmove" -> TODO("need to create moves from Strings")
                "undo" -> UNDO
                else -> throw Error("no valid command detected")
            }
        }

        /**
         *  Parses a move of a given list of strings. The first string describes the color, the second the point on the board.
         */
        fun parseMove(args: List<String>): Move {
            if (args.size != 2) {
                throw IllegalArgumentException("invalid number of arguments (need 2)")
            }

            val colorString = args[0]
            val pointString = args[1]

            val color = Stone.fromString(colorString)
            val point = Point.fromString(pointString)

            return Move(color, point)
        }
    }
}