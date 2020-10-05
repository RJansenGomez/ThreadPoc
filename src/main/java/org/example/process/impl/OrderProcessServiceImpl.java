package org.example.process.impl;

import org.example.entity.Dish;
import org.example.entity.Order;
import org.example.process.OrderProcessService;
import org.example.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderProcessServiceImpl implements OrderProcessService {
    private static final int MAX_THREADS = 30;
    private ExecutorService threadPool;
    private CompletionService<Order> executorCompletion;
    private HashMap<String, OrderProcess> orderProcessing;
    private final NotificationService notificationService;

    public OrderProcessServiceImpl(final NotificationService notificationService) {
        this.notificationService = notificationService;
        initialize();
    }

    private void initialize() {
        this.threadPool = Executors.newCachedThreadPool();
        executorCompletion = new ExecutorCompletionService<>(threadPool);
        orderProcessing = new HashMap<>();
    }

    @Override
    public String startProcess(Order order) {
        OrderProcess orderProcess = new OrderProcess(order, notificationService);
        threadPool.execute(orderProcess);
        return order.getProcessId().toString();
    }

    private void cancelProcess(String processId) {
        OrderProcess orderProcess = orderProcessing.get(processId);
        if (orderProcess == null) {
            throw new RuntimeException("Process can't be cancelled");
        }
        {
            Order order = orderProcess.getOrder();
            if (!order.isProcessCancelable()) {
                throw new RuntimeException("Process can't be cancelled");
            } else {
                killProcess(orderProcess);
            }
        }
    }

    private void killProcess(OrderProcess process) {
        process.kill();
        doRollbackStuff();
    }

    private void doRollbackStuff() {

    }


}

class OrderProcess implements Runnable {
    private final Order order;
    private final NotificationService notificationService;
    private Thread thread;

    public OrderProcess(Order order, final NotificationService notificationService) {
        this.order = order;
        this.notificationService = notificationService;
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        order.storeProcessId(thread.getId());
        makeProcessing(order);
    }

    public void kill() {
        thread.interrupt();
    }

    private void makeProcessing(Order order) {
        for (Dish dish : order.getDishesList()) {
            dish.cock();
            notificationService.notify(order, dish);
        }
    }

    public Order getOrder() {
        return this.order;
    }
}
