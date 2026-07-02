package com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model

data class Criterion(
    val name: String,
    var importance: Int = 1,
    var score: Int = 1
)
