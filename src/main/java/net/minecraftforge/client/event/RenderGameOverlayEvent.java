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

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.ClientBossInfo;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired when an overlay element is rendered to the game window. <br/>
 * See the two subclasses for listening to before and after rendering. </p>
 *
 * <p>An overlay that is not normally active cannot be forced to render, and this event will not fire. </p>
 *
 * @see RenderGameOverlayEvent.Pre
 * @see RenderGameOverlayEvent.Post
 * @see ForgeIngameGui
 */
// TODO: move Cancelable annotation to Pre event, and remove isCancellable override to Post
@Cancelable
public class RenderGameOverlayEvent extends Event
{
    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack()
    {
        return mStack;
    }

    /**
     * @return the amount of partial ticks
     */
    public float getPartialTicks()
    {
        return partialTicks;
    }

    /**
     * @return the game window
     */
    public MainWindow getWindow()
    {
        return window;
    }

    /**
     * @return the type of the overlay element being rendered
     */
    public ElementType getType()
    {
        return type;
    }

    /**
     * The types of the different overlay elements that can be rendered
     */
    public static enum ElementType
    {
        /**
         * Represents all of the overlay elements; cancelling the {@link RenderGameOverlayEvent.Pre} with this type will
         * suppress rendering of all overlay elements
         */
        ALL,
        /**
         * The overlay for the currently worn helmet item, such as the pumpkin overlay texture <br/>
         * <em>See {@code ForgeIngameGui#renderHelmet}</em>
         */
        HELMET,
        /**
         * The nether portal overlay texture <br/>
         * <em>See {@code ForgeIngameGui#renderPortal}</em>
         */
        PORTAL,
        /**
         * The crosshair at the center of the screen
         */
        CROSSHAIRS,
        /**
         * Represents all of the boss bars at the top of the screen <br/>
         * <em>See {@code ForgeIngameGui#renderBossHealth}</em>
         * @see #BOSSINFO
         */
        BOSSHEALTH,
        /**
         * An individual boss bar
         * @see #BOSSHEALTH
         * @see RenderGameOverlayEvent.BossInfo
         */
        BOSSINFO,
        /**
         * The armor bar, representing the player's armor points <br/>
         * <em>See {@code ForgeIngameGui#renderArmor}</em>
         */
        ARMOR,
        /**
         * The health bar, representing the player's hit/health points <br/>
         * <em>See {@code ForgeIngameGui#renderHealth}</em>
         */
        HEALTH,
        /**
         * The food bar, representing the player's hunger level <br/>
         * <em>See {@code ForgeIngameGui#renderFood}</em>
         */
        FOOD,
        /**
         * The air bar, representing the player's remaining breathing time <br/>
         * <em>See {@code ForgeIngameGui#renderAir}</em>
         */
        AIR,
        /**
         * The hotbar with the player's items, the offhand slot, and the attack indicator if present on the hotbar
         */
        HOTBAR,
        /**
         * The experience bar, representing the player's experience points and levels <br/>
         * <em>See {@code ForgeIngameGui#renderExperience}</em>
         */
        EXPERIENCE,
        /**
         * For adding text to be rendered on the screen (after the debug screen text, if visible)
         * @see RenderGameOverlayEvent.Text
         */
        TEXT,
        /**
         * The mount's health bar, representing the player mount's hit/health points
         */
        HEALTHMOUNT,
        /**
         * The jump bar, representing the current power of the mount's jump
         */
        JUMPBAR,
        /**
         * The chat message window overlay
         * @see RenderGameOverlayEvent.Chat
         */
        CHAT,
        /**
         * The player list overlay, when the TAB key (or other keybind) is pressed <br/>
         * <em>See {@code ForgeIngameGui#renderPlayerList}</em>
         */
        PLAYER_LIST,
        /**
         * The debug overlay <br/>
         * <em>See {@code ForgeIngameGui#renderHUDText}</em>
         */
        DEBUG,
        /**
         * The potion icons on the top-right of the screen,
         * representing the player's currently active and visible potion effects <br/>
         */
        POTION_ICONS,
        /**
         * The subtitle overlay on the bottom-right of the screen,
         * representing the sounds played near the player <br/>
         * <em>See {@code ForgeIngameGui#renderSubtitles}</em>
         */
        SUBTITLES,
        /**
         * The FPS graph or lagometer, visible on the debug overlay when SHIFT + F3 is pressed <br/>
         * <em>See {@code ForgeIngameGui#renderFPSGraph}</em>
         */
        FPS_GRAPH,
        /**
         * The vignette overlay (in vanilla, the darkness near the edges of the window)
         */
        VIGNETTE
    }

    private final MatrixStack mStack;
    private final float partialTicks;
    private final MainWindow window;
    private final ElementType type;

