package studio.magemonkey.genesis.pointsystem;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class GenesisPointsPluginFailed extends GenesisPointsPlugin {

    public GenesisPointsPluginFailed() {
        super("Failed");
    }

    public double getPoints(OfflinePlayer player) {
        informPlayer(player);
        return -1;
    }

    public double setPoints(OfflinePlayer player, double points) {
        informPlayer(player);
        return -1;
    }

    public double takePoints(OfflinePlayer player, double points) {
        informPlayer(player);
        return -1;
    }

    public double givePoints(OfflinePlayer player, double points) {
        informPlayer(player);
        return -1;
    }

    private void informPlayer(OfflinePlayer player) { //Not sending to prevent spam on servers that installed Genesis for the first time. It should be enough when Genesis notifies about this on startup.
        Bukkit.getConsoleSender()
                .sendMessage("PlayerPoints/CommandPoints was not found... "
                        + "You need one of that plugins if you want to work with Points! " + "Get PlayerPoints there: "
                        + "http://dev.bukkit.org/server-mods/playerpoints/");
        if (player.isOnline())
            if (player.isOp()) {
                player.getPlayer()
                        .sendMessage(
                                "[Genesis] No Points Plugin installed. If you want to work with Points please install one.");
            } else {
                player.getPlayer()
                        .sendMessage("[Genesis] No Points Plugin installed. Please inform an administrator.");
            }

    }

    @Override
    public boolean usesDoubleValues() {
        return false;
    }
}
