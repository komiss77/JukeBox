package com.statiocraft.jukebox.fromapi.util;

import java.util.Arrays;
import java.util.List;

public class StringUtil {

    public static String convertCodes(String string) {
        if (string == null) {
            return string;
        } else {
            int current = 0;
            char[] cs = string.toCharArray();
            StringBuilder sb = new StringBuilder();
            char[] achar = cs;
            int i = cs.length;

            for (int j = 0; j < i; ++j) {
                char c = achar[j];

                if (current <= cs.length && c == 38) {
                    char tc = Character.toLowerCase(cs[current + 1]);

                    if (tc != 49 && tc != 50 && tc != 51 && tc != 52 && tc != 53 && tc != 54 && tc != 55 && tc != 56 && tc != 57 && tc != 48 && tc != 97 && tc != 98 && tc != 99 && tc != 100 && tc != 101 && tc != 102 && tc != 107 && tc != 108 && tc != 109 && tc != 110 && tc != 111) {
                        sb.append(c);
                    } else {
                        sb.append("§");
                    }
                } else {
                    sb.append(c);
                }

                ++current;
            }

            return sb.toString();
        }
    }

    public static boolean isConfirming(String string) {
        return match(string, new String[] { "on", "true", "yes", "allow", "positive", "enable", "enabled", "confirm", "confirmed"});
    }

    public static boolean isRejecting(String string) {
        return match(string, new String[] { "off", "false", "no", "deny", "negative", "disable", "disabled", "reject", "rejected"});
    }

    public static int countMatches(String string, char c) {
        int n = 0;
        char[] achar;
        int i = (achar = string.toCharArray()).length;

        for (int j = 0; j < i; ++j) {
            char tc = achar[j];

            if (c == tc) {
                ++n;
            }
        }

        return n;
    }

    public static boolean match(String s, String... sa) {
        String[] astring = sa;
        int i = sa.length;

        for (int j = 0; j < i; ++j) {
            String st = astring[j];

            if (st.equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }

    public static boolean match(String s, List list) {
        String[] sa = new String[list.size()];

        for (int n = 0; n < sa.length; ++n) {
            sa[n] = (String) list.get(n);
        }

        return match(s, sa);
    }

    public static String[] split(String string, char c) {
        if (countMatches(string, c) <= 0) {
            return new String[] { string};
        } else {
            String s = string + c;
            String[] sa = new String[countMatches(string, c) + 1];
            StringBuilder sb = new StringBuilder();
            char[] ca = s.toCharArray();
            int n = 0;

            for (int x = 0; x < ca.length; ++x) {
                if (ca[x] == c) {
                    sa[n] = sb.toString();
                    sb = new StringBuilder();
                    ++n;
                } else {
                    sb.append(ca[x]);
                }
            }

            return sa;
        }
    }

    public static String toFormattedList(String[] strings, int startAt, String betweenWords) {
        return toFormattedList(Arrays.asList(strings), startAt, betweenWords);
    }

    public static String toFormattedList(List strings, int startAt, String betweenWords) {
        if (strings != null && startAt < strings.size()) {
            StringBuilder sb = new StringBuilder();

            for (int n = startAt; n < strings.size(); ++n) {
                if (sb.length() > 0) {
                    sb.append(betweenWords);
                }

                sb.append((String) strings.get(n));
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String limit(String string, int startAt, int limit) {
        try {
            return string.substring(startAt, startAt + limit);
        } catch (Exception exception) {
            return string;
        }
    }
}
