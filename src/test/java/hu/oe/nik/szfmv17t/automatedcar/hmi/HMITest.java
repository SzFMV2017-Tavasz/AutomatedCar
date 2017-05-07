package hu.oe.nik.szfmv17t.automatedcar.hmi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by MikiWork on 5/7/2017.
 */
public class HMITest extends HMI {


    public HMITest() {
        super();
    }

    @Test
    public void brake() throws Exception {
        this.gasPedal.setState(50);
        this.Brake();
        Assert.assertEquals(this.getGaspedalValue(), 0);
    }

    @Test
    public void addGasTest() throws Exception {
        this.brakePedal.setState(20);
        this.addGas();
        Assert.assertEquals(this.brakePedal.getState(), 0);

    }
}