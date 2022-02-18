package com.example.composerecipeapp

import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val input1 = 291139
        val input2 = 29113
        val input3 = 2911
        val input4 = 291
        val input5 = 2912345
        val input6 = 21234567

        println(format(input1.toLong()))
        println(format(input2.toLong()))
        println(format(input3.toLong()))
        println(format(input4.toLong()))
        println(format(input5.toLong()))
        println(format(input6.toLong()))
    }

    fun format(value: Long): String {
        val suffixes = TreeMap<Long, String>()
        suffixes[1_000L] = "k"
        suffixes[1_000_000L] = "M"
        suffixes[1_000_0000L] = "Cr"
        suffixes[1_000_000_000L] = "G"
        suffixes[1_000_000_000_000L] = "T"
        suffixes[1_000_000_000_000_000L] = "P"
        suffixes[1_000_000_000_000_000_000L] = "E"
        // Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1)
        if (value < 0) return "-" + format(-value)
        if (value < 1000) return java.lang.Long.toString(value) // deal with easy case
        val e: Map.Entry<Long, String> = suffixes.floorEntry(value)
        val divideBy = e.key
        val suffix = e.value
        val truncated = value / (divideBy / 10) // the number part of the output times 10
        val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
        return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
    }
}
