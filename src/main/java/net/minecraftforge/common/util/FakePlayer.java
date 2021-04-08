/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.common.util;

import javax.annotation.Nullable;
import javax.crypto.Cipher;

import com.mojang.authlib.GameProfile;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.*;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.stats.Stat;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.net.SocketAddress;
import java.util.Set;
import java.util.UUID;

//Preliminary, simple Fake Player class
public class FakePlayer extends ServerPlayerEntity
{
    public FakePlayer(ServerWorld world, GameProfile name)
    {
        super(world.getServer(), world, name, new PlayerInteractionManager(world));
        connection = new FakeServerPlayerNetHandler(world.getServer(), this);
    }

    @Override public Vector3d position() { return new Vector3d(0, 0, 0); }
    @Override public BlockPos blockPosition() { return BlockPos.ZERO; }
    @Override public void displayClientMessage(ITextComponent message, boolean actionBar) { /* noop */ }
    @Override public void sendMessage(ITextComponent message, UUID senderUUID) { /* noop */ }
    @Override public void awardStat(Stat<?> stat, int amount) { /* noop */ }
    @Override public boolean isInvulnerableTo(DamageSource source) { return true; }
    @Override public boolean canHarmPlayer(PlayerEntity player) { return false; }
    @Override public void die(DamageSource source) { /* noop */ }
    @Override public void tick() { /* noop */ }
    @Override public void updateOptions(CClientSettingsPacket packet) { /* noop */ }
    @Override @Nullable public MinecraftServer getServer() { return ServerLifecycleHooks.getCurrentServer(); }

    static class FakeServerPlayerNetHandler extends ServerPlayNetHandler {
        FakeServerPlayerNetHandler(MinecraftServer server, FakePlayer player) {
            // Server is always present, player is always present (FakePlayer)
            // connection needs to be faked
            super(server, new FakeNetworkManager(), player);
        }

        @Override public void tick() { /* noop */ }
        @Override public void resetPosition() { /* noop */ }
        @Override public NetworkManager getConnection() { return super.getConnection(); }
        @Override public void disconnect(ITextComponent text) { /* noop */ }
        @Override public void handlePlayerInput(CInputPacket packet) { /* noop */ }
        @Override public void handleMoveVehicle(CMoveVehiclePacket packet) { /* noop */ }
        @Override public void handleAcceptTeleportPacket(CConfirmTeleportPacket packet) { /* noop */ }
        @Override public void handleRecipeBookSeenRecipePacket(CMarkRecipeSeenPacket packet) { /* noop */ }
        @Override public void handleRecipeBookChangeSettingsPacket(CUpdateRecipeBookStatusPacket packet) { /* noop */ }
        @Override public void handleSeenAdvancements(CSeenAdvancementsPacket packet) { /* noop */ }
        @Override public void handleCustomCommandSuggestions(CTabCompletePacket packet) { /* noop */ }
        @Override public void handleSetCommandBlock(CUpdateCommandBlockPacket packet) { /* noop */ }
        @Override public void handleSetCommandMinecart(CUpdateMinecartCommandBlockPacket packet) { /* noop */ }
        @Override public void handlePickItem(CPickItemPacket packet) { /* noop */ }
        @Override public void handleRenameItem(CRenameItemPacket packet) { /* noop */ }
        @Override public void handleSetBeaconPacket(CUpdateBeaconPacket packet) { /* noop */ }
        @Override public void handleSetStructureBlock(CUpdateStructureBlockPacket packet) { /* noop */ }
        @Override public void handleSetJigsawBlock(CUpdateJigsawBlockPacket packet) { /* noop */ }
        @Override public void handleJigsawGenerate(CJigsawBlockGeneratePacket packet) { /* noop */ }
        @Override public void handleSelectTrade(CSelectTradePacket packet) { /* noop */ }
        @Override public void handleEditBook(CEditBookPacket packet) { /* noop */ }
        @Override public void handleEntityTagQuery(CQueryEntityNBTPacket packet) { /* noop */ }
        @Override public void handleBlockEntityTagQuery(CQueryTileEntityNBTPacket packet) { /* noop */ }
        @Override public void handleMovePlayer(CPlayerPacket packet) { /* noop */ }
        @Override public void teleport(double x, double y, double z, float xRot, float yRot, Set<SPlayerPositionLookPacket.Flags> flags) { /* noop */ }
        @Override public void handlePlayerAction(CPlayerDiggingPacket packet) { /* noop */ }
        @Override public void handleUseItemOn(CPlayerTryUseItemOnBlockPacket packet) { /* noop */ }
        @Override public void handleUseItem(CPlayerTryUseItemPacket packet) { /* noop */ }
        @Override public void handleTeleportToEntityPacket(CSpectatePacket packet) { /* noop */ }
        @Override public void handleResourcePackResponse(CResourcePackStatusPacket packet) { /* noop */ }
        @Override public void handlePaddleBoat(CSteerBoatPacket packet) { /* noop */ }
        @Override public void onDisconnect(ITextComponent message) { /* noop */ }
        @Override public void send(IPacket<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> callback) { /* noop */ }
        @Override public void handleSetCarriedItem(CHeldItemChangePacket packet) { /* noop */ }
        @Override public void handleChat(CChatMessagePacket packet) { /* noop */ }
        @Override public void handleAnimate(CAnimateHandPacket packet) { /* noop */ }
        @Override public void handlePlayerCommand(CEntityActionPacket packet) { /* noop */ }
        @Override public void handleInteract(CUseEntityPacket packet) { /* noop */ }
        @Override public void handleClientCommand(CClientStatusPacket packet) { /* noop */ }
        @Override public void handleContainerClose(CCloseWindowPacket packet) { /* noop */ }
        @Override public void handleContainerClick(CClickWindowPacket packet) { /* noop */ }
        @Override public void handlePlaceRecipe(CPlaceRecipePacket packet) { /* noop */ }
        @Override public void handleContainerButtonClick(CEnchantItemPacket packet) { /* noop */ }
        @Override public void handleSetCreativeModeSlot(CCreativeInventoryActionPacket packet) { /* noop */ }
        @Override public void handleContainerAck(CConfirmTransactionPacket packet) { /* noop */ }
        @Override public void handleSignUpdate(CUpdateSignPacket packet) { /* noop */ }
        @Override public void handleKeepAlive(CKeepAlivePacket packet) { /* noop */ }
        @Override public void handlePlayerAbilities(CPlayerAbilitiesPacket packet) { /* noop */ }
        @Override public void handleClientInformation(CClientSettingsPacket packet) { /* noop */ }
        @Override public void handleCustomPayload(CCustomPayloadPacket packet) { /* noop */ }
        @Override public void handleChangeDifficulty(CSetDifficultyPacket packet) { /* noop */ }
        @Override public void handleLockDifficulty(CLockDifficultyPacket packet) { /* noop */}
    }

