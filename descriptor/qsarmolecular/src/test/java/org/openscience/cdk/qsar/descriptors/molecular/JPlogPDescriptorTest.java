package org.openscience.cdk.qsar.descriptors.molecular;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.DescriptorSpecification;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.descriptors.molecular.JPlogPDescriptor;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

public class JPlogPDescriptorTest {

	static SmilesParser parser = null;

	@Test
	public void testPyridine() throws CDKException {
		parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer struct = parseSmiles("c1ncccc1");
		JPlogPDescriptor desc = new JPlogPDescriptor();
		DescriptorValue answer = desc.calculate(struct);
		DoubleResult result = (DoubleResult) answer.getValue();
		double output = result.doubleValue();
		assertEquals(0.9, output, 0.1);
	}

	@Test
	public void testPropionicAcid() throws CDKException {
		parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer struct = parseSmiles("CCC(=O)O");
		JPlogPDescriptor desc = new JPlogPDescriptor();
		DescriptorValue answer = desc.calculate(struct);
		DoubleResult result = (DoubleResult) answer.getValue();
		double output = result.doubleValue();
		assertEquals(0.3, output, 0.1);
	}

	@Test
	public void testAcetonitrile() throws CDKException {
		parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer struct = parseSmiles("CC#N");
		JPlogPDescriptor desc = new JPlogPDescriptor();
		DescriptorValue answer = desc.calculate(struct);
		DoubleResult result = (DoubleResult) answer.getValue();
		double output = result.doubleValue();
		assertEquals(0.4, output, 0.1);
	}

	@Test
	public void testAniline() throws CDKException {
		parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer struct = parseSmiles("Nc1ccccc1");
		JPlogPDescriptor desc = new JPlogPDescriptor();
		DescriptorValue answer = desc.calculate(struct);
		DoubleResult result = (DoubleResult) answer.getValue();
		double output = result.doubleValue();
		assertEquals(1.2, output, 0.1);
	}

	@Test
	public void testFluorobenzene() throws CDKException {
		parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer struct = parseSmiles("Fc1ccccc1");
		JPlogPDescriptor desc = new JPlogPDescriptor();
		DescriptorValue answer = desc.calculate(struct);
		DoubleResult result = (DoubleResult) answer.getValue();
		double output = result.doubleValue();
		assertEquals(2.0, output, 0.1);
	}

	@Test
	public void testSimpleTextFields() {
		JPlogPDescriptor desc = new JPlogPDescriptor();
		DescriptorSpecification specification = desc.getSpecification();
		String name = desc.getDescriptorNames()[0];
		assertEquals("JPlogP developed at Lhasa Limited www.lhasalimited.org",
				specification.getSpecificationReference());
		assertEquals("Jeffrey Plante - Lhasa Limited", specification.getImplementationVendor());
		assertEquals("JPLogP", name);
		assertEquals(1, desc.getParameterNames().length);
		assertEquals("addImplicitH", desc.getParameterNames()[0]);
	}

	@Test
	public void testGetHologram() throws CDKException {
		JPlogPDescriptor desc = new JPlogPDescriptor();
		parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer molecule = parseSmiles("c1ccccc1");
		Map<Integer, Integer> holo = desc.getMappedHologram(molecule);
		assertEquals(2, holo.keySet().size());
		assertEquals(6, holo.get(106204).intValue());
	}



	private static IAtomContainer parseSmiles(String smiles) throws CDKException {
		parser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer molecule = parser.parseSmiles(smiles);
		AtomContainerManipulator.percieveAtomTypesAndConfigureUnsetProperties(molecule);
		AtomContainerManipulator.convertImplicitToExplicitHydrogens(molecule);
		Aromaticity.cdkLegacy().apply(molecule);
		return molecule;
	}

}
