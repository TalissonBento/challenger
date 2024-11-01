package com.talissonb.challenger.basicfeature.tests

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.talissonb.challenger.basicfeature.data.generateTestRocketsFromPresentation
import com.talissonb.challenger.basicfeature.presentation.composable.ROCKET_DIVIDER_TEST_TAG
import com.talissonb.challenger.basicfeature.presentation.composable.RocketsListContent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RocketsListContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testRockets = generateTestRocketsFromPresentation()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            RocketsListContent(
                rocketList = testRockets,
                onRocketClick = { },
            )
        }
    }

    @Test
    fun rocketsListContent_shouldNotShowTheDividerAfterLastItem() {
        val expectedDividerCount = testRockets.size - 1

        composeTestRule
            .onAllNodesWithTag(ROCKET_DIVIDER_TEST_TAG)
            .assertCountEquals(expectedDividerCount)
    }
}
