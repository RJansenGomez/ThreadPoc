package org.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Order {
    private String id;
    @Singular(value = "dishesList")
    private List<Dish> dishesList;

    private Long processId;
    private LocalDateTime startProcess;
    public boolean isProcessCancelable(){
        return LocalDateTime.now().isBefore(startProcess.plusSeconds(30));
    }
    public synchronized void storeProcessId(long threadId){
        this.processId = threadId;
    }
}
