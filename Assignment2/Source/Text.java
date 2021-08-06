

/**
 *
 * @author yezixin
 */
public class Text implements IText {
    private final String text;
    private final float X;
    private final float Y;
    
    public Text(String text, float X, float Y){
        this.text = text;
        this.X = X;
        this.Y = Y;
    }
   

    @Override
    public String getString(){
        return this.text;
    }

    @Override
    public float getX(){
        return this.X;
    }

    @Override
    public float getY(){
        return this.Y;
    }
    
}