package kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import util.KAFKA_SERVER_LOCALHOST
import util.KOTLIN_KEY_TOPIC
import java.util.*

fun main() {
    val log = LoggerFactory.getLogger("kafka.ProducerAsyncWithKey")
    val props = Properties()
    props[BOOTSTRAP_SERVERS_CONFIG] = KAFKA_SERVER_LOCALHOST
    props[KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java.name
    props[VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name

    KafkaProducer<Int, String>(props).use { producer ->
        for (i in 1..10) {
            producer.send(ProducerRecord(KOTLIN_KEY_TOPIC, i, "hello $i"))
            { recordMetadata, exception ->
                if (exception != null) {
                    log.error("exception occurred while sending message $exception")
                }
                log.info("partition: ${recordMetadata.partition()}\n" +
                         "offset: ${recordMetadata.offset()}\n" +
                         "timestamp: ${recordMetadata.timestamp()}"
                )
            }
        }
    }
}