import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TowerButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TowerButton extends Actor
{
    private String towerName;
    
    public TowerButton (String towerName, String imageFile) {
        this.towerName = towerName;
        setImage(newGreenfootImage(imageFile));
    }

    public String getTowerName() {
        return towerName;
    }    
}
