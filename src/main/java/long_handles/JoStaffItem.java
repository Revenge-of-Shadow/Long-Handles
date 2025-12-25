package long_handles;

import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;


@MethodsReturnNonnullByDefault
public class JoStaffItem extends SwordItem{
    private static final UUID REACH_MODIFIER_UUID =
        UUID.fromString("7c9d1f38-2b9e-4f6a-bf9e-8d9f91a9c001");

    public JoStaffItem(IItemTier tier, int attackDamage, float attackSpeed, Properties properties){
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
        Multimap<Attribute, AttributeModifier> modifiers =
                HashMultimap.create(super.getDefaultAttributeModifiers(slot));

        //  Do nothing if the other hand it occupied. - TODO
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
        return 0.2f;
    }
    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player){
        if(!player.getOffhandItem().isEmpty()){
            return true;
        }
        return super.onBlockStartBreak(stack, pos, player);
    }
    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity){
        if(!player.getOffhandItem().isEmpty()){
            return true;
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
    // Does not prevent use
    // but, like, whatever.


}
