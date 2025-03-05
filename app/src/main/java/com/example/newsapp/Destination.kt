package com.example.newsapp

import kotlinx.serialization.Serializable

@Serializable
object CategoriesScreen

@Serializable
class NewsScreen(val endpointId: String)
