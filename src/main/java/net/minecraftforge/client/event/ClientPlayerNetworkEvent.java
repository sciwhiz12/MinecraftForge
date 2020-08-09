/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
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

package net.minecraftforge.client.event;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.client.ClientHooks;

import javax.annotation.Nullable;

/**
 * <p>Fired on the client for different connectivity events. <br/>
 * See the various subclasses to listen for specific events. </p>
 *
 * <p>These events are fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ClientPlayerNetworkEvent.LoggedInEvent
 * @see ClientPlayerNetworkEvent.LoggedOutEvent
 * @see ClientPlayerNetworkEvent.RespawnEvent
 **/
public class ClientPlayerNetworkEvent extends Event {
    private final PlayerController controller;
    private final ClientPlayerEntity player;
    private final NetworkManager networkManager;

    /**
     * @return the player controller for the client side, or {@code null}
     */
    @Nullable
    public PlayerController getController() {
        return controller;
    }

    /**
     * @return the player instance, or {@code null}
     */
    @Nullable
    public ClientPlayerEntity getPlayer() {
        return player;
    }

    /**
     * @return the network connection, or {@code null}
     */
    @Nullable
    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    ClientPlayerNetworkEvent(final PlayerController controller, final ClientPlayerEntity player, final NetworkManager networkManager) {
        this.controller = controller;
        this.player = player;
        this.networkManager = networkManager;
    }

    /**
     * <p>Fired when the client player logs in to the server. The player should be initialized. </p>
     *
     * <p>{@link #getController()}, {@link #getPlayer()}, and {@link #getNetworkManager()} are guaranteed
     * to never return {@code null} in this event. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ClientHooks#firePlayerLogin(PlayerController, ClientPlayerEntity, NetworkManager)
     */
    public static class LoggedInEvent extends ClientPlayerNetworkEvent {
        public LoggedInEvent(final PlayerController controller, final ClientPlayerEntity player, final NetworkManager networkManager) {
            super(controller, player, networkManager);
        }
    }

    /**
     * <p>Fired when the player logs out. This event may also fire when a new integrated server is being created. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ClientHooks#firePlayerLogout(PlayerController, ClientPlayerEntity)
     */
    public static class LoggedOutEvent extends ClientPlayerNetworkEvent {
        public LoggedOutEvent(final PlayerController controller, final ClientPlayerEntity player, final NetworkManager networkManager) {
            super(controller, player, networkManager);
        }
    }

    /**
     * <p>Fired when the player object respawns, such as dimension changes. </p>
     *
     * <p>{@link #getNewPlayer()} returns the same player instance as {@link #getPlayer()}. <br/>
     * {@link #getController()}, {@link #getPlayer()}, and {@link #getNetworkManager()} are guaranteed
     * to never return {@code null} in this event. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ClientHooks#firePlayerRespawn(PlayerController, ClientPlayerEntity, ClientPlayerEntity, NetworkManager)
     */
    public static class RespawnEvent extends ClientPlayerNetworkEvent {
        private final ClientPlayerEntity oldPlayer;

        public RespawnEvent(final PlayerController pc, final ClientPlayerEntity oldPlayer, final ClientPlayerEntity newPlayer, final NetworkManager networkManager) {
            super(pc, newPlayer, networkManager);
            this.oldPlayer = oldPlayer;
        }

        /**
         * @return the previous player instance
         */
        public ClientPlayerEntity getOldPlayer() {
            return oldPlayer;
        }

        /**
         * @return the newly created player instance
         */
        public ClientPlayerEntity getNewPlayer() {
            return super.getPlayer();
        }
    }
}
