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
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Fired on different events/actions when a {@link Screen} is active and visible. <br/>
 * See the various subclasses for listening to different events. </p>
 *
 * <p>These events are fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see GuiScreenEvent.InitGuiEvent
 * @see GuiScreenEvent.DrawScreenEvent
 * @see GuiScreenEvent.BackgroundDrawnEvent
 * @see GuiScreenEvent.PotionShiftEvent
 * @see GuiScreenEvent.MouseInputEvent
 * @see GuiScreenEvent.KeyboardKeyEvent
 * @author bspkrs
 */
@OnlyIn(Dist.CLIENT)
public class GuiScreenEvent extends Event
{
    private final Screen gui;

    public GuiScreenEvent(Screen gui)
    {
        this.gui = gui;
    }

    /**
     * @return the {@code Screen} that fired this event
     */
    public Screen getGui()
    {
        return gui;
    }

    /**
     * <p>Fired when a {@link Screen} is being initialized. <br/>
     * See the two subclasses for listening before and after the initialization. </p>
     *
     * @see InitGuiEvent.Pre
     * @see InitGuiEvent.Post
     */
    public static class InitGuiEvent extends GuiScreenEvent
    {
        private final Consumer<Widget> add;
        private final Consumer<Widget> remove;

        private final List<Widget> list;

        public InitGuiEvent(Screen gui, List<Widget> list, Consumer<Widget> add, Consumer<Widget> remove)
        {
            super(gui);
            this.list = Collections.unmodifiableList(list);
            this.add = add;
            this.remove = remove;
        }

        /**
         * @return unmodifiable view of list of widgets on the screen
         */
        public List<Widget> getWidgetList()
        {
            return list;
        }

        /**
         * Adds the given {@code Widget} to the screen.
         *
         * @param button the widget to add
         */
        public void addWidget(Widget button)
        {
            add.accept(button);
        }

        /**
         * Removes the given {@code Widget} from the screen.
         *
         * @param button the widget to remove
         */
        public void removeWidget(Widget button)
        {
            remove.accept(button);
        }

        /**
         * <p>Fired <b>before</b> the screen's overridable initialization method is fired. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the initialization method will not be called, and the widgets and children lists will not be cleared. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         */
        @Cancelable
        public static class Pre extends InitGuiEvent
        {
            public Pre(Screen gui, List<Widget> list, Consumer<Widget> add, Consumer<Widget> remove)
            {
                super(gui, list, add, remove);
            }
        }

        /**
         * <p>Fired <b>after</b> the screen's overridable initialization method is called. </p>
         *
         * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         */
        public static class Post extends InitGuiEvent
        {
            public Post(Screen gui, List<Widget> list, Consumer<Widget> add, Consumer<Widget> remove)
            {
                super(gui, list, add, remove);
            }
        }
    }


    /**
     * <p>Fired when a {@link Screen} is being drawn. <br/>
     * See the two subclasses for listening before and after drawing. </p>
     *
     * @see DrawScreenEvent.Pre
     * @see DrawScreenEvent.Post
     * @see ForgeHooksClient#drawScreen(Screen, MatrixStack, int, int, float)
     */
    public static class DrawScreenEvent extends GuiScreenEvent
    {
        private final MatrixStack mStack;
        private final int mouseX;
        private final int mouseY;
        private final float renderPartialTicks;

        public DrawScreenEvent(Screen gui, MatrixStack mStack, int mouseX, int mouseY, float renderPartialTicks)
        {
            super(gui);
            this.mStack = mStack;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.renderPartialTicks = renderPartialTicks;
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

        /**
         * @return the amount of partial render ticks
         */
        public float getRenderPartialTicks()
        {
            return renderPartialTicks;
        }

        /**
         * <p>Fired <b>before</b> the {@link Screen} is drawn. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen will not be drawn. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         */
        @Cancelable
        public static class Pre extends DrawScreenEvent
        {
            public Pre(Screen gui, MatrixStack mStack, int mouseX, int mouseY, float renderPartialTicks)
            {
                super(gui, mStack, mouseX, mouseY, renderPartialTicks);
            }
        }

        /**
         * <p>Fired <b>after</b> the {@link Screen} is drawn. </p>
         *
         * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         */
        public static class Post extends DrawScreenEvent
        {
            public Post(Screen gui, MatrixStack mStack, int mouseX, int mouseY, float renderPartialTicks)
            {
                super(gui, mStack, mouseX, mouseY, renderPartialTicks);
            }
        }
    }

