package cn.com.efuture.o2o.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SerialHelper {

    @Value("${SID}")
    private String SID;


    private static String lastDateString = "";
    private static int lastSuffixQ = 100;
    private static int lastSuffixS = 100;
    private static int lastSuffixV = 100;

    public synchronized String getInvqueSerial() {
        String res;
        String dateString = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        //每秒最多8999单
        int suffix;
        if (dateString.equalsIgnoreCase(lastDateString)) {
            suffix = lastSuffixQ + 1;
        } else {
            suffix = 1000;
        }

        res = "Q" +SID + dateString + suffix;
        lastSuffixQ = suffix;
        lastDateString = dateString;
        return res;
    }

    public synchronized String getSheetSerial() {
        String res;
        String dateString = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        //每秒最多8999单
        int suffix;
        if (dateString.equalsIgnoreCase(lastDateString)) {
            suffix = lastSuffixS + 1;
        } else {
            suffix = 1000;
        }

        res = "S" + SID + dateString + suffix;
        lastSuffixS = suffix;
        lastDateString = dateString;
        return res;
    }

    public synchronized String getInvoiceSerial() {
        String res;
        String dateString = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        //每秒最多8999单
        int suffix;
        if (dateString.equalsIgnoreCase(lastDateString)) {
            suffix = lastSuffixV + 1;
        } else {
            suffix = 1000;
        }

        res = "V" + SID + dateString + suffix;
        lastSuffixV = suffix;
        lastDateString = dateString;
        return res;
    }
}
