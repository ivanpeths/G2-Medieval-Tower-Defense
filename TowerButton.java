import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TowerButton here.
 * 
 * @author Isaac
 * @version (a version number or a date)
 */
public class TowerButton extends Actor
{
    private String towerName;
    
    public TowerButton (String towerName, String imageFile) {
        this.towerName = towerName;
        GreenfootImage img = new GreenfootImage(imageFile);
        img.scale(60, 60);

        setImage(img);
    }

    public String getTowerName() {
        return towerName;
    }    
}
