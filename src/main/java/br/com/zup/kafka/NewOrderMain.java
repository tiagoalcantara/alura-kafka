package br.com.zup.kafka;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var orderDispatcher = new KafkaDispatcher<Order>();
            var emailDispatcher = new KafkaDispatcher<Email>()
        ){
            for (var i = 0; i < 10; i++){
                var key = UUID.randomUUID().toString();

                var userId = UUID.randomUUID().toString();
                var orderId = UUID.randomUUID().toString();
                var value = BigDecimal.valueOf(Math.random() * 5000 + 1);
                var order = new Order(userId, orderId, value);
                orderDispatcher.send("ECOMMERCE_NEW_ORDER", key, order);

                var email = new Email("Purchase confirmation", "Your order is being processed, thanks!");
                emailDispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }



}
