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
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.client.ClientHooks;

/**
 * <p>Fired for hooking into {@link ContainerScreen} rendering. <br/>
 * See the two subclasses to listen for foreground or background rendering. </p>
 *
 * <p>These events are fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see GuiContainerEvent.DrawForeground
 * @see GuiContainerEvent.DrawBackground
 **/
public class GuiContainerEvent extends Event
{

    private final ContainerScreen guiContainer;

    public GuiContainerEvent(ContainerScreen guiContainer)
    {
        this.guiContainer = guiContainer;
    }

    /**
     * @return the container's screen
     */
    public ContainerScreen getGuiContainer()
    {
        return guiContainer;
    }

    /**
     * <p>Fired after the container screen's foreground layer and elements are drawn, but
     * before rendering the tooltips and the item stack being dragged by the player. </p>
     *
     * <p>This can be used for rendering elements that must be above other screen elements, but
     * below tooltips and the dragged stack, such as slot or item stack specific overlays. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class DrawForeground extends GuiContainerEvent
    {
        private final MatrixStack mStack;
        private final int mouseX;
        private final int mouseY;

        public DrawForeground(ContainerScreen guiContainer, MatrixStack mStack, int mouseX, int mouseY)
        {
            super(guiContainer);
            this.mStack = mStack;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        /**
         * @return the matrix stack used for rendering
         */
        public MatrixStack getMatrixStack()
        {
            return mStack;
        }

        /**
         * @return the x coordinate of the mouse pointer
         */
        public int getMouseX()
        {
            return mouseX;
        }

        /**
         * @return the y coordinate of the mouse pointer
         */
        public int getMouseY()
        {
            return mouseY;
        }
    }

    /**
     * <p>Fired after the container screen's background layer and elements are drawn. <br/>
     * This can be used for rendering new background elements. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class DrawBackground extends GuiContainerEvent
    {
        private final MatrixStack mStack;
        private final int mouseX;
        private final int mouseY;

        public DrawBackground(ContainerScreen guiContainer, MatrixStack mStack, int mouseX, int mouseY)
        {
            super(guiContainer);
            this.mStack = mStack;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        /**
         * @return the matrix stack used for rendering
         */
        public MatrixStack getMatrixStack()
        {
            return mStack;
        }

        /**
         * @return the x coordinate of the mouse pointer
         */
        public int getMouseX()
        {
            return mouseX;
        }

        /**
         * @return the y coordinate of the mouse pointer
         */
        public int getMouseY()
        {
            return mouseY;
        }
    }
}