    public RenderGameOverlayEvent(MatrixStack mStack, float partialTicks, MainWindow window)
    {
        this.mStack = mStack;
        this.partialTicks = partialTicks;
        this.window = window;
        this.type = null;
    }

    private RenderGameOverlayEvent(MatrixStack mStack, RenderGameOverlayEvent parent, ElementType type)
    {
        this.mStack = mStack;
        this.partialTicks = parent.getPartialTicks();
        this.window = parent.getWindow();
        this.type = type;
    }

    /**
     * <p>Fired <b>before</b> an active overlay element is rendered to the screen. <br/>
     * <em>See the three sub-classes for specific special overlay elements. </em></p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the overlay corresponding to this event will not be rendered, and the
     * corresponding {@link RenderGameOverlayEvent.Post} is not fired. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see RenderGameOverlayEvent.BossInfo
     * @see RenderGameOverlayEvent.Text
     * @see RenderGameOverlayEvent.Chat
     */
    public static class Pre extends RenderGameOverlayEvent
    {
        public Pre(MatrixStack mStack, RenderGameOverlayEvent parent, ElementType type)
        {
            super(mStack, parent, type);
        }
    }

    /**
     * <p>Fired <b>after</b> an active overlay element is rendered to the screen, e.g. if the corresponding
     * {@link RenderGameOverlayEvent.Post} is not cancelled. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class Post extends RenderGameOverlayEvent
    {
        public Post(MatrixStack mStack, RenderGameOverlayEvent parent, ElementType type)
        {
            super(mStack, parent, type);
        }
        @Override public boolean isCancelable(){ return false; }
    }

    /**
     * <p>Fired <b>before</b> an individual boss health bar is rendered to the screen, and the
     * corresponding {@link RenderGameOverlayEvent.Pre} with type {@link ElementType#BOSSHEALTH} is not cancelled.</p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the boss health bar corresponding to this event will not be rendered, and the
     * corresponding {@link RenderGameOverlayEvent.Post} is not fired. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class BossInfo extends Pre
    {
        private final ClientBossInfo bossInfo;
        private final int x;
        private final int y;
        private int increment;
        public BossInfo(MatrixStack mStack, RenderGameOverlayEvent parent, ElementType type, ClientBossInfo bossInfo, int x, int y, int increment)
        {
            super(mStack, parent, type);
            this.bossInfo = bossInfo;
            this.x = x;
            this.y = y;
            this.increment = increment;
        }

        /**
         * @return The {@link ClientBossInfo} currently being rendered
         */
        public ClientBossInfo getBossInfo()
        {
            return bossInfo;
        }

        /**
         * @return The X position of the boss health bar
         */
        public int getX()
        {
            return x;
        }

        /**
         * @return The Y position of the boss health bar
         */
        public int getY()
        {
            return y;
        }

        /**
         * @return the Y position increment before rendering the next boss health bar
         */
        public int getIncrement()
        {
            return increment;
        }

        /**
         * Sets the Y position increment before rendering the next boss health bar
         *
         * @param increment the new increment
         */
        public void setIncrement(int increment)
        {
            this.increment = increment;
        }
    }

    /**
     * <p>Fired <b>before</b> textual information is rendered to the screen. <br/>
     * This can be used to add or remove text information.</p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the text will not be rendered; <em>this includes the debug overlay text<em/>. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class Text extends Pre
    {
        private final ArrayList<String> left;
        private final ArrayList<String> right;
        public Text(MatrixStack mStack, RenderGameOverlayEvent parent, ArrayList<String> left, ArrayList<String> right)
        {
            super(mStack, parent, ElementType.TEXT);
            this.left = left;
            this.right = right;
        }

        /**
         * @return the list of text to render on the left side
         */
        public ArrayList<String> getLeft()
        {
            return left;
        }

        /**
         * @return the list of text to render on the right side
         */
        public ArrayList<String> getRight()
        {
            return right;
        }
    }

    /**
     * <p>Fired <b>before</b> the chat messages overlay is rendered to the screen.</p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the chat messages overlay will not be rendered. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class Chat extends Pre
    {
        private int posX;
        private int posY;

        public Chat(MatrixStack mStack, RenderGameOverlayEvent parent, int posX, int posY)
        {
            super(mStack, parent, ElementType.CHAT);
            this.setPosX(posX);
            this.setPosY(posY);
        }

        /**
         * @return the X position of the chat messages overlay
         */
        public int getPosX()
        {
            return posX;
        }

        /**
         * Sets the new X position for rendering the chat messages overlay
         *
         * @param posX the new X position
         */
        public void setPosX(int posX)
        {
            this.posX = posX;
        }

        /**
         * @return the Y position of the chat messages overlay
         */
        public int getPosY()
        {
            return posY;
        }

        /**
         * Sets the new Y position for rendering the chat messages overlay
         *
         * @param posY the new y position
         */
        public void setPosY(int posY)
        {
            this.posY = posY;
        }
    }
}
