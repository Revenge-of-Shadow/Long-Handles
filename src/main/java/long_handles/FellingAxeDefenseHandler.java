package long_handles;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Long_Handles.MODID)
public class FellingAxeDefenseHandler {
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event){
        if(!(event.getEntityLiving() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        ItemStack offHand = player.getOffhandItem();
        ItemStack mainHand = player.getMainHandItem();

        if(mainHand.getItem() instanceof FellingAxeItem && offHand.isEmpty() &&player.isUsingItem()){
            float original = event.getAmount();
            event.setAmount(original * 0.7f);   //  Fair?
        }
    }
}