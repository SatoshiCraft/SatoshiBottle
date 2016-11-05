package com.satoshicraft.bottle;

import org.bukkit.entity.Player;

public class NumberCheck
{
  public static boolean isInt(String s, Player p)
  {
    try
    {
      Integer.parseInt(s);
    }
    catch (NumberFormatException nfe)
    {
      return false;
    }
    return true;
  }
}
