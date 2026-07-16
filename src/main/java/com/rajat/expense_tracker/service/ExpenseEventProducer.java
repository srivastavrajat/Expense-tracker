package com.rajat.expense_tracker.service;

import com.rajat.expense_tracker.event.ExpenseCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExpenseEventProducer {
    private final Logger logger=LoggerFactory.getLogger(ExpenseEventProducer.class);
    public final String TOPIC="expense-created";

    private final KafkaTemplate<String, ExpenseCreatedEvent> kafkaTemplate;

    public ExpenseEventProducer(KafkaTemplate<String,ExpenseCreatedEvent> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }
    public void publish(ExpenseCreatedEvent event){
        logger.info("Entered publish() for expenseId={}", event.expenseId());
        kafkaTemplate.send(TOPIC,event.expenseId().toString(),event).
                whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info(
                                "Expense event published successfully. Topic={}, Partition={}, Offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()
                        );
                    } else {
                        logger.error("Failed to publish expense event", ex);
                    }
                });
    }
}

