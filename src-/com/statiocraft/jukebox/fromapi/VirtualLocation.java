package com.statiocraft.jukebox.fromapi;

import java.io.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class VirtualLocation implements Serializable {

    private final String w;
    private final double x;
    private final double y;
    private final double z;
    private final float Y;
    private final float p;

    public VirtualLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.w = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.Y = yaw;
        this.p = pitch;
    }

    public VirtualLocation(String world, double x, double y, double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public VirtualLocation(String world, int x, int y, int z, float yaw, float pitch) {
        this(world, (double) x, (double) y, (double) z, yaw, pitch);
    }

    public VirtualLocation(String world, int x, int y, int z) {
        this(world, (double) x, (double) y, (double) z);
    }

    public VirtualLocation(World world, double x, double y, double z, float yaw, float pitch) {
        this(world.getName(), x, y, z, yaw, pitch);
    }

    public VirtualLocation(World world, double x, double y, double z) {
        this(world.getName(), x, y, z);
    }

    public VirtualLocation(World world, int x, int y, int z, float yaw, float pitch) {
        this(world.getName(), x, y, z, yaw, pitch);
    }

    public VirtualLocation(World world, int x, int y, int z) {
        this(world.getName(), x, y, z);
    }

    public VirtualLocation(Location location) {
        this(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public boolean teleport(Player player) {
        try {
            player.teleport(this.getBukkitLocation());
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public String getWorld() {
        return this.w;
    }

    public World getBukkitWorld() {
        return Bukkit.getWorld(this.w);
    }

    public double getX() {
        return this.x;
    }

    public int getBlockX() {
        return Double.valueOf(this.x).intValue();
    }

    public double getY() {
        return this.y;
    }

    public int getBlockY() {
        return Double.valueOf(this.y).intValue();
    }

    public double getZ() {
        return this.z;
    }

    public int getBlockZ() {
        return Double.valueOf(this.z).intValue();
    }

    public float getYaw() {
        return this.Y;
    }

    public float getPitch() {
        return this.p;
    }

    public Location getBukkitLocation() {
        return new Location(this.getBukkitWorld(), this.x, this.y, this.z, this.Y, this.p);
    }
}
