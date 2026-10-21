import java.util.Objects;

class Card {
    private String rank;
    private String suit;

    Card(String rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString()
    {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Card c)
        {
            if( this.rank.equals(c.rank) && this.suit.equals(c.suit))
                return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(rank,suit);
    }


}