package com.zs.codeDojo.models.auth;

import java.io.File;
// import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import com.zs.codeDojo.models.DAO.User;

public class ProfileOperations {

    public boolean updateProfileImage(String path, String content, User usr) {

        try {

            byte[] imageBytes = Base64.getDecoder().decode(content);
            writeBytesToFile(path, imageBytes);
            System.out.println("Image content has been rewritten successfully.");
            return true;
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return false;
        }

    }

    private static void writeBytesToFile(String filePath, byte[] data) throws IOException {

        File file = new File(filePath);
        
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
    }

}
