package kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import util.KAFKA_SERVER_LOCALHOST
import util.KOTLIN_SIMPLE_TOPIC
import java.util.*

fun main() {
    val log = LoggerFactory.getLogger("SimpleProducerAsync")
    val props = Properties()
    props.setProperty(BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_LOCALHOST)
    props.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    props.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

    KafkaProducer<String, String>(props).use { producer ->
        producer.send(
            ProducerRecord(
                KOTLIN_SIMPLE_TOPIC,
                "async message"
            )
        ) { recordMetadata, exception ->
            if (exception != null) {
                log.error(exception.message, exception)
            }
            log.info(
                "# record metadata received \n" +
                        "partition: ${recordMetadata.partition()}\n" +
                        "offset: ${recordMetadata.offset()}\n" +
                        "timestamp: ${recordMetadata.timestamp()}"
            )
        }
    }
}