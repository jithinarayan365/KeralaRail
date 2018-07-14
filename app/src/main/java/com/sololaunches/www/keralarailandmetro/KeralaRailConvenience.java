package com.sololaunches.www.keralarailandmetro;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by jithinarayan365@gmail.com on 7/9/2017.
 */

public class KeralaRailConvenience {


    public static ArrayList setTrainOrder(ArrayList<String> list) {
        HashMap<Long, String> map = new HashMap<Long, String>();

        ArrayList<Long> numbers = new ArrayList<Long>();
        ArrayList<String> sortedList = new ArrayList<String>();
        sortedList.add("Arrival  Train-No         Destination                                   ");

        for (String s : list) {
            String[] timing = s.split("\\s+");
            map.put(Long.parseLong(timing[0].trim().substring(1) + timing[1].trim()), s);
            numbers.add(Long.parseLong(timing[0].trim().substring(1) + timing[1].trim()));
        }
        Collections.sort(numbers);


        for (long num : numbers) {
            String value = map.get(num);
            if (value != null) {

                String time = value.substring(0, 5);
                String rest = value.substring(5, value.length());
                String formattedtime = time.substring(1, 3) + ":" + time.substring(3, 5);
                String newValue = formattedtime + rest;
                String[] splitArr = newValue.split("#");
                sortedList.add(splitArr[0]);
                sortedList.add(splitArr[1]);

            } else {
                continue;
            }
        }
        return sortedList;
    }


    public static ArrayList<String> setTrainOrderForTrain(ArrayList<String> list) {

        HashMap<Integer, String> map = new HashMap<Integer, String>();

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        ArrayList<String> sortedList = new ArrayList<String>();
        sortedList.add(" Arrival   Day Station                           ");

        for (String s : list) {
            String[] timing = s.split("\\s+");
            map.put(Integer.parseInt(timing[0]), s);
            numbers.add(Integer.parseInt(timing[0]));
        }

        Collections.sort(numbers);
        for (int num : numbers) {
            String value = map.get(num);
            String time = value.substring(0, 5);
            String rest = value.substring(5, value.length());
            String dayVar = time.substring(0, 1);
            String day = String.valueOf(Integer.parseInt(dayVar));

            String formattedtime = time.substring(1, 3) + ":" + time.substring(3, 5);
            String newValue = String.format("%7s", formattedtime) + "   " + day + "  " + rest;

            sortedList.add(newValue);
        }

        return sortedList;
    }



    public static int getIndexofColumn(String stationcode) {

        String[] stationsCodes = new String[]{"Primary key", "Train Number", "Train Name", "UPDATED_ON", "MJS", "UPLA", "KMQ", "KGQ", "KOK", "BKR", "KZE", "NLE", "CHV", "CDRA", "TKQ", "PAY", "ELM", "PAZ", "KPQ", "PPNS", "VAPM", "CQL", "CAN", "CS", "ETK", "DMD", "TLY", "JGE", "MAHE", "MUKE", "NAU", "BDJ", "IGL", "PYOL", "TKT", "VEK", "QLD", "CMC", "ETR", "WH", "VLL", "CLT", "KUL", "FK", "KN", "VLI", "PGI", "TA", "TIR", "TUA", "KTU", "PEU", "PUM", "KODN", "PTB", "KRKD", "NIL", "VNB", "TUV", "MLTR", "PKQ", "AAM", "CQA", "KZC", "VPZ", "VDKS", "SRR", "CLMD", "KJKD", "KTKU", "PGT", "PLL", "MNY", "LDY", "PLPM", "OTP", "MNUR", "VTK", "MUC", "WKI", "MGK", "PNQ", "GUV", "AMLR", "TCR", "OLR", "PUK", "NYI", "IJK", "CKI", "DINR", "KRAN", "KUC", "AFK", "CWR", "AWY", "KLMR", "IPL", "ERN", "ERS", "TNU", "KUMM", "AROR", "EZP", "TUVR", "VAY", "SRTL", "TRVZ", "MAKM", "KAVR", "TMPY", "ALLP", "PNRR", "AMPA", "TZH", "KVTA", "HAD", "CHPD", "TRTR", "MNTT", "KPTM", "PVRD", "VARD", "KDTY", "KRPP", "ETM", "KKQ", "KTYM", "CGV", "CGY", "TRVL", "CNGR", "CYN", "MVLK", "KYJ", "OCR", "KPV", "STKT", "MQO", "PRND", "QLN", "IRP", "MYY", "PVU", "KFI", "EVA", "VAK", "AMY", "KVU", "CRY", "PGZ", "MQU", "KXP", "KZK", "VELI", "KCVL", "TVP", "TVC", "NEM", "BRAM", "NYY", "AMVA", "DAVM", "PASA", "KLQ", "CTPE", "KUV", "KFV", "EKN", "KKZ", "KIF", "AVS", "PUU", "EDN", "OKL", "TML", "KTHY", "AYVN", "AYV"};
        int i = 0;
        for (String s : stationsCodes) {
            if (s.equals(stationcode)) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    public String checkNULL(String str) {

        if (str == null) {

            return " ";

        } else if (str.length() > -1) {

            return str;

        } else return " ";

    }


    public static String getDayOfWeek() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case 1:
                return "S";
            case 2:
                return "M";
            case 3:
                return "T";
            case 4:
                return "W";
            case 5:
                return "H";
            case 6:
                return "F";
            case 7:
                return "A";
        }

        return null;

    }


