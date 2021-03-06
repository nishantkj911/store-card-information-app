package com.example.helloworldapplication

import android.widget.DatePicker
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNewCardActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addManyNewCards() {
        for (i in 1..20) {
            addNewCard()
        }
    }
    @Test
    fun addNewCard() {
        // Click on the add new card button
        onView(withId(R.id.fab)).perform(click())

        // add text information
        onView(withId(R.id.cardNameTextField)).perform(scrollTo(), typeText("Card X"))
        onView(withId(R.id.cardNumberTextField)).perform(scrollTo(), typeText("65465461328946513"))
        onView(withId(R.id.nameOnCardTextField)).perform(scrollTo(), typeText("Nishant Kartikeya"))
        onView(withId(R.id.cvvTextField)).perform(scrollTo(), typeText("123"))
        onView(withId(R.id.pinTextField)).perform(scrollTo(), typeText("1181"))

        Espresso.closeSoftKeyboard()

        // Select date for valid from and through
        onView(withId(R.id.validFromTextView)).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.qualifiedName))).perform(
            PickerActions.setDate(2013, 1, 28)
        )
        onView(withText("OK")).perform(click())

        onView(withId(R.id.validThruTextView)).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.qualifiedName))).perform(
            PickerActions.setDate(2023, 2, 11)
        )
        onView(withText("OK")).perform(click())

        // Add grid information
        onView(withId(R.id.addGridInfoButton)).perform(click())
        for (char in 'A'..'E') {
            onView(withId(R.id.letterTextBox)).perform(typeText(char.toString()))
            onView(withId(R.id.valueTextBox)).perform(typeText(char.toInt().toString()))
            onView(withId(R.id.addValueButton)).perform(click())
        }

        Espresso.closeSoftKeyboard()
        Espresso.pressBack()

        // change some information in grid
        onView(
            Matchers.allOf(
                withParent(withParent(withId(R.id.gridTableAdd))),
                withParent(withParentIndex(2)),
                withParent(withId(R.id.tableRow)),
                withId(R.id.editGridValueButton)
            )
        ).perform(scrollTo(), click())
        onView(withId(R.id.gridValueEditTB)).perform(typeText("128"))
        onView(withId(R.id.editGridValueDialogButton)).perform(click())

        // Delete some grid value
        onView(
            Matchers.allOf(
                withParent(withParent(withId(R.id.gridTableAdd))),
                withParent(withParentIndex(0)),
                withParent(withId(R.id.tableRow)),
                withId(R.id.deleteGridValueButton)
            )
        ).perform(scrollTo(), click())


        // add the card
        onView(withId(R.id.submitButton)).perform(scrollTo(), click())
        onView(withText("YES")).perform(click())
    }

    @Test
    fun addNewCardUnsuccessfully() {
        // Click on the add new card button
        onView(withId(R.id.fab)).perform(click())

        // add text information
        onView(withId(R.id.cardNameTextField)).perform(scrollTo(), typeText("Card X"))
        onView(withId(R.id.cardNumberTextField)).perform(scrollTo(), typeText("65465461328946513"))
        onView(withId(R.id.nameOnCardTextField)).perform(scrollTo(), typeText("Nishant Kartikeya"))
        onView(withId(R.id.cvvTextField)).perform(scrollTo(), typeText("123"))

        Espresso.closeSoftKeyboard()

        // Select date for valid from and through
        onView(withId(R.id.validFromTextView)).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.qualifiedName))).perform(
            PickerActions.setDate(2009, 6, 19)
        )
        onView(withText("OK")).perform(click())

        onView(withId(R.id.validThruTextView)).perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.qualifiedName))).perform(
            PickerActions.setDate(2019, 6, 19)
        )
        onView(withText("OK")).perform(click())

        // Add grid information
        /*onView(withId(R.id.addGridInfoButton)).perform(click())
        for (char in 'A'..'B') {
            onView(withId(R.id.letterTextBox)).perform(typeText(char.toString()))
            onView(withId(R.id.valueTextBox)).perform(typeText(char.toInt().toString()))
            onView(withId(R.id.addValueButton)).perform(click())
        }

        Espresso.closeSoftKeyboard()
        Espresso.pressBack()*/

        // add the card
        onView(withId(R.id.submitButton)).perform(scrollTo(), click())
        onView(withText("YES")).perform(click())
    }

    @Test
    fun deleteCard() {
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MyRecyclerViewAdapter.ViewHolder>(
                MainActivity.cards.size,
                click()
            )
        )
        onView(withId(R.id.deleteButton)).perform(scrollTo(), click())
    }

    @Test
    fun addAndDeleteCard() {
        addNewCard()
        deleteCard()
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
}