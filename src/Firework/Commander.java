package Firework;


import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDateTime;


public class Commander implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        if(commandSender instanceof Player){
            if(args[0].equals("help")){
                LocalDateTime time= LocalDateTime.now();
                commandSender.sendMessage("====================");
                commandSender.sendMessage("/fws on  | 开始选择烟花区");
                commandSender.sendMessage("/fws off | 关闭选择烟花区");
                commandSender.sendMessage("/fws create [持续时间(秒)] [数量] [闪烁(t/f)] | 在选择的两点区域之间创建烟花区");
                commandSender.sendMessage("/fws nows [边长] [持续时间(秒)] [数量] [闪烁(t/f)]| 在自身为中点的边长内创建烟花区");
                commandSender.sendMessage("/fws nowc [半径] [持续时间(秒)] [数量] [闪烁(t/f)]| 在自身为原点的半径内创建烟花区");
                commandSender.sendMessage("/fws locatec [x] [y] [z] [半径] [持续时间(秒)] [数量] [闪烁(t/f)]| 在此点半径内创建烟花区");
                commandSender.sendMessage("/fws locates [x] [y] [z] [边长] [持续时间(秒)] [数量] [闪烁(t/f)]| 在自身为中点的边长内创建烟花区");
                commandSender.sendMessage("/fws stop | 取消自身设定的烟花区");
                commandSender.sendMessage("====================");
                return false;
            }
            try{
                switch (args[0]) {
                    case "on":
                        if (!Main.User.contains((Player) commandSender)) {
                            if(Main.task.containsKey((Player) commandSender)){
                                commandSender.sendMessage("请勿重复启动新烟花区");
                                return false;
                            }
                            Main.User.add((Player) commandSender);
                            commandSender.sendMessage("已进入选择,请左右点击选择两点");
                        } else {
                            commandSender.sendMessage("你已处于选择模式了");
                        }
                        break;
                    case "off":
                        if (Main.User.contains((Player) commandSender)) {
                            Main.User.remove((Player) commandSender);
                            Main.map.remove((Player) commandSender);
                            commandSender.sendMessage("已退出选择");
                        } else {
                            commandSender.sendMessage("你未处于选择模式");
                        }
                        break;
                    case "create":
                        if(Main.User.contains((Player) commandSender)&&Main.map.containsKey((Player)commandSender)){
                            if(Main.map.get((Player) commandSender).done()){
                                if(args.length>=3){
                                    int time=0;
                                    int amount=1;
                                    boolean flicker=false;
                                    time = Integer.parseInt(args[1]);
                                    amount =Integer.parseInt(args[2]);
                                    if(args.length>=4){
                                        flicker=args[3].charAt(0)=='t';
                                    }
                                    Fireworks.start((Player) commandSender,Main.map.get((Player) commandSender),time*20,amount,flicker);
                                    Main.User.remove((Player) commandSender);

                                }else{
                                    commandSender.sendMessage("/fws create [持续时间(秒)] [数量] [闪烁(t/f)] | 在选择的两点区域之间创建烟花区");
                                }
                            }else{
                                commandSender.sendMessage("区域选择未完善,请确定选择好了同世界的两点及高度");
                            }
                        }else{
                            commandSender.sendMessage("你未处于选择模式,无法创建烟花区");
                        }
                        break;
                    case "Y":
                        if(Main.User.contains((Player) commandSender)){
                            if(Main.map.containsKey((Player)commandSender)&&args.length==2){
                                Main.map.get((Player) commandSender).setY(Integer.parseInt(args[1]));
                                commandSender.sendMessage("Y已确认为："+Integer.parseInt(args[1]));
                                commandSender.sendMessage("/fws create [持续时间(秒)] [数量] [闪烁(t/f)]");
                            }
                            else{
                                Main.map.get((Player) commandSender).setY((int)((Player) commandSender).getLocation().getY());
                                commandSender.sendMessage("Y已确认为："+(int)((Player) commandSender).getLocation().getY());
                                commandSender.sendMessage("/fws create [持续时间(秒)] [数量] [闪烁(t/f)]");
                            }
                        }else{
                            return false;
                        }

                        break;
                    case "nows":
                        if (args.length >= 3) {
                            Location location = ((Player) commandSender).getLocation();
                            double length = Double.parseDouble(args[1]);
                            if(length<0){
                                commandSender.sendMessage("边长必须>0");
                            }
                            Location loc1=location.clone();
                            Location loc2=location.clone();
                            int time = Integer.parseInt(args[2]);
                            int amount = 1;
                            boolean flicker=false;
                            if (args.length >= 4) {
                                amount = Integer.parseInt(args[3]);
                                if(args.length>=5){
                                    flicker= args[4].charAt(0)=='t';
                                }
                            }
                            if(Main.task.containsKey((Player) commandSender)){
                                commandSender.sendMessage("请勿重复启动新烟花区");
                                return true;
                            }
                            loc1.add(length/2,0,length/2);
                            loc2.subtract(length/2,0,length/2);
                            Fireworks.start((Player)commandSender,loc1, loc2, time*20, amount,flicker);
                        } else {
                            commandSender.sendMessage("/fws nows [边长] [持续时间(秒)] [数量] [闪烁(t/f)]| 在自身为中点的边长内创建烟花区");
                            return true;
                        }
                        break;
                    case "nowc":
                        if (args.length >= 3) {
                            Location location = ((Player) commandSender).getLocation();
                            double radius = Double.parseDouble(args[1]);
                            int time = Integer.parseInt(args[2]);
                            int amount = 1;
                            boolean flicker=false;
                            if (args.length >= 4) {
                                amount = Integer.parseInt(args[3]);
                                if(args.length>=5){
                                    flicker= args[4].charAt(0)=='t';
                                }
                            }
                            if(Main.task.containsKey((Player) commandSender)){
                                commandSender.sendMessage("请勿重复启动新烟花区");
                                return true;
                            }
                            Fireworks.start((Player)commandSender,location, radius, time*20, amount,flicker);
                        } else {
                            commandSender.sendMessage("/fws nowc [半径] [持续时间(秒)] [数量] [闪烁(t/f)]| 在自身半径内创建烟花区");
                            return true;
                        }
                        break;
                    case "locates":
                        if (args.length >= 5) {
                            double x=Double.parseDouble(args[1]),y=Double.parseDouble(args[2]),z=Double.parseDouble(args[3]);
                            Location location = new Location(((Player)commandSender).getWorld() ,x,y,z);
                            if(y<-128){
                                commandSender.sendMessage("小于-128的位置无意义");
                                return true;
                            }
                            double length = Double.parseDouble(args[4]);
                            int time = Integer.parseInt(args[5]);
                            int amount = 1;
                            boolean flicker=true;
                            if (args.length >= 7) {
                                amount = Integer.parseInt(args[6]);
                                if(args.length>=8){
                                    flicker= args[7].charAt(0)=='t';
                                }
                            }
                            Location location1=location.clone().add(length,0,length);
                            Location location2=location.clone().subtract(length,0,length);
                            if(Main.task.containsKey((Player) commandSender)){
                                commandSender.sendMessage("请勿重复启动新烟花区");
                                return true;
                            }
                            Fireworks.start((Player)commandSender,location1, location2, time*20, amount,flicker);
                        } else {
                            commandSender.sendMessage("/fws locates [x] [y] [z] [边长] [持续时间(秒)] [数量] [闪烁(t/f)]| 在此中点内创建边长值的烟花区");
                            return true;
                        }
                        break;
                    case "locatec":
                        if (args.length >= 5) {
                            double x=Double.parseDouble(args[1]),y=Double.parseDouble(args[2]),z=Double.parseDouble(args[3]);
                            Location location = new Location(((Player)commandSender).getWorld() ,x,y,z);
                            if(y<-128){
                                commandSender.sendMessage("小于-128的位置无意义");
                                return true;
                            }
                            double radius = Double.parseDouble(args[4]);
                            int time = Integer.parseInt(args[5]);
                            int amount = 1;
                            boolean flicker=true;
                            if (args.length >= 7) {
                                amount = Integer.parseInt(args[6]);
                                if(args.length>=8){
                                    flicker= args[7].charAt(0)=='t';
                                }
                            }
                            if(Main.task.containsKey((Player) commandSender)){
                                commandSender.sendMessage("请勿重复启动新烟花区");
                                return true;
                            }
                            Fireworks.start((Player)commandSender,location, radius, time*20, amount,flicker);
                        } else {
                            commandSender.sendMessage("/fws locatec [x] [y] [z] [半径] [持续时间(秒)] [数量] [闪烁(t/f)]| 在此点半径内创建烟花区");
                            return true;
                        }
                        break;
                    case "stop":
                        if(Main.task.containsKey((Player) commandSender)){
                            Main.task.get((Player) commandSender).cancel();
                            Main.task.remove((Player) commandSender);
                            commandSender.sendMessage("已取消");
                        }else{
                            commandSender.sendMessage("无已启动的烟花区");
                        }
                        break;
                    default:
                        commandSender.sendMessage("用法错误!");
                        break;
                }
            }catch (NumberFormatException e){
                commandSender.sendMessage("数据格式输入错误,请仔细检查");
            }catch (ArrayIndexOutOfBoundsException e){
                commandSender.sendMessage("数组溢出了,大概是插件代码有错误,请联系插件作者");
            }
        }else{
            commandSender.sendMessage("控制台暂时无法使用");
        }
        return true;
    }
}
