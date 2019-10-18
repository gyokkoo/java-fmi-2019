public class FunnelChecker {

    public static boolean isFunnel(String str1, String str2) {

        for (int i = 0; i < str1.length(); i++) {

            String current = str1.substring(0, i) + str1.substring(i + 1);
            if (current.equals(str2)) {
                return true;
            }
        }

        return false;
    }
}
