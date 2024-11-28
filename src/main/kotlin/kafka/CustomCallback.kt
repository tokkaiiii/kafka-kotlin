package kafka

import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.RecordMetadata
import util.logger
import java.lang.Exception

class CustomCallback(
    private val seq: Int
) : Callback {
    val log = logger()
    override fun onCompletion(p0: RecordMetadata?, p1: Exception?) {
        if (p1 != null) {
            log.error("error in customCallback", p1)
        }
        log.info("seq: $seq partition: ${p0?.partition()} offset: ${p0?.offset()}")
    }

}