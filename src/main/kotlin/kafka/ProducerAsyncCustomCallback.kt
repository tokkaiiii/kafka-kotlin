package kafka

import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import util.KAFKA_SERVER_LOCALHOST
import util.KOTLIN_KEY_TOPIC
import java.util.*

fun main() {
    val props = Properties()
    props[BOOTSTRAP_SERVERS_CONFIG] = KAFKA_SERVER_LOCALHOST
    props[KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
    props[VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

    KafkaProducer<Int, String>(props).use { producer ->
        for (i in 1..10) {
            val callback: Callback = CustomCallback(i)
            producer.send(ProducerRecord(KOTLIN_KEY_TOPIC, i, "bye $i"), callback)
        }
    }
}