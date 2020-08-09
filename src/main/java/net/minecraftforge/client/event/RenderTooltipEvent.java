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

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.client.gui.GuiUtils;

/**
 * <p>Fired when a tooltip is rendering. <br/>
 * See the various subclasses for listening to specific events. </p>
 *
 * @see RenderTooltipEvent.Pre
 * @see RenderTooltipEvent.Post
 * @see RenderTooltipEvent.Color
 * @see GuiUtils#drawHoveringText(ItemStack, MatrixStack, List, int, int, int, int, int, int, int, int, FontRenderer)
 */
public abstract class RenderTooltipEvent extends Event
{
    @Nonnull
    protected final ItemStack stack;
    protected final List<? extends ITextProperties> lines;
    protected final MatrixStack matrixStack;
    protected int x;
    protected int y;
    protected FontRenderer fr;

    public RenderTooltipEvent(@Nonnull ItemStack stack, @Nonnull List<? extends ITextProperties> lines, MatrixStack matrixStack, int x, int y, @Nonnull FontRenderer fr)
    {
        this.stack = stack;
        this.lines = Collections.unmodifiableList(lines);
        this.matrixStack = matrixStack;
        this.x = x;
        this.y = y;
        this.fr = fr;
    }

    /**
     * @return the stack which the tooltip is being rendered for; may be {@link ItemStack#EMPTY}
     */
    @Nonnull
    public ItemStack getStack()
    {
        return stack;
    }
    
    /**
     * <p>The lines to be drawn. May change between {@link RenderTooltipEvent.Pre} and {@link RenderTooltipEvent.Post}. <br/>
     * <em>Use {@link ItemTooltipEvent} to modify tooltip text. </em></p>
     *
     * @return the unmodifiable list of text on the tooltip
     */
    @Nonnull
    public List<? extends ITextProperties> getLines()
    {
        return lines;
    }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack()
    {
        return matrixStack;
    }

    /**
     * @return the X position of the tooltip box
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return the Y position of the tooltip box
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * @return The font renderer
     */
    @Nonnull
    public FontRenderer getFontRenderer()
    {
        return fr;
    }

    /**
     * <p>Fired <b>before</b> the tooltip is rendered. <br/>
     * This can be used to modify the positioning of the tooltip. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the tooltip will not be rendered and the corresponding
     * {@link RenderTooltipEvent.Color}, {@link RenderTooltipEvent.PostBackground}, and
     * {@link RenderTooltipEvent.PostText} will not be fired. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    @Cancelable
    public static class Pre extends RenderTooltipEvent
    {
        private int screenWidth;
        private int screenHeight;
        private int maxWidth;

        public Pre(@Nonnull ItemStack stack, @Nonnull List<? extends ITextProperties> lines, MatrixStack matrixStack, int x, int y, int screenWidth, int screenHeight, int maxWidth, @Nonnull FontRenderer fr)
        {
            super(stack, lines, matrixStack, x, y, fr);
            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;
            this.maxWidth = maxWidth;
        }

        /**
         * @return the width of the screen
         */
        public int getScreenWidth()
        {
            return screenWidth;
        }

        public void setScreenWidth(int screenWidth)
        {
            this.screenWidth = screenWidth;
        }

        public int getScreenHeight()
        {
            return screenHeight;
        }

        public void setScreenHeight(int screenHeight)
        {
            this.screenHeight = screenHeight;
        }

        /**
         * <p>Returns the maximum width of the tooltip when being rendered. <br/>
         * A value of {@code -1} means an unlimited maximum width. </p>
         *
         * @return the maximum width
         */
        public int getMaxWidth()
        {
            return maxWidth;
        }

        /**
         * <p>Sets the maximum width of the tooltip. <br/>
         * Use {@code -1} for unlimited maximum width. </p>
         *
         * @param maxWidth the new maximum width
         */
        public void setMaxWidth(int maxWidth)
        {
            this.maxWidth = maxWidth;
        }
        
        /**
         * Sets the {@link FontRenderer} to be used to render text.
         *
         * @param fr the new font renderer
         */
        public void setFontRenderer(@Nonnull FontRenderer fr)
        {
            this.fr = fr;
        }

        /**
         * Sets the X origin of the tooltip.
         *
         * @param x the new X origin
         */
        public void setX(int x)
        {
            this.x = x;
        }

