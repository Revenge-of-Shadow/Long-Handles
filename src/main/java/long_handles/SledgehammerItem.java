package long_handles;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;


@MethodsReturnNonnullByDefault
public class SledgehammerItem extends PickaxeItem {
    private static final UUID REACH_MODIFIER_UUID =
        UUID.fromString("9c9d1f38-2b9e-4f6a-bf9e-8d9f91a9c001");
    public static final int cooldown = 40;

    private boolean isOnCooldown(PlayerEntity player){
        if(player.level.isClientSide) return player.getCooldowns().isOnCooldown(this);
        CompoundNBT data = player.getPersistentData();
        long now = player.level.getGameTime();

        return (data.contains("long_handles_cd") && data.getLong("long_handles_cd") > now);
    }
    private void startCooldown(PlayerEntity player){
        if(player.level.isClientSide) return;

        CompoundNBT data = player.getPersistentData();
        long now = player.level.getGameTime();

        //  Logical
        data.putLong("long_handles_cd", now + cooldown);
        //  Visual
        player.getCooldowns().addCooldown(this, cooldown);
    }

    public SledgehammerItem(IItemTier tier, int attackDamage, float attackSpeed, Properties properties){
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
        Multimap<Attribute, AttributeModifier> modifiers =
                HashMultimap.create(super.getDefaultAttributeModifiers(slot));

        if (slot == EquipmentSlotType.MAINHAND) {
            modifiers.put(
                    ForgeMod.REACH_DISTANCE.get(),
                    new AttributeModifier(
                            REACH_MODIFIER_UUID,
                            "Weapon reach bonus",
                            1.00,
                            AttributeModifier.Operation.ADDITION
                    )
            );
        }
        return modifiers;
    }
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state){
        PlayerEntity player = Minecraft.getInstance().player;

        if(player!=null && !isOnCooldown(player)) {
            return Float.MAX_VALUE;
        }
        return 0.f;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player){
        if(isOnCooldown(player))    return true;
        startCooldown(player);
        return false;
    }
    @SubscribeEvent
    public  static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event){
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof SledgehammerItem){
           if(((SledgehammerItem) stack.getItem()).isOnCooldown(player)
                || !player.getOffhandItem().isEmpty()){
               event.setCanceled(true);
           }
           else {
               ((SledgehammerItem) stack.getItem()).startCooldown(player);
           }
        }
    }
    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity){
        if(!player.getOffhandItem().isEmpty() || isOnCooldown(player)){
            return true;
        }
        startCooldown(player);
        return super.onLeftClickEntity(stack, player, entity);
    }
    // Does not prevent use
    // but, like, whatever.
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand){
        ItemStack stack = player.getItemInHand(hand);
        if(!player.getOffhandItem().isEmpty()){
            return ActionResult.pass(player.getItemInHand(hand));
        }
        player.startUsingItem(hand);
        return ActionResult.consume(stack);
    }
    @Override
    public UseAction getUseAnimation(ItemStack stack){
        return UseAction.BLOCK;
    }
    @Override
    public int getUseDuration(ItemStack stack){
        return 72000/4;   //  Shield value, reduced.
    }

}
