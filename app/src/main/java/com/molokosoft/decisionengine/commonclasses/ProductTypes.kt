package com.molokosoft.decisionengine.commonclasses
enum class ProductTypes(val value: String, val productType: String) {
    Weekly("decisionengine_weekly_subscription", "subs"),
    Yearly("yearly_subscription", "subs"),
    FreeTrial("decisionengine_weekly_subscription", "subs"),

    Usages15("decisionengine_pack_15", "inapp"),
    Undefined("", "")
}
