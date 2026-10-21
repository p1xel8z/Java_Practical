import java.util.Objects;


class Point {
    private int x;
    private int y;

    Point(int x,int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {

        return "("+ x + "," + y +")";
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Point p)
        {
         if(this.x == p.x && this.y == p.y)
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
       
        return Objects.hash(x,y);
    }

}