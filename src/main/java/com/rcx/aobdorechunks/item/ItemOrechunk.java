package com.rcx.aobdorechunks.item;

import com.rcx.aobdorechunks.ModuleOreChunks;
import com.rcx.aobdorechunks.ModuleOreChunks.FirstOreInfos;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.item.ItemBase;

public class ItemOrechunk extends ItemBase {

	public ItemOrechunk(ItemEntry itemEntry, IOreEntry oreEntry) {
		super(itemEntry, oreEntry);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		String stoneType = "stone";
		for(FirstOreInfos info : ModuleOreChunks.oreInformation) {
			if(info.entry.getOreName().equals(getOreEntry().getOreName()))
				stoneType = info.stoneType;
		}
		ModelLoader.setCustomModelResourceLocation((Item)this, 0, new ModelResourceLocation("jaopcaoc:orechunk_" + stoneType, "inventory"));
	}
}
