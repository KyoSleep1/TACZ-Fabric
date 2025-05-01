package com.tacz.guns.item.tag;

import java.util.Set;
import java.util.function.Supplier;

import com.tacz.guns.item.tag.extensions.DyeExtension;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class Tags {
	public static class Blocks {
		public static void init() {
		}

		public static final TagKey<Block> BARRELS = tag("barrels");
		public static final TagKey<Block> BARRELS_WOODEN = tag("wooden_barrels");
		public static final TagKey<Block> BOOKSHELVES = ConventionalBlockTags.BOOKSHELVES;
		public static final TagKey<Block> CHESTS = ConventionalBlockTags.CHESTS;
		public static final TagKey<Block> CHESTS_ENDER = tag("ender_chests");
		public static final TagKey<Block> CHESTS_TRAPPED = tag("trapped_chests");
		public static final TagKey<Block> CHESTS_WOODEN = tag("wooden_chests");
		public static final TagKey<Block> COBBLESTONE = tag("cobblestone");
		public static final TagKey<Block> COBBLESTONE_NORMAL = tag("normal_cobblestone");
		public static final TagKey<Block> COBBLESTONE_INFESTED = tag("infested_cobblestone");
		public static final TagKey<Block> COBBLESTONE_MOSSY = tag("mossy_cobblestone");
		public static final TagKey<Block> COBBLESTONE_DEEPSLATE = tag("deepslate_cobblestone");
		public static final TagKey<Block> END_STONES = tag("end_stones");
		public static final TagKey<Block> ENDERMAN_PLACE_ON_BLACKLIST = tag("enderman_place_on_blacklist");
		public static final TagKey<Block> FENCE_GATES = tag("fence_gates");
		public static final TagKey<Block> FENCE_GATES_WOODEN = tag("wooden_fence_gates");
		public static final TagKey<Block> FENCES = tag("fences");
		public static final TagKey<Block> FENCES_NETHER_BRICK = tag("nether_brick_fences");
		public static final TagKey<Block> FENCES_WOODEN = tag("wooden_fences");

		public static final TagKey<Block> GLASS = ConventionalBlockTags.GLASS_BLOCKS;
		public static final TagKey<Block> GLASS_BLACK = tag("black_glass");
		public static final TagKey<Block> GLASS_BLUE = tag("blue_glass");
		public static final TagKey<Block> GLASS_BROWN = tag("brown_glass");
		public static final TagKey<Block> GLASS_COLORLESS = tag("colorless_glass");
		public static final TagKey<Block> GLASS_CYAN = tag("cyan_glass");
		public static final TagKey<Block> GLASS_GRAY = tag("gray_glass");
		public static final TagKey<Block> GLASS_GREEN = tag("green_glass");
		public static final TagKey<Block> GLASS_LIGHT_BLUE = tag("light_blue_glass");
		public static final TagKey<Block> GLASS_LIGHT_GRAY = tag("light_gray_glass");
		public static final TagKey<Block> GLASS_LIME = tag("lime_glass");
		public static final TagKey<Block> GLASS_MAGENTA = tag("magenta_glass");
		public static final TagKey<Block> GLASS_ORANGE = tag("orange_glass");
		public static final TagKey<Block> GLASS_PINK = tag("pink_glass");
		public static final TagKey<Block> GLASS_PURPLE = tag("purple_glass");
		public static final TagKey<Block> GLASS_RED = tag("red_glass");
		/**
		 * Glass which is made from sand and only minor additional ingredients like dyes
		 */
		public static final TagKey<Block> GLASS_SILICA = tag("silica_glass");
		public static final TagKey<Block> GLASS_TINTED = tag("tinted_glass");
		public static final TagKey<Block> GLASS_WHITE = tag("white_glass");
		public static final TagKey<Block> GLASS_YELLOW = tag("yellow_glass");

		public static final TagKey<Block> GLASS_PANES = ConventionalBlockTags.GLASS_PANES;
		public static final TagKey<Block> GLASS_PANES_BLACK = tag("black_glass_panes");
		public static final TagKey<Block> GLASS_PANES_BLUE = tag("blue_glass_panes");
		public static final TagKey<Block> GLASS_PANES_BROWN = tag("brown_glass_panes");
		public static final TagKey<Block> GLASS_PANES_COLORLESS = tag("colorless_glass_panes");
		public static final TagKey<Block> GLASS_PANES_CYAN = tag("cyan_glass_panes");
		public static final TagKey<Block> GLASS_PANES_GRAY = tag("gray_glass_panes");
		public static final TagKey<Block> GLASS_PANES_GREEN = tag("green_glass_panes");
		public static final TagKey<Block> GLASS_PANES_LIGHT_BLUE = tag("light_blue_glass_panes");
		public static final TagKey<Block> GLASS_PANES_LIGHT_GRAY = tag("light_gray_glass_panes");
		public static final TagKey<Block> GLASS_PANES_LIME = tag("lime_glass_panes");
		public static final TagKey<Block> GLASS_PANES_MAGENTA = tag("magenta_glass_panes");
		public static final TagKey<Block> GLASS_PANES_ORANGE = tag("orange_glass_panes");
		public static final TagKey<Block> GLASS_PANES_PINK = tag("pink_glass_panes");
		public static final TagKey<Block> GLASS_PANES_PURPLE = tag("purple_glass_panes");
		public static final TagKey<Block> GLASS_PANES_RED = tag("red_glass_panes");
		public static final TagKey<Block> GLASS_PANES_WHITE = tag("white_glass_panes");
		public static final TagKey<Block> GLASS_PANES_YELLOW = tag("yellow_glass_panes");

		public static final TagKey<Block> GRAVEL = tag("gravel");
		public static final TagKey<Block> NETHERRACK = tag("netherrack");
		public static final TagKey<Block> OBSIDIAN = tag("obsidian");
		/**
		 * Blocks which are often replaced by deepslate ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_DEEPSLATE}, during world generation
		 */
		public static final TagKey<Block> ORE_BEARING_GROUND_DEEPSLATE = tag("ore_bearing_ground/deepslate");
		/**
		 * Blocks which are often replaced by netherrack ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_NETHERRACK}, during world generation
		 */
		public static final TagKey<Block> ORE_BEARING_GROUND_NETHERRACK = tag("ore_bearing_ground/netherrack");
		/**
		 * Blocks which are often replaced by stone ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_STONE}, during world generation
		 */
		public static final TagKey<Block> ORE_BEARING_GROUND_STONE = tag("ore_bearing_ground/stone");
		/**
		 * Ores which on average result in more than one resource worth of materials
		 */
		public static final TagKey<Block> ORE_RATES_DENSE = tag("ore_rates/dense");
		/**
		 * Ores which on average result in one resource worth of materials
		 */
		public static final TagKey<Block> ORE_RATES_SINGULAR = tag("ore_rates/singular");
		/**
		 * Ores which on average result in less than one resource worth of materials
		 */
		public static final TagKey<Block> ORE_RATES_SPARSE = tag("ore_rates/sparse");
		public static final TagKey<Block> ORES = tag("ores");
		public static final TagKey<Block> ORES_COAL = tag("coal_ores");
		public static final TagKey<Block> ORES_COPPER = tag("copper_ores");
		public static final TagKey<Block> ORES_DIAMOND = tag("diamond_ores");
		public static final TagKey<Block> ORES_EMERALD = tag("emerald_ores");
		public static final TagKey<Block> ORES_GOLD = tag("gold_ores");
		public static final TagKey<Block> ORES_IRON = tag("iron_ores");
		public static final TagKey<Block> ORES_LAPIS = tag("lapis_ores");
		public static final TagKey<Block> ORES_NETHERITE_SCRAP = tag("netherite_scrap_ores");
		public static final TagKey<Block> ORES_QUARTZ = ConventionalBlockTags.QUARTZ_ORES;
		public static final TagKey<Block> ORES_REDSTONE = tag("redstone_ores");
		/**
		 * Ores in deepslate (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_DEEPSLATE}) which could logically use deepslate as recipe input or output
		 */
		public static final TagKey<Block> ORES_IN_GROUND_DEEPSLATE = tag("ores_in_ground/deepslate");
		/**
		 * Ores in netherrack (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_NETHERRACK}) which could logically use netherrack as recipe input or output
		 */
		public static final TagKey<Block> ORES_IN_GROUND_NETHERRACK = tag("ores_in_ground/netherrack");
		/**
		 * Ores in stone (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_STONE}) which could logically use stone as recipe input or output
		 */
		public static final TagKey<Block> ORES_IN_GROUND_STONE = tag("ores_in_ground/stone");

		public static final TagKey<Block> SAND = tag("sand");
		public static final TagKey<Block> SAND_COLORLESS = tag("colorless_sand");
		public static final TagKey<Block> SAND_RED = tag("red_sand");

		public static final TagKey<Block> SANDSTONE = tag("sandstone");
		public static final TagKey<Block> STAINED_GLASS = tag("stained_glass");
		public static final TagKey<Block> STAINED_GLASS_PANES = tag("stained_glass_panes");
		public static final TagKey<Block> STONE = tag("stone");
		public static final TagKey<Block> STORAGE_BLOCKS = tag("storage_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_AMETHYST = tag("amethyst_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_COAL = tag("coal_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_COPPER = tag("copper_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_DIAMOND = tag("diamond_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_EMERALD = tag("emerald_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_GOLD = tag("gold_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_IRON = tag("iron_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_LAPIS = tag("lapis_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_NETHERITE = tag("netherite_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_QUARTZ = tag("quartz_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_COPPER = tag("raw_copper_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_GOLD = tag("raw_gold_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_IRON = tag("raw_iron_blocks");
		public static final TagKey<Block> STORAGE_BLOCKS_REDSTONE = tag("redstone_blocks");

		private static TagKey<Block> tag(String name) {
			return TagKey.of(Registries.BLOCK.getKey(), Identifier.of("c", name));
		}
	}

	public static class Items {
		public static void init() {
		}

		public static final TagKey<Item> BARRELS = tag("barrels");
		public static final TagKey<Item> BARRELS_WOODEN = tag("wooden_barrels");
		public static final TagKey<Item> BONES = tag("bones");
		public static final TagKey<Item> BOOKSHELVES = tag("bookshelves");
		public static final TagKey<Item> CHESTS = tag("chests");
		public static final TagKey<Item> CHESTS_ENDER = tag("ender_chests");
		public static final TagKey<Item> CHESTS_TRAPPED = tag("trapped_chests");
		public static final TagKey<Item> CHESTS_WOODEN = tag("wooden_chests");
		public static final TagKey<Item> COBBLESTONE = tag("cobblestone");
		public static final TagKey<Item> COBBLESTONE_NORMAL = tag("normal_cobblestone");
		public static final TagKey<Item> COBBLESTONE_INFESTED = tag("infested_cobblestone");
		public static final TagKey<Item> COBBLESTONE_MOSSY = tag("mossy_cobblestone");
		public static final TagKey<Item> COBBLESTONE_DEEPSLATE = tag("deepslate_cobblestone");
		public static final TagKey<Item> CROPS = tag("crops");
		public static final TagKey<Item> CROPS_BEETROOT = tag("crops/beetroot");
		public static final TagKey<Item> CROPS_CARROT = tag("crops/carrot");
		public static final TagKey<Item> CROPS_NETHER_WART = tag("crops/nether_wart");
		public static final TagKey<Item> CROPS_POTATO = tag("crops/potato");
		public static final TagKey<Item> CROPS_WHEAT = tag("crops/wheat");
		public static final TagKey<Item> DUSTS = tag("dusts");
		public static final TagKey<Item> DUSTS_PRISMARINE = tag("dusts/prismarine");
		public static final TagKey<Item> DUSTS_REDSTONE = tag("dusts/redstone");
		public static final TagKey<Item> DUSTS_GLOWSTONE = tag("dusts/glowstone");

		public static final TagKey<Item> DYES = tag("dyes");
		public static final TagKey<Item> DYES_BLACK = ((DyeExtension) (Object) DyeColor.BLACK).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_RED = ((DyeExtension) (Object) DyeColor.RED).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_GREEN = ((DyeExtension) (Object) DyeColor.GREEN).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_BROWN = ((DyeExtension) (Object) DyeColor.BROWN).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_BLUE = ((DyeExtension) (Object) DyeColor.BLUE).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_PURPLE = ((DyeExtension) (Object) DyeColor.PURPLE).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_CYAN = ((DyeExtension) (Object) DyeColor.CYAN).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_LIGHT_GRAY = ((DyeExtension) (Object) DyeColor.LIGHT_GRAY).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_GRAY = ((DyeExtension) (Object) DyeColor.GRAY).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_PINK = ((DyeExtension) (Object) DyeColor.PINK).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_LIME = ((DyeExtension) (Object) DyeColor.LIME).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_YELLOW = ((DyeExtension) (Object) DyeColor.YELLOW).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_LIGHT_BLUE = ((DyeExtension) (Object) DyeColor.LIGHT_BLUE).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_MAGENTA = ((DyeExtension) (Object) DyeColor.MAGENTA).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_ORANGE = ((DyeExtension) (Object) DyeColor.ORANGE).tACZ_Fabric$getTag();
		public static final TagKey<Item> DYES_WHITE = ((DyeExtension) (Object) DyeColor.WHITE).tACZ_Fabric$getTag();


		public static final TagKey<Item> EGGS = tag("eggs");
		public static final TagKey<Item> ENCHANTING_FUELS = tag("enchanting_fuels");
		public static final TagKey<Item> END_STONES = tag("end_stones");
		public static final TagKey<Item> ENDER_PEARLS = tag("ender_pearls");
		public static final TagKey<Item> FEATHERS = tag("feathers");
		public static final TagKey<Item> FENCE_GATES = tag("fence_gates");
		public static final TagKey<Item> FENCE_GATES_WOODEN = tag("wooden_fence_gates");
		public static final TagKey<Item> FENCES = tag("fences");
		public static final TagKey<Item> FENCES_NETHER_BRICK = tag("nether_brick_fences");
		public static final TagKey<Item> FENCES_WOODEN = tag("wooden_fences");
		public static final TagKey<Item> GEMS = tag("gems");
		public static final TagKey<Item> GEMS_DIAMOND = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.DIAMONDS;
		public static final TagKey<Item> GEMS_EMERALD = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.EMERALDS;
		public static final TagKey<Item> GEMS_AMETHYST = tag("amethyst");
		public static final TagKey<Item> GEMS_LAPIS = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.LAPIS;
		public static final TagKey<Item> GEMS_PRISMARINE = tag("prismarine");
		public static final TagKey<Item> GEMS_QUARTZ = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.QUARTZ;

		public static final TagKey<Item> GLASS = ConventionalItemTags.GLASS_BLOCKS;
		public static final TagKey<Item> GLASS_BLACK = tag("black_glass");
		public static final TagKey<Item> GLASS_BLUE = tag("blue_glass");
		public static final TagKey<Item> GLASS_BROWN = tag("brown_glass");
		public static final TagKey<Item> GLASS_COLORLESS = tag("colorless_glass");
		public static final TagKey<Item> GLASS_CYAN = tag("cyan_glass");
		public static final TagKey<Item> GLASS_GRAY = tag("gray_glass");
		public static final TagKey<Item> GLASS_GREEN = tag("green_glass");
		public static final TagKey<Item> GLASS_LIGHT_BLUE = tag("light_blue_glass");
		public static final TagKey<Item> GLASS_LIGHT_GRAY = tag("light_gray_glass");
		public static final TagKey<Item> GLASS_LIME = tag("lime_glass");
		public static final TagKey<Item> GLASS_MAGENTA = tag("magenta_glass");
		public static final TagKey<Item> GLASS_ORANGE = tag("orange_glass");
		public static final TagKey<Item> GLASS_PINK = tag("pink_glass");
		public static final TagKey<Item> GLASS_PURPLE = tag("purple_glass");
		public static final TagKey<Item> GLASS_RED = tag("red_glass");
		/**
		 * Glass which is made from sand and only minor additional ingredients like dyes
		 */
		public static final TagKey<Item> GLASS_SILICA = tag("silica_glass");
		public static final TagKey<Item> GLASS_TINTED = tag("tinted_glass");
		public static final TagKey<Item> GLASS_WHITE = tag("white_glass");
		public static final TagKey<Item> GLASS_YELLOW = tag("yellow_glass");

		public static final TagKey<Item> GLASS_PANES = ConventionalItemTags.GLASS_PANES;
		public static final TagKey<Item> GLASS_PANES_BLACK = tag("black_glass_panes");
		public static final TagKey<Item> GLASS_PANES_BLUE = tag("blue_glass_panes");
		public static final TagKey<Item> GLASS_PANES_BROWN = tag("brown_glass_panes");
		public static final TagKey<Item> GLASS_PANES_COLORLESS = tag("colorless_glass_panes");
		public static final TagKey<Item> GLASS_PANES_CYAN = tag("cyan_glass_panes");
		public static final TagKey<Item> GLASS_PANES_GRAY = tag("gray_glass_panes");
		public static final TagKey<Item> GLASS_PANES_GREEN = tag("green_glass_panes");
		public static final TagKey<Item> GLASS_PANES_LIGHT_BLUE = tag("light_blue_glass_panes");
		public static final TagKey<Item> GLASS_PANES_LIGHT_GRAY = tag("light_gray_glass_panes");
		public static final TagKey<Item> GLASS_PANES_LIME = tag("lime_glass_panes");
		public static final TagKey<Item> GLASS_PANES_MAGENTA = tag("magenta_glass_panes");
		public static final TagKey<Item> GLASS_PANES_ORANGE = tag("orange_glass_panes");
		public static final TagKey<Item> GLASS_PANES_PINK = tag("pink_glass_panes");
		public static final TagKey<Item> GLASS_PANES_PURPLE = tag("purple_glass_panes");
		public static final TagKey<Item> GLASS_PANES_RED = tag("red_glass_panes");
		public static final TagKey<Item> GLASS_PANES_WHITE = tag("white_glass_panes");
		public static final TagKey<Item> GLASS_PANES_YELLOW = tag("yellow_glass_panes");

		public static final TagKey<Item> GRAVEL = tag("gravel");
		public static final TagKey<Item> GUNPOWDER = tag("gunpowder");
		public static final TagKey<Item> HEADS = tag("heads");
		public static final TagKey<Item> INGOTS = tag("ingots");
		public static final TagKey<Item> INGOTS_BRICK = tag("brick_ingots");
		public static final TagKey<Item> INGOTS_COPPER = tag("copper_ingots");
		public static final TagKey<Item> INGOTS_GOLD = tag("gold_ingots");
		public static final TagKey<Item> INGOTS_IRON = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.IRON_INGOTS;
		public static final TagKey<Item> INGOTS_NETHERITE = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.NETHERITE_INGOTS;
		public static final TagKey<Item> INGOTS_NETHER_BRICK = tag("nether_brick_ingots");
		public static final TagKey<Item> LEATHER = tag("leather");
		public static final TagKey<Item> MUSHROOMS = tag("mushrooms");
		public static final TagKey<Item> NETHER_STARS = tag("nether_stars");
		public static final TagKey<Item> NETHERRACK = tag("netherrack");
		public static final TagKey<Item> NUGGETS = tag("nuggets");
		public static final TagKey<Item> NUGGETS_GOLD = tag("gold_nuggets");
		public static final TagKey<Item> NUGGETS_IRON = tag("iron_nuggets");
		public static final TagKey<Item> OBSIDIAN = tag("obsidian");
		/**
		 * Blocks which are often replaced by deepslate ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_DEEPSLATE}, during world generation
		 */
		public static final TagKey<Item> ORE_BEARING_GROUND_DEEPSLATE = tag("ore_bearing_ground/deepslate");
		/**
		 * Blocks which are often replaced by netherrack ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_NETHERRACK}, during world generation
		 */
		public static final TagKey<Item> ORE_BEARING_GROUND_NETHERRACK = tag("ore_bearing_ground/netherrack");
		/**
		 * Blocks which are often replaced by stone ores, i.e. the ores in the tag {@link #ORES_IN_GROUND_STONE}, during world generation
		 */
		public static final TagKey<Item> ORE_BEARING_GROUND_STONE = tag("ore_bearing_ground/stone");
		/**
		 * Ores which on average result in more than one resource worth of materials
		 */
		public static final TagKey<Item> ORE_RATES_DENSE = tag("ore_rates/dense");
		/**
		 * Ores which on average result in one resource worth of materials
		 */
		public static final TagKey<Item> ORE_RATES_SINGULAR = tag("ore_rates/singular");
		/**
		 * Ores which on average result in less than one resource worth of materials
		 */
		public static final TagKey<Item> ORE_RATES_SPARSE = tag("ore_rates/sparse");
		public static final TagKey<Item> ORES = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.ORES;
		public static final TagKey<Item> ORES_COAL = tag("coal_ores");
		public static final TagKey<Item> ORES_COPPER = tag("copper_ores");
		public static final TagKey<Item> ORES_DIAMOND = tag("diamond_ores");
		public static final TagKey<Item> ORES_EMERALD = tag("emerald_ores");
		public static final TagKey<Item> ORES_GOLD = tag("gold_ores");
		public static final TagKey<Item> ORES_IRON = tag("iron_ores");
		public static final TagKey<Item> ORES_LAPIS = tag("lapis_ores");
		public static final TagKey<Item> ORES_NETHERITE_SCRAP = tag("netherite_scrap_ores");
		public static final TagKey<Item> ORES_QUARTZ = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.QUARTZ_ORES;
		public static final TagKey<Item> ORES_REDSTONE = tag("redstone_ores");
		/**
		 * Ores in deepslate (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_DEEPSLATE}) which could logically use deepslate as recipe input or output
		 */
		public static final TagKey<Item> ORES_IN_GROUND_DEEPSLATE = tag("ores_in_ground/deepslate");
		/**
		 * Ores in netherrack (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_NETHERRACK}) which could logically use netherrack as recipe input or output
		 */
		public static final TagKey<Item> ORES_IN_GROUND_NETHERRACK = tag("ores_in_ground/netherrack");
		/**
		 * Ores in stone (or in equivalent blocks in the tag {@link #ORE_BEARING_GROUND_STONE}) which could logically use stone as recipe input or output
		 */
		public static final TagKey<Item> ORES_IN_GROUND_STONE = tag("ores_in_ground/stone");
		public static final TagKey<Item> RAW_MATERIALS = tag("raw_materials");
		public static final TagKey<Item> RAW_MATERIALS_COPPER = tag("copper_raw_materials");
		public static final TagKey<Item> RAW_MATERIALS_GOLD = tag("gold_raw_materials");
		public static final TagKey<Item> RAW_MATERIALS_IRON = tag("iron_raw_materials");
		public static final TagKey<Item> RODS = tag("rods");
		public static final TagKey<Item> RODS_BLAZE = tag("blaze_rods");
		public static final TagKey<Item> RODS_WOODEN = tag("wooden_rods");

		public static final TagKey<Item> SAND = tag("sand");
		public static final TagKey<Item> SAND_COLORLESS = tag("colorless_sand");
		public static final TagKey<Item> SAND_RED = tag("red_sand");

		public static final TagKey<Item> SANDSTONE = tag("sandstone");
		public static final TagKey<Item> SEEDS = tag("seeds");
		public static final TagKey<Item> SEEDS_BEETROOT = tag("beetroot_seeds");
		public static final TagKey<Item> SEEDS_MELON = tag("melon_seeds");
		public static final TagKey<Item> SEEDS_PUMPKIN = tag("pumpkin_seeds");
		public static final TagKey<Item> SEEDS_WHEAT = tag("wheat_seeds");
		public static final TagKey<Item> SHEARS = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.SHEARS;
		public static final TagKey<Item> SLIMEBALLS = tag("slimeballs");
		public static final TagKey<Item> STAINED_GLASS = tag("stained_glass");
		public static final TagKey<Item> STAINED_GLASS_PANES = tag("stained_glass_panes");
		public static final TagKey<Item> STONE = tag("stone");
		public static final TagKey<Item> STORAGE_BLOCKS = tag("storage_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_AMETHYST = tag("amethyst_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_COAL = tag("coal_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_COPPER = tag("copper_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_DIAMOND = tag("diamond_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_EMERALD = tag("emerald_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_GOLD = tag("gold_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_IRON = tag("iron_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_LAPIS = tag("lapis_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_NETHERITE = tag("netherite_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_QUARTZ = tag("quartz_blocks");
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_COPPER = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.RAW_COPPER_BLOCKS;
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_GOLD = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.RAW_GOLD_BLOCKS;
		public static final TagKey<Item> STORAGE_BLOCKS_RAW_IRON = net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags.RAW_IRON_BLOCKS;
		public static final TagKey<Item> STORAGE_BLOCKS_REDSTONE = tag("redstone_blocks");
		public static final TagKey<Item> STRING = tag("string");
		/**
		 * A tag containing all existing tools.
		 */
		public static final TagKey<Item> TOOLS = tag("tools");
		/**
		 * A tag containing all existing swords.
		 */
		public static final TagKey<Item> TOOLS_SWORDS = ItemTags.SWORDS;
		/**
		 * A tag containing all existing axes.
		 */
		public static final TagKey<Item> TOOLS_AXES = ItemTags.AXES;
		/**
		 * A tag containing all existing pickaxes.
		 */
		public static final TagKey<Item> TOOLS_PICKAXES = ItemTags.PICKAXES;
		/**
		 * A tag containing all existing shovels.
		 */
		public static final TagKey<Item> TOOLS_SHOVELS = ItemTags.SHOVELS;
		/**
		 * A tag containing all existing hoes.
		 */
		public static final TagKey<Item> TOOLS_HOES = ItemTags.HOES;
		/**
		 * A tag containing all existing shields.
		 */
		public static final TagKey<Item> TOOLS_SHIELDS = tag("shields");
		/**
		 * A tag containing all existing bows.
		 */
		public static final TagKey<Item> TOOLS_BOWS = ConventionalItemTags.BOW_TOOLS;
		/**
		 * A tag containing all existing crossbows.
		 */
		public static final TagKey<Item> TOOLS_CROSSBOWS = tag("crossbows");
		/**
		 * A tag containing all existing fishing rods.
		 */
		public static final TagKey<Item> TOOLS_FISHING_RODS = tag("fishing_rods");
		/**
		 * A tag containing all existing tridents.
		 */
		public static final TagKey<Item> TOOLS_TRIDENTS = tag("tridents");
		/**
		 * A tag containing all existing armors.
		 */
		public static final TagKey<Item> ARMORS = tag("armors");
		/**
		 * A tag containing all existing helmets.
		 */
		public static final TagKey<Item> ARMORS_HELMETS = tag("helmets");
		/**
		 * A tag containing all chestplates.
		 */
		public static final TagKey<Item> ARMORS_CHESTPLATES = tag("chestplates");
		/**
		 * A tag containing all existing leggings.
		 */
		public static final TagKey<Item> ARMORS_LEGGINGS = tag("leggings");
		/**
		 * A tag containing all existing boots.
		 */
		public static final TagKey<Item> ARMORS_BOOTS = tag("boots");

		private static TagKey<Item> tag(String name, @Nullable Set<Supplier<Item>> defaults) {
			return TagKey.of(Registries.ITEM.getKey(), Identifier.of("c", name));
		}

		private static TagKey<Item> tag(String name) {
			return tag(name, null);
		}
	}
}