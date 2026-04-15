package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private float x = 140f;
    private float y = 210f;
    private boolean dirx = true;
    private boolean diry = true;
    private float speed = 60f;
    /*public static void Main(){
        System.out.print("test");
    }*/
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("Untitled.png");
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        input(delta);
        batch.begin();
        batch.draw(image, x, y);
        batch.end();
        if(dirx == true){
            x += speed * delta;
            if(x + speed * delta > 640 - 128){
                dirx = false;
            }
        }if(diry == true){
            y += speed * delta;
            if(y + speed * delta > 480 - 128){
                diry = false;
            }
        }if(dirx == false){
            x -= speed * delta;
            if(x + speed * delta < 0){
                dirx = true;
            }
        }if(diry == false){
            y -= speed * delta;
            if(y + speed * delta < 0){
                diry = true;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public void input(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;
        }if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;
        }if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * delta;
        }if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * delta;
        }
    }

}