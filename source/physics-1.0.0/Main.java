package a.a;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Array<Object> objects;
    private Array<Rectangle> objhitboxes;
    private Object plr;
    private FitViewport viewport = new FitViewport(640, 480);
    private float worldWidth = viewport.getWorldWidth();
    private float worldHeight = viewport.getWorldHeight();
    private float floorBounciness = 0.1f;
    private float wallBounciness = 0.1f;
    private float floorFriction = 1.05f;
    private float gravity = 1f;
    private float airFriction = 1.05f;
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        objects = new Array<>();
        objhitboxes = new Array<>();
        //newobj(image, 128, 256, 64, 64, 25, 10);
        //newobj(image, 256, 128, 64, 64, -25, 10);
        plr = newobj(image, 0, 0, 64, 64);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        move(plr, 20, 5);
        logic();
        batch.begin();
        for(Object obj : objects){
            obj.draw(batch);
        }
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }
    public void logic(){
        objhitboxes.clear();
        for (Object v : objects){
            gravity(v); // apply stuff
            drag(v);
            v.translate(v.xvel, v.yvel);
            if(v.getY() < 0){ // collide with borders
                v.yvel = Math.abs(v.yvel * floorBounciness * v.bounciness);
                v.xvel /= floorFriction;
                
            }
            if(v.getX() < 0 || v.getX() > worldWidth - v.getWidth()){
                v.xvel *= -wallBounciness * v.bounciness;
            }
            v.setY(MathUtils.clamp(v.getY(), 0, worldHeight - v.getHeight())); // clamp position
            v.setX(MathUtils.clamp(v.getX(), 0, worldWidth - v.getWidth()));
        }
    }
    public void gravity(Object obj){ //applies gravity to obj
        obj.yvel -= gravity;
    }
    public void drag(Object obj){ //applies drag aka air friction to obj
        obj.yvel /= airFriction;
        obj.xvel /= airFriction;
    }
    public void move(Object obj, float jumppower, float walkspeed){
        if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP)) && obj.getY() <= 0){
            obj.yvel = jumppower;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            obj.xvel = walkspeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            obj.xvel = -walkspeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            obj.yvel -= jumppower / 10; //this control is intended to be sorta a dive/slow down
            obj.xvel /= 1.2; //with how walkspeed works this probably doesn't affect movement much if youre also pressing A or D
        }
    }
    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
    public Object newobj(Texture img){
        Object obj = new Object(img);
        obj.setPosition(0, 256);
        obj.setSize(64, 64);
        objects.add(obj);
        return obj;
    }
    public Object newobj(Texture img, float x, float y, float w, float h){
        Object obj = new Object(img);
        obj.setPosition(x, y);
        obj.setSize(w, h);
        objects.add(obj);
        return obj;
    }
    public Object newobj(Texture img, float x, float y, float w, float h, float xv, float yv){
        Object obj = new Object(img, xv, yv);
        obj.setPosition(x, y);
        obj.setSize(w, h);
        objects.add(obj);
        return obj;
    }
}
