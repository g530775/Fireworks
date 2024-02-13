package Firework;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class Locs{
    Location A=null,B=null;
    double Y=-129;

    public void setA(Location a) {
        A = a;
    }

    public void setB(Location b) {
        B = b;
    }

    public void setY(double y) {
        Y = y;
    }

    public Location getA() {
        return A;
    }

    public Location getB() {
        return B;
    }

    public double getY() {
        return Y;
    }

    public boolean done(){
        return A != null && B != null&&A.getWorld()==B.getWorld()&&Y>-128;
    }
    public boolean ZoneDone(){
        return A!=null&&B!=null&&A.getWorld()==B.getWorld();
    }
}

public class Selector implements Listener {
    @EventHandler
    public void ClickerListener(PlayerInteractEvent PIE){
        Player P=PIE.getPlayer();
        Block B=PIE.getClickedBlock();
        Action A=PIE.getAction();
        if(Main.User.contains(P)){
            PIE.setCancelled(true);
            if(!Main.map.containsKey(P)){
                Main.map.put(P,new Locs());
            }

            if(A==Action.LEFT_CLICK_BLOCK){
                P.sendMessage("§c你已选择点："+B.getLocation().getBlockX()+"/"+B.getLocation().getBlockY()+"/"+B.getLocation().getBlockZ());
                Main.map.get(P).setA(B.getLocation());
            }else if(A==Action.RIGHT_CLICK_BLOCK){
                P.sendMessage("§b你已选择点："+B.getLocation().getBlockX()+"/"+B.getLocation().getBlockY()+"/"+B.getLocation().getBlockZ());
                Main.map.get(P).setB(B.getLocation());
            }
            if(Main.map.get(P).ZoneDone()) {
                P.sendMessage("两点已选择,请选择烟花刷新的高度/fws Y [高度(若无输入则默认为你所站高度)]");
            }
        }

    }

    @EventHandler
    public void Quit(PlayerQuitEvent PQE){
        Player player=PQE.getPlayer();
        if(Main.User.contains(player)||Main.map.containsKey(player)){
            Main.User.remove(player);
            Main.map.remove(player);
        }
    }


}
