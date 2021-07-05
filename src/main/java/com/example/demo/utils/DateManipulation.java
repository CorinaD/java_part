package com.example.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
public class DateManipulation {
        public static final long REQUIRED_WORKING_HOURS = 8 * 3600000; // in ms

        public static String convertMsToActualDate(long milliseconds) {
            DateFormat simple = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            Date result = new Date(milliseconds);

            System.out.println(simple.format(result));
            return simple.format(result);
        }

        /**
         *
         * @param milliseconds
         * @return the milliseconds converted to HH:mm
         */
        public static String msToHHmm(long milliseconds) {
            if (0 == milliseconds) {
                return "-";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date resultdate = new Date(milliseconds);
                return sdf.format(resultdate);
            }
        }

        public static String getCurrentDate() {
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd mm yyyy");
            return date.format(formatter);
        }

    }
