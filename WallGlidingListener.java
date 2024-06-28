//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package WallGliding;

import com.projectkorra.projectkorra.BendingPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class WallGlidingListener implements Listener {
    public WallGlidingListener() {
    }

    @EventHandler
    public void OnSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer.getBoundAbilityName().equalsIgnoreCase("WallGliding")) {
            new WallGliding(player);
        }

    }
}
