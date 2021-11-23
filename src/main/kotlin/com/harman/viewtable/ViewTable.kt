package com.harman.viewtable

import kotlin.math.abs

/**
 * A class for visualizing tables
 *
 * @param command command to select the visualization function
 * @constructor init for calling the function corresponding to the passed command in the class parameters
 */
class ViewTable(command: String) {
    private val data: DictionaryData = DictionaryData()

    init {
        when (command.toLowerCase()) {
            "join vertical" -> println(joinViewVertical())
            "join horizontal" -> println(joinViewHorizontal())
            "join chart vertical" -> println(joinViewVerticalChart())
            "join chart horizontal" -> println(joinViewHorizontalChart())
            else -> println("Error: command '${command}' not found, try again!")
        }
    }

    /**
     * Visualization Horizontal Table
     *
     * The function creates a horizontal table, the names are arranged in one row,
     * with the corresponding values below them.
     * Works on the principle of 'Left join' from databases
     * 8 characters are reserved for each cell
     *
     * @return the finished table in String format
     */
    fun joinViewHorizontal(): String {
        val outTable = StringBuilder()

        val formatTemplateInt = "| %-8d\t"
        val formatTemplateStr = "| %-8s\t"

        outTable.append("Title tables : \tJoin (Table1 -> Table2)\n")
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append("\n")
        outTable.append(formatTemplateStr.format("Month"))
        outTable.append(formatTemplateStr.format("Sales"))
        outTable.append(formatTemplateStr.format("Months"))
        outTable.append("| AllSales   |\n")
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append("\n")

        var i = 0
        for (index in data.firstMonth.indices) {
            val m = data.firstMonth[index]
            val s = data.valueSales[index]
            if (data.secondMonth.size > i && data.secondMonth[i] == data.firstMonth[index]) {
                do {
                    outTable.append(formatTemplateStr.format(m))
                    outTable.append(formatTemplateInt.format(s))
                    outTable.append(formatTemplateStr.format(data.secondMonth[i]))
                    outTable.append(formatTemplateInt.format(data.valueProduct[i]))
                    outTable.append(" |\n")
                    i++
                } while (data.secondMonth.size > i && data.secondMonth[i] == data.firstMonth[index])
            } else {
                outTable.append(formatTemplateStr.format(m))
                outTable.append(formatTemplateInt.format(s))
                outTable.append(formatTemplateStr.format("Null"))
                outTable.append(formatTemplateStr.format("Null"))
                outTable.append(" |\n")
            }
        }

        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append("\n")

        return outTable.toString()
    }

    /**
     * Visualization Vertical Table
     *
     * The function creates a vertical table, in each line, the name
     * of the value is displayed first, then its value.
     * Works on the principle of 'Left join' from databases
     * 8 characters are reserved for each cell
     *
     * @return the finished table in String format
     */
    fun joinViewVertical(): String {
        val outTable = StringBuilder()

        val firstMonth = StringBuilder()
        val firstSale = StringBuilder()
        val secondMonth = StringBuilder()
        val secondSale = StringBuilder()

        val formatTemplateInt = "| %-8d\t"
        val formatTemplateStr = "| %-8s\t"

        // Print header table
        outTable.append("Title tables : \tJoin (Table1 -> Table2)\n")
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append("\n")
        firstMonth.append(formatTemplateStr.format("Month"))
        firstSale.append(formatTemplateStr.format("Sales"))
        secondMonth.append(formatTemplateStr.format("Months"))
        secondSale.append("| AllSales  ")

        var i = 0
        for (index in data.firstMonth.indices) {
            val m = data.firstMonth[index]
            val s = data.valueSales[index]
            if (data.secondMonth.size > i && data.secondMonth[i] == data.firstMonth[index]) {
                do {
                    firstMonth.append(formatTemplateStr.format(m))
                    firstSale.append(formatTemplateInt.format(s))
                    secondMonth.append(formatTemplateStr.format(data.secondMonth[i]))
                    secondSale.append(formatTemplateInt.format(data.valueProduct[i]))
                    i++
                } while (data.secondMonth.size > i && data.secondMonth[i] == data.firstMonth[index])
            } else {
                firstMonth.append(formatTemplateStr.format(m))
                firstSale.append(formatTemplateInt.format(s))
                secondMonth.append(formatTemplateStr.format("Null"))
                secondSale.append(formatTemplateStr.format("Null"))
            }
        }

        // Print finally data
        outTable.append(firstMonth); outTable.append("|\n")
        outTable.append(firstSale); outTable.append("|\n")
        outTable.append(secondMonth); outTable.append("|\n")
        outTable.append(secondSale); outTable.append("|\n")
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append("\n")

        return outTable.toString()
    }

