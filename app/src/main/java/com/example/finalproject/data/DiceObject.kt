package com.example.finalproject.data

import android.util.Log
import com.example.finalproject.R
import dev.chauvin.dicetray.core.dice.Die


data class DiceObject(
    val dice: Die<Int>,
    var Lastroll:Int,
    var currentImage: Int
){
    fun roll(){
        var result = dice.roll(1)

        Lastroll = result[0].value

        currentImage = getImage()
    }
    fun getImage(): Int{
        val imageResource : Int = when(dice.faces.size) {
            4 -> image4dice(Lastroll)
            6 -> image6dice(Lastroll)
            8 -> image8dice(Lastroll)
            10 -> image10dice(Lastroll)
            12 -> image12dice(Lastroll)
            else -> image20dice(Lastroll)
        }

        return imageResource
    }

    private fun image4dice(roll:Int): Int {
        val imageDisplay:Int = when (roll) {
            1 -> R.drawable.d4_1
            2 -> R.drawable.d4_2
            3 -> R.drawable.d4_3
            else -> R.drawable.d4_4
        }

        return imageDisplay
    }

    private fun image6dice(roll:Int): Int {
        val imageDisplay:Int = when (roll) {
            1 ->  R.drawable.d6_1
            2 ->  R.drawable.d6_2
            3 ->  R.drawable.d6_3
            4 ->  R.drawable.d6_4
            5 ->  R.drawable.d6_5
            else -> R.drawable.d6_6
        }

        return imageDisplay
    }

    private fun image8dice (roll:Int): Int {
        val imageDisplay:Int = when (roll) {
            1 ->  R.drawable.d8_1
            2 ->  R.drawable.d8_2
            3 ->  R.drawable.d8_3
            4 ->  R.drawable.d8_4
            5 ->  R.drawable.d8_5
            6 ->  R.drawable.d8_6
            7 ->  R.drawable.d8_7
            else -> R.drawable.d8_8
        }

        return imageDisplay
    }

    private fun image10dice (roll:Int): Int {
        val imageDisplay:Int = when (roll) {
            1 ->  R.drawable.d10_1
            2 ->  R.drawable.d10_2
            3 ->  R.drawable.d10_3
            4 ->  R.drawable.d10_4
            5 ->  R.drawable.d10_5
            6 ->  R.drawable.d10_6
            7 ->  R.drawable.d10_7
            8 ->  R.drawable.d10_8
            9 ->  R.drawable.d10_9
            else -> R.drawable.d10_10
        }
        return  imageDisplay
    }

    private fun image12dice (roll:Int): Int {
        return R.drawable.dice_1
    }

    private fun image20dice (roll:Int): Int {
        val imageDisplay:Int = when (roll) {
            1 -> R.drawable.d20_1
            2 -> R.drawable.d20_2
            3 -> R.drawable.d20_3
            4 -> R.drawable.d20_4
            5 -> R.drawable.d20_5
            6 -> R.drawable.d20_6
            7 -> R.drawable.d20_7
            8 -> R.drawable.d20_8
            9 -> R.drawable.d20_9
            10 -> R.drawable.d20_10
            11 -> R.drawable.d20_11
            12 -> R.drawable.d20_12
            13 -> R.drawable.d20_13
            14 -> R.drawable.d20_14
            15 -> R.drawable.d20_15
            16 -> R.drawable.d20_16
            17 -> R.drawable.d20_17
            18 -> R.drawable.d20_18
            19 -> R.drawable.d20_19
            else -> R.drawable.d20_20
        }
        return imageDisplay

    }
}
