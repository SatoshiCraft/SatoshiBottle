package com.satoshicraft.bottle;

import org.bukkit.entity.Player;

public class XpLevel
{
  private static double xplevel;
  private static int xpe;
  private static int result;
  
  public static void setXpLevel(int level, float cExp)
  {
    if (level > 30)
    {
      xplevel = 4.5D * level * level - 162.5D * level + 2220.0D;
      xpe = 9 * level - 158;
      xplevel += Math.round(cExp * xpe);
      result = (int)xplevel;
      return;
    }
    if (level > 15)
    {
      xplevel = 2.5D * level * level - 40.5D * level + 360.0D;
      xpe = 5 * level - 38;
      xplevel += Math.round(cExp * xpe);
      result = (int)xplevel;
      return;
    }
    if (level <= 15)
    {
      xplevel = level * level + 6 * level;
      xpe = 2 * level + 7;
      xplevel += Math.round(cExp * xpe);
      result = (int)xplevel;
      return;
    }
  }
  
  public static int getXp(Player p)
  {
    setXpLevel(p.getLevel(), p.getExp());
    return result;
  }
}