    /**
     * Visualization Horizontal bar chart
     *
     * Horizontal bar chart: In each row,
     * the name of the quantity is displayed first,
     * then a certain number of characters X,
     * proportional to the value
     *
     * @return the finished chart in String format
     */
    fun joinViewHorizontalChart(): String {
        val formatTemplateStr = "| %-8s\t"
        val formatTemplateStr2 = "%-3s | "
        val outTable = StringBuilder()     // Storing the resulting table

        val osY: ArrayList<String> = arrayListOf()      // Y axis
        val osX = StringBuilder()                       // X axis

        val sortSale = data.valueSales.toSortedSet().reversed()

        // Append the start data, Y and X axis
        for (item in sortSale) {
            osY.add(formatTemplateStr2.format(item))
        }

        osX.append("    ")
        for (item in data.firstMonth) {
            osX.append(formatTemplateStr.format(item))
        }

        var prevMonth = data.secondMonth[0]     // Previous month
        var prevIndex = 0                       // Previous index of the month
        for (index in data.valueProduct.indices) {
            // Algorithm for finding an approximate value
            var near = abs(data.valueProduct[0] - sortSale[index])
            var indexFind = 0
            for (indexSales in sortSale.indices) {
                if (near > (abs(data.valueProduct[index] - sortSale[indexSales]))) {
                    near = abs(data.valueProduct[index] - sortSale[indexSales])
                    indexFind = indexSales
                }
            }

            // Setting 'X' labels
            val a = countSubStringInString(osY[indexFind])
            if (prevMonth == data.secondMonth[index] && prevIndex == indexFind) {
                if (osY[indexFind].length < 5) {
                    for (j in 0 until data.firstMonth.indexOf(data.secondMonth[index])) {
                        osY[indexFind] += "             "
                    }
                }
                osY[indexFind] += "X"
            } else {
                for (j in 0 until data.firstMonth.indexOf(data.secondMonth[index]) - a) {
                    osY[indexFind] += "            "
                }
                osY[indexFind] += "X"
            }
            prevMonth = data.secondMonth[index]
            prevIndex = indexFind
        }

        // Print finally data
        for (item in osY) {
            outTable.append(item)
            outTable.append("\n")
        }
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(">\n")
        outTable.append(osX)

        return outTable.toString()
    }

    /**
     * Visualization Vertical bar chart
     *
     * Vertical bar chart: columns of X symbols are oriented
     * vertically from bottom to top
     * Below them in the bottom row are the names of the
     * corresponding quantities.
     *
     * @return the finished chart in String format
     */
    fun joinViewVerticalChart(): String {
        val formatTemplateStr = "%-8s |\t"
        val outTable = StringBuilder()

        val osX: ArrayList<String> = arrayListOf()
        val osY: ArrayList<String> = arrayListOf()

        val sortValueProduct = data.valueProduct.toMutableList()
        val sortSecondMonth = data.secondMonth.toMutableList()

        var i: Int
        var j = 1
        while (j < sortValueProduct.size) {
            i = 0
            while (i < sortValueProduct.size - 1) {
                if (sortValueProduct[i] > sortValueProduct[i + 1]) {
                    val tmpProd = sortValueProduct[i]
                    sortValueProduct[i] = sortValueProduct[i + 1]
                    sortValueProduct[i + 1] = tmpProd

                    val tmpMonth = sortSecondMonth[i]
                    sortSecondMonth[i] = sortSecondMonth[i + 1]
                    sortSecondMonth[i + 1] = tmpMonth
                }
                i++
            }
            j++
        }
        sortSecondMonth.reverse(); sortValueProduct.reverse()
        val sortSale = data.valueSales.toSortedSet().reversed()

        for (item in sortSale) {
            osX.add(item.toString())
        }

        for (item in data.firstMonth) {
            osY.add(formatTemplateStr.format(item))
        }

        for (index in sortValueProduct.indices) {
            var near = abs(sortValueProduct[0] - sortSale[index])
            var indexFind = 0
            var countSpace = 0

            for (indexSales in sortSale.indices) {
                if (near > (abs(sortValueProduct[index] - sortSale[indexSales]))) {
                    near = abs(sortValueProduct[index] - sortSale[indexSales])
                    countSpace = indexSales
                }
            }
            for (indexY in osY.indices) {
                if (osY[indexY].indexOf(sortSecondMonth[index]) != -1) {
                    indexFind = indexY
                    break
                }
            }

            countSpace -= countSubStringInString(osY[indexFind])
            for (count in 0 until countSpace) {
                osY[indexFind] += "            "
            }
            osY[indexFind] += "X"
        }

        for (item in osY) {
            outTable.append(item)
            outTable.append("\n")
        }

        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(getLineSeparation())
        outTable.append(getLineSeparation()); outTable.append(">\n\t\t |\t")

        for (item in osX) {
            outTable.append(formatTemplateStr.format(item))
        }

        return outTable.toString()
    }

    /**
     * Counting substrings in string
     *
     * @param str Input string
     * @param subString The search symbol, by default X
     * @return Number of occurrences in Int format
     */
    fun countSubStringInString(str: String, subString: Char = 'X'): Int {
        var result = 0
        for (item in str)
            if (item == subString)
                result++
        return result
    }

    /**
     * A string containing a dash to separate
     *
     * @return string containing a dash (26 element)
     */
    fun getLineSeparation(): String = "-------------------------"
}

