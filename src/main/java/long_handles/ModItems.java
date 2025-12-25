package long_handles;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import long_handles.ModItemTier;
import long_handles.Long_Handles;
import long_handles.JoStaffItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;   
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
// The value here should match an entry in the META-INF/mods.toml file
import net.minecraft.item.SwordItem;   

public class ModItems 
{
    public static final DeferredRegister<Item> ITEMS
    = DeferredRegister.create(ForgeRegistries.ITEMS, Long_Handles.MODID);

    public static final RegistryObject<Item> LONG_HANDLE = ITEMS.register("long_handle", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> JO_STAFF = ITEMS.register("jo_staff", () -> new JoStaffItem(
        ModItemTier.JO_STAFF, -1, -2.0f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> BO_STAFF = ITEMS.register("bo_staff", () -> new BoStaffItem(ModItemTier.BO_STAFF, 0, -3.0f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    public static final RegistryObject<Item> WOOD_FELLING_AXE = ITEMS.register("wood_felling_axe", () -> new FellingAxeItem(ModItemTier.WOOD_FELLING_AXE, 0, -2.5f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> STONE_FELLING_AXE = ITEMS.register("stone_felling_axe", () -> new FellingAxeItem(ModItemTier.STONE_FELLING_AXE, 0, -3.f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_FELLING_AXE = ITEMS.register("iron_felling_axe", () -> new FellingAxeItem(ModItemTier.IRON_FELLING_AXE, 0, -3.f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> DIAMOND_FELLING_AXE = ITEMS.register("diamond_felling_axe", () -> new FellingAxeItem(ModItemTier.DIAMOND_FELLING_AXE, 0, -3.f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> GOLDEN_FELLING_AXE = ITEMS.register("golden_felling_axe", () -> new FellingAxeItem(ModItemTier.GOLDEN_FELLING_AXE, 0, -3.f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> NETHERITE_FELLING_AXE = ITEMS.register("netherite_felling_axe", () -> new FellingAxeItem(ModItemTier.NETHERITE_FELLING_AXE, 0, -3.f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

    public static void register(IEventBus bus){
        ITEMS.register(bus);

    }

}

