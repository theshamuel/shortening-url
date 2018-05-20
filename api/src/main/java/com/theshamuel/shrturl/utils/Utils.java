/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.utils;

import com.theshamuel.shrturl.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * The Utils class.
 *
 * @author Alex Gladkikh
 */
public class Utils {
    private static Logger logger =  LoggerFactory.getLogger(UserController.class);

    /**
     * Encrypt password to SHA-256 string.
     *
     * @param password the password
     * @param salt     the salt
     * @return string string
     */
    public static String pwd2sha256(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            password = password.concat(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 255) + 256, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword.toString();
    }


    /**
     * Gen salt for password.
     *
     * @return the string
     */
    public static String genSalt() {
        SecureRandom random;
        byte[] salt = new byte[20];
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt.toString();
    }


    /**
     * Generate short url sequence.
     *
     * @param minSize the minimal size of sequence
     * @return the result sequence
     */
    public static String generateShortUrlSeq(int minSize){
        StringBuffer seq  = new StringBuffer();
        Random rnd = new SecureRandom();
        int sizeSeq = minSize;
        if (minSize == 0) {
            minSize = 8;
            sizeSeq = minSize + rnd.nextInt((minSize + rnd.nextInt(2)) - minSize + 1);
        }
        final String number = "0123456789";
        final String letter = "abcdefghijklmnopqrstuvwxyz";
        final String letterUpp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int i = 0;
        while (i<sizeSeq){
            char next = 0;
            int range = 10;
            switch (rnd.nextInt(3)) {
                case 0:
                    next = 'a';
                    range = 26;
                    break;
                case 1:
                    next = '0';
                    range = 10;
                    break;
                case 2:
                    next = 'A';
                    range = 26;
                    break;
            }

            char currLetter = 0;
            if (next == 'a')
                currLetter = letter.toCharArray()[((rnd.nextInt(range)))];
            else if (next == 'A')
                currLetter = letterUpp.toCharArray()[((rnd.nextInt(range)))];
            else if (next == '0')
                currLetter = number.toCharArray()[((rnd.nextInt(range)))];

            seq.append(currLetter);
            i++;
        }

        return seq.toString();
    }

    /**
     * Gets punycode for pocessing Russian Federation domain (.рф).
     *
     * @param urlPath the url path
     * @return the punycode for .рф domain
     */
    public static String getPunycodeForRfDomain(String urlPath) {
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            logger.error("The URL {} is broken \n{}",urlPath, e);
        }
        StringBuilder result = new StringBuilder();
        result.append(url.getProtocol());
        result.append("://");
        String host = url.getHost();

        String[] tookens = host.split("\\.");
        for (int i = 0; i < tookens.length; i++) {
            tookens[i] = IDN.toASCII(tookens[i]);
        }
        for (int i= 0; i < tookens.length - 1; i++) {
            result.append(tookens[i]);
            result.append(".");
        }
        result.append(tookens[tookens.length-1]);
        result.append(url.getFile());
        return result.toString();
    }
}
