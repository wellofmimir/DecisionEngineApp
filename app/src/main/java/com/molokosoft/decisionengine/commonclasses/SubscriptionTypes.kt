package com.molokosoft.decisionengine.commonclasses
enum class SubscriptionTypes(val value: String) {
    Weekly("decisionengine_weekly_subscription"),
    Yearly("yearly_subscription"),
    FreeTrial("decisionengine_weekly_subscription"),

    Undefined("")
}
