@file:JvmName("RikkaDictionaryConstants")
package moe.feng.autofill.rikka

import java.util.*

var RIKKA_WORDS = emptyArray<String>()

val random = Random().apply { setSeed(System.currentTimeMillis()) }

fun getRandomRikkaWord(): String = RIKKA_WORDS[random.nextInt(RIKKA_WORDS.size)]