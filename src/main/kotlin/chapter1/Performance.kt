package org.example.chapter1

import kotlinx.serialization.Serializable

@Serializable
data class Performance(
    val playId: String,
    val audience: Int
)
