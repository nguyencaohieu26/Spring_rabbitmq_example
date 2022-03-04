package com.example.payment.queue;

import com.example.payment.dto.OrderDto;
import com.example.payment.entity.TransactionHistory;
import com.example.payment.entity.Wallet;
import com.example.payment.enums.PaymentType;
import com.example.payment.enums.Status;
import com.example.payment.repository.TransactionHistoryRepository;
import com.example.payment.repository.WalletRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
public class ConsumerService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private RabbitTemplate template;

    @Transactional
    // handling order (order-service) get from queue -> push new payment(payment-service) info to queue
    public void handlePayment(OrderDto orderDto){
        System.out.println("----- Get Order From Queue -----");
        if(orderDto.getPaymentStatus().equals(Status.PaymentStatus.PENDING.name())){
            handlePending(orderDto);
        }
        if(orderDto.getPaymentStatus().equals(Status.PaymentStatus.REFUND.name())){
            handleRefund(orderDto);
        }
    }
    public void handlePending(OrderDto orderDto){
        Wallet wallet = checkWallet(orderDto);
        if(wallet == null){
            return;
        }
        BigDecimal totalPrice = orderDto.getTotalPrice();
        BigDecimal balance = wallet.getBalance();
        TransactionHistory history = TransactionHistory.builder()
                .paymentType(PaymentType.TRANSFER.name())
                .senderID(orderDto.getAccountID())
                .receiverID(1L)
                .amount(orderDto.getTotalPrice())
                .orderID(orderDto.getOrderID())
                .build();
        if(totalPrice.compareTo(balance) > 0){
            System.out.println("----- Handler Wallet Not Enough Money ------");
            orderDto.setPaymentStatus(Status.PaymentStatus.UNPAID.name());
            orderDto.setMessage(Status.OrderMessage.NOT_ENOUGH_BALANCE);
            orderDto.setOrderStatus(Status.OrderStatus.REJECT.name());
            history.setMessage("Balance is not enough");
            history.setStatus(Status.TransactionStatus.FAIL.name());
            transactionHistoryRepository.save(history);
            template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
            return;
        }
        try{
            System.out.println("----- Handler Pending Success -----");
            wallet.setBalance(balance.subtract(totalPrice));
            walletRepository.save(wallet);
            history.setStatus(Status.TransactionStatus.SUCCESS.name());
            history.setMessage("Pay for order: "+orderDto.getOrderID());
            transactionHistoryRepository.save(history);
            orderDto.setPaymentStatus(Status.PaymentStatus.PAID.name());
            orderDto.setOrderStatus(Status.OrderStatus.CONFIRM.name());
            template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
        }catch (Exception e){
            System.out.println("----- Handler Pending Fail -----");
            history.setStatus(Status.TransactionStatus.FAIL.name());
            transactionHistoryRepository.save(history);
        }

    }
    public void handleRefund(OrderDto orderDto){
        Wallet wallet = checkWallet(orderDto);
        if(wallet == null){
            return;
        }
        TransactionHistory history = TransactionHistory.builder()
                .amount(orderDto.getTotalPrice())
                .senderID(orderDto.getAccountID())
                .receiverID(1L)
                .message("Refund for order "+orderDto.getOrderID())
                .orderID(orderDto.getOrderID())
                .paymentType(PaymentType.REFUNDED.name())
                .build();
        try{
            System.out.println("----- Handler Refund Success -----");
            wallet.setBalance(wallet.getBalance().add(orderDto.getTotalPrice()));
            walletRepository.save(wallet);
            transactionHistoryRepository.save(history);
            orderDto.setOrderStatus(Status.OrderStatus.DONE.name());
            orderDto.setPaymentStatus(Status.PaymentStatus.REFUNDED.name());
            template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
        }catch (Exception e){
            System.out.println("---- Handler Refund Fail -----");
            history.setStatus(Status.TransactionStatus.FAIL.name());
            transactionHistoryRepository.save(history);
        }
    }
    private Wallet checkWallet(OrderDto orderDto){
        if(orderDto.getAccountID() == 0){
            orderDto.setOrderStatus(Status.OrderStatus.REJECT.name());
            orderDto.setMessage(Status.OrderMessage.NOT_FOUND_USER);
            orderDto.setPaymentStatus(Status.PaymentStatus.UNPAID.name());
            template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
            return  null;
        }
        Wallet wallet = walletRepository.findWalletsByAccountID(orderDto.getAccountID());
        System.out.println(wallet);
        if(wallet == null){
            orderDto.setOrderStatus(Status.OrderStatus.REJECT.name());
            orderDto.setMessage(Status.OrderMessage.NOT_FOUND_WALLET);
            orderDto.setPaymentStatus(Status.PaymentStatus.UNPAID.name());
            template.convertAndSend(Config.DIRECT_EXCHANGE,Config.DIRECT_ROUTING_KEY_ORDER,orderDto);
            return null;
        }
        return wallet;
    }
}
