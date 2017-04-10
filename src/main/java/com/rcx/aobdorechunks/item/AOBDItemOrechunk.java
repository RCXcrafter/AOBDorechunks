package com.rcx.aobdorechunks.item;

import de.canitzp.genericdust.item.ColoredItem;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AOBDItemOrechunk extends ColoredItem {

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