    /**
     * <p>Fired directly after the background of the {@code Screen} is drawn. <br/>
     * Can be used for drawing above the background but below the tooltips. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class BackgroundDrawnEvent extends GuiScreenEvent
    {
        private final MatrixStack mStack;

        public BackgroundDrawnEvent(Screen gui, MatrixStack mStack)
        {
            super(gui);
            this.mStack = mStack;
        }

        /**
         * @return the matrix stack used for rendering
         */
        public MatrixStack getMatrixStack()
        {
            return mStack;
        }
    }

    /**
     * <p>Fired when there are potion effects and the {@code Screen} is being shifted. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If the event is cancelled, the screen will be prevented from shifting. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    @Cancelable
    public static class PotionShiftEvent extends GuiScreenEvent
    {
        public PotionShiftEvent(Screen gui)
        {
            super(gui);
        }
    }

    /**
     * <p>Fired whenever an action is performed by the mouse. <br/>
     * See the various subclasses to listen for different actions. </p>
     *
     * @see GuiScreenEvent.MouseClickedEvent
     * @see GuiScreenEvent.MouseReleasedEvent
     * @see GuiScreenEvent.MouseDragEvent
     * @see GuiScreenEvent.MouseScrollEvent
     */
    public static abstract class MouseInputEvent extends GuiScreenEvent
    {
        private final double mouseX;
        private final double mouseY;

        public MouseInputEvent(Screen gui, double mouseX, double mouseY)
        {
            super(gui);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        /**
         * @return the X position of the mouse cursor, relative to the screen
         */
        public double getMouseX()
        {
            return mouseX;
        }

        /**
         * @return the Y position of the mouse cursor, relative to the screen
         */
        public double getMouseY()
        {
            return mouseY;
        }
    }

    /**
     * <p>Fired when a mouse button is clicked. <br/>
     * See the two subclasses for listening before and after the normal handling. </p>
     *
     * @see MouseClickedEvent.Pre
     * @see MouseClickedEvent.Post
     */
    public static abstract class MouseClickedEvent extends MouseInputEvent
    {
        private final int button;

        public MouseClickedEvent(Screen gui, double mouseX, double mouseY, int button)
        {
            super(gui, mouseX, mouseY);
            this.button = button;
        }

        /**
         * @return the key code of the mouse button that was clicked
         */
        public int getButton()
        {
            return button;
        }

        /**
         * <p>Fired <b>before</b> the mouse click is handled by the screen. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen's mouse click handler will be bypassed
         * and the corresponding {@link MouseClickedEvent.Post} will not be fired. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseClickedPre(Screen, double, double, int)
         */
        @Cancelable
        public static class Pre extends MouseClickedEvent
        {
            public Pre(Screen gui, double mouseX, double mouseY, int button)
            {
                super(gui, mouseX, mouseY, button);
            }
        }

        /**
         * <p>Fired <b>after</b> the mouse click is handled, if not handled by the screen
         * and the corresponding {@link MouseClickedEvent.Pre} is not cancelled. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the mouse click will be set as handled. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseClickedPost(Screen, double, double, int)
         */
        @Cancelable
        public static class Post extends MouseClickedEvent
        {
            public Post(Screen gui, double mouseX, double mouseY, int button)
            {
                super(gui, mouseX, mouseY, button);
            }
        }
    }

    /**
     * <p>Fired when a mouse button is released. <br/>
     * See the two subclasses for listening before and after the normal handling. </p>
     *
     * @see MouseReleasedEvent.Pre
     * @see MouseReleasedEvent.Post
     */
    public static abstract class MouseReleasedEvent extends MouseInputEvent
    {
        private final int button;

        public MouseReleasedEvent(Screen gui, double mouseX, double mouseY, int button)
        {
            super(gui, mouseX, mouseY);
            this.button = button;
        }

        /**
         * @return the key code of the mouse button that was released
         */
        public int getButton()
        {
            return button;
        }

