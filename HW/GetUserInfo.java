package HW;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetUserInfo {
    public static void main(String[] args) {
        getUserInfo();
    }

    public static void getUserInfo() { // Метод для получения данных от пользователя
        System.out.println("Введите ФИО, дату рождения, номер телефона и пол через пробел:");
        Scanner keyboard = new Scanner(System.in);
        String userInfo = keyboard.nextLine();
        String[] data = userInfo.split(" "); // Массив введенных данных

        if (checkInfo(data)) {
            System.out.println("Верный ввод");
        }
        else {
            System.out.println("Ошибка ввода");
        }

        createFile(data); // создание файла
    }

    public static boolean checkLength(String[] info) { // Метод для проверки количества введенных данных
        final int size = 6; // Фиксированная длина входных данных
        try {
            if (info.length < size || info.length > size) {
                throw new InvalidInputException("Введено неверное количество данных", info.length);
            }
        } catch(InvalidInputException e)
        {
           if (e.getSize() > size) {
                System.out.println("Необходимо ввести меньше данных");
           }
           else if (e.getSize() < size) {
                System.out.println("Необходимо ввести больше данных");
           }
           return false; // возвращаем false, если длина введеных данных неверная
        }
        return true;
    }

    public static boolean isNumeric(String str) { // Метод для проверки, что имя введено верно (без лишних символов и цифр)
        if (str.matches("-?\\d+(\\.\\d+)?") || str.matches("!@#$%^&*"))
            return true;
        return false;
    }

    public static boolean checkInfo(String[] info) { // Метод для проверки форматов введенных данных
        String surname, name, midName, date;
        Integer phoneNumber;
        Character gender;

        surname = info[0];
        name = info[1];
        midName = info[2];

        try {
        if (isNumeric(surname) || isNumeric(name) || isNumeric(midName)) { 
            throw new IllegalArgumentException("Неверный тип данных для ФИО");
        }
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        date = info[3];
        try {
            if (!date.matches("\\d{2}.\\d{2}.\\d{4}")) {
                throw new Exception("Неверный формат даты");
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        try {
            phoneNumber = Integer.parseInt(info[4]);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат номера телефона");
            return false;
        }

        try {
            if (info[5].length() == 1 && !(info[5].matches("fm"))){
                gender = info[5].charAt(0);
            } 
            else {
                throw new IllegalArgumentException("Неверный формат ввода пола");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean createFile(String[] info) { // Метод для создания файла
        String surname = info[0];
        try {
            File file = new File(surname + ".txt");
            if (file.createNewFile()) {
                System.out.println("Файл создан: " + file.getName());
            }

        } catch (IOException e) {
            System.out.println("Ошибка открытия файла");
            e.printStackTrace();
            return false;
        } 
        addFileInfo(info); // добавление информации о пользователе в файл
        return true;
    }

    public static void addFileInfo(String[] info) { // Метод для добавления информации о пользователе в файл
        String surname = info[0];
        try {
            FileWriter fw = new FileWriter(surname + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < info.length; i++) {
                bw.write(info[i] + " ");
            }
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
            e.printStackTrace();
        }
    }
}
