/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 socraticphoenix@gmail.com
 * Copyright (c) 2017 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gmail.socraticphoenix.ill.instructions;

import com.gmail.socraticphoenix.ill.Cell;
import com.gmail.socraticphoenix.ill.Light;
import com.gmail.socraticphoenix.ill.LightSystem;
import com.gmail.socraticphoenix.ill.ProgramMatrix;

public class NumericalOutput extends AbstractInstruction {

    public NumericalOutput() {
        super('n');
    }

    @Override
    public void tickImpl(Light light, Cell cell, ProgramMatrix matrix) {
        double intens = light.getIntensity();
        if((int) intens == intens) {
            LightSystem.out(String.valueOf((int) intens));
        } else {
            LightSystem.out(String.valueOf(intens));
        }
    }

    @Override
    public void postTickImpl(Light light, Cell cell, ProgramMatrix matrix) {

    }

    @Override
    public void dimImpl(Light light, ProgramMatrix matrix) {

    }

}
