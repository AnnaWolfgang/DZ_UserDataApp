import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол):");
        String input = scanner.nextLine();

        // Разделение строки по пробелам
        String[] userData = input.split(" ");

        try {
            // Проверка количества данных
            if (userData.length != 6) {
                throw new IllegalArgumentException("Ошибка: введено некорректное количество данных. Требуется ввести 6 значений.");
            }

            // Попытка парсинга данных
            String lastName = userData[0];
            String firstName = userData[1];
            String middleName = userData[2];
            String birthDate = userData[3];
            String phoneNumber = userData[4];
            String gender = userData[5];

            // Проверка формата даты рождения
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate parsedDate;
            try {
                parsedDate = LocalDate.parse(birthDate, dateFormatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Введена некорректная дата, надо dd.MM.yyyy");
            }

            // Проверка номера телефона
            if (!phoneNumber.matches("\\d+")) {
                throw new NumberFormatException("Ошибка: номер телефона должен содержать только цифры.");
            }

            // Проверка пола
            if (!(gender.equals("f") || gender.equals("m"))) {
                throw new IllegalArgumentException("Ошибка: пол должен быть 'f' или 'm'.");
            }

            // Запись в файл
            writeToFile(lastName, firstName, middleName, parsedDate, phoneNumber, gender);

        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeToFile(String lastName, String firstName, String middleName, LocalDate birthDate, String phoneNumber, String gender) throws IOException {
        String fileName = lastName + ".txt";
        String dataToWrite = lastName + " " + firstName + " " + middleName + " " + birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + phoneNumber + " " + gender;

        // Используем класс Files для записи в файл
        Path filePath = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(dataToWrite);
            writer.newLine();
        }
    }
}