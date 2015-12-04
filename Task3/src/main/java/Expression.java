/**
 * Created by Alexandr on 05.12.15.
 */
interface Expression {
    double solve(double x);

    class Num implements Expression{
        final private double value;

        public Num(double value){
            this.value = value;
        }

        public double solve(double x){
            return value;
        }
    }

    class Var implements Expression{
        public double solve(double x){
            return x;
        }
    }

    abstract class Binary implements Expression{
        Expression a;
        Expression b;
        public Binary(Expression a,Expression b){
            this.a = a;
            this.b = b;
        }
    }

    class Sum extends Binary{
        public Sum(Expression a,Expression b){
            super(a,b);
        }
        public double solve(double x){
            return a.solve(x) + b.solve(x);
        }
    }

    class Diff extends Binary{
        public Diff(Expression a,Expression b){
            super(a,b);
        }
        public double solve(double x){
            return a.solve(x) - b.solve(x);
        }
    }

    class Mult extends Binary{
        public Mult(Expression a,Expression b){
            super(a,b);
        }
        public double solve(double x){
            return a.solve(x)*b.solve(x);
        }
    }

    class Quot extends Binary{
        public Quot(Expression a,Expression b){
            super(a,b);
        }
        public double solve(double x){
            return a.solve(x)/b.solve(x);
        }
    }
}
