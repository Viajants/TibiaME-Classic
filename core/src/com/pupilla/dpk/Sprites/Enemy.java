package com.pupilla.dpk.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pupilla.dpk.Backend.Constants;
import com.pupilla.dpk.Backend.Item;
import com.pupilla.dpk.Screens.PlayScreen;
import com.pupilla.dpk.Utility;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by orzech on 23.01.2018.
 */

public class Enemy implements Serializable{

    private static final String TAG = "Enemy";
    private transient World world;
    public transient Body body, visibleArea;
    public boolean canMove = false;
    private int x, y;

    public transient Sprite currentSprite;
    public transient Texture enemyTexture;
    public transient Animation<TextureRegion> walkAnimation;
    public transient Animation<TextureRegion> walkLeftAnimation;
    public transient Animation<TextureRegion> walkRightAnimation;
    public transient Animation<TextureRegion> walkUpAnimation;
    public transient Animation<TextureRegion> walkDownAnimation;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    public float stateTime=0f;
    public boolean alive = true;
    public Direction direction = Direction.DOWN;

    public float halfSecondMove = 0f;
    public Sprite healthbar;
    public int maxHealth, currentHealth;
    public int attack, defense;
    public int experience;
    public int damage;

    public boolean randomMovement = true;
    public float walkingTimer=0f;
    public boolean isWalking = false;

    public boolean canHit = false;
    public float hitTimer = 1.2f;

    public Item loot=null;
    private Vector2 position;

    public Enemy(World world, int enemyLevel, Vector2 position){
        this.world = world;
        this.position = position;
        setEnemyLevel(enemyLevel);
    }

    public enum Direction{
        LEFT, RIGHT, UP, DOWN
    }

    public void setup(AssetDescriptor<Texture> sheet){
        healthbar = new Sprite(new Texture(Gdx.files.internal(Constants.healthBar)));
        healthbar.setSize(15, 10);

        Utility ut = new Utility();
        ut.manager.load(sheet);
        ut.manager.finishLoading();
        enemyTexture = ut.manager.get(sheet);

        currentSprite = new Sprite(enemyTexture);
        currentSprite.setSize(Gdx.graphics.getWidth()/ Constants.UNIT_SCALE, Gdx.graphics.getHeight()/ Constants.UNIT_SCALE);

        TextureRegion[][] tmp = TextureRegion.split(enemyTexture, enemyTexture.getWidth()/FRAME_COLS, enemyTexture.getHeight()/FRAME_ROWS);

        TextureRegion[] walkUpFrames = new TextureRegion[FRAME_ROWS];
        TextureRegion[] walkDownFrames = new TextureRegion[FRAME_ROWS];
        TextureRegion[] walkLeftFrames = new TextureRegion[FRAME_ROWS];
        TextureRegion[] walkRightFrames = new TextureRegion[FRAME_ROWS];

        int wd=0, wl=0, wu=0, wr=0;

        for(int i=0; i<FRAME_ROWS; i++){
            for(int j=0; j<FRAME_COLS; j++){
                //walkFrames[index++] = tmp[i][j];
                switch(i){
                    //down
                    case 0:
                        walkDownFrames[wd++] = tmp[i][j];
                        break;
                    //left
                    case 1:
                        walkLeftFrames[wl++] = tmp[i][j];
                        break;
                    //right
                    case 2:
                        walkRightFrames[wr++] = tmp[i][j];
                        break;
                    //up
                    case 3:
                        walkUpFrames[wu++] = tmp[i][j];
                        break;
                }
            }
        }

        walkAnimation = new Animation<TextureRegion>(0.1f, walkDownFrames);
        walkLeftAnimation = new Animation<TextureRegion>(0.1f, walkLeftFrames);
        walkRightAnimation = new Animation<TextureRegion>(0.1f, walkRightFrames);
        walkUpAnimation = new Animation<TextureRegion>(0.1f, walkUpFrames);
        walkDownAnimation = new Animation<TextureRegion>(0.1f, walkDownFrames);
        stateTime = 0f;
    }

