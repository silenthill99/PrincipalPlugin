package fr.silenthill99.principalplugin.inventory.hook;

import fr.silenthill99.principalplugin.ItemBuilder;
import fr.silenthill99.principalplugin.inventory.AbstractInventory;
import fr.silenthill99.principalplugin.inventory.holder.TestHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TestInventory extends AbstractInventory<TestHolder> {
    public TestInventory() {
        super(TestHolder.class);
    }

    @Override
    public void openInventory(Player p, Object... args)
    {
        TestHolder holder = new TestHolder();
        Inventory inv =  createInventory(holder, 9, "Test");
        inv.setItem(0, CLOSE);
        int slot = 1;
        for (Laine laine : Laine.values())
        {
            holder.laine.put(slot, laine);
            inv.setItem(slot++, new ItemBuilder(Material.WHITE_WOOL).toItemStack());
        }
        p.openInventory(inv);
    }

    @Override
    public void manageInventory(InventoryClickEvent e, ItemStack current, Player player, TestHolder holder) {
        Laine laine = holder.laine.get(e.getSlot());
        switch (current.getType())
        {
            case WHITE_WOOL:
                player.sendMessage(laine.getName());
                break;
            default:
                break;
        }
    }

    public enum Laine
    {
        LAINE_1("Essai 1");

        private final String name;
        Laine(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return this.name;
        }
    }
}
