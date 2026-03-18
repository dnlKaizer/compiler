package br.com.comcet;

import br.com.comcet.tp0.TextStatistics;
import br.com.comcet.tp0.TextStatisticsCalculator;
import br.com.comcet.utils.TextContentProvider;

public class App {
    public static void main(String[] args) {
        String content = TextContentProvider.getContent(args);
        if (content == null) return;
        exibirEstatisticas(content);
    }

    public static void exibirEstatisticas(String text) {
        TextStatistics textStatistics = TextStatisticsCalculator.calculate(text);
        System.out.println(textStatistics);
    }
}