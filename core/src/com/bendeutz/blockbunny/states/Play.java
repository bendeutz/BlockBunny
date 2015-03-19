package com.bendeutz.blockbunny.states;

import static com.bendeutz.blockbunny.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bendeutz.blockbunny.Game;
import com.bendeutz.blockbunny.handlers.B2DVars;
import com.bendeutz.blockbunny.handlers.GameStateManager;
import com.bendeutz.blockbunny.handlers.MyContactListener;
import com.bendeutz.blockbunny.handlers.MyInput;

public class Play extends GameState{
	
	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera b2dCam;
	private Body playerBody;
	private MyContactListener cl;
	
	public Play(GameStateManager gsm){
		super(gsm);
		
		world = new World(new Vector2(0,-9.8f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		
		//create platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM,120/PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50/PPM, 5/PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef).setUserData("ground");
		
		
		
		//create player
		
		bdef.position.set(160/PPM,200/PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		
		shape.setAsBox(5/PPM, 5/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
//		fdef.restitution = 0.8f;
		playerBody.createFixture(fdef).setUserData("player");
		
	
		//create foot sensor
		shape.setAsBox(2/PPM, 2/PPM, new Vector2(0, -5/PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("foot");
		
		
		//set up box2d cam
		
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT/PPM);
		
		//StaticBody - dont move unaffected by forces
		
		//kinematic body - dont get affected forces
		
		//dynamic body - always get affected by forces
		
	}

	@Override
	public void handleInput() {
		
		if(MyInput.isPressed(MyInput.BUTTON1)){
			if(cl.playerOnGround()){
				playerBody.applyForceToCenter(0, 200, true);
			}
		}
		
		if(MyInput.isDown(MyInput.BUTTON2)){
			System.out.println("hold E");
		}
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		world.step(dt, 6, 2);
		
		
	}

	@Override
	public void render() {
		//clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		b2dr.render(world, b2dCam.combined);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
