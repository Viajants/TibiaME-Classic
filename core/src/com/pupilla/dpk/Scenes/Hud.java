package com.pupilla.dpk.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pupilla.dpk.Backend.Backpack;
import com.pupilla.dpk.Backend.Collision;
import com.pupilla.dpk.Backend.Constants;
import com.pupilla.dpk.Screens.BackpackScreen;
import com.pupilla.dpk.Screens.DialogueScreen;
import com.pupilla.dpk.Screens.PlayScreen;
import com.pupilla.dpk.Screens.SettingsScreen;
import com.pupilla.dpk.Sprites.Hero;

/**
 * Created by Damian on 29.04.2017.
 */

public class Hud implements HudRender {

    private static final String TAG = "Hud";
    public Stage stage;
    private ImageButton upbutton, downbutton, leftbutton, rightbutton, backpackbutton, optionsbutton, potionbutton;
    public boolean isTouched = false;
    public Hero.Direction direction;
    private static Game game;
    private Image[] backpackSlots;
    private Image weapon, helmet, shield, armor, legs, weapon1, amulet, weapon3, ring;
    private Backpack backpack;
    private TextureRegion region;
    private TextureRegionDrawable drawableRegion;
    public static boolean refresh = false;
    public boolean backpackSwitch = false;


    private SpriteBatch spriteBatch;
    private Viewport viewport;


    public int width, height;

    public Image health;

    private Label potions;
    Texture cell = new Texture(Gdx.files.internal("sprites/others/backpackitembordertibiame.png"));

    public static ImageButton attackbutton, dialoguebutton, swapbutton;

