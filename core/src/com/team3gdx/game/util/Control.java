package com.team3gdx.game.util;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

/**
 * This class is used to handle the controls of the game.
 * It is used to set the controls and set certain attributes to determine action when key is pressed.
 * It is also used to reset/set control attributes when key is released.
 * @author Team3Gdx
 * @author Neves6
 */
public class Control extends InputAdapter implements InputProcessor {

    public boolean up, down, left, right;
    public boolean del;
    private long lastZKeyPressTime = 0; // Handles delay between using powers.
    public boolean interact, drop, flip;
    public boolean tab, shift;
    public boolean power;

    /**
     * used to set controls, set certain attributes to determine action when key is pressed
     * @author Team3Gdx
     * @author Neves6
     */
    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
//		case Keys.DOWN:
//			down = true;
//			break;
//		case Keys.UP:
//			up = true;
//			break;
//		case Keys.LEFT:
//			left = true;
//			break;
//		case Keys.RIGHT:
//			right = true;
//			break;
            case Keys.W:
                up = true;
                break;
            case Keys.A:
                left = true;
                break;
            case Keys.S:
                down = true;
                break;
            case Keys.D:
                right = true;
                break;
            case Keys.DEL:
                del = true;
                break;
            case Keys.Z:
                if (System.currentTimeMillis() - lastZKeyPressTime > 500) { //if its been 0.5 seconds then can press z
                    power = true;
                    lastZKeyPressTime = System.currentTimeMillis();
                }
                break;
        }
        return false;
    }

    /**
     * used to reset/set control attributes when key is released.
     * @author Team3Gdx
     * @author Neves6
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
//		case Keys.DOWN:
//			down = false;
//			break;
//		case Keys.UP:
//			up = false;
//			break;
            case Keys.LEFT:
                tab = true;
                break;
            case Keys.RIGHT:
                shift = true;
                break;
            case Keys.W:
                up = false;
                break;
            case Keys.A:
                left = false;
                break;
            case Keys.S:
                down = false;
                break;
            case Keys.D:
                right = false;
                break;
            case Keys.DEL:
                del = false;
            case Keys.Q:
                interact = true;
                break;
            case Keys.E:
                drop = true;
                break;
            case Keys.F:
                flip = true;
                break;
            case Keys.TAB:
                tab = true;
                break;
//		case Keys.SHIFT_LEFT:
//			shift = true;
//			break;
            case Keys.ESCAPE:
                break;
            case Keys.Z:
                power = false;
                break;
        }
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

}
