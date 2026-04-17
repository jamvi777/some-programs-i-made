package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Attack extends Sprite{
    boolean dirx; //these 2 variables are the only real reason for this file to exist
    boolean diry;
    float speed;
    Attack(Texture image){
        super(image);
        dirx = true;
        diry = true;
        speed = 180f;
    }
    Attack(Texture image, boolean x, boolean y){
        super(image);
        dirx = x;
        diry = y;
        speed = 180f;
    }
    Attack(Texture image, boolean x, boolean y, float spd){
        super(image);
        dirx = x;
        diry = y;
        speed = spd;
    }
}
