package com.axreng.backend.util;

import java.util.UUID;

public class Util {

    public String generateID;

    public String generateID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
