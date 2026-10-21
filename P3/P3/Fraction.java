class Fraction {
    private int num;
    private int den;

    Fraction(int num,int den)
    {
        int g = this.gcd(num,den);
        this.num = num/g;
        this.den = den/g;
    }

   private int gcd(int num, int den) {
        while (den != 0) {
            int g = den;
            den = num % den;
            num = g;
        }
        return num;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

   @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Fraction))
            return false;

        Fraction other = (Fraction) obj;
        return this.num == other.num && this.den == other.den;
    }
   
}