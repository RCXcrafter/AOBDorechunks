package com.rcx.aobdorechunks;

import ganymedes01.aobd.api.IAOBDAddon;
import ganymedes01.aobd.items.AOBDItem;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.ore.OreFinder;
import ganymedes01.aobd.recipes.RecipesModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.rcx.aobdorechunks.config.ConfigHandler;
import com.rcx.aobdorechunks.config.OreChunksConfigs;
import com.rcx.aobdorechunks.lib.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class OreChunkAddon implements IAOBDAddon {

	public static Map<Ore, OreChunksConfigs> chunkMap = new HashMap<Ore, OreChunksConfigs>();
	public static HashMap<String, OreInfos> dropMap = new HashMap<String, OreInfos>();

	@Override
	public void receiveOreList(Collection<Ore> ores) {
		
		for (Ore ore : ores) {
			if (!ore.isEnabled())
				continue;

			OreChunksConfigs config = ConfigHandler.INSTANCE.init(ore);
			chunkMap.put(ore, config);
			if (!config.isEnabled())
				continue;

			// Create the ore chunk item
			String base = "orechunk";
			AOBDItem orechunk = new AOBDItem(base, ore);
			orechunk.setTextureName(Reference.MOD_ID + ":" + base);
			orechunk.setUnlocalizedName(Reference.MOD_ID + "." + base + ore);
			OreFinder.registerOre(base + ore.name(), orechunk);
			OreDictionary.registerOre(AOBDOC.processingUnit + ore.name(), orechunk);

			// Add smelting recipe
			ItemStack ingot;
			ingot = RecipesModule.getOreStack("ingot", ore);
			GameRegistry.addSmelting(new ItemStack(orechunk), ingot, 1F);
			
			//make it drop
			OreInfos infos = new OreInfos(new ItemStack(orechunk), config.getDropCount(), config.getMinXPDrop(), config.getMaxXPDrop());
			dropMap.put("ore" + ore.name(), infos);
			//OreSwapper.addOre(ore, config.getDropCount(), config.getMinXPDrop(), config.getMaxXPDrop(), new ItemStack(orechunk));
		}
	}	
	public void notifyColourCreation() {}


    public class OreInfos
    {
        public ItemStack chunkItem;
        public int count;
        public int minXP;
        public int maxXP;

        public OreInfos(ItemStack oreChunkItem, int baseCount, int dropXPMin, int dropXPMax)
        {
        	chunkItem = oreChunkItem;
            count = baseCount;
            minXP = dropXPMin;
            maxXP = dropXPMax;
        }
    }
}