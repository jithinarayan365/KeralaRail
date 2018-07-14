package com.sololaunches.www.keralarailandmetro;

import java.util.HashMap;

/**
 * Created by hp on 9/6/2017.
 */

public class PhoneNumberGetter {

    public HashMap<String, String[]> getPhoneNumber(String district) {


        HashMap<String, String[]> phoneNumbers = new HashMap<String, String[]>();
        String[] arr = null;
        String[] arr2 = null;
        String[] arr3 = null;
        String[] arr4 = null;
        String[] arr5 = null;
        String[] arr6 = null;
        String[] arr7 = null;

        switch (district) {


            case "Kasargod":
                arr = new String[]{"04994222960 ", "100"};
                arr2 = new String[]{"04994-223030", "9846200100", "999504000"};
                arr3 = new String[]{"04994-225580", "0467-2204333"};
                arr4 = new String[]{"04994-230101", "101"};
                arr5 = new String[]{"04994-226818", "04994-230524"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"04994-257591", "1091"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;

            case "Trichur":
                arr = new String[]{"0487-2424193", "100"};
                arr2 = new String[]{"9497981119", "0487-2428111"};
                arr3 = new String[]{"0487-2200317", "0487-2320784", "0487-2427383"};
                arr4 = new String[]{"0487-2423650", "101"};
                arr5 = new String[]{"0487-2388780", "0487-2200310"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"04994-257591", "1091"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Cannore":

                arr = new String[]{"0497-2763337", "100"};
                arr2 = new String[]{"9846200100", "9497981123", "0497-2705018"};
                arr3 = new String[]{"0490-2326050", "9496233788", "0497-2808080"};
                arr4 = new String[]{"0497-2701092", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr5 = new String[]{"0497-2491168", "0497-2712982"};
                arr7 = new String[]{"04994-257591", "1091"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Kozhikode":
                arr = new String[]{"100", "0495-2721831", "0496-2523091"};
                arr2 = new String[]{"9846200100", "9497981122", "0495-2703499"};
                arr3 = new String[]{"0495-2357457", "0495-2721998", "0495-2365917"};
                arr4 = new String[]{"0495- 2321654", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"0495-2724420", "1091"};
                arr5 = new String[]{"0495-2356530", "0495-2721123"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;

            case "Malapurram":
                arr = new String[]{"0483-2734966", "100"};
                arr2 = new String[]{"9846200100"};
                arr3 = new String[]{"0493-3226505", "0494-2422044", "0483-2766880"};
                arr4 = new String[]{"0483-2734800", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"9497963365", "1091"};
                arr5 = new String[]{"0494-2423834", "0494-2423834"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Ernakulam":
                arr = new String[]{"100", "0484-2359200", "0484-2621100"};
                arr2 = new String[]{"9846200100", "09497981118", "0484-2376359"};
                arr3 = new String[]{"0484-4123456", "0484-361549", "0484-2381762", "0484-2411460"};
                arr4 = new String[]{"0484-2205550", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"1091"};
                arr5 = new String[]{"0484-2361251", "0484-2381768"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Wayanad":
                arr = new String[]{"04936-205808 ", "100"};
                arr2 = new String[]{"9846200100"};
                arr3 = new String[]{"04936-223920", "04935-240264"};
                arr4 = new String[]{"04936-202333", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"04936-206127", "1091"};
                arr5 = new String[]{"04936-220102", "04936-202711"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Palakkad":
                arr = new String[]{"0491-2522340 ", "100"};
                arr2 = new String[]{"9846200100", "9497981121", "0491-2555218"};
                arr3 = new String[]{"9447393396", "0491-2534524 "};
                arr4 = new String[]{"0491-2505701", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"0491-2504650", "1091"};
                arr5 = new String[]{"0491-2528200", "0491-2538320"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Thiruvananthapuram":
                arr = new String[]{"100", "0471-2331843 ", "0471-2316995 "};
                arr2 = new String[]{"9846200100", "0471-2331258"};
                arr3 = new String[]{"0471-2528230", "0471-2307874", "0471-2442541", "0471-2524477", "0470-2646565", "0471-2323457"};
                arr4 = new String[]{"0471-2333101", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"0471-2321555", "1091"};
                arr5 = new String[]{"0471-2447575", "0471-2323772"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Kollam":
                arr = new String[]{"0474-2746000 ", "100"};
                arr2 = new String[]{"9846200100", "9497981114", "0474-2748073"};
                arr3 = new String[]{"9495753221", "9387324072", "0474-2766551", "9447223157", "0474-3041209"};
                arr4 = new String[]{"0474-2746200", "101"};
                arr5 = new String[]{"0474-2742332", "0474-2680477"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"0474-2764579", "1091"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Alappuzha":

                arr = new String[]{"0477-2251166 ", "100"};
                arr2 = new String[]{"9846200100", "9497981117", "0477-2253823"};
                arr3 = new String[]{"0477-2234746", "9446374886", "0477-2282709"};
                arr4 = new String[]{"0477-2230303", "101"};
                arr5 = new String[]{"0477-245312", "0477-2272135"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"1091"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Pathanamthita":
                arr = new String[]{"0468-2222226", "100"};
                arr2 = new String[]{"9846200100"};
                arr3 = new String[]{"0473-4226997", "9744837560"};
                arr4 = new String[]{"0468-2222001", "101"};
                arr5 = new String[]{"0468-2227073", "0468-2222364"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"0468-325352", "1091"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Kottayam":
                arr = new String[]{"0481-5550400 ", "100"};
                arr2 = new String[]{"9846200100", "9497981116", "0481-2562628"};
                arr3 = new String[]{"0481-2597311", "9495443966", "9895795422"};
                arr4 = new String[]{"0481-2567444", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"0481-2561414", "1091"};
                arr5 = new String[]{"0481-2582460", "0481-2790025"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;


            case "Idukki":
                arr = new String[]{"04862-221100", "100"};
                arr2 = new String[]{"9846200100"};
                arr3 = new String[]{"04862-329400", "04862-232444"};
                arr4 = new String[]{"04868-272300", "101"};
                arr6 = new String[]{"1077", "1070"};
                arr7 = new String[]{"04862-236600", "1091"};
                arr5 = new String[]{"04862-232444", "04862-255466"};
                phoneNumbers.put("POLICE", arr);
                phoneNumbers.put("RAIL_POLICE", arr2);
                phoneNumbers.put("BLOOD_BANK", arr3);
                phoneNumbers.put("FIRE", arr4);
                phoneNumbers.put("HOSPITAL", arr5);
                phoneNumbers.put("DISASTER", arr6);
                phoneNumbers.put("WOMEN", arr7);
                return phoneNumbers;

        }

        return null;
    }


}
