package com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model

data class Option(
    val name: String,
    val reversibility: Int,
    val criteria: List<Criterion> = emptyList()
){
    init {
        require(reversibility in 1..10){
            "Reversibility needs to be within the range from 1 to 10."
        }
    }
}
