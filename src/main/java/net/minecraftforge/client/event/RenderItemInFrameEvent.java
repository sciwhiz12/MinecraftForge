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

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nonnull;

/**
 * <p>Fired before an item stack is rendered in an item frame. <br/>
 * This can be used to prevent normal rendering and add custom rendering. </p>
 *
 * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
 * If the event is cancelled, then the item stack will not be rendered</p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ItemFrameRenderer
 */
@Cancelable
public class RenderItemInFrameEvent extends Event
{
    private final ItemStack item;
    private final ItemFrameEntity entityItemFrame;
    private final ItemFrameRenderer renderer;
    private final MatrixStack matrix;
    private final IRenderTypeBuffer buffers;
    private final int light;

    public RenderItemInFrameEvent(ItemFrameEntity itemFrame, ItemFrameRenderer renderItemFrame, MatrixStack matrix,
                                  IRenderTypeBuffer buffers, int light)
    {
        item = itemFrame.getDisplayedItem();
        entityItemFrame = itemFrame;
        renderer = renderItemFrame;
        this.matrix = matrix;
        this.buffers = buffers;
        this.light = light;
    }

    /**
     * @return the item stack being rendered
     */
    @Nonnull
    public ItemStack getItem()
    {
        return item;
    }

    /**
     * @return the item frame entity
     */
    public ItemFrameEntity getEntityItemFrame()
    {
        return entityItemFrame;
    }

    /**
     * @return the renderer for the item frame entity
     */
    public ItemFrameRenderer getRenderer()
    {
        return renderer;
    }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrix() {
        return matrix;
    }

    /**
     * @return the rendering buffers
     */
    public IRenderTypeBuffer getBuffers() {
        return buffers;
    }

    /**
     * @return the amount of packed (sky and block) light for rendering
     */
    public int getLight() {
        return light;
    }
}
