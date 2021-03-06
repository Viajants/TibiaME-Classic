package com.pupilla.dpk.Sprites;

import com.badlogic.gdx.Gdx;
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
import com.pupilla.dpk.Backend.Backpack;
import com.pupilla.dpk.Backend.Constants;
import com.pupilla.dpk.Backend.Equipment;
import com.pupilla.dpk.Backend.Item;
import com.pupilla.dpk.Screens.PlayScreen;
import com.pupilla.dpk.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Damian on 30.04.2017.
 */

public class Hero implements Serializable{

    private static final String TAG = "Hero";
    public transient World world;
    public transient Body b2body;

    public Backpack backpack;
    public Equipment eq;
    public Vector2 position;

    public String map;

    public int experience = 0;
    public int attack = 0;
    public int defense = 0;
    public int damage = 0;
    public int level = 1;
    public int skillPoints = 10;
    public int gold = 0;
    public int potioncount;
    public int maxHealth;
    public int currentHealth;
    public transient Sprite currentSprite;

    public int healed = 0;
    public float hitTimer = 1.2f;

    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;

    private transient Texture heroSheet;

    public transient Animation<TextureRegion> walkAnimation;
    public transient Animation<TextureRegion> walkLeftAnimation;
    public transient Animation<TextureRegion> walkRightAnimation;
    public transient Animation<TextureRegion> walkUpAnimation;
    public transient Animation<TextureRegion> walkDownAnimation;
    public float stateTime;
    public boolean alive = true;
    public Direction direction = Direction.DOWN;

    public int killedMonsters = 0;

    public ArrayList<Integer> changedNPCs = new ArrayList<Integer>();

    public enum Direction{
        LEFT, RIGHT, UP, DOWN
    }

    public Hero(World world){
        this.world = world;
        backpack = new Backpack();
        eq = new Equipment();
        maxHealth = 150;
        currentHealth = maxHealth;
        potioncount = 5;
    }

    public void setup(){
        Utility ut = new Utility();
        ut.manager.load(Utility.heroSheet);
        ut.manager.finishLoading();
        heroSheet = ut.manager.get(Utility.heroSheet);

        currentSprite = new Sprite(heroSheet);
        currentSprite.setSize(Gdx.graphics.getWidth()/ Constants.UNIT_SCALE, Gdx.graphics.getHeight()/ Constants.UNIT_SCALE);

        TextureRegion[][] tmp = TextureRegion.split(heroSheet, heroSheet.getWidth()/FRAME_COLS, heroSheet.getHeight()/FRAME_ROWS);

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
                        System.out.println("walkdownframes1 |"+walkDownFrames+"|");
                        break;
                    //left
                    case 1:
                        walkLeftFrames[wl++] = tmp[i][j];
                        System.out.println("walkleftframes1 |"+walkLeftFrames+"|");
                        break;
                    //right
                    case 2:
                        walkRightFrames[wr++] = tmp[i][j];
                        System.out.println("walkrightframes1 |"+walkRightFrames+"|");

                        break;
                    //up
                    case 3:
                        walkUpFrames[wu++] = tmp[i][j];
                        System.out.println("walkupframes1 |"+walkUpFrames+"|");
                        break;
                }
            }
        }

        walkAnimation = new Animation<TextureRegion>(0.1f, walkDownFrames);
        walkLeftAnimation = new Animation<TextureRegion>(0.1f, walkLeftFrames);
        walkRightAnimation = new Animation<TextureRegion>(0.12f, walkRightFrames);
        walkUpAnimation = new Animation<TextureRegion>(0.1f, walkUpFrames);
        walkDownAnimation = new Animation<TextureRegion>(0.1f, walkDownFrames);
        stateTime = 0f;
    }

    public void defineBody(Vector2 position){
        currentSprite.setPosition(position.x, position.y);
        BodyDef bdef = new BodyDef();
        bdef.position.set(currentSprite.getX(), currentSprite.getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16, 16);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        //fdef.density = 200000;
        fdef.filter.categoryBits = Constants.BIT_PLAYER; /* is a... */
        fdef.filter.maskBits = Constants.BIT_WALL | Constants.BIT_PLAYER | Constants.BIT_ITEM | Constants.BIT_NPC | Constants.BIT_ENEMY
                                    | Constants.BIT_PORTAL; /* collides with... */
        b2body.createFixture(fdef).setUserData("player");
        b2body.setLinearDamping(20f);

        Gdx.app.debug(TAG, currentSprite.getWidth()+" "+currentSprite.getHeight());
    }

    public void usePotion(){
        int currentMaxHealth = calculateHealth();
        if(potioncount>0){
            double heal = 0.33*currentMaxHealth;
            double bonus = bonusHealing();

            if(currentHealth!=currentMaxHealth){
                potioncount--;
                if((int)(currentHealth+heal+bonus)>currentMaxHealth){
                    healed = currentMaxHealth-currentHealth;
                    currentHealth=currentMaxHealth;
                }else{
                    healed = (int)(heal+bonus);
                    currentHealth = (int)(currentHealth+heal+bonus);
                }
                PlayScreen.afterPotion = true;
                PlayScreen._3s = 0f;
            }
        }
    }

    private int bonusHealing(){
        Random r = new Random();
        int percent = (int)(0.1*calculateHealth());
        return r.nextInt(2*percent)-percent;
    }

    public int calculateHealth(){
        return maxHealth+eq.getStatsBoostSum()[3];
    }

    public int getMinDamage(){
        return (int)(1+((damage+getItemsDmgBonus())*0.1)*4);
    }

    public int getMaxDamage(){
        return (int)(2+((damage+getItemsDmgBonus())*0.2)*4.2);
    }

    private int getItemsDmgBonus(){
        return eq.getStatsBoostSum()[2];
    }

    public void increaseAttack(){
        if(skillPoints>0){
            attack++;
            skillPoints--;
        }
    }

    public void increaseDefense(){
        if(skillPoints>0){
            defense++;
            skillPoints--;
        }
    }

    public void increaseDamage(){
        if(skillPoints>0){
            damage++;
            skillPoints--;
        }
    }

    public void increaseHealth(){
        if(skillPoints>0){
            maxHealth+=5;
            skillPoints--;
        }

    }

    public void hit(Enemy enemy){
        int atk = attack+eq.getStatsBoostSum()[0];
        int enemyDef = enemy.defense;

        Random r = new Random();
        int hit = r.nextInt((getMaxDamage()-getMinDamage()))+getMinDamage()+1;
        if(atk>=enemyDef){
            if((atk-enemyDef)<=3){
                // 60% of attack power
                enemy.currentHealth-=(hit*0.6);
            }else if(atk-enemyDef>3 && atk-enemyDef<=8){
                // 80% of attack power
                enemy.currentHealth-=(hit*0.8);
            }else{
                // 100% of attack power
                enemy.currentHealth-=hit;
            }
        }else{
            if(enemyDef-atk<=3){
                // 40% of attack power
                enemy.currentHealth-=(hit*0.4);
            }else if(enemyDef-atk>3 && enemyDef-atk<=8){
                // 20% of attack power
                enemy.currentHealth-=(hit*0.2);
            }else{
                // 10% of attack power
                enemy.currentHealth-=(hit*0.1);
            }
        }
    }
}
