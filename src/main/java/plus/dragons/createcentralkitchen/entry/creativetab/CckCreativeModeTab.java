package plus.dragons.createcentralkitchen.entry.creativetab;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.entry.item.FRItemEntries;
import plus.dragons.createcentralkitchen.entry.item.MDItemEntries;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CckCreativeModeTab extends CreativeModeTab {

    public static final ItemEntry<Item> ICON = REGISTRATE.item("creative_tab_icon", Item::new)
            .lang("CreativeTabIcon")
            .properties(prop -> prop.stacksTo(1))
            .register();

    public CckCreativeModeTab() {
        super("base");
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("CCK");
    }

    @Override
    public ItemStack makeIcon() {
        return ICON.asStack();
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> pItems) {
        List<Item> items = new ArrayList<>();
        items.addAll(this.collectItems());
        items.addAll(this.collectBlocks());
        filterAndOutput(pItems,items);
    }

    private List<Item> collectBlocks() {
        List<Item> items = new ReferenceArrayList<>();
        Iterator var3 = REGISTRATE.getAll(ForgeRegistries.BLOCKS.getRegistryKey()).iterator();

        while(var3.hasNext()) {
            RegistryEntry<Block> entry = (RegistryEntry)var3.next();
            Item item = entry.get().asItem();
            if (item != Items.AIR) {
                items.add(item);
            }
        }

        items = new ReferenceArrayList(new ReferenceLinkedOpenHashSet(items));
        return items;
    }

    private List<Item> collectItems() {
        List<Item> items = new ReferenceArrayList<>();
        Iterator var3 = REGISTRATE.getAll(ForgeRegistries.ITEMS.getRegistryKey()).iterator();

        while(var3.hasNext()) {
            RegistryEntry<Item> entry = (RegistryEntry)var3.next();
            Item item = entry.get();
            if (!(item instanceof BlockItem)) {
                items.add(item);
            }
        }

        return items;
    }

    private static void filterAndOutput(NonNullList<ItemStack> pItems, List<Item> items) {
        Iterator var4 = items.iterator();
        while(var4.hasNext()) {
            Item item = (Item)var4.next();
            if(item.toString().contains("incomplete")) continue;
            if(item.toString().contains("guide")) continue;
            if(item.toString().contains("blaze_burner")) continue;
            if(item.toString().contains("creative_tab_icon")) continue;
            pItems.add(item.getDefaultInstance());
        }
        if (Mods.isLoaded(Mods.FD))
            pItems.add(FDItemEntries.COOKING_GUIDE.asStack());
        if (Mods.isLoaded(Mods.FR))
            pItems.add(FRItemEntries.BREWING_GUIDE.asStack());
        if (Mods.isLoaded(Mods.MD))
            pItems.add(MDItemEntries.MINERS_COOKING_GUIDE.asStack());
    }
}
