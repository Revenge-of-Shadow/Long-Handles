package com.long_handles.long_handlesmod;

import com.long_handles.long_handlesmod.ModItemTier;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
// The value here should match an entry in the META-INF/mods.toml file
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;   
import net.minecraft.item.SwordItem;   

@Mod("long_handles")
public class Long_Handles 
{
    public Long_Handles(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Long_Handles.register(eventBus);
    }

    public static final DeferredRegister<Item> ITEMS
    = DeferredRegister.create(ForgeRegistries.ITEMS, "long_handles");

    public static final RegistryObject<Item> NAME_LONG_HANDLE = ITEMS.register("name_long_handle", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<SwordItem> NAME_JO_STAFF = ITEMS.register("name_jo_staff", () -> new SwordItem(ModItemTier.LONGHANDLES, 4, -2.8f, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}

