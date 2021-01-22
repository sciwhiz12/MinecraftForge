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

package net.minecraftforge.fml;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;

/**
 * A logical side of the Minecraft game.
 * <p>
 * There are two logical sides within the game:
 * <ul>
 *     <li>{@link #CLIENT} is the logical <em>client</em>, which is controlled by the player. It processes inputs to be
 *     sent over to the server, renders the viewport of the game, and maintains a copy of the world as dictated by the
 *     server's copy. </li>
 *     <li>{@link #SERVER} is the logical <em>server</em>, which runs seperately from the logical client. It listens
 *     for and establishes connections from logical clients, handles the game logic for the world, and maintains the
 *     authoritative copy of the world. </li>
 * </ul>
 * <p>
 * The {@linkplain Dist#CLIENT client distribution} has a copy of both the logical
 * server and the logical client, while the {@linkplain Dist#DEDICATED_SERVER
 * dedicated server distribution}, as its name implies, only holds the logical server.
 *
 * @see Dist
 * @see LogicalSidedProvider
 * @see World#isRemote
 */
public enum LogicalSide
{
    /**
     * The logical client of the Minecraft game.
     * <p>
     * This is the side responsible for translating player inputs to entity movement and interaction, rendering the
     * viewport for the player, and storing the local client copy of the world as dictated by the server's
     * authoritative copy of the world.
     * <p>
     * The logical client is only shipped with the client distribution of the game.
     *
     * @see ClientWorld
     * @see Dist#CLIENT
     */
    CLIENT,
    /**
     * The logical server of the Minecraft game.
     * <p>
     * This is the side responsible for managing connections to logical clients, maintaining the authoritative copy of
     * the game world, and running the game simulation logic on the world.
     * <p>
     * The logical server comes with both the client distribution and the dedicated server distribution. The client
     * distribution runs the logical server for the "Singleplayer" mode, wherein the client both holds the logical
     * client and the logical server.
     *
     * @see ServerWorld
     * @see Dist#DEDICATED_SERVER
     */
    SERVER;

    /**
     * Returns if this logical side is a server.
     *
     * @return if this is the server side
     */
    public boolean isServer()
    {
        return !isClient();
    }

    /**
     * Returns if this logical side is a client.
     *
     * @return if this is the client side
     */
    public boolean isClient()
    {
        return this == CLIENT;
    }
}