        /**
         * Sets the Y origin of the tooltip.
         *
         * @param y the new Y origin
         */
        public void setY(int y)
        {
            this.y = y;
        }
    }

    /**
     * <p>Fired <b>after</b> the tooltip is rendered, at different points. <br/>
     * See the two subclasses for listening to after background or after text rendering. </p>
     *
     * @see RenderTooltipEvent.PostBackground
     * @see RenderTooltipEvent.PostText
     */
    protected static abstract class Post extends RenderTooltipEvent
    {
        private final int width;
        private final int height;
        
        public Post(@Nonnull ItemStack stack, @Nonnull List<? extends ITextProperties> textLines, MatrixStack matrixStack,int x, int y, @Nonnull FontRenderer fr, int width, int height)
        {
            super(stack, textLines, matrixStack, x, y, fr);
            this.width = width;
            this.height = height;
        }

        /**
         * @return the width of the inner tooltip box (not including border)
         */
        public int getWidth()
        {
            return width;
        }

        /**
         * @return the height of the inner tooltip box (not including border)
         */
        public int getHeight()
        {
            return height;
        }
    }

    /**
     * <p>Fired <b>after</b> the tooltip background is rendered, and <em>before</em> the tooltip text is rendered. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class PostBackground extends Post 
    {
        public PostBackground(@Nonnull ItemStack stack, @Nonnull List<? extends ITextProperties> textLines, MatrixStack matrixStack, int x, int y, @Nonnull FontRenderer fr, int width, int height)
            { super(stack, textLines, matrixStack, x, y, fr, width, height); }
    }

    /**
     * <p>Fired <b>after</b> the tooltip text is rendered. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class PostText extends Post
    {
        public PostText(@Nonnull ItemStack stack, @Nonnull List<? extends ITextProperties> textLines, MatrixStack matrixStack, int x, int y, @Nonnull FontRenderer fr, int width, int height)
            { super(stack, textLines, matrixStack, x, y, fr, width, height); }
    }

    /**
     * <p>Fired <em>directly <b>before</b> the tooltip background</em> is rendered. <br/>
     * This can be used to modify the background color and the border's gradient colors. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class Color extends RenderTooltipEvent
    {
        private final int originalBackground;
        private final int originalBorderStart;
        private final int originalBorderEnd;
        private int background;
        private int borderStart;
        private int borderEnd;

        public Color(@Nonnull ItemStack stack, @Nonnull List<? extends ITextProperties> textLines, MatrixStack matrixStack, int x, int y, @Nonnull FontRenderer fr, int background, int borderStart,
                int borderEnd)
        {
            super(stack, textLines, matrixStack, x, y, fr);
            this.originalBackground = background;
            this.originalBorderStart = borderStart;
            this.originalBorderEnd = borderEnd;
            this.background = background;
            this.borderStart = borderStart;
            this.borderEnd = borderEnd;
        }

        /**
         * @return the current tooltip background color
         */
        public int getBackground()
        {
            return background;
        }

        /**
         * Sets the new color for the tooltip background.
         *
         * @param background the new tooltip background color
         */
        public void setBackground(int background)
        {
            this.background = background;
        }

        /**
         * @return the start color for the tooltip border
         */
        public int getBorderStart()
        {
            return borderStart;
        }

        /**
         * Sets the new start color for the gradient of the tooltip border.
         *
         * @param borderStart the new start color for the tooltip border
         */
        public void setBorderStart(int borderStart)
        {
            this.borderStart = borderStart;
        }

        /**
         * @return the end color for the tooltip border
         */
        public int getBorderEnd()
        {
            return borderEnd;
        }

        /**
         * Sets the new end color for the gradient of the tooltip border.
         *
         * @param borderEnd the new end color for the tooltip border
         */
        public void setBorderEnd(int borderEnd)
        {
            this.borderEnd = borderEnd;
        }

        /**
         * @return the original tooltip background color
         */
        public int getOriginalBackground()
        {
            return originalBackground;
        }

        /**
         * @return the original tooltip border's start color
         */
        public int getOriginalBorderStart()
        {
            return originalBorderStart;
        }

        /**
         * @return the original tooltip border's end color
         */
        public int getOriginalBorderEnd()
        {
            return originalBorderEnd;
        }
    }
}
