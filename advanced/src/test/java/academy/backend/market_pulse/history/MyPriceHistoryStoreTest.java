package academy.backend.market_pulse.history;

import academy.backend.market_pulse.history.impl.PriceHistoryStoreSkeleton;

class MyPriceHistoryStoreTest extends PriceHistoryStoreContractTest {
    @Override
    protected PriceHistoryStore createStore() {
        return new PriceHistoryStoreSkeleton();
    }
}