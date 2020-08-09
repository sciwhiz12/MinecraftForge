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

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired before a hand is rendered in the first person view. </p>
 *
 * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
 * If this event is cancelled, then the hand will not be rendered. </p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ForgeHooksClient#renderSpecificFirstPersonHand(Hand, MatrixStack, IRenderTypeBuffer, int, float, float, float, float, ItemStack)
 */
@Cancelable
public class RenderHandEvent extends Event
{
    private final Hand hand;
    private final MatrixStack mat;
    private final IRenderTypeBuffer buffers;
    private final int light;
    private final float partialTicks;
    private final float interpolatedPitch;
    private final float swingProgress;
    private final float equipProgress;
    @Nonnull
    private final ItemStack stack;

    public RenderHandEvent(Hand hand, MatrixStack mat, IRenderTypeBuffer buffers, int light,
                           float partialTicks, float interpolatedPitch,
                           float swingProgress, float equipProgress, @Nonnull ItemStack stack)
    {
        this.hand = hand;
        this.mat = mat;
        this.buffers = buffers;
        this.light = light;
        this.partialTicks = partialTicks;
        this.interpolatedPitch = interpolatedPitch;
        this.swingProgress = swingProgress;
        this.equipProgress = equipProgress;
        this.stack = stack;
    }

    /**
     * @return the hand being rendered
     */
    public Hand getHand()
    {
        return hand;
    }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack()
    {
        return mat;
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

    /**
     * @return the amount of partial ticks
     */
    public float getPartialTicks()
    {
        return partialTicks;
    }

    /**
     * @return the interpolated pitch of the player entity
     */
    public float getInterpolatedPitch()
    {
        return interpolatedPitch;
    }

    /**
     * @return the swing progress of the hand being rendered
     */
    public float getSwingProgress()
    {
        return swingProgress;
    }

    /**
     * @return the progress of the equip animation, from 0.0 to 1.0
     */
    public float getEquipProgress()
    {
        return equipProgress;
    }

    /**
     * @return the item stack to be rendered
     */
    @Nonnull
    public ItemStack getItemStack()
    {
        return stack;
    }
}
