package ru.stepagin.mail.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.stepagin.mail.entity.ImageEntity;
import ru.stepagin.mail.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return props;
    }

    @Bean
    public Map<String, Object> userConsumerConfigs() {
        Map<String, Object> props = consumerConfigs();
        props.put(JsonDeserializer.KEY_DEFAULT_TYPE, "String");
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "ru.stepagin.mail.entity.UserEntity");
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, UserEntity> userConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(userConsumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<?> userListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserEntity> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());
        return factory;
    }

    @Bean
    public Map<String, Object> imageConsumerConfigs() {
        Map<String, Object> props = consumerConfigs();
        props.put(JsonDeserializer.KEY_DEFAULT_TYPE, "String");
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "ru.stepagin.mail.entity.ImageEntity");
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, ImageEntity> imageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(imageConsumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<?> imageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ImageEntity> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(imageConsumerFactory());
        return factory;
    }
}
