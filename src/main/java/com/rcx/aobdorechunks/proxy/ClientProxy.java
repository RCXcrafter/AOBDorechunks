package com.rcx.aobdorechunks.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.item.ItemBase;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerModel(Item item, String stoneType) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("aobdoc:orechunk_" + stoneType, "#inventory"));
	}
}
