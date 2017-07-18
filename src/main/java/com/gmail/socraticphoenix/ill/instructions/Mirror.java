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

import com.gmail.socraticphoenix.collect.Items;
import com.gmail.socraticphoenix.ill.Cell;
import com.gmail.socraticphoenix.ill.Direction;
import com.gmail.socraticphoenix.ill.Light;
import com.gmail.socraticphoenix.ill.ProgramMatrix;

import java.util.List;
import java.util.function.Function;

public class Mirror extends AbstractInstruction {
    private Function<Direction, List<Direction>> mirror;

    public Mirror(int id, Function<Direction, List<Direction>> mirror) {
        super(id);
        this.mirror = mirror;
    }

    @Override
    public void tickImpl(Light light, Cell cell, ProgramMatrix matrix) {
        cell.getLight().remove(light);
        List<Direction> drs = this.mirror.apply(light.getDirection());
        for(Direction dr : drs) {
            Light nxt = light.copy();
            nxt.setDirection(dr);
            cell.getLight().add(nxt);
        }
    }

    @Override
    public void postTickImpl(Light light, Cell cell, ProgramMatrix matrix) {

    }

    @Override
    public void dimImpl(Light light, ProgramMatrix matrix) {

    }

}
