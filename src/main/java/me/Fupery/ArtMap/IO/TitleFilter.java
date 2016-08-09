package me.Fupery.ArtMap.IO;

import me.Fupery.ArtMap.ArtMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleFilter {

    private final String title;
    private final char[] chars;
    private final String adjTitle;

    public TitleFilter(String title) {
        this.title = title;
        chars = title.toCharArray();
        adjTitle = replaceCharacters();
    }

    public boolean check() {

        ArtMap plugin = ArtMap.plugin();

        if (containsIllegalCharacters(title)) return false;

        if (!title.matches("[\\w]{3,16}")) {
            return false;
        }

        if (!plugin.getConfig().getString("language").equalsIgnoreCase("english")) return true;

        for (String reject : plugin.getTitleFilter()) {

            if (plugin.getConfig().getBoolean("swearFilter")) {

                if (title.toLowerCase().contains(reject)
                        || adjTitle.contains(reject)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean containsIllegalCharacters(String toExamine) {
        Pattern pattern = Pattern.compile("[!@#$%^&*()-/\\\\;:.,<>~`?\"']");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    //removes repeat characters & underscores
    private String replaceCharacters() {

        String adjString = "";
        char repeatChar = '$';
        char currentChar;

        for (char aChar : chars) {

            if (aChar == '1') {
                currentChar = 'i';

            } else if (aChar == '3') {
                currentChar = 'e';

            } else if (aChar == '0') {
                currentChar = 'o';

            } else if (aChar == '5') {
                currentChar = 's';

            } else if (Character.toLowerCase(aChar) == 'z') {
                currentChar = 's';

            } else if (aChar == '_') {
                continue;

            } else {
                currentChar = Character.toLowerCase(aChar);
            }

            if (repeatChar != currentChar) {
                repeatChar = currentChar;
                adjString += currentChar;
            }
        }
        return adjString;
    }
}
