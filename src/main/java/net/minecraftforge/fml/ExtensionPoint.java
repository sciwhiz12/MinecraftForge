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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.resources.IResourcePack;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.fml.packs.ModFileResourcePack;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * Extension points for mods.
 * <p>
 * Mods can register extension operators for {@code ExtensionPoint}s in their mods, to allow some customization by the
 * mod for some aspects of Forge. Extension points are not required to be registered by mods.
 * @param <T> The type of the extension operator
 * @see ModLoadingContext#registerExtensionPoint(ExtensionPoint, Supplier)
 */
public class ExtensionPoint<T>
{
    /**
     * Configuration screen factory for the mod. Used in the mods list screen to display a custom configuration screen.
     * <p>
     * The extension point consists of a {@link BiFunction}, accepting two objects: the {@link Minecraft} instance and
     * the currently open screen (the {@link ModListScreen}), and returns a new {@link Screen} which will automatically
     * be displayed on the window.
     * <p>
     * The config button for the mod on the mods list screen will be inactive unless this extension point is present in
     * the mod. Forge may also provide a default configuration screen factory (in which case the button will be enabled
     * by default), but this can be overridden by mods through this extension point.
     * <code><pre>
     * {@link ModLoadingContext mlc}.{@link ModLoadingContext#registerExtensionPoint(ExtensionPoint, Supplier)
     * registerExtensionPoint}(CONFIGGUIFACTORY, () -> (minecraft, modsListScreen) -> new MyConfigScreen(minecraft, modsListScreen))
     * </code></pre>
     */
    public static final ExtensionPoint<BiFunction<Minecraft, Screen, Screen>> CONFIGGUIFACTORY = new ExtensionPoint<>();
    // TODO: this is not used anywhere; removed on purpose or accidentally?
    public static final ExtensionPoint<BiFunction<Minecraft, ModFileResourcePack, IResourcePack>> RESOURCEPACK = new ExtensionPoint<>();
    /**
     * Compatibility display test for the mod. Used by the client to display mod compatibility with remote servers and
     * with disk saves.
     * <p>
     * The extension point consists of a {@link Pair} of a {@link Supplier} and a {@link BiPredicate}.
     * <ul>
     *     <li>The {@code Supplier} provides a string: this is the "local" mod version, which is sent over the network
     *     or written to disk. </li>
     *     <li>The {@code BiPredicate} accepts a string and a boolean; the (possibly-{@code null}) string is the mod
     *     version from the remote server or the local save (the "remote" version), and the boolean indicates if the
     *     version comes from over the network (otherwise, it comes from a local save).
     *     <p>
     *     The predicate returns whether the given version from the specified location is compatible with the local version.</li>
     * </ul>
     * For server-side only mods, supply {@link FMLNetworkConstants#IGNORESERVERONLY} for the local version (so it will
     * be ignored when present on the server), and the predicate should accept any remote version from anywhere.
     * <code><pre>
     * {@link ModLoadingContext mlc}.{@link ModLoadingContext#registerExtensionPoint(ExtensionPoint, Supplier)
     * registerExtensionPoint}(DISPLAYTEST, () -> Pair.of(
     *     () -> FMLNetworkConstants.IGNORESERVERONLY, // Ignore this mod because it's server-only
     *     (remoteVersion, network) -> true // Accept any version, whether from network or save
     * ))
     * </code></pre>
     * <p>
     * For client-side only mods, supply any string for the local version, and the predicate should accept any remote
     * version if it comes from the network.
     * <code><pre>
     * mlc.registerExtensionPoint(DISPLAYTEST, () -> Pair.of(
     *     () -> "dQw4w9WgXcQ", // This will be ignored even if sent by the server because of the predicate
     *     (remoteVersion, network) -> network // Accept any version from the server
     * ))
     * </code></pre>
     * <p>
     * <strong>Note: </strong><em>This does not absolutely determine if a client is compatible with the server and can
     * connect successfully.</em> Other factors may apply to the connection, such as difference in network protocols or
     * registry contents, forced disconnection by mods, banned status, and others.
     */
    public static final ExtensionPoint<Pair<Supplier<String>, BiPredicate<String, Boolean>>> DISPLAYTEST = new ExtensionPoint<>();

    // TODO: this is not used anywhere
    private Class<T> type;

    private ExtensionPoint() {
    }

}
