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

import net.minecraft.client.MouseHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Hand;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import net.minecraftforge.fml.LogicalSide;
import org.lwjgl.glfw.GLFW;

/**
 * <p>Fired when an input is detected from the user's input devices. <br/>
 * See the various subclasses to listen for specific devices and inputs. </p>
 *
 * @see InputEvent.RawMouseEvent
 * @see InputEvent.MouseInputEvent
 * @see InputEvent.MouseScrollEvent
 * @see InputEvent.KeyInputEvent
 * @see InputEvent.ClickInputEvent
 */
public class InputEvent extends Event
{
    /**
     * <p>Fired when a mouse button is clicked, <b>before</b> being processed by vanilla. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If the event is cancelled, then the mouse event will not be processed by vanilla (e.g. keybinds and screens) </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onRawMouseClicked(int, int, int)
     * @see <a href="https://www.glfw.org/docs/latest/input_guide.html#input_mouse_button" target="_top">the online GLFW documentation</a>
     */
    @Cancelable
    public static class RawMouseEvent extends InputEvent
    {
        private final int button;
        private final int action;
        private final int mods;

        public RawMouseEvent(int button, int action, int mods)
        {
            this.button = button;
            this.action = action;
            this.mods = mods;
        }

        /**
         * @return the mouse button's input code
         *
         * @see GLFW mouse constants starting with 'GLFW_MOUSE_BUTTON_'
         * @see <a href="https://www.glfw.org/docs/latest/group__buttons.html" target="_top">the online GLFW documentation</a>
         */
        public int getButton()
        {
            return this.button;
        }

        /**
         * @return the mouse button's action
         *
         * @see GLFW#GLFW_PRESS
         * @see GLFW#GLFW_RELEASE
         */
        public int getAction()
        {
            return this.action;
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
        public int getMods()
        {
            return this.mods;
        }
    }

    /**
     * <p>Fired when a mouse button is clicked, <b>after</b> processing. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#fireMouseInput(int, int, int)
     * @see <a href="https://www.glfw.org/docs/latest/input_guide.html#input_mouse_button" target="_top">the online GLFW documentation</a>
     */
    public static class MouseInputEvent extends InputEvent
    {
        private final int button;
        private final int action;
        private final int mods;
        public MouseInputEvent(int button, int action, int mods)
        {
            this.button = button;
            this.action = action;
            this.mods = mods;
        }

        /**
         * @return the mouse button's input code
         *
         * @see GLFW mouse constants starting with 'GLFW_MOUSE_BUTTON_'
         * @see <a href="https://www.glfw.org/docs/latest/group__buttons.html" target="_top">the online GLFW documentation</a>
         */
        public int getButton()
        {
            return this.button;
        }

        /**
         * @return the mouse button's action
         *
         * @see GLFW#GLFW_PRESS
         * @see GLFW#GLFW_RELEASE
         */
        public int getAction()
        {
            return this.action;
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
        public int getMods()
        {
            return this.mods;
        }
    }

    /**
     * <p>Fired when a mouse scroll wheel is used outside of a screen and a player is loaded,
     * <b>before</b> being processed by vanilla. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If the event is cancelled, then the mouse scroll event will not be processed further. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onMouseScroll(MouseHelper, double)
     * @see <a href="https://www.glfw.org/docs/latest/input_guide.html#input_mouse_button" target="_top">the online GLFW documentation</a>
     */
    @Cancelable
    public static class MouseScrollEvent extends InputEvent
    {
        private final double scrollDelta;
        private final double mouseX;
        private final double mouseY;
        private final boolean leftDown;
        private final boolean middleDown;
        private final boolean rightDown;
        public MouseScrollEvent(double scrollDelta, boolean leftDown, boolean middleDown, boolean rightDown, double mouseX, double mouseY)
        {
            this.scrollDelta = scrollDelta;
            this.leftDown = leftDown;
            this.middleDown = middleDown;
            this.rightDown = rightDown;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        /**
         * @return the amount of change / delta of the mouse scroll
         */
        public double getScrollDelta()
        {
            return this.scrollDelta;
        }

        /**
         * @return if the left mouse button is pressed
         */
        public boolean isLeftDown()
        {
            return this.leftDown;
        }

        /**
         * @return if the right mouse button is pressed
         */
        public boolean isRightDown()
        {
            return this.rightDown;
        }

        /**
         * @return if the middle mouse button is pressed
         */
        public boolean isMiddleDown()
        {
            return this.middleDown;
        }

        /**
         * @return the X position of the mouse cursor
         */
        public double getMouseX()
        {
            return this.mouseX;
        }

        /**
         * @return the Y position of the mouse cursor
         */
        public double getMouseY()
        {
            return this.mouseY;
        }
    }

