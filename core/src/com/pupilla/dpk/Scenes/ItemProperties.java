package com.pupilla.dpk.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pupilla.dpk.Backend.Backpack;
import com.pupilla.dpk.Backend.Constants;
import com.pupilla.dpk.Backend.Item;
import com.pupilla.dpk.Screens.BackpackScreen;
import com.pupilla.dpk.Screens.PlayScreen;

import java.util.Arrays;

/**
 * Created by orzech on 30.12.2017.
 */

public class ItemProperties extends Dialog{
    private static final String TAG = "ItemProperties";

    private Skin skin;
    private int index;
    private Item item;
    public ItemProperties(Skin skin, int index, String name) {
        super(name, skin);
        this.skin = skin;
        this.index = index;
        item = PlayScreen.player.backpack.itemArr[index];

        prepareWindow();
    }

    @Override
    protected void result(Object object){
        if(object!=null){
            int action = (Integer) object;
            int a = index;
            switch(action){
                case 0:
                    switch(item.type){
                        case weapon:
                            if(PlayScreen.player.eq.weapon==null)
                                PlayScreen.player.backpack.itemsInBp--;
                            PlayScreen.player.backpack.itemArr[a] = PlayScreen.player.eq.weapon;
                            PlayScreen.player.eq.weapon = item;
                            break;
                        case shield:
                            if(PlayScreen.player.eq.shield==null)
                                PlayScreen.player.backpack.itemsInBp--;
                            PlayScreen.player.backpack.itemArr[a] = PlayScreen.player.eq.shield;
                            PlayScreen.player.eq.shield = item;
                            break;
                        case armor:
                            if(PlayScreen.player.eq.armor==null)
                                PlayScreen.player.backpack.itemsInBp--;
                            PlayScreen.player.backpack.itemArr[a] = PlayScreen.player.eq.armor;
                            PlayScreen.player.eq.armor = item;
                            break;
                        case helmet:
                            if(PlayScreen.player.eq.helmet==null)
                                PlayScreen.player.backpack.itemsInBp--;
                            PlayScreen.player.backpack.itemArr[a] = PlayScreen.player.eq.helmet;
                            PlayScreen.player.eq.helmet = item;
                            break;
                        case legs:
                            if(PlayScreen.player.eq.legs==null)
                                PlayScreen.player.backpack.itemsInBp--;
                            PlayScreen.player.backpack.itemArr[a] = PlayScreen.player.eq.legs;
                            PlayScreen.player.eq.legs = item;
                            break;

                        case ring:
                            if(PlayScreen.player.eq.ring==null)
                                PlayScreen.player.backpack.itemsInBp--;
                            PlayScreen.player.backpack.itemArr[a] = PlayScreen.player.eq.ring;
                            PlayScreen.player.eq.ring = item;
                            break;

                        case amulet:
                            if(PlayScreen.player.eq.amulet==null)
                                PlayScreen.player.backpack.itemsInBp--;
                            PlayScreen.player.backpack.itemArr[a] = PlayScreen.player.eq.amulet;
                            PlayScreen.player.eq.amulet = item;
                            break;



                    }
                    BackpackScreen.refresh = true;
                    remove();
                    break;
                case 1:
                    PlayScreen.player.backpack.removeItem(a);
                    BackpackScreen.refresh = true;
                    remove();
                    break;
                case 2:
                    remove();
                    break;
                default: break;
            }
        }

    }

