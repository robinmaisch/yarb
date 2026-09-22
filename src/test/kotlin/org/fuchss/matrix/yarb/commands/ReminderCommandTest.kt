package org.fuchss.matrix.yarb.commands

import org.fuchss.matrix.yarb.TimerManager
import kotlin.test.Test
import kotlin.test.assertEquals

class ReminderCommandTest {
    private val pizza = "🍕"
    private val burger = "🍔"
    private val salad = "🥗"

    @Test
    fun `single line with emoticon is no poll`() {
        val message = "Let's meet :)"
        assertEquals(mapOf(TimerManager.DEFAULT_REACTION to message), ReminderCommand.parseEmojiToMessage(message))
    }

    @Test
    fun `single line with colon is no poll`() {
        val message = "Lunch: at the canteen"
        assertEquals(mapOf(TimerManager.DEFAULT_REACTION to message), ReminderCommand.parseEmojiToMessage(message))
    }

    @Test
    fun `single line that looks like an option is no poll`() {
        val message = "$pizza: Pizza"
        assertEquals(mapOf(TimerManager.DEFAULT_REACTION to message), ReminderCommand.parseEmojiToMessage(message))
    }

    @Test
    fun `option lines below the command are a poll`() {
        val message = "\n$pizza: Pizza\n$burger: Burger\n$salad: Salad"
        assertEquals(mapOf(pizza to "Pizza", burger to "Burger", salad to "Salad"), ReminderCommand.parseEmojiToMessage(message))
    }

    @Test
    fun `a single option line below the command is a poll`() {
        val message = "\n$pizza: Pizza"
        assertEquals(mapOf(pizza to "Pizza"), ReminderCommand.parseEmojiToMessage(message))
    }

    @Test
    fun `text in the first line disables the poll parsing`() {
        val message = "Lunch?\n$pizza: Pizza\n$burger: Burger"
        assertEquals(mapOf(TimerManager.DEFAULT_REACTION to message), ReminderCommand.parseEmojiToMessage(message))
    }

    @Test
    fun `multi line message without options is no poll`() {
        val message = "\nLet's meet\nSee you!"
        assertEquals(mapOf(TimerManager.DEFAULT_REACTION to message.trim()), ReminderCommand.parseEmojiToMessage(message))
    }

    @Test
    fun `one line without an option disables the poll parsing`() {
        val message = "\n$pizza: Pizza\nOr maybe a burger?"
        assertEquals(mapOf(TimerManager.DEFAULT_REACTION to message.trim()), ReminderCommand.parseEmojiToMessage(message))
    }
}
