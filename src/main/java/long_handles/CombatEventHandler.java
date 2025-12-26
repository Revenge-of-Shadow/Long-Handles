package long_handles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Long_Handles.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatEventHandler {

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        PlayerEntity player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if (!(heldItem.getItem() instanceof BoStaffItem)
            && !(heldItem.getItem() instanceof JoStaffItem)
            && !(heldItem.getItem() instanceof FellingAxeItem)
            && !(heldItem.getItem() instanceof SledgehammerItem)) {
            return;
        }

        // Perform raycast with extended distance
        double extendedReach = getExtendedReach(player);

        RayTraceResult result = rayTraceEntities(player, extendedReach);

        if (result instanceof EntityRayTraceResult) {
            EntityRayTraceResult entityResult = (EntityRayTraceResult) result;

            // Send packet to server using ModNetwork
            ModNetwork.sendToServer(new ExtendedAttackPacket(entityResult.getEntity().getId()));
        }
    }

    private static double getExtendedReach(PlayerEntity player) {
        ModifiableAttributeInstance reachAttribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        return reachAttribute != null ? reachAttribute.getValue() : 3.0D;
    }

    private static RayTraceResult rayTraceEntities(PlayerEntity player, double distance) {
        Vector3d eyePos = player.getEyePosition(1.0F);
        Vector3d lookVec = player.getViewVector(1.0F);
        Vector3d endPos = eyePos.add(lookVec.scale(distance));

        AxisAlignedBB searchBox = player.getBoundingBox().expandTowards(lookVec.scale(distance)).inflate(1.0D);

        EntityRayTraceResult entityResult = null;
        double closestDistance = distance;

        for (Entity entity : player.level.getEntities(player, searchBox, (e) -> !e.isSpectator() && e.isPickable())) {
            AxisAlignedBB entityBox = entity.getBoundingBox().inflate(entity.getPickRadius());
            Optional<Vector3d> hitResult = entityBox.clip(eyePos, endPos);

            if (hitResult.isPresent()) {
                double dist = eyePos.distanceTo(hitResult.get());
                if (dist < closestDistance) {
                    entityResult = new EntityRayTraceResult(entity, hitResult.get());
                    closestDistance = dist;
                }
            }
        }

        return entityResult != null ? entityResult : BlockRayTraceResult.miss(endPos, Direction.UP, new BlockPos(endPos));
    }
}