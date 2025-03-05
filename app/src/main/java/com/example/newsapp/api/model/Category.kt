package com.example.newsapp.api.model

import com.example.newsapp.R


data class  Category(
    val titleResId: Int? = null,
    val drawableResId: Int? = null,
    val endpointId: String? = null
) {
    companion object {


        private val BUSINESS = "business"
        private val ENTERTAINMENT = "entertainment"
        private val GENERAL = "general"
        private val HEALTH = "health"
        private val SCIENCE = "science"
        private val SPORTS = "sports"
        private val TECHNOLOGY = "technology"
        fun getCategoriesList(): List<Category> {
            return listOf(
                fromId(GENERAL),
                fromId(BUSINESS),
                fromId(SPORTS),
                fromId(TECHNOLOGY),
                fromId(ENTERTAINMENT),
                fromId(SCIENCE),
                fromId(HEALTH)
            )
        }

        private fun fromId(id: String): Category {
            return when (id) {
                BUSINESS -> Category(
                    titleResId = R.string.business,
                    endpointId = BUSINESS,
                    drawableResId = R.drawable.business
                )

                ENTERTAINMENT -> Category(
                    titleResId = R.string.entertainment,
                    drawableResId = R.drawable.entertainment, endpointId = ENTERTAINMENT
                )

                GENERAL -> Category(
                    titleResId = R.string.general,
                    drawableResId = R.drawable.general,
                    endpointId = GENERAL
                )

                HEALTH -> Category(
                    titleResId = R.string.health,
                    drawableResId = R.drawable.health_1,
                    endpointId = HEALTH
                )

                SCIENCE -> Category(
                    titleResId = R.string.science,
                    drawableResId = R.drawable.science_1,
                    endpointId = SCIENCE
                )

                SPORTS -> Category(
                    titleResId = R.string.sports,
                    drawableResId = R.drawable.sports_1,
                    endpointId = SPORTS
                )

                TECHNOLOGY -> Category(
                    titleResId = R.string.technology,
                    drawableResId = R.drawable.technology_1,
                    endpointId = TECHNOLOGY,
                )

                else -> Category(
                    titleResId = R.string.technology,
                    drawableResId = R.drawable.technology_1,
                    endpointId = TECHNOLOGY,
                )
            }
        }
    }

}
