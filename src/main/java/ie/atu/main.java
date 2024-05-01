package ie.atu;

import pool.Operations;

public class main {
    public static void main(String[] args) {
        Operations op = new Operations();

        op.select();
        op.update();
        op.delete();
        op.select();

    }
}