    /**
     * Defining body of object
     */
    public void defineBody(){
        currentSprite.setPosition(position.x, position.y);
        BodyDef bdef = new BodyDef();
        bdef.position.set(currentSprite.getX(), currentSprite.getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14, 14);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 99999;
        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.BIT_ENEMY; /* is a... */
        fdef.filter.maskBits = Constants.BIT_WALL | Constants.BIT_PLAYER | Constants.BIT_NPC | Constants.BIT_ENEMY; /* collides with... */
        body.createFixture(fdef).setUserData(this);
        body.setLinearDamping(20f);

        bdef = new BodyDef();
        bdef.position.set(currentSprite.getX()-112, currentSprite.getY()-112);
        bdef.type = BodyDef.BodyType.DynamicBody;
        visibleArea = world.createBody(bdef);

        CircleShape shape1 = new CircleShape();
        shape1.setRadius(128);
        //shape.setAsBox(128, 128);
        fdef = new FixtureDef();
        fdef.isSensor = true;
        fdef.shape = shape1;
        body.createFixture(fdef).setUserData(visibleArea);
    }

    public void hit(){
        int playerDef = PlayScreen.player.defense+PlayScreen.player.eq.getStatsBoostSum()[1];
        // if attack of monster is higher or equal to players defense
        Random r = new Random();
        int hit = r.nextInt((getMaxDamage()-getMinDamage()))+getMinDamage()+1;
        if(attack>=playerDef){
            if((attack-playerDef)<=3){
                // 60% of attack power
                PlayScreen.player.currentHealth-=(hit*0.6);
                Gdx.app.debug(TAG, "Monster hit: "+(hit*0.6));
            }else if(attack-playerDef>3 && attack-playerDef<=8){
                // 80% of attack power
                PlayScreen.player.currentHealth-=(hit*0.8);
                Gdx.app.debug(TAG, "Monster hit: "+(hit*0.8));
            }else{
                // 100% of attack power
                PlayScreen.player.currentHealth-=hit;
                Gdx.app.debug(TAG, "Monster hit: "+(hit));
            }
        }else{
            if(playerDef-attack<=3){
                // 40% of attack power
                PlayScreen.player.currentHealth-=(hit*0.4);
                Gdx.app.debug(TAG, "Monster hit: "+(hit*0.4));
            }else if(playerDef-attack>3 && playerDef-attack<=8){
                // 20% of attack power
                PlayScreen.player.currentHealth-=(hit*0.2);
                Gdx.app.debug(TAG, "Monster hit: "+(hit*0.2));
            }else{
                // 10% of attack power
                PlayScreen.player.currentHealth-=(hit*0.1);
                Gdx.app.debug(TAG, "Monster hit: "+(hit*0.1));
            }
        }
    }

    private int getMinDamage(){
        return (int)(1+(damage*0.1)*4);
    }

    private int getMaxDamage(){
        return (int)(2+(damage*0.2)*4.2);
    }

