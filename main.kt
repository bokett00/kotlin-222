fun agoToText(seconds: Int): String {
    return when {
        seconds <= 60 -> "был(а) только что"
        seconds == 60 * 60 -> "был(а) в сети 1 час назад"
        seconds < 60 * 60 -> "был(а) в сети ${minutesAgo(seconds)}"
        seconds <= 24 * 60 * 60 -> "был(а) в сети ${hoursAgo(seconds)}"
        seconds <= 2 * 24 * 60 * 60 -> "был(а) вчера"
        seconds <= 3 * 24 * 60 * 60 -> "был(а) позавчера"
        else -> "был(а) давно"
    }
}

fun minutesAgo(seconds: Int): String {
    val minutes = seconds / 60
    val word = when {
        minutes % 100 in 11..14 -> "минут"
        minutes % 10 == 1 -> "минуту"
        minutes % 10 in 2..4 -> "минуты"
        else -> "минут"
    }

    return "$minutes $word назад"
}

fun hoursAgo(seconds: Int): String {
    val hours = seconds / (60 * 60)
    val word = when {
        hours % 100 in 11..14 -> "часов"
        hours % 10 == 1 -> "час"
        hours % 10 in 2..4 -> "часа"
        else -> "часов"
    }

    return "$hours $word назад"
}

fun main() {
    println(agoToText(30))       // был(а) только что
    println(agoToText(120))      // был(а) в сети 2 минуты назад
    println(agoToText(60 * 60))  // был(а) в сети 1 час назад
    println(agoToText(90_000))   // был(а) вчера


    // Задание 2
    print("Тип карты (VK Pay, MasterCard, Maestro, Visa, Мир): ")
    val cardType = readln().ifBlank { "VK Pay" }

    print("Предыдущие переводы за месяц: ")
    val previousTransfers = readln().toIntOrNull() ?: 0

    print("Сумма нового перевода: ")
    val transferAmount = readln().toInt()

    val commission = calculateCommission(cardType, previousTransfers, transferAmount)
    println("Комиссия: $commission руб.")
}

fun calculateCommission(
    cardType: String = "VK Pay",
    previousTransfers: Int = 0,
    transferAmount: Int
): Int {
    return when (cardType.lowercase()) {
        "vk pay" -> 0

        "mastercard", "maestro" -> {
            val limit = 75_000
            val amountOverLimit = previousTransfers + transferAmount - limit

            if (amountOverLimit <= 0) {
                0
            } else {
                (amountOverLimit * 0.006 + 20).toInt()
            }
        }

        "visa", "мир" ->
            maxOf((transferAmount * 0.0075).toInt(), 35)

        else -> {
            println("Неизвестный тип карты")
            0
        }
    }
}
