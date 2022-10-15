package com.xr6software.digitalwallet.utils

/**
 * This extension is used to check if given a String, and a String with all the user cards, the card already exists in CardList
 * @param this card String to check existence
 * @param userCreditCardList String card list
 */
fun String.checkIfCardIsValid(userCreditCardList: String): Boolean {
    var cardsList: MutableList<String> = ArrayList()
    var userCreditCards: String = userCreditCardList
    var card: String = ""
    var index: Int = 0

    while (userCreditCards.length >= (15)) {

        when (userCreditCards[index]) {
            '4', '5' -> {
                card = userCreditCards.substring(index, index + 16);index += 16;
            }
            '3' -> {
                card = userCreditCards.substring(index, index + 15); index += 15;
            }
        }

        userCreditCards = userCreditCards.substring(index, userCreditCards.length);
        index = 0
        cardsList.add(card)
    }
    return cardsList.contains(this)
}

