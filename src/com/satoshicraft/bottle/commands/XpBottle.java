package com.satoshicraft.bottle.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.satoshicraft.bottle.Main;
import com.satoshicraft.bottle.NumberCheck;
import com.satoshicraft.bottle.XpLevel;

public class XpBottle
  implements CommandExecutor
{
  private Main pl;
  String message;
  List<String> messagel;
  
  public XpBottle(Main plugin)
  {
    this.pl = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((!(sender instanceof Player)) && 
      (args.length != 2))
    {
      sender.sendMessage("�cTo give xp Bottle to player use /xpb <amount> <playername>");
      return true;
    }
    Player p = null;
    if ((sender instanceof Player)) {
      p = (Player)sender;
    }
    if (sender.hasPermission("satoshicraft.bottle"))
    {
      if ((args.length == 0) && 
        (p.hasPermission("satoshicraft.bottle")))
      {
        this.messagel = this.pl.getCfg().getConfig().getStringList("Messages.Xpb");
        for (String s : this.messagel)
        {
          s = ChatColor.translateAlternateColorCodes('&', s);
          p.sendMessage(s);
        }
        return true;
      }
      String xpargs0 = args[0].replaceAll("\\-", "");
      if (NumberCheck.isInt(xpargs0, p))
      {
        int takenXp = Integer.parseInt(xpargs0);
        if ((args.length == 1) && 
          (p.hasPermission("satoshicraft.bottle")))
        {
          if (p.getInventory().firstEmpty() == -1)
          {
            this.message = this.pl.getCfg().getConfig().getString("Messages.FullInventory");
            this.message = ChatColor.translateAlternateColorCodes('&', this.message);
            p.sendMessage(this.message);
            return true;
          }
          int MinXp = this.pl.getCfg().getConfig().getInt("MinXp");
          if (takenXp < MinXp)
          {
            this.message = this.pl.getCfg().getConfig().getString("Messages.MinXpMessage");
            this.message = this.message.replaceAll("%minxp%", String.valueOf(MinXp));
            this.message = ChatColor.translateAlternateColorCodes('&', this.message);
            p.sendMessage(this.message);
            return true;
          }
          int MaxXp = this.pl.getCfg().getConfig().getInt("MaxXp");
          if (takenXp > MaxXp)
          {
            this.message = this.pl.getCfg().getConfig().getString("Messages.MaxXpMessage");
            this.message = this.message.replaceAll("%maxxp%", String.valueOf(MaxXp));
            this.message = ChatColor.translateAlternateColorCodes('&', this.message);
            p.sendMessage(this.message);
            return true;
          }
          int xp = XpLevel.getXp(p);
          if (xp >= takenXp)
          {
            xp -= takenXp;
            this.pl.xpBottle.setXpb(takenXp, p);
            p.setTotalExperience(0);
            p.setLevel(0);
            p.setExp(0.0F);
            p.giveExp(xp);
            ItemStack Xpb = this.pl.xpBottle.getXpb();
            p.getInventory().addItem(new ItemStack[] { Xpb });
            this.messagel = this.pl.getCfg().getConfig().getStringList("Messages.XpTransferred");
            for (String s : this.messagel)
            {
              s = ChatColor.translateAlternateColorCodes('&', s);
              s = s.replaceAll("%takenxp%", String.valueOf(takenXp));
              p.sendMessage(s);
            }
            return true;
          }
          this.message = this.pl.getCfg().getConfig().getString("Messages.NotEnough");
          this.message = this.message.replaceAll("%pxp%", String.valueOf(xp));
          this.message = ChatColor.translateAlternateColorCodes('&', this.message);
          p.sendMessage(this.message);
          return true;
        }
        if (sender.hasPermission("satoshicraft.bottleadmin"))
        {
          if (args.length == 2)
          {
            Player target = Bukkit.getServer().getPlayer(args[1]);
            if (target == null)
            {
              sender.sendMessage("�cCan t find player " + args[0] + "!");
              this.message = this.pl.getCfg().getConfig().getString("Messages.CantFind");
              this.message = this.message.replaceAll("%target%", args[0]);
              this.message = ChatColor.translateAlternateColorCodes('&', this.message);
              p.sendMessage(this.message);
              return true;
            }
            this.pl.xpBottle.setXpbServer(takenXp);
            ItemStack Xpb = this.pl.xpBottle.getXpb();
            target.getInventory().addItem(new ItemStack[] { Xpb });
            this.message = this.pl.getCfg().getConfig().getString("Messages.BottleRecive");
            this.message = this.message.replaceAll("%xp%", String.valueOf(takenXp));
            this.message = ChatColor.translateAlternateColorCodes('&', this.message);
            sender.sendMessage(this.message);
            return true;
          }
        }
        else
        {
          this.message = this.pl.getCfg().getConfig().getString("Messages.NoPermission");
          this.message = ChatColor.translateAlternateColorCodes('&', this.message);
          p.sendMessage(this.message);
          return true;
        }
      }
      else
      {
        this.message = this.pl.getCfg().getConfig().getString("Messages.NoNumber");
        this.message = ChatColor.translateAlternateColorCodes('&', this.message);
        p.sendMessage(this.message);
        return true;
      }
    }
    else
    {
      this.message = this.pl.getCfg().getConfig().getString("Messages.NoPermission");
      this.message = ChatColor.translateAlternateColorCodes('&', this.message);
      p.sendMessage(this.message);
      return true;
    }
    return false;
  }
}
