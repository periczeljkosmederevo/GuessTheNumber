package com.periczeljkosmederevo.myapplication

class GameManager {
    var zamisljeniBroj = 0
        private set
    var preostaloPokusaja = 10
        private set

    fun novaIgra() {
        zamisljeniBroj = (1..99).random()
        preostaloPokusaja = 10
    }

    fun proveriBroj(korisnikovBroj: Int): Int {
        preostaloPokusaja--
        return when {
            korisnikovBroj == zamisljeniBroj -> 0
            korisnikovBroj < zamisljeniBroj -> 1 // Treba veći
            else -> -1 // Treba manji
        }
    }

    fun isGameOver() = preostaloPokusaja <= 0
}