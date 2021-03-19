package com.github.gogetters.letsgo.game

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PointTest {

    @Test
    fun equalsComparesDeep() {
        val a = Point(1, 2)
        val b = Point(1, 2)
        val c = Point(2, 3)
        assertTrue(a == b)
        assertTrue(a != c)
        assertTrue(b != c)
    }

    @Test
    fun sumOfPointsHasSummedComponents() {
        val a = Point(1, 2)
        val b = Point(3, 4)

        val sum = a + b
        assertTrue(Point(4, 6) == sum)
    }

    @Test
    fun hashcodeEqualTo31TimesFirstPlusSecond() {
        for (i in 1..10) {
            for (j in 1..10) {
                assertEquals(31 * i + j, Point(i, j).hashCode())
            }
        }
    }

    @Test
    fun toStringEquivalentToGTP() {
        //i is skipped on purpose due to the GTP protocol
        val columns = "abcdefghjklmnopqrst".toList()
        for (i in 1..19) {
            for (j in 1..19) {
                val point = Point(i, j)
                val col = columns[i - 1]
                val row = j.toString()
                assertEquals("$col$row", "$point")
            }
        }
    }
}