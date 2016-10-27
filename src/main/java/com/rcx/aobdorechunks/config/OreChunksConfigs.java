package com.rcx.aobdorechunks.config;

public class OreChunksConfigs {

	private boolean isEnabled = true;
	private int dropCount = 1;
	private int MinXPDrop = 1;
	private int MaxXPDrop = 4;

	public OreChunksConfigs(String name) {

		if ("atlarus".equals(name) || "adamantine".equals(name) || "sanguinite".equals(name) || "meutoite".equals(name) || "iridium".equals(name) || "plutonium".equals(name)) {
			MaxXPDrop = 8;
		} else if ("platinum".equals(name) || "orichalcum".equals(name) || "kalendrite".equals(name) || "vulcanite".equals(name) || "eximite".equals(name)) {
			MaxXPDrop = 7;
		} else if ("cobalt".equals(name) || "ardite".equals(name) || "mithril".equals(name) || "rubracium".equals(name) || "ceruclase".equals(name) || "alduorite".equals(name) || "osmium".equals(name) || "tungsten".equals(name) || "titanium".equals(name) || "uranium".equals(name)) {
			MaxXPDrop = 6;
		} else if ("endium".equals(name) || "manganese".equals(name) || "oureclase".equals(name) || "astralsilver".equals(name) || "carmot".equals(name) || "midasium".equals(name) || "vyroxeres".equals(name) || "darkiron".equals(name)) {
			MaxXPDrop = 5;
		} else {
			dropCount = 1;
			MinXPDrop = 1;
			MaxXPDrop = 4;
		}
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getDropCount() {
		return dropCount;
	}

	public void setDropCount(int dropAmount) {
		this.dropCount = dropAmount;
	}

	public int getMinXPDrop() {
		return MinXPDrop;
	}

	public void setMinXPDrop(int XPAmount) {
		this.MinXPDrop = XPAmount;
	}

	public int getMaxXPDrop() {
		return MaxXPDrop;
	}

	public void setMaxXPDrop(int XPAmount) {
		this.MaxXPDrop = XPAmount;
	}

}
