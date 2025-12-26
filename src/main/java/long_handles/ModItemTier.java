package long_handles;
import long_handles.ModItems;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;

import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;   

@MethodsReturnNonnullByDefault
public enum ModItemTier implements IItemTier{
    JO_STAFF(() -> Ingredient.of(ItemTags.PLANKS), 104, 6.0F, 3.0F, 2, 15),
    BO_STAFF(() -> Ingredient.of(ItemTags.PLANKS), 208, 6.0F, 3.0F, 2, 15),

    WOOD_FELLING_AXE(() -> Ingredient.of(ItemTags.PLANKS), 59, 0.5F, 0.0F, 1, 15),
    STONE_FELLING_AXE(() -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 131, 0.5F, 4.0F, 1, 15),
    IRON_FELLING_AXE(() -> Ingredient.of(Items.IRON_INGOT), 250, 0.5F, 8.0F, 2, 14),
    GOLDEN_FELLING_AXE(() -> Ingredient.of(Items.GOLD_INGOT), 32, 0.5F, 7.0F, 2, 14),
    DIAMOND_FELLING_AXE(() -> Ingredient.of(Items.DIAMOND), 1561, 0.5F, 9.0F, 3, 10),
    NETHERITE_FELLING_AXE(() -> Ingredient.of(Items.NETHERITE_INGOT), 2031, 0.5F, 10.0F, 4, 14);

    private final int uses;
    private final float speed;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModItemTier(Supplier<Ingredient> repairIngredient, int uses, float speed, float attackDamage, int harvestLevel, int enchantmentValue) {
        this.uses        = uses;
        this.speed     = speed;
        this.attackDamage   = attackDamage;
        this.harvestLevel   = harvestLevel;
        this.enchantmentValue   = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }
    @Override
    public int getUses(){
        return this.uses;
    }
    @Override
    public float getSpeed(){
        return this.speed;
    }
    @Override
    public float getAttackDamageBonus(){
        return this.attackDamage;
    }
    @Override
    public int getLevel(){
        return this.harvestLevel;
    }
    @Override
    public int getEnchantmentValue(){
        return this.enchantmentValue;
    }
    @Override
    public Ingredient getRepairIngredient(){
        return this.repairIngredient.get();
    }
}

