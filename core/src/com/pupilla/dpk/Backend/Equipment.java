package com.pupilla.dpk.Backend;

import java.io.Serializable;

/**
 * Created by orzech on 31.08.2017.
 */

public class Equipment implements Serializable{

    public Item helmet, weapon, armor, shield, legs,amulet,ring;

    public Equipment() {
//
//
//        helmet = null;
//        armor = null;
//        shield = null;
//        legs = null;
//        weapon = null;
//
////
        helmet = new Item( Constants.eqFrostHelmet,1, Item.Type.helmet);
        weapon =  new Item (Constants.eqSpiritBlade,1, Item.Type.weapon);;
        armor = new Item (Constants.eqFrostArmor,1, Item.Type.armor);
        shield = new Item(Constants.eqIceShield,1,Item.Type.shield);
        legs = new Item(Constants.eqFrostLegs,1,Item.Type.legs);;
        amulet = new Item(Constants.eqAmuletOfSnow,1,Item.Type.amulet);;
        ring = new Item(Constants.eqCrystalRing,1,Item.Type.ring);;


    }
    public int[] getStatsBoostSum(){
        int atk=0;
        int def=0;
        int bonus=0;
        int health=0;
        if(helmet!=null){
            atk+=helmet.atk;
            def+=helmet.def;
            bonus+=helmet.dmgbonus;
            health+=helmet.healthbonus;
        }




        if(weapon!=null){
            atk+=weapon.atk;
            def+=weapon.def;
            bonus+=weapon.dmgbonus;
            health+=weapon.healthbonus;
        }

        if(armor!=null){
            atk+=armor.atk;
            def+=armor.def;
            bonus+=armor.dmgbonus;
            health+=armor.healthbonus;
        }

        if(shield!=null){
            atk+=shield.atk;
            def+=shield.def;
            bonus+=shield.dmgbonus;
            health+=shield.healthbonus;
        }

        if(legs!=null){
            atk+=legs.atk;
            def+=legs.def;
            bonus+=legs.dmgbonus;
            health+=legs.healthbonus;
        }

        if(amulet!=null){
            atk+=amulet.atk;
            def+=amulet.def;
            bonus+=amulet.dmgbonus;
            health+=amulet.healthbonus;
        }

        if(ring!=null){
            atk+=ring.atk;
            def+=ring.def;
            bonus+=ring.dmgbonus;
            health+=ring.healthbonus;
        }



        return new int[]{atk, def, bonus, health};
    }


}
