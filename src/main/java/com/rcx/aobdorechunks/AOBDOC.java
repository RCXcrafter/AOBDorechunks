package com.rcx.aobdorechunks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;

import com.rcx.aobdorechunks.config.ConfigHandler;
import com.rcx.aobdorechunks.lib.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class AOBDOC {

	@Instance(Reference.MOD_ID)
	public static AOBDOC instance;
	public static String processingUnit;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//ConfigHandler.INSTANCE.preInit(event.getSuggestedConfigurationFile());
		//MinecraftForge.EVENT_BUS.register(ConfigHandler.INSTANCE);
		//AddonRegisterer.registerAddon();

		//MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		//ConfigHandler.INSTANCE.init();
		//MinecraftForge.EVENT_BUS.register(new OreSwapper());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
}
