package br.com.comcet.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextContentProvider {
    public static String getContent(String[] args) {
        if (args.length > 0) {
            return processArguments(args);
        }
        return processInteractiveInput();
    }

    private static String processArguments(String[] args) {
        if (args[0].equals("-f") && args.length > 1) {
            return readFileContent(args[1]);
        }
        
        String input = args[0];
        return isExistingTextFile(input) ? readFileContent(input) : input;
    }

    private static boolean isExistingTextFile(String path) {
        return path != null && path.toLowerCase().endsWith(".txt") && new File(path).exists();
    }

    private static String readFileContent(String filePath) {
        try (Scanner sc = new Scanner(new File(filePath))) {
            return sc.hasNext() ? sc.useDelimiter("\\Z").next() : "";
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo não encontrado: " + filePath);
            return null;
        }
    }

    private static String processInteractiveInput() {
        try (Scanner keyboard = new Scanner(System.in)) {
            System.out.print("Digite o texto ou o caminho de um arquivo .txt: ");
            String input = keyboard.nextLine();
            return isExistingTextFile(input) ? readFileContent(input) : input;
        }
    }

}
