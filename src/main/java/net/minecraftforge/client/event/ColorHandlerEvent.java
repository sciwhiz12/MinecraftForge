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

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * <p>Fired for registering block and item color handlers at the appropriate time. <br/>
 * See the two subclasses for registering blocks or items color handlers. </p>
 *
 * @see ColorHandlerEvent.Block
 * @see ColorHandlerEvent.Item
 **/
public abstract class ColorHandlerEvent extends Event implements IModBusEvent
{
    /**
     * <p>Fired for registering block colors handlers. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and do not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain FMLJavaModLoadingContext#getModEventBus() mod-specific event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onBlockColorsInit(BlockColors)
     */
    public static class Block extends ColorHandlerEvent
    {
        private final BlockColors blockColors;

        public Block(BlockColors blockColors)
        {
            this.blockColors = blockColors;
        }

        /**
         * @return an instance of the block colors
         * @see BlockColors#register(IBlockColor, net.minecraft.block.Block...)
         */
        public BlockColors getBlockColors()
        {
            return blockColors;
        }
    }

    /**
     * <p>Fired for registering item colors handlers. </p>
     *
     * <p>The block colors should only be used for referencing or
     * delegating item colors to their respective block colors. <br/>
     * Use {@link ColorHandlerEvent.Block} for registering your block color handlers. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and do not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain FMLJavaModLoadingContext#getModEventBus() mod-specific event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onItemColorsInit(ItemColors, BlockColors)
     */
    public static class Item extends ColorHandlerEvent
    {
        private final ItemColors itemColors;
        private final BlockColors blockColors;

        public Item(ItemColors itemColors, BlockColors blockColors)
        {
            this.itemColors = itemColors;
            this.blockColors = blockColors;
        }

        /**
         * @return an instance of the item colors
         * @see ItemColors#register(IItemColor, IItemProvider...)
         */
        public ItemColors getItemColors()
        {
            return itemColors;
        }

        /**
         * @return an instance of the block colors
         */
        public BlockColors getBlockColors()
        {
            return blockColors;
        }
    }
}
