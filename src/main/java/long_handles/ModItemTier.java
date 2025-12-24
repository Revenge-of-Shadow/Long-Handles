package long_handles;
import long_handles.ModItems;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;   

@MethodsReturnNonnullByDefault
public enum ModItemTier implements IItemTier{
    LONGHANDLES(1, 96, 4.0F, 3.0F, 5, () -> Ingredient.of(ModItems.LONG_HANDLE.get()));

    private final int uses;
    private final float speed;
    private final float attackDamage;
    private final int level;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairingredient;

    ModItemTier(int level, int uses, float speed, float attackDamage, int enchantmentValue, Supplier<Ingredient> repairingredient){
        this.uses        = uses;
        this.speed     = speed;
        this.attackDamage   = attackDamage;
        this.level   = level;
        this.enchantmentValue   = enchantmentValue;
        this.repairingredient = repairingredient;
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
        return this.level;
    }
    @Override
    public int getEnchantmentValue(){
        return this.enchantmentValue;
    }
    @Override
    public Ingredient getRepairIngredient(){
        return this.repairingredient.get();
    }
}

