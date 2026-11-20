import java.util.*;
import java.util.stream.*;

record Film(String title, double rating, int year, String genre) {}

public class FilmStreamHomework {
    public static void main(String[] args) {
        List<Film> films = List.of(
                new Film("Крестный отец", 9.2, 1972, "Криминал"),
                new Film("Волк с Уолл-стрит", 8.0, 2013, "Биография"),
                new Film("Рокки", 8.1, 1976, "Драма"),
                new Film("Маска", 7.0, 1994, "Комедия"),
                new Film("Области тьмы", 7.4, 2011, "Триллер"),
                new Film("Неудержимые", 6.5, 2010, "Боевик"),
                new Film("Крид 3", 6.7, 2023, "Драма"),
                new Film("Во все тяжкие: Путь", 7.3, 2019, "Криминал")
        );

        List<String> topRatedTitles = films.stream()
                .filter(f -> f.rating() > 8.0)
                .map(Film::title)
                .collect(Collectors.toList());
        System.out.println("1. Фильмы с оценкой выше 8.0: " + topRatedTitles);

        double averageRating = films.stream()
                .mapToDouble(Film::rating)
                .average()
                .orElse(0.0);
        System.out.printf("2. Средняя оценка всех фильмов: %.2f%n", averageRating);

        long dramaCount = films.stream()
                .filter(f -> f.genre().equals("Драма"))
                .count();
        System.out.println("   Количество фильмов в жанре Драма: " + dramaCount);

        List<String> top3Films = films.stream()
                .sorted(Comparator.comparingDouble(Film::rating).reversed())
                .limit(3)
                .map(Film::title)
                .collect(Collectors.toList());
        System.out.println("3. Топ-3 фильма по оценке: " + top3Films);

        System.out.println("4. Проверка повторного вызова Stream:");
        Stream<Film> singleUseStream = films.stream();
        long totalCount = singleUseStream.count(); // Первый терминальный вызов (закрывает поток)
        System.out.println("   Первый вызов count(): " + totalCount);

        try {
            singleUseStream.forEach(System.out::println);
        } catch (IllegalStateException e) {
            System.out.println("   Поймана ошибка: " + e);
        }
    }
}