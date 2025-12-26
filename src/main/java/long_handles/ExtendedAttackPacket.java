package long_handles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ExtendedAttackPacket {
    private final int entityId;

    public ExtendedAttackPacket(int entityId) {
        this.entityId = entityId;
    }

    public ExtendedAttackPacket(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(entityId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                Entity target = player.level.getEntity(entityId);

                if (target != null && !(target instanceof PlayerEntity)) {
                    // Verify distance with extended reach
                    double extendedReach = getExtendedReach(player);
                    double distance = player.distanceTo(target);

                    if (distance <= extendedReach) {
                        player.attack(target);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private double getExtendedReach(PlayerEntity player) {
        ModifiableAttributeInstance reachAttribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        return reachAttribute != null ? reachAttribute.getValue() : 3.0D;
    }
}