    public static String getDays(String str) {

        String retStr = "";

        char[] arrDay = str.toCharArray();


        for (char c : arrDay) {

            String temp = String.valueOf(c);

            if (temp.equalsIgnoreCase("S")) {

                retStr += "Sun,";

            } else if (temp.equalsIgnoreCase("M")) {
                retStr += "Mon,";

            } else if (temp.equalsIgnoreCase("T")) {

                retStr += "Tue,";

            } else if (temp.equalsIgnoreCase("W")) {

                retStr += "Wed,";

            } else if (temp.equalsIgnoreCase("H")) {

                retStr += "Thu,";


            } else if (temp.equalsIgnoreCase("F")) {

                retStr += "Fri,";


            } else if (temp.equalsIgnoreCase("A")) {

                retStr += "Sat,";


            } else if (temp.equalsIgnoreCase("D")) {

                retStr += "Daily";


            }
        }


        return retStr;

    }


    public static ArrayList<String> getSortedMetroTimeTableUp(ArrayList<Integer> list) {
        ArrayList<String> strList = new ArrayList<String>();
        strList.add("  ");
        for (int n : list) {
            String st = String.valueOf(n);
            String value = String.format("%4s", st.replace(' ', '0'));
            String buffered = value.substring(0, 2) + ":" + value.substring(2, 4);
            strList.add(buffered + "      ALUVA");
        }
        return strList;
    }


    public static ArrayList<String> getSortedMetroTimeTableDw(ArrayList<Integer> list) {
        ArrayList<String> strList = new ArrayList<String>();
        strList.add("  ");

        for (int n : list) {
            String st = String.valueOf(n);
            String value = String.format("%4s", st.replace(' ', '0'));
            String buffered = value.substring(0, 2) + ":" + value.substring(2, 4);

            strList.add(buffered + "      PETTAH");
        }
        return strList;
    }

    public static String getAccurateDay(String day, String dayFormed) {


        ArrayList<String> list = new ArrayList<String>();
        list.add("S");
        list.add("M");
        list.add("T");
        list.add("W");
        list.add("H");
        list.add("F");
        list.add("A");


        if (dayFormed.equals("D")) {

            return dayFormed;

        } else {

            int fwd = Integer.parseInt(day);
            char[] crset = dayFormed.toCharArray();


            StringBuffer stbf = new StringBuffer();

            for (char c : crset) {

                int i = list.indexOf(String.valueOf(c));

                int nw = i;

                // for(int k = fwd;k>1;k--){

                int temp = nw + (fwd - 1);

                if (temp > 6) {
                    temp = temp - 7;
                }


                String s = list.get(temp);


                stbf.append(s);
                // }

            }

            return stbf.toString();

        }

    }

    public String getMetroCode(int position) {

        String[] metroArr = new String[]{"AUV", "PCD", "CPD", "ABK", "MTM", "KLMR", "CUSAT", "PTP", "IPL", "CSKP", "PLV", "JLNS", "KLR", "LIS", "MGR", "MRJ", "ERS", "KDV", "ELM", "VYT", "TYK", "EKP"};


        if (position > 0) {
            String returnValue = metroArr[position - 1];
            return returnValue;
        }

        return null;
    }

/*
    public String getShortQueries(String sent) {
        while (sent != null) {

            StringBuilder sb = new StringBuilder();
            String[] str = sent.split(",");
            for (String s : str) {


                String matcher = null;

                if (s.contains("WHERE")) {

                    String[] array2 = s.split("WHERE");
                    String temp = array2[0];
                    String[] tempArr = temp.split("=");
                    String t1[] = tempArr[1].toString().split("-");


                    String Tmatcher = t1[1];

                    if (sb.toString().contains(Tmatcher)) {
                        Log.d("not adding Tmatcher", "getShortQueries: " + s + " final " + sb);
                        sb.append("WHERE " + array2[1]);
                        continue;
                    } else {
                        sb.append(s);
                    }

                } else if (s.contains("=")) {
                    String[] arrey = s.split("=");

                    String t1[] = arrey[1].toString().split("-");


                    matcher = t1[1];
                    // matcher = arrey[1];

                    if (sb.toString().contains(matcher)) {
                        Log.d("not adding string", "getShortQueries: " + s + " final " + sb);
                        continue;
                    } else {

                        sb.append(s + ",");

                    }
                }


            }
            return sb.toString();
        }
        return null;
    }

    */
}

