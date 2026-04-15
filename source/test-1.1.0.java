package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch; // initialization
    private Texture image;
    private Texture attackimg;
    private Sprite player;
    private Sprite attack;
    private Rectangle plrhitbox;
    private Rectangle atkhitbox;
    private float x = 350f;
    private float y = 210f;
    private FitViewport viewport = new FitViewport(640, 480);
    private Boolean hit;
    private boolean dirx = true;
    private boolean diry = true;
    private float speed = 180f;
    private float worldWidth = viewport.getWorldWidth();
    private float worldHeight = viewport.getWorldHeight();
    private float playerWidth = 64f;
    private float playerHeight = 64f;
    private float atkWidth = 64f;
    private float atkHeight = 64f;
    /*public static void Main(){
        System.out.print("test");
    }*/
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("Untitled.png");
        attackimg = new Texture("attack.png");
        player = new Sprite(image);
        player.setSize(playerWidth, playerHeight);
        player.setPosition(x, y);
        attack = new Sprite(attackimg);
        attack.setPosition(0, 0);
        attack.setSize(atkWidth, atkHeight);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        input(delta);
        logic(delta);
        batch.begin();
        attack.draw(batch);
        player.draw(batch);
        batch.end();
        
        
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public void input(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.translateX(speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.translateX(-speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.translateY(speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.translateY(-speed * delta);
        }
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }
    private void logic(float delta){
        plrhitbox = player.getBoundingRectangle();
        atkhitbox = attack.getBoundingRectangle();
        player.setX(MathUtils.clamp(player.getX(), 0, worldWidth - playerWidth));
        player.setY(MathUtils.clamp(player.getY(), 0, worldHeight - playerHeight));
        hit = plrhitbox.overlaps(atkhitbox);
        if(hit == true){
            System.exit(-1);
        }
        if(dirx == true){
            attack.translateX(speed * delta);
            
            if(attack.getX() + speed * delta > worldWidth - atkWidth){
                dirx = false;
            }
        }if(diry == true){
            attack.translateY(speed * delta);
            if(attack.getY() + speed * delta > worldHeight - atkHeight){
                diry = false;
            }
        }if(dirx == false){
            attack.translateX(-speed * delta);
            if(attack.getX() + speed * delta < 0){
                dirx = true;
            }
        }if(diry == false){
            attack.translateY(-speed * delta);
            if(attack.getY() + speed * delta < 0){
                diry = true;
            }
        }
    }
}
