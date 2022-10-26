package com.co.sofka.config;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfig {

    @Bean
    Queue Piso1Queue() {
        return new Queue("Piso1Queue", false);
    }

    @Bean
    Queue Piso2Queue() {
        return new Queue("Piso2Queue", false);
    }

    @Bean
    Queue Piso3Queue() {
        return new Queue("Piso3Queue", false);
    }

    @Bean
    Queue allQueue() {
        return new Queue("allQueue", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }

    @Bean
    Binding marketing1Binding(Queue Piso1Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(Piso1Queue).to(topicExchange).with("queue.pisoimpar");
    }

    @Bean
    Binding finance1Binding(Queue Piso2Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(Piso2Queue).to(topicExchange).with("queue.pisopar");
    }
    @Bean
    Binding finance2Binding(Queue Piso3Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(Piso3Queue).to(topicExchange).with("queue.pisoimpar");
    }


    @Bean
    Binding allBinding(Queue allQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(allQueue).to(topicExchange).with("queue.*");
    }

    @Bean
    public MessageConverter json1MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer message1ListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(json1MessageConverter());
        return rabbitTemplate;
    }
}
