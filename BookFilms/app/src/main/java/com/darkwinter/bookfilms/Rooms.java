package com.darkwinter.bookfilms;

import java.io.Serializable;

/**
 * Created by DarkWinter on 10/27/17.
 */

public class Rooms implements Serializable {
    private String id;
    private String Capacity;
    private String Status;

    public Rooms() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
