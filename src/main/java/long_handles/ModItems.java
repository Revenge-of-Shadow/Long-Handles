package long_handles;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import long_handles.ModItemTier;
import long_handles.Long_Handles;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;   
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
// The value here should match an entry in the META-INF/mods.toml file
import net.minecraft.item.SwordItem;   

@Mod("long_handles")
public class ModItems 
{
    public static final DeferredRegister<Item> ITEMS
    = DeferredRegister.create(ForgeRegistries.ITEMS, Long_Handles.MOD_ID);

    public static final RegistryObject<Item> LONG_HANDLE = ITEMS.register("name_long_handle", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<SwordItem> JO_STAFF = ITEMS.register("name_jo_staff", () -> new SwordItem(ModItemTier.LONGHANDLES, 0, -2.2f, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));

    public static void register(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(eventBus);
    }

}

