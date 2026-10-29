package academy.backend.market_pulse.repository.store.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import academy.backend.market_pulse.factory.InstrumentFactories;
import academy.backend.market_pulse.model.Currency;
import academy.backend.market_pulse.model.Quote;
import academy.backend.market_pulse.repository.store.QuoteFileStore;

/**
 * Заготовка для самостоятельной работы (см. «Требования к файловому хранилищу.md», семинар 7) —
 * структура есть, решения нет. Формат файла — на ваш выбор (JSON, CSV, построчный текст); главное,
 * чтобы {@code load(save(quotes))} возвращал те же котировки. Проверьте себя тестом
 * {@code QuoteFileStoreContractTest} (замените в нём эталонную реализацию на свою).
 */
public class QuoteFileStoreSkeleton implements QuoteFileStore {

    @Override
    public void save(Path path, List<Quote> quotes) {
        try {
            List<String> lines = new ArrayList<>();
            for (Quote q : quotes) {
                lines.add(q.getInstrument().getType() + ";" + q.getInstrument().getTicker() + ";"
                        + q.getInstrument().getName() + ";" + q.getInstrument().getCurrency() + ";"
                        + q.getPrice() + ";" + q.getChangePercent());
            }
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Quote> load(Path path) {
        try {
            if (!Files.exists(path)) return new ArrayList<>();
            List<Quote> result = new ArrayList<>();
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                String[] p = line.split(";");
                result.add(new Quote(
                        InstrumentFactories.create(p[0], p[1], p[2], Currency.valueOf(p[3])), new BigDecimal(p[4]), new BigDecimal(p[5])));
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
