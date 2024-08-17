//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.wither.wallgliding;


import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WallGliding extends ChiAbility implements AddonAbility, PassiveAbility, Listener {
    private final Location location;
    private long cooldown;
    private boolean particles;
    //checks that the particles should be shown or not
    private int particles_amount;
    //checks the amount of particles
    private boolean sounds;
    //checks that sounds are Enabled or not
    private double volume;
    //Volume of the sounds
    private Permission perm;
    public void setFields() {
        Configuration config = ConfigManager.getConfig();
        this.cooldown = config.getLong("ExtraAbilities.Sc1_original.Passives.WallGliding.Cooldown");
        this.particles = config.getBoolean("ExtraAbilities.Sc1_original.Passives.WallGliding.Particles.Enabled");
        this.particles_amount = config.getInt("ExtraAbilities.Sc1_original.Passives.WallGliding.Particles.Amount");
        this.sounds = config.getBoolean("ExtraAbilities.Sc1_original.Passives.WallGliding.Sounds.Enabled");
        this.volume = config.getDouble("ExtraAbilities.Sc1_original.Passives.WallGliding.Sounds.Volume");
    }

    private boolean isAgainstWall() {
        Location location = this.player.getLocation();
        if (location.getBlock().getRelative(BlockFace.NORTH).getType().isSolid()) {
            return true;
        } else if (location.getBlock().getRelative(BlockFace.SOUTH).getType().isSolid()) {
            return true;
        } else {
            return location.getBlock().getRelative(BlockFace.WEST).getType().isSolid() || location.getBlock().getRelative(BlockFace.EAST).getType().isSolid();
        }
    }


    public WallGliding(Player player) {
        super(player);
        this.location = player.getLocation();
        this.bPlayer.addCooldown(this);
        this.setFields();
        this.start();
    }

    public boolean ReadytoDo() {
        return player.isSneaking() && isAgainstWall() && !player.isOnGround() && !player.getGameMode().equals(GameMode.SPECTATOR) && !bPlayer.isChiBlocked() && !bPlayer.isBloodbent() && !bPlayer.isControlledByMetalClips() && !bPlayer.isParalyzed() && bPlayer.isToggledPassives() && !player.isFlying();
    }


    public void progress() {

        if(ReadytoDo()){
            if (this.particles) {
                ParticleEffect.CRIT.display(this.player.getLocation(), particles_amount, Math.random(), -1.0, Math.random(), 0.0);
                ParticleEffect.BLOCK_CRACK.display(this.player.getLocation(), particles_amount, Math.random(), -1.0, Math.random(), 0.1, Material.STONE.createBlockData());
            }
            if(this.sounds) {
                player.playSound(this.player.getLocation(), Sound.BLOCK_ROOTED_DIRT_HIT, (float) volume, 1);
            }

            this.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 2, 10, false, false, false));

        }

        else {
            this.player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        }

    }

    public long getCooldown() {
        return this.cooldown;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getName() {
        return "WallGliding";
    }

    public boolean isHiddenAbility() {
        return false;
    }

    public boolean isHarmlessAbility() {
        return true;
    }

    public boolean isSneakAbility() {
        return true;
    }

    public String getAuthor() {
        return "Sc1_original";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public String getDescription() {
        return "WallGliding is an ability helping chi blockers to develop their agility, to use, hold sneak(shift) when you are connected to a wall.";
    }

    public void load() {
        this.perm = new Permission("bending.ability.wallgliding");
        this.perm.setDefault(PermissionDefault.OP);
        ProjectKorra.plugin.getServer().getPluginManager().addPermission(this.perm);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sc1_original.Passives.WallGliding.Cooldown", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sc1_original.Passives.WallGliding.Particles.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sc1_original.Passives.WallGliding.Particles.Amount", 2);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sc1_original.Passives.WallGliding.Sounds.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Sc1_original.Passives.WallGliding.Sounds.Volume", 1);
    }

    public void stop() {
        ProjectKorra.plugin.getServer().getPluginManager().removePermission(this.perm);
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isInstantiable() {
        return true;
    }

    public boolean isProgressable() {
        return true;
    }
}
