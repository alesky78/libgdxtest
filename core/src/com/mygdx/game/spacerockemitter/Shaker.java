package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Utility that is able to calculate random shake of a Vector2 
 * 
 * @author Alessandro D'Ottavio
 *
 */
public class Shaker {

	private float shakeDuration = 0;
	private float currentTime = 0;
	private float shakePower = 0;
	private float currentPower = 0;
	private Vector2 pos = new Vector2();

	
	public Shaker(float shakePower) {
		this.shakePower = shakePower;
		this.shakeDuration = 0;
		currentTime = 0;
	}
	
	public Shaker(float shakePower, float shakeDuration) {
		this.shakePower = shakePower;
		this.shakeDuration = shakeDuration;
		currentTime = 0;
	}

	public void reset() {
		currentTime = 0;
	}

	public void reset(float shakeDuration) {
		currentTime = 0;
		this.shakeDuration = shakeDuration;
	}

	public void reset(float shakeDuration,float shakePower) {
		currentTime = 0;
		this.shakeDuration = shakeDuration;
		this.shakePower = shakePower;
	}


	/**
	 * increase the shaker in the configure time
	 * 
	 * @param delta
	 * @return
	 */
	public Vector2 shakeOn(float delta) {

		currentTime += delta;
		
		if (currentTime <= shakeDuration) {

			currentPower = shakePower * FunctionUtils.linearFallOn(currentTime, shakeDuration);

			pos.x = (MathUtils.random(1f) - 0.5f) * 2 * currentPower;
			pos.y = (MathUtils.random(1f) - 0.5f) * 2 * currentPower;

		} else {
			currentTime = shakeDuration;
			pos.set(0, 0);
		}
		return pos;
	}

	

	/**
	 * shaker at the maximum power
	 * 
	 * @param delta
	 * @return
	 */
	public Vector2 shake(float delta) {

		pos.x = (MathUtils.random(1f) - 0.5f) * 2 * shakePower;
		pos.y = (MathUtils.random(1f) - 0.5f) * 2 * shakePower;
		return pos;
	}

	/**
	 * decrease the shaker in the configure time
	 * 
	 * @param delta
	 * @return
	 */
	public Vector2 shakeOff(float delta) {

		currentTime += delta;
		
		if (currentTime <= shakeDuration) {

			currentPower = shakePower * FunctionUtils.linearFallOff(currentTime, shakeDuration);

			pos.x = (MathUtils.random(1f) - 0.5f) * 2 * currentPower;
			pos.y = (MathUtils.random(1f) - 0.5f) * 2 * currentPower;

		} else {
			currentTime = shakeDuration;
			pos.set(0, 0);
		}
		return pos;
	}


	public float getShakeTimeLeft() {
		return shakeDuration - currentTime;
	}


	public Vector2 getPos() {
		return pos;
	}


}


