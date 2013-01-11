package com.jbooktrader.platform.test;

import com.jbooktrader.platform.util.CLNYMEXMostLiquid;

import java.util.Calendar;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * Date: 1/9/13
 * Time: 12:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class NYMEXMostLiquidTest {

    @Test
    public void backupToBusinessDay1() {
        Calendar aSaturday = Calendar.getInstance();
        aSaturday.set(Calendar.YEAR,2013);
        aSaturday.set(Calendar.MONTH,Calendar.JANUARY);
        aSaturday.set(Calendar.DATE,5);

        assert(aSaturday.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
        CLNYMEXMostLiquid.backupToBusinessDay(aSaturday); // should back up to Friday
        assert(aSaturday.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY);
        CLNYMEXMostLiquid.backupToBusinessDay(aSaturday);
        assert(aSaturday.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY);  // should still be Friday

    }

    // http://www.cmegroup.com/trading/energy/crude-oil/light-sweet-crude_product_calendar_futures.html
    @Test
    public void getExpireDayForMonthYearTest() {
        // test some real NYMEX dates from the CL (Light Sweet Crude index futures

        int janExpire = CLNYMEXMostLiquid.getExpireDateForMonthYear(1, 2013);  // actually this is called the Feb delivery future, exprires in Jan though
        assert(janExpire == 22);
        int febExpire = CLNYMEXMostLiquid.getExpireDateForMonthYear(2, 2013);
        assert(febExpire == 20);
        int marExpire = CLNYMEXMostLiquid.getExpireDateForMonthYear(3, 2013);
        assert(marExpire == 20);
        int aprExpire = CLNYMEXMostLiquid.getExpireDateForMonthYear(4, 2013);
        assert(aprExpire == 22);
        int mayExpire = CLNYMEXMostLiquid.getExpireDateForMonthYear(5, 2013);
        assert(mayExpire == 21);
        int junExpire = CLNYMEXMostLiquid.getExpireDateForMonthYear(6, 2013);
        assert(junExpire == 20);
        int julExpire = CLNYMEXMostLiquid.getExpireDateForMonthYear(7, 2013);
        assert(julExpire == 22);

    }

    @Test
    public void getMostLiquidNymexExpiryTest1() {
        Calendar mydate = Calendar.getInstance();
        mydate.set(Calendar.YEAR, 2013);
        mydate.set(Calendar.MONTH, Calendar.JANUARY);
        mydate.set(Calendar.DATE, 5);

        String expiry = CLNYMEXMostLiquid.getMostLiquidExpiry(mydate);
        assertTrue("expiration of contract not correct", expiry.equals("201302"));  // note, this is called the Feburary Contract, but expries on Jan 22

    }

    @Test
    public void getMostLiquidNymexExpiryTestDecRolltoNewyear() {
        Calendar mydate = Calendar.getInstance();
        mydate.set(Calendar.YEAR, 2012);
        mydate.set(Calendar.MONTH, Calendar.DECEMBER);
        mydate.set(Calendar.DATE, 18);

        String expiry = CLNYMEXMostLiquid.getMostLiquidExpiry(mydate);
        assertEquals("expiration of contract not correct", "201302",expiry);  // note, this is called the Feburary Contract, but expries on Jan 22

    }

    @Test
    public void getMostLiquidNymexExpiryTestDecnoRollover() {
        Calendar mydate = Calendar.getInstance();
        mydate.set(Calendar.YEAR, 2012);
        mydate.set(Calendar.MONTH, Calendar.DECEMBER);
        mydate.set(Calendar.DATE, 8);

        String expiry = CLNYMEXMostLiquid.getMostLiquidExpiry(mydate);
        assertEquals("expiration of contract not correct", "201301",expiry);  // note, this is called the Feburary Contract, but expries on Jan 22

    }

    @Test
    public void getMostLiquidNymexExpiryTestTooClose() {
        Calendar mydate = Calendar.getInstance();
        mydate.set(Calendar.YEAR, 2013);
        mydate.set(Calendar.MONTH, Calendar.JANUARY);
        mydate.set(Calendar.DATE, 22);

        String expiry = CLNYMEXMostLiquid.getMostLiquidExpiry(mydate);
        assertEquals("expiration of contract not correct", "201303",expiry);  // note, this is called the Feburary Contract, but expries on Jan 22

    }

}


