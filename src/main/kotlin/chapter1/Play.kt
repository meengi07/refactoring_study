package org.example.chapter1

import kotlinx.serialization.Serializable

@Serializable
data class Play(
    val name: String,
    val type: String,
)
