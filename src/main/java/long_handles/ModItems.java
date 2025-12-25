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
        ModItemTier.JO_STAFF, -1, -2.0f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)
    ));
    public static final RegistryObject<Item> BO_STAFF = ITEMS.register("bo_staff", () -> new BoStaffItem(
            ModItemTier.BO_STAFF, 0, -3.0f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)
    ));

    public static void register(IEventBus bus){
        ITEMS.register(bus);

    }

}

