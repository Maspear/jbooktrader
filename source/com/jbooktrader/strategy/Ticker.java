package com.jbooktrader.strategy;

import com.jbooktrader.indicator.depth.*;
import com.jbooktrader.indicator.index.*;
import com.jbooktrader.platform.indicator.*;
import com.jbooktrader.platform.model.*;
import com.jbooktrader.platform.optimizer.*;

/**
 *
 */
public class Ticker extends StrategyES {

    // Technical indicators
    private final Indicator balanceEmaInd, tickIndexEmaInd;

    // Strategy parameters names
    private static final String PERIOD = "Period";
    private static final String BALANCE_ENTRY = "DepthBalance Entry";
    private static final String TICK_ENTRY = "Tick Entry";


    // Strategy parameters values
    private final int balanceEntry, tickEntry;


    public Ticker(StrategyParams optimizationParams) throws JBookTraderException {
        super(optimizationParams);

        balanceEntry = getParam(BALANCE_ENTRY);
        tickEntry = getParam(TICK_ENTRY);

        // Create technical indicators
        balanceEmaInd = new DepthBalanceEMA(getParam(PERIOD));
        tickIndexEmaInd = new TickIndexEMA(getParam(PERIOD));

        addIndicator(balanceEmaInd);
        addIndicator(tickIndexEmaInd);
    }

    /**
     * Adds parameters to strategy. Each parameter must have 5 values:
     * name: identifier
     * min, max, step: range for optimizer
     * value: used in backtesting and trading
     */
    @Override
    public void setParams() {
        addParam(PERIOD, 1, 100, 1, 44);
        addParam(BALANCE_ENTRY, 0, 50, 1, 24);
        addParam(TICK_ENTRY, 50, 800, 50, 200);
    }

    /**
     * This method is invoked by the framework when an order book changes and the technical
     * indicators are recalculated. This is where the strategy itself should be defined.
     */
    @Override
    public void onBookChange() {
        double balanceEma = balanceEmaInd.getValue();
        double tickIndex = tickIndexEmaInd.getValue();
        if (balanceEma >= balanceEntry && tickIndex >= tickEntry) {
            setPosition(1);
        } else if (balanceEma <= -balanceEntry && tickIndex <= -tickEntry) {
            setPosition(-1);
        }
    }
}
