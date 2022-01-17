package com.pupilla.dpk.Backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by orzech on 19.12.2017.
 */

public class Constants {
    public static final short BIT_PLAYER = 1;
    public static final short BIT_WALL = 2;
    public static final short BIT_HAUSE = 3;
    public static final short BIT_ITEM = 4;
    public static final short BIT_COLLECTED = 8;
    public static final short BIT_NPC = 16;
    public static final short BIT_ENEMY = 32;
    public static final short BIT_PORTAL = 64;

    public static final int UNIT_SCALE = 32;

    //skin
    public static final String skin = "data/skin/skin.json";
    public static final String font = "data/skin/worksans.fnt";

    // Textures constants
    public static final String backArrow = "sprites/others/backarrow.png";
    public static final String healthBar = "sprites/others/health.png";
    public static final String attackBtn = "sprites/others/attackbutton.png";
    public static final String dialogueBtn = "sprites/others/dialoguebutton.png";
    public static final String swapBtn = "sprites/others/swapbutton.png";
    public static final String doors = "sprites/others/doors.png";

    public static final String steelSword = "sprites/items/weapons/weapon1.png";
    public static final String spear = "sprites/items/weapons/weapon2.png";
    public static final String halberd = "sprites/items/weapons/weapon3.png";
    public static final String hatchet = "sprites/items/weapons/weapon4.png";
    public static final String mace = "sprites/items/weapons/weapon5.png";
    public static final String sabre = "sprites/items/weapons/weapon6.png";
    public static final String warAxe = "sprites/items/weapons/weapon7.png";

    public static final String cometSword = "sprites/items/weapons/CometSword.png";
    public static final String tempestRod = "sprites/items/weapons/TempestRod.png";
    public static final String emberRune = "sprites/items/weapons/EmberRune.png";
    public static final String volcanicAxe = "sprites/items/weapons/VolcanicAxe.png";

    public static final String leatherLegs = "sprites/items/legs/LeatherLegs.png";
    public static final String leatherArmor = "sprites/items/armors/LeatherArmour.png";
    public static final String roundShield = "sprites/items/shields/RoundShield.png";
    public static final String battleHelmet =  "sprites/items/helmets/BattleHelmet.png";


    //AMULETS
    public static final String talisman = "sprites/items/amulets/Talisman.png";
    public static final String amuletOfSnow = "sprites/items/amulets/AmuletOfSnow.png";
    public static final String crystalChain = "sprites/items/amulets/CrystalChain.png";

    //SWORDS

    public static final String fireSword = "sprites/items/weapons/FireSword.png";
    public static final String spiritBlade= "sprites/items/weapons/SpiritBlade.png";
    public static final String dragonSlayer = "sprites/items/weapons/DragonSlayer.png";
    public static final String iceDiscus = "sprites/items/weapons/IceDiscus.png";

    //LEGS
    public static final String brassLegs = "sprites/items/legs/BrassLegs.png";
    public static final String frostLegs ="sprites/items/legs/FrostLegs.png";


    //Armors
    public static final String frostArmor = "sprites/items/armors/FrostArmour.png";
    public static final String gown = "sprites/items/armors/Gown.png";


    //Shields
    public static final String iceShield = "sprites/items/shields/IceShield.png";


    //Helmets
    public static final String frostHelmet =  "sprites/items/helmets/FrostHelmet.png";

    //Rings
    public static final String crystalRing ="sprites/items/rings/CrystalRing.png";



    public static final String woodenShield = "sprites/items/shields/shield1.png";
    public static final String steelShield = "sprites/items/shields/shield2.png";
    public static final String emeraldShield = "sprites/items/shields/shield3.png";
    public static final String guardianShield = "sprites/items/shields/shield4.png";
    public static final String royalShield = "sprites/items/shields/shield5.png";

    public static final String emeraldArmor = "sprites/items/armors/a2.png";
    public static final String royalArmor = "sprites/items/armors/a3.png";

    public static final String knightLegs = "sprites/items/legs/l2.png";
    public static final String emeraldLegs = "sprites/items/legs/l3.png";

