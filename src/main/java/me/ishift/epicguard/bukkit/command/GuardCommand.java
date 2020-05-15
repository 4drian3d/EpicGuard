/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.epicguard.bukkit.command;

import lombok.AllArgsConstructor;
import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class GuardCommand implements CommandExecutor {
    private final AttackManager manager;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] args) {
        final String version = EpicGuardBukkit.getInstance().getDescription().getVersion();
        if (sender instanceof Player && !sender.hasPermission("epicguard.admin")) {
            send(sender, "&7This server uses &6EpicGuard v" + version + " &7by &ciShift and rusekh&7.");
            send(sender, Messages.noPermission);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageHelper.color("&8&m---------------------------------------------------"));
            sender.sendMessage(MessageHelper.color("  &6&lEpicGuard &8(Spigot version)"));
            sender.sendMessage(MessageHelper.color(""));
            sender.sendMessage(MessageHelper.color("&8» &7Running version &f" + version));
            sender.sendMessage(MessageHelper.color("&8» &7Created by &fiShift and rusekh &8© 2020"));
            sender.sendMessage(MessageHelper.color(""));
            sender.sendMessage(MessageHelper.color("&8» &7/guard &fmenu &7to open main plugin GUI."));
            sender.sendMessage(MessageHelper.color("&8» &7/guard &fstatus &7to toggle antibot notifications."));
            sender.sendMessage(MessageHelper.color("&8» &7/guard &freload &7to reload configuration and messages."));
            sender.sendMessage(MessageHelper.color("&8» &7/guard &fplayer <player> &7to see information about specific player."));
            sender.sendMessage(MessageHelper.color("&8» &7/guard &fwhitelist <adress> &7to add specific adress to the whitelist."));
            sender.sendMessage(MessageHelper.color("&8» &7/guard &fblacklist <adress> &7to add specific adress to the blacklist."));
            sender.sendMessage(MessageHelper.color("&8&m---------------------------------------------------"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "menu":
                if (!(sender instanceof Player)) {
                    send(sender, Messages.playerOnly);
                    return true;
                }

                EpicGuardBukkit.getInstance().getInventoryManager().open("MAIN", (Player) sender);
                break;
            case "status": {
                if (!(sender instanceof Player)) {
                    send(sender, Messages.playerOnly);
                    return true;
                }

                final User user = new User(sender.getName(), this.manager);
                send(sender, (user.isNotifications() ? Messages.statusOff : Messages.statusOn));
                user.setNotifications(!user.isNotifications());
                break;
            }
            case "reload":
                Configuration.load();
                SpigotSettings.load();
                Messages.load();
                send(sender, Messages.configReload);
                break;
            case "player": {
                if (args.length != 2) {
                    send(sender, Messages.usage.replace("{USAGE}", s + " player <player>"));
                    return true;
                }

                final User user = new User(args[1], this.manager);
                if (!user.isExisting()) {
                    send(sender, Messages.playerNotFound);
                    return true;
                }

                sendNoPrefix(sender, "&8&m---------------------------------------------------");
                sendNoPrefix(sender, "&8» &7Viewing data of " + (user.getPlayer() != null ? "&aonline" : "&coffline") + " &7user: &6" + args[1]);
                sendNoPrefix(sender, "");
                sendNoPrefix(sender, "&8» &7Name: &f" + user.getName());
                sendNoPrefix(sender, "&8» &7UUID: &f" + user.getUuid());
                sendNoPrefix(sender, "&8» &7Address: &f" + user.getAddress());
                sendNoPrefix(sender, "&8» &7Country: &f" + user.getCountry());
                sendNoPrefix(sender, "&8» &7City: &f" + user.getCity());
                sendNoPrefix(sender, "&8» &7OP: " + (user.getPlayer().isOp() ? "&aYes" : "&cNo"));
                if (!user.getAddressHistory().isEmpty()) {
                    sendNoPrefix(sender, " ");
                    sendNoPrefix(sender, "&8» &7IP History:");
                    for (String address : user.getAddressHistory()) {
                        sendNoPrefix(sender, "  &7- &f" + address + (user.getAddress().equals(address) ? " &8(&6Current&8)" : ""));
                    }
                }
                sendNoPrefix(sender, "&8&m---------------------------------------------------");
                break;
            }
            case "whitelist": {
                if (args.length != 2) {
                    send(sender, Messages.usage.replace("{USAGE}", s + " whitelist <address>"));
                    return true;
                }

                final String address = args[1];
                StorageManager.getStorage().whitelist(address);
                send(sender, Messages.whitelisted.replace("{ADDRESS}", address));
                break;
            }
            case "blacklist": {
                if (args.length != 2) {
                    send(sender, Messages.usage.replace("{USAGE}", s + " blacklist <address>"));
                    return true;
                }

                final String address = args[1];
                StorageManager.getStorage().blacklist(address);
                send(sender, Messages.blacklisted.replace("{ADDRESS}", address));
                break;
            }
            default:
                send(sender, Messages.unknownCommand);
                break;
        }
        return true;
    }

    private void send(CommandSender sender, String message) {
        sender.sendMessage(MessageHelper.color(Messages.prefix + message));
    }

    private void sendNoPrefix(CommandSender sender, String message) {
        sender.sendMessage(MessageHelper.color(message));
    }
}
