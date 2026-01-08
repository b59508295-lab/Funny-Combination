package com.funnycombination.data

enum class EmojiType(val emoji: String) {
    SMILE("😊"),
    HEART("❤️"),
    STAR("⭐"),
    FIRE("🔥"),
    ROCKET("🚀");
    
    companion object {
        val all = values().toList()
    }
}

