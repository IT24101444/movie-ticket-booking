package com.movieticket.service;

import com.movieticket.model.User;
import java.io.*;

public class UserService {
    private final String userFile = "src/main/resources/data/users.txt";

    public void saveUser(User user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true));
        writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getEmail());
        writer.newLine();
        writer.close();
    }

    public User findUser(String username, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(userFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                reader.close();
                return new User(parts[0], parts[1], parts[2]);
            }
        }
        reader.close();
        return null;
    }

    public User findUserByUsername(String username) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(userFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 3 && parts[0].equals(username)) {
                reader.close();
                return new User(parts[0], parts[1], parts[2]);
            }
        }
        reader.close();
        return null;
    }
}