    private void setEnemyLevel(int level){
        Random r = new Random();
        int lootID = r.nextInt(11);
        switch (level){
            case 1:default:
                maxHealth = 1;
                currentHealth = maxHealth;
                attack = 2;
                defense = 5;
                experience = 15;
                damage = 2;
                if(lootID>=4 && lootID<=5){
                    loot = new Item(Constants.eqIceDiscus, 1, Item.Type.weapon);
                }
                if(lootID>6 && lootID<=8){
                    loot = new Item(Constants.eqGown,4, Item.Type.armor);
                }
                if(lootID==9){
                    loot = new Item(Constants.eqIceDiscus,1, Item.Type.weapon);
                }
                if(lootID==10){
                    loot = new Item(Constants.eqCrystalChain,1, Item.Type.amulet);
                }
                break;
            case 2:
                maxHealth = 1;
                currentHealth = maxHealth;
                attack = 5;
                defense = 6;
                experience = 25;
                damage = 8;
                if(lootID>=4 && lootID<=5){
                    loot = new Item(Constants.eqBrassLegs, 1, Item.Type.legs);
                }
                if(lootID>=6 && lootID<=7){
                    loot = new Item(Constants.eqFireSword, 1, Item.Type.weapon);
                }
                if(lootID>=8 && lootID<=9){
                    loot = new Item(Constants.eqTempestRod, 1, Item.Type.weapon);
                }
//                if(lootID==10){
//                    loot = new Item(Constants.eqTalisman, 1, Item.Type.armor);
//                }
                break;
            case 3:
                maxHealth = 40;
                currentHealth = maxHealth;
                attack = 8;
                defense = 12;
                experience = 40;
                damage = 12;
                if(lootID>=4 && lootID<=5){
                    loot = new Item(Constants.eqHatchet, 2, Item.Type.weapon);
                }
                if(lootID>=6 && lootID<=7){
                    loot = new Item(Constants.eqSteelShield, 2, Item.Type.shield);
                }
                if(lootID>=8 && lootID<=9){
                    loot = new Item(Constants.eqKnightLegs, 2, Item.Type.legs);
                }
                if(lootID==10){
                    loot = new Item(Constants.eqKnightHelmet, 2, Item.Type.helmet);
                }
                break;
            case 4:
                maxHealth = 60;
                currentHealth = maxHealth;
                attack = 15;
                defense = 15;
                experience = 70;
                damage = 18;
                if(lootID>=4 && lootID<=5){
                    loot = new Item(Constants.eqMace, 2, Item.Type.weapon);
                }
                if(lootID>=6 && lootID<=7){
                    loot = new Item(Constants.eqEmeraldShield, 2, Item.Type.shield);
                }
                if(lootID>=8 && lootID<=9){
                    loot = new Item(Constants.eqKnightLegs, 3, Item.Type.legs);
                }
                if(lootID==10){
                    loot = new Item(Constants.eqEmeraldArmor, 3, Item.Type.armor);
                }
                break;
            case 5:
                maxHealth = 160;
                currentHealth = maxHealth;
                attack = 28;
                defense = 20;
                experience = 120;
                damage = 25;
                if(lootID>=4 && lootID<=5){
                    loot = new Item(Constants.eqSabre, 3, Item.Type.weapon);
                }
                if(lootID>=6 && lootID<=7){
                    loot = new Item(Constants.eqEmeraldShield, 3, Item.Type.shield);
                }
                if(lootID>=8 && lootID<=9){
                    loot = new Item(Constants.eqEmeraldLegs, 3, Item.Type.legs);
                }
                if(lootID==10){
                    loot = new Item(Constants.eqEmeraldHelmet, 3, Item.Type.helmet);
                }
                break;
            case 6:
                maxHealth = 250;
                currentHealth = maxHealth;
                attack = 40;
                defense = 30;
                experience = 200;
                damage = 35;
                if(lootID>=4 && lootID<=5){
                    loot = new Item(Constants.eqSabre, 3, Item.Type.weapon);
                }
                if(lootID>=6 && lootID<=7){
                    loot = new Item(Constants.eqGuardianShield, 4, Item.Type.shield);
                }
                if(lootID>=8 && lootID<=9){
                    loot = new Item(Constants.eqEmeraldLegs, 4, Item.Type.legs);
                }
                if(lootID==10){
                    loot = new Item(Constants.eqEmeraldHelmet, 4, Item.Type.helmet);
                }
                break;
            case 7:
                maxHealth = 500;
                currentHealth = maxHealth;
                attack = 50;
                defense = 40;
                experience = 450;
                damage = 40;
                if(lootID==6){
                    loot = new Item(Constants.eqRoyalArmor, 5, Item.Type.armor);
                }
                if(lootID==7){
                    loot = new Item(Constants.eqWarAxe, 5, Item.Type.weapon);
                }
                if(lootID==8){
                    loot = new Item(Constants.eqRoyalShield, 5, Item.Type.shield);
                }
                if(lootID==9){
                    loot = new Item(Constants.eqEmeraldLegs, 5, Item.Type.legs);
                }
                if(lootID==10){
                    loot = new Item(Constants.eqVikingHelmet, 5, Item.Type.helmet);
                }
                break;
        }
    }
}
