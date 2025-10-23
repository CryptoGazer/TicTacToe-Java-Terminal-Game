public class StringFunc {

    public static String customStringsTrim(String stringToTrim, String stringToRemove) {
        /*
         * here stringToRemove.length() is the "beginIndex",
         * so we get a string, that starts right from the letter,
         * which had standed at the position of this index
         */

        String returnString = stringToTrim;
        int rLength = stringToRemove.length();

        while(returnString.startsWith(stringToRemove)) {
            returnString = returnString.substring(rLength);
        }

        while(returnString.endsWith(stringToRemove)) {
            returnString = returnString.substring(0, returnString.length() - rLength);
        }
        return returnString;
    }

    public static String customStringsTrim(String stringToTrim, String stringToRemoveLeft, String stringToRemoveRight) {
        String returnString = stringToTrim;

        while(returnString.startsWith(stringToRemoveLeft)) {
            returnString = returnString.substring(stringToRemoveLeft.length());
        }

        while(returnString.endsWith(stringToRemoveRight)) {
            returnString = returnString.substring(0, returnString.length() - stringToRemoveRight.length());
        }
        return returnString;
    }

    public static String customStringsTrim(String stringToTrim, char charToRemove) {
        /*
         * here stringToRemove.length() is the "beginIndex",
         * so we get a string, that starts right from the letter,
         * which had standed at the position of this index
         */
        String returnString = stringToTrim;
        String charAsStringToRemove = String.valueOf(charToRemove);

        while(returnString.startsWith(charAsStringToRemove)) {
            returnString = returnString.substring(1);
        }

        while(returnString.endsWith(charAsStringToRemove)) {
            returnString = returnString.substring(0, returnString.length() - 1);
        }
        return returnString;
    }

    public static String addStrings(String stringInit, String stringToAdd) {
        return stringToAdd + stringInit + stringToAdd;
    }

    public static String addStrings(String stringInit, String stringToAddLeft, String stringToAddRight) {
        return stringToAddLeft + stringInit + stringToAddRight;
    }

    public static String addStrings(String stringInit, String stringToAdd, int repeat) {
        String returnString = stringInit;
        for(int i=0; i<repeat; i++) {
            returnString = stringToAdd + returnString + stringToAdd;
        }
        return returnString;
    }

    public static String addStrings(String stringInit, String stringToAddLeft, String stringToAddRight, int repeat) {
        String returnString = stringInit;
        for(int i=0; i<repeat; i++) {
            returnString = stringToAddLeft + returnString + stringToAddRight;
        }
        return returnString;
    }

    public static String addStrings(String stringInit, String stringToAddLeft, String stringToAddRight, int repeatLeft, int repeatRight) {
        String returnString = stringInit;
        for(int i=0; i<repeatLeft; i++) {
            returnString = stringToAddLeft + returnString;
        }

        for(int i=0; i<repeatRight; i++) {
            returnString = returnString + stringToAddRight;
        }
        return returnString;
    }
}
