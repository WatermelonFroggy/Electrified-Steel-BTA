package wf.electrifiedsteel;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.*;
import net.minecraft.core.world.World;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.WeatherStorm;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Properties;

public class ElectrifiedSteel implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "electrifiedsteel";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	static boolean isThundering;
	public static int FORTUNE_AMOUNT;
	public static int LOOTING_AMOUNT;
	public static int ELECTRIFIED_STEEL_INGOT_ID;
	public static int ELECTRIFIED_STEEL_PICKAXE_ID;
	public static int ELECTRIFIED_STEEL_AXE_ID;
	public static int ELECTRIFIED_STEEL_SHOVEL_ID;
	public static int ELECTRIFIED_STEEL_HOE_ID;
	public static int ELECTRIFIED_STEEL_SWORD_ID;
	public static int ELECTRIFIED_STEEL_BLOCK_ID;
	public static int ELECTRIFIED_STEEL_HELMET_ID;
	public static int ELECTRIFIED_STEEL_CHESTPLATE_ID;
	public static int ELECTRIFIED_STEEL_LEGGINGS_ID;
	public static int ELECTRIFIED_STEEL_BOOTS_ID;
	static {
		Properties prop = new Properties();
		prop.setProperty("fortune_amount","3");
		prop.setProperty("looting_amount","3");
		prop.setProperty("electrified_steel_ingot_id","18340");
		prop.setProperty("electrified_steel_pickaxe_id","18341");
		prop.setProperty("electrified_steel_axe_id","18342");
		prop.setProperty("electrified_steel_shovel_id","18343");
		prop.setProperty("electrified_steel_hoe_id","18344");
		prop.setProperty("electrified_steel_sword_id","18345");
		prop.setProperty("electrified_steel_helmet_id","18346");
		prop.setProperty("electrified_steel_chestplate_id","18347");
		prop.setProperty("electrified_steel_leggings_id","18348");
		prop.setProperty("electrified_steel_boots_id","18349");
		prop.setProperty("electrified_steel_block_id", "13490");
		ConfigHandler config = new ConfigHandler(MOD_ID,prop);

		FORTUNE_AMOUNT = config.getInt("fortune_amount");
		LOOTING_AMOUNT = config.getInt("looting_amount");
		ELECTRIFIED_STEEL_INGOT_ID = config.getInt("electrified_steel_ingot_id");
		ELECTRIFIED_STEEL_PICKAXE_ID = config.getInt("electrified_steel_pickaxe_id");
		ELECTRIFIED_STEEL_AXE_ID = config.getInt("electrified_steel_axe_id");
		ELECTRIFIED_STEEL_SHOVEL_ID = config.getInt("electrified_steel_shovel_id");
		ELECTRIFIED_STEEL_HOE_ID = config.getInt("electrified_steel_hoe_id");
		ELECTRIFIED_STEEL_SWORD_ID = config.getInt("electrified_steel_sword_id");
		ELECTRIFIED_STEEL_HELMET_ID = config.getInt("electrified_steel_helmet_id");
		ELECTRIFIED_STEEL_CHESTPLATE_ID = config.getInt("electrified_steel_chestplate_id");
		ELECTRIFIED_STEEL_LEGGINGS_ID = config.getInt("electrified_steel_leggings_id");
		ELECTRIFIED_STEEL_BOOTS_ID = config.getInt("electrified_steel_boots_id");
		ELECTRIFIED_STEEL_BLOCK_ID = config.getInt("electrified_steel_block_id");

		config.updateConfig();
	}
	public static Block electrifiedSteelBlock = new BlockBuilder(MOD_ID)
		.setSideTextures("electrifiedsteel_block_side.png")
		.setTopTexture("electrifiedsteel_block_top.png")
		.setBottomTexture("electrifiedsteel_block_bottom.png")
		.setHardness(5f)
		.setResistance(2000f)
		.addTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("block.electrifiedsteel", ELECTRIFIED_STEEL_BLOCK_ID, Material.metal));
	public static ToolMaterial electrifiedSteelTool = new ToolMaterial().setDurability(4608).setEfficiency(7.0f, 14.0f).setMiningLevel(3);
	public static Item ingotElectrifiedSteel = ItemHelper.createItem(MOD_ID, new Item("ingot.electrifiedsteel", ELECTRIFIED_STEEL_INGOT_ID), "electrifiedsteel_ingot.png");
	public static Item toolPickaxeElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemToolPickaxe("tool.pickaxe.electrifiedsteel", ELECTRIFIED_STEEL_PICKAXE_ID, electrifiedSteelTool), "electrifiedsteel_pickaxe.png");
	public static Item toolAxeElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemToolAxe("tool.axe.electrifiedsteel", ELECTRIFIED_STEEL_AXE_ID, electrifiedSteelTool), "electrifiedsteel_axe.png");
	public static Item toolShovelElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemToolShovel("tool.shovel.electrifiedsteel", ELECTRIFIED_STEEL_SHOVEL_ID, electrifiedSteelTool), "electrifiedsteel_shovel.png");
	public static Item toolHoeElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemToolHoe("tool.hoe.electrifiedsteel", ELECTRIFIED_STEEL_HOE_ID, electrifiedSteelTool), "electrifiedsteel_hoe.png");
	public static Item toolSwordElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemToolSword("tool.sword.electrifiedsteel", ELECTRIFIED_STEEL_SWORD_ID, electrifiedSteelTool), "electrifiedsteel_sword.png");
	public static ArmorMaterial electrifiedSteelArmor = ArmorHelper.createArmorMaterial(MOD_ID, "electrifiedsteel", 1200, 55.0f, 150.0f, 55.0f, 55.0f);
	public static Item helmetElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("helmet.electrifiedsteel", ELECTRIFIED_STEEL_HELMET_ID, electrifiedSteelArmor, 0), "electrifiedsteel_helmet.png");
	public static Item chestplateElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("chestplate.electrifiedsteel", ELECTRIFIED_STEEL_CHESTPLATE_ID, electrifiedSteelArmor, 1), "electrifiedsteel_chestplate.png");
	public static Item leggingsElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("leggings.electrifiedsteel", ELECTRIFIED_STEEL_LEGGINGS_ID, electrifiedSteelArmor, 2), "electrifiedsteel_leggings.png");
	public static Item bootsElectrifiedSteel = ItemHelper.createItem(MOD_ID, new ItemArmor("boots.electrifiedsteel", ELECTRIFIED_STEEL_BOOTS_ID, electrifiedSteelArmor, 3), "electrifiedsteel_boots.png");
	public static Tag<Block> forceFortune = Tag.of("electrifiedsteel$force_enable_fortune");
	public static Tag<Block> forceNoFortune = Tag.of("electrifiedsteel$force_disable_fortune");
	public static boolean canBeFortuned(Block block){
		if (block.hasTag(forceFortune)) return true;
		if (block.hasTag(forceNoFortune)) return false;
		if (block instanceof BlockLeavesBase) return true;
		if (block instanceof BlockOreCoal) return true;
		if (block instanceof BlockOreDiamond) return true;
		if (block instanceof BlockOreGold) return true;
		if (block instanceof BlockOreIron) return true;
		if (block instanceof BlockOreLapis) return true;
		if (block instanceof BlockOreNetherCoal) return true;
		if (block instanceof BlockOreRedstone) return true;
		if (block instanceof BlockTallGrass) return true;
		return false;
	}
    @Override
    public void onInitialize() {
        LOGGER.info("ElectrifiedSteel initialized.");
    }
	public static void OnTickInGame(Minecraft mc)
	{
		isThundering = (mc.theWorld.weatherManager.getCurrentWeather() == Weather.overworldStorm);
		if (isThundering) {
			LOGGER.info("It is storming!");
		}
	}
	@Override
	public void onRecipesReady() {

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M",
				"M",
				"S")
			.addInput('M', ingotElectrifiedSteel)
			.addInput('S', Item.stick)
			.create("moonsteel_sword", toolSwordElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MMM",
				" S ",
				" S ")
			.addInput('M', ingotElectrifiedSteel)
			.addInput('S', Item.stick)
			.create("electrifiedsteel_pickaxe", toolPickaxeElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MM",
				"MS",
				" S")
			.addInput('M', ingotElectrifiedSteel)
			.addInput('S', Item.stick)
			.create("electrifiedsteel_axe", toolAxeElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M",
				"S",
				"S")
			.addInput('M', ingotElectrifiedSteel)
			.addInput('S', Item.stick)
			.create("electrifiedsteel_shovel", toolShovelElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MM",
				" S",
				" S")
			.addInput('M', ingotElectrifiedSteel)
			.addInput('S', Item.stick)
			.create("electrifiedsteel_hoe", toolHoeElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MMM",
				"M M",
				"   ")
			.addInput('M', ingotElectrifiedSteel)
			.create("electrifiedsteel_helmet", helmetElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M M",
				"MMM",
				"MMM")
			.addInput('M', ingotElectrifiedSteel)
			.create("electrifiedsteel_chestplate", chestplateElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MMM",
				"M M",
				"M M")
			.addInput('M', ingotElectrifiedSteel)
			.create("electrifiedsteel_leggings", leggingsElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"M M",
				"M M",
				"   ")
			.addInput('M', ingotElectrifiedSteel)
			.create("electrifiedsteel_boots", bootsElectrifiedSteel.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
			.setShape(
				"MMM",
				"MMM",
				"MMM")
			.addInput('M', ingotElectrifiedSteel)
			.create("block_of_electrifiedsteel", electrifiedSteelBlock.getDefaultStack());

	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}
}
