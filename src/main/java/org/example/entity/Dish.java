package org.example.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Dish {
    private String id;
    private String name;
    private long preparationTimeInSeconds;

    public void cock(){
        try {
            Thread.sleep(preparationTimeInSeconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
