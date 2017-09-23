package com.rcx.aobdorechunks.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerModel(Item item, String stoneType) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("jaopcaoc:orechunk_" + stoneType, "#inventory"));
	}
}
