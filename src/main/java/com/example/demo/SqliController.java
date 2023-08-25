package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
@RequestMapping("/sqli")
class SqliController {
    @GetMapping
    public String intro() {
        return "<h2>SQL Injection Demo</h2>" +
                "<li><a href='/sqli/demo1?username=john'>/sqli/demo1?username=john</a> (Look up john by username)" +
                "<li><a href='/sqli/demo1?username=jane'>/sqli/demo1?username=jane</a> (Look up jane by username)" +
                "<li><a href='/sqli/demo1?username=1%27%20OR%20%271%27=%271'>/sqli/demo1?username=1%27%20OR%20%271%27=%271</a>  (Hack it)";
    }

    @GetMapping("/demo1")
    public String sqliDemo1(@RequestParam(required = false) String username) {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
            Statement statement = connection.createStatement();

            // Create a table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255))");

            // Insert records
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('admin', 'adminpw1111')");
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('john', 'johnpw1234')");
            statement.executeUpdate("INSERT INTO users (username, password) VALUES ('jane', 'janepw2345')");

            // Select users by provided username
            String selectQuery = "SELECT * FROM users WHERE username = '" + username + "'";
            ResultSet resultSet = statement.executeQuery(selectQuery);
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String selectedUsername = resultSet.getString("username");
                String password = resultSet.getString("password");

                JSONObject userJson = new JSONObject();
                userJson.put("id", id);
                userJson.put("username", selectedUsername);
                userJson.put("password", password);

                jsonArray.put(userJson);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();

            return jsonArray.toString(4);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    @GetMapping("/demo2")
    public String sqliDemo2() {
        return "Not implemented";
    }
}
