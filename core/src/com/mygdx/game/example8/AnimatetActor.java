package com.mygdx.game.example8;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class AnimatetActor extends BaseActor {


	public float elapsedTime;
	public Animation<TextureRegion> anim;

	public AnimatetActor(){
		super();
		elapsedTime = 0;
	}

	public void setAnimation(Animation<TextureRegion> a){
		Texture t = a.getKeyFrame(0).getTexture();
		setTexture(t);
		anim = a;
	}

	public void act(float dt){
		super.act( dt );
		elapsedTime += dt;
		if (velocityX != 0 || velocityY != 0)
			setRotation( MathUtils.atan2( velocityY, velocityX ) * MathUtils.radiansToDegrees );
	}

	public void draw(Batch batch, float parentAlpha){
		region.setRegion( anim.getKeyFrame(elapsedTime) );
		super.draw(batch, parentAlpha);
	}	

}
