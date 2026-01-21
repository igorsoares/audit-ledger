package com.audit_ledger.audit_writer.config;

import com.audit_ledger.audit_writer.application.dtos.RecvEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

    private final KafkaProperties kafkaProperties;

    public KafkaConsumerConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, RecvEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                this.kafkaProperties.getBootstrapServers());
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                this.kafkaProperties.getConsumer().getGroupId());

        JacksonJsonDeserializer<RecvEvent> valueDeserializer =
                new JacksonJsonDeserializer<>(RecvEvent.class);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RecvEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RecvEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

        // Configura error handler com backoff e limite
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                // DeadLetterPublishingRecoverer (opcional - envia para tópico de erro)
                (record, exception) -> {
                    System.err.println("Erro ao processar mensagem após todas as tentativas");
                    System.err.println("Offset: " + record.offset());
                    System.err.println("Partition: " + record.partition());
                    System.err.println("Mensagem: " + record.value());
                    System.err.println("Erro: " + exception.getMessage());
                },
                // Backoff exponencial: 1s, 2s, 4s
                new FixedBackOff(1000L, 3) // 3 tentativas com 1 segundo entre elas
        );

        // Define quais exceções não devem ser retriadas (pula imediatamente)
        errorHandler.addNotRetryableExceptions(
                DeserializationException.class
        );

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
