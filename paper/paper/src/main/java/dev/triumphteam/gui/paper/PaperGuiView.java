/**
 * MIT License
 * <p>
 * Copyright (c) 2024 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.actions.GuiCloseAction;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.processor.ClickProcessor;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import dev.triumphteam.gui.title.GuiTitle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class PaperGuiView extends AbstractGuiView<Player, ItemStack> implements InventoryHolder {

    private final PaperContainerType containerType;
    private Inventory inventory = null;

    public PaperGuiView(
        final @NotNull Player player,
        final @NotNull GuiTitle title,
        final @NotNull PaperContainerType containerType,
        final @NotNull List<GuiComponent<Player, ItemStack>> components,
        final @NotNull List<GuiCloseAction> closeActions,
        final @NotNull GuiComponentRenderer<Player, ItemStack> componentRenderer,
        final @NotNull ClickHandler<Player> clickHandler,
        final long spamPreventionDuration
    ) {
        super(player, title, components, closeActions, containerType, componentRenderer, clickHandler, new ClickProcessor<>(spamPreventionDuration));
        this.containerType = containerType;
    }

    @Override
    public void openInventory(final boolean updating) {
        if (inventory == null) {
            this.inventory = containerType.createInventory(this, getTitle());
        }

        final var viewer = viewer();
        viewer.getScheduler().run(PaperGuiSettings.get().getPlugin(), (task) -> {
            setUpdating(true);
            viewer.openInventory(inventory);
            setUpdating(false);
        }, null);
    }

    @Override
    public void close() {
        final var viewer = viewer();
        viewer.getScheduler().runDelayed(PaperGuiSettings.get().getPlugin(), (task) -> viewer.closeInventory(), null, 2L);
    }

    @Override
    public @NotNull Inventory getInventory() {
        checkInventory();
        return inventory;
    }

    @Override
    protected void clearSlot(final int slot) {
        checkInventory();
        inventory.clear(slot);
    }

    @Override
    public @NotNull String viewerName() {
        return viewer().getName();
    }

    @Override
    public @NotNull UUID viewerUuid() {
        return viewer().getUniqueId();
    }

    @Override
    protected void populateInventory(final @NotNull Map<Integer, RenderedGuiItem<Player, ItemStack>> renderedItems) {
        renderedItems.forEach((slot, item) -> inventory.setItem(slot, item.item()));
    }

    private void checkInventory() throws TriumphGuiException {
        if (inventory == null) {
            throw new TriumphGuiException("Tried to get inventory before it was available.");
        }
    }
}
