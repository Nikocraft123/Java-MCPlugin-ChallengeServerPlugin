//PACKAGE
package de.nikocraft.challengeserver.utils;


//IMPORTS
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;


//ITEM BUILDER CLASS
public class ItemBuilder {

    //VARIABLES

    //The item stack self
    private final ItemStack itemStack;

    //The item meta of the item stack
    private final ItemMeta itemMeta;


    //CONSTRUCTOR
    public ItemBuilder(Material material, int amount){

        //Define variables
        itemStack = new ItemStack(material);
        itemStack.setAmount(amount);
        itemMeta = itemStack.getItemMeta();

    }


    //METHODS

    //Set the display name
    public ItemBuilder setDisplayName(String displayName){
        itemMeta.setDisplayName(displayName);
        return this;
    }

    //Set the localized name
    public ItemBuilder setLocalizedName(String localizedName){
        itemMeta.setLocalizedName(localizedName);
        return this;
    }

    //Set the item lore
    public ItemBuilder setLore(String... lore){
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    //Set the item unbreakable
    public ItemBuilder setUnbreakable(boolean unbreakable){
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    //Add item flags
    public ItemBuilder addItemFlags(ItemFlag... itemFlags){
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    //Add an enchantment
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    //Set custom model data
    public ItemBuilder setCustomModelData(int customModelData) {
        itemMeta.setCustomModelData(customModelData);
        return this;
    }

    //Add an attribute modifier
    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        itemMeta.addAttributeModifier(attribute, attributeModifier);
        return this;
    }

    //To string
    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta + ", " +
                "itemStack=" + itemStack + '}';
    }

    //Build the item stack
    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
