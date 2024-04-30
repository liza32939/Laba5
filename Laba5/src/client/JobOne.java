package client;

import java.io.Serializable;
import java.math.BigInteger;
import interfaces.Executable;

public class JobOne implements Executable, Serializable {
    private static final long serialVersionUID = -1L;
    private int n;

    public JobOne(int n) {
        this.n = n;
    }

    @Override
    public Object execute() {
        BigInteger res = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }
}