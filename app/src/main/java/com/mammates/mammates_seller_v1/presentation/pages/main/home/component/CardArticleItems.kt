package com.mammates.mammates_seller_v1.presentation.pages.main.home.component

import com.mammates.mammates_seller_v1.R

data class CardArticleItems(
    val title: String,
    val illustration: Int,
    val description: String,
    val url: String,
)

val cardArticleItems = listOf(
    CardArticleItems(
        title = "'Best before' labels scrutinised as food waste concerns grow",
        illustration = R.drawable.article_1,
        description = "As awareness grows around the world about the problem of food waste, one culprit in particular is drawing scrutiny: “best before” labels.",
        url = "https://www.voanews.com/a/best-before-labels-scrutinized-as-food-waste-concerns-grow/6776858.html"
    ),
    CardArticleItems(
        title = "With Hong Kong running out of space for its trash, residents are doing more to reduce food waste",
        illustration = R.drawable.article_2,
        description = "Food waste makes up 30 per cent of Hong Kong’s total municipal solid waste and cannot break down well in landfills.",
        url = "https://www.channelnewsasia.com/asia/hong-kong-landfills-running-out-space-3217566"
    ),
    CardArticleItems(
        title = "CNA Explains: Should you throw away food that is past the 'best before' date?",
        illustration = R.drawable.article_3,
        description = "How are date marks on prepacked food decided and how do you tell if food past such dates is still edible? CNA talks to a nutritionist, food science expert and manufacturer.",
        url = "https://www.channelnewsasia.com/singapore/food-expiry-best-dates-safe-eat-cna-explains-3296001"
    ),
)