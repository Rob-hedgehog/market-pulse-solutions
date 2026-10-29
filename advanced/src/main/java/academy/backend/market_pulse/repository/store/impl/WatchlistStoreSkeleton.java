package academy.backend.market_pulse.repository.store.impl;

import academy.backend.market_pulse.repository.store.WatchlistStore;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Заготовка для самостоятельной работы (см. «Требования к файловому хранилищу.md», семинар 7) —
 * структура есть, решения нет. Формат файла — на ваш выбор (JSON, построчный текст и т.д.); главное,
 * чтобы {@code load(save(tickers))} возвращал тот же набор тикеров в том же порядке. Проверьте себя
 * тестом {@code WatchlistStoreContractTest} (замените в нём эталонную реализацию на свою).
 */
public class WatchlistStoreSkeleton implements WatchlistStore {

    @Override
    public void save(Path path, Set<String> tickers) {
        try {
            Files.write(path,tickers, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> load(Path path) {
        try {
            if (!Files.exists(path)) {
                return Set.of();
            }
            return new LinkedHashSet<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