    private void prepareWindow(){
        Table table = new Table();

        // attack, defense, bonusdmg, health
        int a, d, b, h;

        switch(item.type){
            case weapon:
                if(PlayScreen.player.eq.weapon==null){
                    a=0;
                    d=0;
                    b=0;
                    h=0;
                }else{
                    a = PlayScreen.player.eq.weapon.atk;
                    d = PlayScreen.player.eq.weapon.def;
                    b = PlayScreen.player.eq.weapon.dmgbonus;
                    h = PlayScreen.player.eq.weapon.healthbonus;
                }

                fillTable(table, a, d, b, h);
                break;
            case armor:
                if(PlayScreen.player.eq.armor==null){
                    a=0;
                    d=0;
                    b=0;
                    h=0;
                }else{
                    a = PlayScreen.player.eq.armor.atk;
                    d = PlayScreen.player.eq.armor.def;
                    b = PlayScreen.player.eq.armor.dmgbonus;
                    h = PlayScreen.player.eq.armor.healthbonus;
                }

                fillTable(table, a, d, b, h);
                break;
            case shield:
                if(PlayScreen.player.eq.shield==null){
                    a=0;
                    d=0;
                    b=0;
                    h=0;
                }else{
                    a = PlayScreen.player.eq.shield.atk;
                    d = PlayScreen.player.eq.shield.def;
                    b = PlayScreen.player.eq.shield.dmgbonus;
                    h = PlayScreen.player.eq.shield.healthbonus;
                }

                fillTable(table, a, d, b, h);
                break;

            case ring:
                if(PlayScreen.player.eq.ring==null){
                    a=0;
                    d=0;
                    b=0;
                    h=0;
                }else{
                    a = PlayScreen.player.eq.ring.atk;
                    d = PlayScreen.player.eq.ring.def;
                    b = PlayScreen.player.eq.ring.dmgbonus;
                    h = PlayScreen.player.eq.ring.healthbonus;
                }

                fillTable(table, a, d, b, h);
                break;


            case amulet:
                if(PlayScreen.player.eq.amulet==null){
                    a=0;
                    d=0;
                    b=0;
                    h=0;
                }else{
                    a = PlayScreen.player.eq.amulet.atk;
                    d = PlayScreen.player.eq.amulet.def;
                    b = PlayScreen.player.eq.amulet.dmgbonus;
                    h = PlayScreen.player.eq.amulet.healthbonus;
                }

                fillTable(table, a, d, b, h);
                break;


            case helmet:
                if(PlayScreen.player.eq.helmet==null){
                    a=0;
                    d=0;
                    b=0;
                    h=0;
                }else{
                    a = PlayScreen.player.eq.helmet.atk;
                    d = PlayScreen.player.eq.helmet.def;
                    b = PlayScreen.player.eq.helmet.dmgbonus;
                    h = PlayScreen.player.eq.helmet.healthbonus;
                }

                fillTable(table, a, d, b, h);
                break;
            case legs:
                if(PlayScreen.player.eq.legs==null){
                    a=0;
                    d=0;
                    b=0;
                    h=0;
                }else{
                    a = PlayScreen.player.eq.legs.atk;
                    d = PlayScreen.player.eq.legs.def;
                    b = PlayScreen.player.eq.legs.dmgbonus;
                    h = PlayScreen.player.eq.legs.healthbonus;
                }

                fillTable(table, a, d, b, h);
                break;

        }
        button(Constants.takeOn, 0);
        button(Constants.throwOut, 1);
        button(Constants.close, 2);
        getContentTable().add(table);
    }

    private void fillTable(Table table, int a, int d, int b, int h){
        int difference;
        BitmapFont bf = new BitmapFont(Gdx.files.internal(Constants.font));
        Label.LabelStyle greenFont = new Label.LabelStyle(bf, Color.GREEN);
        Label.LabelStyle redFont = new Label.LabelStyle(bf, Color.RED);
        float scale = 0.75f;

        Label attack = new Label(Constants.attack+": ", skin);
        Label defense = new Label(Constants.defense+": ", skin);
        Label bonus = new Label(Constants.bonus+": ", skin);
        Label health = new Label(Constants.health+": ", skin);

        attack.setFontScale(scale);
        defense.setFontScale(scale);
        bonus.setFontScale(scale);
        health.setFontScale(scale);
        difference = item.atk-a;
        if(item.atk>0){
            table.add(attack);
            table.add(new Label(item.atk+" ", skin));
            if(difference>0){
                table.add(new Label("+"+difference, greenFont));
                table.row();
            }else if(difference<0){
                table.add(new Label(difference+"", redFont));
                table.row();
            }else{
                table.row();
            }
        }else if(item.atk==0 && a>0){
            table.add(attack);
            table.add(new Label(item.atk+" ", skin));
            table.add(new Label(difference+"", redFont));
            table.row();
        }

        difference = item.def-d;
        if(item.def>0){
            table.add(defense);
            table.add(new Label(item.def+" ", skin));
            if(difference>0){
                table.add(new Label("+"+difference, greenFont));
                table.row();
            }else if(difference<0){
                table.add(new Label(difference+"", redFont));
                table.row();
            }else{
                table.row();
            }
        }else if(item.def==0 && d>0){
            table.add(defense);
            table.add(new Label(item.def+" ", skin));
            table.add(new Label(difference+"", redFont));
            table.row();
        }

        difference = item.dmgbonus-b;
        if(item.dmgbonus>0){
            table.add(bonus);
            table.add(new Label(item.dmgbonus+" ", skin));
            if(difference>0){
                table.add(new Label("+"+difference, greenFont));
                table.row();
            }else if(difference<0){
                table.add(new Label(difference+"", redFont));
                table.row();
            }else{
                table.row();
            }
        }else if(item.dmgbonus==0 && b>0){
            table.add(bonus);
            table.add(new Label(item.dmgbonus+" ", skin));
            table.add(new Label(difference+"", redFont));
            table.row();
        }

        difference = item.healthbonus-h;
        if(item.healthbonus>0){
            table.add(health);
            table.add(new Label(item.healthbonus+" ", skin));
            if(difference>0){
                table.add(new Label("+"+difference, greenFont));
                table.row();
            }else if(difference<0){
                table.add(new Label(difference+"", redFont));
                table.row();
            }else{
                table.row();
            }
        }else if(item.healthbonus==0 && h>0){
            table.add(health);
            table.add(new Label(item.healthbonus+" ", skin));
            table.add(new Label(difference+"", redFont));
            table.row();
        }
    }
}
