package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.gui.GuiPlayers;
import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GuiRefreshTask implements Runnable {
    private static int animation = 0;

    @Override
    public void run() {
        try {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getOpenInventory().getTitle().equals("EpicGuard Management Menu")) {
                    GuiMain.show(p);
                }
                if (p.getOpenInventory().getTitle().equals("EpicGuard Player Manager")) {
                    GuiPlayers.show(p);
                }
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
