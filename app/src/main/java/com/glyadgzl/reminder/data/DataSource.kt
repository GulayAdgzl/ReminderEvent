package com.glyadgzl.reminder.data

import com.glyadgzl.reminder.R
import com.glyadgzl.reminder.models.ComposeRandomItem

object DataSource {
    val plants = listOf(
        ComposeRandomItem(
            name = "Aloe Vera",
            schedule = "Monthly",
            type = "Succulent",
            imageResId = R.drawable.aloevera,
            description = "Aloe vera is a succulent plant species of the genus Aloe. It is cultivated for agricultural and medicinal uses."
        ),
        ComposeRandomItem(
            name = "Bamboo Palm",
            schedule = "Weekly",
            type = "Palm",
            imageResId = R.drawable.bambo,
            description = "The Bamboo Palm, also known as the Reed Palm, is a medium-sized palm native to South America. It is a popular houseplant and can grow up to 12 feet tall."
        ),
        ComposeRandomItem(
            name = "Spider Plant",
            schedule = "Daily",
            type = "Perennial",
            imageResId = R.drawable.spider,
            description = "The Spider Plant, also known as Chlorophytum comosum, is a popular houseplant with long, thin, green leaves. It is easy to care for and can grow in low-light conditions."
        ),
        ComposeRandomItem(
            name = "Fiddle Leaf Fig",
            schedule = "Biweekly",
            type = "Fig",
            imageResId = R.drawable.fidle,
            description = "The Fiddle Leaf Fig, also known as Ficus lyrata, is a popular indoor tree with large, fiddle-shaped leaves. It is native to tropical regions of Africa and is known for its air-purifying properties."
        ),
        ComposeRandomItem(
            name = "Rubber Plant",
            schedule = "Monthly",
            type = "Evergreen",
            imageResId = R.drawable.ruber,
            description = "The Rubber Plant, also known as Ficus elastica, is a large tree native to Southeast Asia. It is a popular houseplant and can grow up to 100 feet tall in its native habitat."
        ),
        ComposeRandomItem(
            name = "Peace Lily",
            schedule = "Weekly",
            type = "Flowering Plant",
            imageResId = R.drawable.pea,
            description = "The Peace Lily, also known as Spathiphyllum wallisii, is a popular houseplant known for its white flowers. It is easy to care for and can grow in low-light conditions."
        ),

    )
}