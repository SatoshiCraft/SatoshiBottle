package com.satoshicraft.bottle;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class XpItem
{
  @SuppressWarnings("unused")
private Main pl;
  private ItemStack Xpb;
  
  public XpItem(Main plugin)
  {
    this.pl = plugin;
  }
  
  public void setXpb(int takenXp, Player p)
  {
    this.Xpb = new ItemStack(Material.EXP_BOTTLE, 1);
    ItemMeta xpbmeta = this.Xpb.getItemMeta();
    String BottleName = "&6&lFrasco de XP &7(%value% XP)";
    BottleName = BottleName.replaceAll("&", "§");
    BottleName = BottleName.replace("%value%", String.valueOf(takenXp));
    xpbmeta.setDisplayName(BottleName);
    List<String> lore = new ArrayList<String>();
    String value = "&dValor&r %value% XP";
    value = value.replaceAll("&", "§");
    value = value.replace("%value%", String.valueOf(takenXp));
    lore.add(value);
    String enchanter = "&dEngarrafado por&r %player%";
    enchanter = enchanter.replaceAll("&", "§");
    if (enchanter.contains("%player%")) {
      enchanter = enchanter.replace("%player%", p.getName());
    }
    lore.add(enchanter);
    xpbmeta.setLore(lore);
    this.Xpb.setItemMeta(xpbmeta);
  }
  
  public void setXpbServer(int takenXp)
  {
    this.Xpb = new ItemStack(Material.EXP_BOTTLE, 1);
    ItemMeta xpbmeta = this.Xpb.getItemMeta();
    String BottleName = "&6&lFrasco de XP &7(%value% XP)";
    BottleName = BottleName.replaceAll("&", "�");
    BottleName = BottleName.replace("%value%", String.valueOf(takenXp));
    xpbmeta.setDisplayName(BottleName);
    List<String> lore = new ArrayList<String>();
    String value = "&dValor&r %value% XP";
    value = value.replaceAll("&", "�");
    value = value.replace("%value%", String.valueOf(takenXp));
    lore.add(value);
    String enchanter = "&dEngarrafado por &rServer";
    enchanter = enchanter.replaceAll("&", "�");
    lore.add(enchanter);
    xpbmeta.setLore(lore);
    this.Xpb.setItemMeta(xpbmeta);
  }
  
  public ItemStack getXpb()
  {
    return this.Xpb;
  }
}
