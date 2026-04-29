package a.a;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Object extends Sprite{
    float xvel;
    float yvel;
    float bounciness;
    Object(Texture img){
        super(img);
        xvel = 0f;
        yvel = 0f;
        bounciness = 1f;
    }
    Object(Texture img, float xv, float yv){
        super(img);
        xvel = xv;
        yvel = yv;
        bounciness = 1f;
    }
    Object(Texture img, float xv, float yv, float b){
        super(img);
        xvel = xv;
        yvel = yv;
        bounciness = b;
    }
}
