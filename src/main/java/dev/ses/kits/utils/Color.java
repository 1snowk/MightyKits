package dev.ses.kits.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Color {

    public String translate(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> translate(List<String> list){
        List<String> toReturn = new ArrayList<>();
        for (String texts : list){
            toReturn.add(translate(texts));
        }
        return toReturn;
    }

    public String[] translate(String[] texts) {
        String[] translatedTexts = new String[texts.length];
        for (int i = 0; i < texts.length; i++) {
            translatedTexts[i] = translate(texts[i]);
        }
        return translatedTexts;
    }

}