    public static final String leatherHelmet = "sprites/items/helmets/h1.png";
    public static final String knightHelmet = "sprites/items/helmets/h2.png";
    public static final String emeraldHelmet = "sprites/items/helmets/h3.png";
    public static final String vikingHelmet = "sprites/items/helmets/h4.png";




    // items name

    //  SWORDS NAME
    public static final String eqCometSword = "Comet Sword";
    public static final String eqFireSword = "Fire Sword";
    public static final String eqSpiritBlade = "Spirit Blade";
    public static final String eqDragonSlayer ="Dragon Slayer";
    public static final String eqIceDiscus = "Ice Discus";




    //AMULETS
    public static final String eqTalisman = "Talisman";
    public static final String eqAmuletOfSnow="Amulet Of Snow";
    public static final String eqCrystalChain = "Crystal Chain";

    //LEGS
    public static final String eqBrassLegs =" Brass Legs";
    public static final String eqFrostLegs =" Frost Legs";

    //Shield
    public static final String eqIceShield = "Ice Shield";




    //Armor
    public static final String eqFrostArmor = "Frost Armor";
    public static final String eqGown = "Gown";

    //Helmets
    public static final String eqFrostHelmet = "Frost Helmet";


    //RINGS
    public static final String eqCrystalRing = "Crystal Ring";




    public static final String eqTempestRod = "Tempest Rod";
    public static final String eqVulcanicAxe = "Vulcanic Axe";
    public static final String eqEmberRune ="Ember Rune";
    public static final String eqSteelSword = "Stalowy miecz";
    public static final String eqSpear = "Dzida";
    public static final String eqHalberd = "Halabarda";
    public static final String eqHatchet = "Siekiera";
    public static final String eqMace = "Buzydygan";
    public static final String eqSabre = "Szabla";
    public static final String eqWarAxe = "Topór wojenny";

    public static final String eqWoodenShield = "Drewniana tarcza";
    public static final String eqSteelShield = "Stalowa tarcza";
    public static final String eqEmeraldShield = "Szmaragdowa tarcza";
    public static final String eqGuardianShield = "Tarcza opiekuna";
    public static final String eqRoyalShield = "Tarcza królewska";

    public static final String eqLeatherArmor = "Skórzana zbroja";
    public static final String eqEmeraldArmor = "Szmaragdowa zbroja";
    public static final String eqRoyalArmor = "Królewska zbroja";

    public static final String eqLeatherLegs = "Skórzane nogawice";
    public static final String eqKnightLegs = "Rycerskie nogawice";
    public static final String eqEmeraldLegs = "Szmaragdowe nogawice";

    public static final String eqLeatherHelmet = "Skórzany hełm";
    public static final String eqBattleHelmet = "Battle Helmet";
    public static final String eqRoundShield = "Round Shield";

    public static final String eqKnightHelmet = "Rycerski hełm";
    public static final String eqEmeraldHelmet = "Szmaragdowy hełm";
    public static final String eqVikingHelmet = "Hełm Wikinga";

    // strings used in game
    public static final String activeTasks = "Aktywne zadania";
    public static final String buy = "Kup";
    public static final String endedTasks = "Zakończone zadania";
    public static final String ended = "Zakończone";
    public static final String informations = "About";
    public static final String active = "active";
    public static final String start = "New Game";
    public static final String save = "save";
    public static final String load = "Continue";
    public static final String takeOn = "take on";
    public static final String takeOff = "take off";
    public static final String throwOut = "drop";
    public static final String trade = "trade";
    public static final String close = "x";
    public static final String menu = "Menu";
    public static final String exit = "exit";
    public static final String end = "end";
    public static final String gameOver = "die";
    public static final String inEQ = "Posiadasz";
    public static final String player = "player";
    public static final String potion = "fliud";
    public static final String price = "price";
    public static final String priceForEach = "Cena za sztukę";
    public static final String sell = "sell";
    public static final String seller = "Sprzedawca";
    public static final String shop = "Sklep";
    public static final String stats = "Stats";
    public static final String exp = "exp";
    public static final String toNextLevel = "exp to next level";

    public static final String attack = "attack";
    public static final String defense = "defense";
    public static final String bonus = "bonus";
    public static final String health = "health";
    public static final String points = "points";
    public static final String gold = "gold";
    public static final String level = "level";

}
