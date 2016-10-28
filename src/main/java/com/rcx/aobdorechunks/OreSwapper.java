package com.rcx.aobdorechunks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rcx.aobdorechunks.OreChunkAddon.OreInfos;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.aobd.ore.Ore;

public class OreSwapper {

    @SubscribeEvent
    public void SwapOres(HarvestDropsEvent event)
    {
        if(event.isSilkTouching)
            return;
        if(event.drops.isEmpty())
            return;

        OreInfos oreInfos = null;
        String orename = null;
    	ItemStack modifiedDrop = null;

        for(ItemStack drop : event.drops){
            if(drop.getItem() instanceof ItemBlock)
            {
                int[] oreids = OreDictionary.getOreIDs(drop);
                for (int i=0; i<oreids.length; i++) {
                	orename = OreDictionary.getOreName(oreids[i]);
                	if (!orename.startsWith("ore"))
                		continue;

                    if(!OreChunkAddon.dropMap.containsKey(orename))
                    	continue;

                	oreInfos = OreChunkAddon.dropMap.get(orename);
                	int count = randomCount(oreInfos.count, event.fortuneLevel, event.world);
                	
                	ItemStack chunkStack = new ItemStack(oreInfos.chunkItem);
                	chunkStack.stackSize = count;
        			modifiedDrop = chunkStack;
                	break;
                	
                }
            }
        }

        if(oreInfos == null)
            return;
        
        event.drops.clear();
        event.dropChance = 1.0f;
    	event.drops.add(modifiedDrop);
        
        if(oreInfos.maxXP <= 0)
            return;
        
        int countOrbs = event.world.rand.nextInt(oreInfos.maxXP-oreInfos.minXP);
        countOrbs += oreInfos.minXP;
        
        for(int i = 0; i < countOrbs; ++i)
        {
            event.world.spawnEntityInWorld(new EntityXPOrb(event.world, (double)event.x + 0.5D, (double)event.y + 0.5D, (double)event.z + 0.5D, 1));
        }
    }

    private int randomCount(int baseCount, int fortuneLevel, World world)
    {		
        if(fortuneLevel > 0)
        {
            int j = world.rand.nextInt(fortuneLevel + 2) - 1;

            if (j < 0)
            	j = 0;

            return baseCount * (j + 1);
        } else {
            return baseCount;
        }
    }
}
