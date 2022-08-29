package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = 0
    val score : Int
        get() = _score

    private var _currentScrambleCount = 0
    val currentScrambleCount : Int
        get() =  _currentScrambleCount

    private lateinit var _currentScrambleWord : String
    val currentScrambleWord: String get() = _currentScrambleWord

    // List of words used in the game
    private var wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "Game View Model destroyed! ")
    }

    /*
     * Updates currentWord and currentScrambledWord with the next word.
     */
    private fun getNextWord() {
        // mengambil data kata kata dengan random indexnya
        currentWord = allWordsList.random()
        // mengubah data yang tadinya berbentuk list menjadi charArray
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)){
            tempWord.shuffle()
        }
        if (wordList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambleWord = String(tempWord)
            ++ _currentScrambleCount
            wordList.add(currentWord)
        }
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score = 0
        _currentScrambleCount = 0
        wordList.clear()
        getNextWord()
    }


    /*
    * Increases the game score if the player's word is correct.
    */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /*
    * Returns true if the player word is correct.
    * Increases the score accordingly.
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun nextWord(): Boolean {
        return if(currentScrambleCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false
    }
}