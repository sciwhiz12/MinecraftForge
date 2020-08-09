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

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Set;

/**
 * <p>Fired when the texture map is stitched together. <br/>
 * See the two subclasses to listen for before and after stitching. </p>
 *
 * @see TextureStitchEvent.Pre
 * @see TextureStitchEvent.Post
 * @see AtlasTexture
 */
public class TextureStitchEvent extends Event implements IModBusEvent
{
    private final AtlasTexture map;

    public TextureStitchEvent(AtlasTexture map)
    {
        this.map = map;
    }

    /**
     * @return the texture atlas
     */
    public AtlasTexture getMap()
    {
        return map;
    }

    /**
     * <p>Fired <b>before</b> the {@link AtlasTexture} is stitched together. <br/>
     * This can be used to add custom sprites to be stitched into the atlas. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain FMLJavaModLoadingContext#getModEventBus()} mod-specific event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onTextureStitchedPre(AtlasTexture, Set)
     */
    public static class Pre extends TextureStitchEvent
    {
        private final Set<ResourceLocation> sprites;

        public Pre(AtlasTexture map, Set<ResourceLocation> sprites)
        {
            super(map);
            this.sprites = sprites;
        }

        /**
         * Adds a sprite to be stitched into the texture atlas.
         *
         * @param sprite the location of the sprite
         */
        public boolean addSprite(ResourceLocation sprite) {
            return this.sprites.add(sprite);
        }
    }

    /**
     * <p>Fired <b>after</b> the {@link AtlasTexture} is stitched together. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain FMLJavaModLoadingContext#getModEventBus()} mod-specific event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onTextureStitchedPost(AtlasTexture)
     */
    public static class Post extends TextureStitchEvent
    {
        public Post(AtlasTexture map){ super(map); }
    }
}
