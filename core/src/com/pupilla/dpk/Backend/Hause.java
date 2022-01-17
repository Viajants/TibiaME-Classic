package com.pupilla.dpk.Backend;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Hause {

    private static final String TAG = "Hause";
    public Vector2 from, to;
    public String destinationMap;

    public Hause(Vector2 from, String destinationMap){
        this.from = from;
        this.destinationMap = destinationMap;
    }



    public void createBody(World world){
        BodyDef bdef = new BodyDef();
        bdef.position.set(from.x, from.y);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8, 8);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 99999;
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT_HAUSE; /* is a... */
        fdef.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_ITEM; /* collides with... */
        body.createFixture(fdef).setUserData(this);
        body.setLinearDamping(20f);
    }



}
