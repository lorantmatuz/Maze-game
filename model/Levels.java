package model;


public enum Levels {
    EASY(10, 12, .6, 1),
    MEDIUM(20, 18, .7,2 ),
    HARD(35, 25, .8,5 ),
    IMPOSSIBLE(50, 45, .85, 8);

    public final int width, height;
    public final double density;
    public final int numOfDragons;
    public final int lookDistance;

    Levels(int width, int height, double density, int numOfDragons) {
        this.width = width;
        this.height = height;
        this.density = density;
        this.numOfDragons = numOfDragons;
        this.lookDistance = 2;
    }

}