        /**
         * <p>Fired <b>before</b> the mouse release is handled by the screen. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen's mouse release handler will be bypassed
         * and the corresponding {@link MouseReleasedEvent.Post} will not be fired. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseReleasedPre(Screen, double, double, int)
         */
        @Cancelable
        public static class Pre extends MouseReleasedEvent
        {
            public Pre(Screen gui, double mouseX, double mouseY, int button)
            {
                super(gui, mouseX, mouseY, button);
            }
        }

        /**
         * <p>Fired <b>after</b> the mouse release is handled, if not handled by the screen
         * and the corresponding {@link MouseReleasedEvent.Pre} is not cancelled. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the mouse release will be set as handled. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseReleasedPost(Screen, double, double, int)
         */
        @Cancelable
        public static class Post extends MouseReleasedEvent
        {
            public Post(Screen gui, double mouseX, double mouseY, int button)
            {
                super(gui, mouseX, mouseY, button);
            }
        }
    }

    /**
     * <p>Fired when the mouse was dragged while a button is being held down. <br/>
     * See the two subclasses for listening before and after the normal handling. </p>
     *
     * @see MouseDragEvent.Pre
     * @see MouseDragEvent.Post
     */
    public static abstract class MouseDragEvent extends MouseInputEvent
    {
        private final int mouseButton;
        private final double dragX;
        private final double dragY;

        public MouseDragEvent(Screen gui, double mouseX, double mouseY, int mouseButton, double dragX, double dragY)
        {
            super(gui, mouseX, mouseY);
            this.mouseButton = mouseButton;
            this.dragX = dragX;
            this.dragY = dragY;
        }

        /**
         * @return the key code of the mouse button that was released
         */
        public int getMouseButton()
        {
            return mouseButton;
        }

        /**
         * @return amount of mouse drag along the {@code x} axis
         */
        public double getDragX()
        {
            return dragX;
        }

        /**
         * @return amount of mouse drag along the {@code y} axis
         */
        public double getDragY()
        {
            return dragY;
        }

        /**
         * <p>Fired <b>before</b> the mouse drag is handled by the screen. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen's mouse drag handler will be bypassed
         * and the corresponding {@link MouseDragEvent.Post} will not be fired. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseDragPre(Screen, double, double, int, double, double)
         */
        @Cancelable
        public static class Pre extends MouseDragEvent
        {
            public Pre(Screen gui, double mouseX, double mouseY, int mouseButton, double dragX, double dragY)
            {
                super(gui, mouseX, mouseY, mouseButton, dragX, dragY);
            }
        }

        /**
         * Fired <b>after</b> the mouse drag is handled, if not handled by the screen
         * and the corresponding {@link MouseDragEvent.Pre} is not cancelled.
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the mouse drag will be set as handled. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseDragPost(Screen, double, double, int, double, double)
         */
        @Cancelable
        public static class Post extends MouseDragEvent
        {
            public Post(Screen gui, double mouseX, double mouseY, int mouseButton, double dragX, double dragY)
            {
                super(gui, mouseX, mouseY, mouseButton, dragX, dragY);
            }
        }
    }

    /**
     * <p>Fired when the mouse was dragged while a button is being held down. <br/>
     * See the two subclasses for listening before and after the normal handling. </p>
     *
     * @see MouseScrollEvent.Pre
     * @see MouseScrollEvent.Post
     */
    public static abstract class MouseScrollEvent extends MouseInputEvent
    {
        private final double scrollDelta;

        public MouseScrollEvent(Screen gui, double mouseX, double mouseY, double scrollDelta)
        {
            super(gui, mouseX, mouseY);
            this.scrollDelta = scrollDelta;
        }

        /**
         * @return the amount of change / delta of the mouse scroll
         */
        public double getScrollDelta()
        {
            return scrollDelta;
        }

        /**
         * <p>Fired <b>before</b> the mouse scroll is handled by the screen. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen's mouse scroll handler will be bypassed
         * and the corresponding {@link MouseScrollEvent.Post} will not be fired. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseScrollPre(MouseHelper, Screen, double)
         */
        @Cancelable
        public static class Pre extends MouseScrollEvent
        {
            public Pre(Screen gui, double mouseX, double mouseY, double scrollDelta)
            {
                super(gui, mouseX, mouseY, scrollDelta);
            }
        }