    /**
     * <p>Fired when a keyboard key input occurs, such as pressing, releasing, or repeating a key.</p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#fireKeyInput(int, int, int, int)
     * @see InputMappings#getInputByCode(int, int)
     */
    public static class KeyInputEvent extends InputEvent
    {
        private final int key;
        private final int scanCode;
        private final int action;
        private final int modifiers;
        public KeyInputEvent(int key, int scanCode, int action, int modifiers)
        {
            this.key = key;
            this.scanCode = scanCode;
            this.action = action;
            this.modifiers = modifiers;
        }

        /**
         * @return {@code GLFW} (platform-agnostic) key code
         *
         * @see GLFW key constants starting with 'GLFW_KEY_'
         * @see <a href="https://www.glfw.org/docs/latest/group__keys.html" target="_top">the online GLFW documentation</a>
         */
        public int getKey()
        {
            return this.key;
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
            return this.scanCode;
        }

        /**
         * @return the keyboard key's action
         *
         * @see GLFW#GLFW_PRESS
         * @see GLFW#GLFW_RELEASE
         * @see GLFW#GLFW_REPEAT
         */
        public int getAction()
        {
            return this.action;
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
            return this.modifiers;
        }
    }

    /**
     * <p>Fired when a keybinding that by default involves clicking the mouse buttons is triggered. </p>
     *
     * <p>The key bindings that trigger this event are:
     * <ul>
     *     <li><b>Use Item</b> - defaults to <em>left mouse click</em></li>
     *     <li><b>Pick Block</b> - defaults to <em>middle mouse click</em></li>
     *     <li><b>Attack</b> - defaults to <em>right mouse click</em></li>
     * </ul></p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the keybind's action is not processed further, and the hand will be swung
     * according to {@link #shouldSwingHand()}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onClickInput(int, KeyBinding, Hand)
     */
    @Cancelable
    public static class ClickInputEvent extends InputEvent
    {
        private final int button;
        private final KeyBinding keyBinding;
        private final Hand hand;
        private boolean handSwing = true;
        public ClickInputEvent(int button, KeyBinding keyBinding, Hand hand)
        {
            this.button = button;
            this.keyBinding = keyBinding;
            this.hand = hand;
        }

        /**
         * Sets whether to swing the hand. <br/>
         * This takes effect even if the event is cancelled.
         *
         * @param value whether to swing the hand
         */
        public void setSwingHand(boolean value)
        {
            handSwing = value;
        }

        /**
         * @return whether to swing the hand; takes effect even when cancelled
         */
        public boolean shouldSwingHand()
        {
            return handSwing;
        }

        /**
         * <p>The hand which is causing the event to get triggered. </p>
         *
         * <p>The event will be called for both hands if this is a use item input regardless
         * of both event's cancellation. <br/>
         * Will always be {@link Hand#MAIN_HAND} if this is an attack or pick block input. </p>
         *
         * @return the hand causing the input
         */
        public Hand getHand()
        {
            return hand;
        }

        /**
         * @return if the mouse button is the left mouse button
         */
        public boolean isAttack()
        {
            return button == 0;
        }

        /**
         * @return if the mouse button is the right mouse button
         */
        public boolean isUseItem()
        {
            return button == 1;
        }

        /**
         * @return if the mouse button is the middle mouse button
         */
        public boolean isPickBlock()
        {
            return button == 2;
        }

        /**
         * @return the keybinding triggering this event
         */
        public KeyBinding getKeyBinding()
        {
            return keyBinding;
        }
    }
}
