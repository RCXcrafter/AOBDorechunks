package com.rcx.aobdorechunks.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.aobd.items.AOBDItem;
import ganymedes01.aobd.ore.Ore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class AOBDItemOrechunk extends AOBDItem {

	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	protected final String stoneType;

	public AOBDItemOrechunk(String base, Ore ore, String stoneType) {
		super(base, ore);
		this.stoneType = stoneType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 0 ? icon[0] : icon[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icon = new IIcon[2];
		icon[0] = reg.registerIcon(getIconString() + "_" + stoneType);
		icon[1] = reg.registerIcon(getIconString() + "_overlay_" + stoneType);
	}
}