        /**
         * <p>Fired <b>after</b> the mouse scroll is handled, if not handled by the screen
         * and the corresponding {@link MouseScrollEvent.Pre} is not cancelled. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the mouse scroll will be set as handled. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiMouseScrollPost(MouseHelper, Screen, double)
         */
        @Cancelable
        public static class Post extends MouseScrollEvent
        {
            public Post(Screen gui, double mouseX, double mouseY, double scrollDelta)
            {
                super(gui, mouseX, mouseY, scrollDelta);
            }
        }
    }

    /**
     * <p>Fired whenever a keyboard key is pressed or released. <br/>
     * See the various subclasses to listen for key pressing or releasing. </p>
     *
     * @see GuiScreenEvent.KeyboardKeyPressedEvent
     * @see GuiScreenEvent.KeyboardKeyReleasedEvent
     * @see InputMappings#getInputByCode(int, int)
     * @see <a href="https://www.glfw.org/docs/latest/input_guide.html#input_key" target="_top">the online GLFW documentation</a>
     */
    public static abstract class KeyboardKeyEvent extends GuiScreenEvent
    {
        private final int keyCode;
        private final int scanCode;
        private final int modifiers;

        public KeyboardKeyEvent(Screen gui, int keyCode, int scanCode, int modifiers)
        {
            super(gui);
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.modifiers = modifiers;
        }

        /**
         * @return {@code GLFW} (platform-agnostic) key code
         *
         * @see GLFW key constants starting with 'GLFW_KEY_'
         * @see <a href="https://www.glfw.org/docs/latest/group__keys.html" target="_top">the online GLFW documentation</a>
         */
        public int getKeyCode()
        {
            return keyCode;
        }

        /**
         * <p>Returns the platform-specific scan code. </p>
         *
         * <p>The scan code is unique for every key, regardless of whether it has a key code. <br/>
         * Scan codes are platform-specific but consistent over time, so keys will have different scan codes depending
         * on the platform but they are safe to save to disk as custom key bindings. </p>
         *
         * @return platform-specific scan code
         */
        public int getScanCode()
        {
            return scanCode;
        }

        /**
         * @return bit field representing the active modifier keys
         *
         * @see GLFW#GLFW_MOD_SHIFT SHIFT modifier key bit
         * @see GLFW#GLFW_MOD_CONTROL CTRL modifier key bit
         * @see GLFW#GLFW_MOD_ALT ALT modifier key bit
         * @see GLFW#GLFW_MOD_SUPER SUPER modifier key bit
         * @see GLFW#GLFW_KEY_CAPS_LOCK CAPS LOCK modifier key bit
         * @see GLFW#GLFW_KEY_NUM_LOCK NUM LOCK modifier key bit
         * @see <a href="https://www.glfw.org/docs/latest/group__mods.html" target="_top">the online GLFW documentation</a>
         */
        public int getModifiers()
        {
            return modifiers;
        }
    }

    /**
     * <p>Fired when a keyboard key is pressed. <br/>
     * See the two subclasses for listening before and after the normal handling. </p>
     *
     * @see KeyboardKeyPressedEvent.Pre
     * @see KeyboardKeyPressedEvent.Post
     */
    public static abstract class KeyboardKeyPressedEvent extends KeyboardKeyEvent
    {
        public KeyboardKeyPressedEvent(Screen gui, int keyCode, int scanCode, int modifiers)
        {
            super(gui,  keyCode, scanCode, modifiers);
        }

        /**
         * <p>Fired <b>before</b> the key press is handled by the screen. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable} and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen's key press handler will be bypassed
         * and the corresponding {@link KeyboardKeyPressedEvent.Post} will not be fired. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiKeyPressedPre(Screen, int, int, int)
         */
        @Cancelable
        public static class Pre extends KeyboardKeyPressedEvent
        {
            public Pre(Screen gui, int keyCode, int scanCode, int modifiers)
            {
                super(gui, keyCode, scanCode, modifiers);
            }
        }

        /**
         * <p>Fired <b>after</b> the key press is handled, if not handled by the screen
         * and the corresponding {@link KeyboardKeyPressedEvent.Pre} is not cancelled. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the key press will be set as handled. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiKeyPressedPost(Screen, int, int, int)
         */
        @Cancelable
        public static class Post extends KeyboardKeyPressedEvent
        {
            public Post(Screen gui, int keyCode, int scanCode, int modifiers)
            {
                super(gui, keyCode, scanCode, modifiers);
            }
        }
    }

