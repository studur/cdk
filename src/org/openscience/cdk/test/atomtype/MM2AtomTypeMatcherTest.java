/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 * 
 * Copyright (C) 1997-2005  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */

package org.openscience.cdk.test.atomtype;

import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.openscience.cdk.test.CDKTestCase;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.io.MDLReader;
import org.openscience.cdk.interfaces.AtomType;
import org.openscience.cdk.atomtype.MM2AtomTypeMatcher;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.tools.manipulator.*;
import org.openscience.cdk.tools.AtomTypeTools;

/**
 * Checks the functionality of the AtomType-MMFF94AtomTypeMatcher.
 *
 * @cdk.module test
 *
 * @see org.openscience.cdk.atomtype.MMFF94AtomTypeMatcher
 */
public class MM2AtomTypeMatcherTest extends CDKTestCase {

    public MM2AtomTypeMatcherTest(String name) {
        super(name);
    }

    public void setUp() {}

    public static Test suite() {
        return new TestSuite(MM2AtomTypeMatcherTest.class);
    }
    
    public void testMMFF94AtomTypeMatcher() throws ClassNotFoundException, CDKException, java.lang.Exception {
    	MM2AtomTypeMatcher matcher = new MM2AtomTypeMatcher();
	    assertNotNull(matcher);
	    
    }
    
    public void testFindMatchingAtomType_AtomContainer_Atom() throws ClassNotFoundException, CDKException, java.lang.Exception {
    	//System.out.println("**** START MM2 ATOMTYPE TEST ******");
    	AtomTypeTools att=new AtomTypeTools();
    	Molecule mol=null;
    	MM2AtomTypeMatcher atm= new MM2AtomTypeMatcher();
        BufferedReader fin =null;
        InputStream ins=null;
		try{
			ins = this.getClass().getClassLoader().getResourceAsStream("data/mdl/mmff94AtomTypeTest_molecule.mol");
			fin = new BufferedReader(new InputStreamReader(ins));
			//fin=new BufferedReader(new FileReader("data/mmff94AtomTypeTest_molecule.mol"));
			MDLReader mdl=new MDLReader(fin);
			mol=(Molecule)mdl.read(new Molecule());
		}catch (Exception exc1){
			System.out.println("Problems loading file due to "+exc1.toString());
		}
		//System.out.println("Molecule load:"+mol.getAtomCount());
        att.assignAtomTypePropertiesToAtom(mol);
        for (int i=0;i<mol.getAtomCount();i++){
        	//System.out.print("atomNr:"+i);
        	AtomType matched = atm.findMatchingAtomType(mol, mol.getAtomAt(i));
        	AtomTypeManipulator.configure(mol.getAtomAt(i), matched);       
        }
        
        assertEquals("Sthi",mol.getAtomAt(0).getAtomTypeName());
        assertEquals("Csp2",mol.getAtomAt(7).getAtomTypeName());
        assertEquals("Csp",mol.getAtomAt(51).getAtomTypeName());
        assertEquals("N=C",mol.getAtomAt(148).getAtomTypeName());
        assertEquals("Oar",mol.getAtomAt(198).getAtomTypeName());
        assertEquals("N2OX",mol.getAtomAt(233).getAtomTypeName());
        assertEquals("Nsp2",mol.getAtomAt(256).getAtomTypeName());
      //  System.out.println("**** END OF ATOMTYPE TEST ******");
    }
}
