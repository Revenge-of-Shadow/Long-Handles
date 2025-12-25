package long_handles;

import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;


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
}
