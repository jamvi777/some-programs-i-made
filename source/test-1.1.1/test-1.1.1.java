package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch; // initialization
    private Texture image;
    private Texture attackimg;
    private Sprite player;
    private Rectangle plrhitbox;
    
    //private Rectangle atkhitbox;
    private float x = 350f;
    private float y = 210f;
    private FitViewport viewport = new FitViewport(640, 480);
    private Boolean hit;
    //private boolean dirx = true;
    //private boolean diry = true;
    private float speed = 180f;
    private float worldWidth = viewport.getWorldWidth();
    private float worldHeight = viewport.getWorldHeight();
    private float playerWidth = 64f;
    private float playerHeight = 64f;
    //private float atkWidth = 64f;
    //private float atkHeight = 64f;
    private Array<Attack> attacks;
    private Array<Rectangle> atkhitboxes;
    /*public static void Main(){
        System.out.print("test");
    }*/
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        attacks = new Array<>();
        atkhitboxes = new Array<>();
        image = new Texture("Untitled.png");
        attackimg = new Texture("attack.png");
        player = new Sprite(image);
        player.setSize(playerWidth, playerHeight);
        player.setPosition(x, y);
        newattack(64, 64, 0, 0);
        newattack(96, 64, 256, 128, true, false, 240f);
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
        for(Sprite attack : attacks){
            attack.draw(batch);
        }
        player.draw(batch);
        batch.end();
        
        
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public void input(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.translateX(speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.translateX(-speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.translateY(speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.translateY(-speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.D)){
            player.translateX(speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.A)){
            player.translateX(-speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.translateY(speed * delta);
        }if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player.translateY(-speed * delta);
        }
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }
    private void logic(float delta){
        plrhitbox = player.getBoundingRectangle();
        atkhitboxes.clear();
        for(Sprite attack : attacks){
            atkhitboxes.add(attack.getBoundingRectangle());
        }
        player.setX(MathUtils.clamp(player.getX(), 0, worldWidth - playerWidth));
        player.setY(MathUtils.clamp(player.getY(), 0, worldHeight - playerHeight));
        for(Rectangle atkhitbox : atkhitboxes){
            hit = plrhitbox.overlaps(atkhitbox);
            if(hit == true){
                System.exit(-1);
            }
        }
        for(Attack attack : attacks){
            if(attack.dirx == true){
                attack.translateX(attack.speed * delta);
                if(attack.getX() + attack.speed * delta > worldWidth - attack.getWidth()){
                    attack.dirx = false;
                }
            }if(attack.diry == true){
                attack.translateY(attack.speed * delta);
                if(attack.getY() + attack.speed * delta > worldHeight - attack.getHeight()){
                    attack.diry = false;
                }
            }if(attack.dirx == false){
                attack.translateX(-attack.speed * delta);
                if(attack.getX() + attack.speed * delta < 0){
                    attack.dirx = true;
                }
            }if(attack.diry == false){
                attack.translateY(-attack.speed * delta);
                if(attack.getY() + attack.speed * delta < 0){
                    attack.diry = true;
                }
            }
        }
    }

    private void newattack(float w, float h, float x, float y){
        Attack attack = new Attack(attackimg, true, true);
        attack.setSize(w, h);
        attack.setPosition(x, y);
        attacks.add(attack);
    }
    private void newattack(float w, float h, float x, float y, boolean dx, boolean dy){
        Attack attack = new Attack(attackimg, dx, dy);
        attack.setSize(w, h);
        attack.setPosition(x, y);
        attacks.add(attack);
    }
    private void newattack(float w, float h, float x, float y, boolean dx, boolean dy, float s){
        Attack attack = new Attack(attackimg, dx, dy, s);
        attack.setSize(w, h);
        attack.setPosition(x, y);
        attacks.add(attack);
    }
}
