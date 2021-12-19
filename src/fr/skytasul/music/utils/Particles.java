// 
// Decompiled by Procyon v0.5.36
// 

package fr.skytasul.music.utils;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import java.util.Random;

public class Particles
{
    private static Random ran;
    
    static {
        Particles.ran = new Random();
    }
    
    public static void sendParticles(final Player p) {
        p.spawnParticle(Particle.NOTE, p.getEyeLocation().add(p.getLocation().getDirection().multiply(2)), 1, (double)(Particles.ran.nextInt(24) / 24), 0.5, 0.1, 1.0, (Object)null);
    }
}
