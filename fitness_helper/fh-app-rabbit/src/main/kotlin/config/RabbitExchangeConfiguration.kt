package config

data class RabbitExchangeConfiguration(
    val keyIn: String = "",
    val keyOut: String = "",
    val exchange: String = "",
    val queue: String = "",
    val consumerTag: String = "",
    val exchangeType: String = "direct" // Объявляем обменник типа "direct"
) {
    companion object {
        val NONE = RabbitExchangeConfiguration()
    }
}