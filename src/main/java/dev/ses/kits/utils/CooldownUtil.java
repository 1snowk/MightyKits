package dev.ses.kits.utils;

import com.google.common.collect.HashBasedTable;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class CooldownUtil {

    private final HashBasedTable<UUID, String, Long> cooldowns = HashBasedTable.create();

    public boolean isInCooldown(Player player, String cooldownName){
        return cooldowns.contains(player.getUniqueId(), cooldownName) && cooldowns.get(player.getUniqueId(), cooldownName) > System.currentTimeMillis();
    }

    public void addCooldown(Player player, String cooldownName, long time){
        if (time == 0L){
            cooldowns.remove(player.getUniqueId(), cooldownName);
            return;
        }

        if (time < 0L){
            cooldowns.remove(player.getUniqueId(), cooldownName);
            return;
        }

        cooldowns.put(player.getUniqueId(), cooldownName, time + System.currentTimeMillis());
    }


    public String getCooldown(Player player , String cooldownName){
        long timeLeft = cooldowns.get(player.getUniqueId(), cooldownName) - System.currentTimeMillis();
        return StringUtil.format(timeLeft);
    }





}
