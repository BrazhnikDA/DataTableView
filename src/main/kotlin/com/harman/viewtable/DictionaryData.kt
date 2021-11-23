package com.harman.viewtable

/**
 * Class for creating test data
 *
 * @constructor Creates 4 variables with test data
 */
class DictionaryData {
    // First table
    val firstMonth: List<String> = mutableListOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
        "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )
    val valueSales: List<Int> = mutableListOf(
        180, 180, 180, 100, 200, 50, 250, 65, 10, 300, 225, 180
    )

    //Second table
    val secondMonth: List<String> = mutableListOf(
        "Январь", "Февраль", "Февраль", "Март", "Март", "Март", "Август", "Ноябрь"
    )

    val valueProduct: List<Int> = mutableListOf(
        500, 350, 300, 250, 10, 200, 65, 50
    )
}