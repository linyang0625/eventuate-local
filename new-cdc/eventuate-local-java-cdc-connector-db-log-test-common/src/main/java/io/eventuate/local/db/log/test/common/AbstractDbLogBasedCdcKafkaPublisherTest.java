package io.eventuate.local.db.log.test.common;

import io.eventuate.local.common.CdcDataPublisher;
import io.eventuate.local.common.PublishedEvent;
import io.eventuate.local.db.log.common.DbLogBasedCdcDataPublisher;
import io.eventuate.local.db.log.common.DuplicatePublishingDetector;
import io.eventuate.local.db.log.common.OffsetStore;
import io.eventuate.local.java.kafka.consumer.EventuateKafkaConsumerConfigurationProperties;
import io.eventuate.local.java.kafka.producer.EventuateKafkaProducer;
import io.eventuate.local.java.kafka.producer.EventuateKafkaProducerConfigurationProperties;
import io.eventuate.local.test.util.CdcKafkaPublisherTest;
import org.springframework.beans.factory.annotation.Autowired;


public class AbstractDbLogBasedCdcKafkaPublisherTest extends CdcKafkaPublisherTest {

  @Autowired
  private OffsetStore offsetStore;

  @Override
  protected CdcDataPublisher<PublishedEvent> createCdcKafkaPublisher() {
    return new DbLogBasedCdcDataPublisher<>(() ->
            new EventuateKafkaProducer(eventuateKafkaConfigurationProperties.getBootstrapServers(), EventuateKafkaProducerConfigurationProperties.empty()),
            offsetStore,
            new DuplicatePublishingDetector(eventuateKafkaConfigurationProperties.getBootstrapServers(), EventuateKafkaConsumerConfigurationProperties.empty()),
            publishingStrategy);
  }
}
