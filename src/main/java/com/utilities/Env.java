package com.utilities;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
    private final static Dotenv env = Dotenv.configure().directory("./").filename(".env").load();

    public static String get(String var) {
        return env.get(var);
    }
}