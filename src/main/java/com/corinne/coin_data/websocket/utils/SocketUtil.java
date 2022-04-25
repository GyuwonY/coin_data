package com.corinne.coin_data.websocket.utils;

public class SocketUtil {
    public static String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }
}
