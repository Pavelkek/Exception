/*
 * Класс MyException
 * version - 2.0
 * Авторское право: все мое.
 */
package exception;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Класс сохраняющий телефонную
 * книгу в файл. Если контакт есть,
 * будет выбрасываться исключение и
 * выводится printStackTrace().
 * @version 2.0 16 Apr 2019
 * @author Pavel Samotaev
 */
public final class MyException {
    private MyException() { }
    public static void fileWrite(final HashMap<String, Integer> map) {
        class GetException extends Exception { }
        int lengthMas = 0;
        int count = 0;
        try (FileInputStream fin = new FileInputStream("PhoneBook.txt")) {
            int i;
            while ((i = fin.read()) != -1) {
                if (((char) i == ' ') || ((char) i == '\n')) {
                    lengthMas++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String[] mas = new String[lengthMas];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = "";
        }
        try (FileInputStream fin = new FileInputStream("PhoneBook.txt")) {
            int i;
            while ((i = fin.read()) != -1) {
                if (((char) i == ' ') || ((char) i == '\n')) {
                    count++;
                }
                if (((char) i != ' ') && ((char) i != '\n')) {
                    mas[count] += (char) i;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            for (int i = 0; i < mas.length; i++) {
                if (mas[i].equals(entry.getKey())) {
                    try {
                        throw (new GetException());
                    } catch (GetException getException) {
                        getException.printStackTrace();
                        map.remove(entry.getKey());
                    }
                }
            }
        }
        for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
            try (FileOutputStream fos = new FileOutputStream(
                    "C://Users//DNS//Desktop//Exception//PhoneBook.txt", true)) {
                byte[] buffer;
                buffer = (entry.getKey() + " " + entry.getValue() + "\n").getBytes();
                fos.write(buffer, 0, buffer.length);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void main(final String[] args) {
        /*
         * Сначала в файл добавляется Петр,
         * потом выдается ошибка из-за
         * второй попытки добавления Петра и добавляется Арман.
         */
        final int phone = 123456;
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Petr", phone);
        MyException.fileWrite(map);
        map.put("Arman", phone);
        MyException.fileWrite(map);
    }
}
