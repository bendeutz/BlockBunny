package com.bendeutz.blockbunny.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

	
	private boolean playerOnGround;
	
	@Override
	//called wehen two fixtures start to collide
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		//Testkommentar
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			playerOnGround = true;
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("foot")){
			playerOnGround = true;
		}
		
	}

	@Override
	//called when two fixtures no longer collide
	public void endContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")){
			playerOnGround = false;
		}
		
		if(fb.getUserData() != null && fb.getUserData().equals("foot")){
			playerOnGround = false;
		}
	}
	
	
	public boolean playerOnGround(){
		return playerOnGround;
	}

	
	
	
	
	
	
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
