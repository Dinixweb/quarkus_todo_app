package io.dinix.constants

import kotlin.random.Random


class GlobalFunction {
    private val random = Random(10);
    fun generatedUserId():String{
        val id = StringBuilder()
        repeat(4){
            val randomDigit = random.nextInt(10)
            id.append(randomDigit)
        }
        return id.toString();
    }
}