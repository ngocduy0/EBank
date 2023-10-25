/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author lekha
 */
public class EBank {

    private static final Scanner in = new Scanner(System.in);
    private static boolean isEnglish = true;

    private static final String ACCOUNT_NUMBER_VALID = "^\\d{10}$";
    private static final char[] chars = {'1', 'A', 'a', 'B', 'b', 'C',
        'c', '2', 'D', 'd', 'E', 'e', 'F', 'f', '3', 'G', 'g', 'H', 'h',
        'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', '4', 'M', 'm', 'N', 'n',
        'O', 'o', '5', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't',
        '6', '7', 'U', 'u', 'V', 'v', 'U', 'u', 'W', 'w', '8', 'X', 'x',
        'Y', 'y', 'Z', 'z', '9'};

    public int checkInputIntLimit(int min, int max) {
        while (true) {
            try {
                int result = Integer.parseInt(in.nextLine());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException ex) {
                System.err.println("Input enter from " + min + " to" + max);
                System.out.println("Enter agian: ");
            }
        }
    }

    public String checkInputString() {
        while (true) {
            String result = in.nextLine();
            if (result.isEmpty()) {
                System.err.println("Not empty");
                System.out.println("Enter again: ");
            } else {
                return result;
            }
        }
    }

    public int checkInputAccount() {
        int accountAttempts = 0;
        while (accountAttempts < 5) {
            String result = in.nextLine();
            if (result.matches(ACCOUNT_NUMBER_VALID)) {
                return Integer.parseInt(result);
            }
            System.err.println("Account Input Error");
            System.out.println("Enter Again: ");
            accountAttempts++;
        }
        System.out.println("Bạn đã nhập sai số tài khoản quá 5 lần. Chương trình sẽ thoát.");
        System.exit(0);
        return -1;
    }

    public String checkInputPassword() {
        int passwordAttempts = 0;
        while (passwordAttempts < 5) {
            String result = checkInputString();
            if (isValidPassword(result)) {
                return result;
            }
            System.err.println("Password must be between 8 and 31 characters long.");
            System.err.println("Password must contain at least one letter and one number.");
            System.out.println("Enter again: ");
            passwordAttempts++;
        }
        System.out.println("You have entered the wrong password more than 5 times. Your account has been locked.");
        System.exit(0);
        return null;
    }

    public boolean isValidPassword(String password) {
        int lengthPassword = password.length();
        if (lengthPassword < 8 || lengthPassword > 31) {
           
            return false;
        }

        boolean hasDigit = false;
        boolean hasLetter = false;

        for (int i = 0; i < lengthPassword; i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }

        if (!hasDigit || !hasLetter) {
            System.err.println("Password must contain at least one letter and one number.");
            return false;
        }

        return true;
    }

    public boolean checkInputCaptcha(String captchaGenerated) {
        System.out.println("My Captcha: " +captchaGenerated);
        System.out.print(getMessage("Enter Captcha"));
        String captchaInput = checkInputString();
        for (int i = 0; i < captchaInput.length(); i++) {
            if (captchaGenerated.indexOf(captchaInput.charAt(i)) == -1) {
                System.err.println("Err Captcha Incorrect");
                System.out.println();
                return false;
            }
        }
        return true;
    }

    public String generateCaptchaText() {
        StringBuilder sb = new StringBuilder();
        int index;
        for (int i = 0; i < 6; i++) {
            index = (int) (Math.random() * chars.length);
            sb.append(chars[index]);
        }
        return sb.toString();
    }

    public void display() {
        System.out.println("1. English");
        System.out.println("2. Tiếng Việt");
        System.out.println("3. Exit");
        System.out.print(getMessage("Choose Language"));
        int choice = checkInputIntLimit(1, 3);
        switch (choice) {
            case 1:
                isEnglish = true;
                break;
            case 2:
                isEnglish = false;
                break;
            case 3:
                return;
        }

        System.out.print(getMessage("Enter Account Number"));
        int accountNumber = checkInputAccount();
        System.out.print(getMessage("Enter Password"));
        String passString = checkInputPassword();
        String captchaGenerated = generateCaptchaText();
        while (true) {
            if (checkInputCaptcha(captchaGenerated)) {
                System.err.println("Log in Succesfull ");
                System.out.println();
                return;
            }
        }
    }

    public String getMessage(String key) {
        if (isEnglish) {
            // Nếu là tiếng Anh
            switch (key) {
                case "Choose Language":
                    return "Please choose one option: ";
                case "Enter Account Number":
                    return "Enter account number: ";
                case "Enter Password":
                    return "Enter password: ";
                case "Enter Captcha":
                    return "Enter Captcha: ";
                default:
                    return "Invalid input"
                            + " Please try again: ";
            }
        } else {
            // Nếu là tiếng Việt
            switch (key) {
                case "Choose Language":
                    return "Vui lòng chọn một tùy chọn: ";
                case "Enter Account Number":
                    return "Nhập số tài khoản: ";
                case "enterPassword":
                    return "Nhập mật khẩu: ";
                case "Enter Captcha":
                    return "Nhập Captcha: ";
                default:
                    return "Nhập không hợp lệ. Vui lòng thử lại.";
            }
        }
    }

    public static void main(String[] args) {
        EBank ebank = new EBank();
        ebank.display();
    }
}
