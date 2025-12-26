package long_handles;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;


@MethodsReturnNonnullByDefault
public class FellingAxeItem extends AxeItem {
    private static final UUID REACH_MODIFIER_UUID =
        UUID.fromString("9c9d1f38-2b9e-4f6a-bf9e-8d9f91a9c001");
    public static final int cooldown = 40;

    private boolean isOnCooldown(PlayerEntity player){
        if(player.level.isClientSide) return player.getCooldowns().isOnCooldown(this);
        CompoundNBT data = player.getPersistentData();
        long now = player.level.getGameTime();

        return (data.contains("felling_axe_cd") && data.getLong("felling_axe_cd") > now);
    }
    private void startCooldown(PlayerEntity player){
        if(player.level.isClientSide) return;

        CompoundNBT data = player.getPersistentData();
        long now = player.level.getGameTime();

        //  Logical
        data.putLong("felling_axe_cd", now + cooldown);
        //  Visual
        player.getCooldowns().addCooldown(this, cooldown);
    }

    public FellingAxeItem(IItemTier tier, int attackDamage, float attackSpeed, Properties properties){
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

        if (stack.getItem() instanceof FellingAxeItem){
           if(((FellingAxeItem) stack.getItem()).isOnCooldown(player)
                || !player.getOffhandItem().isEmpty()){
               event.setCanceled(true);
           }
           else {
               ((FellingAxeItem) stack.getItem()).startCooldown(player);
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

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = player.level;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof FellingAxeItem)) {
            return;
        }
        event.setCanceled(true);

        double reach = 4.0D;
        Vector3d eyePos = player.getEyePosition(1.0F);
        Vector3d lookVec = player.getLookAngle();
        Vector3d reachVec = eyePos.add(lookVec.scale(reach));

        AxisAlignedBB box = new AxisAlignedBB(eyePos.x, eyePos.y, eyePos.z, reachVec.x, reachVec.y, reachVec.z).inflate(1.0D);

        EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(world, player, eyePos, reachVec, box, e -> e instanceof LivingEntity && e != player && e.isPickable());

        if (result == null) {
            return;
        }

        Entity target = result.getEntity();
        if (!(target instanceof LivingEntity))
            return;
        float damage = (((FellingAxeItem) stack.getItem()).getAttackDamage())
                + ((FellingAxeItem) stack.getItem()).getTier().getAttackDamageBonus();
        target.hurt(DamageSource.playerAttack(player), damage);
        int kbLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, player);
        ((LivingEntity) target).knockback(0.4F*(kbLevel), reachVec.x, reachVec.z);
        player.swing(Hand.MAIN_HAND, true);

    }

}

//  Range is still fucked
//      changed range for Bo?
