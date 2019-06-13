package com.lambdaschool.dogsinitial

import com.lambdaschool.dogsinitial.model.DogList
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableScheduling
@EnableWebMvc
@SpringBootApplication
open class DogsinitialApplication {
    @Bean
    open fun appExchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    open fun appQueueLow(): Queue {
        return Queue(QUEUE_NAME_LOW)
    }

    @Bean
    open fun declareBindingLow(): Binding {
        return BindingBuilder.bind(appQueueLow()).to(appExchange()).with(QUEUE_NAME_LOW)
    }

    @Bean
    open fun appQueueHigh(): Queue {
        return Queue(QUEUE_NAME_HIGH)
    }

    @Bean
    open fun declareBindingHigh(): Binding {
        return BindingBuilder.bind(appQueueHigh()).to(appExchange()).with(QUEUE_NAME_HIGH)
    }

    @Bean
    open fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    companion object {
        public val EXCHANGE_NAME = "LambdaServer"
        public val QUEUE_NAME_LOW = "LowPriorityQueue"
        public val QUEUE_NAME_HIGH = "HighPriorityQueue"

        lateinit var ourDogList: DogList
        @JvmStatic
        fun main(args: Array<String>) {
            ourDogList = DogList()
            SpringApplication.run(DogsinitialApplication::class.java, *args)
        }
    }
}

