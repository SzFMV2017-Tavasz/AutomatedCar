/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
//Object state missing, not added to uml yet.
public interface IWorldObject {
    public int[] getPosition();
    //I think it should be double
    public double getRotation();
    public String getImageFileName();
    public int getZIndex();
    public WorldObjectState state();
}
