package kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import util.KAFKA_SERVER_LOCALHOST
import util.KOTLIN_SIMPLE_TOPIC
import java.util.*

fun main() {
    val props = Properties()
    props.setProperty(BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_LOCALHOST)
    props.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    props.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    val producer = KafkaProducer<String, String>(props)
    val record = ProducerRecord<String, String>(KOTLIN_SIMPLE_TOPIC,"Hello kafka")
    producer.send(record)
    producer.flush()
    producer.close()
}