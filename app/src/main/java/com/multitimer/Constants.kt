package com.multitimer

const val step: Long = 50

val strToNum: (String) -> Int = { if (it.isNotEmpty()) it.toInt() else 0 }
