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
import org.bukkit.inventory.meta.SkullMeta;
import java.util.Arrays;


//ITEM HEAD BUILDER CLASS
public class ItemHeadBuilder {

    //VARIABLES

    //The item stack self
    private final ItemStack itemStack;

    //The item meta of the item stack
    private final ItemMeta itemMeta;


    //CONSTRUCTOR
    public ItemHeadBuilder(Material material, int amount){

        //Define variables
        itemStack = new ItemStack(material);
        itemStack.setAmount(amount);
        itemMeta = itemStack.getItemMeta();

    }


    //METHODS

    //Set the display name
    public ItemHeadBuilder setDisplayName(String displayName){
        itemMeta.setDisplayName(displayName);
        return this;
    }

    //Set the localized name
    public ItemHeadBuilder setLocalizedName(String localizedName){
        itemMeta.setLocalizedName(localizedName);
        return this;
    }

    //Set the item lore
    public ItemHeadBuilder setLore(String... lore){
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    //Set the item unbreakable
    public ItemHeadBuilder setUnbreakable(boolean unbreakable){
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    //Add item flags
    public ItemHeadBuilder addItemFlags(ItemFlag... itemFlags){
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    //Add an enchantment
    public ItemHeadBuilder addEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    //Set custom model data
    public ItemHeadBuilder setCustomModelData(int customModelData) {
        itemMeta.setCustomModelData(customModelData);
        return this;
    }

    //Add an attribute modifier
    public ItemHeadBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        itemMeta.addAttributeModifier(attribute, attributeModifier);
        return this;
    }

    //Set head
    public ItemHeadBuilder setHead(String name) {
        ((SkullMeta) itemMeta).setOwner(name);
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


    //GETTERS

    //The item stack self
    public ItemStack getItemStack() { return itemStack; }

    //The item meta of the item stack
    public ItemMeta getItemMeta() { return itemMeta; }

}
