package ru.pr0stik;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin implements Listener
{
	
	private static MainClass instance;
	FileConfiguration config = getConfig();
	
	public static MainClass instance(){
		return instance;
	}
	
	boolean allowfly = false;
	boolean allowgod = false;
	
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
	}
	
	public void onDisable(){
		
	}
	
	public boolean checkSender(CommandSender sender){
		if(sender instanceof Player){
			return false;
		}
		return true;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(!(sender instanceof Player)){
			sender.sendMessage("This plugin is a player only!");
		}
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("heal")){
			p.setHealth(20);
			p.sendMessage(ChatColor.ITALIC + "Ваше здоровье было успешно восстановлено!");
		}
			if(cmd.getName().equalsIgnoreCase("food")){
			p.setFoodLevel(20);
			p.sendMessage(ChatColor.ITALIC + "Ваша еда была успешно восстановлена!");
		}
			if(cmd.getName().equalsIgnoreCase("tp")){
				if(args.length == 0){
					p.sendMessage(ChatColor.RED + "Please specify a player");
				}
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if(target == null){
					p.sendMessage(ChatColor.RED + "Could not find player " + args[0] + "!");
				}
			p.teleport(target.getLocation());
			}
			
			if(cmd.getName().equalsIgnoreCase("fly")){
			
			allowfly = !allowfly;
			
			if(allowfly) p.sendMessage(ChatColor.GREEN + "Вы включили /fly");
			else{
				p.sendMessage(ChatColor.RED + "Вы выключили /fly");
			}
			p.setAllowFlight(!p.getAllowFlight());
			
			if(cmd.getName().equalsIgnoreCase("savehome")){
				if(checkSender(sender)) {
					sender.sendMessage("Only for player!");	
				return true;
			}
			
			
			SetLocation(p.getName(), p.getLocation());
			p.sendMessage(ChatColor.GREEN + "Дом установлен!");
			
			}
			if(cmd.getName().equalsIgnoreCase("tphome")){
				if(checkSender(sender)) {
					sender.sendMessage("Only for player!");
					return true;
			}
				
				Location home = GetLocation(p.getName());
				p.teleport(home);
				p.sendMessage(ChatColor.GREEN + "Телепорт домой!");
				
				return true;
			
			}
			}
			return false;
	}
		
			public void SetLocation(String name, Location loc){
			config.set("locations." + name + ".world", loc.getWorld().getName());
			config.set("locations." + name + ".x", loc.getBlockX());
			config.set("locations." + name + ".y", loc.getBlockY());
			config.set("locations." + name + ".z", loc.getBlockZ());
			saveConfig();
		}
		
		public Location GetLocation(String name){		
			Location loc = new Location(Bukkit.getServer().getWorld(config.getString("locations." + name + ".world")),
					config.getInt("location." + name + ".x"),
					config.getInt("location." + name + ".y"),
					config.getInt("location." + name + ".z"));
			return loc;
	
		}

	
}
