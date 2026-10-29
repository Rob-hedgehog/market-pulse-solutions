package academy.backend.market_pulse.history.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import academy.backend.market_pulse.history.PriceHistoryStore;
import academy.backend.market_pulse.history.PricePoint;


/**
 * Заготовка для самостоятельной работы (см. «Требования к файловому хранилищу.md», семинар 7) —
 * структура есть, решения нет. Формат файла — на ваш выбор (бинарный, текстовый и т.д.); главное,
 * чтобы каждый {@link #append} создавал НОВЫЙ файл (не переписывал предыдущий) и
 * {@link #load(Path, String)} возвращал все сохранённые точки по тикеру в порядке возрастания
 * времени. Проверьте себя тестом {@code PriceHistoryStoreContractTest} (замените в нём эталонную
 * реализацию на свою).
 */
public class PriceHistoryStoreSkeleton implements PriceHistoryStore {

    @Override
    public Path append(Path directory, PricePoint point) {
        try {
            Files.createDirectories(directory);
            Path file = directory.resolve(System.nanoTime() + ".txt");
            Files.writeString(file, point.ticker() + ";" + point.timestamp() + ";" + point.price(),
                    StandardCharsets.UTF_8);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PricePoint> load(Path directory, String ticker) {
        try {
            if (!Files.exists(directory)) {
                return new ArrayList<>();
            }
            List<PricePoint> result = new ArrayList<>();
            for (Path f : Files.list(directory).toList()) {
                String[] p = Files.readString(f, StandardCharsets.UTF_8).split(";");
                if (p[0].equals(ticker)) {
                    result.add(new PricePoint(p[0], Instant.parse(p[1]), new BigDecimal(p[2])));
                }
            }
            result.sort(Comparator.comparing(PricePoint::timestamp));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