    static class FakeNetworkManager extends NetworkManager {
        public FakeNetworkManager() {
            super(PacketDirection.SERVERBOUND);
        }
        @Override public void channelActive(ChannelHandlerContext context) throws Exception { /* noop */ }
        @Override public void setProtocol(ProtocolType p_150723_1_) { /* noop */ }
        @Override public void channelInactive(ChannelHandlerContext context) throws Exception { /* noop */ }
        @Override public void exceptionCaught(ChannelHandlerContext context, Throwable exception) { /* noop */ }
        @Override protected void channelRead0(ChannelHandlerContext context, IPacket<?> packet) throws Exception { /* noop */ }
        @Override public void setListener(INetHandler listener) { super.setListener(listener); }
        @Override public void send(IPacket<?> packet) { /* noop */ }
        @Override public void send(IPacket<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> callback) { /* noop */ }
        @Override public void tick() { /* noop */ }
        @Override protected void tickSecond() { /* noop */ }
        @Override public SocketAddress getRemoteAddress() { return super.getRemoteAddress(); }
        @Override public void disconnect(ITextComponent message) { /* noop */ }
        @Override public boolean isMemoryConnection() { return super.isMemoryConnection(); }
        @Override public void setEncryptionKey(Cipher decryptCipher, Cipher encryptCipher) { /* noop */ }
        @Override public boolean isEncrypted() { return super.isEncrypted(); }
        @Override public boolean isConnected() { return super.isConnected(); }
        @Override public boolean isConnecting() { return super.isConnecting(); }
        @Override public INetHandler getPacketListener() { return super.getPacketListener(); }
        @Nullable @Override public ITextComponent getDisconnectedReason() { return super.getDisconnectedReason(); }
        @Override public void setReadOnly() { /* noop */ }
        @Override public void setupCompression(int compressionLevel) { /* noop */ }
        @Override public void handleDisconnection() { /* noop */ }
        @Override public float getAverageReceivedPackets() { return super.getAverageReceivedPackets(); }
        @Override public float getAverageSentPackets() { return super.getAverageSentPackets(); }
        @Override public Channel channel() { return super.channel(); }
        @Override public PacketDirection getDirection() {
            return super.getDirection();
        }
    }
}
