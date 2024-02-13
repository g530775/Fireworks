package Firework;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Fireworks implements Listener {
    private static FireworkEffect.Type[] effects={FireworkEffect.Type.BALL,FireworkEffect.Type.BALL_LARGE,FireworkEffect.Type.BURST,FireworkEffect.Type.CREEPER,FireworkEffect.Type.STAR};
    private static Color[] colors={Color.AQUA,Color.BLACK,Color.BLUE,Color.FUCHSIA,Color.GRAY,Color.GREEN,Color.LIME,Color.MAROON,Color.NAVY,Color.OLIVE,Color.ORANGE,Color.PURPLE,Color.RED,Color.SILVER,Color.TEAL,Color.WHITE,Color.YELLOW};
    private static BukkitTask Task;
    //AQUA浅绿,BLACK黑色,BLUE蓝色,FUCHSIA樱红色,GRAY灰色,GREEN绿色,LIME黄绿色,MAROON栗色,NAVY海军色,OLIVE橄榄色,ORANGE橙色,PURPLE紫色,RED红色,SILVER银色,TEAL蓝绿色,WHITE白色,YELLOW黄色

    //BALL小型球状效果,BALL_LARGE大型球状效果,BURST爆裂效果,CREEPER苦力怕脸型效果,STAR星形效果

    private static Color RandomColor(){
        return colors[(int)(Math.random()*17)];
    }
    private static FireworkEffect.Type RandomType(){
        return effects[(int)(Math.random()*5)];
    }

    private static FireworkEffect getEffect(boolean flicker){
        int num =(int)(Math.random()*3+1);
        return switch (num) {
            case 1 -> FireworkEffect.builder()
                    .flicker(flicker)
                    .withColor(RandomColor())
                    .with(RandomType())
                    .trail(true)
                    .build();
            case 2 -> FireworkEffect.builder()
                    .flicker(flicker)
                    .withColor(RandomColor(), RandomColor())
                    .with(RandomType())
                    .trail(true)
                    .build();
            case 3 -> FireworkEffect.builder()
                    .flicker(flicker)
                    .withColor(RandomColor(), RandomColor(), RandomColor())
                    .with(RandomType())
                    .trail(true)
                    .build();
            default -> null;
        };
    }



    public static Location randomLocation(Location loc, double Radius){
        double x, z;
        do {
            x = Math.random() * 2 * Radius - Radius;
            z = Math.random() * 2 * Radius - Radius;
        } while (loc.distance(loc.clone().add(x, 0, z)) > Radius);
        return loc.clone().add(x, 0, z);
    }
    public static Location randomLocation(Locs locs){
        double Ax=locs.getA().getBlockX(),Az=locs.getA().getBlockZ(),Bx=locs.getB().getBlockX(),Bz=locs.getB().getBlockZ();
        double dX=Math.abs(Ax-Bx),dZ=Math.abs(Az-Bz);
        double mX=(Ax+Bx)/2,mZ=(Az+Bz)/2;
        double Y= locs.getY();
        double x=(Math.random()*2*dX-dX)/2, z=(Math.random()*2*dZ-dZ)/2;
        Location loc=locs.getA().clone();
        loc.setX(mX+x);
        loc.setY(Y+0.1);
        loc.setZ(mZ+z);
        return loc;
    }
    public static Location randomLocation(Location loc1,Location loc2){
        double Ax=loc1.getBlockX(),Az=loc1.getBlockZ(),Bx=loc2.getBlockX(),Bz=loc2.getBlockZ();
        double dX=Math.abs(Ax-Bx),dZ=Math.abs(Az-Bz);
        double mX=(Ax+Bx)/2,mZ=(Az+Bz)/2;
        double x=(Math.random()*2*dX-dX)/2, z=(Math.random()*2*dZ-dZ)/2;
        Location loc=loc1.clone();
        loc.setX(mX+x);
        loc.setY(loc1.getY()+0.1);
        loc.setZ(mZ+z);
        return loc;
    }

    public static void spawnFirework(Location location, double Radius,boolean flicker) {
        Location rl= randomLocation(location, Radius);
        if(rl!=null){
            Firework fw = (Firework) rl.getWorld().spawnEntity(rl, EntityType.FIREWORK);
            fw.addScoreboardTag("No_Damage");
            fw.setSilent(true);
            FireworkMeta fwm = fw.getFireworkMeta();
            fwm.addEffect(getEffect(flicker));
            fwm.setPower(1);
            fw.setFireworkMeta(fwm);
            fw.setVelocity(fw.getVelocity().setY(Math.random()*0.1+0.035));
        }
    }
    public static void spawnFirework(Locs locs,boolean flicker) {
        Location rl= randomLocation(locs);
        if(rl!=null){
            Firework fw = (Firework) rl.getWorld().spawnEntity(rl, EntityType.FIREWORK);
            fw.addScoreboardTag("No_Damage");
            fw.setSilent(true);
            FireworkMeta fwm = fw.getFireworkMeta();
            fwm.addEffect(getEffect(flicker));
            fwm.setPower(1);
            fw.setFireworkMeta(fwm);
            fw.setVelocity(fw.getVelocity().setY(Math.random()*0.1+0.035));
        }
    }
    public static void spawnFirework(Location loc1,Location loc2,boolean flicker) {
        Location rl= randomLocation(loc1,loc2);
        if(rl!=null){
            Firework fw = (Firework) rl.getWorld().spawnEntity(rl, EntityType.FIREWORK);
            fw.addScoreboardTag("No_Damage");
            fw.setSilent(true);
            FireworkMeta fwm = fw.getFireworkMeta();
            fwm.addEffect(getEffect(flicker));
            fwm.setPower(1);
            fw.setFireworkMeta(fwm);
            fw.setVelocity(fw.getVelocity().setY(Math.random()*0.1+0.035));
        }
    }

    @EventHandler
    public void NoDamage(EntityDamageByEntityEvent EDBEE){
        if(EDBEE.getCause()== EntityDamageEvent.DamageCause.ENTITY_EXPLOSION&&EDBEE.getDamager().getScoreboardTags().contains("No_Damage")){
            EDBEE.setCancelled(true);
        }
    }

    static public void start(Player p,Location location ,double radius,int time,int amount,boolean flicker){
        BukkitRunnable run=new BukkitRunnable() {
            final Location loc=location;
            int Time=time;
            int Amount=0;
            @Override
            public void run() {
                if(Time%20==0){
                    Amount=0;
                    do{
                        Fireworks.spawnFirework(loc,radius,flicker);
                    }while(++Amount< amount);
                }
                if(--Time<0){
                    p.sendMessage("烟花已结束(此消息仅你可见)");
                    Main.User.remove(p);
                    Main.map.remove(p);
                    Main.task.remove(p);
                    this.cancel();
                }
            }
        };
        Task= run.runTaskTimer(Main.getPlugin(Main.class),0,1);
        Main.task.put(p,Task);
    }
    static public void start(Player p,Locs locs,int time,int amount,boolean flicker){
        BukkitRunnable run=new BukkitRunnable() {
            int Time=time;
            int Amount=0;
            @Override
            public void run() {
                if(Time%20==0){
                    Amount=0;
                    do{
                        Fireworks.spawnFirework(locs,flicker);
                    }while(++Amount< amount);
                }
                if(--Time<0){
                    p.sendMessage("烟花已结束(此消息仅你可见)");
                    Main.User.remove(p);
                    Main.map.remove(p);
                    Main.task.remove(p);
                    this.cancel();
                }
            }
        };
        Task= run.runTaskTimer(Main.getPlugin(Main.class),0,1);
        Main.task.put(p,Task);
    }
    static public void start(Player p,Location loc1,Location loc2,int time,int amount,boolean flicker){
        BukkitRunnable run=new BukkitRunnable() {
            int Time=time;
            int Amount=0;
            @Override
            public void run() {
                if(Time%20==0){
                    Amount=0;
                    do{
                        Fireworks.spawnFirework(loc1,loc2,flicker);
                    }while(++Amount< amount);
                }
                if(--Time<0){
                    p.sendMessage("烟花已结束(此消息仅你可见)");
                    Main.User.remove(p);
                    Main.map.remove(p);
                    Main.task.remove(p);
                    this.cancel();
                }
            }
        };
        Task= run.runTaskTimer(Main.getPlugin(Main.class),0,1);
        Main.task.put(p,Task);
    }



}
