package com.example.hci.data;

public class WeatherResponse {
    public Main main;
    public Weather[] weather;

    public static class Main {
        public double temp;
        public int humidity;
    }

    public static class Weather {
        public String description;
    }
} 