    /**
     * <p>Fired when a keyboard key is released. <br/>
     * See the two subclasses for listening before and after the normal handling. </p>
     *
     * @see KeyboardKeyReleasedEvent.Pre
     * @see KeyboardKeyReleasedEvent.Post
     */
    public static abstract class KeyboardKeyReleasedEvent extends KeyboardKeyEvent
    {
        public KeyboardKeyReleasedEvent(Screen gui, int keyCode, int scanCode, int modifiers)
        {
            super(gui, keyCode, scanCode, modifiers);
        }

        /**
         * <p>Fired <b>before</b> the key release is handled by the screen. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen's key release handler will be bypassed
         * and the corresponding {@link KeyboardKeyReleasedEvent.Post} will not be fired. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiKeyPressedPost(Screen, int, int, int)
         */
        @Cancelable
        public static class Pre extends KeyboardKeyReleasedEvent
        {
            public Pre(Screen gui, int keyCode, int scanCode, int modifiers)
            {
                super(gui, keyCode, scanCode, modifiers);
            }
        }

        /**
         * <p>Fired <b>after</b> the key release is handled, if not handled by the screen
         * and the corresponding {@link KeyboardKeyReleasedEvent.Pre} is not cancelled. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the key release will be set as handled. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiKeyReleasedPost(Screen, int, int, int)
         */
        @Cancelable
        public static class Post extends KeyboardKeyReleasedEvent
        {
            public Post(Screen gui, int keyCode, int scanCode, int modifiers)
            {
                super(gui, keyCode, scanCode, modifiers);
            }
        }
    }

    /**
     * <p>Fired when a keyboard key corresponding to a character is typed. <br/>
     * See the two subclasses for listening before and after the normal handling. </p>
     *
     * @see KeyboardCharTypedEvent.Pre
     * @see KeyboardCharTypedEvent.Post
     * @see <a href="https://www.glfw.org/docs/latest/input_guide.html#input_char" target="_top">the online GLFW documentation</a>
     */
    public static class KeyboardCharTypedEvent extends GuiScreenEvent
    {
        private final char codePoint;
        private final int modifiers;

        public KeyboardCharTypedEvent(Screen gui, char codePoint, int modifiers)
        {
            super(gui);
            this.codePoint = codePoint;
            this.modifiers = modifiers;
        }

        /**
         * @return the character code point typed
         */
        public char getCodePoint()
        {
            return codePoint;
        }

        /**
         * @return bit field representing the active modifier keys
         *
         * @see GLFW#GLFW_MOD_SHIFT SHIFT modifier key bit
         * @see GLFW#GLFW_MOD_CONTROL CTRL modifier key bit
         * @see GLFW#GLFW_MOD_ALT ALT modifier key bit
         * @see GLFW#GLFW_MOD_SUPER SUPER modifier key bit
         * @see <a href="https://www.glfw.org/docs/latest/group__mods.html" target="_top">the online GLFW documentation</a>
         */
        public int getModifiers()
        {
            return modifiers;
        }

        /**
         * <p>Fired <b>before</b> the character input is handled by the screen. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the screen's character input handler will be bypassed
         * and the corresponding {@link KeyboardCharTypedEvent.Post} will not be fired. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see KeyboardCharTypedEvent <em>the superclass for event firing information</em>
         * @see ForgeHooksClient#onGuiCharTypedPre(Screen, char, int)
         */
        @Cancelable
        public static class Pre extends KeyboardCharTypedEvent
        {
            public Pre(Screen gui, char codePoint, int modifiers)
            {
                super(gui, codePoint, modifiers);
            }
        }

        /**
         * <p>Fired <b>after</b> the character input is handled, if not handled by the screen
         * and the corresponding {@link KeyboardCharTypedEvent.Pre} is not cancelled. </p>
         *
         * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
         * If the event is cancelled, the character input will be set as handled. </p>
         *
         * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
         * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
         *
         * @see ForgeHooksClient#onGuiCharTypedPost(Screen, char, int)
         */
        @Cancelable
        public static class Post extends KeyboardCharTypedEvent
        {
            public Post(Screen gui, char codePoint, int modifiers)
            {
                super(gui, codePoint, modifiers);
            }
        }
    }
}
