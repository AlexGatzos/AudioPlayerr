package com.universitymusic.app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
  Connection getConnection() {
    Connection c = null;
    try {
      Class.forName("org.postgresql.Driver");
      c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/music_player?schema=public", "postgres",
          "postgres");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    return c;
  }
}