    public Hud(int width, int height, Game game) {
        Gdx.app.debug(TAG, width + " " + height);
        this.game = game;
        this.width = width;
        this.height = height;
        Texture arrowup = new Texture(Gdx.files.internal("sprites/others/arrowup.png"));
        Texture arrowdown = new Texture(Gdx.files.internal("sprites/others/arrowdown.png"));
        Texture arrowleft = new Texture(Gdx.files.internal("sprites/others/arrowleft.png"));
        Texture arrowright = new Texture(Gdx.files.internal("sprites/others/arrowright.png"));
        Texture backpackimage = new Texture(Gdx.files.internal("sprites/others/backpackimage.png"));
        Texture optionsimage = new Texture(Gdx.files.internal("sprites/others/optiongear.png"));
        Texture healthbar = new Texture(Gdx.files.internal("sprites/others/healthbar.png"));
        Texture potion = new Texture(Gdx.files.internal("sprites/others/potion.png"));


        health = new Image(new Texture(Gdx.files.internal(Constants.healthBar)));
        Texture attackbtn = new Texture(Gdx.files.internal(Constants.attackBtn));
        Texture dialoguebtn = new Texture(Gdx.files.internal(Constants.dialogueBtn));
        Texture swapbtn = new Texture(Gdx.files.internal(Constants.swapBtn));

        TextureRegion region = new TextureRegion(arrowup);
        TextureRegionDrawable drawableRegion = new TextureRegionDrawable(region);
        upbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(arrowdown);
        drawableRegion = new TextureRegionDrawable(region);
        downbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(arrowleft);
        drawableRegion = new TextureRegionDrawable(region);
        leftbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(arrowright);
        drawableRegion = new TextureRegionDrawable(region);
        rightbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(backpackimage);
        drawableRegion = new TextureRegionDrawable(region);
        backpackbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(optionsimage);
        drawableRegion = new TextureRegionDrawable(region);
        optionsbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(healthbar);
        drawableRegion = new TextureRegionDrawable(region);
        ImageButton healthbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(potion);
        drawableRegion = new TextureRegionDrawable(region);
        potionbutton = new ImageButton(drawableRegion);

        region = new TextureRegion(attackbtn);
        drawableRegion = new TextureRegionDrawable(region);
        attackbutton = new ImageButton(drawableRegion);

//        region = new TextureRegion(dialoguebtn);
//        drawableRegion = new TextureRegionDrawable(region);
//        dialoguebutton = new ImageButton(drawableRegion);


        region = new TextureRegion(swapbtn);
        drawableRegion = new TextureRegionDrawable(region);
        swapbutton = new ImageButton(drawableRegion);


        backpack = PlayScreen.player.backpack;
        backpackSlots = new Image[backpack.itemArr.length];

        Viewport viewport = new FitViewport(width, height, new OrthographicCamera());
        stage = new Stage(viewport, new SpriteBatch());
        upbutton.setX(86);
        upbutton.setY(86);
        downbutton.setX(86);
        downbutton.setY(22);
        leftbutton.setX(22);
        leftbutton.setY(22);
        rightbutton.setX(150);
        rightbutton.setY(22);
        backpackbutton.setX(22);
        backpackbutton.setY(height - backpackbutton.getHeight() - 22);
        optionsbutton.setX(width - optionsbutton.getWidth() - 22);
        optionsbutton.setY(height - optionsbutton.getHeight() - 22);
        healthbutton.setX((width / 2) - (healthbutton.getWidth() / 2));
        healthbutton.setY(height - healthbutton.getHeight());

        health.setX(healthbutton.getX() + 2);
        health.setY(healthbutton.getY() + 2);
        Gdx.app.debug(TAG, health.getWidth() + "");

        BitmapFont bf = new BitmapFont(Gdx.files.internal(Constants.font));
        Label.LabelStyle whiteFont = new Label.LabelStyle(bf, Color.WHITE);
        potions = new Label(PlayScreen.player.potioncount + "", whiteFont);
        potions.setFontScale(0.6f);
        potionbutton.setTransform(true);
        potionbutton.setScale(1.5f);
        potionbutton.align(Align.bottomLeft);
        Table potionTable = new Table();
        potionTable.add(potions);
        potionTable.row();
        potionTable.add(potionbutton).width(50).height(50).center();
        potionTable.setPosition(width - 50, height / 2 - 10);

        attackbutton.setX(250);
        attackbutton.setY(45);
        attackbutton.setTransform(true);
        attackbutton.setScale(1.5f);

        swapbutton.setX(width - attackbutton.getWidth() - 45);
        swapbutton.setY(arrowup.getHeight() + 150);
        swapbutton.setTransform(true);
        swapbutton.setScale(1.5f);


        dialoguebutton.setX(attackbutton.getX());
        dialoguebutton.setY(attackbutton.getY());
        dialoguebutton.setTransform(true);
        dialoguebutton.setScale(1.5f);
        dialoguebutton.setVisible(false);

        stage.addActor(upbutton);
        stage.addActor(downbutton);
        stage.addActor(leftbutton);
        stage.addActor(rightbutton);
     //   stage.addActor(backpackbutton);
        stage.addActor(optionsbutton);
     //   stage.addActor(health);
        //   stage.addActor(healthbutton);
        //   stage.addActor(potionTable);
        // stage.addActor(attackbutton);
         stage.addActor(dialoguebutton);
        stage.addActor(swapbutton);
        addListeners();
        Gdx.input.setInputProcessor(stage);

        if (!backpackSwitch) {
            drawBackpack();
            setupArmor();
        }
        // setupItemBackpack();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!refresh) {
            show();
            Gdx.app.debug(TAG, "veikas: ");
            refresh = false;
        }
        spriteBatch.begin();
        spriteBatch.end();
        stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
        spriteBatch.dispose();
    }

    private void show() {
        setupBackpack();
    }

    private void drawBackpack() {
        weapon = new Image(cell);
        weapon1 = new Image(cell);
        amulet = new Image(cell);
        weapon3 = new Image(cell);
        ring = new Image(cell);
        shield = new Image(cell);
        armor = new Image(cell);
        legs = new Image(cell);
        helmet = new Image(cell);

        weapon1.setX(width - 140);
        weapon1.setY(upbutton.getY() + 60);
        helmet.setY(weapon1.getY());
        helmet.setX(weapon1.getX() - 64);

        shield.setX(helmet.getX());
        shield.setY((weapon1.getY() - helmet.getHeight()) + 4);

        armor.setY(weapon1.getY());
        armor.setX(weapon1.getX() + 64);

        amulet.setY(shield.getY());
        amulet.setX(armor.getX());





        weapon.setY(shield.getY());



        legs.setX(shield.getX());
        legs.setY((shield.getY() - shield.getHeight()) + 4);


        weapon3.setX(weapon1.getX());
        weapon3.setY(legs.getY());
        ring.setX(armor.getX());
        ring.setY(weapon3.getY());
        weapon.setX(weapon1.getX());

        stage.addActor(helmet);
        stage.addActor(shield);
        stage.addActor(armor);
        stage.addActor(weapon);
        stage.addActor(weapon1);
        stage.addActor(amulet);
        stage.addActor(weapon3);
        stage.addActor(ring);

        stage.addActor(legs);


    }


    public void setupBackpack() {
        for (int i = 0; i < backpackSlots.length; i++) {
            if (backpack.itemArr[i] != null) {
                // creating image buttons to enter item options
                region = new TextureRegion(backpack.itemArr[i].texture);
                drawableRegion = new TextureRegionDrawable(region);
                drawableRegion.setMinHeight(40f);
                drawableRegion.setMinWidth(40f);
                ImageButton button = new ImageButton(drawableRegion);
                final int index = i;
                button.setX(backpackSlots[i].getX() + 14);
                button.setY(backpackSlots[i].getY() + 14);
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.debug(TAG, "clicked item ID: " + index);
                        ItemProperties ip = new ItemProperties(new Skin(Gdx.files.internal(Constants.skin)), index, backpack.itemArr[index].name);
                        ip.show(stage);
                    }
                });
                stage.addActor(button);
            }
        }
    }


    public void setupArmor() {
        // create items buttons


        // create EQ items buttons
        if (PlayScreen.player.eq.weapon != null) {
            region = new TextureRegion(PlayScreen.player.eq.weapon.texture);
            drawableRegion = new TextureRegionDrawable(region);

            drawableRegion.setMinHeight(40f);
            drawableRegion.setMinWidth(40f);
            ImageButton weaponbtn = new ImageButton(drawableRegion);

            weaponbtn.setX(weapon.getX() + 14);
            weaponbtn.setY(weapon.getY() + 14);
            weaponbtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedItemProperties eip = new EquippedItemProperties(new Skin(Gdx.files.internal(Constants.skin)), PlayScreen.player.eq.weapon);
                    eip.show(stage);
                }
            });
            stage.addActor(weaponbtn);
        }
        if (PlayScreen.player.eq.armor != null) {
            region = new TextureRegion(PlayScreen.player.eq.armor.texture);
            drawableRegion = new TextureRegionDrawable(region);
            drawableRegion.setMinHeight(40f);
            drawableRegion.setMinWidth(40f);
            ImageButton armorbtn = new ImageButton(drawableRegion);
            armorbtn.setX(armor.getX() + 14);
            armorbtn.setY(armor.getY() + 14);
            armorbtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedItemProperties eip = new EquippedItemProperties(new Skin(Gdx.files.internal(Constants.skin)), PlayScreen.player.eq.armor);
                    eip.show(stage);
                }
            });
            stage.addActor(armorbtn);
        }
        if (PlayScreen.player.eq.shield != null) {
            region = new TextureRegion(PlayScreen.player.eq.shield.texture);
            drawableRegion = new TextureRegionDrawable(region);
            drawableRegion.setMinHeight(40f);
            drawableRegion.setMinWidth(40f);
            ImageButton shieldbtn = new ImageButton(drawableRegion);
            shieldbtn.setX(shield.getX() + 14);
            shieldbtn.setY(shield.getY() + 14);
            shieldbtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedItemProperties eip = new EquippedItemProperties(new Skin(Gdx.files.internal(Constants.skin)), PlayScreen.player.eq.shield);
                    eip.show(stage);
                }
            });
            stage.addActor(shieldbtn);
        }
        if (PlayScreen.player.eq.helmet != null) {
            region = new TextureRegion(PlayScreen.player.eq.helmet.texture);
            drawableRegion = new TextureRegionDrawable(region);
            drawableRegion.setMinHeight(40f);
            drawableRegion.setMinWidth(40f);
            ImageButton helmetbtn = new ImageButton(drawableRegion);
            helmetbtn.setX(helmet.getX() + 14);
            helmetbtn.setY(helmet.getY() + 14);
            helmetbtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedItemProperties eip = new EquippedItemProperties(new Skin(Gdx.files.internal(Constants.skin)), PlayScreen.player.eq.helmet);
                    eip.show(stage);
                }
            });
            stage.addActor(helmetbtn);
        }
        if (PlayScreen.player.eq.legs != null) {
            region = new TextureRegion(PlayScreen.player.eq.legs.texture);
            drawableRegion = new TextureRegionDrawable(region);
            drawableRegion.setMinHeight(40f);
            drawableRegion.setMinWidth(40f);
            ImageButton legsbtn = new ImageButton(drawableRegion);
            legsbtn.setX(legs.getX() + 14);
            legsbtn.setY(legs.getY() + 14);
            legsbtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedItemProperties eip = new EquippedItemProperties(new Skin(Gdx.files.internal(Constants.skin)), PlayScreen.player.eq.legs);
                    eip.show(stage);
                }
            });
            stage.addActor(legsbtn);
        }

        if (PlayScreen.player.eq.amulet != null) {
            region = new TextureRegion(PlayScreen.player.eq.amulet.texture);
            drawableRegion = new TextureRegionDrawable(region);
            drawableRegion.setMinHeight(40f);
            drawableRegion.setMinWidth(40f);
            ImageButton amuletbtn = new ImageButton(drawableRegion);
            amuletbtn.setX(amulet.getX() + 14);
            amuletbtn.setY(amulet.getY() + 14);
            amuletbtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedItemProperties eip = new EquippedItemProperties(new Skin(Gdx.files.internal(Constants.skin)), PlayScreen.player.eq.amulet);
                    eip.show(stage);
                }
            });
            stage.addActor(amuletbtn);
        }

        if (PlayScreen.player.eq.ring != null) {
            region = new TextureRegion(PlayScreen.player.eq.ring.texture);
            drawableRegion = new TextureRegionDrawable(region);
            drawableRegion.setMinHeight(40f);
            drawableRegion.setMinWidth(40f);
            ImageButton ringbtn = new ImageButton(drawableRegion);
            ringbtn.setX(ring.getX() + 14);
            ringbtn.setY(ring.getY() + 14);
            ringbtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    EquippedItemProperties eip = new EquippedItemProperties(new Skin(Gdx.files.internal(Constants.skin)), PlayScreen.player.eq.ring);
                    eip.show(stage);
                }
            });
            stage.addActor(ringbtn);
        }




    }

    private void addListeners() {
        upbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                direction = Hero.Direction.UP;
                isTouched = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });

        downbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                direction = Hero.Direction.DOWN;
                isTouched = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });

        leftbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                direction = Hero.Direction.LEFT;
                isTouched = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });

        rightbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                direction = Hero.Direction.RIGHT;
                isTouched = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });

        backpackbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PlayScreen.parent = game.getScreen();
                game.setScreen(new BackpackScreen(game, width, height));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });

        swapbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!backpackSwitch) {
                    drawBackpackItems();
                    setupBackpack();
                    backpackSwitch = true;
                } else if (backpackSwitch) {
                    drawBackpack();
                    setupArmor();
                    backpackSwitch = false;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });


        optionsbutton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PlayScreen.parent = game.getScreen();
                game.setScreen(new SettingsScreen(game, width, height));
                return true;
            }
        });

        potionbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.player.usePotion();
                potions.setText(PlayScreen.player.potioncount + "");
            }
        });

        dialoguebutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!Collision.NPCname.equals("")) {
                    PlayScreen.parent = game.getScreen();
                    game.setScreen(new DialogueScreen(game));
                }

            }
        });

        attackbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean hit = false;
                for (int i = 0; i < PlayScreen.enemies.size(); i++) {
                    if (PlayScreen.enemies.get(i).canHit && PlayScreen.player.hitTimer >= 1f) {
                        PlayScreen.player.hit(PlayScreen.enemies.get(i));
                        hit = true;
                    }
                }
                // if player attacked someone, set hit timer to 0
                if (hit) {
                    PlayScreen.player.hitTimer = 0;
                }
            }
        });
    }

    public static void dialogGame()
    {
        PlayScreen.parent = game.getScreen();
        game.setScreen(new DialogueScreen(game));
    }


    public static void attackEngine() {
        {
            boolean hit = false;
            for (int i = 0; i < PlayScreen.enemies.size(); i++) {
                if (PlayScreen.enemies.get(i).canHit && PlayScreen.player.hitTimer >= 1f) {
                    PlayScreen.player.hit(PlayScreen.enemies.get(i));
                    hit = true;
                }
            }
            // if player attacked someone, set hit timer to 0
            if (hit) {
                PlayScreen.player.hitTimer = 0;
            }
        }
    }


    public void drawBackpackItems() {

        backpackSlots[0] = new Image(cell);
        backpackSlots[1] = new Image(cell);
        backpackSlots[2] = new Image(cell);
        backpackSlots[3] = new Image(cell);
        backpackSlots[4] = new Image(cell);
        backpackSlots[5] = new Image(cell);
        backpackSlots[6] = new Image(cell);
        backpackSlots[7] = new Image(cell);
        backpackSlots[8] = new Image(cell);


        //     backpackSlots[6].setX(width/2-16);                  backpackSlots[6].setY(height/2);
//        backpackSlots[19].setX(backpackSlots[6].getX());    backpackSlots[19].setY(backpackSlots[6].getY()-backpackSlots[6].getHeight()-4);
//        backpackSlots[32].setX(backpackSlots[6].getX());    backpackSlots[32].setY(backpackSlots[19].getY()-backpackSlots[19].getHeight()-4);
//        backpackSlots[45].setX(backpackSlots[6].getX());    backpackSlots[45].setY(backpackSlots[32].getY()-backpackSlots[32].getHeight()-4);

        backpackSlots[0].setY(helmet.getY());
        backpackSlots[0].setX(helmet.getX());

        backpackSlots[1].setX(weapon1.getX());
        backpackSlots[1].setY(weapon1.getY());

        backpackSlots[2].setX(armor.getX());
        backpackSlots[2].setY(armor.getY());

        backpackSlots[3].setY(shield.getY());
        backpackSlots[3].setX(shield.getX());


        backpackSlots[4].setX(weapon.getX());
        backpackSlots[4].setY(weapon.getY());


        backpackSlots[5].setY(amulet.getY());
        backpackSlots[5].setX(amulet.getX());

        backpackSlots[6].setX(legs.getX());
        backpackSlots[6].setY(legs.getY());


        backpackSlots[7].setX(weapon3.getX());
        backpackSlots[7].setY(weapon3.getY());


        backpackSlots[8].setX(ring.getX());
        backpackSlots[8].setY(ring.getY());

        for (int i = 0; i < 9; i++) {
            stage.addActor(backpackSlots[i]);
        }


    }

//
//    public static void setupItemBackpack(){
//
//
//        PlayScreen.parent = game.getScreen();
//        game.setScreen(new BackpackScreen(game, width, height));
//    }
//        // create items buttons
//      final Backpack  backpack = PlayScreen.player.backpack;
//        Image[]  backpackSlots = new Image[backpack.itemArr.length];
//         TextureRegion region;
//         TextureRegionDrawable drawableRegion;
//
//        for (int i = 0; i < backpackSlots.length; i++) {
//            if (backpack.itemArr[i] != null) {
//                // creating image buttons to enter item options
//                region = new TextureRegion(backpack.itemArr[i].texture);
//                drawableRegion = new TextureRegionDrawable(region);
//                ImageButton button = new ImageButton(drawableRegion);
//                final int index = i;
//                button.setX(backpackSlots[i].getX());
//                button.setY(backpackSlots[i].getY());
//                button.addListener(new ClickListener() {
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        Gdx.app.debug(TAG, "clicked item ID: " + index);
//                        ItemProperties ip = new ItemProperties(new Skin(Gdx.files.internal(Constants.skin)), index, backpack.itemArr[index].name);
//                        ip.show(stage);
//                    }
//                });
//                stage.addActor(button);
//            }
//        }
//


}
