package com.harman.viewtable

/**
 * Class for creating test data
 *
 * @constructor Creates 4 variables with test data
 */
class DictionaryData {
    // First table
    val firstMonth: List<String> = mutableListOf(
        "January", "February", "March", "April", "May", "June", "July", "August",
        "Septemb.", "October", "November", "December"
    )
    val valueSales: List<Int> = mutableListOf(
        180, 180, 180, 100, 200, 50, 250, 65, 10, 300, 225, 180
    )

    //Second table
    val secondMonth: List<String> = mutableListOf(
        "January", "February", "February", "March", "March", "March",
        "August", "November"
    )

    val valueProduct: List<Int> = mutableListOf(
        500, 350, 300, 250, 10, 200, 65, 50
    )
}