package com.satoshicraft.bottle;

import java.util.HashMap;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.satoshicraft.bottle.commands.XpBottle;
import com.satoshicraft.bottle.listener.XpBottleThrow;

public class Main
  extends JavaPlugin
  implements Listener
{
  public HashMap<String, Integer> xpitemlore;
  public XpLevel xpl = new XpLevel();
  public XpItem xpBottle = new XpItem(this);
  private YmlMaker cfg;
  
  public void onEnable()
  {
    this.cfg = new YmlMaker(this, "config.yml");
    this.cfg.saveDefaultConfig();
    onRegisterCommands();
    onRegisterEvents();
    this.xpitemlore = new HashMap<String, Integer>();
    getServer().getPluginManager().registerEvents(this, this);
    double dropPercentage = this.cfg.getConfig().getDouble("DropPercentage");
    if ((dropPercentage <= 0.0D) || (dropPercentage > 100.0D))
    {
      this.cfg.getConfig().set("DropPercentage", Integer.valueOf(100));
      this.cfg.saveConfig();
      this.cfg.reloadConfig();
    }
  }
  
  public void onRegisterCommands()
  {
    getCommand("satoshixp").setExecutor(new XpBottle(this));
  }
  
  public void onRegisterEvents()
  {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new XpBottleThrow(this), this);
  }
  
  public void onDisable() {}
  
  public YmlMaker getCfg()
  {
    return this.cfg;
  }
}
