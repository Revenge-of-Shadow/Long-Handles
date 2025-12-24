package long_handles;

import long_handles.ModItemTier;
import long_handles.ModItems;

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

@Mod(Long_Handles.MODID)
public class Long_Handles 
{
    public static final String MODID = "long_handles";

    public Long_Handles(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
    }
}

