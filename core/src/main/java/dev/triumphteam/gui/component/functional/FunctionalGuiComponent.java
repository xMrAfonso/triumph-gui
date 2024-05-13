/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.builder.BaseGuiBuilder;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Similar to a {@link GuiComponent} this component will take in states and render a component.
 * Unlike {@link GuiComponent} it is not meant to be extended upon and is only used by the {@link BaseGuiBuilder}.
 *
 * @param <P> The player type.
 * @param <I> The item type.
 */
public interface FunctionalGuiComponent<P, I> extends BaseFunctionalGuiComponent<P> {

    /**
     * A component render function.
     * The function inside works the same as a normal {@link ReactiveGuiComponent#render(GuiContainer, Object)} would.
     *
     * @param render The component render.
     */
    void render(final @NotNull FunctionalGuiComponentRender<@NotNull P, @NotNull I> render);
}
