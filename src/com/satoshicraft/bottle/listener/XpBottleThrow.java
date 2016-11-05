package com.satoshicraft.bottle.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.satoshicraft.bottle.Main;
import com.satoshicraft.bottle.XpLevel;

public class XpBottleThrow
  implements Listener
{
  private Main pl;
  
  public XpBottleThrow(Main plugin)
  {
    this.pl = plugin;
  }
  
  @SuppressWarnings("deprecation")
@EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent e)
  {
    if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && 
      (e.getPlayer().getItemInHand().getType().equals(Material.EXP_BOTTLE)))
    {
      Block block = e.getClickedBlock();
      if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (
        (block.getType() == Material.CHEST) || (block.getType() == Material.FURNACE) || 
        (block.getType() == Material.BURNING_FURNACE) || (block.getType() == Material.ENDER_CHEST) || 
        (block.getType() == Material.TRAPPED_CHEST) || (block.getType() == Material.ANVIL) || 
        (block.getType() == Material.BED_BLOCK) || (block.getType() == Material.ENCHANTMENT_TABLE) || 
        (block.getType() == Material.DISPENSER) || (block.getType() == Material.NOTE_BLOCK) || 
        (block.getType() == Material.LEVER) || (block.getType() == Material.DIODE_BLOCK_OFF) || 
        (block.getType() == Material.DIODE_BLOCK_ON) || (block.getType() == Material.SPRUCE_DOOR) || 
        (block.getType() == Material.BIRCH_DOOR) || (block.getType() == Material.JUNGLE_DOOR) || 
        (block.getType() == Material.ACACIA_FENCE_GATE) || (block.getType() == Material.DARK_OAK_FENCE_GATE) || 
        (block.getType() == Material.JUNGLE_FENCE_GATE) || (block.getType() == Material.BIRCH_FENCE_GATE) || 
        (block.getType() == Material.SPRUCE_FENCE_GATE) || (block.getType() == Material.WOOD_DOOR) || 
        (block.getType() == Material.IRON_DOOR_BLOCK) || (block.getType() == Material.DARK_OAK_DOOR) || 
        (block.getType() == Material.ACACIA_DOOR) || (block.getType() == Material.WORKBENCH) || 
        (block.getType() == Material.HOPPER) || (block.getType() == Material.DROPPER) || 
        (block.getType() == Material.FENCE_GATE) || (block.getType() == Material.TRAP_DOOR) || 
        (block.getType() == Material.IRON_TRAPDOOR) || (block.getType() == Material.STONE_BUTTON) || 
        (block.getType() == Material.WOOD_BUTTON) || (block.getType() == Material.BEACON) || 
        (block.getType() == Material.CAULDRON)))
      {
        if (this.pl.xpitemlore.containsKey(e.getPlayer().getName())) {
          this.pl.xpitemlore.remove(e.getPlayer().getName());
        }
        e.getPlayer().updateInventory();
        return;
      }
      Player p = e.getPlayer();
      String ExpName = this.pl.getCfg().getConfig().getString("BottleName");
      ExpName = ExpName.replaceAll("&", "�");
      ExpName = ExpName.replaceAll("\\d","");
      if (!p.getItemInHand().hasItemMeta())
      {
        if (this.pl.xpitemlore.containsKey(p.getName()))
        {
          e.setCancelled(true);
          p.updateInventory();
        }
        return;
      }
      if (!p.getItemInHand().getItemMeta().hasLore()) {
        return;
      }
      if (p.getItemInHand().getItemMeta().getDisplayName().contains("Frasco de XP"))
      {
        if (this.pl.xpitemlore.containsKey(p.getName()))
        {
          e.setCancelled(true);
          p.updateInventory();
          return;
        }
        String lore = (String)e.getPlayer().getItemInHand().getItemMeta().getLore().get(0);
        if (e.hasItem())
        {
          lore = ChatColor.stripColor(lore);
          String[] values = lore.split(" ");
          String[] arrayOfString1;
          int j = (arrayOfString1 = values).length;
          for (int i = 0; i < j; i++)
          {
            String bottlevalue = arrayOfString1[i];
            try
            {
              int exp = Integer.parseInt(bottlevalue);
              this.pl.xpitemlore.put(p.getName(), Integer.valueOf(exp));
            }
            catch (NumberFormatException localNumberFormatException) {}
          }
        }
      }
    }
  }
  
  @EventHandler
  public void onExp(ExpBottleEvent e)
  {
    Projectile pro = e.getEntity();
    if (!(pro.getShooter() instanceof Player)) {
      return;
    }
    Player p = (Player)pro.getShooter();
    if (!(pro instanceof ThrownExpBottle)) {
      return;
    }
    if (!this.pl.xpitemlore.isEmpty())
    {
      int xp = ((Integer)this.pl.xpitemlore.get(p.getName())).intValue();
      
      e.setExperience(xp);
      this.pl.xpitemlore.remove(p.getName());
      p.updateInventory();
    }
    else {}
  }
  
  @EventHandler
  public void PlayerDeath(PlayerDeathEvent e)
  {
    Player p = e.getEntity();
    if (!p.hasPermission("exp.drop")) {
      return;
    }
    int xp = XpLevel.getXp(p);
    if (xp <= 0) {
      return;
    }
    double dropPercentage = this.pl.getCfg().getConfig().getDouble("DropPercentage") / 100.0D;
    xp = (int)(xp * dropPercentage);
    this.pl.xpBottle.setXpb(xp, p);
    
    ItemStack Xpb = this.pl.xpBottle.getXpb();
    
    p.getWorld().dropItem(p.getLocation(), Xpb);
    p.setTotalExperience(0);
    p.setLevel(0);
    p.setExp(0.0F);
    e.setDroppedExp(0);
  }
}
