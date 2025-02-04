package studio.magemonkey.genesis.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import studio.magemonkey.genesis.Genesis;
import studio.magemonkey.genesis.core.GenesisShop;
import studio.magemonkey.genesis.managers.ClassManager;
import studio.magemonkey.genesis.managers.features.PlayerDataHandler;
import studio.magemonkey.genesis.misc.userinput.GenesisChatUserInput;

public class PlayerListener implements Listener {


    private final Genesis plugin;

    public PlayerListener(Genesis plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void shopCommand(PlayerCommandPreprocessEvent e) {
        if (!e.isCancelled()) {
            if (plugin.getClassManager().getSettings().getShopCommandsEnabled()) {
                Player p   = e.getPlayer();
                String cmd = e.getMessage().substring(1);

                if (plugin.getClassManager().getShops() != null) {
                    GenesisShop shop = plugin.getClassManager().getShops().getShopByCommand(cmd);
                    if (shop != null) {

                        if (p.hasPermission("Genesis.open") || p.hasPermission("Genesis.open.command")
                                || p.hasPermission("Genesis.open.command." + shop.getShopName())) {
                            ClassManager.manager.getShops().openShop(p, shop);
                        } else {
                            ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", p);
                        }
                        //p.performCommand("genesis "+shop.getShopName());

                        e.setCancelled(true);
                    }
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.LOW)
    public void chat(AsyncPlayerChatEvent e) {
        if (ClassManager.manager.getPlayerDataHandler() != null) {
            PlayerDataHandler h = ClassManager.manager.getPlayerDataHandler();

            GenesisChatUserInput i = h.getInputRequest(e.getPlayer());
            if (i != null) {
                if (i.isUpToDate()) {
                    i.input(e.getPlayer(), e.getMessage());
                    e.setCancelled(true);
                }
                h.removeInputRequest(e.getPlayer());
            }
        }
    }


    @EventHandler
    public void quitServer(PlayerQuitEvent event) {
        leave(event.getPlayer());
    }

    @EventHandler
    public void kickedOffServer(PlayerKickEvent event) {
        leave(event.getPlayer());
    }

    public void leave(Player p) {
        if (ClassManager.manager.getPlayerDataHandler() != null) {
            ClassManager.manager.getPlayerDataHandler().leftServer(p);
        }
    }

}
