/*
 * This file is part of the PSL software.
 * Copyright 2011 University of Maryland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.umd.cs.psl.application.learning.weight;

import java.util.HashSet;
import java.util.Set;

import edu.umd.cs.psl.model.atom.Atom;
import edu.umd.cs.psl.model.atom.GroundAtom;
import edu.umd.cs.psl.model.kernel.BindingMode;
import edu.umd.cs.psl.model.kernel.CompatibilityKernel;
import edu.umd.cs.psl.model.kernel.GroundCompatibilityKernel;
import edu.umd.cs.psl.model.parameters.PositiveWeight;
import edu.umd.cs.psl.model.parameters.Weight;
import edu.umd.cs.psl.reasoner.function.FunctionSum;
import edu.umd.cs.psl.reasoner.function.FunctionSummand;
import edu.umd.cs.psl.reasoner.function.FunctionTerm;

/**
 * Special ground kernel that penalizes being close to a fixed value
 * 
 * @author Bert Huang <bert@cs.umd.edu>
 */
public class LossAugmentingGroundKernel implements GroundCompatibilityKernel {
	
	/**
	 * 
	 * @param atom 
	 * @param truthValue
	 */
	public LossAugmentingGroundKernel(GroundAtom atom, double truthValue) {
		this.atom = atom;
		this.groundTruth = truthValue;
	}

	@Override
	public boolean updateParameters() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CompatibilityKernel getKernel() {
		return null;
	}

	@Override
	public Set<GroundAtom> getAtoms() {
		Set<GroundAtom> ret = new HashSet<GroundAtom>();
		ret.add(atom);
		return ret;
	}

	@Override
	public double getIncompatibility() {
		return 1 - Math.abs(atom.getValue() - this.groundTruth);
	}

	@Override
	public BindingMode getBinding(Atom atom) {
		// TODO Auto-generated method stub (uh what?)
		return null;
	}

	@Override
	public Weight getWeight() {
		return new PositiveWeight(1.0);
	}

	@Override
	public double getIncompatibilityDerivative(int parameterNo) {
		return 1.0;
	}

	@Override
	public double getIncompatibilityHessian(int parameterNo1, int parameterNo2) {
		return 0.0;
	}

	@Override
	public FunctionTerm getFunctionDefinition() {
		FunctionSum sum = new FunctionSum();
		if (groundTruth == 1.0) {
			sum.add(new FunctionSummand(1.0, atom.getVariable()));
		}
		else if (groundTruth == 0.0) {
			sum.add(new FunctionSummand(-1.0, atom.getVariable()));
		}
		else {
//			throw new IllegalStateException("Ground truth is not 0 or 1.");
			sum.add(new FunctionSummand(1.0, atom.getVariable()));
		}
		
		return sum;
	}

	
	private GroundAtom atom;
	private double groundTruth;
}
